package com.ls.qdaliy.ui.box;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Feed;
import com.ls.qdaliy.daliy.DetailActivity;
import com.ls.qdaliy.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/5/25.
 * Describe: 图片在右边的普通文章显示的 item
 */
public class ArticleBox extends FrameLayout implements BaseBox, View.OnClickListener{

    @BindView(R.id.category_title)
    TextView mCategoryTitle;
    @BindView(R.id.praise_count)
    TextView mPraiseCount;
    @BindView(R.id.comment_count)
    TextView mCommentCount;
    @BindView(R.id.public_time)
    TextView mPublicTime;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.image)
    ImageView mImage;

    private Feed mFeed;
    private Context mContext;
    private int mDrawableHeight;

    private static final String TAG = "ArticleBox";

    public ArticleBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_article_1, this);
        ButterKnife.bind(this);

        this.setOnClickListener(this);
    }

    public void setTitle(String titleText){
        this.mTitle.setText(titleText);
    }

    public void setPublicTime(String time){
        this.mPublicTime.setText(time);
    }

    public void setCategoryTitle(String category){
        this.mCategoryTitle.setText(category);
    }

    public void setPraiseCount(int count){
        if(count == 0){
            return;
        }
        mPraiseCount.setVisibility(View.VISIBLE);

        int h = mDrawableHeight == 0 ? getDrawableHeight(mPraiseCount) : mDrawableHeight;
        Drawable praiseDrawable = getResources().getDrawable(R.drawable.praise);
        praiseDrawable.setBounds(new Rect(0, 0, h, h));

        mPraiseCount.setCompoundDrawablesRelative(praiseDrawable, null, null, null);
        mPraiseCount.setCompoundDrawablePadding(AppUtil.dip2px(2));
        mPraiseCount.setText(String.valueOf(count));
    }

    public void setCommentCount(int count){
        if(count == 0){
            return;
        }
        mCommentCount.setVisibility(View.VISIBLE);
        int h = mDrawableHeight == 0 ? getDrawableHeight(mPraiseCount) : mDrawableHeight;
        Drawable commentDrawable = getResources().getDrawable(R.drawable.comment);
        commentDrawable.setBounds(0, 0, h, h);

        mCommentCount.setCompoundDrawablesRelative(commentDrawable, null, null, null);
        mCommentCount.setCompoundDrawablePadding(AppUtil.dip2px(2));
        mCommentCount.setText(String.valueOf(count));
    }

    /**
     * 测量 TextView 的高度 ( 为了在 TextView 中设置 Drawable)
     * @param textView  待测量的 TextView
     * @return TextView 的高度
     */
    private int getDrawableHeight(TextView textView){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        textView.measure(w, h);
        mDrawableHeight = textView.getMeasuredHeight();
        return mDrawableHeight;
    }

    public void setImage(Bitmap bitmap){
        mImage.setImageBitmap(bitmap);
    }

    public ImageView getImage(){
        return mImage;
    }

    @Override
    public <T>void onBind(T t, int position){
        mFeed = (Feed)t;
//        holder.articleBox.setPublicTime(feed.getPost().getPublishTime());
        setTitle(mFeed.getPost().getTitle());
        setCategoryTitle(mFeed.getPost().getCategory().getTitle());
        setPraiseCount(mFeed.getPost().getPraiseCount());
        setCommentCount(mFeed.getPost().getCommentCount());
        Glide.with(mContext).load(mFeed.getImageLink()).into(getImage());
    }

    @Override
    public void onBoxClick(View view) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.putExtra(DetailActivity.ARTICLE_ID, mFeed.getPost().getId());
        mContext.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        onBoxClick(view);
    }
}
