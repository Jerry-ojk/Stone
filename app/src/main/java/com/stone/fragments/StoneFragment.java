package com.stone.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.Data;
import com.stone.R;
import com.stone.Utils;
import com.stone.activities.StoneActivity;
import com.stone.activities.VideoActivity;
import com.stone.image.ImageManager;
import com.stone.model.Stone;
import com.stone.views.PhotoView;
import com.stone.views.SuperSubSpan;

import java.util.List;

import okhttp3.internal.Util;

@SuppressLint("ValidFragment")
public abstract class StoneFragment extends Fragment {

    private PhotoView photoView;
    private TextView tv_chaName;
    private TextView tv_engName;
    private TextView tv_formula;
    private TextView tv_crystalSystem;
    private TextView tv_uniformity;
    private TextView tv_reflectivity;
    private TextView tv_hardness;
    private TextView tv_reflectColor;
    private TextView tv_DRr;
    private TextView tv_internalReflection;
    private TextView tv_mic;
    private TextView tv_features;
    private ImageView iv_heart;
    private boolean isCollect = false;
    private String fmt;
    private String srcfmt;
    private Spannable str;
    private List<Integer> se;


    private StoneActivity stoneActivity;
    private Stone stone;

    public StoneFragment() {

    }

    public StoneFragment(StoneActivity stoneActivity) {
        this.stoneActivity = stoneActivity;
        //setExitTransition(new Fade());
    }

    @Override
    public void onAttach(Context context) {
        stoneActivity = (StoneActivity) context;
        super.onAttach(context);
        //setExitTransition(new Fade());
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public PhotoView getPhotoView() {
        return photoView;
    }

    public void setPhotoView(PhotoView photoView) {
        this.photoView = photoView;
    }

    private final String tag = "DetailsActivity";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(getViewId(), container, false);
        if (stone == null) {
            Toast.makeText(getContext(), "传递数据错误，请重试", Toast.LENGTH_SHORT).show();
            return parent;
        }

        parent.findViewById(R.id.iv_back).setOnClickListener(v -> stoneActivity.finish());
        TextView tv_title = parent.findViewById(R.id.tv_title);
        tv_title.setText(stone.chaName);
        iv_heart = parent.findViewById(R.id.iv_heart);
        if (Data.COLLECT_LIST.contains(stone)) {
            isCollect = true;
            iv_heart.setImageResource(R.drawable.blue_heart);
        }

        iv_heart.setOnClickListener(v -> {
            isCollect = !isCollect;
            if (isCollect) {
                Data.collectStone(stone, true);
//                iv_heart.setImageTintList(ColorStateList.valueOf(0xff2196F3));
                iv_heart.setImageResource(R.drawable.blue_heart);
                Toast.makeText(stoneActivity, "收藏"+stone.chaName, Toast.LENGTH_SHORT).show();
            } else {
                Data.collectStone(stone, false);
//                iv_heart.setImageTintList(ColorStateList.valueOf(0xaa8b8b8b));
                iv_heart.setImageResource(R.drawable.hollow_heart);
                Toast.makeText(stoneActivity, "取消收藏"+stone.chaName, Toast.LENGTH_SHORT).show();
            }
        });

        photoView = parent.findViewById(R.id.photo_view);
        TextView tv_video = parent.findViewById(R.id.tv_video);
        if(stone.videoUrl == null){
            tv_video.setText("暂无视频");
        }
        tv_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stone.videoUrl != null) {
                    Intent intent = new Intent(stoneActivity, VideoActivity.class);
                    intent.putExtra(VideoActivity.VIDEO_NAME, stone.chaName);
                    intent.putExtra(VideoActivity.VIDEO_URL, stone.videoUrl);
                    startActivity(intent);
                } else {
                    Toast.makeText(stoneActivity, "该矿石暂无视频", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.i("666", "进入详情界面，加载" + stone.chaName);
        ImageManager.loadBigImage(stone, photoView);
        photoView.setOnClickListener(v -> stoneActivity.showImage(ImageManager.getBitmapFromImageView(photoView)));

        //superPlayerView = parent.findViewById(R.id.video_player);
        if (stone.videoUrl != null) {
            //superPlayerView.setVideoPath(stone.videoUrl);
        }

        tv_chaName = parent.findViewById(R.id.tv_chaName);
        tv_engName = parent.findViewById(R.id.tv_engName);
        tv_formula = parent.findViewById(R.id.tv_formula);
        tv_crystalSystem = parent.findViewById(R.id.tv_crystal);
        tv_internalReflection = parent.findViewById(R.id.tv_internalReflection);
        tv_uniformity = parent.findViewById(R.id.tv_uniformity);
        tv_hardness = parent.findViewById(R.id.tv_hardness);
        tv_reflectivity = parent.findViewById(R.id.tv_reflectivity);
        tv_reflectColor = parent.findViewById(R.id.tv_reflectColor);
        tv_DRr = parent.findViewById(R.id.tv_DRr);
        tv_features = parent.findViewById(R.id.tv_features);
        tv_mic = parent.findViewById(R.id.tv_mic);

        tv_chaName.setText(stone.chaName);
        tv_engName.setText(stone.engName);

        fmt = stone.formula;
        srcfmt = Utils.fmt2src(fmt);
        str = new SpannableString(srcfmt);
        se = Utils.findse(fmt);
        if(!se.isEmpty()) {
            int count = 0;
            for (int i = 0; i < se.size(); i += 2) {
                str.setSpan(new SuperSubSpan(), se.get(i)-(2*count+1), se.get(i + 1)-(2*count+1), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                count += 1;
            }
        }
        tv_formula.setText(str);

        tv_crystalSystem.setText(stone.crystalSystem);
        tv_uniformity.setText(stone.uniformity);
        tv_reflectivity.setText(stone.reflectivity);
        tv_hardness.setText(stone.hardness);
        tv_reflectColor.setText(stone.reflectColor);
        tv_DRr.setText(stone.DRr);
        tv_internalReflection.setText(stone.internalReflection);
        tv_features.setText(stone.features);
        tv_mic.setText(stone.mic);

//        Window window = getWindow();
//        ChangeImageTransform transform = new ChangeImageTransform();
//        transform.setDuration(1000);
//        transform.addTarget(photoView);
//        //window.setSharedElementEnterTransition(transform);
//        window.setSharedElementExitTransition(transform);
        return parent;
    }

    @LayoutRes
    public abstract int getViewId();


//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        if (superPlayerView != null) {
//            outState.putLong("During", superPlayerView.getDuration());
//            outState.putLong("PlayPosition", superPlayerView.getPlayPosition());
//            outState.putFloat("BufferLength", superPlayerView.getBufferLengthPixel());
//            superPlayerView.pause();
//        }
//    }
//
//    @Override
//    public void onViewStateRestored(@Nullable Bundle bundle) {
//        super.onViewStateRestored(bundle);
//        if (bundle != null && superPlayerView != null) {
//            superPlayerView.setDuration(bundle.getLong("During", 0));
//            superPlayerView.setPlayPosition(bundle.getLong("PlayPosition", 0));
//            superPlayerView.setBufferLengthPixel(bundle.getFloat("BufferLength", 0f));
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        if (superPlayerView != null)
//            superPlayerView.pause();
//        //ImageManager.clearBigImageCache();
//        super.onDestroyView();
//    }
//
//    @Override
//    public void onDestroy() {
//        if (superPlayerView != null)
//            superPlayerView.release();
//        super.onDestroy();
//    }
}
