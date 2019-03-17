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
import com.stone.model.Stone;
import com.stone.model.StoneNotUniformity;
import com.stone.model.StoneUniformity;

import static android.view.View.GONE;


public class DetailsActivity extends AppCompatActivity {

    private TextView tv_chaName;
    private TextView tv_engName;
    private TextView tv_formula;
    private TextView tv_crystalSystem;
    private TextView tv_uniformity;
    private TextView tv_reflectivity;
    private TextView tv_hardness;
    private TextView tv_reflectColor;
    private TextView tv_DRr;
    private TextView tv_internalReflection;
    private TextView tv_mic;
    private TextView tv_features;

    private final String tag = "DetailsActivity";

    final String chaName = "中文名称:  ";
    final String engName = "英文名称:  ";
    final String formula = "化学式:  ";
    final String crystalSystem = "矿物晶系:  ";
    final String uniformity = "均非性:  ";
    final String Rr = "反射视旋转角Rr:  ";
    final String DRr = "反射视旋转色散:  ";
    final String internalReflection = "内反射:  ";
    final String Ar = "非均质视旋转角Ar:  ";
    final String DAr = "非均质视旋转色散DAr:  ";
    final String Rs = "旋向Rs:  ";
    final String Ps = "相符Ps:  ";
    final String DAR = "反射视旋转色散DAR:  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.com_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final LinearLayout content = findViewById(R.id.com_content);
        tv_chaName = content.findViewById(R.id.tv_chaName);
        tv_engName = content.findViewById(R.id.tv_engName);
        tv_formula = content.findViewById(R.id.tv_formula);
        tv_crystalSystem = content.findViewById(R.id.tv_crys_sys);
        tv_uniformity = content.findViewById(R.id.tv_unif_not);
        tv_reflectivity = content.findViewById(R.id.tv_refl_rate);
        tv_hardness = content.findViewById(R.id.tv_hard);
        tv_reflectColor = content.findViewById(R.id.tv_refl_colo);
        tv_DRr = content.findViewById(R.id.tv_DRr);
        tv_internalReflection = content.findViewById(R.id.tv_inner_refl);
        tv_mic = content.findViewById(R.id.tv_mic);
        tv_features = content.findViewById(R.id.tv_features);

        TextView tv_not_unif_doubleRefl = findViewById(R.id.tv_not_unif_doubleRefl);
        TextView tv_not_unif_Ar = findViewById(R.id.tv_not_unif_Ar);
        TextView tv_Rr = findViewById(R.id.tv_unif_Rr);
        TextView tv_not_unif_DAr = findViewById(R.id.tv_not_unif_DAr);
        TextView tv_not_unif_Rs = findViewById(R.id.tv_not_unif_Rs);
        TextView tv_not_unif_Ps = findViewById(R.id.tv_not_unif_Ps);
        TextView tv_not_unif_DAR = findViewById(R.id.tv_not_unif_DAR);

        int index = getIntent().getIntExtra("index", -1);
        if (index == -1) {
            Toast.makeText(DetailsActivity.this, "传递数据错误，请重试", Toast.LENGTH_SHORT).show();
            return;
        }
        Stone target = Data.STONE_LIST.get(index);
        StoneNotUniformity stoneNotUniformity = null;
        StoneUniformity stoneUniformity = null;
        if (target instanceof StoneUniformity) {
            stoneUniformity = (StoneUniformity) target;
        } else {
            stoneNotUniformity = (StoneNotUniformity) target;
        }
        actionBar.setTitle(target.chaName);
        tv_chaName.setText(chaName + target.chaName);
        tv_engName.setText(engName + target.engName);
        tv_formula.setText(formula + target.formula);
        tv_crystalSystem.setText(crystalSystem + target.crystalSystem);
        tv_uniformity.setText(uniformity + target.uniformity);
        tv_reflectivity.setText(target.reflectivity);
        tv_hardness.setText(target.hardness);
        tv_reflectColor.setText(target.reflectColor);
        tv_DRr.setText(DRr + target.DRr);
        tv_internalReflection.setText(internalReflection + target.internalReflection);
        tv_features.setText(target.features);
        tv_mic.setText(target.mic);
        if (stoneUniformity != null) {
            tv_Rr.setText(Rr + stoneUniformity.Rr);
            TextView tv_title_doubleRefl = findViewById(R.id.title_doubleRefl);
            tv_title_doubleRefl.setVisibility(GONE);
            tv_not_unif_doubleRefl.setVisibility(GONE);
            tv_not_unif_Ar.setVisibility(GONE);
            tv_not_unif_DAr.setVisibility(GONE);
            tv_not_unif_Rs.setVisibility(GONE);
            tv_not_unif_Ps.setVisibility(GONE);
            tv_not_unif_DAR.setVisibility(GONE);
        } else {
            tv_Rr.setVisibility(GONE);
            tv_not_unif_doubleRefl.setText(stoneNotUniformity.doubleReflectColor);
            tv_not_unif_Ar.setText(Ar + stoneNotUniformity.Ar);
            tv_not_unif_DAr.setText(DAr + stoneNotUniformity.DAr);
            tv_not_unif_Rs.setText(Rs + stoneNotUniformity.Rs);
            tv_not_unif_Ps.setText(Ps + stoneNotUniformity.Ps);
            tv_not_unif_DAR.setText(DAR + stoneNotUniformity.DAR);
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

    public void addUniformityTv() {

    }

    public void addNotUniformityTv() {


    }
}