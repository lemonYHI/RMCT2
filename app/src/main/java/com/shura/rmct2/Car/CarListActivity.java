package com.shura.rmct2.Car;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.shura.rmct2.R;
import com.shura.rmct2.adapter.TitleFragmentPagerAdapter;
import com.shura.rmct2.tool.CommonToolBar;
import com.shura.rmct2.tool.SPUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 车辆列表
 * Created by lemon on 2017/10/24.
 */

public class CarListActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    List<Fragment> fragmentList;
    List<String> title;
    TabLayout tab;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carlist);
        initTitle();
        tab = (TabLayout) findViewById(R.id.tab);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        initViewPager();
    }


    private void initTitle() {
        CommonToolBar toolbar = (CommonToolBar) findViewById(R.id.toolbarcarlist);
        toolbar.setCenterTitle("车辆列表", 17, R.color.white);
        toolbar.setImmerseState(this, true);
        toolbar.setNavIcon(R.drawable.white_back_icon);
        toolbar.setNavigationListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setRightTitle("添加设备", 14, R.color.white);
        toolbar.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了添加设备", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CarListActivity.this, AdddeviceActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initViewPager() {
        title = new ArrayList<String>();
        title.add("全部（9）");
        title.add("在线（8）");
        title.add("离线（1）");
        for (int i = 0; i < title.size(); i++) {
            tab.addTab(tab.newTab().setText(title.get(i)));
        }
        fragmentList = new ArrayList<>();
        fragmentList.add(new CarFragment());
        fragmentList.add(new CarFragment());
        fragmentList.add(new CarFragment());

        TitleFragmentPagerAdapter adapter = new TitleFragmentPagerAdapter(getSupportFragmentManager(),
                fragmentList, title);

        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        tab.setTabsFromPagerAdapter(adapter);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private final String VP_PAGE = "vpPage";//页码

    @Override
    public void onPageSelected(int position) {
        SPUtils.put(getApplicationContext(), VP_PAGE, position);//缓存页码，下次直接打开
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
