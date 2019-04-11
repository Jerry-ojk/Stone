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
import com.stone.model.StoneUniform;

@SuppressLint("ValidFragment")
public class StoneUniformFragment extends StoneFragment {

    private StoneUniform stone;

    public StoneUniformFragment() {
        super();
    }

    public StoneUniformFragment(StoneActivity stoneActivity) {
        super(stoneActivity);
    }

    @Override
    public void setStone(Stone stone) {
        super.setStone(stone);
        this.stone = (StoneUniform) (stone);
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_stone_uniform;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = super.onCreateView(inflater, container, savedInstanceState);

        if (stone == null) {
            Toast.makeText(getContext(), "传递数据错误，请重试", Toast.LENGTH_SHORT).show();
            return parent;
        }

        Log.i("666", "进入详情界面，加载" + stone.chaName);
        TextView tv_uni_Rr = parent.findViewById(R.id.tv_uni_Rr);
        tv_uni_Rr.setText(stone.Rr);
        return parent;
    }
}
