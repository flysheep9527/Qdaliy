package com.ls.qdaliy.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ls.qdaliy.R;
import com.ls.qdaliy.bean.Option;
import com.ls.qdaliy.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by asus on 2018/6/14.
 * Describe: 好奇心研究所中 "投票" 的投票选项 item
 */
public class OptionItem extends LinearLayout implements View.OnClickListener{

    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.content)
    TextView mContent;
    @BindView(R.id.select)
    ImageView mSelect;

    private boolean mIsSelect;
    private Option mOption;
    private Context mContext;
    private OnSelectChangeListener mSelectChangeListener;

    public OptionItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item_option, this);
        LayoutParams params  = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(AppUtil.dip2px(24), AppUtil.dip2px(24), AppUtil.dip2px(24), 0);
        this.setLayoutParams(params);
        this.setBackgroundColor(getResources().getColor(R.color.gray));

        this.mContext = context;
        ButterKnife.bind(this);
        mSelect.setOnClickListener(this);
        mIsSelect = false;

    }

    public OptionItem(Context context, @Nullable AttributeSet attrs, Option option){
        this(context, attrs);
        this.mOption = option;
        Glide.with(mContext).load(mOption.getImageLink()).into(mImage);
        mContent.setText(mOption.getContent());
    }

    @Override
    public void onClick(View view) {
        mSelect.setSelected(!mIsSelect);
        mIsSelect = !mIsSelect;
        mSelectChangeListener.onSelectChange(mSelect.isSelected(), mOption.getId());
    }

    public void setSelectChangeListener(OnSelectChangeListener listener){
        this.mSelectChangeListener = listener;
    }

    public interface OnSelectChangeListener{
        void onSelectChange(boolean select, long id);
    }
}
