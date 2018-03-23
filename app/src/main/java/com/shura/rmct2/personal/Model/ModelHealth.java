package com.shura.rmct2.personal.Model;

import com.shura.rmct2.RetrofitService;
import com.shura.rmct2.personal.Cantranct;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lemon on 2018/2/28.
 *
 * 请求数据  然后将数据回调给P层
 */

public class ModelHealth implements Cantranct.IModel {

    private String baseUrl = "https://api.kkmh.com/";

    @Override
    public void model(final CallBack callBack) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)//添加baseurl
                //添加Rxjava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建添加api类
        RetrofitService apiInterface = retrofit.create(RetrofitService.class);

        apiInterface.getHealthData()
                //制定被观察者线程
                .subscribeOn(Schedulers.io())
                //制定观察者线程
                .observeOn(AndroidSchedulers.mainThread())
                //进行订阅
                .subscribe(new Observer<healthbean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(healthbean mybean) {
                        if(mybean != null){
                            List<healthbean.DataBean.ComicsBean> data = mybean.getData().getComics();
                            //请求到数据后 将数据保存到callback接口的方法里
                            //用于将数据回调给P层 在P层里将数据给V
                            callBack.callData(data);
                        }

                    }
                });




    }

}

