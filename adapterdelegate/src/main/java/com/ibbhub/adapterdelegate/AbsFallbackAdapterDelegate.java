package com.ibbhub.adapterdelegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * This class can be used as base class for a fallback delegate {@link
 * AdapterDelegatesManager#setFallbackDelegate(AbsFallbackAdapterDelegate)}.
 *
 * @author Hannes Dorfmann
 * @since 1.1.0
 */
public abstract class AbsFallbackAdapterDelegate<T> extends AdapterDelegate<T> {

    protected AdapterListener<T> listener;

    public void setListener(AdapterListener<T> listener) {
        this.listener = listener;
    }

    /**
     * Not needed, because never called for fallback adapter delegates.
     *
     * @param items    The data source of the Adapter
     * @param position The position in the datasource
     * @return true
     */
    @Override
    public final boolean isForViewType(@NonNull Object items, int position) {
        return true;
    }

    @Override
    public void onBindViewHolder(@NonNull final T items, final int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(items, position);
                }

            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onLongClick(items, position);
                    return true;
                }
                return false;
            }
        });
    }
}
