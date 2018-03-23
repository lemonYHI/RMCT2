package com.shura.rmct2.fence.presenter;

import com.shura.rmct2.fence.CantractFence;
import com.shura.rmct2.fence.model.DataModel;
import com.shura.rmct2.fence.model.MyDataBean;

import java.util.List;

/**
 * Created by lemon on 2018/3/21.
 */

public class DataPresenter implements CantractFence.IPresenter {

    private CantractFence.IView iView;
    private DataModel dataModel;

    public DataPresenter(CantractFence.IView iview){
        this.iView = iview;
        dataModel = new DataModel();
    }

    @Override
    public void presenter() {
        dataModel.model(new CantractFence.Callback() {
            @Override
            public void calldata(List<MyDataBean.DataBean> myDataBean) {
                //将请求到的数据传递到View层
                iView.view(myDataBean);
            }
        });

    }
}
