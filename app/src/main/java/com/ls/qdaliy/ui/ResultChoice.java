package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.ChoiceResult;
import com.ls.qdaliy.bean.Post;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/20.
 * Describe: 好奇心研究所中选择的结果界面
 */
public class ResultChoice extends ScrollView{

    @BindView(R.id.head_image)
    ImageView mHeadImage;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.result_title)
    TextView mResultTitle;
    @BindView(R.id.result_image)
    ImageView mResultImage;
    @BindView(R.id.result_content)
    TextView mResultContent;

    public ResultChoice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ButterKnife.bind(this, LayoutInflater.from(context).inflate(R.layout.layout_result_three, this));
    }

    public void setResult(Post post, ChoiceResult result){
        Glide.with(this).load(post.getImage()).into(mHeadImage);
        mTitle.setText(post.getTitle());
        mDescription.setText(post.getDescription());

        mResultTitle.setText(result.getTitle());
        mResultContent.setText(result.getContent());
        Glide.with(this).load(result.getResultPic()).into(mResultImage);
    }
}
