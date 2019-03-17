package com.stone.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stone.R;


/**
 * Created by Jerry on 2017/10/11
 */

public class loopAdapter extends RecyclerView.Adapter<loopAdapter.ViewHolder> {
    private Context context;
    private int[] res = {R.drawable.photo_1, R.drawable.photo_2, R.drawable.photo_3, R.drawable.photo_4, R.drawable.photo_5};

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_lunbo);
        }
    }

    public loopAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loop, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //用Glide加载图片
        //Utils.loadImage(holder.imageView, fragment, Urls[position]);
        // new String[]{"1.jpg", "2.jpg", "3.jpg"}
        holder.imageView.setImageResource(res[position % 5]);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
