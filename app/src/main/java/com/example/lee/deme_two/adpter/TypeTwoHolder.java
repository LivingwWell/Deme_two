package com.example.lee.deme_two.adpter;

import android.view.View;
import android.widget.TextView;

import com.example.lee.deme_two.R;
import com.example.lee.deme_two.data.ItemTwo;

public class TypeTwoHolder extends TypeAbstarctViewHolder<ItemTwo> {
    private TextView header;

    public TypeTwoHolder(View itemView) {
        super(itemView);
        header=itemView.findViewById(R.id.header);
    }

    @Override
    public void bindHolder(ItemTwo item) {
        header.setText(item.header);
    }
}
