package com.example.lee.deme_two.adpter;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.utils.ToastUtils;
import com.example.lee.deme_two.Utils.NotificationUtils;
import com.example.lee.deme_two.activity.BeginActivity;
import com.example.lee.deme_two.activity.JoinActivity;
import com.example.lee.deme_two.R;
import com.example.lee.deme_two.activity.MyScheduleActivity;
import com.example.lee.deme_two.activity.NewMeetActivity;
import com.example.lee.deme_two.data.Item;
import com.example.lee.deme_two.data.ItemOne;
import com.example.lee.deme_two.data.ItemTwo;
import com.example.lee.deme_two.data.ItemThree;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.lee.deme_two.R2.string.app_name;


public class MyAdapter extends RecyclerView.Adapter<RecyclerView .ViewHolder>{

    public final static int TYPE_FOOTER=1;
    public final static int TYPE_HEARD=2;
    public final static int TYPE_NORMAL=3;
    private LayoutInflater mLayoutInflater;
    private Context mContext;


    public MyAdapter(Context context){
        mLayoutInflater=LayoutInflater.from(context);
    }

    //存放个个list的type
    private List<Integer>types=new ArrayList<>();

    //存放list的type,键是type,值是大小
    private Map<Integer,Integer>mPosition=new HashMap<>();

    public void clearType(){
        types.clear();
        mPosition.clear();
    }

    private List<ItemOne> item1;
    private List<ItemTwo>item2;
    private List<ItemThree>item3;
    public void addList(List<ItemOne>list1, List<ItemTwo>list2,List<ItemThree>list3){
        addListByType(TYPE_FOOTER,list1);
        addListByType(TYPE_HEARD,list2);
        addListByType(TYPE_NORMAL,list3);

        item1=list1;
        item2=list2;
        item3=list3;
    }

    //使用此方法从mianactivty获取数据，这样就不用从构造方法里传数据了
    private void addListByType(int type,List list){
        mPosition.put(type,types.size());
        for (int i = 0; i < list.size(); i++) {
            types.add(type);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return types.get(position);
    }

public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        //根据viewType区分不同的布局
        switch (viewType){
            case TYPE_FOOTER:
                return new TypeOneHolder(mLayoutInflater.inflate(R.layout.item_head,parent,false));
            case TYPE_HEARD:
                return new TypeTwoHolder(mLayoutInflater.inflate(R.layout.item_header,parent,false));
            case TYPE_NORMAL:
                return new TypeThreeHolder(mLayoutInflater.inflate(R.layout.item_list,parent,false));
        }
        return null;
}
public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position){
        int viewType=getItemViewType(position);
        //获取每个VIEW在列表里的相对位置
        int realPositions=position-mPosition.get(viewType);
    //    System.out.println("realPositions == "+realPositions+"viewType == "+viewType +"mPosition == "+mPosition.toString());
        switch (viewType){
            case Item.TYPE_ONE:
                //根据Type设置ITem的监听事件
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onClick(View view) {
                        //ToastUtils.showShortToast(mLayoutInflater.getContext(),""+item1.get(position).getName());
                        switch (position){
                            case 0:
                                Intent intent=new Intent(holder.itemView.getContext(),JoinActivity.class);
                                      holder.itemView.getContext().startActivity(intent);
                               // ToastUtils.showShortToast(mLayoutInflater.getContext(),"加入会议");
                                break;
                            case 1:
                                Intent intent1=new Intent(holder.itemView.getContext(),BeginActivity.class);
                                holder.itemView.getContext().startActivity(intent1);
                                //ToastUtils.showShortToast(mLayoutInflater.getContext(),"发起会议");
                                break;
                            case 2:
                                ToastUtils.showShortToast(mLayoutInflater.getContext(),"主持会议");
                                break;
                            case 3:
                                Intent intent2=new Intent(holder.itemView.getContext(),NewMeetActivity.class);
                                holder.itemView.getContext().startActivity(intent2);
//                              NotificationUtils notificationUtils=new NotificationUtils(   holder.itemView.getContext());
//                              notificationUtils.sendNotification("测试标题","测试内容");
                                break;
                            case 4:
                                ToastUtils.showShortToast(mLayoutInflater.getContext(),"新建直播");
                                break;
                            case 5:
                                Intent intent3=new Intent(holder.itemView.getContext(), MyScheduleActivity.class);
                                holder.itemView.getContext().startActivity(intent3);
                                break;
                        }
                    }
                });
                ((TypeOneHolder)holder).bindHolder(item1.get(realPositions));
                break;

            case Item.TYPE_TWO:
                  ((TypeTwoHolder)holder).bindHolder(item2.get(realPositions));
                    break;

            case Item.TYPE_THREE:
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShortToast(mLayoutInflater.getContext(),""+item3.get(position-7).getName()+realPositions);
                    }
                });
                ((TypeThreeHolder)holder).bindHolder(item3.get(realPositions));
               // TypeThreeHolder typeThreeHolder= (TypeThreeHolder) holder;

                break;
        }
}
public int getItemCount(){
        return types.size();
}
}
