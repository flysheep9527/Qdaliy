package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/6/8.
 * Describe:
 */
public class Comment {
    private long id;
    private String content;
    private String image;
    @SerializedName("praise_count")
    private int praiseCount;
    private int perfect;
    private Author author;

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getImage() {
        return image;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public int getPerfect() {
        return perfect;
    }

    public Author getAuthor() {
        return author;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public void setPerfect(int perfect) {
        this.perfect = perfect;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
