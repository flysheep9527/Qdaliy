package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/6/17.
 * Describe:
 */
public class Paper {
    private long id;
    private String title;
    private String description;
    @SerializedName("record_count")
    private long recordCount;
    @SerializedName("comment_count")
    private int commentCount;
    @SerializedName("comment_status")
    private boolean commentStatus;
    @SerializedName("praise_count")
    private int praiseCount;
    @SerializedName("publish_time")
    private String publishTime;
    @SerializedName("image")
    private String imageLink;
    @SerializedName("post_id")
    private long postId;
    @SerializedName("page_style")
    private int pageStyle;
    @SerializedName("film_length")
    private String filmLength;
    @SerializedName("datatype")
    private String dataType;
    private Category category;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public boolean isCommentStatus() {
        return commentStatus;
    }

    public int getPraiseCount() {
        return praiseCount;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getImageLink() {
        return imageLink;
    }

    public long getPostId() {
        return postId;
    }

    public int getPageStyle() {
        return pageStyle;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public String getDataType() {
        return dataType;
    }

    public Category getCategory() {
        return category;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setCommentStatus(boolean commentStatus) {
        this.commentStatus = commentStatus;
    }

    public void setPraiseCount(int praiseCount) {
        this.praiseCount = praiseCount;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public void setPageStyle(int pageStyle) {
        this.pageStyle = pageStyle;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
