package com.stone;

import android.content.Context;
import android.content.res.AssetManager;

import com.stone.model.Stone;
import com.stone.model.StoneUnUniform;
import com.stone.model.StoneUniform;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Data {
    public static final List<Stone> STONE_LIST = new ArrayList<>(56);

    public static void loadData(Context context) {
        try (InputStream inputStream = context.getAssets().open("ununiform.csv", AssetManager.ACCESS_STREAMING);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lines = line.split("#");
                if (lines.length == 18) {
                    StoneUnUniform stone = new StoneUnUniform(lines);
                    stone.id = Data.STONE_LIST.size();
                    Data.STONE_LIST.add(stone);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (InputStream inputStream = context.getAssets().open("uniform.csv", AssetManager.ACCESS_STREAMING);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lines = line.split("#");
                if (lines.length == 13) {
                    StoneUniform stone = new StoneUniform(lines);
                    stone.id = Data.STONE_LIST.size();
                    Data.STONE_LIST.add(stone);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StoneUnUniform parseStoneUnUniform(String line) {
        String[] lines = line.split("#");
        StoneUnUniform stone = null;
        if (lines.length == 18) {
            stone = new StoneUnUniform(lines);
            stone.id = Data.STONE_LIST.size();
            Data.STONE_LIST.add(stone);
        }
        return stone;
    }

    public static Stone findStoneById(int id) {
        for (Stone stone : STONE_LIST) {
            if (stone.id == id) return stone;
        }
        return null;
    }

    public static Stone findStoneByName(String name) {
        for (Stone stone : STONE_LIST) {
            if (stone.chaName.equals(name)) return stone;
        }
        return null;
    }
}
