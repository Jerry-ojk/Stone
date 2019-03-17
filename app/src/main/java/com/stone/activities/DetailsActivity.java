package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.Data;
import com.stone.R;


public class DetailsActivity extends AppCompatActivity {
    private LinearLayout content;
    private TextView tv_chaName;
    private TextView tv_engName;
    private TextView tv_formula;
    private TextView tv_features;
    private TextView tv_mic;

    private final String tag = "DetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.com_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        content = findViewById(R.id.com_content);
        tv_chaName = content.findViewById(R.id.tv_chaName);
        tv_engName = content.findViewById(R.id.tv_engName);
        tv_formula = content.findViewById(R.id.tv_formula);
//        born_time = content.findViewById(R.id.tv_com_born_time);
//        status_name = content.findViewById(R.id.tv_com_status_name);
//        scale_name = content.findViewById(R.id.tv_com_scale_name);
//        fund_needs_name = content.findViewById(R.id.tv_com_fund_needs_name);
//        com_url = content.findViewById(R.id.tv_com_url);
//        weibo_url = content.findViewById(R.id.tv_com_weibo_url);
//        tel = content.findViewById(R.id.tv_com_tel);
//        email = content.findViewById(R.id.tv_com_email);
        tv_features = content.findViewById(R.id.tv_features);
        tv_mic = content.findViewById(R.id.tv_mic);

        int index = getIntent().getIntExtra("index", -1);
        if (index == -1) {
            Toast.makeText(DetailsActivity.this, "传递数据错误，请重试", Toast.LENGTH_SHORT).show();
        } else {
            tv_chaName.setText(Data.STONE_LIST.get(index).chaName);
            tv_engName.setText(Data.STONE_LIST.get(index).engName);
            tv_formula.setText(Data.STONE_LIST.get(index).formula);
            tv_features.setText(Data.STONE_LIST.get(index).features);
            tv_mic.setText(Data.STONE_LIST.get(index).mic);
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
}
