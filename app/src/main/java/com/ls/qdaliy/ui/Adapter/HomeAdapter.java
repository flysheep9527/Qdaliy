package com.ls.qdaliy.ui.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ls.qdaliy.bean.Feed;
import com.ls.qdaliy.ui.box.ArticleBox;
import com.ls.qdaliy.ui.box.ArticleBox2;
import com.ls.qdaliy.ui.box.ArticleBox3;

import java.util.List;

/**
 * Created by asus on 2018/5/24.
 * Describe: 用于包含了首页中三种 viewType 的 Recyclerview
 */
public class HomeAdapter<V> extends RecyclerView.Adapter<BaseHolder> {

    private Context mContext;
    private List<V> mData;
    private static final int TYPE_0 = 0;
    private static final int TYPE_1 = 1;
    private static final int TYPE_2 = 2;
//    private static final String TAG = "HomeAdapter";

    public HomeAdapter(Context context, List<V> data) {
        this.mContext = context;
        this.mData = data;
    }

    /**
     * 在创建 ViewHolder 的时候,
     * 根据不同的 viewType 来返回不同的 ViewHolder,
     * 实际上的区别在于 BaseHolder 内部的 BaseBox
     * 实例不一样
     * @param parent parent
     * @param viewType viewType
     * @return BaseHolder
     */
    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /*ArticleBox articleBox = new ArticleBox(mContext, null);
        return new ArticleBoxHolder(articleBox);*/
        BaseHolder holder = null;
        switch (viewType){
            case TYPE_0:
//                holder = ArticleBox3.getBaseHolder(mContext);
                holder = BaseHolder.getHolder(mContext, ArticleBox3.class);
                break;
            case TYPE_1:
//                holder = ArticleBox.getHolder(mContext);
                holder = BaseHolder.getHolder(mContext, ArticleBox.class);
                break;
            case TYPE_2:
//                holder = ArticleBox2.getBaseHolder(mContext);
                holder = BaseHolder.getHolder(mContext, ArticleBox2.class);
                break;
        }
        return holder;
    }

    /**
     * 在绑定数据的时候, 直接根据 holder
     * 获取 holder 中的 ItemView (即 baseBox 的子类),
     * 调用 itemView 的 onBind()方法进行数据绑定.
     * @param holder holder
     * @param position position
     */
    @Override
    public void onBindViewHolder(@NonNull final BaseHolder holder, int position) {
        holder.getItemView().onBind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * 需要重写这个方法, 为不同的 ItemView 返回不同的 ItemViewType,
     * @param position position
     * @return itemView
     */
    @Override
    public int getItemViewType(int position) {
        return ((Feed) mData.get(position)).getType();
    }
}
