package com.ls.qdaliy.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.TotsQuestion;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/26.
 * Describe: 1002 的二选一
 */
public class TotsChoice extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.head_image)
    ImageView mHeadImage;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.option_pic_left)
    ImageView mOptionPicLeft;
    @BindView(R.id.percent_left)
    RunTextView mPercentLeft;
    @BindView(R.id.option_pic_right)
    ImageView mOptionPicRight;
    @BindView(R.id.percent_right)
    RunTextView mPercentRight;

    private int mSlideFlag; // 判断是点击了左边还是点击了右边
    private TotsQuestion mQuestion;
    private OnOptionChooseListener mChooseListener;

    public TotsChoice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_tots_choice, this);
        ButterKnife.bind(this);

        mOptionPicLeft.setOnClickListener(this);
        mOptionPicRight.setOnClickListener(this);
    }

    public void showTots(TotsQuestion question) {
        this.mQuestion = question;
        mPercentLeft.setVisibility(View.GONE);
        mPercentRight.setVisibility(View.GONE);

        mContent.setText(question.getContent());
        Glide.with(this).load(question.getOptions().get(0).getOptionPicUrl()).into(mOptionPicLeft);
        Glide.with(this).load(question.getOptions().get(1).getOptionPicUrl()).into(mOptionPicRight);
    }

    public void showHeader(String imageLink) {
        mHeadImage.setAlpha(0.5f);
        Glide.with(this).load(imageLink).into(mHeadImage);
    }

    @Override
    public void onClick(View view) {
        mPercentLeft.setVisibility(View.VISIBLE);
        mPercentRight.setVisibility(View.VISIBLE);

        switch (view.getId()) {
            case R.id.option_pic_left:
                mSlideFlag = 0;
                break;
            case R.id.option_pic_right:
                mSlideFlag = 1;
                break;
        }

        textAnimatorSet(mQuestion.getOptions().get(0).getPercent(), mQuestion.getOptions().get(1).getPercent());
    }

    private void next() {
        mChooseListener.onOptionChoose(mQuestion.getOptions().get(mSlideFlag).getId(), mQuestion.getId());
    }

    private void textAnimatorSet(int number1, int number2) {
        clearAnimation();
        ObjectAnimator animatorLeft = ObjectAnimator.ofInt(mPercentLeft, "number", 0, number1);
        ObjectAnimator animatorRight = ObjectAnimator.ofInt(mPercentRight, "number", 0, number2);
        AnimatorSet animatorSet = new AnimatorSet();

        animatorSet.setDuration(100);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                next();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.setInterpolator(new CustomInterpolator());
        animatorSet.playTogether(animatorLeft, animatorRight);
        animatorSet.start();
    }

    public void setChooseListener(OnOptionChooseListener chooseListener) {
        this.mChooseListener = chooseListener;
    }

    public void removeChooseListener() {
        mOptionPicLeft.setOnClickListener(null);
        mOptionPicRight.setOnClickListener(null);
    }

    public interface OnOptionChooseListener {
        void onOptionChoose(long id, long questionId);
    }

    /**
     * 用于数字滚动的动画插值器, 现在 0.5 的时间内按照余弦加速到头, 然后暂停 0.5 的时间
     */
    private class CustomInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float v) {
            if (v <= 0.5) {
                return (float) (2 * ((Math.cos((v + 1) * Math.PI) / 2.0f) + 0.5f));
            } else {
                return 1;
            }
        }
    }
}
