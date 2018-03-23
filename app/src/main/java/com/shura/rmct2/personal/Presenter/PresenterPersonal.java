package com.shura.rmct2.personal.Presenter;

import com.shura.rmct2.personal.CantranctPersonal;
import com.shura.rmct2.personal.Model.ModelPersonal;

/**
 * Created by lemon on 2018/3/5.
 */

public class PresenterPersonal  implements CantranctPersonal.IPresenter {

    private CantranctPersonal.IView iViewPersonal;
    private ModelPersonal mp;

    public PresenterPersonal(CantranctPersonal.IView iview) {
        this.iViewPersonal = iview;
        mp = new ModelPersonal();
    }


    @Override
    public void send(String name, String pwd) {

        //将参数传递给model层，model层将校验结果返回给view层。
        mp.validate(name,pwd,new CantranctPersonal.IModel.CallbackPersonal() {

            @Override
            public void result(int result) {
              switch(result){
                  case 1:
                      iViewPersonal.onSuccess();
                      break;
                  case 2:
                      iViewPersonal.onFail();
                      break;
              }
            }
        });

    }
}
