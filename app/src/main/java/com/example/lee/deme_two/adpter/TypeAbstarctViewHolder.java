package com.example.lee.deme_two.adpter;



import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class TypeAbstarctViewHolder<T> extends RecyclerView.ViewHolder {

    public TypeAbstarctViewHolder( View itemView) {
        super(itemView);
    }

    public abstract void bindHolder( T item);
}
