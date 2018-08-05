package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.GenderQuestion;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/25.
 * Describe: 好奇心研究所中自定义的 RadioButton
 */
public class RadioButton extends FrameLayout {

    @BindView(R.id.radio_option)
    Button mButton;

    public RadioButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        this.setLayoutParams(params);
        LayoutInflater.from(context).inflate(R.layout.layout_radio_btn, this);
        ButterKnife.bind(this);

    }

    public void setOption(GenderQuestion.Option option) {
//        GenderQuestion.Option option1 = option;
        mButton.setText(option.getTitle());
    }
}
