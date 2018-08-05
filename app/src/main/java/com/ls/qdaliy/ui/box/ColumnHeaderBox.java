package com.ls.qdaliy.ui.box;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Column;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/7/20.
 * Describe: 栏目的 headerView
 */
public class ColumnHeaderBox extends LinearLayout implements BaseBox {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.author_description)
    TextView mAuthorDescription;
    @BindView(R.id.subscriber_num)
    TextView mSubscriberNum;
    @BindView(R.id.image)
    ImageView mImage;

    private Context mContext;

    public ColumnHeaderBox(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_column_header, this);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
        this.setOrientation(LinearLayout.VERTICAL);

        this.mContext = context;
        ButterKnife.bind(this);
    }

    @Override
    public <T> void onBind(T t, int position) {
        Column column = (Column)t;
        Glide.with(mContext).load(column.getImageLarge()).into(mImage);
        mTitle.setText(column.getName());
        mDescription.setText(column.getDescription());
        mSubscriberNum.setText(String.valueOf(column.getSubscriberNum()).concat(" 人订阅了此栏目"));
    }

    @Override
    public void onBoxClick(View view) {

    }
}
