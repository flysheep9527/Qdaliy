package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/6/8.
 * Describe:
 */
public class Author {
    private long id;
    private String description;
    private String avatar;
    private String name;
    @SerializedName("background_image")
    private String bgImage;

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBgImage(String bgImage) {
        this.bgImage = bgImage;
    }
}
