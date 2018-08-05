package com.ls.qdaliy.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.ls.qdaliy.R;

/**
 * Created by asus on 2018/7/9.
 * Describe: 显示滚动数字
 */
public class RunTextView extends android.support.v7.widget.AppCompatTextView{

    private int number;

    public RunTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RunTextView);
//        int duration = typedArray.getInt(R.styleable.RunTextView_duration, 1);
        typedArray.recycle();
    }

    public void setNumber(int number){
        this.number = number;
        this.setText(String.valueOf(number).concat(" %"));
    }

    public int getNumber(){
        return number;
    }
}
