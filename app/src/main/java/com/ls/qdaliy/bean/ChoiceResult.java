package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/6/17.
 * Describe: 1003, 1004 的返回结果
 */
public class ChoiceResult {
    private String title;
    private String content;
    private String source;
    @SerializedName("result_pic")
    private String resultPic;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSource() {
        return source;
    }

    public String getResultPic() {
        return resultPic;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setResultPic(String resultPic) {
        this.resultPic = resultPic;
    }
}
