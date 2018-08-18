package com.ibbhub.adapterdelegate;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/18 9:29
 * @description ：
 * @email ：chezi008@163.com
 */
public interface IDelegateManager<T> {

    IDelegateManager<T> addDelegate(@NonNull IDelegate<T> delegate);

    IDelegateManager<T> addDelegate(int viewType,
                                    @NonNull IDelegate<T> delegate);

    IDelegateManager<T> addDelegate(int viewType, boolean allowReplacingDelegate,
                                    @NonNull IDelegate<T> delegate);

    IDelegateManager<T> removeDelegate(@NonNull IDelegate<T> delegate);

    IDelegateManager<T> removeDelegate(int viewType);

    int getItemViewType(@NonNull T items, int position);

    @NonNull
    RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    void onBindViewHolder(@NonNull T items, int position,
                          @NonNull RecyclerView.ViewHolder holder, List payloads);

    void onBindViewHolder(@NonNull T items, int position,
                          @NonNull RecyclerView.ViewHolder holder);

    void onViewRecycled(@NonNull RecyclerView.ViewHolder holder);

    boolean onFailedToRecycleView(@NonNull RecyclerView.ViewHolder holder);

    void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder);

    void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder);

    IDelegateManager<T> setFallbackDelegate(
            @Nullable AbsFallbackAdapterDelegate<T> fallbackDelegate);

    int getViewType(@NonNull IDelegate<T> delegate);

    @Nullable
    IDelegate<T> getDelegateForViewType(int viewType);

    @Nullable
    AbsFallbackAdapterDelegate<T> getFallbackDelegate();
}
