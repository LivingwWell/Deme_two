package com.example.lee.deme_two;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.blankj.utilcode.utils.SizeUtils;
import com.blankj.utilcode.utils.ToastUtils;
import com.example.lee.deme_two.Utils.DemoIntentService;
import com.example.lee.deme_two.Utils.DemoPushService;
import com.example.lee.deme_two.activity.ShareActivity;
import com.example.lee.deme_two.adpter.MyPagerAdapter;
import com.example.lee.deme_two.adpter.TabEntity;
import com.example.lee.deme_two.fragment.FragmentB;
import com.example.lee.deme_two.fragment.FragmentA;
import com.example.lee.deme_two.fragment.FragmentC;
import com.example.lee.deme_two.fragment.FragmentD;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import com.igexin.sdk.PushManager;
import com.qiniu.droid.rtc.QNRTCEngine;
import com.qiniu.droid.rtc.QNRTCEnv;
import com.qiniu.pili.droid.streaming.StreamingEnv;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;
import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tablayout)
    CommonTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.more)
    Button more;
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @OnClick(R.id.tongzhi)
    public void tongzhi(){
        ToastUtils.showShortToast(this,"更多");
    }
    private EasyPopup mCirclePop;

    private String[] mTitles = {"会议", "直播", "文件夹", "设置"};
    int REQUEST_CODE_SCAN=1;
    private int[] mIconUnselectIds = {R.drawable.ic_people_black_24dp,
            R.drawable.ic_contact_mail_black_24dp,
            R.drawable.ic_folder_open_black_24dp,
            R.drawable.ic_person_black_24dp
    };
    private int[] mIconSelectIds = {
            R.drawable.ic_people_bule_24dp,
            R.drawable.ic_contact_mail_bule_24dp,
            R.drawable.ic_folder_open_bule_24dp,
            R.drawable.ic_person_bule_24dp};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    public void initData() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ArrayList<Fragment> datas = new ArrayList<Fragment>();
        datas.add(new FragmentA());
        datas.add(new FragmentB());
        datas.add(new FragmentC());
        datas.add(new FragmentD());
        myPagerAdapter.setData(datas);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(4);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tl_2();
    }

    /**
     * 绑定Tablayout和Viewpager
     */
    private void tl_2() {
        tablayout.setTabData(mTabEntities);
        tablayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tablayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(0);
    }

    private void initView() {
        initpop();
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showQQPop(v);
            }
        });
    }

    private void showQQPop(View view) {
       int offsetX = SizeUtils.dp2px(this, 20) - view.getWidth() / 2;
       int offsetY = (toolbar.getHeight() - view.getHeight()) / 2;
        mCirclePop.showAtAnchorView(view, YGravity.BELOW, XGravity.ALIGN_RIGHT, offsetX, offsetY);
    }
   protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

       if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
           if (data != null) {
               String content = data.getStringExtra(Constant.CODED_CONTENT);
               ToastUtils.showShortToast(this,"扫描结果为：" + content);
               Log.d("saoyisao", "saoyisao "+content);

           }
       }

   }
    private void initpop() {
        mCirclePop = new EasyPopup(MainActivity.this)
                .setAnimationStyle(R.style.RightTop2PopAnim)
                .setOnViewListener(new EasyPopup.OnViewListener() {
                    @Override
                    public void initViews(View view, EasyPopup easyPopup) {
                        View arrowView=view.findViewById(R.id.v_arrow);
                        view.findViewById(R.id.saoyisao).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(MainActivity.this,CaptureActivity.class);
                                startActivityForResult(intent,REQUEST_CODE_SCAN);
                                ToastUtils.showShortToast(MainActivity.this,"扫一扫");
                                mCirclePop.dismiss();
                            }
                        });
                        view.findViewById(R.id.xiazai).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showShortToast(MainActivity.this,"分享下载链接");
                                Intent intent=new Intent(MainActivity.this,ShareActivity.class);
                                startActivity(intent);
                                mCirclePop.dismiss();
                            }
                        });
                        arrowView.setBackground(new TriangleDrawable(TriangleDrawable.TOP,Color.parseColor("#ffffff")));
                    }
                })
                .setBackgroundDimEnable(true)
                .setContentView(R.layout.popup_window)
                .setFocusAndOutsideEnable(true)
                .apply();

    }

}
