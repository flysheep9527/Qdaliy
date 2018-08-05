package com.ls.qdaliy.ui;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by asus on 2018/5/24.
 * Describe: viewPaper 的 adapter
 */
public class LaunchAdapter extends PagerAdapter{
    private List<View> mViewList;

    public LaunchAdapter(List<View> viewList){
        this.mViewList = viewList;
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {//必须实现，实例化
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {//必须实现，销毁
        container.removeView(mViewList.get(position));
    }
}
