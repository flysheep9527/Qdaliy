package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus on 2018/5/31.
 * Describe:
 */
public class ArticleDetail {
    private long id;
    private String body;
    @SerializedName("js")
    private List<String> jsLink;
    @SerializedName("css")
    private List<String> cssLink;
    @SerializedName("image")
    private List<String> imageLink;

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public List<String> getJsLink() {
        return jsLink;
    }

    public List<String> getCssLink() {
        return cssLink;
    }

    public List<String> getImageLink() {
        return imageLink;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setJsLink(List<String> jsLink) {
        this.jsLink = jsLink;
    }

    public void setCssLink(List<String> cssLink) {
        this.cssLink = cssLink;
    }

    public void setImageLink(List<String> imageLink) {
        this.imageLink = imageLink;
    }
}
