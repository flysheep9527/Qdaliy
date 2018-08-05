package com.ls.qdaliy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Comment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2018/6/7.
 * Describe: 好奇心实验室中 "我说" 的弹幕
 */
public class BarrageView extends RelativeLayout {

    private List<Comment> mBarrages;  // 所有的弹幕
    private List<Comment> mPageBarrage; // 一页弹幕
//    private int barrageCount; // 弹幕总数
    private long mTimeGap;  // 两条弹幕进入屏幕的时间差
    private int mBarrageSize; // 一页可装载弹幕数量
//    private float percent; // 该布局占父布局的百分比
    private int mFirstBarrageOfNextPage = 0; // 下一页的第一条弹幕
    private int mBarrageNum; // 记录当前弹幕是第几条弹幕
    private int barrageHeight;
    private NextBarrageHandler mHandler;

    private Context mContext;

    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BarrageView);
        mTimeGap = typedArray.getInt(R.styleable.BarrageView_time_gap, 1000);
//        percent = typedArray.getFloat(R.styleable.BarrageView_percent, 0.5f);
        typedArray.recycle();

        mHandler = new NextBarrageHandler(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        barrageHeight = getMeasuredHeight() / mBarrageSize;
    }

    public void start(){
        mPageBarrage = new ArrayList<>();
        nextPage();
        mHandler.sendEmptyMessageDelayed(0, mTimeGap);
    }

    private void nextPage() {
        if(mBarrages.size() - mFirstBarrageOfNextPage > mBarrageSize){
            if(mPageBarrage.size() > 0){
                mPageBarrage.clear();
                mPageBarrage.addAll(mBarrages.subList(mFirstBarrageOfNextPage, mFirstBarrageOfNextPage + mBarrageSize));
                mFirstBarrageOfNextPage += mBarrageSize;
            }else{
                mPageBarrage.addAll(mBarrages.subList(mFirstBarrageOfNextPage, mFirstBarrageOfNextPage + mBarrageSize));
            }
        }else if(mBarrages.size() < mBarrageSize){
            if(mPageBarrage.size() == 0){
                mPageBarrage.addAll(mBarrages);
            }
        }else {
            mPageBarrage.clear();
            mPageBarrage.addAll(mBarrages.subList(mFirstBarrageOfNextPage, mBarrages.size()));
            mFirstBarrageOfNextPage = mBarrageSize - (mBarrages.size() - mFirstBarrageOfNextPage);
            mPageBarrage.addAll(mBarrages.subList(0, mFirstBarrageOfNextPage));
        }
        mBarrageNum = 0;
    }

    private void nextBarrage(){
        Comment comment = mPageBarrage.get(mBarrageNum);
        final BarrageItem item = new BarrageItem(mContext, null);
        item.setComment(comment.getContent());
        item.setUserFace(comment.getAuthor().getAvatar());

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.topMargin = mBarrageNum * barrageHeight;
        item.setLayoutParams(params);
        this.addView(item);

        int leftMargin = this.getRight() - this.getLeft() - this.getPaddingLeft();
        TranslateAnimation animation = new TranslateAnimation(leftMargin, -getItemWidth(item), 0, 0);
        animation.setDuration(10000);
        animation.setFillAfter(true);
//        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                item.clearAnimation();
                BarrageView.this.removeView(item);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        item.startAnimation(animation);

        if(mBarrageNum +1== mBarrageSize){
            nextPage();
        }else{
            ++mBarrageNum;
        }
    }

    public void stop(){
        mHandler.removeCallbacksAndMessages(null);
    }

    private int getItemWidth(BarrageItem item){
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        item.measure(w, h);
        return item.getMeasuredWidth();
    }

    private static class NextBarrageHandler extends Handler {
        WeakReference<BarrageView> mWeakReference;

        NextBarrageHandler(BarrageView barrageView){
            mWeakReference = new WeakReference<>(barrageView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BarrageView barrageView = mWeakReference.get();
            barrageView.nextBarrage();
            this.sendEmptyMessageDelayed(0, barrageView.mTimeGap);
        }
    }

    public void setBarrages(List<Comment> barrages) {
        this.mBarrages = barrages;
    }

    /*public void setTimeGap(long mTimeGap) {
        this.mTimeGap = mTimeGap;
    }*/

    public void setBarrageSize(int barrageSize) {
        this.mBarrageSize = barrageSize;
    }

    /*public void setPercent(float percent) {
        this.percent = percent;
    }*/
}
