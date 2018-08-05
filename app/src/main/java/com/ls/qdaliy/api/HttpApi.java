package com.ls.qdaliy.api;

import android.util.Log;

import java.util.Map;

/**
 * Created by asus on 2018/5/23.
 * Describe:
 */
public class HttpApi {
    private static final String HOST = "http://app3.qdaily.com/";
    private static final String NEWS = "app3/homes/left_sidebar.json";  // 新闻分类
    private static final String NEWS_LIST = "app3/categories/index/";  // 某类新闻列表
    private static final String HOME = "app3/homes/index/";  // 首页
    private static final String PAPER = "app3/papers/index/";  // 好奇心研究室
    private static final String COLUMNS = "app3/columns/all_columns_index/";  // 栏目中心
    private static final String COLUMNS_LIST = "app3/columns/index/";  // 某类栏目列表
    private static final String SEARCH = "app3/searches/post_highlighting_list.json?search=";  // 搜索

    private static final String TAG = "HttpApi";

    public static void getHomeList(String lastKey, HttpCallBack callBack){
        String url = HOST.concat(HOME).concat(lastKey).concat(".json");
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void getNews(HttpCallBack callBack){
        String url = HOST.concat(NEWS);
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void getNewsList(int id, String lastKey, HttpCallBack callBack){
        String url = HOST.concat(NEWS_LIST).concat(String.valueOf(id)).concat("/")
                .concat(lastKey).concat(".json");
        Log.d(TAG, String.format("news list url: %s", url));
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void getPaper(String lastKey, HttpCallBack callBack){
        String url = HOST.concat(PAPER).concat(lastKey);
        Log.d(TAG, String.format("url: %s", url));
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void getSearchResult(String searchKey, String lastKey, HttpCallBack callBack){
        String url = HOST.concat(SEARCH).concat(searchKey).concat("&last_key=").concat(lastKey);
        Log.w(TAG, url);
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void getColumns(String lastKey, HttpCallBack callBack){
        String url = HOST.concat(COLUMNS).concat(lastKey);
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void getColumnsList(int id, String lastKey, HttpCallBack callBack){
        String url = HOST.concat(COLUMNS_LIST).concat(String.valueOf(id)).concat("/")
                .concat(lastKey).concat(".json");
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void getDetail(long id, HttpCallBack callBack){
        String url = HOST.concat("/app3/articles/detail/").concat(String.valueOf(id).concat(".json"));
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void getPaperOne(long id, HttpCallBack callBack){
        String url = HOST.concat("app3/papers/detail/").concat(String.valueOf(id)).concat(".json");
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void postPaperZeroResult(HttpCallBack callBack, Map<String, String> data){
        String url = HOST.concat("app3/papers/done");
        HttpClient.getInstance().doPost(url, callBack, data);
    }

    public static void getChoice(String genre, long id, HttpCallBack callBack){
        String url;
        if(genre.equals("1003")){
            url = HOST.concat("app3/paper/choices/").concat(String.valueOf(id));
        }else{
            url = HOST.concat("app3/paper/whos/").concat(String.valueOf(id));
        }
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void postChoice(String genre, HttpCallBack callBack, Map<String, String> data){
        String url;
        if(genre.equals("1003")){
            url = HOST.concat("app3/paper/choices");
        }else{
            url = HOST.concat("app3/paper/whos");
        }
        HttpClient.getInstance().doPost(url, callBack, data);
    }

    public static void getTots(long id, HttpCallBack callBack){
        String url = HOST.concat("app3/paper/tots/").concat(String.valueOf(id));
        HttpClient.getInstance().doGet(url, callBack);
    }

    public static void postTots(Map<String, String> data, HttpCallBack callBack){
        String url = HOST.concat("app3/paper/tots");
        HttpClient.getInstance().doPost(url, callBack, data);
    }

    public static void getColumnInfo(int id, HttpCallBack callBack){
        String url = HOST.concat("app3/columns/info/").concat(String.valueOf(id)).concat(".json");
        HttpClient.getInstance().doGet(url, callBack);
    }
}
