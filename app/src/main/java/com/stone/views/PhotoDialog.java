package com.stone.views;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stone.R;

public class PhotoDialog extends Dialog {
    private String url;
    private ImageView imageView;

    public PhotoDialog(@NonNull Context context) {
        //super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        super(context, R.style.PhotoDialog);

    }

    public PhotoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PhotoDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        layoutParams.x = displayMetrics.widthPixels;
        layoutParams.y = displayMetrics.heightPixels;
        window.setAttributes(layoutParams);
        Context context = getContext();
        window.setLayout(displayMetrics.widthPixels, displayMetrics.heightPixels);
        imageView = new ImageView(context);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(imageView, new ViewGroup.LayoutParams(displayMetrics.widthPixels, displayMetrics.heightPixels));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
                if (bitmapDrawable != null) {
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    Log.i("666", bitmap.getWidth() + "x" + bitmap.getHeight() + "-" + bitmap.getByteCount() + "-" + bitmap.getAllocationByteCount());
                }
            }
        });
        Glide.with(context).load(url).into(imageView);

    }
}
