package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.stone.R;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.rtmp.TXLiveConstants;

public class VideoActivity extends AppCompatActivity {
    private SuperPlayerView superPlayerView;
    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_NAME = "video_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window window = getWindow();
        //window.getDecorView().setSystemUiVisibility(uiFlags);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        superPlayerView = findViewById(R.id.player_view);
        superPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_FULLSCREEN);
        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
        // 播放器默认缓存个数
        prefs.maxCacheItem = 10;
        // 设置播放器渲染模式
        prefs.enableHWAcceleration = true;
        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;

        String videoName = getIntent().getStringExtra(VIDEO_NAME);
        String videoUrl = getIntent().getStringExtra(VIDEO_URL);

        if (videoUrl != null) {
            SuperPlayerModel model = new SuperPlayerModel();
            model.title = videoName;
            model.videoURL = videoUrl;
            superPlayerView.playWithMode(model);
        }
    }

    @Override
    protected void onResume() {
        if (superPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
            superPlayerView.onResume();
            if (superPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
                superPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (superPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            superPlayerView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        superPlayerView.release();
        if (superPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            superPlayerView.resetPlayer();
        }
        super.onDestroy();
    }
}
