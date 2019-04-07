package com.stone.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stone.R;
import com.stone.activities.StoneActivity;
import com.stone.model.Stone;
import com.stone.player.SuperPlayerView;
import com.stone.views.PhotoView;

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
        View parent = inflater.inflate(R.layout.fragment_stone_un, container, false);
        if (stone == null) {
            Toast.makeText(getContext(), "传递数据错误，请重试", Toast.LENGTH_SHORT).show();
            return parent;
        }

        Toolbar toolbar = parent.findViewById(R.id.toolbar_stone);
        toolbar.setTitle(stone.chaName);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> stoneActivity.finish());

        photoView = parent.findViewById(R.id.photo_view);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stoneActivity.showImage(stone.bigImageUrl);
            }
        });

        //superPlayerView = parent.findViewById(R.id.video_player);
        if (stone.videoUrl != null) {
            //superPlayerView.setVideoPath(stone.videoUrl);
        }

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
        Log.i("666", "进入详情界面，加载" + stone.chaName);
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

//        Window window = getWindow();
//        ChangeImageTransform transform = new ChangeImageTransform();
//        transform.setDuration(1000);
//        transform.addTarget(photoView);
//        //window.setSharedElementEnterTransition(transform);
//        window.setSharedElementExitTransition(transform);
        return parent;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (superPlayerView != null) {
            outState.putLong("During", superPlayerView.getDuration());
            outState.putLong("PlayPosition", superPlayerView.getPlayPosition());
            outState.putFloat("BufferLength", superPlayerView.getBufferLengthPixel());
            superPlayerView.pause();
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle != null && superPlayerView != null) {
            superPlayerView.setDuration(bundle.getLong("During", 0));
            superPlayerView.setPlayPosition(bundle.getLong("PlayPosition", 0));
            superPlayerView.setBufferLengthPixel(bundle.getFloat("BufferLength", 0f));
        }
    }

    @Override
    public void onDestroyView() {
        if (superPlayerView != null)
            superPlayerView.pause();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (superPlayerView != null)
            superPlayerView.release();
        super.onDestroy();
    }
}
