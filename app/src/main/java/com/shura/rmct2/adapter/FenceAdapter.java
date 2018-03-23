package com.shura.rmct2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shura.rmct2.R;
import com.shura.rmct2.fence.model.MyDataBean;

import java.util.List;


/**
 * Created by lemon on 2017/9/28.
 * 围栏设置对应的adapter
 */

public class FenceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    //定义一个集合，接收从Activity中传递过来的数据和上下文
    private List<MyDataBean.DataBean> mList;

    private Context mContext;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    //将接口和函数结合起来，这个是自定义函数  //参数是下边定义的接口
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }


    public FenceAdapter(Context context, List<MyDataBean.DataBean> list) {
        this.mContext = context;
        this.mList = list;
    }


    //加载子item的布局
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_fence, parent, false);

        return new MyHolder(v);
    }


    //对子item进行赋值  进行操作
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            final String itemText = mList.get(position).getContent();
            ((MyHolder) holder).tv.setText(itemText);
        }

//判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =  holder.getLayoutPosition();// 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }

        if (mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position);
                    //返回true 表示消耗了事件 事件不会继续传递
                    return true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }




    // 通过holder的方式来初始化每一个ChildView的内容
    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_fence);

        }
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }


}
