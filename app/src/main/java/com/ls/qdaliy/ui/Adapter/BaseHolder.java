package com.ls.qdaliy.ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.ls.qdaliy.ui.box.BaseBox;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * <p>
 * 在 BaseHolder 的内部持有一个 BaseBox 的引用,</br>
 * 具体指向由 BaseBox 的子类决定,
 * </p>
 * Created by asus on 2018/5/24.
 * Describe:
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    private BaseBox mItemView;

    private BaseHolder(View itemView) {
        super(itemView);
        this.mItemView = (BaseBox) itemView;
    }

    public static BaseHolder getHolder(Context context, Class<? extends BaseBox> clazz) {

        BaseBox baseBox = null;
        try {
            Constructor constructor = clazz.getConstructor(Context.class, AttributeSet.class);
            baseBox = (BaseBox) constructor.newInstance(context, null);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return new BaseHolder((View) baseBox);
    }

    public static BaseHolder getHolder(View view){
        return new BaseHolder(view);
    }

    public BaseBox getItemView() {
        return mItemView;
    }
}
