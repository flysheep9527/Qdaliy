package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.SlideQuestion;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/26.
 * Describe: 滑动条
 */
public class Slide extends LinearLayout{

    @BindView(R.id.seek_title)
    TextView mSeekTitle;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.seek_left)
    TextView mSeekLeft;
    @BindView(R.id.seek_right)
    TextView mSeekRight;
    @BindView(R.id.seek_left_foot)
    TextView mSeekLeftFoot;
    @BindView(R.id.seek_right_foot)
    TextView mSeekRightFoot;

    private long mQuestionId;
    private OnSeeKBarSlideListener mSlideListener;

    public Slide(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_seek, this);
        ButterKnife.bind(this);

        mSeekBar.setMax(100);
        mSeekBar.setProgress(50);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int i, boolean b) {
                mSlideListener.onSeekBarSlide(mQuestionId, i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar bar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {

            }
        });
    }

    public void setSlideQuestion(SlideQuestion question){
        mSeekTitle.setText(question.getContent());
        mSeekLeft.setText(question.getOptions().get(0).getTitle());
        mSeekRight.setText(question.getOptions().get(1).getTitle());
        mSeekLeftFoot.setText(question.getOptions().get(0).getContent());
        mSeekRightFoot.setText(question.getOptions().get(1).getContent());
        this.mQuestionId = question.getId();
    }

    public void setSlideListener(OnSeeKBarSlideListener slideListener){
        this.mSlideListener = slideListener;
    }

    public interface OnSeeKBarSlideListener{
        void onSeekBarSlide(long questionId, int progress);
    }

}
