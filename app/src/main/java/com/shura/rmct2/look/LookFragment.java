package com.shura.rmct2.look;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.shura.rmct2.R;
import com.shura.rmct2.adapter.CarAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lemon on 2017/10/24.
 */

public class LookFragment extends Fragment{

    private View view ;

    private RecyclerView recycler ;

    private CarAdapter madapter ;

    int colors[] = {R.color.app_color,R.color.oklib_frame_black,R.color.white} ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_car,container,false);

        //在布局中找到定义
        recycler = view.findViewById(R.id.recyclerview);
        //grid一行有2列的意思
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = recycler.getAdapter().getItemViewType(position);
                if(type == DataModel.TYPE_THREE){
                    //这个是占据2个单元格
                    return gridLayoutManager.getSpanCount();
                }else{
                    //占据一个单元格
                    return 1;
                }
            }
        });

        //设置布局管理器
//        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,
//                false));
        recycler.setLayoutManager(gridLayoutManager);

        madapter =  new CarAdapter(getContext());
        //设置适配器
        recycler.setAdapter(madapter);
        //添加item之间的分割线
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                //super.getItemOffsets(outRect, view, parent, state);
                GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) view.getLayoutParams();

                int spansize = layoutParams.getSpanSize();
                int spanindex = layoutParams.getSpanIndex();
                outRect.top = 20;
                if(spansize != gridLayoutManager.getSpanCount()){
                    if(spanindex == 1){
                        outRect.left = 10;
                    }else{
                        outRect.right = 10;
                    }
                }
            }
        });

        initData();
        return view;
    }

    /**
     * 模拟list集合.
     * 针对三种不同的类型，可以去写三个实体类来代替DataModel.
     *
     */
    private void initData(){

        List<DataModel> list = new ArrayList<>();
         for(int i = 0;i < 30; i++){
             //int type = (int)(Math.random()*3)+1;
             int type ;
              if( i < 6 || (i>15 && i<20)){
                type = 1;
              }else if(i<10 || i>26){
                  type = 2;
              }else{
                  type = 3;
              }

             DataModel data = new DataModel();
             data.avatarColor = colors[type - 1];
             data.type = type;
             data.content = "content:"+i;
             data.name = "name:"+i;
             data.contentColor = colors[(type+1)%3];
             list.add(data);
         }
        madapter.addList(list);
        madapter.notifyDataSetChanged();
    }
}
