package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.ls.qdaliy.bean.Feed;

import java.util.List;

/**
 * Created by asus on 2018/5/27.
 * Describe:
 */
public class BannerView extends RelativeLayout {

    private Context mContext;
    private ViewPager mViewPager;
    private int size;
    private VPBannerAdapter bannerAdapter;

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mViewPager = new ViewPager(context);
        addView(mViewPager);

        /*bannerAdapter = new VPBannerAdapter(mContext, feeds);
        mViewPager.setAdapter(bannerAdapter);
        size = feeds.size() + 2;*/

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    mViewPager.setCurrentItem(size-1);
                }else if(position == size-1){
                    mViewPager.setCurrentItem(0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setData(List<Feed> feeds){
        bannerAdapter = new VPBannerAdapter(mContext, feeds);
        mViewPager.setAdapter(bannerAdapter);
        size = feeds.size() + 2;
    }

    public VPBannerAdapter getBannerAdapter() {
        return bannerAdapter;
    }
}
