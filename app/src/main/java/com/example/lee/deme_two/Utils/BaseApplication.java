package com.example.lee.deme_two.Utils;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.lee.deme_two.DaoMaster;
import com.example.lee.deme_two.DaoSession;
import com.example.lee.deme_two.DataBaseDao;
import com.example.lee.deme_two.data.DataBase;
import com.igexin.sdk.PushManager;
import com.qiniu.droid.rtc.QNRTCEnv;
import com.qiniu.pili.droid.streaming.StreamingEnv;


public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //个推
        PushManager.getInstance().initialize(this.getApplicationContext(),DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(),DemoIntentService.class);
        // 七牛云
        StreamingEnv.init(getApplicationContext());
        QNRTCEnv.init(getApplicationContext());
        //GreenDao
        initGreenDao();
    }
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "Base.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;
    public DaoSession getDaoSession() {
        return daoSession;
    }
}
