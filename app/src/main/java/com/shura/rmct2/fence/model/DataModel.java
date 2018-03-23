package com.shura.rmct2.fence.model;

import com.shura.rmct2.fence.CantractFence;
import com.shura.rmct2.RetrofitService;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lemon on 2018/3/20.
 */

public class DataModel implements CantractFence.IModel {

    private String baseurl = "http://120.76.205.241:8000/";

    @Override
    public void model(final CantractFence.Callback callback) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseurl)//添加baseurl
                //添加Rxjava
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //创建添加api类
        RetrofitService apiInterface = retrofit.create(RetrofitService.class);

        apiInterface.getfenceData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyDataBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MyDataBean myDataBean) {
                        if(myDataBean != null) {
                            //这是网络请求到的数据
                            List<MyDataBean.DataBean> contenet = myDataBean.getData();
                            //将数据回掉到P层
                            callback.calldata(contenet);
                        }
                    }
                });
    }
}
