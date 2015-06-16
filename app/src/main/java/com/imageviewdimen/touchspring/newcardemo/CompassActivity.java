package com.imageviewdimen.touchspring.newcardemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.imageviewdimen.touchspring.newcardemo.app.LocationApplication;

/**
 * Created by KIM on 2015/6/16 0016.
 */
public class CompassActivity extends Activity  {
    private Button btn_back;
    private ImageView mPointer;
    private TextView tv_direction;
    private LinearLayout mAngleLayout;
    /**
     * 当前浮点方向
     */
    private int mDirection;

    /**
     *  目标浮点方向
     */
    private int mTargetDirection;

    /**
     * 百度地图 LocationClient
     */
    private LocationClient mLocationClient;

    /**
     * 是否停止指南针旋转的标志位
     */
    private boolean mStopDrawing;

    /**
     * 最多旋转一周，即360°
     */
    private final float MAX_ROATE_DEGREE = 1.0f;

    /**
     * 动画从开始到结束，变化率是一个加速的过程,就是一个动画速率
     */
    private AccelerateInterpolator mInterpolator;



    protected final Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x01:
                    //tv_direction.setText(msg.obj+"");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_transport);
        initView();
        initLocation();
    }

    private void initView() {
        btn_back = (Button)findViewById(R.id.btn_back);
        mPointer = (ImageView)findViewById(R.id.compass_pointer);
        //tv_direction = (TextView)findViewById(R.id.tv_direction);
        mAngleLayout = (LinearLayout) findViewById(R.id.layout_angle);

        mDirection = 0;// 初始化起始方向
        mStopDrawing = true;
        mInterpolator = new AccelerateInterpolator();// 实例化加速动画对象
    }



    private void initLocation(){
        mLocationClient = ((LocationApplication)getApplication()).mLocationClient;
        InitLocation();
        mLocationClient.start();
    }



    private void InitLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
        option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000);//设置发起定位请求的间隔时间为
        option.setIsNeedAddress(true);//返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);
        option.setOpenGps(true);
        mLocationClient.setLocOption(option);
    }

    protected  Runnable mCompassViewUpdater = new Runnable() {
        @Override
        public void run() {
            mTargetDirection =(int) LocationApplication.tv_dirction;
            if (mPointer != null && !mStopDrawing) {
                if (mDirection != mTargetDirection) {
                    // calculate the short routine
                    /*float to = mTargetDirection;
                    if (to - mDirection > 180) {
                        to -= 360;
                    } else if (to - mDirection < -180) {
                        to += 360;
                    }
                    // limit the max speed to MAX_ROTATE_DEGREE
                    float distance = to - mDirection;
                    if (Math.abs(distance) > MAX_ROATE_DEGREE) {
                        distance = distance > 0 ? MAX_ROATE_DEGREE
                                : (-1.0f * MAX_ROATE_DEGREE);
                    }
                    // need to slow down if the distance is short
                    mDirection = normalizeDegree(mDirection
                            + ((to - mDirection) * mInterpolator
                            .getInterpolation(Math.abs(distance) > MAX_ROATE_DEGREE ? 0.4f
                                    : 0.3f)));// 用了一个加速动画去旋转图片，很细致*/
                    //旋转动画
                    AnimationSet animationSet = pointerAnimator(mDirection, mTargetDirection);
                    mPointer.startAnimation(animationSet);
                    //更新textview
                    updateDirection();

                    mDirection = mTargetDirection;
                    //更新textview
                    Message message = new Message();
                    message.what = 0x01;
                    message.obj = mTargetDirection;
                    mHandler.sendMessage(message);
                   // mPointer.updateDirection(mDirection);// 更新指南针旋转
                    mHandler.postDelayed(mCompassViewUpdater, 5000);// 5000毫米后重新执行自己，比定时器好
                }
            }

        }
    };

    /**
     * 更新度数
     */
    private void updateDirection() {
        LinearLayout.LayoutParams lp = new  LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        mAngleLayout.removeAllViews();
        // 下面是根据方向度数显示度数图片数字
        int direction2 = (int) mTargetDirection;
        boolean show = false;
        if (direction2 >= 100) {
            mAngleLayout.addView(getNumberImage(direction2 / 100));
            direction2 %= 100;
            show = true;
        }
        if (direction2 >= 10 || show) {
            mAngleLayout.addView(getNumberImage(direction2 / 10));
            direction2 %= 10;
        }
        mAngleLayout.addView(getNumberImage(direction2));
        // 下面是增加一个°的图片
        ImageView degreeImageView = new ImageView(this);
        degreeImageView.setImageResource(R.drawable.degree);
        degreeImageView.setLayoutParams(lp);

        mAngleLayout.addView(degreeImageView);
    }



    @Override
    protected void onResume() {
        super.onResume();
        mStopDrawing = false;
        mHandler.postDelayed(mCompassViewUpdater, 5000);// 5000毫秒执行一次更新指南针图片旋转
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStopDrawing = true;
    }


    /**
     * 速度指针偏移动画
     * @param from
     * @param to
     * @return
     */
    public AnimationSet pointerAnimator(float from,float to){
        AnimationSet animationSet = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(from,to, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        //根据偏移角度计算偏移时间
        int time =Math.abs((int)((to - from)/360*3000));
        rotate.setDuration(time);
        animationSet.addAnimation(rotate);
        animationSet.setFillAfter(true);
        //设置动画速率
        animationSet.setInterpolator( new DecelerateInterpolator());
        return animationSet;
    }




    // 获取方向度数对应的图片，返回ImageView
    private ImageView getNumberImage(int number) {
        ImageView image = new ImageView(this);
        LinearLayout.LayoutParams lp = new  LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        switch (number) {
            case 0:
                image.setImageResource(R.drawable.number_0);
                break;
            case 1:
                image.setImageResource(R.drawable.number_1);
                break;
            case 2:
                image.setImageResource(R.drawable.number_2);
                break;
            case 3:
                image.setImageResource(R.drawable.number_3);
                break;
            case 4:
                image.setImageResource(R.drawable.number_4);
                break;
            case 5:
                image.setImageResource(R.drawable.number_5);
                break;
            case 6:
                image.setImageResource(R.drawable.number_6);
                break;
            case 7:
                image.setImageResource(R.drawable.number_7);
                break;
            case 8:
                image.setImageResource(R.drawable.number_8);
                break;
            case 9:
                image.setImageResource(R.drawable.number_9);
                break;
        }
        image.setLayoutParams(lp);
        return image;
    }



}
