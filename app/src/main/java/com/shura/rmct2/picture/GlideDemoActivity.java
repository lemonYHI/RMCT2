package com.shura.rmct2.picture;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shura.rmct2.R;
import com.shura.rmct2.tool.CommonToolBar;

/**
 * Created by lemon on 2017/12/12.
 */

public class GlideDemoActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        initTitle();
        ImageView iv = findViewById(R.id.iv);
        Glide.with(this)   //决定生命周期
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                .into(iv);
        /**
         * with()
         * activity/fragment可以传递this;
         * 若不是activity/fragment,传递applicationContext,只有当程序被杀掉的时候，图片加载才会停止。
         */
    }

    public void initTitle(){
        CommonToolBar tb_camera = findViewById(R.id.glidetb);
        tb_camera.setCenterTitle("图片加载",17,R.color.white);
        tb_camera.setNavIcon(R.drawable.white_back_icon);
        tb_camera.setImmerseState(this,true);
        tb_camera.setNavigationListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tb_camera.setRightTitle("完成", 14, R.color.white);
        tb_camera.setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GlideDemoActivity.this,"点击了完成",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
