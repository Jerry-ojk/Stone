package com.stone.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class PhotoView extends ImageView {
    private String url;
    private PhotoDialog dialog;

    public PhotoView(Context context) {
        this(context, null, 0);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dialog = new PhotoDialog(context);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    public void setUrl(String url) {
        this.url = url;
        dialog.setUrl(url);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.scale(0.8f, 0.8f);
        super.onDraw(canvas);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//        }
//        return super.onTouchEvent(event);
//    }
}