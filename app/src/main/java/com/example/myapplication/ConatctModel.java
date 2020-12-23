package com.example.myapplication;

public class ConatctModel{

    private String name;
    private int imgId;

    public ConatctModel(String name, int imgId) {
        this.name = name;
        this.imgId = imgId;
    }
    public String getName() {
        return name;
    }
    public void setName(String description) {
        this.name = description;
    }
    public int getImgId() {
        return imgId;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
