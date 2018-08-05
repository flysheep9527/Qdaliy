package com.ls.qdaliy.daliy;

import android.support.v7.widget.RecyclerView;

import com.ls.qdaliy.base.BasePresenter;
import com.ls.qdaliy.base.BaseView;
import com.ls.qdaliy.bean.Feed;
import com.ls.qdaliy.bean.NewsCategory;

import java.util.List;

/**
 * Created by asus on 2018/5/26.
 * Describe:
 */
public interface LaunchContract {
    interface View extends BaseView<Presenter> {
        void initHomeList(List<Feed> feeds, List<Feed> banners);
        void updateHomeList();
        void initLabList(List<Feed> labs);
        void updateLabList();
        void initSideMenu(List<NewsCategory> newsCategories);
        void showLabError(boolean isError);
        void showHomeError(boolean isError);
        void showFAB();
        void hideFAB();
//        void initBanner(List<Feed> banners);
    }

    interface Presenter extends BasePresenter{
        RecyclerView.OnScrollListener getMoreListListener(int perItems, int tag);
        void getHomeList(final String lastKey);
        void getLabList(final String lastkey);
    }
}
