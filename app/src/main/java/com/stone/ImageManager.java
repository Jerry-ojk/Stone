package com.stone;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stone.model.Stone;
import com.stone.model.StonePicture;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ImageManager {

    public interface ImageLoadListener {
        void onFinish();
    }

    /**
     * 加载并匹配所有图片
     */
    public static void loadAndMatchAllImages(ImageLoadListener loadListener) {
        BmobQuery<StonePicture> bmobQuery = new BmobQuery<>("StonePicture");
        bmobQuery.findObjects(new FindListener<StonePicture>() {
            @Override
            public void done(List<StonePicture> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        for (StonePicture picture : list) {
                            Stone stone = Data.findStoneById(picture.id);
                            if (stone != null) {
                                stone.thumbnailUrl = picture.thumbnail.getUrl();
                                stone.bigImageUrl = picture.bigImage.getUrl();
                            }
                        }
                        if (loadListener != null)
                            loadListener.onFinish();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void loadImage(Stone stone, ImageView imageView) {
        if (stone.bigImageUrl == null) {
            BmobQuery<StonePicture> bmobQuery = new BmobQuery<>("StonePicture");
            bmobQuery.addWhereEqualTo("name", stone.chaName).setLimit(1);
            bmobQuery.findObjects(new FindListener<StonePicture>() {
                @Override
                public void done(List<StonePicture> list, BmobException e) {
                    if (e == null) {
                        if (list != null && list.size() != 0) {
                            StonePicture stonePicture = list.get(0);
                            String thumbnail = stonePicture.getThumbnail().getUrl();
                            String bigImage = stonePicture.getBigImage().getUrl();
                            stone.thumbnailUrl = thumbnail;
                            stone.bigImageUrl = bigImage;
                            if (imageView != null) {
                                Glide.with(imageView.getContext()).load(bigImage).into(imageView);
                            }
                            Log.i("666", stone.chaName + "loadImage" + bigImage);
                        } else {
                            Log.i("666", stone.chaName + "loadImage" + (list == null) + "");
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Glide.with(imageView.getContext()).load(stone.bigImageUrl).into(imageView);
            Log.i("666", stone.chaName + "bigImageUrl有了" + stone.bigImageUrl);
        }
    }

    public static void loadThumbnail(Stone stone, ImageView imageView) {
        if (stone.thumbnailUrl == null) {
            BmobQuery<StonePicture> bmobQuery = new BmobQuery<>("StonePicture");
            bmobQuery.addWhereEqualTo("name", stone.chaName).setLimit(1);
            bmobQuery.findObjects(new FindListener<StonePicture>() {
                @Override
                public void done(List<StonePicture> list, BmobException e) {
                    if (e == null) {
                        if (list != null && list.size() != 0) {
                            StonePicture stonePicture = list.get(0);
                            String thumbnail = stonePicture.getThumbnail().getUrl();
                            String bigImage = stonePicture.getBigImage().getUrl();
                            stone.thumbnailUrl = thumbnail;
                            stone.bigImageUrl = bigImage;
                            if (imageView != null)
                                Glide.with(imageView.getContext()).load(thumbnail).into(imageView);
                            Log.i("666", stone.chaName + "loadThumbnail" + thumbnail);
                        } else {
                            Log.i("666", stone.chaName + "loadThumbnail" + (list == null) + "");
                        }
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Glide.with(imageView.getContext()).load(stone.thumbnailUrl).into(imageView);
            Log.i("666", stone.chaName + "thumbnailUrll有了" + stone.thumbnailUrl);
        }
    }
}
