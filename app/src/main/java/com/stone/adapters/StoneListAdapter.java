package com.stone.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import com.stone.R;
import com.stone.activities.MainActivity;
import com.stone.activities.StoneActivity;
import com.stone.image.ImageManager;
import com.stone.model.Stone;

import java.util.ArrayList;


/**
 * Created by Jerry on 2017/12/7
 */

public class StoneListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Stone> data;

    public static int TYPE_HEADER = 0;
    public static int TYPE_ITEM = 1;
    private Context context;
    private View.OnClickListener listener;

    public StoneListAdapter(Context context, ArrayList<Stone> data) {
        this.data = data;
        this.context = context;
        listener = v -> {
            int id = (int) v.getTag();
            Intent intent = new Intent(StoneListAdapter.this.context, StoneActivity.class);
            intent.putExtra("index", id);
            StoneListAdapter.this.context.startActivity(intent);
        };
    }

//    public void refresh(List<StoneNotUniformity> newList) {
//        this.stoneList = newList;
//        notifyItemRangeChanged(1, newList.size());
//    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == TYPE_ITEM) {
            View item = LayoutInflater.from(context).inflate(R.layout.item_stone, parent, false);
            //ImageView imageView= item.findViewById(R.id.item_image);
            //imageView.getDrawable().getIntrinsicHeight();
            item.setOnClickListener(listener);
            holder = new ItemViewHolder(item);
        } else {
            View header = LayoutInflater.from(context).inflate(R.layout.header_layout, parent, false);
            holder = new HeaderViewHolder(header, context);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Stone item = data.get(position);
            ItemViewHolder itemHolder = ((ItemViewHolder) holder);
            itemHolder.itemView.setTag(item.id);
            itemHolder.name.setText(item.chaName + "  " + item.formula);
            itemHolder.company.setText(item.crystalSystem + "  " + item.uniformity);
            itemHolder.des.setText(item.features);
            if (MainActivity.access)
                ImageManager.loadThumbnail(item, itemHolder.image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemHolder = ((ItemViewHolder) holder);
            itemHolder.image.setImageDrawable(null);
            Object object = itemHolder.image.getTag();
            if (object instanceof AsyncTask) {
                ((AsyncTask) object).cancel(false);
            }
            itemHolder.image.setTag(null);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView company;
        TextView project;
        TextView thing;
        TextView people;


        public HeaderViewHolder(View itemView, Context context) {
            super(itemView);
            company = itemView.findViewById(R.id.home_tv_company);
            project = itemView.findViewById(R.id.home_tv_project);
            thing = itemView.findViewById(R.id.home_tv_thing);
            people = itemView.findViewById(R.id.home_tv_people);

            ViewOutlineProvider outline = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    view.setClipToOutline(true);
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 30);
                }
            };
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, ProjectActivity.class);
//                    intent.putExtra("name", ((TextView) v).getText());
//                    context.startActivity(intent);
                }
            };
            company.setOnClickListener(listener);
            project.setOnClickListener(listener);
            thing.setOnClickListener(listener);
            company.setOnClickListener(listener);
            people.setOnClickListener(listener);

            company.setOutlineProvider(outline);
            project.setOutlineProvider(outline);
            thing.setOutlineProvider(outline);
            people.setOutlineProvider(outline);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView company;
        TextView des;

        public ItemViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            des = itemView.findViewById(R.id.item_des);
            company = itemView.findViewById(R.id.item_company);
            //type = itemView.findViewById(R.id.item_type);
        }
    }
}
