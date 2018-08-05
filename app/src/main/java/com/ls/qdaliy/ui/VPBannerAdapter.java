package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.ls.qdaliy.bean.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2018/5/27.
 * Describe:
 */
public class VPBannerAdapter extends PagerAdapter {

    private List<Feed> mFeeds;
    private List<BannerItem> mViewList;
    private Context mContext;

    VPBannerAdapter(Context context, List<Feed> feeds){
        this.mFeeds = feeds;
        this.mContext = context;
        initViewList();
    }

    private void initViewList(){
        int len = mFeeds.size() + 2;
        mViewList = new ArrayList<BannerItem>();

        if(mFeeds.size() <= 0){
            return;
        }

        BannerItem start = new BannerItem(mContext, null);
        start.initBannerItem(mFeeds.get(mFeeds.size()-1));
        mViewList.add(start);

        for(Feed f:mFeeds){
            BannerItem item = new BannerItem(mContext, null);
            item.initBannerItem(f);
            mViewList.add(item);
        }

        BannerItem end = new BannerItem(mContext, null);
        end.initBannerItem(mFeeds.get(0));
        mViewList.add(end);
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
        container.removeView(mViewList.get(position));
    }
}
