package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.stone.R;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoActivity extends AppCompatActivity {
    private JzvdStd videoView;
    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_NAME = "video_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.video_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        setSupportActionBar(toolbar);
        Jzvd.SAVE_PROGRESS = false;
        Jzvd.TOOL_BAR_EXIST = true;
        Jzvd.ACTION_BAR_EXIST = false;

        String videoName = getIntent().getStringExtra(VIDEO_NAME);
        String videoUrl = getIntent().getStringExtra(VIDEO_URL);
        Jzvd.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT);

        if (videoUrl != null) {
            getSupportActionBar().setTitle(videoName);
            videoView.setUp(videoUrl, videoName, Jzvd.SCREEN_WINDOW_NORMAL);
            videoView.startButton.performClick();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Jzvd.releaseAllVideos();
        super.onDestroy();
    }
}
