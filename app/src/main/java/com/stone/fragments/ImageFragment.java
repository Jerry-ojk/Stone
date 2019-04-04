package com.stone.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stone.R;
import com.stone.activities.StoneActivity;

@SuppressLint("ValidFragment")
public class ImageFragment extends Fragment {
    private StoneActivity stoneActivity;
    private String imageUrl;
    private ImageView imageView;

    public ImageFragment(StoneActivity stoneActivity, String imageUrl) {
        this.stoneActivity = stoneActivity;
        this.imageUrl = imageUrl;

        setSharedElementEnterTransition(new ChangeBounds());
        //setSharedElementEnterTransition(TransitionInflater.from(stoneActivity).inflateTransition(android.R.transition.move).setDuration(5000));
        setSharedElementReturnTransition(new ChangeBounds());
        //setEnterTransition(new Slide(Gravity.RIGHT));
        setReturnTransition(new Fade());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("666", "onCreateView start");
        View parent = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = parent.findViewById(R.id.image);
        //imageView.setBackground(new ColorDrawable(0XFFFFFFFF));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                ImageFragment.this.stoneActivity.dismissImage();
            }
        });
        if (imageUrl != null) {
            Glide.with(stoneActivity).load(imageUrl).into(imageView);
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
}
