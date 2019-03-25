package com.stone.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.stone.ImageManager;
import com.stone.R;
import com.stone.Utils;
import com.stone.fragments.HomeFragment;
import com.stone.fragments.SearchFragment;


public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long a = System.currentTimeMillis();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        Log.i("MainActivity", "布局花费" + (System.currentTimeMillis() - a) + "ms");
        recFragment();
        showFragment(R.id.navigation_home);
        ImageManager.loadImage(0);
    }


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
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(searchFragment);
            transaction.commit();
        } else {
            super.onBackPressed();
        }
    }

    public void showBottom() {
        //navigation.setVisibility(View.VISIBLE);
        searchFragment = null;
    }

    //参数是点击的item的id
    private void showFragment(@IdRes int itemId) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.navigation_home:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.fragment_main, homeFragment, "homeFragment");
                } else {
                    transaction.show(homeFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("onPermissionsResult", "grantResults[0] =" + grantResults[0] + " " + "grantResults[1]=" + grantResults[1]);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == 0 && grantResults[1] == 0) {
                    Toast.makeText(this, "你允许了权限", Toast.LENGTH_SHORT).show();
                    Utils.locateCity(this);
                } else {
                    Toast.makeText(this, "你拒绝了权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
