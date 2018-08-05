package com.ls.qdaliy.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.base.AppContext;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/8.
 * Describe: 好奇心实验室中 "我说" 中的弹幕 item
 */
public class BarrageItem extends LinearLayout {

    @BindView(R.id.user_face)
    ImageView mUserFace;
    @BindView(R.id.comment)
    TextView mComment;

//    private static final String TAG = "BarrageItem";

    public BarrageItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_barrage, this);
        ButterKnife.bind(this);
    }

    public void setUserFace(String link) {
        Glide.with(AppContext.getInstance()).load(link).into(mUserFace);
    }

    public void setComment(String content) {
        mComment.setText(content);
    }

}
