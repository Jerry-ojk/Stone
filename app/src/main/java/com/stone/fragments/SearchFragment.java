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
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.stone.Data;
import com.stone.R;
import com.stone.activities.ConditionActivity;
import com.stone.activities.MainActivity;
import com.stone.adapters.TextAdapter;
import com.stone.model.Stone;
import com.stone.model.StoneUnUniform;
import com.stone.model.StoneUniform;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    private MainActivity mainActivity;
    private SearchView searchView;
    private RecyclerView advice;
    private ArrayList<Stone> list = new ArrayList<>();
    private TextAdapter adapter;
    private boolean hasUni = true;//显示均质
    private boolean hasNotUni = true;//显示非均质
    private boolean isSort = false;//启用筛选
    private String key;

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
        CheckBox cb_uni = view.findViewById(R.id.cb_uni);
        cb_uni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasUni = isChecked;
                searchAndRefresh(key);
            }
        });
        CheckBox cb_not_uni = view.findViewById(R.id.cb_not_uni);
        cb_not_uni.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasNotUni = isChecked;
                searchAndRefresh(key);
            }
        });
        CheckBox cb_sort = view.findViewById(R.id.cb_sort);
        cb_sort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSort = isChecked;
                searchAndRefresh(key);
            }
        });
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
     *
     * @param key 输入的关键字
     */
    public void searchAndRefresh(String key) {
        this.key = key;
        list.clear();
        if (key != null && key.length() > 0) {
            for (Stone stone : Data.STONE_LIST) {
                if (!hasUni && stone instanceof StoneUniform) {
                    continue;
                }
                if (!hasNotUni && stone instanceof StoneUnUniform) {
                    continue;
                }
                if (stone.chaName != null && stone.engName != null && stone.formula != null) {
                    if (stone.chaName.contains(key) || stone.formula.contains(key) || stone.engName.contains(key)) {
                        list.add(stone);
                    }
                }
            }
        }
        adapter.refresh(list);
    }

//    public void updateCondition() {
//        int size = list.size();
//        for (int i = 0; i < size; i++) {
//            Stone stone = list.get(i);
//            if (!hasUni && stone instanceof StoneUniform) {
//                list.remove(i);
//                i--;
//                size--;
//            } else if (!hasNotUni && stone instanceof StoneUnUniform) {
//                list.remove(i);
//                i--;
//                size--;
//            }
//        }
//        adapter.refresh(list);
//    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        searchView.clearFocus();
        mainActivity = null;
    }
}
