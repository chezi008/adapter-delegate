package com.ibbhub.adapterdelegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

/**
 * @author ：chezi008 on 2018/8/18 9:37
 * @description ：
 * @email ：chezi008@163.com
 */
public abstract class AdapterDelegate<T> implements IDelegate<T> {

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder) {
        return false;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
    }
}
