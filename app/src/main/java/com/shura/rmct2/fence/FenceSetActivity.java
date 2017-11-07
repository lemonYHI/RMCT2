package com.shura.rmct2.fence;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.shura.rmct2.R;
import com.shura.rmct2.adapter.FenceAdapter;
import com.shura.rmct2.tool.CommonToolBar;
import com.shura.rmct2.tool.MyDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static com.loc.x.e;

/**
 * Created by lemon on 2017/10/25.
 */

public class FenceSetActivity extends Activity {

    //定义RecyclerView
    private RecyclerView mRecyclerView = null;
    //定义一个List集合，用于存放RecyclerView中的每一个数据
    private List<String> mData = null;
    //定义一个Adapter
    private FenceAdapter mAdapter;
    //定义一个LinearLayoutManager
    private LinearLayoutManager mLayoutManager;
    private View v;
    private String fencename;

    private View.OnClickListener itemsOnClick;
    private CommonToolBar commonToolBar;
    private fenceBean fenceBean;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, "79b75b808751427bcf8f74fa46aacf96");
        setContentView(R.layout.activity_fenceset);
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


        initData();

        mAdapter = new FenceAdapter(this,mData);
        mRecyclerView.setLayoutManager(mLayoutManager);


        /**
         * 设置item的长点击事件
         */
        mAdapter.setOnItemLongClickListener(new FenceAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"long click "+mData.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * 设置item的点击事件
         */
        mAdapter.setOnItemClickListener(new FenceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getApplicationContext()," click "+mData.get(position),
                        Toast.LENGTH_SHORT).show();


            }
        });

        //为弹出窗口实现监听类
        itemsOnClick = new View.OnClickListener(){

            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.modify:
                        Toast.makeText(getApplicationContext(),"修改",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.delete:
                        break;
                    default:
                        break;
                }


            }  };

        mRecyclerView.setAdapter(mAdapter);
        //这句就是添加我们自定义的分隔线
        mRecyclerView.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
    }

    //初始化加载到RecyclerView中的数据, 我这里只是给每一个Item添加了String类型的数据
    private void initData(){
        mData = new ArrayList<String>();
        //查询一条数据
        //查找Person表里面id为0c089ccaec的数据
      //  BmobQuery<fenceBean> bmobQuery = new BmobQuery<fenceBean>();
//     查询单条数据
//   bmobQuery.getObject("0c089ccaec", new QueryListener<fenceBean>() {
//            @Override
//            public void done(fenceBean fenceBean, BmobException e) {
//                if(e==null){
//                    Toast.makeText(FenceSetActivity.this,"查询成功:"+fenceBean.getFencename(),
//                            Toast.LENGTH_SHORT).show();
//
//
//                }else{
//                    Toast.makeText(FenceSetActivity.this,"查询失败：" + e.getMessage(),
//                            Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        //BmobQuery bmobQuery = new BmobQuery("fenceBean");
      /*  bmobQuery.findObjects(FenceSetActivity.this, new FindListener<fenceBean>() {

            @Override
            public void done(List<fenceBean> list, BmobException e) {
                if(e==null){
                    Toast.makeText(FenceSetActivity.this,"查询成功:共"+list.size()+"条数据",
                           Toast.LENGTH_SHORT).show();
                    for(fenceBean f : list){
                       mData.add("fencename : "+f.getFencename());
                    }

                }else{
                    Toast.makeText(FenceSetActivity.this,"查询失败：" + e.getMessage(),
                           Toast.LENGTH_SHORT).show();
                }
            }
        });
*/

        for (int i = 0; i < 20; i++){
            mData.add("Item:" + 54);
        }


    }



}
