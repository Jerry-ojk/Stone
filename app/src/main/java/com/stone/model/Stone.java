package com.stone.model;

public class Stone {
    public int id = -1;
    public String chaName;
    public String engName;
    //化学式
    public String formula;
    //晶系
    public String crystalSystem;
    //均非性
    public String uniformity;
    //反射率
    public String reflectivity;
    //硬度
    public String hardness;

    public String reflectColor;

    //反射视旋转色散DRr
    public String DRr;

    //内反射
    public String internalReflection;


    //矿物成因产状形态特征及伴生矿物
    public String features;
    //主要鉴定特征 Main identification characteristics
    public String mic;

    // 1 代表均质，0代表非均质
    public int uniformOrNot;
}
