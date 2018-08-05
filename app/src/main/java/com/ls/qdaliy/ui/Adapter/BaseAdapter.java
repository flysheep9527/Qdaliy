package com.ls.qdaliy.ui.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ls.qdaliy.ui.box.BaseBox;

import java.util.List;

/**
 * Created by asus on 2018/6/11.
 * Describe: 用于只包含一种 viewType 的 Recyclerview
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    private Context mContext;
    private List<T> mData;
    private Class<? extends BaseBox> mBaseBox;

    public BaseAdapter(Context context, List<T> data, Class<? extends BaseBox> baseBox) {
        this.mContext = context;
        this.mData = data;
        this.mBaseBox = baseBox;
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseHolder.getHolder(mContext, mBaseBox);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        holder.getItemView().onBind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
