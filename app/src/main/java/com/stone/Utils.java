package com.stone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jerry on 2017/9/11
 */

public class Utils {

    public static void setStatusBarDark(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }


    public static String locateCity(Activity activity) {
        double longitude;
        double latitude;

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != 0 ||
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != 0) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return "允许权限后，点击重试";
        } else {
            Location location = ((LocationManager) activity.getSystemService(Context.LOCATION_SERVICE))
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location == null) return "定位失败";
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            return "经度" + longitude + "纬度" + latitude;
        }
    }

    public static Bitmap blurBitmap(Context context, Bitmap bitmap, float radius) {
        long a = System.currentTimeMillis();
        float scale = 15;
        Matrix m = new Matrix();
        m.setScale(1/scale, 1/scale);
        Bitmap blurBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
        bitmap.recycle();
        RenderScript rs = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(rs, blurBitmap);
        Allocation output = Allocation.createTyped(rs, input.getType());
        //创建一个模糊效果的RenderScript的工具对象
        //第二个参数Element相当于一种像素处理的算法，高斯模糊的话用这个就好
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        //设置渲染的模糊程度, 25f是最大模糊度
        script.setRadius(radius);
        // 设置blurScript对象的输入内存
        script.setInput(input);
        // 将输出数据保存到输出刚刚创建的输出内存中
        script.forEach(output);
        // 将数据填充到bitmap中
        output.copyTo(blurBitmap);
        //销毁它们释放内存
        input.destroy();
        output.destroy();
        script.destroy();
        Log.i("高斯模糊", "耗时" + (System.currentTimeMillis() - a) + "ms");
        return blurBitmap;
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static float dip2px(float dp) {
        return Resources.getSystem().getDisplayMetrics().density * dp;
    }

    public static List<Integer> findse(String string) {
        char[] chars = string.toCharArray();
        List<Integer> start_end = new ArrayList<>();
        for(int i=0;i<chars.length;i++){
            if(chars[i] == '$'){
                start_end.add(i+1);
            }else if(chars[i] == '@'){
                start_end.add(i);
            }
        }
        return start_end;
    }

    public static String fmt2src(String string){
        String str = string.replaceAll("[$@]","");
        return str;
    }
}
