package com.shura.rmct2.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shura.rmct2.look.DataModel;
import com.shura.rmct2.look.TypeAbstractViewHolder;
import com.shura.rmct2.look.TypeOneViewHolder;
import com.shura.rmct2.look.TypeThreeViewHolder;
import com.shura.rmct2.look.TypeTwoViewHolder;
import com.shura.rmct2.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lemon on 2018/2/5.
 *
 * 随便看看里面的recyclerview对应的adapter.
 * 多一个类型又要改好多的地方。
 */

public class CarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInfater;
    private List<DataModel> mList = new ArrayList<>();

    public CarAdapter(Context context) {
        mLayoutInfater = LayoutInflater.from(context);
    }

    public void addList(List<DataModel> list) {
        mList.addAll(list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DataModel.TYPE_ONR:
                return new TypeOneViewHolder(mLayoutInfater.inflate(R.layout.item_car1,parent,false));
            case DataModel.TYPE_TWO:
                return new TypeTwoViewHolder(mLayoutInfater.inflate(R.layout.item_car2,parent,false));
            case DataModel.TYPE_THREE:
                return new TypeThreeViewHolder(mLayoutInfater.inflate(R.layout.item_car3,parent,false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //判断需要获取哪个数据
        int viewType = getItemViewType(position);
        ((TypeAbstractViewHolder)holder).bindHolder(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).type;
    }


}
