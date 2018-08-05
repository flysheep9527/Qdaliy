package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/6/14.
 * Describe: 投票的选项
 */
public class Option {
    private long id;
    private String content;
    @SerializedName("image")
    private String imageLink;
    @SerializedName("praise_count")
    private long praiseCount;
    private int prefect;

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public long getPraiseCount() {
        return praiseCount;
    }

    public int getPrefect() {
        return prefect;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setPraiseCount(long praiseCount) {
        this.praiseCount = praiseCount;
    }

    public void setPrefect(int prefect) {
        this.prefect = prefect;
    }
}
