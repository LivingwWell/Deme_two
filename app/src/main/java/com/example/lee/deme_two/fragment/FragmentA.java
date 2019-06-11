package com.example.lee.deme_two.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.lee.deme_two.activity.JoinActivity;
import com.example.lee.deme_two.data.Item;
import com.example.lee.deme_two.data.ItemOne;
import com.example.lee.deme_two.data.ItemTwo;
import com.example.lee.deme_two.data.ItemThree;
import com.example.lee.deme_two.adpter.MyAdapter;
import com.example.lee.deme_two.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.OnClick;

public class FragmentA extends Fragment {

    private RecyclerView myRecycler;
    private MyAdapter myAdapter;
    List itemOnes = new ArrayList<>();
    List itemTwos = new ArrayList<>();
    List itemThrees = new ArrayList<>();
    private int src[] = {R.drawable.join, R.drawable.begain, R.drawable.zhuchi, R.drawable.yuyue, R.drawable.newhuiyi, R.drawable.daly};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fargment_a, container, false);
        myRecycler = view.findViewById(R.id.myRecycler);
        myAdapter = new MyAdapter(getActivity());
       init();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initData() {
        ItemOne itemOne = new ItemOne();
        ItemOne itemTwo = new ItemOne();
        ItemOne itemThree = new ItemOne();
        ItemOne itemFour = new ItemOne();
        ItemOne itemFive = new ItemOne();
        ItemOne itemSix = new ItemOne();
        itemOne.name = "加入会议";
        itemTwo.name = "发起会议";
        itemThree.name = "主持会议";
        itemFour.name = "预约会议";
        itemFive.name = "新建直播";
        itemSix.name = "我的日程";
        itemOne.imgsrc = src[0];
        itemTwo.imgsrc = src[1];
        itemThree.imgsrc = src[2];
        itemFour.imgsrc = src[3];
        itemFive.imgsrc = src[4];
        itemSix.imgsrc = src[5];
        itemOnes.add(itemOne);
        itemOnes.add(itemTwo);
        itemOnes.add(itemThree);
        itemOnes.add(itemFour);
        itemOnes.add(itemFive);
        itemOnes.add(itemSix);

        ItemTwo itemTwo1 = new ItemTwo();
        itemTwo1.header = "最近通话";
        itemTwos.add(itemTwo1);

        ItemThree three = new ItemThree();
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("list",Context.MODE_PRIVATE);
        three.name=sharedPreferences.getString("name","null");
        three.list_imager = R.drawable.title;
        three.time=sharedPreferences.getString("time","null");
        itemThrees.add(three);

        myAdapter.addList(itemOnes, itemTwos,JoinActivity.itemThrees);
        myAdapter.notifyDataSetChanged();
    }

    public void init() {
        //gridlayoutmanager构造参数里的2，指的是一行有几列
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        myRecycler.setLayoutManager(manager);
        initData();
        myRecycler.setAdapter(myAdapter);
        //设置占用的列数
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int i) {
                int type = myRecycler.getAdapter().getItemViewType(i);
                //若是TYPE_THREE，占用两列，否则占用一列
                if (type == Item.TYPE_THREE || type == Item.TYPE_TWO) {
                    return manager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();

            myAdapter.clearType();
            initData_2();


    }

    private void initData_2() {
        //resume的时候重新请求一次数据啊
        System.out.println(Arrays.toString(itemOnes.toArray()));
        System.out.println(Arrays.toString(itemTwos.toArray()));
        System.out.println(Arrays.toString(JoinActivity.itemThrees.toArray()));
        Collections.reverse(JoinActivity.itemThrees);
        myAdapter.addList(itemOnes, itemTwos,JoinActivity.itemThrees);
        myAdapter.notifyDataSetChanged();
    }
}
