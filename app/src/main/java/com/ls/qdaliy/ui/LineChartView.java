package com.ls.qdaliy.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ls.qdaliy.R;
import com.ls.qdaliy.util.AppUtil;

import java.util.List;

/**
 * Created by asus on 2018/6/28.
 * Describe: 1002 结果的条形图
 */
public class LineChartView extends View {

    private Paint mNormalBgPaint;
    private Paint mSelectedBgPaint;
    private Paint mTextPaint;
    private float[] mPercentList;
    private List<Integer> mPercents;
    private int mWidth;
    private int mHeight; // view 的高度
    private int maxHeight; // 柱形图的最高高度
    private int mSpace;
    private int mScoreSection = -1; // 用于标记用户分数落在那个区间
    private boolean mIsInit = false;
    private boolean mSectionIsSet = false;
    private Rect mBounds;

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.mPercentList = new float[5];
        this.mSpace = AppUtil.dip2px(2);
        init();
    }

    private void init() {
        mNormalBgPaint = new Paint();
        mNormalBgPaint.setAntiAlias(true);
        mNormalBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mNormalBgPaint.setColor(getResources().getColor(R.color.light_yellow));

        mSelectedBgPaint = new Paint();
        mSelectedBgPaint.setAntiAlias(true);
        mSelectedBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mSelectedBgPaint.setColor(getResources().getColor(R.color.yellow));


        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(AppUtil.sp2px(12));

        mBounds = new Rect();
    }

    public void setPercentList(List<Integer> percents, float score) {
        this.mPercents = percents;
        int maxPercent = percents.get(0);
        score *= 10;

        for (int i = 0; i < 4; ++i) {
            if (percents.get(i) < percents.get(i + 1)) {
                maxPercent = percents.get(i + 1);
            }

            score -= percents.get(i);
            if(score <= 0 && !mSectionIsSet){
                mScoreSection = i;
                mSectionIsSet = true;
            }
        }
        if (mScoreSection == -1) {
            mScoreSection = 4;
        }

        for (int i = 0; i < 5; ++i) {
            mPercentList[i] = (float) percents.get(i) / maxPercent;
            /*Log.d(TAG, String.format("第 %d 个分数为 %d", i, percents.get(i)));
            Log.d(TAG, String.format("第 %d 个高度比例为 %f", i, mPercentList[i]));*/
        }

        this.mIsInit = true;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.maxHeight = (int) (mHeight * 0.8f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.mIsInit) {
            return;
        }
        float cubeWidth = (float) ((this.mWidth - (this.mSpace * 4)) / 5);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        float minHeight = 16.0f;

        for (int i = 0; i < 5; ++i) {
            float cubeHeight = this.maxHeight * mPercentList[i];
            String text = this.mPercents.get(i) + "%";
            mTextPaint.getTextBounds(text, 0, text.length(), mBounds);
            float baseLine = this.mHeight - cubeHeight - fontMetrics.top + minHeight / 2;
            float textX = (this.mSpace + cubeWidth) * i + cubeWidth / 2;
            if (i == mScoreSection) {
                canvas.drawRect((this.mSpace + cubeWidth) * i, this.mHeight - cubeHeight - minHeight, (this.mSpace + cubeWidth) * i + cubeWidth, this.mHeight, mSelectedBgPaint);
                canvas.drawText(text, textX, baseLine, mTextPaint);
            } else {
                canvas.drawRect((this.mSpace + cubeWidth) * i, this.mHeight - cubeHeight - minHeight, (this.mSpace + cubeWidth) * i + cubeWidth, this.mHeight, mNormalBgPaint);
                canvas.drawText(text, textX, baseLine, mTextPaint);
            }
        }
    }
}
