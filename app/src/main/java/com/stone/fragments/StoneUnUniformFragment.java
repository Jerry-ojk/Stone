package com.stone.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.R;
import com.stone.activities.StoneActivity;
import com.stone.model.Stone;
import com.stone.model.StoneUnUniform;

@SuppressLint("ValidFragment")
public class StoneUnUniformFragment extends StoneFragment {

    private StoneUnUniform stone;

    public StoneUnUniformFragment() {

    }

    public StoneUnUniformFragment(StoneActivity stoneActivity) {
        super(stoneActivity);
    }

    @Override
    public void setStone(Stone stone) {
        super.setStone(stone);
        this.stone = (StoneUnUniform) stone;
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_stone_un_uniform;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState);
        if (stone == null) {
            Toast.makeText(getContext(), "传递数据错误，请重试", Toast.LENGTH_SHORT).show();
            return parent;
        }

        TextView tv_not_dRColor = parent.findViewById(R.id.tv_not_dRColor);
        TextView tv_not_Ar = parent.findViewById(R.id.tv_not_Ar);
        TextView tv_not_DAr = parent.findViewById(R.id.tv_not_DAr);
        TextView tv_not_Rs = parent.findViewById(R.id.tv_not_Rs);
        TextView tv_not_Ps = parent.findViewById(R.id.tv_not_Ps);
        TextView tv_not_DAR = parent.findViewById(R.id.tv_DAR);

        tv_not_dRColor.setText(stone.doubleReflectColor);
        tv_not_Ar.setText(stone.Ar);
        tv_not_DAr.setText(stone.DAr);
        tv_not_Rs.setText(stone.Rs);
        tv_not_Ps.setText(stone.Ps);
        tv_not_DAR.setText(stone.DRr);
        return parent;
    }
}
