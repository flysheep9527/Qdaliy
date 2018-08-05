package com.ls.qdaliy.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.ChoiceOption;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by asus on 2018/6/18.
 * Describe: lab 选择的选项容器
 */
public class  ChoiceSelect extends LinearLayout implements View.OnClickListener {

    private boolean mIsInit;
    private Context mContext;
    private OnOptionClickListener mListener;
    private List<ChoiceOptionView> mOptionViews;
    private int mRightAnswerIdx;
    private boolean mIsChoice; // 为 true 的话, 选择有对错之分, false 没有对错之分

    private static final String TAG = "ChoiceSelect";

    public ChoiceSelect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mIsInit = false;
        this.setOrientation(LinearLayout.VERTICAL);
    }

    public void initOptions(List<ChoiceOption> options) {
        mOptionViews = new ArrayList<>();
        if (options.size() == 4) {
            initGridLayout();
        } else {
            initNormalLayout(options.size());
        }

        this.mIsInit = true;
        fillOptionViews(options);
    }

    private void initGridLayout() {
        @SuppressLint("InflateParams")
        LinearLayout gridLayout = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.layout_grid, null);
        mOptionViews.add((ChoiceOptionView)gridLayout.findViewById(R.id.grid_1));
        mOptionViews.add((ChoiceOptionView)gridLayout.findViewById(R.id.grid_2));
        mOptionViews.add((ChoiceOptionView)gridLayout.findViewById(R.id.grid_3));
        mOptionViews.add((ChoiceOptionView)gridLayout.findViewById(R.id.grid_4));

        for(ChoiceOptionView view:mOptionViews){
            view.setOnClickListener(this);
        }

        this.addView(gridLayout);
    }

    // FIXME: 2018/6/19 这里应该添加一个 LinearLayout, 如果每个问题得到选项不一样的话, 可以直接增删
    private void initNormalLayout(int size) {
        for (int i = 0; i < size; ++i) {
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            ChoiceOptionView optionView = new ChoiceOptionView(mContext, null);
            optionView.setLayoutParams(params);
            optionView.setOnClickListener(this);
            mOptionViews.add(optionView);

            this.addView(optionView, -1);
        }
    }

    // FIXME: 2018/6/19 每个问题的选项数量不一定是相同的
    private void fillOptionViews(List<ChoiceOption> options) {
        for (int i = 0; i < mOptionViews.size(); ++i) {
            mOptionViews.get(i).showView(options.get(i));

            if(mIsChoice && options.get(i).getScore()==1){
                mRightAnswerIdx = i;
            }
        }
    }

    public void setOptions(List<ChoiceOption> options) {
        if (!mIsInit) {
            initOptions(options);
        }
        fillOptionViews(options);
    }

    @Override
    public void onClick(View view) {
        ChoiceOptionView optionView = (ChoiceOptionView) view;
        startAnimation(mOptionViews.indexOf(optionView), optionView);
        /*for(int i=0;i<mOptionViews.size();++i){
            mOptionViews.get(i).reSetBG();
            mOptionViews.get(i).setAlpha(1.0f);
        }*/
//        mListener.onOptionClick(optionView, optionView.getSelectId(), optionView.getScore());
    }

    private void startAnimation(int choose, final ChoiceOptionView optionView){
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<>();
        int rightIndex = 0;
        final ObjectAnimator rightChoice;

        for(int i=0;i<mOptionViews.size();++i){
            if(i != mRightAnswerIdx){
                animators.add(ObjectAnimator.ofFloat(mOptionViews.get(i), "alpha", 1, 0));
            }else{
                rightIndex = i;
            }
        }
        rightChoice = ObjectAnimator.ofFloat(mOptionViews.get(rightIndex), "alpha", 1, 0);

        AccelerateInterpolator interpolator = new AccelerateInterpolator();
        animatorSet.setDuration(400);
        animatorSet.setInterpolator(interpolator);
        rightChoice.setDuration(400);
        rightChoice.setInterpolator(interpolator);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Log.d(TAG, "animation end");
                mOptionViews.get(mRightAnswerIdx).setBg(true);
                rightChoice.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        rightChoice.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                for(int i=0;i<mOptionViews.size();++i){
                    mOptionViews.get(i).reSetBG();
                    mOptionViews.get(i).setAlpha(1.0f);
                }
                mListener.onOptionClick(optionView, optionView.getSelectId(), optionView.getScore());
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        if(mIsChoice && choose != mRightAnswerIdx){
            mOptionViews.get(choose).setBg(false);
        }
        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    public void setIsChoice(boolean isChoice){
        this.mIsChoice = isChoice;
    }

    public void setOnOptionClickListener(OnOptionClickListener listener) {
        this.mListener = listener;
    }

    // FIXME: 2018/6/19 运行的话将所有或者部分 long 型的 id 改为 String 类型
    public interface OnOptionClickListener {
        void onOptionClick(View view, long selectId, int score);
    }
}
