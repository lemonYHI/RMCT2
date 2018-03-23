package com.shura.rmct2.personal.Model;

import com.shura.rmct2.personal.CantranctPersonal;

/**
 * Created by lemon on 2018/3/5.
 */

public class ModelPersonal implements CantranctPersonal.IModel {

    @Override
    public void validate(String name, String pwd, CallbackPersonal call) {

        if(name.equals("111")&&pwd.equals("111")){
            call.result(1);
        }else{
            call.result(2);
        }

    }
}
