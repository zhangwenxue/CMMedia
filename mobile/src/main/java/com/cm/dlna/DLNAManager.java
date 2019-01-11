package com.cm.dlna;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import androidx.core.util.Pair;
import com.cm.dlna.server.MediaServer;
import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.android.AndroidUpnpServiceImpl;
import org.fourthline.cling.controlpoint.ControlPoint;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

import java.lang.ref.WeakReference;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DLNAManager {
    private static final String TAG = "ClingManager";

    public interface InitCallback {
        void onInitSuccess();

        void onInitFailed();

        void onRelease();
    }

    public interface SearchCallback {
        void onDevicesRefresh(List<Device> list);
    }

    private static final int MSG_DEVICE_ADDED = 1;
    private static final int MSG_DEVICE_REMOVED = 2;
    private static final int MSG_SEARCH = 3;
    private static final int MSG_PLAY = 4;
    private static final DeviceType DEVICE_TYPE = new UDADeviceType("MediaRenderer");


    private MediaServer mediaServer;
    private Registry mRegistry;
    private WeakReference<Context> mCtxRef;

    private WeakReference<InitCallback> mInitCallRef;
    private WeakReference<SearchCallback> mSearchCallRef;

    private DLNAPlayer mPlayer;
    private Device mDevice;

    private volatile static DLNAManager instance;
    private AndroidUpnpService upnpService;
    private ControlPoint mControlPoint;
    private final List<Device> mDeviceList = new ArrayList<>();
    private volatile boolean searchCmd = false;
    private volatile boolean playCmd = false;
    private volatile Pair<String, String> mPlayUrl;

    public static DLNAManager getInstance() {
        if (instance == null) {
            synchronized (DLNAManager.class) {
                if (instance == null) {
                    instance = new DLNAManager();
                }
            }
        }
        return instance;
    }

    private DLNAManager() {
    }

    public void setSearchCallback(SearchCallback callback) {
        this.mSearchCallRef = new WeakReference<>(callback);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            upnpService = (AndroidUpnpService) service;
            mRegistry = upnpService.getRegistry();
            upnpService.getRegistry().addListener(mRegistryListener);
            try {
                mediaServer = new MediaServer();
            } catch (ValidationException | SocketException e) {
                e.printStackTrace();
            }
            if (searchCmd) {
                mHandler.obtainMessage(MSG_SEARCH).sendToTarget();
            }
            mControlPoint = upnpService.getControlPoint();
            if (playCmd && mPlayUrl != null && mDevice != null) {
                mHandler.obtainMessage(MSG_PLAY, mPlayUrl).sendToTarget();
            }
            if (checkWeakRef(mInitCallRef)) {
                mInitCallRef.get().onInitSuccess();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (checkWeakRef(mInitCallRef)) {
                mInitCallRef.get().onInitSuccess();
            }
            doRelease();
        }

        @Override
        public void onBindingDied(ComponentName name) {
            if (checkWeakRef(mInitCallRef)) {
                mInitCallRef.get().onInitSuccess();
            }
            doRelease();
        }
    };

    public void init(Context context, InitCallback callback) {
        this.mInitCallRef = new WeakReference<>(callback);
        if (mCtxRef != null && mCtxRef.get() != null) {
            return;
        }
        Objects.requireNonNull(context);
        mCtxRef = new WeakReference<>(context);
        mPlayer = new DLNAPlayer();
        boolean ret = context.bindService(
                new Intent(context.getApplicationContext(), AndroidUpnpServiceImpl.class),
                mServiceConnection,
                Context.BIND_AUTO_CREATE);
        if (!ret && checkWeakRef(mInitCallRef)) {
            mInitCallRef.get().onInitFailed();
        }
    }

    public void setDevice(Device device) {
        this.mDevice = device;
    }

    public Device getDevice() {
        return this.mDevice;
    }

    public void play(Pair<String, String> urlPair) {
        this.mPlayUrl = urlPair;
        Objects.requireNonNull(mPlayUrl);
        Objects.requireNonNull(mDevice);
        if (mControlPoint == null) {
            playCmd = true;
            return;
        }
        mHandler.obtainMessage(MSG_PLAY, mPlayUrl).sendToTarget();
    }

    public List<Device> getDeviceList() {
        return mDeviceList;
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DEVICE_REMOVED:
                    Device removedDevice = (Device) msg.obj;
                    int removedPos = mDeviceList.indexOf(removedDevice);
                    if (removedPos >= 0) {
                        mDeviceList.remove(removedPos);
                        notifyDeviceListChanged();
                    }
                    break;
                case MSG_DEVICE_ADDED:
                    Device addedDevice = (Device) msg.obj;
                    int addedPos = mDeviceList.indexOf(addedDevice);
                    if (addedPos < 0) {
                        mDeviceList.add(addedDevice);
                        notifyDeviceListChanged();
                    }
                    break;
                case MSG_SEARCH:
                    mRegistry.removeAllRemoteDevices();
                    mDeviceList.clear();
                    mRegistry.addDevice(mediaServer.getDevice());
                    mDeviceList.addAll(mRegistry.getDevices());
                    upnpService.getControlPoint().search();
                    notifyDeviceListChanged();
                    break;
                case MSG_PLAY:
                    Pair<String, String> url = (Pair<String, String>) msg.obj;
                    playCmd = false;
                    mPlayer.setUp(mDevice, mControlPoint);
                    mPlayer.play(url.first, url.second);
                    break;
                default:
                    break;
            }
        }
    };


    private final DefaultRegistryListener mRegistryListener = new DefaultRegistryListener() {

        @Override
        public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device, Exception ex) {
            Log.e(TAG, "remoteDeviceDiscoveryFailed," + ex.getMessage());
            mHandler.obtainMessage(MSG_DEVICE_REMOVED, device).sendToTarget();
        }

        @Override
        public void deviceAdded(Registry registry, Device device) {
            if (device == null) {
                Log.e(TAG, "deviceAdded, device is null");
                return;
            }
            if (device.getType().equals(DEVICE_TYPE)) {
                Log.d(TAG, "deviceAdded," + device.getDetails().getFriendlyName());
                mHandler.obtainMessage(MSG_DEVICE_ADDED, device).sendToTarget();
            }
        }

        @Override
        public void deviceRemoved(Registry registry, Device device) {
            if (device == null) {
                Log.e(TAG, "deviceRemoved(),device device is null");
                return;
            }
            if (device.getType().equals(DEVICE_TYPE)) {
                Log.d(TAG, "deviceRemoved," + device.getDetails().getFriendlyName());
                mHandler.obtainMessage(MSG_DEVICE_REMOVED, device).sendToTarget();
            }
        }
    };

    public void search() {
        if (upnpService == null) {
            searchCmd = true;
            return;
        }
        mHandler.obtainMessage(MSG_SEARCH).sendToTarget();
        searchCmd = false;
    }

    public ControlPoint getControlPoint() {
        if (upnpService == null) {
            return null;
        }
        return upnpService.getControlPoint();
    }

    public void release() {
        Objects.requireNonNull(mCtxRef);
        Objects.requireNonNull(mCtxRef.get());
        mCtxRef.get().getApplicationContext().unbindService(mServiceConnection);
        doRelease();
    }


    private void doRelease() {
        if (mRegistry != null) {
            mRegistry.removeListener(mRegistryListener);
            mRegistry.shutdown();
        }
        if (mediaServer != null) {
            mediaServer.stop();
        }
        upnpService = null;
        mCtxRef = null;
        if (checkWeakRef(mInitCallRef)) {
            mInitCallRef.get().onRelease();
        }
    }

    private void notifyDeviceListChanged() {
        if (checkWeakRef(mSearchCallRef)) {
            mSearchCallRef.get().onDevicesRefresh(mDeviceList);
        }
    }

    private boolean checkWeakRef(WeakReference ref) {
        return ref != null && ref.get() != null;
    }
}
