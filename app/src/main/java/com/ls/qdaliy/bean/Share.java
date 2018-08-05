package com.ls.qdaliy.bean;

/**
 * Created by asus on 2018/5/24.
 * Describe:
 */
public class Share {
    private String url;
    private String title;
    private String text;
    private String image;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getImage() {
        return image;
    }
}
