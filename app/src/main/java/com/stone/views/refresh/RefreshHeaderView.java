package com.stone.views.refresh;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Jerry on 2017/10/6
 */

public class RefreshHeaderView extends RelativeLayout implements RefreshHeader {
    private int state = RefreshLayout.STATE_HIDE;
    SimpleDateFormat format = new SimpleDateFormat("上次更新 MM月dd日 HH:mm", Locale.CHINA);
    private Date lastTime = new Date();
    private TextView titleText;
    private TextView timeText;
    private View arrowView;
    private View progressView;
    public ObjectAnimator animator;


    public RefreshHeaderView(Context context) {
        super(context);

        initView();
    }

    public RefreshHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        Drawable arrow = getResources().getDrawable(R.drawable.ic_arrow);
        Log.i("是否为", "" + (arrow instanceof VectorDrawable));
        arrowView.setBackground(arrow);

        titleText = new TextView(getContext());
        titleText.setText("下拉刷新");
        titleText.setTextSize(16);
        titleText.setTextColor(0xffffffff);

        timeText = new TextView(getContext());
        timeText.setTextSize(12);
        timeText.setTextColor(0xff9fcbef);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(titleText, params);
        linearLayout.addView(timeText, params);

        params.addRule(CENTER_IN_PARENT);
        addView(linearLayout, params);

        LayoutParams params2 = new LayoutParams((int) Utils.dip2px(28), (int) Utils.dip2px(28));
        params2.addRule(CENTER_VERTICAL);
        params2.addRule(START_OF, android.R.id.widget_frame);
        addView(arrowView, params2);
    }

    /**
     * 下拉刷新时调用
     */
    @Override
    public void onRefresh() {
        Log.i("ReFreshHeaderView", "onRefresh");
    }

    /**
     * @return 返回View示例
     */
    @Override
    public View getView() {
        return this;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public void notifyStateChange(int state) {
        if (this.state == state) {
            Log.i("RefreshHeaderView", "收到重复" + state);
            return;
        }
        Log.i("RefreshHeaderView", "收到" + state);
        switch (state) {
            case RefreshLayout.STATE_HIDE:
                lastTime.setTime(System.currentTimeMillis());
                if (animator != null) {
                    animator.cancel();
                }
                arrowView.setVisibility(VISIBLE);
                arrowView.setRotation(0);
                titleText.setText("下拉刷新");
                timeText.setText(format.format(lastTime));
                break;
            case RefreshLayout.STATE_DRAG:
                break;
            case RefreshLayout.STATE_OVER:
                titleText.setText("释放刷新");
                changeArrow(arrowView,0, 180);
                break;
            case RefreshLayout.STATE_OVER_RETURN:
                titleText.setText("下拉刷新");
                changeArrow(arrowView,180, 0);
                break;
            case RefreshLayout.STATE_REFRESH:
                break;
            case RefreshLayout.STATE_RETURN:
                arrowView.setVisibility(INVISIBLE);
                arrowView.setRotation(0);
                titleText.setText("刷新完成");
                changeArrow(arrowView,180, 0);
                break;
        }
        this.state = state;
    }

    public void changeArrow(View view,float start, float end) {
        if (animator != null) {
            animator.cancel();
        }
        animator = ObjectAnimator.ofFloat(view, "rotation", start, end);
        animator.setDuration(200);
        animator.start();
    }
}
