package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Feed;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/5/27.
 * Describe:
 */
public class BannerItem extends FrameLayout{

    @BindView(R.id.banner_image)
    ImageView mBannerImage;
    @BindView(R.id.banner_title)
    TextView mBannerTitle;

    private Context mContext;

    public BannerItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_banner, this);
        this.mContext = context;
        ButterKnife.bind(this);
    }

    public void initBannerItem(Feed feed){
        mBannerTitle.setText(feed.getPost().getTitle());
        Glide.with(mContext).load(feed.getImageLink()).into(mBannerImage);
    }
}
