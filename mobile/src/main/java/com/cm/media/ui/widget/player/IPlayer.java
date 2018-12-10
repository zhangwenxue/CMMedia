package com.cm.media.ui.widget.player;

import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;

public interface IPlayer {

    class Settings {
        private boolean useMediaCodec;
        private boolean useMediaCodecAutoRotate;
        private boolean mediaCodecHandleResolutionChange;
        private boolean useOpenSLES;
        private String pixelFormat;

        public static class Builder {
            private boolean useMediaCodec;
            private boolean useMediaCodecAutoRotate;
            private boolean mediaCodecHandleResolutionChange;
            private boolean useOpenSLES;
            private String pixelFormat;

            public Builder useMediaCodec(boolean useMediaCodec) {
                this.useMediaCodec = useMediaCodec;
                return this;
            }

            public Builder useMediaCodecAutoRotate(boolean useMediaCodecAutoRotate) {
                this.useMediaCodecAutoRotate = useMediaCodecAutoRotate;
                return this;
            }

            public Builder mediaCodecHandleResolutionChange(boolean mediaCodecHandleResolutionChange) {
                this.mediaCodecHandleResolutionChange = mediaCodecHandleResolutionChange;
                return this;
            }

            public Builder useOpenSLES(boolean useOpenSLES) {
                this.useOpenSLES = useOpenSLES;
                return this;
            }

            public Builder pixelFormat(String pixelFormat) {
                this.pixelFormat = pixelFormat;
                return this;
            }

            public Settings build() {
                Settings settings = new Settings();
                settings.setUseMediaCodec(this.useMediaCodec);
                settings.setUseMediaCodecAutoRotate(this.useMediaCodecAutoRotate);
                settings.setMediaCodecHandleResolutionChange(this.mediaCodecHandleResolutionChange);
                settings.setPixelFormat(this.pixelFormat);
                settings.setUseOpenSLES(this.useOpenSLES);
                return settings;
            }
        }

        public boolean isUseMediaCodec() {
            return useMediaCodec;
        }

        void setUseMediaCodec(boolean useMediaCodec) {
            this.useMediaCodec = useMediaCodec;
        }

        public boolean isUseMediaCodecAutoRotate() {
            return useMediaCodecAutoRotate;
        }

        void setUseMediaCodecAutoRotate(boolean useMediaCodecAutoRotate) {
            this.useMediaCodecAutoRotate = useMediaCodecAutoRotate;
        }

        public boolean isMediaCodecHandleResolutionChange() {
            return mediaCodecHandleResolutionChange;
        }

        void setMediaCodecHandleResolutionChange(boolean mediaCodecHandleResolutionChange) {
            this.mediaCodecHandleResolutionChange = mediaCodecHandleResolutionChange;
        }

        public boolean isUseOpenSLES() {
            return useOpenSLES;
        }

        void setUseOpenSLES(boolean useOpenSLES) {
            this.useOpenSLES = useOpenSLES;
        }

        public String getPixelFormat() {
            return pixelFormat;
        }

        void setPixelFormat(String pixelFormat) {
            this.pixelFormat = pixelFormat;
        }
    }

    enum AspectRatio {
        FILL_PARENT,
        FIT_PARENT,
        WRAP_CONTENT,
        MATCH_PARENT,
        RATIO_16_9,
        RATIO_4_3,
    }

    enum PlayState {
        IDEAL,
        PREPARING,
        BUFFERING,
        PLAYING,
        PAUSED,
        FINISHED,
        ERROR
    }

    enum PlayError {
        INVALID_URI,
        PARSE_FAILED,
        UNSUPPORTED_FORMAT,
        INTERNET_FAILURE,
        TIME_OUT,
        EXCEPTION
    }

    interface PlayStateCallback {
        void onPrepare();

        void onPlay();

        void onPause();

        void onError(PlayError error);

        void onBufferingUpdateChange(int percent);

        void onProgessStateChange(int firstProgress, int secondProgress, int total);

        void onSeekComplete();


        void onNext(String uri);

        void onComplete();

    }


    void setUris(Uri... uris);

    void setSettings(Settings settings);

    void prepare();

    void prepareAsync();

    void setDisplay(SurfaceHolder holder);

    void setSurface(Surface surface);


    void play();

    void pause();

    void stop();

    void seekTo(long durationTimeSeconds);

    void setAutoPlay(boolean autoPlay);

    void setScreenOnWhilePlaying(boolean screenOnWhilePlaying);

    void setAspectRatio(AspectRatio aspectRatio);

    long getDuration();

    Uri getPlayingUri();

    void release();

    boolean isSettingsEnable();

    void setPlayStateCallback(PlayStateCallback callback);
}
