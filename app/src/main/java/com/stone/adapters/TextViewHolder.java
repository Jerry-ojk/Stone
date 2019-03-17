package com.stone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Jerry on 2017/11/1
 */

public class TextViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public TextViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView;
    }
}
