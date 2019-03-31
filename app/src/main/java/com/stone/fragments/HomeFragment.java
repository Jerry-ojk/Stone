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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.Data;
import com.stone.R;
import com.stone.adapters.StoneListAdapter;


public class HomeFragment extends Fragment {

    private SwipeRefreshLayout refresh;
    private TextView hintView;
    private RecyclerView recyclerView;
    private LinearLayout searchView;
    private StoneListAdapter stoneListAdapter;
    private String tag = "HomeFragment";
    //private SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);

    //private Retrofit retrofit;
    //private SortedList.Callback<ComInfoBean> callback;

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        long a = System.currentTimeMillis();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.home_rec);
        if (context == null) {
            context = container.getContext();
        }
        Data.loadData(context);
        stoneListAdapter = new StoneListAdapter(context);
        recyclerView.setAdapter(stoneListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        recyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//                refresh();
//            }
//        });

        initSearchView(view);
        initScrollView(view);

        hintView = view.findViewById(R.id.hint_home);
        Log.i("HomeFragment", "布局花费" + (System.currentTimeMillis() - a) + "ms");
        return view;
    }

    private void refresh() {
        Toast.makeText(context, "刷新失败", Toast.LENGTH_SHORT).show();
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
            transaction.commit();
        });
    }


    public void onImageLoadFinish() {
        stoneListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
