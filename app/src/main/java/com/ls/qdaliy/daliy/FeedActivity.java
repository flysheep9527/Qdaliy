package com.ls.qdaliy.daliy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Column;
import com.ls.qdaliy.bean.Feed;
import com.ls.qdaliy.ui.Adapter.AdapterWrapper;
import com.ls.qdaliy.ui.Adapter.BaseAdapter;
import com.ls.qdaliy.ui.Adapter.HomeAdapter;
import com.ls.qdaliy.ui.box.ColumnBox;
import com.ls.qdaliy.ui.box.ColumnHeaderBox;
import com.ls.qdaliy.ui.box.FooterBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/7/18.
 * Describe:
 */
public class FeedActivity extends AppCompatActivity implements FeedContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_feed)
    RecyclerView mRvFeed;
    @BindView(R.id.rv_flush)
    SwipeRefreshLayout mRvFlush;

    private HomeAdapter<Feed> mNewsListAdapter;
    private AdapterWrapper mColumnCenterAdapter;
    private AdapterWrapper mColumnListAdapter;
    private FeedContract.Presenter mPresenter;

    private static final String TAG = "FeedActivity";
    public static final String FEED_TITLE = "feed_title";
    public static final String FEED_ID = "feed_id";
    public static final String FEED_TYPE = "feed_type";
    public static final int TAG_NWE_LIST = 1;
    public static final int TAG_COLUMN_CENTER = 2;
    public static final int TAG_COLUMN_LIST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mPresenter = new FeedPresenter(this);

        initRV();
        Intent intent = getIntent();
        init(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        init(intent);
    }

    private void init(Intent intent) {
        String title = intent.getStringExtra(FEED_TITLE);
        int feedId = intent.getIntExtra(FEED_ID, 0);
        Log.d(TAG, String.format("news id : %d", feedId));
        int tag = intent.getIntExtra(FEED_TYPE, -1);

        mToolbar.setTitle(title);

        mPresenter.init(tag, feedId);
//        initRV();
        mPresenter.start();
    }

    private void initRV() {
        mRvFeed.setLayoutManager(new LinearLayoutManager(this));
        mRvFeed.addOnScrollListener(mPresenter.getMoreListListener(5));

        mRvFlush.setProgressViewOffset(true, 50, 200);
        mRvFlush.setSize(SwipeRefreshLayout.LARGE);
        mRvFlush.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mRvFlush.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getNewsList("0");
            }
        });
    }

    public void initNewsList(List<Feed> feedList) {
        feedList.clear();
        if(mNewsListAdapter == null){
            mNewsListAdapter = new HomeAdapter<>(this, feedList);
        }
        mRvFeed.setAdapter(mNewsListAdapter);
        mRvFlush.setEnabled(true);
    }

    public void initColumnCenter(List<Object> footerList, List<Column> columnCenters) {
        columnCenters.clear();
        if(mColumnCenterAdapter == null){
            BaseAdapter<Column>  baseAdapter= new BaseAdapter<>(this, columnCenters, ColumnBox.class);
            mColumnCenterAdapter = new AdapterWrapper(baseAdapter);
        }

        footerList.clear();
        mColumnCenterAdapter.addFootView(FooterBox.class);
        footerList.add(getResources().getString(R.string.no_more));
        mColumnCenterAdapter.setFooterData(footerList);
        mRvFeed.setAdapter(mColumnCenterAdapter);
        mRvFlush.setEnabled(false);
    }

    public void initColumnList(List<Object> columnHeaderList, List<Feed> feedList) {
        if(mNewsListAdapter == null){
            feedList.clear();
            mNewsListAdapter = new HomeAdapter<>(this, feedList);
        }
        if(mColumnListAdapter == null){
            mColumnListAdapter = new AdapterWrapper(mNewsListAdapter);
        }

        columnHeaderList.clear();
        mColumnListAdapter.addHeaderView(ColumnHeaderBox.class);
        mColumnListAdapter.setHeaderData(columnHeaderList);
        mRvFeed.setAdapter(mColumnListAdapter);
        mRvFlush.setEnabled(true);
    }

    public void updateNewsList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNewsListAdapter.notifyDataSetChanged();
                Log.d(TAG, "update news list!");
                mRvFlush.setRefreshing(false);
            }
        });
    }

    public void updateColumnCenter() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mColumnCenterAdapter.notifyDataSetChanged();
                Log.d(TAG, "update columns!");
            }
        });
    }

    public void updateColumnList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mColumnListAdapter.notifyDataSetChanged();
                mRvFlush.setRefreshing(false);
            }
        });
    }
}
