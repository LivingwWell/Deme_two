package com.example.lee.deme_two.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lee.deme_two.R;
import com.example.lee.deme_two.data.ItemOne;

public class TypeOneHolder extends TypeAbstarctViewHolder<ItemOne> {
    private ImageView imageView;
    private TextView name;

    public TypeOneHolder(View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.name);
        imageView=itemView.findViewById(R.id.imageView);
    }

    @Override
    public void bindHolder(ItemOne item) {
        name.setText(item.name);
        imageView.setImageResource(item.imgsrc);
    }


}
