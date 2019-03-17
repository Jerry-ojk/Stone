package com.stone.views.refresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.OverScroller;


import com.stone.R;
import com.stone.Utils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Jerry on 2017/10/6
 */

public class RefreshLayout extends ViewGroup implements NestedScrollingParent {

    private int headerHeight;
    private int footerHeight;
    private int max_distance;
    private final int rate = (int) Utils.dip2px(8);
    private int effect_distance;
    private final int dragRate = 6;
    private int mMediumAnimationDuration = getResources().getInteger(
            android.R.integer.config_mediumAnimTime);
    private int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public static final int STATE_HIDE = 0;
    public static final int STATE_DRAG = 1;
    public static final int STATE_OVER = 2;
    public static final int STATE_OVER_RETURN = 5;
    public static final int STATE_REFRESH = 3;
    public static final int STATE_RETURN = 4;

    private RefreshHeaderView header;
    private View content;
    private RefreshFooterView footer;
    private Paint paint;

    private OnRefreshListener refreshListener;

    private OverScroller scroller;
    private NestedScrollingParentHelper parentHelper;
    private int axes;

    public RefreshLayout(Context context) {
        super(context);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        header = new RefreshHeaderView(getContext());
        header.notifyStateChange(STATE_HIDE);
        footer = new RefreshFooterView(getContext(), header);
        footer.notifyStateChange(STATE_HIDE);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        scroller = new OverScroller(context);
        parentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        Log.i("RefreshLayout", "onFinishInflate");
        super.onFinishInflate();
        content = getChildAt(0);

        addView(header, MATCH_PARENT, (int) Utils.dip2px(60));
        addView(footer, MATCH_PARENT, (int) Utils.dip2px(60));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        measureChild(header, widthMeasureSpec, heightMeasureSpec);
        measureChild(footer, widthMeasureSpec, heightMeasureSpec);
        measureChild(content, widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int parentWidth = right - left;
        final int parentHeight = bottom - top;

        header.layout(0, -header.getMeasuredHeight(), parentWidth, 0);
        headerHeight = effect_distance = header.getMeasuredHeight();
        max_distance = headerHeight * dragRate;

        content.layout(0, 0, parentWidth, parentHeight);

        footer.layout(0, parentHeight, parentWidth, parentHeight + footer.getMeasuredHeight());
        footerHeight = footer.getMeasuredHeight();

    }

    public void setRefreshListener(OnRefreshListener listener) {
        this.refreshListener = listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            Log.i("RefreshLayout", "computeScroll：开始回弹");
            if (scroller.getCurrY() == 0) {
                Log.i("RefreshLayout", "computeScroll：回弹结束");
                header.notifyStateChange(STATE_HIDE);
                footer.notifyStateChange(STATE_HIDE);
            }
            scrollTo(0, scroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //画背景
        if (getScrollY() < 0) {
            canvas.drawRect(0, getScrollY(), getWidth(), 0, paint);
            super.dispatchDraw(canvas);
            return;
        }
        if (getScrollY() > 0) {
            canvas.drawRect(0, getHeight(), getWidth(), getHeight() + getScrollY(), paint);
        }
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("dispatchTouchEvent", "");
        if (ev.getAction() == MotionEvent.ACTION_UP && getScrollY() != 0) {
            //下拉回弹
            if (getScrollY() < 0) {
                if (header.getState() == STATE_OVER) {
                    //上拉刷新
                    header.notifyStateChange(STATE_RETURN);
                }
                header.notifyStateChange(STATE_RETURN);
            } else {//上拉回弹
                if (footer.getState() == STATE_OVER) {
                    //下拉加载
                    footer.notifyStateChange(STATE_RETURN);
                }
            }
            scroller.startScroll(0, getScrollY(), 0, -getScrollY());
            invalidate();
        }
        return super.dispatchTouchEvent(ev);
    }

    //嵌套滑动父View
    //开启，停止嵌套滑动时调用
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        return (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        this.axes = axes;
    }

    @Override
    public void onStopNestedScroll(@NonNull View child) {
        Log.i("onStopNestedScroll", "");
        super.onStopNestedScroll(child);
    }

    //嵌套滑动时调用
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {
        Log.i("onNestedPreScroll", "dy=" + dy);
        if (dy < 0) {
            if (-getScrollY() < 2 * headerHeight && !target.canScrollVertically(-1)) {
                Log.i("onNestedPreScroll", "开始下拉");
                if (-(getScrollY() + dy) <= 2 * headerHeight) {
                    consumed[1] = dy;
                    scrollBy(0, dy);
                } else {
                    Log.i("onNestedPreScroll", "下拉超过最大");
                    consumed[1] = (2 * headerHeight + getScrollY());
                    scrollTo(0, -(2 * headerHeight));
                }
                if (-getScrollY() > headerHeight + rate && header.getState() != STATE_OVER) {
                    header.notifyStateChange(STATE_OVER);
                }
            } else if (getScrollY() > 0) {
                consumed[1] = dy;
                scrollBy(0, dy);
                if (getScrollY() < footerHeight && footer.getState() != STATE_OVER_RETURN) {
                    footer.notifyStateChange(STATE_OVER_RETURN);
                }
            }
            return;
        }
        if (dy > 0) {
            if (getScrollY() < 2 * footerHeight && !target.canScrollVertically(1)) {
                if (getScrollY() > footerHeight + rate && footer.getState() != STATE_OVER) {
                    footer.notifyStateChange(STATE_OVER);
                }
                if ((getScrollY() + dy) <= 2 * footerHeight) {
                    consumed[1] = dy;
                    scrollBy(0, dy);
                } else {
                    Log.i("onNestedPreScroll", "上拉拉超过最大");
                    consumed[1] = dy + getScrollY() - 2 * footerHeight;
                    scrollTo(0, 2 * footerHeight);
                }
            } else if (getScrollY() < 0) {
                consumed[1] = dy;
                scrollBy(0, dy);
                if (-getScrollY() < headerHeight && header.getState() != STATE_OVER_RETURN) {
                    header.notifyStateChange(STATE_OVER_RETURN);
                }
            }
        }

    }

    //惯性滑动时调用
    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY,
                                 boolean consumed) {
        Log.i("onNestedFling", "");
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        Log.i("onNestedPreFling", "");
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
        Log.i("getNestedScrollAxes", "");
        return axes;
    }
}
