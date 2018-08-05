package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by asus on 2018/6/25.
 * Describe: tots 中的二选一问题
 */
public class GenderQuestion {
    private long id;
    private String content;
    private int genre;
    private int position;
    @SerializedName("background_pic_url")
    private String bgPicUrl;
    private List<Option> options;

    public class Option{
        private long id;
        private String title;
        private String content;
        @SerializedName("option_pic_url")
        private String optionPicUrl;

        public long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getOptionPicUrl() {
            return optionPicUrl;
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

        public void setOptionPicUrl(String optionPicUrl) {
            this.optionPicUrl = optionPicUrl;
        }
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getGenre() {
        return genre;
    }

    public int getPosition() {
        return position;
    }

    public String getBgPicUrl() {
        return bgPicUrl;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setBgPicUrl(String bgPicUrl) {
        this.bgPicUrl = bgPicUrl;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }
}
