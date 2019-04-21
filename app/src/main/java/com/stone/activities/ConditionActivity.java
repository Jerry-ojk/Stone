package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.stone.R;
import com.stone.adapters.ConditionAdapter;

public class ConditionActivity extends AppCompatActivity {
    private Spinner sp_uniform;
    private Spinner sp_reflect;
    private Spinner sp_ps;
    private Spinner sp_rs;
    private Spinner sp_rrl;
    private Spinner sp_rc;
    private Spinner sp_DAr;
    private Spinner sp_drc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        findViewById(R.id.iv_back).setOnClickListener((view) -> finish());
        sp_uniform = findViewById(R.id.sp_uniform);
        sp_uniform.setAdapter(new ConditionAdapter(new String[]{"非均性：", "强", "弱"}));
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

        sp_rs = findViewById(R.id.sp_rs);
        sp_rs.setAdapter(new ConditionAdapter(new String[]{"旋向Rs：", "+", "-"}));

        sp_rrl = findViewById(R.id.sp_rrl);
        sp_rrl.setAdapter(new ConditionAdapter(new String[]{"反射率视测分级：", "1", "2", "3", "4", "5"}));

        sp_rc = findViewById(R.id.sp_rc);
        sp_rc.setAdapter(new ConditionAdapter(new String[]{"反射色：", "灰色", "棕色", "粉色", "紫色", "无色", "白色"}));

        sp_DAr = findViewById(R.id.sp_DAr);
        sp_DAr.setAdapter(new ConditionAdapter(new String[]{"非均质视旋转色散：", "r>v", "r<v"}));

        sp_drc = findViewById(R.id.sp_drc);
        sp_drc.setAdapter(new ConditionAdapter(new String[]{"双反射及反射多色性：", "无", "特强", "显著", "清楚", "微弱"}));


        Toast.makeText(this, "该功能暂未完善", Toast.LENGTH_SHORT).show();

        findViewById(R.id.bu_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_uniform.setSelection(0);
                sp_reflect.setSelection(0);
                sp_ps.setSelection(0);
                sp_rs.setSelection(0);
                sp_rrl.setSelection(0);
                sp_rc.setSelection(0);
                sp_DAr.setSelection(0);
                sp_drc.setSelection(0);
            }
        });
    }
}
