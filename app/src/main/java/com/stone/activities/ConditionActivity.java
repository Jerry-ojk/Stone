package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.stone.Data;
import com.stone.R;
import com.stone.adapters.ConditionAdapter;
import com.stone.model.Stone;
import com.stone.model.StoneUnUniform;

import java.util.ArrayList;

public class ConditionActivity extends AppCompatActivity {
    private Spinner sp_uniform;
    private Spinner sp_reflect;
    private Spinner sp_ps;
    private Spinner sp_rs;
    private Spinner sp_rrl;
    private Spinner sp_rc;
    private Spinner sp_DAr;
    private Spinner sp_drc;

    private Condition condition_uniform;
    private Condition condition_reflect;
    private Condition condition_ps;
    private Condition condition_rs;
    private Condition condition_rrl;
    private Condition condition_rc;
    private Condition condition_DAr;
    private Condition condition_drc;

    private ArrayList<Condition> conditionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        findViewById(R.id.iv_back).setOnClickListener((view) -> finish());

        sp_uniform = findViewById(R.id.sp_uniform);
        sp_uniform.setAdapter(new ConditionAdapter(new String[]{"非均性：", "强", "弱"}));
        condition_uniform = new Condition(sp_uniform, (stone, condition) -> condition == null || stone.uniformity.contains(condition));

        sp_reflect = findViewById(R.id.sp_reflect);
        sp_reflect.setAdapter(new ConditionAdapter(new String[]{"内反射：", "无", "红", "黄", "蓝", "绿", "白"}));
        condition_reflect = new Condition(sp_uniform, (stone, condition) -> condition == null || stone.reflectColor.contains(condition));

        sp_ps = findViewById(R.id.sp_ps);
        sp_ps.setAdapter(new ConditionAdapter(new String[]{"相符Ps：", "+", "-"}));
        condition_ps = new Condition(sp_ps, (stone, condition) -> condition == null || stone instanceof StoneUnUniform && ((StoneUnUniform) (stone)).Ps.contains(condition));

        sp_rs = findViewById(R.id.sp_rs);
        sp_rs.setAdapter(new ConditionAdapter(new String[]{"旋向Rs：", "+", "-"}));
        condition_rs = new Condition(sp_ps, (stone, condition) -> condition == null || stone instanceof StoneUnUniform && ((StoneUnUniform) (stone)).Rs.contains(condition));

        sp_rrl = findViewById(R.id.sp_rrl);
        sp_rrl.setAdapter(new ConditionAdapter(new String[]{"反射率视测分级：", "Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ"}));
        condition_rrl = new Condition(sp_ps, (stone, condition) -> condition == null || (stone).reflectivity.contains(condition));

        sp_rc = findViewById(R.id.sp_rc);
        sp_rc.setAdapter(new ConditionAdapter(new String[]{"反射色：", "灰色", "棕色", "粉色", "紫色", "无色", "白色"}));
        condition_rc = new Condition(sp_ps, (stone, condition) -> condition == null || stone instanceof StoneUnUniform && ((StoneUnUniform) (stone)).Ps.contains(condition));

        sp_DAr = findViewById(R.id.sp_DAr);
        sp_DAr.setAdapter(new ConditionAdapter(new String[]{"非均质视旋转色散：", "r>v", "r<v"}));
        condition_DAr = new Condition(sp_ps, (stone, condition) -> condition == null || stone instanceof StoneUnUniform && ((StoneUnUniform) (stone)).DAr.contains(condition));

        sp_drc = findViewById(R.id.sp_drc);
        sp_drc.setAdapter(new ConditionAdapter(new String[]{"双反射及反射多色性：", "无", "特强", "显著", "清楚", "微弱"}));
        condition_drc = new Condition(sp_ps, (stone, condition) -> condition == null || stone instanceof StoneUnUniform && ((StoneUnUniform) (stone)).doubleReflectColor.contains(condition));


        conditionList = new ArrayList<>();
        conditionList.add(condition_uniform);
        conditionList.add(condition_reflect);
        conditionList.add(condition_ps);
        conditionList.add(condition_rs);
        conditionList.add(condition_rrl);
        conditionList.add(condition_rc);
        conditionList.add(condition_DAr);
        conditionList.add(condition_drc);
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
                condition_uniform.setCondition(null);
                condition_reflect.setCondition(null);
                condition_ps.setCondition(null);
                condition_rs.setCondition(null);
                condition_rrl.setCondition(null);
                condition_rc.setCondition(null);
                condition_DAr.setCondition(null);
                condition_drc.setCondition(null);

            }
        });
        findViewById(R.id.bu_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int size_Stone = Data.STONE_LIST.size();
                final int size = conditionList.size();
                for (int i = 0; i < size_Stone; i++) {
                    Stone stone = Data.STONE_LIST.get(i);
                    boolean a = true;
                    for (int j = 0; j < size; j++) {
                        if (!conditionList.get(j).validate(stone)) {
                            a = false;
                            break;
                        }
                    }
                    if (a) {
                        Log.i("666", stone.chaName);
                    }
                }

            }
        });
    }
}
