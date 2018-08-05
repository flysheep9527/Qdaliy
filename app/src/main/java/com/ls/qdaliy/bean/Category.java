package com.ls.qdaliy.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by asus on 2018/5/24.
 * Describe:
 */
public class Category {
    private int id;
    private String title;
    private String normal;
    @SerializedName("normal_hl")
    private String normalHl;
    @SerializedName("image_lab")
    private String imageLab;
    @SerializedName("image_experiment")
    private String imageExperiment;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNormal() {
        return normal;
    }

    public String getNormalHl() {
        return normalHl;
    }

    public String getImageLab() {
        return imageLab;
    }

    public String getImageExperiment() {
        return imageExperiment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public void setNormalHl(String normalHl) {
        this.normalHl = normalHl;
    }

    public void setImageLab(String imageLab) {
        this.imageLab = imageLab;
    }

    public void setImageExperiment(String imageExperiment) {
        this.imageExperiment = imageExperiment;
    }
}
