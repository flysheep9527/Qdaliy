package com.ls.qdaliy.util;

import android.graphics.Color;

/**
 * Created by asus on 2018/7/7.
 * Describe: 好奇心研究所 "我说" 中评论 item 的背景颜色
 */
public enum ColorEnum {
    COLOR_1(Color.parseColor("#49c3cc")),
    COLOR_2(Color.parseColor("#f8a43d")),
    COLOR_3(Color.parseColor("#e76a60")),
    COLOR_4(Color.parseColor("#9eb95f")),
    COLOR_5(Color.parseColor("#83d0ab")),
    COLOR_6(Color.parseColor("#8e8cc0")),
    COLOR_7(Color.parseColor("#efa2a5"));

    private int color;

    ColorEnum(int color){
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
