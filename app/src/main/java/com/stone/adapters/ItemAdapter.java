package com.stone.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stone.R;


/**
 * Created by Jerry on 2017/9/13
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    public ItemAdapter() {
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView company;
        TextView des;

        ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            des = itemView.findViewById(R.id.item_des);
            company = itemView.findViewById(R.id.item_company);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stone, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 8;
    }

}
