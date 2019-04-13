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

import com.stone.R;
import com.stone.activities.StoneActivity;
import com.stone.image.ImageManager;
import com.stone.views.PhotoView;

@SuppressLint("ValidFragment")
public class ImageFragment extends Fragment {
    private StoneActivity stoneActivity;
    private Bitmap bitmap;
    private PhotoView photoView;

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
        photoView = parent.findViewById(R.id.photo_view);
        //imageView.setBackground(new ColorDrawable(0XFFFFFFFF));
        photoView.setOnClickListener(v -> {
            ImageFragment.this.stoneActivity.dismissImage();
            getFragmentManager().popBackStack();
        });
        if (bitmap != null) {
            photoView.setImageBitmap(bitmap);
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
//        Bitmap bitmap = ImageManager.getBitmapFromImageView(photoView);
//        bitmap.recycle();
//        photoView.setImageDrawable(null);
        super.onDestroyView();
        Log.i("666", "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("666", "onDestroy");
    }
}
