package com.stone.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.stone.Data;
import com.stone.R;
import com.stone.fragments.ImageFragment;
import com.stone.fragments.StoneFragment;
import com.stone.fragments.StoneUnUniformFragment;
import com.stone.fragments.StoneUniformFragment;
import com.stone.model.Stone;
import com.stone.model.StoneUnUniform;
import com.stone.model.StoneUniform;


public class StoneActivity extends AppCompatActivity {
    private StoneFragment stoneFragment;
    private ImageFragment imageFragment;
    private Stone stone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stone);

        int index = getIntent().getIntExtra("index", -1);
        if (index != -1) {
            stone = Data.STONE_LIST.get(index);
            if (stone instanceof StoneUniform) {
                StoneFragment fragment = new StoneUniformFragment(this);
                fragment.setStone(stone);
                stoneFragment = fragment;
            } else if (stone instanceof StoneUnUniform) {
                StoneUnUniformFragment fragment = new StoneUnUniformFragment(this);
                fragment.setStone(stone);
                stoneFragment = fragment;
            }
        }
        if (stoneFragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_root, stoneFragment).commit();
        } else {
            Toast.makeText(this, "传递数据错误，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showImage(Bitmap bitmap) {
        if (imageFragment == null) {
            imageFragment = new ImageFragment(this, bitmap);
        }
//        int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        //Window window = getWindow();
        //window.getDecorView().setSystemUiVisibility(uiFlags);
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager().beginTransaction()
                //.addSharedElement(stoneFragment.getPhotoView(), "image")
                .replace(R.id.fragment_root, imageFragment)
                .addToBackStack(null)
                .commit();
    }

    public void dismissImage() {
        //getWindow().setFlags(0, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        if (imageFragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .addSharedElement(stoneFragment.getPhotoView(), "image")
//                    .replace(R.id.fragment_root, stoneFragment).commit();
//        }
    }
}