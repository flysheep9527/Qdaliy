package com.ls.qdaliy.bean;

import java.util.List;

/**
 * Created by asus on 2018/6/14.
 * Describe: 投票的问题
 */
public class Question {
    private long id;
    private String title;
    private String content;
    private int genre;
    private List<Option> options;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getGenre() {
        return genre;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
