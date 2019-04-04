package com.stone.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stone.R;
import com.stone.activities.StoneActivity;
import com.stone.model.Stone;
import com.stone.model.StoneNotUniformity;
import com.stone.model.StoneUniformity;
import com.stone.player.SuperPlayerView;
import com.stone.views.PhotoView;

import static android.view.View.GONE;

@SuppressLint("ValidFragment")
public class StoneFragment extends Fragment {

    private PhotoView photoView;
    private SuperPlayerView superPlayerView;
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


    private StoneActivity stoneActivity;
    private Stone stone;

    public StoneFragment() {

    }

    public StoneFragment(StoneActivity stoneActivity) {
        this.stoneActivity = stoneActivity;
        setExitTransition(new Fade());
    }

    @Override
    public void onAttach(Context context) {
        stoneActivity = (StoneActivity) context;
        super.onAttach(context);
        setExitTransition(new Fade());
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

    final String chaName = "中文名称:  ";
    final String engName = "英文名称:  ";
    final String formula = "化学式:  ";
    final String crystalSystem = "矿物晶系:  ";
    final String uniformity = "均非性:  ";
    final String Rr = "反射视旋转角Rr:  ";
    final String DRr = "反射视旋转色散:  ";
    final String internalReflection = "内反射:  ";
    final String Ar = "非均质视旋转角Ar:  ";
    final String DAr = "非均质视旋转色散DAr:  ";
    final String Rs = "旋向Rs:  ";
    final String Ps = "相符Ps:  ";
    final String DAR = "反射视旋转色散DAR:  ";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_stone, container, false);

        if (stone == null) {
            Toast.makeText(getContext(), "传递数据错误，请重试", Toast.LENGTH_SHORT).show();
            return parent;
        }

        Toolbar toolbar = parent.findViewById(R.id.com_toolbar);

        photoView = parent.findViewById(R.id.photo_view);
        superPlayerView = parent.findViewById(R.id.video_player);

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


        TextView tv_uni_Rr = parent.findViewById(R.id.tv_uni_Rr);


        TextView tv_not_dRColor = parent.findViewById(R.id.tv_not_doubleReflectColor);
        TextView tv_not_Ar = parent.findViewById(R.id.tv_not_Ar);
        TextView tv_not_DAr = parent.findViewById(R.id.tv_not_DAr);
        TextView tv_not_Rs = parent.findViewById(R.id.tv_not_Rs);
        TextView tv_not_Ps = parent.findViewById(R.id.tv_not_Ps);
        TextView tv_not_DAR = parent.findViewById(R.id.tv_not_DAR);


        Log.i("666", "进入详情界面，加载" + stone.chaName);
        Glide.with(stoneActivity).asBitmap().load(stone.bigImageUrl).into(photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stoneActivity.showImage(stone.bigImageUrl);
            }
        });

        if (stone.videoUrl != null) {
            superPlayerView.setVideoPath(stone.videoUrl);
        }

        StoneNotUniformity stoneNotUniformity = null;
        StoneUniformity stoneUniformity = null;


        toolbar.setTitle(stone.chaName);
        tv_chaName.setText(stone.chaName);
        tv_engName.setText(stone.engName);
        tv_formula.setText(stone.formula);
        tv_crystalSystem.setText(stone.crystalSystem);
        tv_uniformity.setText(stone.uniformity);
        tv_reflectivity.setText(stone.reflectivity);
        tv_hardness.setText(stone.hardness);
        tv_reflectColor.setText(stone.reflectColor);
        tv_DRr.setText(stone.DRr);
        tv_internalReflection.setText(stone.internalReflection);
        tv_features.setText(stone.features);
        tv_mic.setText(stone.mic);

        if (stone instanceof StoneUniformity) {
            stoneUniformity = (StoneUniformity) stone;
            tv_not_dRColor.setText(stoneUniformity.Rr);
            TextView tv_title_doubleRefl = parent.findViewById(R.id.tv_not_doubleReflectColor_tag);
            tv_title_doubleRefl.setVisibility(GONE);
            tv_not_dRColor.setVisibility(GONE);
            tv_not_Ar.setVisibility(GONE);
            tv_not_DAr.setVisibility(GONE);
            tv_not_Rs.setVisibility(GONE);
            tv_not_Ps.setVisibility(GONE);
            tv_not_DAR.setVisibility(GONE);
        } else {
            stoneNotUniformity = (StoneNotUniformity) stone;
            tv_uni_Rr.setVisibility(GONE);
            tv_not_dRColor.setText(stoneNotUniformity.doubleReflectColor);
            tv_not_Ar.setText(stoneNotUniformity.Ar);
            tv_not_DAr.setText(stoneNotUniformity.DAr);
            tv_not_Rs.setText(stoneNotUniformity.Rs);
            tv_not_Ps.setText(stoneNotUniformity.Ps);
            tv_not_DAR.setText(stoneNotUniformity.DAR);
        }
//        Window window = getWindow();
//        ChangeImageTransform transform = new ChangeImageTransform();
//        transform.setDuration(1000);
//        transform.addTarget(photoView);
//        //window.setSharedElementEnterTransition(transform);
//        window.setSharedElementExitTransition(transform);
        return parent;
    }

    private void showUniformity() {

    }

    private void showNotUniformity() {

    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong("During", superPlayerView.getDuration());
        outState.putLong("PlayPosition", superPlayerView.getPlayPosition());
        outState.putFloat("BufferLength", superPlayerView.getBufferLengthPixel());
        superPlayerView.pause();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle != null) {
            superPlayerView.setDuration(bundle.getLong("During", 0));
            superPlayerView.setPlayPosition(bundle.getLong("PlayPosition", 0));
            superPlayerView.setBufferLengthPixel(bundle.getFloat("BufferLength", 0f));
        }
    }

    @Override
    public void onDestroyView() {
        superPlayerView.pause();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        superPlayerView.release();
        super.onDestroy();
    }
}
