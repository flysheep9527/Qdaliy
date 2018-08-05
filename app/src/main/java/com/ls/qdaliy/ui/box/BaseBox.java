package com.ls.qdaliy.ui.box;

import android.view.View;

/**
 * Created by asus on 2018/5/27.
 * Describe:
 */
public interface BaseBox {
    <T> void onBind(T t, int position);
    void onBoxClick(View view);
}
