package com.stone.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.R;
import com.stone.activities.StoneActivity;
import com.stone.model.StoneNotUniformity;

@SuppressLint("ValidFragment")
public class StoneUnFragment extends StoneFragment {

    private StoneNotUniformity stone;

    public StoneUnFragment() {

    }

    public StoneUnFragment(StoneActivity stoneActivity) {
        super(stoneActivity);
    }

    public void setStone(StoneNotUniformity stone) {
        this.stone = stone;
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
        TextView tv_not_DAR = parent.findViewById(R.id.tv_not_DAR);

        tv_not_dRColor.setText(stone.doubleReflectColor);
        tv_not_Ar.setText(stone.Ar);
        tv_not_DAr.setText(stone.DAr);
        tv_not_Rs.setText(stone.Rs);
        tv_not_Ps.setText(stone.Ps);
        tv_not_DAR.setText(stone.DAR);
        return parent;
    }
}
