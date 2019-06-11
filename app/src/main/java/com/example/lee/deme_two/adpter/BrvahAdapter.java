package com.example.lee.deme_two.adpter;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.lee.deme_two.R;
import com.example.lee.deme_two.data.DataBase;

import org.w3c.dom.Text;

import java.util.List;

public class BrvahAdapter extends BaseItemDraggableAdapter<DataBase, BaseViewHolder> {

    public BrvahAdapter(List<DataBase> data) {
        super(R.layout.item_meeting, data);
    }
private View.OnClickListener mOnClickListener;
    @Override
    protected void convert(BaseViewHolder helper, DataBase item) {
        helper.setText(R.id.tx_theme,item.getTheme())
                .setText(R.id.tx_place,item.getPlace())
                .setText(R.id.tx_date,item.getDate())
        .setImageResource(R.id.imageView9, R.drawable.join);
        TextView theme=helper.getView(R.id.tx_theme);
        TextView place=helper.getView(R.id.tx_place);
        TextView data=helper.getView(R.id.tx_date);
        theme.setOnClickListener(mOnClickListener);
            }
}
