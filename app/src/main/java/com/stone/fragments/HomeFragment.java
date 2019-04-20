package com.stone.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.Data;
import com.stone.R;
import com.stone.activities.CollectActivity;
import com.stone.activities.MainActivity;
import com.stone.activities.SettingsActivity;
import com.stone.adapters.StoneListAdapter;


public class HomeFragment extends Fragment {

    private SwipeRefreshLayout refresh;
    private TextView hintView;
    private RecyclerView recyclerView;
    private FrameLayout searchView;
    private StoneListAdapter stoneListAdapter;
    private String tag = "HomeFragment";
    private MainActivity activity;
    private PopupMenu popupMenu;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (MainActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        long a = System.currentTimeMillis();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.home_rec);
        ImageView iv_menu = view.findViewById(R.id.iv_menu);
        iv_menu.setOnClickListener(v -> {
            if (popupMenu == null) {
                popupMenu = new PopupMenu(activity, v);
                popupMenu.inflate(R.menu.menu_home);
                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.menu_collect:
                            activity.startActivity(new Intent(activity, CollectActivity.class));
                            break;
                        case R.id.menu_settings:
                            activity.startActivity(new Intent(activity, SettingsActivity.class));
                            break;
                        case R.id.menu_exit:
                            activity.finish();
                            break;
                    }
                    return true;
                });
            }
            popupMenu.show();
        });
        if (activity == null) {
            activity = (MainActivity) container.getContext();
        }
        Data.loadData(activity);
        stoneListAdapter = new StoneListAdapter(activity, Data.STONE_LIST);
        recyclerView.setAdapter(stoneListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        initSearchView(view);
        initScrollView(view);

        hintView = view.findViewById(R.id.hint_home);
        Log.i("HomeFragment", "布局花费" + (System.currentTimeMillis() - a) + "ms");

        return view;
    }

    private void refresh() {
        Toast.makeText(activity, "刷新失败", Toast.LENGTH_SHORT).show();
    }

    private void initScrollView(View parent) {
        refresh = parent.findViewById(R.id.home_refresh);
        refresh.setColorSchemeResources(R.color.colorPrimary);
        refresh.setEnabled(true);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stoneListAdapter.notifyDataSetChanged();
                refresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
//                login.setText(userName);
            }
        }
    }

    private void initSearchView(View parent) {
        searchView = parent.findViewById(R.id.home_searchView);
        searchView.setOnClickListener(view -> {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            SearchFragment searchFragment = new SearchFragment();
            searchFragment.setHint(hintView.getText().toString());
            transaction.add(R.id.fragment_main, searchFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }


    public void onImageLoadFinish() {
        if (stoneListAdapter != null)
            stoneListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
