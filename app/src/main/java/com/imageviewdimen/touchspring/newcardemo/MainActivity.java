package com.imageviewdimen.touchspring.newcardemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.imageviewdimen.touchspring.newcardemo.app.LocationApplication;
import com.imageviewdimen.touchspring.newcardemo.fragment.BaoyangFragment;
import com.imageviewdimen.touchspring.newcardemo.fragment.JiFenFragment;
import com.imageviewdimen.touchspring.newcardemo.fragment.MyFragment;


public class MainActivity extends FragmentActivity {
    /**
     * fragment
     */
    private Fragment fragment_bao;
    private Fragment fragment_my;
    private Fragment fragment_ji;

    /**
     * 底部选择控件RadioGroup
     */
    private RadioGroup radioGroup;

    /**
     * fragment管理器
     */
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    //toolbar
    /**
     * 显示位置图标
     */
    private ImageButton ib_titleClick;
    /**
     * toobar左边显示城市TextView
     */
    private TextView tv_titleLeft;
    /**
     * 中间textView
     */
    private TextView tv_titleCenter;

    /**
     *toolbar右边textView
     */
    private TextView tv_titleRightR;
    private TextView tv_titleRightL;
    /**
     * 百度地图 LocationClient
     */
    private LocationClient mLocationClient;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what == 0x01){
                tv_titleLeft.setText(msg.obj.toString());
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initAdapter();

    }

    private void initview() {

        radioGroup  = (RadioGroup)findViewById(R.id.rg_check);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        if(fragment_bao == null){
            fragment_bao = new BaoyangFragment();
        }

        fragmentTransaction.add(R.id.fram_main,fragment_bao).commit();
        ib_titleClick = (ImageButton)findViewById(R.id.ib_titleClick);
        tv_titleLeft = (TextView)findViewById(R.id.tv_titleLeft);
        tv_titleCenter = (TextView)findViewById(R.id.tv_titleCenter);
        tv_titleRightR = (TextView)findViewById(R.id.tv_titleRightR);
        tv_titleRightL = (TextView)findViewById(R.id.tv_titleRightL);

        initLocation();
    }

    /**
     * 初始化所在城市
     */
    private void initLocation() {
        mLocationClient = ((LocationApplication)getApplication()).mLocationClient;
        InitLocation();
        mLocationClient.start();
        String addr = mLocationClient.getLastKnownLocation().getAddrStr();
        Message message =mHandler.obtainMessage();
        message.what = 0x01;
        message.obj =addr;
        message.sendToTarget();

    }


    private void initAdapter() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                hide(transaction);
                setbar(checkedId);
                switch (checkedId){
                    case R.id.rb_baoyang:
                        if(fragment_bao ==null){
                            fragment_bao = new BaoyangFragment();
                            transaction.add(R.id.fram_main,fragment_bao);
                        }
                        transaction.show(fragment_bao);
                        break;
                    case R.id.rb_jifen:
                        if(fragment_ji == null){
                            fragment_ji = new JiFenFragment();
                            transaction.add(R.id.fram_main,fragment_ji);
                        }
                        transaction.show(fragment_ji);
                        break;
                    case R.id.rb_my:
                        if(fragment_my == null){
                            fragment_my = new MyFragment();
                            transaction.add(R.id.fram_main,fragment_my);
                        }
                        transaction.show(fragment_my);
                        break;
                }
                transaction.commit();
            }
        });
    }



    private void hide(FragmentTransaction transaction) {
        if(fragment_bao != null)
        {
            transaction.hide(fragment_bao);
        }
        if(fragment_ji != null){
            transaction.hide(fragment_ji);
        }
        if(fragment_my != null){
            transaction.hide(fragment_my);
        }
    }

    private void setbar(int checkedId) {
        switch (checkedId){
            case R.id.rb_baoyang:
                ib_titleClick.setVisibility(View.VISIBLE);
                tv_titleLeft.setVisibility(View.VISIBLE);
                tv_titleRightL.setVisibility(View.VISIBLE);
                tv_titleRightR.setVisibility(View.VISIBLE);
                tv_titleCenter.setVisibility(View.GONE);
                tv_titleRightL.setText("登陆");
                tv_titleRightR.setText("注册");
                break;
            case R.id.rb_jifen:
                Log.i("INIT",ib_titleClick +" 2");
                ib_titleClick.setVisibility(View.GONE);
                tv_titleLeft.setVisibility(View.GONE);
                tv_titleRightL.setVisibility(View.GONE);
                tv_titleCenter.setVisibility(View.VISIBLE);
                tv_titleRightR.setVisibility(View.VISIBLE);
                tv_titleCenter.setText("积分通服务");
                tv_titleRightR.setText("去付款");
                break;
            case R.id.rb_my:
                ib_titleClick.setVisibility(View.GONE);
                tv_titleLeft.setVisibility(View.GONE);
                tv_titleRightL.setVisibility(View.GONE);
                tv_titleRightR.setVisibility(View.GONE);
                tv_titleCenter.setVisibility(View.VISIBLE);
                tv_titleCenter.setText("我的管理");
                break;
        }

    }


    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);
        mLocationClient.setLocOption(option);
    }
}
