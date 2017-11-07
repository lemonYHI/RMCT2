package com.shura.rmct2.tool;


import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.shura.rmct2.R;

/**
 * Created by lemon on 2017/10/23.
 */

public class TestActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test);
        initTitle();
        TextView t1 = (TextView) findViewById(R.id.t1);

    }

    protected void initTitle() {
        Toast.makeText(this,"点击了  进来了",Toast.LENGTH_SHORT).show();
        CommonToolBar tb_toolbar = (CommonToolBar) findViewById(R.id.tb_toolbar);
        tb_toolbar.setImmerseState(this, true)//是否侵入，默认侵入
                .setNavIcon(R.drawable.white_back_icon)//返回图标
                .setNavigationListener(new View.OnClickListener() {//返回图标监听
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setCenterTitle("TestActivity", 17, R.color.white);
    }
}