package com.ls.qdaliy.ui.box;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ls.qdaliy.R;
import com.ls.qdaliy.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/7/29.
 * Describe: footerView
 */
public class FooterBox extends FrameLayout implements BaseBox {

    @BindView(R.id.footer)
    TextView mFooter;

    public FooterBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.footer_box, this);
        ButterKnife.bind(this);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, AppUtil.dip2px(4), 0, AppUtil.dip2px(4));
        this.setLayoutParams(params);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public <T> void onBind(T t, int position) {
        if (position == 0) {
            this.setVisibility(View.GONE);
            return;
        } else {
            this.setVisibility(View.VISIBLE);
        }
        String str = (String) t;
        mFooter.setText(str);
    }

    @Override
    public void onBoxClick(View view) {

    }
}
