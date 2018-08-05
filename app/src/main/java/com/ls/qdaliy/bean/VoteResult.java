package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus on 2018/6/14.
 * Describe: 投票的结果返回
 */
public class VoteResult {
    @SerializedName("comment_count")
    private int commentCount;
    private Option option;
    @SerializedName("everyones_attitude")
    private List<Attitude> attitudes;

    public int getCommentCount() {
        return commentCount;
    }

    public Option getOption() {
        return option;
    }

    public List<Attitude> getAttitudes() {
        return attitudes;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public void setAttitudes(List<Attitude> attitudes) {
        this.attitudes = attitudes;
    }

    public class Option{
        private long id;
        @SerializedName("image")
        private String imageLink;
        private String content;
        private String title;

        public long getId() {
            return id;
        }

        public String getImageLink() {
            return imageLink;
        }

        public String getContent() {
            return content;
        }

        public String getTitle() {
            return title;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setImageLink(String imageLink) {
            this.imageLink = imageLink;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class Attitude{
        private long id;
        private String content;
        private int percent;
        private boolean selected;

        public long getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public int getPercent() {
            return percent;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setId(long id) {
            this.id = id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
}
