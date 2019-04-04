package com.stone.player;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.stone.R;
import com.stone.activities.StoneActivity;
import com.stone.views.DPUtils;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.IjkTimedText;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;

public class SuperPlayerView extends ViewGroup implements PlayerController {
    private StoneActivity context;
    private SurfaceView surfaceView;
    private HeaderView headerView;
    private FooterView footerView;

    private IjkMediaPlayer mediaPlayer;

    private String path;
    private boolean isStartPlay = false;
    private boolean isSurfaceCreated = false;

    private int videoWidth;
    private int videoHeight;
    private int surfaceWidth;
    private int surfaceHeight;
    private boolean isLandscape = false;
    private Runnable runnable;
    private Runnable runnableProcess;
    private int time = 0;

    public SuperPlayerView(Context context) {
        this(context, null, 0);
    }

    public SuperPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = (StoneActivity) context;
        initView();
    }

    private void initView() {

        //IjkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        setBackgroundColor(Color.BLACK);

        videoWidth = 0;
        videoHeight = 0;

        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        headerView = new HeaderView(context, this);
        headerView.setBackgroundResource(R.drawable.player_top_shadow);
        addView(headerView, new LayoutParams(LayoutParams.MATCH_PARENT, DPUtils.DP40));

        footerView = new FooterView(context, this);
        footerView.setBackgroundResource(R.drawable.player_bottom_shadow);
        addView(footerView, new LayoutParams(LayoutParams.MATCH_PARENT, DPUtils.DP40));

        runnable = () -> {
            if ((--time) <= 0) {
                time = 0;
                headerView.setVisibility(INVISIBLE);
                footerView.setVisibility(INVISIBLE);
            }
        };
        runnableProcess = () -> {
            if (mediaPlayer != null) {
                footerView.setPlayPosition(mediaPlayer.getCurrentPosition());
                postDelayed(runnableProcess, 1000);
            }
        };
    }

    public View getTitleView() {
        return headerView;
    }

    private void createSurface() {
        if (surfaceView == null) {
            surfaceView = new SurfaceView(context);
            addView(surfaceView, 0, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    Log.i("666", "surfaceCreated");
                    isSurfaceCreated = true;
                    if (!isStartPlay && path != null) {
                        startPlay();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    Log.i("666", "surfaceChanged" + width + "*" + height);
                    //requestLayout();
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    Log.i("666", "surfaceDestroyed");
                    if (footerView != null) {
                        footerView.showPause();
                    }
                    if (mediaPlayer != null) {
                        //release();
                        mediaPlayer.stop();
                        mediaPlayer.setDisplay(null);
                    }
                }
            });
        }
    }

    private void createPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.setDisplay(null);
            mediaPlayer.release();
        }
        mediaPlayer = new IjkMediaPlayer();
        //开启硬解码 速度块
        mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        //精确定位
        //mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
        //开启硬解码
//        if (mSettings.getUsingMediaCodec()) {
//            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
//            if (mSettings.getUsingMediaCodecAutoRotate()) {
//                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);
//            } else {
//                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 0);
//            }
//            if (mSettings.getMediaCodecHandleResolutionChange()) {
//                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);
//            } else {
//                ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 0);
//            }
//        } else {
//            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 0);
//        }
//
////        if (mSettings.getUsingOpenSLES()) {
////            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);
////        } else {
////            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 0);
////        }
//
//        String pixelFormat = mSettings.getPixelFormat();
//        if (TextUtils.isEmpty(pixelFormat)) {
//            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
//        } else {
//            ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", pixelFormat);
//        }
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
//
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "http-detect-range-support", 0);
//
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);

        mediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer iMediaPlayer) {
                //iMediaPlayer.start();
                footerView.setDuration(iMediaPlayer.getDuration());
                postDelayed(runnableProcess, 0);
            }
        });
        mediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                //Log.i("666", "onInfo" + i + "," + i1);
                return false;
            }
        });
        mediaPlayer.setOnVideoSizeChangedListener(new IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(IMediaPlayer mediaPlayer, int width, int height, int sar_num, int sar_den) {
                videoWidth = mediaPlayer.getVideoWidth();
                videoHeight = mediaPlayer.getVideoHeight();
//                mVideoSarNum = mediaPlayer.getVideoSarNum();
//                mVideoSarDen = mediaPlayer.getVideoSarDen();
//                if (mVideoWidth != 0 && mVideoHeight != 0) {
//                    if (mRenderView != null) {
//                        mRenderView.setVideoSize(mVideoWidth, mVideoHeight);
//                        mRenderView.setVideoSampleAspectRatio(mVideoSarNum, mVideoSarDen);
//                    }
//                }
//                surfaceView.getHolder().setFixedSize(videoWidth, videoHeight);
//                requestLayout();
                Log.i("666", "width=" + width + " x height=" + height + ",sar_num=" + sar_num + ",sar_den=" + sar_den);
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new IMediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(IMediaPlayer iMediaPlayer) {
                Log.i("666", "onSeekComplete");
            }
        });
        mediaPlayer.setOnBufferingUpdateListener((iMediaPlayer, percent) -> {
            footerView.setBufferPercent(percent);
            Log.i("666", "onBufferingUpdate,percent=" + percent);
        });
        mediaPlayer.setOnTimedTextListener(new IMediaPlayer.OnTimedTextListener() {
            @Override
            public void onTimedText(IMediaPlayer mediaPlayer, IjkTimedText text) {
                Log.i("666", "onTimedText,text=" + text.getText());
            }
        });
        mediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                Log.i("666", "onError" + i + "," + i1);
                footerView.showPause();
                return false;
            }
        });
        mediaPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mediaPlayer, int what, int extra) {
                Log.i("666", "onInfo,what=" + what + "，extra=" + extra);
                return false;
            }
        });
    }

    private void startPlay() {
        //每次都要重新创建IMediaPlayer
        createPlayer();
        try {
            mediaPlayer.setDataSource(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //给mediaPlayer设置视图
        mediaPlayer.setDisplay(surfaceView.getHolder());
        mediaPlayer.prepareAsync();
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        surfaceView.setKeepScreenOn(true);
        time++;
        postDelayed(runnable, 3000);
    }

    public void setVideoPath(String path) {
        this.path = path;
        createSurface();
        if (isSurfaceCreated) {
            startPlay();
        } else {
            isStartPlay = false;
        }
    }

    public void setTitle(String title) {
        headerView.setTitle(title);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (surfaceView != null) {
            if (isLandscape) {
                surfaceView.measure(widthMeasureSpec, heightMeasureSpec);
            } else {
                surfaceView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((width * 9) >> 4, MeasureSpec.EXACTLY));
            }
        }
        headerView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(DPUtils.DP40, MeasureSpec.EXACTLY));
        footerView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(DPUtils.DP40, MeasureSpec.EXACTLY));
        setMeasuredDimension(width, (width * 9) >> 4);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int width = getWidth();
        int height;
        if (isLandscape) {
            height = getHeight();
        } else if (surfaceView != null) {
            height = surfaceView.getMeasuredHeight();
        } else {
            height = getMeasuredHeight();
        }
        if (surfaceView != null) {
            surfaceView.layout(0, 0, width, height);
        }
        headerView.layout(0, 0, width, DPUtils.DP40);
        footerView.layout(0, height - DPUtils.DP40, width, height);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        Log.i("666", "onConfigurationChanged start");
        //获取屏幕方向
        int orientation = newConfig.orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE && !isLandscape) {
            isLandscape = true;
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            final Window window = context.getWindow();
            window.getDecorView().setSystemUiVisibility(uiFlags);
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            requestLayout();
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT && isLandscape) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            final Window window = context.getWindow();
            window.getDecorView().setSystemUiVisibility(uiFlags);
            window.setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            isLandscape = false;
            requestLayout();
        }
        Log.i("666", "onConfigurationChanged end");
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() != MotionEvent.ACTION_MOVE) {
            if (headerView.getVisibility() != VISIBLE) {
                headerView.setVisibility(VISIBLE);
                footerView.setVisibility(VISIBLE);
            }
            time++;
            postDelayed(runnable, 3000);
        }
        return false;
    }

    @Override
    public boolean isLandscape() {
        return isLandscape;
    }

    @Override
    public boolean onPlay(boolean isPlay) {
        if (isPlay && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            return true;
        } else if (!isPlay && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(long process) {
        mediaPlayer.seekTo(process);
        return true;
    }

    @Override
    public boolean onZoom() {
        if (isLandscape) {
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            //context.getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
            context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            Log.i("666", "onZoom end");
        }
        return true;
    }

    @Override
    public void onBack() {
        context.onBackPressed();
    }

    public long getPlayPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    public void setPlayPosition(long position) {
        footerView.setPlayPosition(position);
    }

    public float getBufferLengthPixel() {
        return footerView.getBufferLengthPixel();
    }

    public void setBufferLengthPixel(float bufferLength) {
        footerView.setBufferLengthPixel(bufferLength);
    }

    public long getDuration() {
        return footerView.getDuration();
    }

    public void setDuration(long duration) {
        footerView.setDuration(duration);
    }


    public void showMediaInfo() {
        if (mediaPlayer == null)
            return;

        int selectedVideoTrack = mediaPlayer.getSelectedTrack(ITrackInfo.MEDIA_TRACK_TYPE_VIDEO);
        int selectedAudioTrack = mediaPlayer.getSelectedTrack(ITrackInfo.MEDIA_TRACK_TYPE_AUDIO);
        int selectedSubtitleTrack = mediaPlayer.getSelectedTrack(ITrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT);

//        TableLayoutBinder builder = new TableLayoutBinder(getContext());
//        builder.appendSection(R.string.mi_player);
//        builder.appendRow2(R.string.mi_player, MediaPlayerCompat.getName(mMediaPlayer));
//        builder.appendSection(R.string.mi_media);
//        builder.appendRow2(R.string.mi_resolution, buildResolution(mVideoWidth, mVideoHeight, mVideoSarNum, mVideoSarDen));
//        builder.appendRow2(R.string.mi_length, buildTimeMilli(mMediaPlayer.getDuration()));
//
//        ITrackInfo trackInfos[] = mMediaPlayer.getTrackInfo();
//        if (trackInfos != null) {
//            int index = -1;
//            for (ITrackInfo trackInfo : trackInfos) {
//                index++;
//
//                int trackType = trackInfo.getTrackType();
//                if (index == selectedVideoTrack) {
//                    builder.appendSection(getContext().getString(R.string.mi_stream_fmt1, index) + " " + getContext().getString(R.string.mi__selected_video_track));
//                } else if (index == selectedAudioTrack) {
//                    builder.appendSection(getContext().getString(R.string.mi_stream_fmt1, index) + " " + getContext().getString(R.string.mi__selected_audio_track));
//                } else if (index == selectedSubtitleTrack) {
//                    builder.appendSection(getContext().getString(R.string.mi_stream_fmt1, index) + " " + getContext().getString(R.string.mi__selected_subtitle_track));
//                } else {
//                    builder.appendSection(getContext().getString(R.string.mi_stream_fmt1, index));
//                }
//                builder.appendRow2(R.string.mi_type, buildTrackType(trackType));
//                builder.appendRow2(R.string.mi_language, buildLanguage(trackInfo.getLanguage()));
//
//                IMediaFormat mediaFormat = trackInfo.getFormat();
//                if (mediaFormat == null) {
//                } else if (mediaFormat instanceof IjkMediaFormat) {
//                    switch (trackType) {
//                        case ITrackInfo.MEDIA_TRACK_TYPE_VIDEO:
//                            builder.appendRow2(R.string.mi_codec, mediaFormat.getString(IjkMediaFormat.KEY_IJK_CODEC_LONG_NAME_UI));
//                            builder.appendRow2(R.string.mi_profile_level, mediaFormat.getString(IjkMediaFormat.KEY_IJK_CODEC_PROFILE_LEVEL_UI));
//                            builder.appendRow2(R.string.mi_pixel_format, mediaFormat.getString(IjkMediaFormat.KEY_IJK_CODEC_PIXEL_FORMAT_UI));
//                            builder.appendRow2(R.string.mi_resolution, mediaFormat.getString(IjkMediaFormat.KEY_IJK_RESOLUTION_UI));
//                            builder.appendRow2(R.string.mi_frame_rate, mediaFormat.getString(IjkMediaFormat.KEY_IJK_FRAME_RATE_UI));
//                            builder.appendRow2(R.string.mi_bit_rate, mediaFormat.getString(IjkMediaFormat.KEY_IJK_BIT_RATE_UI));
//                            break;
//                        case ITrackInfo.MEDIA_TRACK_TYPE_AUDIO:
//                            builder.appendRow2(R.string.mi_codec, mediaFormat.getString(IjkMediaFormat.KEY_IJK_CODEC_LONG_NAME_UI));
//                            builder.appendRow2(R.string.mi_profile_level, mediaFormat.getString(IjkMediaFormat.KEY_IJK_CODEC_PROFILE_LEVEL_UI));
//                            builder.appendRow2(R.string.mi_sample_rate, mediaFormat.getString(IjkMediaFormat.KEY_IJK_SAMPLE_RATE_UI));
//                            builder.appendRow2(R.string.mi_channels, mediaFormat.getString(IjkMediaFormat.KEY_IJK_CHANNEL_UI));
//                            builder.appendRow2(R.string.mi_bit_rate, mediaFormat.getString(IjkMediaFormat.KEY_IJK_BIT_RATE_UI));
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            }
//        }
//
//        AlertDialog.Builder adBuilder = builder.buildAlertDialogBuilder();
//        adBuilder.setTitle(R.string.media_information);
//        adBuilder.setNegativeButton(R.string.close, null);
//        adBuilder.show();
    }

    public void pause() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            footerView.showPause();
        }
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            am.abandonAudioFocus(null);
        }
        removeCallbacks(runnable);
        removeCallbacks(runnableProcess);
    }
}