package com.rahulrv.tweetz.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rahulrv.tweetz.R;
import com.rahulrv.tweetz.databinding.TrendsRowLayoutBinding;
import com.rahulrv.tweetz.model.trends.TrendsItem;

import java.util.Collections;
import java.util.List;

/**
 *
 *
 */

public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.TrendsViewHolder> {

    private List<TrendsItem> trendsItems = Collections.emptyList();

    public TrendsAdapter(final List<TrendsItem> items) {
        this.trendsItems = items;
    }

    @Override
    public TrendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TrendsRowLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.trends_row_layout, parent, false);
        return new TrendsViewHolder(binding);
    }

    @Override public void onBindViewHolder(TrendsViewHolder holder, int position) {
        holder.update(trendsItems.get(position));
    }

    @Override public int getItemCount() {
        return trendsItems.size();
    }

    public static class TrendsViewHolder extends RecyclerView.ViewHolder {

        TrendsRowLayoutBinding binding;

        public TrendsViewHolder(TrendsRowLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void update(TrendsItem trendsItem) {
            binding.setTrend(trendsItem);
            binding.executePendingBindings();
        }
    }
}
