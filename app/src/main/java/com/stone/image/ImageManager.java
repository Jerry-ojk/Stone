package com.stone.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stone.Data;
import com.stone.R;
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
    public static String IMAGE_ROOT = Environment.getExternalStorageDirectory() + "/stone_image/";
    private static String bigImageDir = IMAGE_ROOT + "image";
    private static String identifyDir = IMAGE_ROOT + "identify";
    private static ArrayCache thumbnailCache = new ArrayCache(56);
    public static Bitmap bigImageCache = null;
    public static String name = null;
    public static final int TYPE_THUMBNAIL = 0;
    public static final int TYPE_BIGIMAGE = 1;


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
                                BmobFile bmobFile = picture.identifyImage;
                                if (bmobFile != null) {
                                    stone.identifyImageUrl = bmobFile.getUrl();
                                }
                                bmobFile = picture.bigImage;
                                if (bmobFile != null) {
                                    stone.bigImageUrl = bmobFile.getUrl();
                                }
                                bmobFile = picture.video;
                                if (bmobFile != null) {
                                    stone.videoUrl = bmobFile.getUrl();
                                    //Log.i("666", stone.chaName + stone.videoUrl);
                                }
                            }
                        }
                        //downloadAllImages();
                    }
                } else {
                    e.printStackTrace();
                }
                if (loadListener != null)
                    loadListener.onFinish();
            }
        });
    }

    public static void loadThumbnail(Stone stone, ImageView imageView) {
        Bitmap bitmap = thumbnailCache.get(stone.id);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setImageResource(R.drawable.ic_empty_photo);
        if (loadLocalBigImageAsync(stone, TYPE_THUMBNAIL, imageView)) {
            return;
        }
        if (stone.identifyImageUrl != null) {
            downloadImageAsync(stone.id, stone.identifyImageUrl, getImagePath(stone.chaName, 0), imageView);
        }
    }

    public static void loadBigImage(Stone stone, ImageView imageView) {
        if (bigImageCache != null && stone.chaName.equals(name)) {
            imageView.setImageBitmap(bigImageCache);
            return;
        }
        imageView.setImageResource(R.drawable.ic_empty_photo);
        if (loadLocalBigImageAsync(stone, TYPE_BIGIMAGE, imageView)) {
            return;
        }
        if (stone.identifyImageUrl != null) {
            downloadImageAsync(stone.id, stone.identifyImageUrl, getImagePath(stone.chaName, 0), imageView);
        }
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
                //if (bitmap != null)
                //Log.i("666", bitmap.getWidth() + "x" + bitmap.getHeight() + "-" + bitmap.getByteCount() + "-" + bitmap.getAllocationByteCount());
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
        while (width > 1.6 * targetWidth) {
            sampleSize = sampleSize << 1;
            width = width >> 1;
        }
        return sampleSize;
    }

    public static Bitmap loadLocalImage(String path, ImageView imageView) {
        ViewGroup.LayoutParams params = imageView.getLayoutParams();
        int width;
        if (params.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            width = imageView.getContext().getResources().getDisplayMetrics().widthPixels;
            //Log.i("666", width + "");
        } else {
            width = params.width;
        }
        return toBitmap(new File(path), width);
    }


    public static void putThumbnailCache(int index, Bitmap bitmap) {
        thumbnailCache.put(index, bitmap);
    }

    public static void putBigImageCache(String name, Bitmap bitmap) {
        ImageManager.name = name;
        bigImageCache = bitmap;
    }


    private static Bitmap loadLocalThumbnail(String path, int index, ImageView imageView) {
        Bitmap bitmap = loadLocalImage(path, imageView);
        if (bitmap != null) {
            thumbnailCache.put(index, bitmap);
            imageView.setImageBitmap(bitmap);
        }
        return bitmap;
    }

    private static boolean loadLocalBigImageAsync(Stone stone, int type, ImageView imageView) {
        File file = new File(getImagePath(stone.chaName, TYPE_BIGIMAGE));
        if (file.exists()) {
            ImageLoadTask task = new ImageLoadTask(stone, type, imageView);
            imageView.setTag(task);
            task.execute();
            return true;
        } else {
            return false;
        }

    }

    private static void downloadImageAsync(int index, String url, String path, ImageView imageView) {
        File fileBigImage = new File(bigImageDir);
        File fileIdentify = new File(identifyDir);
        if ((fileBigImage.exists() || fileBigImage.mkdirs()) && (fileIdentify.exists() || fileIdentify.mkdirs())) {
            //Log.i("666", "下载图片");
            ImageDownloadTask task = new ImageDownloadTask(index, url, path, imageView);
            imageView.setTag(task);
            task.execute();
        }
    }

    public static String getImagePath(String name, int type) {
//        if (type == TYPE_THUMBNAIL) {
//            return bigImageDir + '/' + name + ".jpg";
//        } else {
//            return identifyDir + '/' + name + ".jpg";
//        }
        return bigImageDir + '/' + name + ".jpg";
    }

    public static void clearBigImageCache() {
        bigImageCache = null;
        name = null;
    }

    //低内存时准备回收bitmap空间
    public static void clearCache() {
        thumbnailCache.clear();
        clearBigImageCache();
    }

    public static Bitmap getBitmapFromImageView(ImageView imageView) {
        return ((BitmapDrawable) imageView.getDrawable()).getBitmap();
    }
}