package com.shura.rmct2.netconnect.present;

import android.content.Intent;

import com.shura.rmct2.netconnect.view.BookView;

/**
 * Created by lemon on 2017/11/20.
 * presenter主要用于网络的请求以及数据的获取，view就是将presenter获取到的数据进行展示。
 */

public interface Presenter {
    void onCreate();

    void onStart();//暂时没用到

    void onStop();

    void pause();//暂时没用到

    /*
    用于绑定我们定义的View.也就是，你想把请求下来的数据实体类给哪个View就传入哪个View
     */
    void attachView(BookView view);


    void attachIncomingIntent(Intent intent);//暂时没用到


}
