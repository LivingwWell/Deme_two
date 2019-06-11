package com.example.lee.deme_two.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.blankj.utilcode.utils.ToastUtils;
import com.example.lee.deme_two.R;
import com.example.lee.deme_two.activity.JoinActivity;
import com.example.lee.deme_two.activity.MediaActivity;
import com.example.lee.deme_two.activity.SWCameraStreamingActivity;
import com.example.lee.deme_two.adpter.LiveAdpter;
import com.example.lee.deme_two.data.Live;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FragmentB extends Fragment {

@BindView(R.id.Recycler_zhibo)
RecyclerView Recycler_zhibo;
    private Unbinder unbinder;

    private String title2;
    private View view;
    private static final String KEY = "title";
    @BindView(R.id.fb_bt)
    public FloatingActionButton fb_bt;
    private Live[] lives={new Live("迪士普公司年会直播",R.drawable.logo,"rtmp://pili-live-rtmp.live.dsppa.com/dsppa/dsppa")};
    private List<Live> livelist=new ArrayList<>();
    private LiveAdpter liveAdpter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_b, container, false);
        unbinder = ButterKnife.bind(this, view);
        fb_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),SWCameraStreamingActivity.class);
                startActivity(intent);
                ToastUtils.showShortToast(getContext(),"直播间");
            }
        });
        livelist.add(lives[0]);
        GridLayoutManager layoutManager=new GridLayoutManager(this.getContext(),2,GridLayoutManager.VERTICAL,false);
        Recycler_zhibo.setLayoutManager(layoutManager);
        liveAdpter=new LiveAdpter(livelist);
        liveAdpter.setOnItemClickLitener(new LiveAdpter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(getContext(),MediaActivity.class);
                intent.putExtra("videoPath",livelist.get(0).getLiveurl());
                startActivity(intent);
            }
        });
        Recycler_zhibo.setAdapter(liveAdpter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
