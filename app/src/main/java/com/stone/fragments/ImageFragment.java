package com.stone.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

@SuppressLint("ValidFragment")
public class ImageFragment extends Fragment {
    private String imageUrl;
    private ImageView imageView;

    public ImageFragment(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        imageView = new ImageView(getContext());
        return imageView;
    }
}
