package com.example.lee.deme_two.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.LinearLayout;

import com.blankj.utilcode.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.example.lee.deme_two.DaoSession;
import com.example.lee.deme_two.DataBaseDao;
import com.example.lee.deme_two.R;
import com.example.lee.deme_two.Utils.BaseApplication;
import com.example.lee.deme_two.adpter.BrvahAdapter;
import com.example.lee.deme_two.data.DataBase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class MyScheduleActivity extends Activity {
    private RecyclerView mRecyclerView;
    private BrvahAdapter mBrvahAdapter;
    private LinearLayoutManager mLayoutManager;
    private DataBase dataBase;
    private  DataBaseDao dataBaseDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myschedule);
        mRecyclerView=findViewById(R.id.ms_recyclerview);

        List<DataBase> dataBases= ((BaseApplication) getApplication()).getDaoSession().loadAll(DataBase.class);
        mBrvahAdapter=new BrvahAdapter(queryAllList());
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);

        ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mBrvahAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
         //滑动删除
        mBrvahAdapter.enableSwipeItem();
        mBrvahAdapter.setOnItemSwipeListener(onItemSwipeListener);
        //拖拽
        mBrvahAdapter.enableDragItem(itemTouchHelper, R.id.Drag, true);
        mBrvahAdapter.setOnItemDragListener(onItemDragListener);

        //显示动画
        mBrvahAdapter.openLoadAnimation();
        mBrvahAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //显示空页面
        //mBrvahAdapter.setEmptyView();

        //下拉加载
         mBrvahAdapter.setUpFetchEnable(true);
         //开始位置
        mBrvahAdapter.setStartUpFetchPosition(2);
//        mBrvahAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
//            @Override
//            public void onUpFetch() {
//                startUpFetch();
//            }
//        });

        mRecyclerView.setAdapter(mBrvahAdapter);
    }


    public List queryAllList(){
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        QueryBuilder<DataBase> qb = daoSession.queryBuilder(DataBase.class);
        List<DataBase> list = qb.list(); // 查出所有的数据
        return list;
    }

    public Long getID(int pos){
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        QueryBuilder<DataBase> qb = daoSession.queryBuilder(DataBase.class);
        List<DataBase> list = qb.list(); // 查出所有的数据
         Long id=list.get(pos).getId();
        return id;
    }

    public void deleteDB(Long id){
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        daoSession.getDataBaseDao().deleteByKey(id);
    }

    OnItemDragListener onItemDragListener = new OnItemDragListener() {
        @Override
        public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos){
            ToastUtils.showShortToast(MyScheduleActivity.this,""+pos);
        }
        @Override
        public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
        }
        @Override
        public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {

            ToastUtils.showShortToast(MyScheduleActivity.this,""+pos);
        }
    };

    OnItemSwipeListener onItemSwipeListener = new OnItemSwipeListener() {
        @Override
        public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int pos) {
            deleteDB(getID(pos));
        }
        @Override
        public void clearView(RecyclerView.ViewHolder viewHolder, int pos) {

        }
        @Override
        public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int pos) {
            Log.d("MyScheduleActivity","onItemSwiped:"+pos);
        }

        @Override
        public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dX, float dY, boolean isCurrentlyActive) {
            canvas.drawColor(ContextCompat.getColor(MyScheduleActivity.this, R.color.color_light_blue));
        }
    };

    private void startUpFetch(){
       mBrvahAdapter.setUpFetching(true);
       mRecyclerView.postDelayed(new Runnable() {
           @Override
           public void run() {
               mBrvahAdapter.addData(0,dataBase);
               mBrvahAdapter.setUpFetching(false);
           }
       },300);

    }
}
