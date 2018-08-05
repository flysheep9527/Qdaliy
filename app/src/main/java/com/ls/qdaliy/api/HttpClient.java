package com.ls.qdaliy.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonIOException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by asus on 2018/5/23.
 * Describe:
 */
public class HttpClient {
    private final OkHttpClient mClient;
    private static HttpClient mHttpClient;

    private static final String TAG = "HttpClient";

    private HttpClient() {
        mClient = getClient();
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .readTimeout(3000, TimeUnit.MILLISECONDS)//获取数据并读取数据的超时时间
                .connectTimeout(3000, TimeUnit.MILLISECONDS)//连接超时时间
                .build();
    }

    public static HttpClient getInstance() {
        synchronized (HttpClient.class) {
            if (mHttpClient == null) {
                mHttpClient = new HttpClient();
            }
            return mHttpClient;
        }
    }

    public void doGet(String url, final HttpCallBack callBack) {
        Log.d(TAG, url);
        Request request = new Request.Builder()
                .url(url)
                .header("Accept-Encoding", "gzip")
                .addHeader("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 5.1.1; NX507J Build/LMY47V)")
                .build();

        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBack.onFailure(-1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.code() == 200 ) {
                    InputStream inputStream;
                    inputStream = Objects.requireNonNull(response.body()).byteStream();
                    try{
                        callBack.onResponse(inputStream);
                    } catch (JsonIOException e){
                        callBack.onFailure(-1);
                    }
                } else {
                    callBack.onFailure(response.code());
                }
            }
        });
    }

    public void doPost(String url, final HttpCallBack callBack, Map<String, String> data) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> d : data.entrySet()) {
            builder.add(d.getKey(), d.getValue());
        }
        FormBody formBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .header("Accept-Encoding", "gzip")
                .addHeader("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 5.1.1; NX507J Build/LMY47V)")
                .build();

        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callBack.onFailure(-1);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.code() == 200) {
                    InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
                    callBack.onResponse(inputStream);
                } else {
                    callBack.onFailure(response.code());
                }
            }
        });
    }
}
