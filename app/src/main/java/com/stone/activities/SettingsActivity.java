package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.R;
import com.stone.image.ImageManager;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this, "该功能暂未完善", Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_settings);
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
