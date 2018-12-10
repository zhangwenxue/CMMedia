package com.cm.media.ui.widget.player.ijkplayer;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import com.cm.media.ui.widget.player.IPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import java.io.IOException;

public class IjkPlayer implements IPlayer {
    private static final String TAG = "IjkPlayer";
    private Uri[] mPlayUris;
    private PlayState curPlayState;
    private PlayStateCallback mCallback;
    private boolean autoPlay;
    private IjkMediaPlayer mPlayer;
    private Uri curUri;
    private int curPlayIndex;
    private int mVideoRotationDegree;

    public IjkPlayer() {
        initPlayer();
    }

    @Override
    public void setUris(Uri... uris) {
        this.mPlayUris = uris;
        if (uris == null || uris.length == 0) {
            if (mCallback != null) {
                mCallback.onError(PlayError.INVALID_URI);
            }
            return;
        }
        curUri = mPlayUris[0];
        curPlayIndex = 0;
        try {
            mPlayer.setDataSource(curUri.toString());
        } catch (IOException e) {
            if (mCallback != null) {
                mCallback.onError(PlayError.EXCEPTION);
            }
        }
    }

    @Override
    public void setSettings(Settings settings) {
        if (!isSettingsEnable() || settings == null) {
            return;
        }
        if (settings.isUseMediaCodec()) {
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
            if (settings.isUseMediaCodecAutoRotate()) {
                mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
            } else {
                mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
            }
            if (settings.isMediaCodecHandleResolutionChange()) {
                mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
            } else {
                mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 0);
            }
        } else {
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
        }
        if (settings.isUseOpenSLES()) {
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);
        } else {
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
        }
        if (TextUtils.isEmpty(settings.getPixelFormat())) {
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
        } else {
            mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", settings.getPixelFormat());
        }

        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);

        mPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
    }

    @Override
    public void prepare() {
        prepareAsync();
    }

    @Override
    public void prepareAsync() {
        mPlayer.prepareAsync();
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
        mPlayer.setDisplay(holder);
    }

    @Override
    public void setSurface(Surface surface) {
        mPlayer.setSurface(surface);
    }

    @Override
    public void play() {
        mPlayer.start();
    }

    @Override
    public void pause() {
        mPlayer.pause();
    }

    @Override
    public void stop() {
        mPlayer.stop();
    }

    @Override
    public void seekTo(long durationTimeSeconds) {
        mPlayer.seekTo(durationTimeSeconds);
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    @Override
    public void setScreenOnWhilePlaying(boolean screenOnWhilePlaying) {
        mPlayer.setScreenOnWhilePlaying(screenOnWhilePlaying);
    }

    @Override
    public void setAspectRatio(AspectRatio aspectRatio) {
        //not supported yet
    }

    @Override
    public long getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public Uri getPlayingUri() {
        return Uri.parse(mPlayer.getDataSource());
    }

    @Override
    public void release() {
        mPlayer.release();
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public boolean isSettingsEnable() {
        return true;
    }

    @Override
    public void setPlayStateCallback(PlayStateCallback callback) {
        this.mCallback = callback;
        mPlayer.setOnPreparedListener(iMediaPlayer -> {
            if (mCallback != null) {
                mCallback.onPrepare();
            }
        });
        mPlayer.setOnVideoSizeChangedListener((iMediaPlayer, width, height, sarNum, sarDen) -> {

        });
        mPlayer.setOnCompletionListener(iMediaPlayer -> {
            if (autoPlay && curPlayIndex < mPlayUris.length - 1) {
                try {
                    mPlayer.setDataSource(mPlayUris[++curPlayIndex].toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    if (mCallback != null) {
                        mCallback.onError(PlayError.EXCEPTION);
                    }
                }
            } else {
                if (mCallback != null) {
                    mCallback.onComplete();
                }
            }
        });
        mPlayer.setOnErrorListener((mp, what, extra) -> {
            if (mCallback != null) {
                mCallback.onError(PlayError.EXCEPTION);
                return true;
            }
            return false;
        });
        mPlayer.setOnInfoListener((mp, what, extra) -> {
            /*if (mOnInfoListener != null) {
                mOnInfoListener.onInfo(mp, arg1, arg2);
            }*/
            switch (what) {
                case IMediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    Log.d(TAG, "MEDIA_INFO_VIDEO_TRACK_LAGGING:");
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    Log.d(TAG, "MEDIA_INFO_VIDEO_RENDERING_START:");
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Log.d(TAG, "MEDIA_INFO_BUFFERING_START:");
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Log.d(TAG, "MEDIA_INFO_BUFFERING_END:");
                    break;
                case IMediaPlayer.MEDIA_INFO_NETWORK_BANDWIDTH:
                    Log.d(TAG, "MEDIA_INFO_NETWORK_BANDWIDTH: " + extra);
                    break;
                case IMediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                    Log.d(TAG, "MEDIA_INFO_BAD_INTERLEAVING:");
                    break;
                case IMediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                    Log.d(TAG, "MEDIA_INFO_NOT_SEEKABLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                    Log.d(TAG, "MEDIA_INFO_METADATA_UPDATE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                    Log.d(TAG, "MEDIA_INFO_UNSUPPORTED_SUBTITLE:");
                    break;
                case IMediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                    Log.d(TAG, "MEDIA_INFO_SUBTITLE_TIMED_OUT:");
                    break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                    mVideoRotationDegree = extra;
                    Log.d(TAG, "MEDIA_INFO_VIDEO_ROTATION_CHANGED: " + extra);
                    /*if (mRenderView != null)
                        mRenderView.setVideoRotation(arg2);*/
                    break;
                case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    Log.d(TAG, "MEDIA_INFO_AUDIO_RENDERING_START:");
                    break;
            }
            return true;
        });
        mPlayer.setOnBufferingUpdateListener((iMediaPlayer, percent) -> {
            if (mCallback != null) {
                mCallback.onBufferingUpdateChange(percent);
            }
        });
        mPlayer.setOnSeekCompleteListener(iMediaPlayer -> {
            if (mCallback != null) {
                mCallback.onSeekComplete();
            }
        });
        mPlayer.setOnTimedTextListener((iMediaPlayer, ijkTimedText) -> {
            Log.d(TAG, "ijkTimedText:" + ijkTimedText);
        });
    }

    private void initPlayer() {
        if (mPlayer != null) {
            return;
        }
        mPlayer = new IjkMediaPlayer();
        setSettings(new Settings.Builder().build());
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }
}
