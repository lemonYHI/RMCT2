package com.shura.rmct2.fence.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.shura.rmct2.R;
import com.shura.rmct2.adapter.FenceAdapter;
import com.shura.rmct2.fence.AddFenceActivity;
import com.shura.rmct2.fence.CantractFence;
import com.shura.rmct2.fence.model.MyDataBean;
import com.shura.rmct2.fence.presenter.DataPresenter;
import com.shura.rmct2.tool.CommonToolBar;
import com.shura.rmct2.tool.MyDecoration;

import java.util.List;


/**
 * Created by lemon on 2017/10/25.
 * 展示已经设置的围栏 - 展示新闻
 */

public class FenceSetActivity extends Activity implements CantractFence.IView {

    //定义RecyclerView
    private RecyclerView mRecyclerView = null;
    //定义一个Adapter
    private FenceAdapter mAdapter;
    //定义一个LinearLayoutManager
    private LinearLayoutManager mLayoutManager;
    private View v;
    private String fencename;

    private View.OnClickListener itemsOnClick;
    private CommonToolBar commonToolBar;
    private com.shura.rmct2.fence.model.fenceBean fenceBean;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenceset);
        //初始化recyclerview需要的数据

        //RecyclerView三步曲+LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.recyclerview);


        commonToolBar = findViewById(R.id.toolbarfenceset);
        commonToolBar.setCenterTitle("围栏设置", 17, R.color.white);
        commonToolBar.setImmerseState(this, true);
        commonToolBar.setNavIcon(R.drawable.white_back_icon);
        commonToolBar.setNavigationListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        commonToolBar.setRightTitle("新增", 14, R.color.white);
        commonToolBar.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了新增", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FenceSetActivity.this, AddFenceActivity.class);
                startActivity(intent);
            }
        });


        //自动执行
        DataPresenter dp = new DataPresenter(this);
        dp.presenter();

    }


    //意思就是将adapter的一系列操作都放在view里面
    @Override
    public void view(List<MyDataBean.DataBean> myDataBean) {
        mAdapter = new FenceAdapter(this,myDataBean);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /**
         * 设置item的长点击事件
         */
        mAdapter.setOnItemLongClickListener(new FenceAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"long click ",
                        Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * 设置item的点击事件
         */
        mAdapter.setOnItemClickListener(new FenceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext()," click ",
                        Toast.LENGTH_SHORT).show();
            }
        });


        mRecyclerView.setAdapter(mAdapter);
        //这句就是添加我们自定义的分隔线
        mRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
    }

}
