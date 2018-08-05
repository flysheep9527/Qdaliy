package com.ls.qdaliy.ui.box;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Column;
import com.ls.qdaliy.daliy.FeedActivity;
import com.ls.qdaliy.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/7/19.
 * Describe: 栏目 item
 */
public class ColumnBox extends RelativeLayout implements BaseBox, View.OnClickListener {

    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.info)
    TextView mInfo;

    private Context mContext;
    private int mId;
    private String mName;

    public ColumnBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_column, this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, AppUtil.dip2px(4), 0, 0);
        this.setLayoutParams(params);
        this.setBackgroundColor(Color.WHITE);

        mContext = context;
        ButterKnife.bind(this);
        this.setOnClickListener(this);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public <T> void onBind(T t, int position) {
        Column column = (Column) t;
        Glide.with(mContext).load(column.getImage()).into(mImage);
        mTitle.setText(column.getName());
        mDescription.setText(column.getDescription());
        mInfo.setText(String.format("%d 人已经订阅, 更新至 %d 篇", column.getSubscriberNum(), column.getPostCount()));

        mId = column.getId();
        mName = column.getName();
    }

    @Override
    public void onBoxClick(View view) {
        Intent intent = new Intent(mContext, FeedActivity.class);
        intent.putExtra(FeedActivity.FEED_TYPE, FeedActivity.TAG_COLUMN_LIST);
        intent.putExtra(FeedActivity.FEED_TITLE, mName);
        intent.putExtra(FeedActivity.FEED_ID, mId);
        mContext.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        onBoxClick(view);
    }
}
