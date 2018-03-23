package com.shura.rmct2.fence;


import com.shura.rmct2.fence.model.MyDataBean.DataBean;

import java.util.List;

/**
 * Created by lemon on 2018/3/20.
 */

public class CantractFence {

    /**
     * M层
     */
    public interface IModel {
        void model(Callback callback);
    }

    public interface Callback {
        void calldata(List<DataBean> myDataBean);
    }

    /**
     * P层
     */
    public interface IPresenter {
        void presenter();
    }

    /**
     * V层
     */
    public  interface IView{
        void view(List<DataBean> myDataBean);
    }


}
