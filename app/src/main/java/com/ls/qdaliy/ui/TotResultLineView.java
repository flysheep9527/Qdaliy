package com.ls.qdaliy.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ls.qdaliy.R;
import com.ls.qdaliy.util.AppUtil;

/**
 * Created by asus on 2018/7/10.
 * Describe: tots 结果显示中一部分
 */
public class TotResultLineView extends View {

    private Paint mBitmapPaint;
//    private Bitmap expectBitmap;
//    private Bitmap realBitmap;
    private Bitmap mExpectLineBitmap;
    private Bitmap mRealLineBitmap;
    private Bitmap mUserIconBitmap;
    private Bitmap mProgressBitmap;
    private float mExpectScore;
    private float mRealScore;
    private boolean mIsScoreSet;
    private int mWidth;
    private int mHeight;

    public TotResultLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        this.mIsScoreSet = false;

        this.mBitmapPaint = new Paint();
        this.mBitmapPaint.setAntiAlias(true);

        mExpectLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tots_expect_line);
        mExpectLineBitmap = Bitmap.createScaledBitmap(mExpectLineBitmap, mExpectLineBitmap.getWidth()/2, mExpectLineBitmap.getHeight()/2, true);
        mRealLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tots_real_line);
        mRealLineBitmap = Bitmap.createScaledBitmap(mRealLineBitmap, mRealLineBitmap.getWidth()/2, mRealLineBitmap.getHeight()/2, true);
        mProgressBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_tots_progress);
        mUserIconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.missing_face);
        mUserIconBitmap = Bitmap.createScaledBitmap(mUserIconBitmap, AppUtil.dip2px(27), AppUtil.dip2px(27), true);

    }

    public void setScore(float expectScore, float realScore){
        this.mExpectScore = expectScore;
        this.mRealScore = realScore;

        this.mIsScoreSet = true;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas){
        if(!mIsScoreSet){
            return;
        }
        drawProgress(canvas);
        drawScore(canvas);
    }

    private void drawProgress(Canvas canvas ){
        if(this.mProgressBitmap != null){
//            this.mProgressBitmap = Bitmap.createScaledBitmap(this.mProgressBitmap, this.mWidth, (int)(this.mProgressBitmap.getHeight()/((float)(this.mProgressBitmap.getWidth()/this.mWidth))), true);
            this.mProgressBitmap = Bitmap.createScaledBitmap(this.mProgressBitmap, this.mWidth, AppUtil.dip2px(18), true);
            canvas.drawBitmap(this.mProgressBitmap, 0.0f, this.mHeight-this.mProgressBitmap.getHeight(), this.mBitmapPaint);
        }
    }

    private void drawScore(Canvas canvas){
        float lineTop = this.mHeight - AppUtil.dip2px(5) - this.mRealLineBitmap.getHeight();
        float iconTop = lineTop - this.mUserIconBitmap.getHeight() + 5.0f;
        float lineWidth = this.mRealLineBitmap.getWidth();
        float iconWidth = this.mUserIconBitmap.getWidth();

        canvas.drawBitmap(this.mRealLineBitmap, this.mWidth*this.mRealScore /10-lineWidth/2, lineTop, this.mBitmapPaint);
        canvas.drawBitmap(this.mExpectLineBitmap, this.mWidth*this.mExpectScore /10-lineWidth/2, lineTop, this.mBitmapPaint);
        canvas.drawBitmap(this.mUserIconBitmap, this.mWidth*this.mRealScore /10 - iconWidth/2, iconTop, this.mBitmapPaint);
        canvas.drawBitmap(this.mUserIconBitmap, this.mWidth*this.mExpectScore /10 - iconWidth/2, iconTop, this.mBitmapPaint);
    }

}
