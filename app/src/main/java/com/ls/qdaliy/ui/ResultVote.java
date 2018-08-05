package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.VoteResult;
import com.ls.qdaliy.ui.Adapter.BaseAdapter;
import com.ls.qdaliy.ui.box.VoterRsultBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/14.
 * Describe: 投片对应的界面
 */
public class ResultVote extends LinearLayout{

    @BindView(R.id.result_image)
    ImageView mResultImage;
    @BindView(R.id.question)
    TextView mQuestion;
    @BindView(R.id.result_container)
    RecyclerView mResultContainer;

    private Context mContext;
//    private static final String TAG = "ResultZero";

    public ResultVote(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_result_zero, this);
        ButterKnife.bind(this);
    }

    public void setResultImage(String imageLink){
        Glide.with(this).load(imageLink).into(mResultImage);
    }

    public void setQuestion(String q){
        mQuestion.setText(q);
    }

    public void setResult(List<VoteResult.Attitude> attitudes){
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mResultContainer.setLayoutManager(layoutManager);
        BaseAdapter<VoteResult.Attitude> adapter = new BaseAdapter<>(mContext, attitudes, VoterRsultBox.class);
        mResultContainer.setAdapter(adapter);
    }
}
