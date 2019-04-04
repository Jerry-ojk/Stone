package com.stone.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stone.Data;
import com.stone.model.Stone;
import com.stone.model.StonePicture;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ImageManager {
    private static String imageRoot = Environment.getExternalStorageDirectory() + "/stone_image/";
    private static String bigImageDir = imageRoot + "image";
    private static String thumbnailDir = imageRoot + "thumbnail";
    private static ArrayList<Bitmap> thumbnailCache = new ArrayList<>();

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
                                BmobFile videoFile = picture.video;
                                if (videoFile != null) {
                                    stone.videoUrl = videoFile.getUrl();
                                }
                            }
                        }
                        downloadAllImages();
                        if (loadListener != null)
                            loadListener.onFinish();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void loadImage(String path, ImageView imageView) {
        if (!loadLocalImage(path, imageView)) {
            //downloadImage(, path);
        }
    }

    public static boolean loadLocalImage(String path, ImageView imageView) {
        File file = new File(path);
        if (file.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = null;
            try (FileInputStream stream = new FileInputStream(file)) {
                bitmap = BitmapFactory.decodeStream(stream, null, options);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void loadThumbnail(Stone stone, ImageView imageView) {
        Bitmap bitmap = thumbnailCache.get(stone.id);
//        if () {
//
//        }
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

    public static File downloadImage(OkHttpClient okHttpClient, String url, String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                return file;
            } else if (!file.createNewFile()) {
                return null;
            }
            Request request = new Request.Builder().url(url)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody == null || responseBody.contentLength() == 0) {
                return null;
            }
            InputStream inputStream = responseBody.byteStream();
            BufferedOutputStream bufferedStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[4096];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                bufferedStream.write(bytes, 0, len);
            }
            inputStream.close();
            bufferedStream.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void downloadAllImages() {
        File fileBigImage = new File(bigImageDir);
        File fileThumbnail = new File(thumbnailDir);
        if ((fileBigImage.exists() || fileBigImage.mkdirs()) && (fileThumbnail.exists() || fileThumbnail.mkdirs())) {
            ImageDownloadTask task = new ImageDownloadTask();
            task.execute();
        }
    }

    public static String getBigImagePath(Stone stone) {
        return bigImageDir + '/' + stone.chaName + ".jpg";
    }

    public static String getThumbnailPath(Stone stone) {
        return thumbnailDir + '/' + stone.chaName + ".jpg";
    }
}
