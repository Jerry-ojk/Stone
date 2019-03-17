package com.stone.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stone.Data;
import com.stone.R;
import com.stone.activities.MainActivity;
import com.stone.adapters.TextAdapter;
import com.stone.model.Stone;
import com.stone.model.StoneNotUniformity;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    private MainActivity mainActivity;
    private SearchView searchView;
    private RecyclerView advice;

    private ArrayList<Stone> list = new ArrayList<>();

    private String tag = "SearchFragment";
    private TextAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取到mainActivity的引用
        mainActivity = (MainActivity) context;
    }

    public void setHint(String hint) {
        if (searchView != null) {
            searchView.setQueryHint(hint);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        view.findViewById(R.id.search_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit();
            }
        });
        initViews(view);
        return view;
    }

    public void quit() {
        searchView.clearFocus();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.remove(SearchFragment.this);
        transaction.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        searchView.clearFocus();
        mainActivity.showBottom();
        mainActivity = null;
    }

    private void initViews(View parent) {
        searchView = parent.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconified(false);
        //去下划线
        searchView.findViewById(android.support.v7.appcompat.R.id.search_plate).setBackground(null);
        searchView.findViewById(android.support.v7.appcompat.R.id.submit_area).setBackground(null);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchAndRefresh(s);
                if (searchView.isFocused()) {
                    searchView.clearFocus();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchAndRefresh(s);
                return true;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("666", "OnSearchClick");
                searchView.clearFocus();
            }
        });
        mainActivity.showSearchFragment(this);

        advice = parent.findViewById(R.id.search_result);
        adapter = new TextAdapter(mainActivity, this);
        advice.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
        advice.setLayoutManager(manager);
    }

    public void searchAndRefresh(String key) {
        list.clear();
        if (key != null && key.length() > 0) {
            for (Stone stone : Data.STONE_LIST) {
                if (stone.chaName != null && stone.engName != null && stone.formula != null) {
                    if (stone.chaName.contains(key) || stone.formula.contains(key) || stone.engName.contains(key)) {
                        list.add(stone);
                    }
                }
            }
        }
        adapter.refresh(list);
    }
}
