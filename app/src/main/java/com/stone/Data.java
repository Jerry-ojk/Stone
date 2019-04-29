package com.stone;

import android.content.Context;
import android.content.res.AssetManager;

import com.stone.model.Stone;
import com.stone.model.StoneUnUniform;
import com.stone.model.StoneUniform;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Data {
    public static final ArrayList<Stone> STONE_LIST = new ArrayList<>(56);
    public static final ArrayList<Stone> COLLECT_LIST = new ArrayList<>();

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

        final int size = STONE_LIST.size();
        try (FileInputStream stream = context.openFileInput("favour")) {
            int index = stream.read();
            while (index >= 0 && index < size) {
                COLLECT_LIST.add(STONE_LIST.get(index));
                index = stream.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void collectStone(Stone stone, boolean isCollect) {
        if (isCollect && !COLLECT_LIST.contains(stone)) {
            COLLECT_LIST.add(stone);
        } else {
            COLLECT_LIST.remove(stone);
        }
    }

    public static void saveData(Context context) {
        final int size = COLLECT_LIST.size();
        try (FileOutputStream stream = context.openFileOutput("favour", Context.MODE_PRIVATE)) {
            for (int i = 0; i < size; i++) {
                stream.write(COLLECT_LIST.get(i).id);
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
//        for (Stone stone : STONE_LIST) {
//            if (stone.id == id) return stone;
//        }
        return STONE_LIST.get(id);
    }

    public static Stone findStoneByName(String name) {
        for (Stone stone : STONE_LIST) {
            if (stone.chaName.equals(name)) return stone;
        }
        return null;
    }
}
