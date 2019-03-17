package com.stone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jerry on 2017/9/29
 */

public class MyGridAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private int textViewId;
    private int imageViewId;
    private String[] titleArray;
    private int[] imageIdArray;

    static class MyViewHolder {
        ImageView imageView;
        TextView textView;
    }

    public MyGridAdapter(Context context, int layoutId, int textViewId, int imageViewId, String[] titleArray, int[] imageIdArray) {
        this.context = context;
        this.layoutId = layoutId;
        this.textViewId = textViewId;
        this.imageViewId = imageViewId;
        this.titleArray = titleArray;
        this.imageIdArray = imageIdArray;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int i) {
        return titleArray[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            viewHolder = new MyViewHolder();
            viewHolder.textView = convertView.findViewById(textViewId);
            viewHolder.imageView = convertView.findViewById(imageViewId);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(titleArray[position]);
        viewHolder.imageView.setImageResource(imageIdArray[0]);
        return convertView;
    }
}
