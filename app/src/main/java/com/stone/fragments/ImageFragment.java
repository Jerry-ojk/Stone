package com.stone.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stone.R;
import com.stone.activities.StoneActivity;
import com.stone.photoview.PhotoView;

@SuppressLint("ValidFragment")
public class ImageFragment extends Fragment {
    private StoneActivity stoneActivity;
    private Bitmap bitmap;
    private PhotoView photoView;

    public ImageFragment(StoneActivity stoneActivity, Bitmap bitmap) {
        this.stoneActivity = stoneActivity;
        this.bitmap = bitmap;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_image, container, false);
        photoView = parent.findViewById(R.id.photo_view);
        photoView.setOnClickListener(v -> {
            ImageFragment.this.stoneActivity.dismissImage();
            getFragmentManager().popBackStack();
        });
        if (bitmap != null) {
            photoView.setImageBitmap(bitmap);
        }
        return parent;
    }
}
