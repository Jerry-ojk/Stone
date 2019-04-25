package com.stone.adapters;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Jerry on 2017/9/29
 */

public class ConditionAdapter extends BaseAdapter {

    private String[] items;

    public ConditionAdapter(String[] items) {
        this.items = items;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view;
        if (convertView == null) {
            view = new TextView(parent.getContext());
//            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
//                    , ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.setMargins(12, 4, 12, 4);
            view.setPadding(12, 4, 12, 4);
            //view.setLayoutParams(params);
            view.setTextSize(16);
            view.setGravity(Gravity.CENTER);
        } else {
            view = (TextView) convertView;
        }
        view.setText(items[position]);
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getDropDownView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) return null;
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
