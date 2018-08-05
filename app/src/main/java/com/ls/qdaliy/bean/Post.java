package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/5/24.
 * Describe:
 */
public class Post {
    private long id;
    private int genre;
    private String title;
    private String description;
    @SerializedName("publish_time")
    private String publishTime;
    private String image;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("comment_count")
    private int commentCount;
    @SerializedName("comment_status")
    private boolean commentStatus;
    @SerializedName("praise_count")
    private int praiseCount;
    @SerializedName("super_tag")
    private String superTag;
    @SerializedName("page_style")
    private int pageStyle;
    @SerializedName("post_id")
    private long postId;
    @SerializedName("appview")
    private String appView;
    @SerializedName("film_length")
    private String filmLength;
    @SerializedName("datatype")
    private String dataType;
    private Category category;
    private Column column;

    public long getId() {
        return id;
    }

    public int getGenre() {
        return genre;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public String getImage() {
        return image;
    }

    public String getStartTime() {
        return startTime;
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

    public String getSuperTag() {
        return superTag;
    }

    public int getPageStyle() {
        return pageStyle;
    }

    public long getPostId() {
        return postId;
    }

    public String getAppView() {
        return appView;
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

    public Column getColumn() {
        return column;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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

    public void setSuperTag(String superTag) {
        this.superTag = superTag;
    }

    public void setPageStyle(int pageStyle) {
        this.pageStyle = pageStyle;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public void setAppView(String appView) {
        this.appView = appView;
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

    public void setColumn(Column column) {
        this.column = column;
    }
}
