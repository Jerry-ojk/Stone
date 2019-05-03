package com.stone.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stone.R;
import com.stone.Utils;
import com.stone.activities.StoneActivity;
import com.stone.activities.MainActivity;
import com.stone.fragments.SearchFragment;
import com.stone.model.Stone;

import java.util.ArrayList;


/**
 * Created by Jerry on 2017/10/15
 */

public class TextAdapter extends RecyclerView.Adapter<TextViewHolder> {
    private SearchFragment searchFragment;
    private MainActivity mainActivity;
    private ArrayList<Stone> list;
    private View.OnClickListener listener;

    public TextAdapter(MainActivity context, SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
        this.mainActivity = context;
        listener = v -> {
            int id = (int) v.getTag();
            Intent intent = new Intent(this.mainActivity, StoneActivity.class);
            intent.putExtra("index", id);
            mainActivity.startActivity(intent);
            //this.searchFragment.exit();
        };
    }

    public void refresh(ArrayList<Stone> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_advice, parent, false);
        TextView view = new TextView(parent.getContext());
        view.setSingleLine(true);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) Utils.dip2px(32));
        params.bottomMargin = 24;
        params.rightMargin = 24;
        view.setLayoutParams(params);
        view.setBackgroundResource(R.drawable.bg_advice);
        view.setGravity(Gravity.CENTER);
        view.setOnClickListener(listener);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TextViewHolder holder, int position) {
        Stone stone = list.get(position);
        holder.itemView.setTag(stone.id);
        holder.textView.setText(stone.chaName + "  " + stone.engName);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}
