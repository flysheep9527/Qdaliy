package com.ls.qdaliy.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ls.qdaliy.base.AppContext;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by asus on 2018/7/21.
 * Describe: 管理搜索记录
 */
public class SearchRecordUtil {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private static final String NAME = "search_record";
    private static final String KEY = "record";
    private Gson mGson;

    @SuppressLint("CommitPrefEdits")
    public SearchRecordUtil() {
        mPreferences = AppContext.getInstance().getSharedPreferences(NAME, Activity.MODE_PRIVATE);
        mEditor = mPreferences.edit();
        mGson = new Gson();
    }

    public void addRecord(String record) {
        Set<String> recordList;
        String jsonStr = mPreferences.getString(KEY, null);
        if (null == jsonStr) {
            recordList = new HashSet<>();
            recordList.add(record);

        } else {
            recordList = mGson.fromJson(jsonStr, new TypeToken<HashSet<String>>() {
            }.getType());
            recordList.add(record);

        }
        jsonStr = mGson.toJson(recordList);
        mEditor.clear();
        mEditor.putString(KEY, jsonStr);
        mEditor.apply();
    }

    public List<String> getRecord() {
        String jsonStr = mPreferences.getString(KEY, null);
        if (null == jsonStr) {
            return new ArrayList<>();
        } else {
            return mGson.fromJson(jsonStr, new TypeToken<ArrayList<String>>() {
            }.getType());
        }
    }

    public void clearRecord() {
        mEditor.clear();
        mEditor.apply();
    }
}
