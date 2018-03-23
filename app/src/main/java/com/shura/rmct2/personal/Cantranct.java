package com.shura.rmct2.personal;

import com.shura.rmct2.personal.Model.healthbean;

import java.util.List;

/**
 * Created by lemon on 2018/2/28.
 *
 * mvp接口的总类
 */

public class Cantranct {

    //M层接口及方法： 获取数据
    public interface IModel {
        //M层获取请求数据的方法 方法参数为下面的接口对象
        void model(CallBack callBack);

        //M层获取到数据之后 存入这个接口的方法然后把数据回调给P层
        interface CallBack {
            //方法的参数保存m层获取到的数据 然后回调给P层
            void callData(List<healthbean.DataBean.ComicsBean> comics);
        }
    }

    //P层接口及方法：M和V层的交互等逻辑（其实P层写不写接口都可以 用接口显得统一规范）
    public interface IPresenter {
        void presenter();
    }


    //V层接口 ：接收数据 显示数据
    public interface IView {
        //方法的参数用于接收在P层里通过M层获取到的数据
        void view(List<healthbean.DataBean.ComicsBean> comics);
    }

}
