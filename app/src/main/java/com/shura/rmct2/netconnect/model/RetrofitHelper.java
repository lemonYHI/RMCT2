package com.shura.rmct2.netconnect.model;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.shura.rmct2.RetrofitService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lemon on 2017/11/20.
 * 主要用于Retrofit的初始化：
 */

public class RetrofitHelper {
    public static Context mCntext;

    OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;

    //静态方法getInstance用于获取自身RetrofitHelper的实例化，并且只会实例化一次。
    public static RetrofitHelper getInstance(Context context){
        if (instance == null){
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    //todo add 自己提供url
    public static RetrofitHelper getInstance(Context context,String url){
        if(context!=null){
            mCntext = context;
        }
        return new RetrofitHelper(context,url);
    }


    private RetrofitHelper(Context mContext){
        mCntext = mContext;
        resetApp();
    }


    //todo add 自己提供url
    private RetrofitHelper(Context context,String url){
        mCntext = context;
        resetConstellation();
    }



    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .client(client)
                // 因为接口返回的数据不是我们需要的实体类，我们需要调用addConverterFactory方法进行转换。
                // 由于返回的数据为json类型，所以在这个方法中传入Gson转换工厂
                .addConverterFactory(factory)
                //支持RXJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }



    private void resetConstellation(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://web.juhe.cn:8080/")
                .client(client)
                // 因为接口返回的数据不是我们需要的实体类，我们需要调用addConverterFactory方法进行转换。
                // 由于返回的数据为json类型，所以在这个方法中传入Gson转换工厂
                .addConverterFactory(factory)
                //支持RXJava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }



    //创建RetrofitService的实体类
    //getServer方法就是为了获取RetrofitService接口类的实例化。
    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }

}
