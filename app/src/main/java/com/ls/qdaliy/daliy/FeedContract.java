package com.ls.qdaliy.daliy;

import android.support.v7.widget.RecyclerView;

import com.ls.qdaliy.base.BasePresenter;
import com.ls.qdaliy.base.BaseView;
import com.ls.qdaliy.bean.Column;
import com.ls.qdaliy.bean.Feed;

import java.util.List;

/**
 * Created by asus on 2018/7/24.
 * Describe:
 */
public interface FeedContract {
    interface View extends BaseView<Presenter>{
        void updateNewsList();
        void updateColumnCenter();
        void updateColumnList();
        void initNewsList(List<Feed> feedList);
        void initColumnCenter(List<Object> footerList, List<Column> columnCenters);
        void initColumnList(List<Object> columnHeaderList, List<Feed> feedList);
    }

    interface Presenter extends BasePresenter{
        RecyclerView.OnScrollListener getMoreListListener(int perItems);
        void getNewsList(String lastKey);
        void getColumns(String lastKey);
        void getColumnList(int id, String lastKey);
        void getColumnInfo();
        void init(int tag, int feedId);
    }
}
