package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/5/24.
 * Describe:
 */
public class Feed {
    @SerializedName("image")
    private String imageLink;
    private int type;
    private Post post;

    public String getImageLink() {
        return imageLink;
    }

    public int getType() {
        return type;
    }

    public Post getPost() {
        return post;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
