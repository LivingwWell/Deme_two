package com.example.lee.deme_two.adpter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.ToastUtils;
import com.example.lee.deme_two.R;
import com.example.lee.deme_two.activity.MediaActivity;
import com.example.lee.deme_two.data.Live;
import com.example.lee.deme_two.fragment.FragmentB;

import java.util.List;

public class LiveAdpter extends RecyclerView.Adapter<LiveAdpter.ViewHolder> {
    private Context mContext;
    private List<Live>mLive;
    private OnItemClickLitener   mOnItemClickLitener;
    @NonNull
    @Override
    public LiveAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (mContext==null){
            mContext=viewGroup.getContext();
        }
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_zhibo,viewGroup,false);
        return new ViewHolder(view);
    }

    public LiveAdpter(List<Live>liveList){
        mLive=liveList;
    }
    @Override
    public void onBindViewHolder(@NonNull LiveAdpter.ViewHolder viewHolder, int i) {
       Live live=mLive.get(i);
       viewHolder.text_title.setText(live.getTitle());
       viewHolder.img_zhibo.setImageResource(live.getImgurl());
         if (mOnItemClickLitener!=null){
             viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                  mOnItemClickLitener.onItemClick(v,i);
                 }
             });
         }

    }

    @Override
    public int getItemCount() {
        return mLive.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView img_zhibo;
        TextView text_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cardView);
            img_zhibo=itemView.findViewById(R.id.img_zhibo);
            text_title=itemView.findViewById(R.id.text_tltle);
        }
    }

    public interface OnItemClickLitener{
        void onItemClick(View view ,int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener){
        this.mOnItemClickLitener=mOnItemClickLitener;
    }
}
