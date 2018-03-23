package com.shura.rmct2.fence;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolylineOptions;
import com.shura.rmct2.R;
import com.shura.rmct2.fence.model.fenceBean;
import com.shura.rmct2.tool.CommonToolBar;

import java.text.SimpleDateFormat;
import java.util.Date;

/*import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;*/

/**
 * Created by lemon on 2017/10/25.
 * 新增围栏
 */

public class AddFenceActivity extends Activity implements LocationSource, AMapLocationListener {

    private AMapLocationClient mlocationClient = null;// 定位发起端
    private AMapLocationClientOption mLocationOption = null;// 定位参数
    private OnLocationChangedListener mListener = null;// 定位监听器
    // 标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    // 以前的定位点
    private LatLng oldLatLng;

    private AMap aMap;// 地图对象
    private MapView mMapView;
    private Vibrator vibrator;
    private LatLng centerLatLng;
    final String ACTION_GEO_FENCE = "geo fence action";
    final int REQ_LOCATION = 0x12;
    final int REQ_GEO_FENCE = 0x13;
    private IntentFilter intentFilter;
    private Button btn_setFenceName;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfence);

        CommonToolBar tb = (CommonToolBar) findViewById(R.id.toolbaraddfence);
        tb.setCenterTitle("新增围栏", 17, R.color.white);
        tb.setImmerseState(this, true);
        tb.setNavIcon(R.drawable.white_back_icon);
        tb.setNavigationListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tb.setRightTitle("完成", 14, R.color.white);
        tb.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了完成", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        mMapView = (MapView) findViewById(R.id.addfenceMap);
        btn_setFenceName = (Button) findViewById(R.id.btn_setFenceName);
        /**
         * 在地图上修改围栏名称
         */
        btn_setFenceName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddFenceActivity.this,"修改围栏名称",Toast.LENGTH_SHORT).show();

                final EditText et = new EditText(AddFenceActivity.this);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(AddFenceActivity.this);
                inputDialog.setTitle("修改围栏名称").setView(et);
                inputDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(),
                                et.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                        fenceBean f1 = new fenceBean();
                        f1.setFencename(et.getText().toString());
                        f1.setFencepic("图片喽");
                       /* f1.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId,BmobException e) {
                                if(e==null){
                                    Toast.makeText(getApplicationContext(),"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"创建数据失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });*/
                    }
                }).show();

            }
        });




        // 1.获取震动服务
        vibrator = (Vibrator) getApplicationContext().getSystemService(VIBRATOR_SERVICE);
/**
 * 生命周期方法，必须要重写。实现地图的生命周期管理
 */
        mMapView.onCreate(savedInstanceState);
        aMap = mMapView.getMap();// 获取地图对象

        // 设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        // 设置定位监听，实现LocationSource接口
        aMap.setLocationSource(this);
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        // 是否可触发定位并显示定位层
        aMap.setMyLocationEnabled(true);
        // 定位的小图标 默认是蓝点 其实就是一张图片
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.strokeColor(android.R.color.transparent);
        aMap.setMyLocationStyle(myLocationStyle);
        //处理进出地理围栏事件
        intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_GEO_FENCE);
        // 开始定位
        initLoc();
    }



    private void initLoc() {
        mlocationClient = new AMapLocationClient(getApplicationContext());
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位回调监听，这里要实现AMapLocationListener接口，AMapLocationListener接口只有onLocationChanged方法可以实现，
        // 用于接收异步返回的定位结果，参数是AMapLocation类型。
        mlocationClient.setLocationListener(this);
        // 设置定位模式为高精度模式，(将同时使用高德网络定位和GPS定位,优先返回精度高的定位)
        // Battery_Saving为低功耗模式(只是用网络)，Device_Sensors是仅设备模式（仅GPS）
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(5000);
        // 设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 启动定位
        mlocationClient.startLocation();
    }

    /** 绘制两个坐标点之间的线段,从以前位置到现在位置 */
    private void setUpMap(LatLng oldData, LatLng newData) {
        // 绘制一个大地曲线
        aMap.addPolyline((new PolylineOptions()).add(oldData, newData).geodesic(true).color(Color.GREEN));
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            // 定位成功回调信息，设置相关消息
            amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见官方定位类型表
            amapLocation.getLatitude();// 获取纬度
            amapLocation.getLongitude();// 获取经度
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(amapLocation.getTime());
            df.format(date);// 定位时间
            amapLocation.getAddress();// 地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。

            // 定位成功
            LatLng newLatLng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());

            // 如果不设置标志位，此时再拖动地图时，它会不断将地图移动到当前的位置
            // 如果是第一次定位
            if (isFirstLoc) {
                // 设置缩放级别
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                // 将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory
                        .changeLatLng(new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude())));
                // 点击定位按钮 能够将地图的中心移动到定位点
                mListener.onLocationChanged(amapLocation);


                // 获取定位信息
                StringBuffer buffer = new StringBuffer();
                buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity()
                        + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + ""
                        + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
                Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();

                // 记录第一次的定位信息
                oldLatLng = newLatLng;
                isFirstLoc = false;// 此后不再是第一次定位
            }
            // 位置有变化
            if (oldLatLng != newLatLng) {
                setUpMap(oldLatLng, newLatLng);
                oldLatLng = newLatLng;
            }

            // 设置地理围栏
            if (centerLatLng == null) {
                // 2.设置地理围栏中心,围栏中心应该是一个定值,这个定值是不是以摄像头所在的地方为准？
                centerLatLng = new LatLng(34.223556, 108.882886);

                Intent intent = new Intent(ACTION_GEO_FENCE);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, REQ_GEO_FENCE, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
				/* 100:是围栏半径（测试发现，设置的太小，不会发出广播）；-1：是超时时间（单位：ms，-1代表永不超时） */
                mlocationClient.addGeoFenceAlert("fenceId", centerLatLng.latitude, centerLatLng.longitude, 100, -1,pendingIntent);

                addCircle(centerLatLng, 500);
            } else {
                double latitude = amapLocation.getLatitude();
                double longitude = amapLocation.getLongitude();
                LatLng endLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
                // 计算量坐标点距离
                double distances = AMapUtils.calculateLineDistance(centerLatLng, endLatlng);
                Toast.makeText(this, "当前距离中心点：" + ((int) distances), Toast.LENGTH_LONG).show();
            }

        } else {
            // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            Toast.makeText(this, "定位失败，呜呜呜~~~~", Toast.LENGTH_LONG).show();
        }
    }

    public void addCircle(LatLng latLng, int radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeWidth(4);
        circleOptions.strokeColor(Color.RED);
        //circleOptions.fillColor(Color.YELLOW);
        aMap.addCircle(circleOptions);
    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        // 注册监听，进出地理围栏事件
        getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        // 处理进出地理围栏事件
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收广播
            if (intent.getAction().equals(ACTION_GEO_FENCE)) {
                Bundle bundle = intent.getExtras();
                // 根据广播的event来确定是在区域内还是在区域外
                int status = bundle.getInt("event");
                String geoFenceId = bundle.getString("fenceId");
                if (status == 1) {
                    Toast.makeText(getApplicationContext(), "进入地理围栏~", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(3000);
                    // textView.setText("In");
                } else if (status == 2) {
                    // 离开围栏区域
                    Toast.makeText(getApplicationContext(), "离开地理围栏~", Toast.LENGTH_LONG).show();
                    vibrator.vibrate(3000);
                    // textView.setText("Out");
                }
            }
        }
    };

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        // 取消监听
        getApplicationContext().unregisterReceiver(broadcastReceiver);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();// 销毁定位客户端。
        // 销毁定位客户端之后，若要重新开启定位请重新New一个AMapLocationClient对象。
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
    }


}
