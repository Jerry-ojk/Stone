package com.stone;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.stone.model.Stone;
import com.stone.model.StoneNotUniformity;
import com.stone.model.StoneUniformity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

//
public class Data {
    public static final List<Stone> STONE_LIST = new ArrayList<>(56);

    public static void loadData(Context context) {
        try (InputStream inputStream = context.getAssets().open("notJunZhi.csv", AssetManager.ACCESS_STREAMING);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //用 | 分割  |需要转义为 \||
                String[] lines = line.split("\\|");
                if (lines.length == 18) {
                    StoneNotUniformity stone = new StoneNotUniformity(lines);
                    stone.id = Data.STONE_LIST.size();
                    Data.STONE_LIST.add(stone);
                    Log.i("666", STONE_LIST.get(STONE_LIST.size() - 1).chaName);
                } else {
                    Log.i("666", lines.length + "");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (InputStream inputStream = context.getAssets().open("junzhi.csv", AssetManager.ACCESS_STREAMING);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //用 | 分割  |需要转义为 \||
                String[] lines = line.split("\\|");
                if (lines.length == 13) {
                    StoneUniformity stone = new StoneUniformity(lines);
                    stone.id = Data.STONE_LIST.size();
                    Data.STONE_LIST.add(stone);
                    Log.i("666", STONE_LIST.get(STONE_LIST.size() - 1).chaName);
                } else {
                    Log.i("666", lines.length + "");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
