package com.shura.rmct2.personal.Presenter;

import com.shura.rmct2.personal.Cantranct;
import com.shura.rmct2.personal.Model.ModelHealth;
import com.shura.rmct2.personal.Model.healthbean;

import java.util.List;

/**
 * Created by lemon on 2018/2/28.
 *
 * 主要将M层获取到的数据给V层
 */

public class Presenter implements Cantranct.IPresenter {

    //创建M层对象
    private ModelHealth model;
    //创建V层接口的对象
    private Cantranct.IView iView;


    //构造方法的参数为V层的接口对象
    public Presenter(Cantranct.IView iView) {
        //待会展示数据的类实现V接口 创建P层的时候 将本身传进来
        //也就是说P层和展示数据的类他俩使用的是共同的一个V层接口 自然这个V层接口方法里的数据就可以共用了
        this.iView = iView;
        //创建M层的时候自然运行M层实现的请求数据方法 现在可以理解为已经请求到了数据
        model = new ModelHealth();
    }

    //在这个方法里进行M层和V层的交互
    @Override
    public void presenter() {

        //M层创建保存数据的callback接口对象 这个接口里方法的参数就是数据集合
        model.model(new Cantranct.IModel.CallBack() {
            @Override
            public void callData(List<healthbean.DataBean.ComicsBean> comics) {
                //然后再用V层接口对象保存数据 在V层里展示出来
              iView.view(comics);
            }
        });

    }
}
