package com.example.lee.deme_two.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.utils.ToastUtils;
import com.example.lee.deme_two.DaoSession;
import com.example.lee.deme_two.R;
import com.example.lee.deme_two.Utils.BaseApplication;

import com.example.lee.deme_two.data.DataBase;

import org.feezu.liuli.timeselector.TimeSelector;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewMeetActivity extends Activity {
    @BindView(R.id.et_place)
    EditText et_place;
    @BindView(R.id.et_theme)
    EditText et_theme;
    @BindView(R.id.et_date)
    TextView et_date;
    @BindView(R.id.et_content)
    TextView et_content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newhuiyi);
        ButterKnife.bind(this);

        TimeSelector timeSelector = new TimeSelector(NewMeetActivity.this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                Toast.makeText(NewMeetActivity.this, time, Toast.LENGTH_SHORT).show();
                et_date.setText(time);
            }
        }, "2019-01-01 00:00", "2050-12-31 23:59:59", "0:00", "23:00");

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             timeSelector.setIsLoop(false);
             timeSelector.setMode(TimeSelector.MODE.YMDHM);
             timeSelector.show();
            }
        });
    }
    @OnClick(R.id.bt_add)
    public void bt_add(){
       if (et_theme.getText().toString().trim().equals("")){
           ToastUtils.showShortToast(this,"请输入会议主题");
       }else if (et_place.getText().toString().trim().equals("")){
           ToastUtils.showShortToast(this,"请输入会议地点");
       }else if (et_content.getText().toString().trim().equals("")){
           ToastUtils.showShortToast(this,"请输入会议主题");
       }else {
           DaoSession mDaoSession= ((BaseApplication)getApplication()).getDaoSession();
           DataBase dataBase=new DataBase(null,et_place.getText().toString()
                   ,et_content.getText().toString()
                   ,et_theme.getText().toString()
                   ,et_date.getText().toString());
           mDaoSession.insertOrReplace(dataBase);
           showDataList();
           finish();
       }

    }
    private void showDataList() {
    List<DataBase>dataBases= ((BaseApplication) getApplication()).getDaoSession().loadAll(DataBase.class);
    StringBuilder sb = new StringBuilder();
        for(DataBase dataBase:dataBases){
            // dataArea.setText("id:"+p);
            sb.append("id:").append(dataBase.getId())
                    .append("theme:").append(dataBase.getTheme())
                    .append("content:").append(dataBase.getContent())
                    .append("place:").append(dataBase.getPlace())
                    .append("data").append(dataBase.getDate())
                    .append("\n");
        }
        Log.d("newMeet", "showDataList: "+sb.toString());
    }

    public void deleteAll() {
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        daoSession.deleteAll(DataBase.class);
    }
    public void deleteData(DataBase s) {
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        daoSession.delete(s);
    }

    public void updataData(DataBase s) {
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        daoSession.update(s);
    }

    public List queryAllList(){
        DaoSession daoSession = ((BaseApplication) getApplication()).getDaoSession();
        QueryBuilder<DataBase> qb = daoSession.queryBuilder(DataBase.class);
        List<DataBase> list = qb.list(); // 查出所有的数据
        return list;
    }
}
