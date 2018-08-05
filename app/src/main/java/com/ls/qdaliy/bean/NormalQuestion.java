package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus on 2018/6/17.
 * Describe: 1003, 1004 的问题
 */
public class NormalQuestion {
    private long id;
    private String content;
    @SerializedName("image")
    private String imageLink;
    private int genre;
    private List<ChoiceOption> options;

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getImageLink() {
        return imageLink;
    }

    public int getGenre() {
        return genre;
    }

    public List<ChoiceOption> getOptions() {
        return options;
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

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public void setOptions(List<ChoiceOption> options) {
        this.options = options;
    }
}
