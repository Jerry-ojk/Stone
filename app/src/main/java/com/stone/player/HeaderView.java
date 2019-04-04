package com.stone.player;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.stone.views.DPUtils;


public class HeaderView extends View {
    private Paint paint;
    private float textOffset;
    private boolean isTouchBack = false;
    private PlayerController controller;
    private float lastX = 0;
    private String title = "超级播放器";

    public HeaderView(Context context, PlayerController controller) {
        super(context);
        this.controller = controller;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(6);
        paint.setTextSize(DPUtils.DP15);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        textOffset = (fontMetrics.bottom - fontMetrics.top - 6) / 2 - fontMetrics.descent;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height2 = getHeight() >> 1;
        //返回按钮
        paint.setStrokeWidth(DPUtils.DP2);
        if (isTouchBack) {
            paint.setColor(0x5FFFFFFF);
            canvas.drawCircle(DPUtils.DP13, DPUtils.DP18, DPUtils.DP18, paint);
        }
        paint.setColor(0xFFFFFFFF);
        canvas.drawLine(DPUtils.DP10, DPUtils.DP18, DPUtils.DP15, DPUtils.DP13, paint);
        canvas.drawLine(DPUtils.DP10, DPUtils.DP18, DPUtils.DP15, DPUtils.DP23, paint);

        //标题
        canvas.drawText(title, DPUtils.DP36, height2 + textOffset, paint);
    }

    private void invalidateBack() {
        invalidate(0, 0, DPUtils.DP32, getHeight());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                if (x < DPUtils.DP32) {
                    isTouchBack = true;
                    invalidateBack();
                }
                lastX = x;
                return true;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                if (Math.abs(x - lastX) > DPUtils.DP4) {
                    if (isTouchBack) {
                        isTouchBack = false;
                        invalidateBack();
                    }
                }
                lastX = x;
                return true;
            case MotionEvent.ACTION_UP:
                if (isTouchBack) {
                    controller.onBack();
                    invalidateBack();
                    isTouchBack = false;
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                isTouchBack = false;
                invalidateBack();
                return true;
        }
        return true;
    }


    public void setTitle(String title) {
        this.title = title;
    }

}