package com.stone.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.stone.R;


/**
 * Created by Jerry on 2017/9/17
 */

public class LoginButton extends View {

    private Paint paint_text;
    private Paint paint_Arc;

    private final float scaling = getResources().getDisplayMetrics().density;
    private int default_width;

    private float startAngle;
    private float sweepAngle;
    private float arc_width;

    private boolean isLogging = false;

    private String text = "登陆";

    private float textY;

    private ValueAnimator animator;

    public LoginButton(Context context) {
        super(context);
        initView();
    }

    public LoginButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoginButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void start() {
        animator.start();
        this.text = "登陆中...";
        sweepAngle = 120;
        isLogging = true;
    }

    public void cancel() {
        animator.cancel();
        sweepAngle = 360;
        this.text = "重试";
        paint_text.setColor(getResources().getColor(R.color.colorPrimary));
        isLogging = false;
        invalidate();
    }

    public boolean islogging() {
        return isLogging;
    }

    public void success() {
        animator.cancel();
        sweepAngle = 360;
        this.text = "登陆成功";
        paint_text.setColor(getResources().getColor(R.color.colorAccent));
        isLogging = false;
        animator = null;
        invalidate();
    }

    public void fail() {
        animator.cancel();
        this.text = "登陆失败";
        sweepAngle = 360;
        paint_text.setColor(getResources().getColor(R.color.error_color_material));
        isLogging = false;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(arc_width, arc_width, default_width - arc_width, default_width - arc_width, startAngle, sweepAngle, false, paint_Arc);

        canvas.drawText(text, default_width / 2, textY, paint_text);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.AT_MOST) {
            widthSpecSize = default_width;
            heightSpecSize = default_width;
        }
        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    private void initView() {
        arc_width = 2 * scaling;
        paint_text = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_text.setColor(getResources().getColor(R.color.colorPrimary));
        paint_text.setStyle(Paint.Style.FILL);
        paint_text.setTextSize(12 * scaling);
        paint_text.setTextAlign(Paint.Align.CENTER);
        paint_Arc = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_Arc.setColor(getResources().getColor(R.color.colorPrimary));
        paint_Arc.setStrokeWidth(arc_width * 2);
        paint_Arc.setStyle(Paint.Style.STROKE);
        default_width = (int) (60 * scaling);
        startAngle = 0;
        sweepAngle = 360;
        Paint.FontMetrics fm = paint_text.getFontMetrics();
        textY = default_width / 2 + (fm.descent - fm.ascent) / 2 - fm.descent;
        animator = ValueAnimator.ofFloat(0, 360);
        animator.setDuration(600);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(10);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                startAngle = (Float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !isLogging && super.onTouchEvent(event);
    }
}
