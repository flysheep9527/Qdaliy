package com.ls.qdaliy.ui.box;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Comment;
import com.ls.qdaliy.util.ColorEnum;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/13.
 * Describe: 好奇心研究室中 "我说" 的[评论 item
 */
public class CommentBox extends LinearLayout implements BaseBox {

    @BindView(R.id.user_face)
    ImageView mUserFace;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.comment)
    TextView mComment;
    @BindView(R.id.praise_count)
    TextView mPraiseCount;

    private Context mContext;

    public CommentBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.item_comment, this);
        ButterKnife.bind(this);
        this.setOrientation(LinearLayout.VERTICAL);
    }

    @Override
    public <T> void onBind(T t, int position) {
        Comment c = (Comment) t;
        Glide.with(mContext).load(c.getAuthor().getAvatar()).into(mUserFace);
        mUserName.setText(c.getAuthor().getName());
        mComment.setText(c.getContent());
        mPraiseCount.setText(String.valueOf(c.getPraiseCount()).concat(" 赞"));

        this.setBackgroundColor(ColorEnum.values()[position % 5].getColor());
    }

    @Override
    public void onBoxClick(View view) {

    }
}
