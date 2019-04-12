package com.stone.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jerry on 2017/9/29
 */

public class ConditionAdapter extends RecyclerView.Adapter<ConditionAdapter.ViewHolder> {
    private Context context;
    private int layoutId;
    private int textViewId;
    private int imageViewId;
    private String[] titleArray;
    private int[] imageIdArray;


    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View parent) {
            super(parent);
        }

        ImageView imageView;
        TextView textView;
    }

    public ConditionAdapter(Context context, int layoutId, int textViewId, int imageViewId, String[] titleArray, int[] imageIdArray) {
        this.context = context;
        this.layoutId = layoutId;
        this.textViewId = textViewId;
        this.imageViewId = imageViewId;
        this.titleArray = titleArray;
        this.imageIdArray = imageIdArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
