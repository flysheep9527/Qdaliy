package com.ls.qdaliy.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.ls.qdaliy.bean.Feed;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by asus on 2018/5/29.
 * Describe: 主要用于解析 Http 响应内容
 */
public class JsonParseUtil {
    private static Gson gson = null;
    private JsonElement mResponse;

    public JsonParseUtil(InputStream input) throws JsonIOException {
        if(null == gson){
            gson = new Gson();
        }
        Reader reader = new InputStreamReader(input);
        mResponse = new JsonParser().parse(reader).getAsJsonObject()
                .get("response");
    }

    public static Gson getGson() {
        return gson;
    }

    public JsonElement getResponse(){
        return this.mResponse;
    }

    public LinkedList<Feed> getFeeds(String key){
        return gson.fromJson(mResponse.getAsJsonObject().get(key), new TypeToken<LinkedList<Feed>>() {
        }.getType());
    }

    public <T> T getData(String key, Class<T> clazz){
        return gson.fromJson(mResponse.getAsJsonObject().get(key), clazz);
    }

    public  List<String> getList(String key){
        return gson.fromJson(mResponse.getAsJsonObject().get(key), new TypeToken<ArrayList<String>>() {
        }.getType());
    }

    public <T>List<T> getList(String key, Type type){
        return gson.fromJson(mResponse.getAsJsonObject().get(key), type);
    }
}
