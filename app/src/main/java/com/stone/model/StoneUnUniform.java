package com.stone.model;

public class StoneUnUniform extends Stone {
    public static final String STONE_ID = "STONE_ID";
    //相符Ps
    public String Ps;

    //旋向Rs
    public String Rs;

    //非均质视旋转角Ar
    public String Ar;
    //非均质视旋转色散DAr
    public String DAr;

    //反射视旋转色散DRr
    public String DRr;

    //双反射及反射多色性
    public String doubleReflectColor;

    public StoneUnUniform() {

    }

    public StoneUnUniform(int id) {
        this.id = id;
    }

    public StoneUnUniform(String[] lines) {
        if (lines.length == 18) {
            chaName = lines[0];
            engName = lines[1];
            formula = lines[2];
            crystalSystem = lines[3];
            uniformity = lines[4];
            reflectivity = lines[5];
            hardness = lines[6];
            reflectColor = lines[7];
            doubleReflectColor = lines[8];
            Ar = lines[9];
            DAr = lines[10];
            Rs = lines[11];
            Ps = lines[12];
            DRr = lines[13];
            DRr = lines[14];
            internalReflection = lines[15];
            features = lines[16];
            mic = lines[17];
        }
    }
}
