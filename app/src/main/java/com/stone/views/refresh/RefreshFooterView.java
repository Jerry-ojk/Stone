package com.stone.views.refresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stone.R;
import com.stone.Utils;


/**
 * Created by Jerry on 2017/10/10
 */

public class RefreshFooterView extends RelativeLayout {
    private int state = -1;
    private TextView titleText;
    private View arrowView;
    private View progressView;
    private RefreshHeaderView header;

    public RefreshFooterView(Context context, RefreshHeaderView header) {
        super(context);
        this.header = header;
        initView();
    }

    public RefreshFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshFooterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setId(android.R.id.widget_frame);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //linearLayout.setBackgroundColor(0xff2196F3);
        linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);

        arrowView = new View(getContext());
        Drawable arrow = getResources().getDrawable(R.drawable.ic_arrow, null);
        arrowView.setBackground(arrow);

        titleText = new TextView(getContext());
        titleText.setTextSize(16);
        titleText.setTextColor(0xffffffff);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(titleText, params);


        params.addRule(CENTER_IN_PARENT);
        addView(linearLayout, params);

        LayoutParams params2 = new LayoutParams((int) Utils.dip2px(28), (int) Utils.dip2px(28));
        params2.addRule(CENTER_VERTICAL);
        params2.addRule(START_OF, android.R.id.widget_frame);
        addView(arrowView, params2);
    }

    public int getState() {
        return state;
    }

    public void notifyStateChange(int state) {
        if (this.state == state) {
            Log.i("RefreshFooterView", "收到重复" + state);
            return;
        }
        Log.i("RefreshHeaderView", "收到" + state);
        switch (state) {
            case RefreshLayout.STATE_HIDE:
                if (header.animator != null) {
                    header.animator.cancel();
                }
                arrowView.setVisibility(VISIBLE);
                arrowView.setRotation(180);
                titleText.setText("上拉加载更多");
                break;
            case RefreshLayout.STATE_DRAG:
                break;
            case RefreshLayout.STATE_OVER:
                titleText.setText("释放立即加载");
                header.changeArrow(arrowView, 180, 0);
                break;
            case RefreshLayout.STATE_OVER_RETURN:
                titleText.setText("上拉加载更多");
                header.changeArrow(arrowView, 0, 180);
                break;
            case RefreshLayout.STATE_REFRESH:
                break;
            case RefreshLayout.STATE_RETURN:
                arrowView.setVisibility(INVISIBLE);
                titleText.setText("加载完成");
                break;
        }
        this.state = state;
    }
}
