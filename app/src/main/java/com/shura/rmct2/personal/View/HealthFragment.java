package com.shura.rmct2.personal.View;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shura.rmct2.R;
import com.shura.rmct2.personal.Cantranct;
import com.shura.rmct2.adapter.HealthAdapter;
import com.shura.rmct2.personal.Presenter.Presenter;
import com.shura.rmct2.personal.Model.healthbean;

import java.util.List;

/**
 * Created by lemon on 2018/2/26.
 */

public class HealthFragment extends Fragment implements Cantranct.IView {

    private RecyclerView rv_health;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health,container,false);
        rv_health = view.findViewById(R.id.recycler_health);

        //创建P层对象 传入本身 因为P层的构造函数是IView接口 而本类实现了IView接口
        //将本身传进去之后 在P层进行交互的V接口获取到数据 在本类实现的V层接口方法里的数据就可以用了
        Presenter presenter = new Presenter(this);
        presenter.presenter();
        return view;
    }

    //从P层获取到的数据  在P层里获取到M层请求的数据
    //将数据展示在recyclerview中
    @Override
    public void view(List<healthbean.DataBean.ComicsBean> comics) {
        HealthAdapter adapter = new HealthAdapter(getContext(),comics);
        rv_health.setAdapter(adapter);
        rv_health.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
