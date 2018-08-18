package com.ibbhub.ibbadapterdelegate.sample.adapterdelegates;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.ibbhub.adapterdelegate.AbsFallbackAdapterDelegate;
import com.ibbhub.ibbadapterdelegate.sample.R;
import com.ibbhub.ibbadapterdelegate.sample.model.DisplayableItem;

import java.util.List;

/**
 * @author Hannes Dorfmann
 */
public class ReptilesFallbackDelegate extends AbsFallbackAdapterDelegate<List<DisplayableItem>> {

    private LayoutInflater inflater;

    public ReptilesFallbackDelegate(Activity activity) {
        inflater = activity.getLayoutInflater();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_unknown_reptile, parent, false);
        return new ReptileFallbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        super.onBindViewHolder(items, position, holder, payloads);
    }

    class ReptileFallbackViewHolder extends RecyclerView.ViewHolder {
        public ReptileFallbackViewHolder(View itemView) {
            super(itemView);
        }
    }
}
