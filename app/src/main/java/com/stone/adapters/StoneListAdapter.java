package com.stone.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stone.R;
import com.stone.Utils;
import com.stone.activities.MainActivity;
import com.stone.activities.StoneActivity;
import com.stone.image.ImageManager;
import com.stone.model.Stone;
import com.stone.views.SuperSubSpan;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jerry on 2017/12/7
 */

public class StoneListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Stone> data;

    private Context context;
    private String fmt;
    private String srcfmt;
    private Spannable str;
    private List<Integer> se;
    private View.OnClickListener listener;

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView che_name;
        TextView company;
        TextView des;

        public ItemViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_name);
            che_name = itemView.findViewById(R.id.item_che_name);
            des = itemView.findViewById(R.id.item_des);
            company = itemView.findViewById(R.id.item_company);
            //type = itemView.findViewById(R.id.item_type);
        }
    }

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
        View item = LayoutInflater.from(context).inflate(R.layout.item_stone, parent, false);
        item.setOnClickListener(listener);
        return new ItemViewHolder(item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Stone item = data.get(position);
            ItemViewHolder itemHolder = ((ItemViewHolder) holder);
            itemHolder.itemView.setTag(item.id);
            itemHolder.name.setText(item.chaName + "  ");

            fmt = item.formula;
            srcfmt = Utils.fmt2src(fmt);
            str = new SpannableString(srcfmt);
            se = Utils.findse(fmt);
            if (!se.isEmpty()) {
                int count = 0;
                for (int i = 0; i < se.size(); i += 2) {
                    str.setSpan(new SuperSubSpan(), se.get(i) - (2 * count + 1), se.get(i + 1) - (2 * count + 1), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    count += 1;
                }
            }
            itemHolder.che_name.setText(str);

            itemHolder.company.setText(item.crystalSystem + "  " + item.uniformity);
            itemHolder.des.setText(item.features);
            if (MainActivity.access)
                ImageManager.loadThumbnail(item, itemHolder.image);
        }
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

}
