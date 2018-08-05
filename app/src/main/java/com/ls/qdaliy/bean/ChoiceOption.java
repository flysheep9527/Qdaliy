package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/6/18.
 * Describe: lab 选择的选项
 */
public class ChoiceOption{
    private long id;
    private String title;
    @SerializedName("option_pic_url")
    private String optionPicUrl;
    private int score;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOptionPicUrl() {
        return optionPicUrl;
    }

    public int getScore() {
        return score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOptionPicUrl(String optionPicUrl) {
        this.optionPicUrl = optionPicUrl;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
