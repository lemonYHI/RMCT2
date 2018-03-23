package com.shura.rmct2.personal.View;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shura.rmct2.R;
import com.shura.rmct2.personal.CantranctPersonal;
import com.shura.rmct2.personal.Presenter.PresenterPersonal;

/**
 * Created by lemon on 2018/2/26.
 */

public class PersonalFragment extends Fragment implements CantranctPersonal.IView {

    private Button btn_register;
    private Button btn_login;
    private EditText et_username;
    private EditText et_pwd;
    private PresenterPersonal pp;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal,container,false);

        et_username = view.findViewById(R.id.et_username);
        et_pwd = view.findViewById(R.id.et_pwd);
        btn_register = view.findViewById(R.id.btn_register);
        btn_login = view.findViewById(R.id.btn_login);

        pp = new PresenterPersonal(this);

        //点击事件是传递输入框的参数到P层
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"点击了注册",Toast.LENGTH_SHORT).show();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pp.send(et_username.getText().toString(),et_pwd.getText().toString());
            }
        });

        return view;
    }



    @Override
    public void onSuccess() {
        Toast.makeText(getContext(),"onSuccess-1",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail() {
        Toast.makeText(getContext(),"onFail-1",Toast.LENGTH_SHORT).show();
    }






}
