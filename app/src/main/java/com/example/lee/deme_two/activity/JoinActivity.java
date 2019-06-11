package com.example.lee.deme_two.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;

import com.blankj.utilcode.utils.ToastUtils;
import com.example.lee.deme_two.R;
import com.example.lee.deme_two.data.ItemThree;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class JoinActivity extends AppCompatActivity {
    public static boolean onCamera;
    public static boolean onAudio;

    public static String mUserName;
    public static String mRoomName;
    public static String meeting;

    @BindView(R.id.back_join)
    ImageView back_join;
    @BindView(R.id.switch1)
    Switch switch1;
    @OnCheckedChanged(R.id.switch1)
     void switch1(boolean ischcked){
        if (ischcked){
            onCamera=true;
        }else {
            onCamera=false;
        }
}

    @BindView(R.id.switch2)
    Switch switch2;
    @OnCheckedChanged(R.id.switch2)
    void switch2(boolean ischcked){
        if (ischcked){
            onAudio=true;
        }else {
            onAudio=false;
        }
        Log.d("ischcked", "switch2: "+switch2.isChecked()+"is"+onAudio);
    }
    @OnClick(R.id.back_join)
    public void back_join() {
        JoinActivity.this.finish();
    }
    String token;
    @OnClick(R.id.join)
    public void join() {
        meeting=et_meeting.getText().toString().trim();
        name=et_name.getText().toString();

        mRoomName=meeting;
        mUserName=name;
        Log.d("JoinActivity", "name: "+name);
        if (name.equals("111")){
          token="4XWwdV_IKN4slV7WNMNtGTo8Ahp9aqq-8I07S5RE:ivwqHYgukc3yXyQR40sppehucxc=:eyJhcHBJZCI6ImR5ajBkaTlibyIsInJvb21OYW1lIjoidGVzdDk5OSIsInVzZXJJZCI6IjExMSIsImV4cGlyZUF0IjoxNTQ4ODk3NjU0LCJwZXJtaXNzaW9uIjoidXNlciJ9";
        }else if (name.equals("222")){
            token="4XWwdV_IKN4slV7WNMNtGTo8Ahp9aqq-8I07S5RE:vsAGMsUSoA5TLtE2dXyiluEt474=:eyJhcHBJZCI6ImR5ajBkaTlibyIsInJvb21OYW1lIjoidGVzdDk5OSIsInVzZXJJZCI6IjIyMiIsImV4cGlyZUF0IjoxNTQ4ODk3NjU0LCJwZXJtaXNzaW9uIjoiYWRtaW4ifQ==";
        }

        if (TextUtils.isEmpty(et_meeting.getText())||TextUtils.isEmpty(et_name.getText())){
            ToastUtils.showShortToast(this,"房间号和名字不能为空！");
        }else {
            savelate();
            Intent intent4 = new Intent(this, SWCameraStreamingActivity.class);
            intent4.putExtra(RoomLiveActivity.EXTRA_ROOM_ID,mRoomName.trim());
            intent4.putExtra(RoomLiveActivity.EXTRA_USER_ID,mUserName);
            intent4.putExtra(RoomLiveActivity.EXTRA_ROOM_TOKEN,token);
            startActivity(intent4);
        }

    }

    @BindView(R.id.et_meeting)
    EditText et_meeting;
    @BindView(R.id.et_place)
    EditText et_name;

    public static String list_time;
    public  String name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        ButterKnife.bind(this);
    }
   public static List itemThrees = new ArrayList<>();
    private void savelate(){
        ItemThree three = new ItemThree();

//        SharedPreferences sharedPreferences=this.getSharedPreferences("list",Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date(System.currentTimeMillis());
        list_time=simpleDateFormat.format(date);
        three.time=list_time;
        three.name=meeting;
        three.list_imager = R.drawable.title;
        itemThrees.add(three);
//        editor.putString("time",list_time);
//        editor.putString("name",meeting);
//        editor.commit();
    }
}
