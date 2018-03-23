package com.shura.rmct2.personal;

/**
 * Created by lemon on 2018/3/5.
 */

public class CantranctPersonal {

    public interface IModel{
        void validate(String name,String pwd,CallbackPersonal call);

        interface CallbackPersonal {
            void result(int result);
        }

    }


    public interface IPresenter{
        //1-将调用的方法
        void send(String name,String pwd);

    }

    public interface IView{
        //1-在界面上点击按钮，调用P层的方法进行传递参数，在生命周期方法里完成
        //调用P层构造函数
        //2-将P层传递的结果进行展示
        void onSuccess();
        void onFail();
    }


}
