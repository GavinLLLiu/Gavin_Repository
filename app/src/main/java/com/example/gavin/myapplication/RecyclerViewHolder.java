package com.example.gavin.myapplication;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Gavin
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private OnItemClick onItemClick;
    protected AppCompatTextView itemTxt;

    public RecyclerViewHolder(View itemView, OnItemClick onItemClick) {
        super(itemView);
        this.onItemClick = onItemClick;
        itemTxt = itemView.findViewById(R.id.item_name_txt);

        itemTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemClick.onItemClick(getAdapterPosition());
    }
}
