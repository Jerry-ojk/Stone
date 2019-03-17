package com.stone.fragments;

import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import com.stone.R;
import com.stone.Utils;


/**
 * Created by Jerry on 2017/9/21
 */

public class SettingsFragment extends PreferenceFragment {
    Preference about;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long a = System.currentTimeMillis();
        addPreferencesFromResource(R.xml.pref_mian);
        Log.i("SettingsFragment", "布局花费" + (System.currentTimeMillis() - a) + "ms");
        about = findPreference("version");
        about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                PackageManager packageManager = getActivity().getPackageManager();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String versionName = null;
                try {
                    PackageInfo info = packageManager.getPackageInfo(getActivity().getPackageName(), PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
                    versionName = info.versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (!Utils.isEmpty(versionName)) {
                    builder.setTitle(preference.getTitle())
                            .setMessage("当前版本：" + versionName + "\n" + "更新日期：" + "2017年12月10日")
                            .setPositiveButton("确定", null)
                            .setNeutralButton("检查更新", null)
                            .show();
                }
                return true;
            }
        });
    }

}
