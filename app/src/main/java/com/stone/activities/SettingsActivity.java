package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.R;
import com.stone.image.ImageManager;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Toast.makeText(this, "该功能暂未完善", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_settings);
        Window window = this.getWindow();
        window.setStatusBarColor(0xffffffff);
        window.getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        findViewById(R.id.iv_back).setOnClickListener((v) -> finish());
        TextView textView = findViewById(R.id.tv_image);
        textView.setText("缓存目录：" + ImageManager.IMAGE_ROOT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
