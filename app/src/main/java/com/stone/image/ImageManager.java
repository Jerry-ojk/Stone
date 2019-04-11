package com.stone.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stone.Data;
import com.stone.model.Stone;
import com.stone.model.StonePicture;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ImageManager {
    private static String imageRoot = Environment.getExternalStorageDirectory() + "/stone_image/";
    private static String bigImageDir = imageRoot + "image";
    private static String identifyDir = imageRoot + "identify";
    private static ArrayCache thumbnailCache = new ArrayCache(56);
    private static Bitmap bigImageCache = null;
    private static String name = null;


    public interface ImageLoadListener {
        void onFinish();
    }

    /**
     * 加载并匹配所有图片
     */
    public static void fillStoneImages(ImageLoadListener loadListener) {
        BmobQuery<StonePicture> bmobQuery = new BmobQuery<>("StonePicture");
        bmobQuery.findObjects(new FindListener<StonePicture>() {
            @Override
            public void done(List<StonePicture> list, BmobException e) {
                if (e == null) {
                    if (list != null) {
                        for (StonePicture picture : list) {
                            Stone stone = Data.findStoneById(picture.id);
                            if (stone != null) {
                                if (picture.identifyImage != null) {
                                    stone.identifyImageUrl = picture.identifyImage.getUrl();
                                }
                                if (picture.bigImage != null) {
                                    stone.bigImageUrl = picture.bigImage.getUrl();
                                }
                                BmobFile videoFile = picture.video;
                                if (videoFile != null) {
                                    stone.videoUrl = videoFile.getUrl();
                                }
                            }
                        }
                        //downloadAllImages();
                        if (loadListener != null)
                            loadListener.onFinish();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void loadThumbnail(Stone stone, ImageView imageView) {
        Bitmap bitmap = thumbnailCache.get(stone.id);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else if (loadLocalThumbnail(getImagePath(stone.chaName, 0), stone.id, imageView) == null) {
            if (stone.identifyImageUrl != null) {
                downloadImageAsync(stone.id, stone.identifyImageUrl, getImagePath(stone.chaName, 0), imageView);
            }
        }
    }

    public static void loadBigImage(Stone stone, ImageView imageView) {
        if (bigImageCache != null && stone.chaName.equals(name)) {
            imageView.setImageBitmap(bigImageCache);
        } else if (loadLocalBigImage(getImagePath(stone.chaName, 0), imageView) == null) {
            if (stone.identifyImageUrl != null) {
                downloadImageAsync(stone.id, stone.identifyImageUrl, getImagePath(stone.chaName, 0), imageView);
            }
        }
    }

    public static void putThumbnailCache(int index, Bitmap bitmap) {
        thumbnailCache.put(index, bitmap);
    }


    public static Bitmap toBitmap(File file, int width) {
        if (file.exists()) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = null;
            try {
                FileInputStream stream = new FileInputStream(file);
                BitmapFactory.decodeStream(stream, null, options);
                stream.close();
                options.inJustDecodeBounds = false;
                options.inSampleSize = calculateSampleSize(options.outWidth, width);
                stream = new FileInputStream(file);
                bitmap = BitmapFactory.decodeStream(stream, null, options);
                if (bitmap != null)
                    Log.i("666", bitmap.getWidth() + "x" + bitmap.getHeight() + "-" + bitmap.getByteCount() + "-" + bitmap.getAllocationByteCount());
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        } else {
            return null;
        }
    }

    private static int calculateSampleSize(int width, int targetWidth) {
        if (targetWidth < 0) return 1;
        int sampleSize = 1;
        while (width > targetWidth) {
            sampleSize = sampleSize << 1;
            width = width >> 1;
        }
        return sampleSize;
    }

    private static Bitmap loadLocalImage(String path, ImageView imageView) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int width;
        if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            width = imageView.getContext().getResources().getDisplayMetrics().widthPixels;
            Log.i("666", width + "");
        } else {
            width = params.width;
        }
        return toBitmap(new File(path), width);
    }

    private static Bitmap loadLocalBigImage(String path, ImageView imageView) {
        Bitmap bitmap = loadLocalImage(path, imageView);
        if (bitmap != null) {
            bigImageCache = bitmap;
            imageView.setImageBitmap(bitmap);
        }
        return bitmap;
    }

    private static Bitmap loadLocalThumbnail(String path, int index, ImageView imageView) {
        Bitmap bitmap = loadLocalImage(path, imageView);
        if (bitmap != null) {
            thumbnailCache.put(index, bitmap);
            imageView.setImageBitmap(bitmap);
        }
        return bitmap;
    }

    private static void downloadImageAsync(int index, String url, String path, ImageView imageView) {
        File fileBigImage = new File(bigImageDir);
        File fileIdentify = new File(identifyDir);
        if ((fileBigImage.exists() || fileBigImage.mkdirs()) && (fileIdentify.exists() || fileIdentify.mkdirs())) {
            ImageDownloadTask task = new ImageDownloadTask(index, url, path, imageView);
            task.execute();
        }
    }

    public static String getImagePath(String name, int type) {
        if (type == 0) {
            return bigImageDir + '/' + name + ".jpg";
        } else {
            return identifyDir + '/' + name + ".jpg";
        }
    }

    public static void clearBigImageCache() {
        bigImageCache = null;
        name = null;
    }

    public static void onLowMemory() {
        thumbnailCache.clear();
        clearBigImageCache();
    }
}