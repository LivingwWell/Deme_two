package com.example.lee.deme_two.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.lee.deme_two.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BeginActivity extends AppCompatActivity {
    @OnClick(R.id.back_begin)
    public void back_begin(){
        BeginActivity.this.finish();
    }
    @OnClick(R.id.share)
    public void share(){
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"迪士普好啊");
        startActivity(Intent.createChooser(intent,"分享"));
    }
    @OnClick(R.id.wechat)
    public void wechat(){
        Intent wechatintent=new Intent(Intent.ACTION_SEND);
        wechatintent.setPackage("com.tencent.mm");
        wechatintent.setType("text/plain");
        wechatintent.putExtra(Intent.EXTRA_TEXT,"迪士普好啊");
        startActivity(wechatintent);
    }
    @OnClick(R.id.email)
    public void email(){
        Intent emailintent=new Intent(Intent.ACTION_SEND);
        emailintent.setType("message/rfc822");
        emailintent.putExtra(Intent.EXTRA_EMAIL,"");
        emailintent.putExtra(Intent.EXTRA_TEXT, "迪士普好啊");
        emailintent.putExtra(Intent.EXTRA_SUBJECT, "迪士普好啊");
        startActivity(Intent.createChooser(emailintent,"send mail"));
    }
    @OnClick(R.id.msg)
    public void msg(){
        Uri sms=Uri.parse("smsto:");
        Intent msgintent=new Intent(Intent.ACTION_VIEW,sms);
        msgintent.putExtra("sms_body","迪士普好啊");
        startActivity(msgintent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_begin);
        ButterKnife.bind(this);
    }
}
