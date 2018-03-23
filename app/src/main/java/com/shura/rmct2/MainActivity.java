package com.shura.rmct2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.model.PolylineOptions;
import com.shura.rmct2.look.LookActivity;
import com.shura.rmct2.fence.view.FenceSetActivity;
import com.shura.rmct2.netconnect.BookActivity;
import com.shura.rmct2.personal.SettingActivity;
import com.shura.rmct2.tool.CommonToolBar;
import com.shura.rmct2.picture.GlideDemoActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,LocationSource, AMapLocationListener {


    private AMapLocationClient mlocationClient = null;// 定位发起端
    private AMapLocationClientOption mLocationOption = null;// 定位参数
    private LocationSource.OnLocationChangedListener mListener = null;// 定位监听器
    // 标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    // 以前的定位点
    private LatLng oldLatLng;

    private AMap aMap;// 地图对象
    private MapView mMapView;
    private TextView tv_zhuizong;
    private TextView tv_guiji;
    private LatLng centerLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommonToolBar toolbar = findViewById(R.id.toolbarmain);
        toolbar.setCenterTitle("首页", 17, R.color.white);
        toolbar.setImmerseState(this);
        toolbar.setRightTitle("随便看看", 14, R.color.white);
        toolbar.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"点击了随便看看",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, LookActivity.class);
                startActivity(intent);
            }
        });



        mMapView =  findViewById(R.id.mapView);
        tv_zhuizong =  findViewById(R.id.tv_zhuizong);
        tv_guiji =  findViewById(R.id.tv_guiji);
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
        tv_zhuizong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"点击了追踪",Toast.LENGTH_SHORT).show();
            }
        });

        // 开始定位
        initLoc();

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void initLoc() {
        mlocationClient = new AMapLocationClient(this);
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_main:
                //首页  展示地图 定位
                // 随便看看一下新闻，RecyclerView里面还没有填充数据。填充iDataAPI的数据。
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                break;
            case R.id.nav_fenceSetting:
                //围栏设置 因为之前涉及过Bmob，现在展示有些问题；已经修复。
                // 最好再添加上拉刷新下拉加载功能。
                startActivity(new Intent(MainActivity.this, FenceSetActivity.class));
                break;
            case R.id.nav_picture:
                //图片展示 这个是个图片加载（现在只是简单的demo）Gank.io-妹子图片，
                // 还需要完善图片的具体展示。
                startActivity(new Intent(MainActivity.this, GlideDemoActivity.class));
                break;
            case R.id.nav_netconnect:
                //网络请求 MVP框架 用最新的技术写的一个网络请求 返回json数据。
                //最好将此json数据进行解析出来，展示在recyclerview上。
                startActivity(new Intent(MainActivity.this, BookActivity.class));
                break;
            case R.id.nav_setting:
                //我的
                //加底部导航栏ButtomTabBar；健康课堂和个人中心
                startActivity(new Intent(MainActivity.this, SettingActivity.class));
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                // 添加图钉
                // aMap.addMarker(getMarkerOptions(amapLocation));
                // 获取定位信息
                StringBuffer buffer = new StringBuffer();
                buffer.append(amapLocation.getCountry() + "" + amapLocation.getProvince() + "" + amapLocation.getCity()
                        + "" + amapLocation.getProvince() + "" + amapLocation.getDistrict() + ""
                        + amapLocation.getStreet() + "" + amapLocation.getStreetNum());
               // Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();

                // 记录第一次的定位信息
                oldLatLng = newLatLng;
                isFirstLoc = false;// 此后不再是第一次定位
            }
            // 位置有变化
            if (oldLatLng != newLatLng) {
                setUpMap(oldLatLng, newLatLng);
                oldLatLng = newLatLng;
            }

        } else {
            // 显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            Log.i("map", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:"
                    + amapLocation.getErrorInfo());

           // Toast.makeText(this, "定位失败，呜呜呜~~~~", Toast.LENGTH_LONG).show();
        }
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

    }
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
