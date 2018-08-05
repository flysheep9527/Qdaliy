package com.ls.qdaliy.daliy;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.ls.qdaliy.api.HttpApi;
import com.ls.qdaliy.api.HttpCallBack;
import com.ls.qdaliy.bean.Column;
import com.ls.qdaliy.bean.Feed;
import com.ls.qdaliy.util.JsonParseUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by asus on 2018/7/24.
 * Describe:
 */
public class FeedPresenter implements FeedContract.Presenter {

    private FeedContract.View mView;
    private String mNewsListLastKey = "0";
    private String mColumnCenterLastKey = "0";
    private String mColumnListLastKey = "0";
    private boolean mHasMoreColumns;
    private List<Feed> mFeedList;
    private List<Column> mColumnCenters;
    private List<Object> mColumnHeaderList;
    private List<Object> mColumnCenterFooterList;
    private int mTag;
    private int mFeedId;
    private boolean mIsLoadingMore = false;

    private static final String TAG = "FeedPresenter";

    FeedPresenter(FeedContract.View view) {
        this.mView = view;
        mFeedList = new LinkedList<>();
        mColumnCenters = new LinkedList<>();
        mColumnHeaderList = new ArrayList<>(2);
        mColumnCenterFooterList = new ArrayList<>(1);
    }

    public void getNewsList(final String lastKey) {
        HttpApi.getNewsList(mFeedId, lastKey, new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                Log.d(TAG, "fail to get news list");
            }

            @Override
            public void onResponse(InputStream inputStream) {
                JsonParseUtil json = new JsonParseUtil(inputStream);
                mNewsListLastKey = json.getData("last_key", String.class);

                List<Feed> feeds = json.getFeeds("feeds");
                if (!lastKey.equals("0") || mFeedList.size() == 0) {
                    mFeedList.addAll(feeds);
                } else {
                    List<Feed> newFeeds = getNewFeeds(mFeedList.get(0).getPost().getId(), feeds);
                    if (newFeeds.size() > 0) {
                        mFeedList.addAll(0, newFeeds);
                    }
                }
                mView.updateNewsList();
            }
        });
    }

    public void getColumns(String lastKey) {
        HttpApi.getColumns(lastKey, new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                Log.d(TAG, "fail to get column center!");
            }

            @Override
            public void onResponse(InputStream inputStream) {
                JsonParseUtil json = new JsonParseUtil(inputStream);
                mColumnCenterLastKey = json.getData("last_key", String.class);
                mHasMoreColumns = json.getData("has_more", Boolean.class);

                List<Column> columns = json.getList("columns", new TypeToken<LinkedList<Column>>() {
                }.getType());
                mColumnCenters.addAll(columns);
                Log.d(TAG, String.format("size of columns:%d", mColumnCenters.size()));

                mView.updateColumnCenter();
            }
        });
    }

    public void getColumnList(int id, final String lastKey) {
        HttpApi.getColumnsList(id, lastKey, new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                Log.d(TAG, "fail to gwt column list!");
            }

            @Override
            public void onResponse(InputStream inputStream) {
                JsonParseUtil json = new JsonParseUtil(inputStream);
                mColumnListLastKey = json.getData("last_key", String.class);

                List<Feed> feeds = json.getFeeds("feeds");
                if (!lastKey.equals("0") || mFeedList.size() == 0) {
                    mFeedList.addAll(feeds);
                } else {
                    List<Feed> newFeeds = getNewFeeds(mFeedList.get(0).getPost().getId(), feeds);
                    if (newFeeds.size() > 0) {
                        mFeedList.addAll(0, newFeeds);
                    }
                }

                mView.updateColumnList();
            }
        });
    }

    public void getColumnInfo() {
        HttpApi.getColumnInfo(mFeedId, new HttpCallBack() {
            @Override
            public void onFailure(int code) {
                Log.d(TAG, "fail to get column center info!");
            }

            @Override
            public void onResponse(InputStream inputStream) {
                JsonParseUtil json = new JsonParseUtil(inputStream);
                mColumnHeaderList.clear();
                Column column = json.getData("column", Column.class);
                mColumnHeaderList.add(column);

                mView.updateColumnList();
            }
        });
    }

    public RecyclerView.OnScrollListener getMoreListListener(final int perItems) {
        return new RecyclerView.OnScrollListener() {
            int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Log.d(TAG, String.format("adapter item count: %d", recyclerView.getAdapter().getItemCount()));
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + perItems >= recyclerView.getAdapter().getItemCount() && !mIsLoadingMore) {
                    mIsLoadingMore = true;
                    switch (mTag) {
                        case FeedActivity.TAG_NWE_LIST:
                            Log.d(TAG, "get more news!");
                            getNewsList(mNewsListLastKey);
                            break;
                        case FeedActivity.TAG_COLUMN_CENTER:
                            if (mHasMoreColumns) {
                                getColumns(mColumnCenterLastKey);
                            }
                            break;
                        case FeedActivity.TAG_COLUMN_LIST:
                            getColumnList(mFeedId, mColumnListLastKey);
                            Log.d(TAG, "get more column list!");
                            break;
                    }
                }
                mIsLoadingMore = false;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        };
    }

    private LinkedList<Feed> getNewFeeds(long id, List<Feed> feedList) {
        LinkedList<Feed> newFeeds = new LinkedList<>();
        for (Feed f : feedList) {
            if (id == f.getPost().getId()) {
                break;
            }
            newFeeds.add(f);
        }
        return newFeeds;
    }

    public void init(int tag, int feedId) {
        this.mTag = tag;
        this.mFeedId = feedId;
    }

    @Override
    public void start() {
        switch (mTag) {
            case FeedActivity.TAG_NWE_LIST:
                mView.initNewsList(mFeedList);
                getNewsList("0");
                break;
            case FeedActivity.TAG_COLUMN_CENTER:
                mView.initColumnCenter(mColumnCenterFooterList, mColumnCenters);
                getColumns("0");
                break;
            case FeedActivity.TAG_COLUMN_LIST:
                mView.initColumnList(mColumnHeaderList, mFeedList);
                getColumnList(mFeedId, "0");
                getColumnInfo();
                break;
        }
    }
}
