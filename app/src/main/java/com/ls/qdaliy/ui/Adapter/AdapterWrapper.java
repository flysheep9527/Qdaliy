package com.ls.qdaliy.ui.Adapter;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ls.qdaliy.ui.box.BaseBox;

import java.util.List;

/**
 * Created by asus on 2018/7/19. <br>
 * Describe: 为 recyclerView adapter 添加 headerView, footView 以及错误页面的包装类
 */
public class AdapterWrapper extends RecyclerView.Adapter<BaseHolder> {

    private RecyclerView.Adapter<BaseHolder> mInnerAdapter;
    private SparseArrayCompat<Class<? extends BaseBox>> mHeaderViews;
    private SparseArrayCompat<Class<? extends BaseBox>> mFooterViews;
    private View mErrorPage;
    private List mHeaderData;
    private List mFooterData;
    private boolean mIsError = false;

    private static final int BASE_HEADER_TYPE = 1000;
    private static final int BASES_FOOTER_TYPE = 2000;
    private static final int ERROR_TYPE = -1000;

    public AdapterWrapper(RecyclerView.Adapter<BaseHolder> adapter) {
        this.mInnerAdapter = adapter;
        mHeaderViews = new SparseArrayCompat<>(2);
        mFooterViews = new SparseArrayCompat<>(1);
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mIsError){
            return BaseHolder.getHolder(mErrorPage);
        }

        Class<? extends BaseBox> clazz;
        if ((clazz = mHeaderViews.get(viewType)) != null) {
            return BaseHolder.getHolder(parent.getContext(), clazz);
        }
        if ((clazz = mFooterViews.get(viewType)) != null) {
            return BaseHolder.getHolder(parent.getContext(), clazz);
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        if(mIsError){
            return;
        }
        if (position < getHeaderCount()) {
            holder.getItemView().onBind(mHeaderData.get(position), position);
            return;
        }

        if (position >= getHeaderCount() + mInnerAdapter.getItemCount()) {
            position = position - getHeaderCount() - mInnerAdapter.getItemCount();
            holder.getItemView().onBind(mFooterData.get(position), position);
            return;
        }


        mInnerAdapter.onBindViewHolder(holder, position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
        if(mIsError){
            return 1;
        }
        return getHeaderCount() + getFooterCount() + mInnerAdapter.getItemCount();
    }

    public int getItemViewType(int position) {
        if (position < getHeaderCount()) {
            return mHeaderViews.keyAt(position);
        } else if (position >= mInnerAdapter.getItemCount() + getHeaderCount()) {
            return mFooterViews.keyAt(position - mInnerAdapter.getItemCount() - getHeaderCount());
        }
        if(mIsError){
            return ERROR_TYPE;
        }

        return mInnerAdapter.getItemViewType(position - getHeaderCount());
    }

    public void addHeaderView(Class<? extends BaseBox> headerViewClazz) {
        mHeaderViews.put(getHeaderCount() + BASE_HEADER_TYPE, headerViewClazz);
    }

    public void addFootView(Class<? extends BaseBox> footerViewClazz) {
        mFooterViews.put(getFooterCount() + BASES_FOOTER_TYPE, footerViewClazz);
    }

    public void setHeaderData(List headerData) {
        this.mHeaderData = headerData;
    }

    public void setFooterData(List footerData) {
        this.mFooterData = footerData;
    }

    private int getHeaderCount() {
        return mHeaderData == null ? 0 : mHeaderData.size();
    }

    private int getFooterCount() {
        return mFooterData == null ? 0 : mFooterData.size();
    }

    public void setErrorPage(View errorPage){
        this.mErrorPage = errorPage;
    }

    public void showErrorPage(boolean isError){
        mIsError = isError;
    }
}
