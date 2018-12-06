package com.example.gavin.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Gavin
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private OnItemClick onItemClick;
    private List<String> data;

    public RecyclerViewAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_adapter_item, parent, false);
        return new RecyclerViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.itemTxt.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        if (data == null || data.isEmpty()) {
            return 0;
        } else {
            return data.size();
        }
    }

    public void setItems(List<String> strings) {
        data = strings;
        notifyDataSetChanged();
    }

    public String getItem(int position) {
        return data.get(position);
    }

    public void deleteItem(int position){
        data.remove(data.get(position));
        notifyDataSetChanged();
    }
}
