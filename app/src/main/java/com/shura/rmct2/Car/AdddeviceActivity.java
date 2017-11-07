package com.shura.rmct2.Car;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shura.rmct2.MainActivity;
import com.shura.rmct2.R;
import com.shura.rmct2.tool.CommonToolBar;


/**
 * Created by lemon on 2017/10/24.
 */

public class AdddeviceActivity extends AppCompatActivity {

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddevice);
        initTitle();
    }

    public void initTitle(){
        CommonToolBar toolbar = (CommonToolBar) findViewById(R.id.ctb);
        toolbar.setCenterTitle("添加设备", 17, R.color.white);
        toolbar.setImmerseState(this,true);
        toolbar.setNavIcon(R.drawable.white_back_icon);
        toolbar.setNavigationListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setRightTitle("完成", 14, R.color.white).setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"点击了完成",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdddeviceActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
