package com.stone.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Jerry on 2017/11/7
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private Paint paint;

    public MyItemDecoration() {
        paint = new Paint();
        paint.setColor(0XFFAEAEAE);
        paint.setStrokeWidth(1);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getLeft();
        int right = parent.getRight();
        int i = parent.getChildCount();
        for (; i > 0; i--) {
            int y = parent.getChildAt(i - 1).getBottom() + 3;
            c.drawLine(left+40, y, right-40, y, paint);
        }

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, 1);
    }
}
