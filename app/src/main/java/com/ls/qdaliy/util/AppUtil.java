package com.ls.qdaliy.util;

import com.ls.qdaliy.base.AppContext;

import java.util.List;

/**
 * Created by asus on 2018/5/26.
 * Describe:
 */
public class AppUtil {
    public static int dip2px(float dpValue) {
        final float scale = AppContext.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = AppContext.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static String mergeString(List<String> param) {
        StringBuilder builder = new StringBuilder();
        for (String p : param) {
            builder.append(p);
            builder.append(",");
        }
        return builder.toString();
    }
}
