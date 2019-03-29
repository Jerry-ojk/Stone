package com.stone.model;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class StonePicture extends BmobObject {
    public int id;
    public BmobFile thumbnail;
    public BmobFile bigImage;
    public String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BmobFile getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(BmobFile thumbnail) {
        this.thumbnail = thumbnail;
    }

    public BmobFile getBigImage() {
        return bigImage;
    }

    public void setBigImage(BmobFile bigImage) {
        this.bigImage = bigImage;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
