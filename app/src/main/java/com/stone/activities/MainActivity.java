package com.stone.activities;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.stone.R;
import com.stone.fragments.HomeFragment;
import com.stone.fragments.SearchFragment;
import com.stone.image.ImageManager;


public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    public static boolean access = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        recFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.fragment_main, homeFragment, "homeFragment").commit();
        } else {
            transaction.show(homeFragment).commit();
        }
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, android.os.Process.myPid(), android.os.Process.myUid()) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            access = true;
            ImageManager.fillStoneImages(new ImageManager.ImageLoadListener() {
                @Override
                public void onFinish() {
                    homeFragment.onImageLoadFinish();
                }
            });
        }
    }

    //恢复Fragments
    private void recFragment() {
        FragmentManager manager = getFragmentManager();
        homeFragment = (HomeFragment) manager.findFragmentByTag("homeFragment");
    }


    public void showSearchFragment(SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

    @Override
    public void onBackPressed() {
        if (searchFragment != null) {
            searchFragment.exitWithAnimation();
        } else {
            super.onBackPressed();
        }
    }

    //在SearchFragment退出时调用
    public void onSearchFragmentDestroy() {
        //navigation.setVisibility(View.VISIBLE);
        searchFragment = null;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                showToast("授权失败！");
            } else {
                access = true;
                ImageManager.fillStoneImages(new ImageManager.ImageLoadListener() {
                    @Override
                    public void onFinish() {
                        homeFragment.onImageLoadFinish();
                    }
                });
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLowMemory() {
        //低内存时准备回收bitmap空间
        ImageManager.onLowMemory();
        super.onLowMemory();
    }
}
