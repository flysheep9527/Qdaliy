package com.ls.qdaliy.daliy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.ls.qdaliy.api.HttpApi;
import com.ls.qdaliy.api.HttpCallBack;
import com.ls.qdaliy.bean.Feed;
import com.ls.qdaliy.bean.NewsCategory;
import com.ls.qdaliy.util.JsonParseUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by asus on 2018/5/26.
 * Describe:
 */
public class LaunchPresenter implements LaunchContract.Presenter {

    private LaunchContract.View mView;
    private final List<Feed> mNews;
    private final List<Feed> mBanners;
    private final List<Feed> mLabs;
    private String mHomeLastKey = "0";
    private String mLabLastKey = "0";

    private static final String TAG = "LaunchPresenter";

    LaunchPresenter(LaunchContract.View view) {
        this.mView = view;
        mNews = new LinkedList<>();
        mBanners = new ArrayList<>();
        mLabs = new LinkedList<>();
    }

    @Override
    public void start() {
        mView.initHomeList(mNews, mBanners);
        mView.initLabList(mLabs);
        getHomeList(mHomeLastKey);
        getLabList(mLabLastKey);
        getNewsCategory();
    }

    @Override
    public void getHomeList(final String lastKey) {
        HttpApi.getHomeList(lastKey, new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                if(mNews.size() == 0){
                    mView.showHomeError(true);
                    mView.updateHomeList();
                }
                Log.d(TAG, String.format("http 请求失败, 错误代码:%s", String.valueOf(code)));
            }

            @Override
            public void onResponse(InputStream inputStream) {
                JsonParseUtil jsonParseUtil = new JsonParseUtil(inputStream);
                mHomeLastKey = jsonParseUtil.getData("last_key", String.class);
                LinkedList<Feed> fs = jsonParseUtil.getFeeds("feeds");

                if (!lastKey.equals("0") || mNews.size() == 0) {
                    mNews.addAll(fs);
                } else {
                    List<Feed> newFeeds = getNewFeeds(mNews.get(0).getPost().getId(), fs);
                    if (newFeeds.size() != 0) {
                        mNews.addAll(0, newFeeds);
                    }
                }
                mView.showHomeError(false);
                mView.updateHomeList();
            }
        });
    }

    @Override
    public void getLabList(final String lastKey) {
        HttpApi.getPaper(lastKey, new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                if(mLabs.size() == 0){
                    mView.showLabError(true);
                    mView.updateLabList();
                }
                Log.d(TAG, String.format("http 请求失败, 错误代码:%s", String.valueOf(code)));
            }

            @Override
            public void onResponse(InputStream inputStream) throws JsonIOException {
                JsonParseUtil jsonParseUtil = new JsonParseUtil(inputStream);
                mLabLastKey = jsonParseUtil.getData("last_key", String.class);
                LinkedList<Feed> ls = jsonParseUtil.getFeeds("feeds");

                if (!lastKey.equals("0") || mLabs.size() == 0) {
                    mLabs.addAll(ls);
                } else {
                    List<Feed> newLabs = getNewFeeds(mLabs.get(0).getPost().getId(), ls);
                    if (newLabs.size() != 0) {
                        mLabs.addAll(0, newLabs);
                    }
                }
                mView.showLabError(false);
                mView.updateLabList();
            }
        });
    }

    /**
     * 当下拉刷新的时候, 会返回一个 list, 但这个 list 里面可能会有的内容已经加载出来了,
     * 需要获取没有加载出来的内容
     *
     * @param id       已有内容中的第一个 post.id
     * @param feedList 刷新获取的内容
     * @return 一个只包含新内容的 list, 如果没有, 返回 null
     */
    private LinkedList<Feed> getNewFeeds(long id, LinkedList<Feed> feedList) {
        LinkedList<Feed> newFeeds = new LinkedList<>();
        for (Feed f : feedList) {
            if (id == f.getPost().getId()) {
                break;
            }
            newFeeds.add(f);
        }
        return newFeeds;
    }

    /**
     * @param perItems 提前几个 item 加载下一页数据
     * @return 一个 RecyclerView.OnScrollListener
     */
    @Override
    public RecyclerView.OnScrollListener getMoreListListener(final int perItems, final int tag) {
        return new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.d(TAG, "state change");
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + perItems >= recyclerView.getAdapter().getItemCount()) {
                    switch (tag) {
                        case LaunchActivity.HOME:
                            getHomeList(mHomeLastKey);
                            break;
                        case LaunchActivity.LAB:
                            Log.d(TAG, "start get lab_select list");
                            Log.d(TAG, String.format("item count od item: %d", recyclerView.getAdapter().getItemCount()));
                            getLabList(mLabLastKey);
                            break;
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (dy < 0) {
                    mView.showFAB();
                }else{
                    mView.hideFAB();
                }
            }
        };
    }

    private void getNewsCategory() {
        HttpApi.getNews(new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                Log.d(TAG, "fail to get new category!");
            }

            @Override
            public void onResponse(InputStream inputStream) {
                JsonParseUtil json = new JsonParseUtil(inputStream);
                JsonArray jsonArray = json.getResponse().getAsJsonArray();
                List<NewsCategory> newsCategories = new ArrayList<>();

                for (JsonElement element : jsonArray) {
                    newsCategories.add(JsonParseUtil.getGson().fromJson(element, NewsCategory.class));
                }
                mView.initSideMenu(newsCategories);
            }
        });
    }
}