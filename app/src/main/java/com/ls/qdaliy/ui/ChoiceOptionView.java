package com.ls.qdaliy.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.ChoiceOption;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/18.
 * Describe: lab 选择的选项
 */
public class ChoiceOptionView extends LinearLayout{

    @BindView(R.id.option_pic)
    ImageView mOptionImage;
    @BindView(R.id.title)
    TextView mTitle;

    private Context mContext;
    private long selectId;
    private int score;
//    private static final String TAG = "ChoiceOptionView";

    public ChoiceOptionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_choice_1, this);
        this.setOrientation(LinearLayout.VERTICAL);
        this.mContext = context;
        ButterKnife.bind(this);
    }

    public void showView(ChoiceOption option){
        this.selectId = option.getId();
        this.score = option.getScore();
        String picUrl = option.getOptionPicUrl();
        if(!(picUrl == null || picUrl.equals("")) && mOptionImage.getVisibility() == View.GONE){
            mOptionImage.setVisibility(View.VISIBLE);
        }

        if(mOptionImage.getVisibility() == View.VISIBLE){
            Glide.with(mContext).load(picUrl).into(mOptionImage);
        }

        mTitle.setText(option.getTitle());
    }

    public void setBg(boolean isRight){
        if(isRight){
            this.setBackgroundColor(getResources().getColor(R.color.correct));
        }else{
            this.setBackgroundColor(getResources().getColor(R.color.incorrect));
        }
    }

    public void reSetBG(){
        this.setBackgroundColor(Color.TRANSPARENT);
    }



    public long getSelectId(){
        return this.selectId;
    }

    public int getScore(){
        return this.score;
    }
}
