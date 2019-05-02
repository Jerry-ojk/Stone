package com.stone.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    }

    @Override
    public void onAttach(Context context) {
        stoneActivity = (StoneActivity) context;
        super.onAttach(context);
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public PhotoView getPhotoView() {
        return photoView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(getViewId(), container, false);
        if (stone == null) {
            stoneActivity.showToast("传递数据错误，请重试");
            return parent;
        }

        parent.findViewById(R.id.iv_back).setOnClickListener(v -> stoneActivity.finish());
        TextView tv_title = parent.findViewById(R.id.tv_title);
        tv_title.setText(stone.chaName);
        iv_heart = parent.findViewById(R.id.iv_heart);
        if (Data.COLLECT_LIST.contains(stone)) {
            isCollect = true;
            iv_heart.setImageTintList(ColorStateList.valueOf(0XFFFF6161));
        }

        iv_heart.setOnClickListener(v -> {
            isCollect = !isCollect;
            if (isCollect) {
                stoneActivity.showToast("收藏" + stone.chaName);
                Data.collectStone(stone, true);
                iv_heart.setImageTintList(ColorStateList.valueOf(0XFFFF6161));
            } else {
                stoneActivity.showToast("取消收藏" + stone.chaName);
                Data.collectStone(stone, false);
                iv_heart.setImageTintList(ColorStateList.valueOf(0XFFFFFFFF));
            }
        });

        photoView = parent.findViewById(R.id.photo_view);
        TextView tv_video = parent.findViewById(R.id.tv_video);
        tv_video.setOnClickListener(v -> {
            if (stone.videoUrl != null) {
                Intent intent = new Intent(stoneActivity, VideoActivity.class);
                intent.putExtra(VideoActivity.VIDEO_NAME, stone.chaName);
                intent.putExtra(VideoActivity.VIDEO_URL, stone.videoUrl);
                startActivity(intent);
            } else {
                stoneActivity.showToast("暂无该矿石的视频");
            }
        });
        ImageManager.loadBigImage(stone, photoView);
        photoView.setOnClickListener((v) -> {
            if (stone.chaName.equals(ImageManager.name) && ImageManager.bigImageCache != null) {
                stoneActivity.showImage(ImageManager.getBitmapFromImageView(photoView));
            }
        });

        tv_chaName = parent.findViewById(R.id.tv_chaName);
        tv_engName = parent.findViewById(R.id.tv_engName);
        tv_formula = parent.findViewById(R.id.tv_formula);
        tv_crystalSystem = parent.findViewById(R.id.tv_crystal);
        tv_uniformity = parent.findViewById(R.id.tv_uniformity);
        tv_hardness = parent.findViewById(R.id.tv_hardness);
        tv_reflectivity = parent.findViewById(R.id.tv_reflectivity);
        tv_reflectColor = parent.findViewById(R.id.tv_reflectColor);
        tv_DRr = parent.findViewById(R.id.tv_DRr);
        tv_internalReflection = parent.findViewById(R.id.tv_internalReflection);
        tv_features = parent.findViewById(R.id.tv_features);
        tv_mic = parent.findViewById(R.id.tv_mic);
        tv_chaName.setText(stone.chaName);
        tv_engName.setText(stone.engName);

        fmt = stone.formula;
        srcfmt = Utils.fmt2src(fmt);
        str = new SpannableString(srcfmt);
        se = Utils.findse(fmt);
        if (!se.isEmpty()) {
            int count = 0;
            for (int i = 0; i < se.size(); i += 2) {
                str.setSpan(new SuperSubSpan(), se.get(i) - (2 * count + 1), se.get(i + 1) - (2 * count + 1), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
        return parent;
    }

    @LayoutRes
    public abstract int getViewId();
}
