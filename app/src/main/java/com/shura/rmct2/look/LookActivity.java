package com.shura.rmct2.look;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.shura.rmct2.R;
import com.shura.rmct2.adapter.TitleFragmentPagerAdapter;
import com.shura.rmct2.tool.CommonToolBar;
import com.shura.rmct2.tool.SPUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * 随便看看
 * Created by lemon on 2017/10/24.
 */

public class LookActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
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
        toolbar.setCenterTitle("随便看看", 17, R.color.white);
        toolbar.setImmerseState(this, true);
        toolbar.setNavIcon(R.drawable.white_back_icon);
        toolbar.setNavigationListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void initViewPager() {
        //给TabLayout设置值
        title = new ArrayList<String>();
        title.add("微信精选");
        title.add("星座运势");
        title.add("随便什么");
        for (int i = 0; i < title.size(); i++) {
            tab.addTab(tab.newTab().setText(title.get(i)));
        }

        //填充viewpager的内容，每个carFragment里面都是fragment.
        fragmentList = new ArrayList<>();
        fragmentList.add(new LookFragment());
        fragmentList.add(new LookFragment());
        fragmentList.add(new LookFragment());

        //将tablayout和viewpager适配起来
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
