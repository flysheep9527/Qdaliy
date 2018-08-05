package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Post;
import com.ls.qdaliy.util.JsonParseUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/7/4.
 * Describe:
 */
public class ResultTots extends LinearLayout {

    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.score_view)
    LineChartView mScoreView;
    @BindView(R.id.line_view)
    TotResultLineView mLineView;

    private Context mContext;

    public ResultTots(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_result_tots, this);
        ButterKnife.bind(this);
    }

    public void setContent(JsonParseUtil json) {
        Post paper = json.getData("paper", Post.class);

        Glide.with(mContext).load(paper.getImage()).into(mImage);
        mTitle.setText(paper.getTitle());
        mDescription.setText(paper.getDescription());

        List<Integer> percents = json.getList("percent_list", new TypeToken<ArrayList<Integer>>() {
        }.getType());
        mScoreView.setPercentList(percents, json.getData("real_score", Float.class));

        mLineView.setScore(json.getData("expect_score", Float.class), json.getData("real_score", Float.class));
    }
}
