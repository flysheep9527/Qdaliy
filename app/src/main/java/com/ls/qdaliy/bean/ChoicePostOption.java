package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/6/17.
 * Describe: lab 选择 提交的选项
 */
public class ChoicePostOption {
    private long id;
    @SerializedName("question_id")
    private long questionId;
    private int score;

    public ChoicePostOption(long id, long questionId, int score) {
        this.id = id;
        this.questionId = questionId;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public long getQuestionId() {
        return questionId;
    }

    public int getScore() {
        return score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
