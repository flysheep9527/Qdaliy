package com.ls.qdaliy.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.NewsCategory;
import com.ls.qdaliy.daliy.FeedActivity;
import com.ls.qdaliy.daliy.SearchACtivity;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/7/18.
 * Describe: 侧栏菜单
 */
public class SideMenu extends Dialog implements View.OnClickListener {

    @BindView(R.id.search)
    TextView mSearch;
    @BindView(R.id.news_category_list)
    LinearLayout mNewsCategoryList;
    @BindView(R.id.columns)
    LinearLayout mColumns;
    @BindView(R.id.arrow)
    ImageView mArrow;
    @BindView(R.id.news_category)
    LinearLayout mNewsCategory;
    @BindView(R.id.menu)
    LinearLayout mMenu;

    private Context mContext;

    public SideMenu(@NonNull Context context) {
        super(context, R.style.dialog);
        setOwnerActivity((Activity) context);
        setContentView(R.layout.dialog_slide_menu);
        ButterKnife.bind(this);

        mContext = context;
        mSearch.setOnClickListener(this);
        mNewsCategory.setOnClickListener(this);
        mColumns.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(params);

        shoeAnimation();
    }

    @Override
    public void cancel() {
        if (mNewsCategoryList.getVisibility() == View.VISIBLE) {
            mNewsCategoryList.setVisibility(View.GONE);
            mArrow.setSelected(false);
        } else {
            super.cancel();
        }
    }

    public void setNewsCategoryList(List<NewsCategory> newsCategories) {
        for (int i = 0; i < newsCategories.size(); ++i) {
            NewsCategoryItem item = new NewsCategoryItem(mContext, null);
            item.setItem(newsCategories.get(i));
            mNewsCategoryList.addView(item);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_category:
                switchNewsCategoryList();
                break;
            case R.id.columns:
                Intent intent = new Intent(mContext, FeedActivity.class);
                intent.putExtra(FeedActivity.FEED_TITLE, "栏目中心");
                intent.putExtra(FeedActivity.FEED_TYPE, FeedActivity.TAG_COLUMN_CENTER);
                mContext.startActivity(intent);
                break;

            case R.id.search:
                Intent searchIntent = new Intent(mContext, SearchACtivity.class);
                mContext.startActivity(searchIntent);
                break;
        }
    }

    private void switchNewsCategoryList() {
        if (mNewsCategoryList.getVisibility() == View.GONE) {
            mNewsCategoryList.setVisibility(View.VISIBLE);
            mArrow.setSelected(true);
        } else {
            mNewsCategoryList.setVisibility(View.GONE);
            mArrow.setSelected(false);
        }
    }

    private void shoeAnimation() {
        BounceInterpolator interpolator = new BounceInterpolator();
        TranslateAnimation searchShowAnimation
                = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1, Animation.RELATIVE_TO_SELF, 0);
        searchShowAnimation.setDuration(500);
        searchShowAnimation.setInterpolator(interpolator);

        TranslateAnimation menuAnimation
                = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        menuAnimation.setDuration(500);
        menuAnimation.setInterpolator(interpolator);

        mSearch.startAnimation(searchShowAnimation);
        mMenu.startAnimation(menuAnimation);
    }
}
