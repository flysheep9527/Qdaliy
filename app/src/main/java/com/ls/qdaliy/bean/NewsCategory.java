package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/7/18.
 * Describe:
 */
public class NewsCategory {
    private int id;
    private String title;
    private String normal;
    @SerializedName("white_icon")
    private String whiteIcon;
    @SerializedName("black_icon")
    private String blackIcon;

    public int getId() {
        return id;
    }

    public String getTltle() {
        return title;
    }

    public String getNormal() {
        return normal;
    }

    public String getWhiteIcon() {
        return whiteIcon;
    }

    public String getBlackIcon() {
        return blackIcon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTltle(String title) {
        this.title = title;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public void setWhiteIcon(String whiteIcon) {
        this.whiteIcon = whiteIcon;
    }

    public void setBlackIcon(String blackIcon) {
        this.blackIcon = blackIcon;
    }
}
