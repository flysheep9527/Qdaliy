package com.ls.qdaliy.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.NewsCategory;
import com.ls.qdaliy.daliy.FeedActivity;
import com.ls.qdaliy.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/7/18.
 * Describe: 新闻分类的 item
 */
public class NewsCategoryItem extends LinearLayout implements View.OnClickListener{

    @BindView(R.id.img_news_class_item)
    ImageView mImgNewsCategoryItem;
    @BindView(R.id.text_news_class_item)
    TextView mTextNewsCategoryItem;

    private Context mContext;
    private String mTitle;
    private int mId;

    public NewsCategoryItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_news_category, this);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, AppUtil.dip2px(12), 0, 0);
        this.setLayoutParams(params);
        this.setGravity(Gravity.CENTER_VERTICAL);


        this.mContext = context;
        ButterKnife.bind(this);
        this.setOnClickListener(this);
    }

    public void setItem(NewsCategory newscategory){
        Glide.with(mContext).load(newscategory.getWhiteIcon()).into(mImgNewsCategoryItem);
        mTextNewsCategoryItem.setText(newscategory.getTltle());

        this.mTitle = newscategory.getTltle();
        this.mId = newscategory.getId();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, FeedActivity.class);
        intent.putExtra(FeedActivity.FEED_TITLE, mTitle);
        intent.putExtra(FeedActivity.FEED_ID, mId);
        intent.putExtra(FeedActivity.FEED_TYPE, FeedActivity.TAG_NWE_LIST);
        mContext.startActivity(intent);
    }
}
