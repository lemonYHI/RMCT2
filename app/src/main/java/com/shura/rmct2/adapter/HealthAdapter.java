package com.shura.rmct2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shura.rmct2.R;
import com.shura.rmct2.personal.Model.healthbean;

import java.util.List;

/**
 * Created by lemon on 2018/3/1.
 */

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.MyViewHolder> {

    private Context context;
    private List<healthbean.DataBean.ComicsBean> data = null;

    public HealthAdapter(Context context, List<healthbean.DataBean.ComicsBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_health,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).getCover_image_url()).into(holder.im);
        holder.tv.setText(data.get(position).getTopic().getDescription());

    }

    @Override
    public int getItemCount() {
        return data.size() != 0 ? data.size() : 0;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView im;
        public TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            im = itemView.findViewById(R.id.iv_health);
            tv = itemView.findViewById(R.id.tv_health);
        }
    }
}


