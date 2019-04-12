package com.stone.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stone.R;
import com.stone.activities.StoneActivity;
import com.stone.image.ImageManager;

@SuppressLint("ValidFragment")
public class ImageFragment extends Fragment {
    private StoneActivity stoneActivity;
    private Bitmap bitmap;
    private ImageView imageView;

    public ImageFragment(StoneActivity stoneActivity, Bitmap bitmap) {
        this.stoneActivity = stoneActivity;
        this.bitmap = bitmap;

        //setSharedElementEnterTransition(new ChangeBounds());
        //setSharedElementEnterTransition(TransitionInflater.from(stoneActivity).inflateTransition(android.R.transition.move).setDuration(5000));
        //setSharedElementReturnTransition(new ChangeBounds());
        //setEnterTransition(new Slide(Gravity.RIGHT));
        //setReturnTransition(new Fade());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("666", "onCreateView start");
        View parent = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = parent.findViewById(R.id.image);
        //imageView.setBackground(new ColorDrawable(0XFFFFFFFF));
        imageView.setOnClickListener(v -> {
            ImageFragment.this.stoneActivity.dismissImage();
            getFragmentManager().popBackStack();
        });
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        Log.i("666", "onCreateView end");
        return parent;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i("666", "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        Log.i("666", "onInflate");
    }

    @Override
    public void onDestroyView() {
        Bitmap bitmap = ImageManager.getBitmapFromImageView(imageView);
        bitmap.recycle();
        imageView.setImageDrawable(null);
        super.onDestroyView();
        Log.i("666", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("666", "onDestroy");
    }
}
