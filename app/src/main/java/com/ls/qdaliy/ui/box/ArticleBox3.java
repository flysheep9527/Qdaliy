package com.ls.qdaliy.ui.box;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.ls.qdaliy.daliy.LaunchActivity;
import com.ls.qdaliy.daliy.PaperChoiceActivity;
import com.ls.qdaliy.daliy.PaperCommentActivity;
import com.ls.qdaliy.daliy.PaperVoteActivity;
import com.ls.qdaliy.daliy.TotsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/5/29.
 * Describe: 好奇研究所的 item
 */
public class ArticleBox3 extends FrameLayout implements BaseBox, View.OnClickListener {

    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.image_lab)
    ImageView mImageLab;

    private Feed mFeed;
    private Context mContext;
    public static final String GENRE = "genre";

    public ArticleBox3(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_article_3, this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
        ButterKnife.bind(this);

        setOnClickListener(this);
    }

    /*
     * 获取一个 BaseHolder 实例,
     * 这个方法原本应该在 BaseBox 里面有定义的,
     * 不过因为 jdk1.7 的接口不支持静态方法
     * ( Android API 24 以上才支持 jdk1.8),
     * 所以没有卸载接口里面.
     * 但是每一个实现 BaseBox 的类都必须提供一个
     * 获取 BaseHolder实例放入静态方法.
     * @param context 实例化 ArticleBox所需要的 context
     * @return BaseHolder 实例
     */
    /*public static BaseHolder getBaseHolder(Context context){
        ArticleBox3 box3 = new ArticleBox3(context, null);
        return new BaseHolder(box3);
    }*/

    /**
     * 在 Adapter 的 onBindViewHolder() 方法
     * 中回调这个方法, 并将相应需要的数据触底进来,
     * 将数据绑定到视图上面
     *
     * @param t   待绑定到 item 上的数据
     * @param <T> 声明泛型
     */
    @Override
    public <T> void onBind(T t, int position) {
        mFeed = (Feed) t;
        mTitle.setText(mFeed.getPost().getTitle());
        mDescription.setText(mFeed.getPost().getDescription());
        Glide.with(mContext).load(mFeed.getImageLink()).into(mImage);
        Glide.with(mContext).load(mFeed.getPost().getCategory().getImageLab()).into(mImageLab);
    }

    /**
     * 在 ItemView 被点击的时候回调
     *
     * @param view 被点击的 view
     */
    @Override
    public void onBoxClick(View view) {
        switch (mFeed.getPost().getGenre()) {
            case 1000:
                Intent intent = new Intent(mContext, PaperVoteActivity.class);
                intent.putExtra(LaunchActivity.PAPER_ID, mFeed.getPost().getId());
                mContext.startActivity(intent);
                break;
            case 1001:
                Intent intent1 = new Intent(mContext, PaperCommentActivity.class);
                intent1.putExtra(LaunchActivity.PAPER_ID, mFeed.getPost().getId());
                mContext.startActivity(intent1);
                break;
            case 1002:
                Intent intent2 = new Intent(mContext, TotsActivity.class);
                intent2.putExtra(LaunchActivity.PAPER_ID, mFeed.getPost().getId());
                mContext.startActivity(intent2);
                break;
            case 1003:
                Intent intent3 = new Intent(mContext, PaperChoiceActivity.class);
                intent3.putExtra(LaunchActivity.PAPER_ID, mFeed.getPost().getId());
                intent3.putExtra(GENRE, "1003");
                mContext.startActivity(intent3);
                break;
            case 1004:
                Intent intent4 = new Intent(mContext, PaperChoiceActivity.class);
                intent4.putExtra(LaunchActivity.PAPER_ID, mFeed.getPost().getId());
                intent4.putExtra(GENRE, "1004");
                mContext.startActivity(intent4);
                break;
        }
    }

    @Override
    public void onClick(View view) {
        onBoxClick(view);
    }
}
