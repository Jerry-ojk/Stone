package com.stone.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Spinner;

import com.stone.R;
import com.stone.adapters.ConditionAdapter;
import com.stone.model.Stone;
import com.stone.model.StoneUnUniform;

import java.util.ArrayList;

public class ConditionActivity extends AppCompatActivity {
    private Spinner sp_uniform;
    private Spinner sp_interReflect;
    private Spinner sp_ps;
    private Spinner sp_rs;
    private Spinner sp_rrl;
    private Spinner sp_rc;
    private Spinner sp_DAr;
    private Spinner sp_drc;

    private Condition condition_uniform;
    private Condition condition_interReflect;
    private Condition condition_ps;
    private Condition condition_rs;
    private Condition condition_rrl;
    private Condition condition_rc;
    private Condition condition_DAr;
    private Condition condition_drc;

    public static ArrayList<Condition> CONDITION_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        findViewById(R.id.iv_back).setOnClickListener((view) -> finish());

        sp_uniform = findViewById(R.id.sp_uniform);
        sp_uniform.setAdapter(new ConditionAdapter(new String[]{"非均性：", "强", "弱"}));

        sp_interReflect = findViewById(R.id.sp_reflect);
        sp_interReflect.setAdapter(new ConditionAdapter(new String[]{"内反射：", "无", "红", "黄", "蓝", "绿", "白"}));

        sp_ps = findViewById(R.id.sp_ps);
        sp_ps.setAdapter(new ConditionAdapter(new String[]{"相符Ps：", "+", "-"}));

        sp_rs = findViewById(R.id.sp_rs);
        sp_rs.setAdapter(new ConditionAdapter(new String[]{"旋向Rs：", "+", "-"}));

        sp_rrl = findViewById(R.id.sp_rrl);
        sp_rrl.setAdapter(new ConditionAdapter(new String[]{"反射率视测分级：", "Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ"}));

        sp_rc = findViewById(R.id.sp_rc);
        sp_rc.setAdapter(new ConditionAdapter(new String[]{"反射色：", "灰色", "棕色", "粉色", "紫色", "无色", "白色"}));

        sp_DAr = findViewById(R.id.sp_DAr);
        sp_DAr.setAdapter(new ConditionAdapter(new String[]{"非均质视旋转色散：", "r>v", "r<v", "r≈v"}));

        sp_drc = findViewById(R.id.sp_drc);
        sp_drc.setAdapter(new ConditionAdapter(new String[]{"双反射及反射多色性：", "无", "特强", "显著", "清楚", "微弱"}));

        if (CONDITION_LIST == null) {
            condition_uniform = new Condition(sp_uniform, (stone, condition) -> condition == null || stone.uniformity.contains(condition));
            condition_interReflect = new Condition(sp_interReflect, (stone, condition) -> condition == null || stone.internalReflection.contains(condition));
            condition_ps = new Condition(sp_ps, (stone, condition) -> condition == null || stone instanceof StoneUnUniform && ((StoneUnUniform) (stone)).Ps.contains(condition));
            condition_rs = new Condition(sp_rs, (Stone stone, String condition) -> {
                if (condition == null) {
                    return true;
                }
                if (stone instanceof StoneUnUniform) {
                    StoneUnUniform stoneUnUniform = ((StoneUnUniform) (stone));
                    if ("+".equals(condition)) {
                        return stoneUnUniform.Rs.contains("Rs延长(+)");
                    }
                    if ("-".equals(condition)) {
                        return stoneUnUniform.Rs.contains("Rs延长(-)");
                    }
                }
                return false;
            });
            condition_rrl = new Condition(sp_rrl, new Validator() {
                @Override
                public boolean validate(Stone stone, String condition) {
                    return condition == null || (stone).reflectivity.contains(condition);
                }
            });
            condition_rc = new Condition(sp_rc, (stone, condition) -> condition == null || stone instanceof StoneUnUniform && ((StoneUnUniform) (stone)).reflectColor.contains(condition));
            condition_DAr = new Condition(sp_DAr, (stone, condition) -> condition == null || stone instanceof StoneUnUniform && ((StoneUnUniform) (stone)).DAr.contains(condition));
            condition_drc = new Condition(sp_drc, (stone, condition) -> condition == null || stone instanceof StoneUnUniform && ((StoneUnUniform) (stone)).doubleReflectColor.contains(condition));

            CONDITION_LIST = new ArrayList<>();
            CONDITION_LIST.add(condition_uniform);
            CONDITION_LIST.add(condition_interReflect);
            CONDITION_LIST.add(condition_ps);
            CONDITION_LIST.add(condition_rs);
            CONDITION_LIST.add(condition_rrl);
            CONDITION_LIST.add(condition_rc);
            CONDITION_LIST.add(condition_DAr);
            CONDITION_LIST.add(condition_drc);
        } else {
            CONDITION_LIST.get(0).setSpinner(sp_uniform);
            CONDITION_LIST.get(1).setSpinner(sp_interReflect);
            CONDITION_LIST.get(2).setSpinner(sp_ps);
            CONDITION_LIST.get(3).setSpinner(sp_rs);
            CONDITION_LIST.get(4).setSpinner(sp_rrl);
            CONDITION_LIST.get(5).setSpinner(sp_rc);
            CONDITION_LIST.get(6).setSpinner(sp_DAr);
            CONDITION_LIST.get(7).setSpinner(sp_drc);
        }

        findViewById(R.id.bu_clear).setOnClickListener(v -> {
            sp_uniform.setSelection(0);
            sp_interReflect.setSelection(0);
            sp_ps.setSelection(0);
            sp_rs.setSelection(0);
            sp_rrl.setSelection(0);
            sp_rc.setSelection(0);
            sp_DAr.setSelection(0);
            sp_drc.setSelection(0);
            condition_uniform.setCondition(0, null);
            condition_interReflect.setCondition(0, null);
            condition_ps.setCondition(0, null);
            condition_rs.setCondition(0, null);
            condition_rrl.setCondition(0, null);
            condition_rc.setCondition(0, null);
            condition_DAr.setCondition(0, null);
            condition_drc.setCondition(0, null);
        });
    }
}
