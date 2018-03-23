package com.shura.rmct2.netconnect.model;

import android.content.Context;

import com.shura.rmct2.RetrofitService;

import rx.Observable;

/**
 * Created by lemon on 2017/11/20.
 * 这个是和其他层直接调用的接口
 * 让你更方便的调用RetrofitService 中定义的方法：
 */

public class DataManager {

    private RetrofitService mRetrofitService;

    // 在它的构造方法中，我们得到了RetrofitService 的实例化
    // 这样，把RetrofitService 中定义的方法都封装到DataManager 中，
    // 以后无论在哪个要调用方法时直接在DataManager 中调用就可以了，
    // 而不是重复建立RetrofitService 的实例化，再调用其中的方法。
    public DataManager(Context context){
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();//这句很经典
    }

    //todo add 自己提供url
    public DataManager(Context context,String url){
        this.mRetrofitService = RetrofitHelper.getInstance(context,url).getServer();//这句很经典
    }

    public Observable<Book> getSearchBooks(String name, String tag, int start, int count){
        return mRetrofitService.getSearchBook(name,tag,start,count);
    }

    //获取星座信息
    public Observable<constellation> getConstellation(String consname,String type){
        //在构造函数中，初始化Retrofitservice.
        return mRetrofitService.getConstellationData(consname, type);
    }



}
