package com.ls.qdaliy.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ls.qdaliy.R;

/**
 * Created by asus on 2018/6/14.
 * Describe: 好奇心研究所 "投票" 中显示结果百分比
 */
public class Pie extends View{

    private int mPercent;
    private Paint mPaint;
    private boolean mReadyToDraw;
    private boolean mIsSelect;

    public Pie(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
//        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        mPaint.setStyle(Paint.Style.FILL);
        mReadyToDraw = false;
    }

    public void initPie(boolean isSelect, int percent){
        this.mPercent = percent;
        this.mIsSelect = isSelect;
        if(isSelect){
            mPaint.setColor(getResources().getColor(R.color.pie_no_select_yellow));
        }else{
            mPaint.setColor(getResources().getColor(R.color.pie_no_select_gray));
        }
        mReadyToDraw = true;
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if(!mReadyToDraw){
            return;
        }
        super.onDraw(canvas);
        int w = getWidth()/2;
        int h = getHeight()/2;
        int r = h > w ? w : h;
        canvas.drawCircle(r, r, r, mPaint);
        RectF oval = new RectF(w-r, h-r, w+r, h+r);


        if(mIsSelect){
            mPaint.setColor(getResources().getColor(R.color.pie_select_yellow));
        }else{
            mPaint.setColor(getResources().getColor(R.color.pie_select_gray));
        }
        canvas.drawArc(oval,  -90, 360* mPercent /100, true,mPaint);
    }
}
