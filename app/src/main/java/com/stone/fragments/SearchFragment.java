package com.stone.fragments;

import android.animation.Animator;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.stone.Data;
import com.stone.R;
import com.stone.activities.ConditionActivity;
import com.stone.activities.MainActivity;
import com.stone.adapters.TextAdapter;
import com.stone.model.Stone;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    private MainActivity mainActivity;
    private SearchView searchView;
    private RecyclerView advice;
    private ArrayList<Stone> list = new ArrayList<>();
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
        //点击退出
        view.findViewById(R.id.iv_back).setOnClickListener(v -> exitWithAnimation());
        //点击打开高级搜索界面
        view.findViewById(R.id.iv_condition).setOnClickListener(
                v -> mainActivity.startActivity(new Intent(mainActivity, ConditionActivity.class)));
        initViews(view);
        return view;
    }

    /**
     * 带动画效果退出
     */
    public void exitWithAnimation() {
        searchView.clearFocus();
        mainActivity.onSearchFragmentDestroy();
        ViewPropertyAnimator animator = getView().animate().alpha(0);
        animator.setDuration(150);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束时退出
                getFragmentManager().popBackStack();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
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


    /**
     * 输入key后搜索并刷新界面显示结果
     * @param key 输入的关键字
     */
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


    @Override
    public void onDestroy() {
        super.onDestroy();
        searchView.clearFocus();
        mainActivity = null;
    }
}
