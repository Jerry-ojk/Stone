package com.stone.model;

public class StoneUniform extends Stone {

    //反射视旋转角Rr
    public String Rr;
//    //反射视旋转色散DRr
//    public String DRr;


    public StoneUniform() {

    }

    public StoneUniform(String[] lines) {
        if (lines.length == 13) {
            chaName = lines[0];
            engName = lines[1];
            formula = lines[2];
            crystalSystem = lines[3];
            uniformity = lines[4];
            reflectivity = lines[5];
            hardness = lines[6];
            reflectColor = lines[7];
            Rr = lines[8];
            DRr = lines[9];
            internalReflection = lines[10];
            features = lines[11];
            mic = lines[12];
        }
    }
}
