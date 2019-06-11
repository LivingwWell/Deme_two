package com.example.lee.deme_two.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.lee.deme_two.R;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.util.BitSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareActivity extends AppCompatActivity {
     @BindView(R.id.erweima)
    ImageView erweima;
     @OnClick(R.id.back_share)
     public void back_share(){
         finish();
     }
    @OnClick({R.id.imageView2,R.id.imageView3,R.id.imageView4})
    public void onViewClicked(View view){
         switch (view.getId()){
             case R.id.imageView2:
                 Intent wechatintent=new Intent(Intent.ACTION_SEND);
                 wechatintent.setPackage("com.tencent.mm");
                 wechatintent.setType("text/plain");
                 wechatintent.putExtra(Intent.EXTRA_TEXT,"迪士普好啊");
                 startActivity(wechatintent);
                 break;
             case R.id.imageView3:
                 Intent intent=new Intent(Intent.ACTION_SEND);
                 intent.setType("text/plain");
                 intent.putExtra(Intent.EXTRA_TEXT,"迪士普好啊");
                 startActivity(Intent.createChooser(intent,"分享"));
                 break;
             case R.id.imageView4:
                 Uri sms=Uri.parse("smsto:");
                 Intent msgintent=new Intent(Intent.ACTION_VIEW,sms);
                 msgintent.putExtra("sms_body","迪士普好啊");
                 startActivity(msgintent);
                 break;
         }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        Bitmap logo=CodeCreator.createQRCode("www.baidu.com",200,200,null);
        ButterKnife.bind(this);
       if (logo!=null){
           erweima.setImageBitmap(logo);
       }

    }
}
