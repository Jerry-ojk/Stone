package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.stone.R;
import com.stone.adapters.ConditionAdapter;

public class ConditionActivity extends AppCompatActivity {
    private Spinner sp_uniform;
    private Spinner sp_reflect;
    private Spinner sp_ps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        findViewById(R.id.iv_back).setOnClickListener((view) -> finish());
        sp_uniform = findViewById(R.id.sp_uniform);
        sp_uniform.setAdapter(new ConditionAdapter(new String[]{"均非性：", "强", "弱"}));
        sp_uniform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("666", "onItemSelected" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("666", "onNothingSelected");
            }
        });
        sp_reflect = findViewById(R.id.sp_reflect);
        sp_reflect.setAdapter(new ConditionAdapter(new String[]{"内反射：", "无", "红", "黄", "蓝", "绿", "白"}));
        sp_ps = findViewById(R.id.sp_ps);
        sp_ps.setAdapter(new ConditionAdapter(new String[]{"相符Ps：", "+", "-"}));
    }
}
