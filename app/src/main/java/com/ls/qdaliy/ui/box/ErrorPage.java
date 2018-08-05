package com.ls.qdaliy.ui.box;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ls.qdaliy.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/7/31.
 * Describe: 加载错误页面
 */
public class ErrorPage extends LinearLayout implements View.OnClickListener, BaseBox {

    @BindView(R.id.try_again)
    Button mTryAgain;

    private OnTryAgainClickListener mTryAgainClickListener;

    public ErrorPage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_error_page, this);
        ButterKnife.bind(this);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        this.setLayoutParams(params);
        this.setOrientation(LinearLayout.VERTICAL);

        mTryAgain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mTryAgainClickListener.onTryAgainClick(view);
    }

    public void setTryAgainClickListener(OnTryAgainClickListener tryAgainClickListener) {
        mTryAgainClickListener = tryAgainClickListener;
    }

    public interface OnTryAgainClickListener{
        void onTryAgainClick(View view);
    }

    @Override
    public <T> void onBind(T t, int position) {

    }

    @Override
    public void onBoxClick(View view) {

    }
}
