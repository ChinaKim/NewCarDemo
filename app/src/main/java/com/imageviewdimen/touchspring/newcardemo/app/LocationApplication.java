package com.imageviewdimen.touchspring.newcardemo.app;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

/**
 * Created by KIM on 2015/5/11 0011.
 */
public class LocationApplication extends Application {
    public LocationClient mLocationClient;
    private MyLocationListener mListener;
    public  TextView tv_Addr;
    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new LocationClient(getApplicationContext());
        mListener = new  MyLocationListener();
        mLocationClient.registerLocationListener(mListener);
    }


    class  MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.i("TAGLocation1", "INF:" +"in");
            if (location == null)
                return ;
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());

            }
            setAddr(location.getCity());
            Log.i("TAGLocation1" ,"INF:"+ location.getCity());
        }
    }


    private void setAddr(String addr){
        if(tv_Addr != null){
            tv_Addr.setText(addr);
            tv_Addr.setTextColor(Color.BLUE);
        }
    }


}
