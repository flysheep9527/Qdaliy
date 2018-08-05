package com.ls.qdaliy.ui.box;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.VoteResult;
import com.ls.qdaliy.ui.Pie;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/14.
 * Describe: 投票结果显示的 item
 */
public class VoterRsultBox extends LinearLayout implements BaseBox{

    @BindView(R.id.pie)
    Pie mPie;
    @BindView(R.id.percent)
    TextView mPercent;
    @BindView(R.id.content)
    TextView mContent;

    public VoterRsultBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_result_1, this);
        ButterKnife.bind(this);
    }

    public void setPercent(String p){
        mPercent.setText(p);
    }

    public void setContent(String c){
        mContent.setText(c);
    }

    @Override
    public <T> void onBind(T t, int position) {
        VoteResult.Attitude attitude = (VoteResult.Attitude)t;
        mContent.setText(attitude.getContent());
        mPercent.setText(String.valueOf(attitude.getPercent()).concat("%"));
        mPie.initPie(attitude.isSelected(), attitude.getPercent());
    }

    @Override
    public void onBoxClick(View view) {

    }
}
