package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/5/24.
 * Describe:
 */
public class Column {
    private int id;
    private String  name;
    private String description;
    @SerializedName("subscribe_status")
    private boolean subscribeStatus;
    private String icon;
    private String image;
    @SerializedName("image_large")
    private String imageLarge;
    @SerializedName("content_provider")
    private String contentProvider;
    @SerializedName("show_type")
    private int showType;
    private int genre;
    @SerializedName("subscriber_num")
    private int subscriberNum;
    @SerializedName("post_count")
    private int postCount;
    @SerializedName("sort_time")
    private String sortTime;
    @SerializedName("column_tag")
    private String columnTag;
    private Integer location;
    private Share share;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isSubscribeStatus() {
        return subscribeStatus;
    }

    public String getIcon() {
        return icon;
    }

    public String getImage() {
        return image;
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public String getContentProvider() {
        return contentProvider;
    }

    public int getShowType() {
        return showType;
    }

    public int getGenre() {
        return genre;
    }

    public int getSubscriberNum() {
        return subscriberNum;
    }

    public int getPostCount() {
        return postCount;
    }

    public String getSortTime() {
        return sortTime;
    }

    public String getColumnTag() {
        return columnTag;
    }

    public Integer getLocation() {
        return location;
    }

    public Share getShare() {
        return share;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSubscribeStatus(boolean subscribeStatus) {
        this.subscribeStatus = subscribeStatus;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
    }

    public void setContentProvider(String contentProvider) {
        this.contentProvider = contentProvider;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public void setSubscriberNum(int subscriberNum) {
        this.subscriberNum = subscriberNum;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public void setSortTime(String sortTime) {
        this.sortTime = sortTime;
    }

    public void setColumnTag(String columnTag) {
        this.columnTag = columnTag;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public void setShare(Share share) {
        this.share = share;
    }
}
