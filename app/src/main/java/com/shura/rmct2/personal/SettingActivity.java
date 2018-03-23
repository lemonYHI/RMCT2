package com.shura.rmct2.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hjm.bottomtabbar.BottomTabBar;
import com.shura.rmct2.R;
import com.shura.rmct2.personal.View.HealthFragment;
import com.shura.rmct2.personal.View.PersonalFragment;
import com.shura.rmct2.tool.CommonToolBar;

/**
 * Created by lemon on 2018/2/26.
 */

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }


    public void initView(){
        CommonToolBar commonToolBar = findViewById(R.id.toolbarsetting);
        BottomTabBar btb = findViewById(R.id.btbsetting);
        commonToolBar.setCenterTitle("个人中心", 17, R.color.white);
        commonToolBar.setImmerseState(this, true);
        commonToolBar.setNavIcon(R.drawable.white_back_icon);
        commonToolBar.setNavigationListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btb.init(getSupportFragmentManager())
                .addTabItem("健康课堂",R.drawable.ic_menu_camera,HealthFragment.class)
                .addTabItem("个人中心",R.drawable.ic_menu_manage,PersonalFragment.class);
    }


}
