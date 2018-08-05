package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.GenderQuestion;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/25.
 * Describe:
 */
public class RadioGroup extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.radio_left)
    TextView mRadioLeft;
    @BindView(R.id.radio_right)
    TextView mRadioRight;
    @BindView(R.id.content)
    TextView mContent;

    private long mQuestionId;
    private OnRadioCheckListener mListener;

    public RadioGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_radio_group, this);
        ButterKnife.bind(this);

        mRadioLeft.setOnClickListener(this);
        mRadioRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView radioBtn = (TextView) view;
        if (radioBtn == mRadioLeft) {
            mRadioLeft.setSelected(true);
            mRadioRight.setSelected(false);
        } else {
            mRadioLeft.setSelected(false);
            mRadioRight.setSelected(true);
        }
        mListener.onRadioCheck(view, radioBtn.getId(), this.mQuestionId);
    }

    public void setRadio(GenderQuestion question) {
        mContent.setText(question.getContent());
        mRadioLeft.setText(question.getOptions().get(0).getTitle()) ;
        mRadioRight.setText(question.getOptions().get(1).getTitle());
        mQuestionId = question.getId();
    }

    public void setOnRadioCheckListener(OnRadioCheckListener listener) {
        this.mListener = listener;
    }

    public interface OnRadioCheckListener {
        void onRadioCheck(View view, long id, long questionId);
    }
}
