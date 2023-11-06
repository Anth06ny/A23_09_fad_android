package com.amonteiro.a23_09_fad_android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amonteiro.a23_09_fad_android.beans.WindBean;
import com.amonteiro.a23_09_fad_android.databinding.RowWeatherBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends ListAdapter<WindBean, WeatherAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {
        RowWeatherBinding binding;

        public ViewHolder(RowWeatherBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    static class Comparator extends DiffUtil.ItemCallback<WindBean> {

        @Override
        public boolean areItemsTheSame(@NonNull WindBean oldItem, @NonNull WindBean newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull WindBean oldItem, @NonNull WindBean newItem) {
            return oldItem.getSpeed() == newItem.getSpeed();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowWeatherBinding binding =  RowWeatherBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            WindBean item = getItem(position);
            holder.binding.tvtemp.setText(item.getSpeed() + "");
    }

    public WeatherAdapter() {
        super(new Comparator());
    }
}
