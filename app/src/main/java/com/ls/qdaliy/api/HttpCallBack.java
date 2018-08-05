package com.ls.qdaliy.api;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by asus on 2018/5/23.
 * Describe:
 */
public interface HttpCallBack {
    void onFailure(int code);
    void onResponse(InputStream inputStream) throws IOException;
}
