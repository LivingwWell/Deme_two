package com.example.lee.deme_two.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lee.deme_two.R;
import com.example.lee.deme_two.data.ItemThree;

public class TypeThreeHolder extends TypeAbstarctViewHolder<ItemThree> {
    private TextView name;
    private TextView time;
    private ImageView list_imager;
    public TypeThreeHolder(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.list_name);
        time=itemView.findViewById(R.id.list_numb);
        list_imager=itemView.findViewById(R.id.list_imager);
    }

    @Override
    public void bindHolder(ItemThree item) {
        name.setText(item.name);
        time.setText(item.time);
        list_imager.setImageResource(item.list_imager);

    }
}
