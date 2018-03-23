package com.shura.rmct2.netconnect;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shura.rmct2.MainActivity;
import com.shura.rmct2.R;
import com.shura.rmct2.netconnect.model.Book;
import com.shura.rmct2.netconnect.model.constellation;
import com.shura.rmct2.netconnect.present.BookPresenter;
import com.shura.rmct2.netconnect.view.BookView;
import com.shura.rmct2.tool.CommonToolBar;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lemon on 2017/11/20.
 */

public class BookActivity extends AppCompatActivity {

    private TextView text ;
    private TextView tv_xingzuo;
    private Button btn ;
    private Button btn_xingzuo;
    private BookPresenter mBookPresenter = new BookPresenter(this);
    private Gson gson;
    private String jsonTest;
    private GsonBuilder builder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initTitle();
        text = (TextView) findViewById(R.id.text);
        btn = (Button) findViewById(R.id.button);
        tv_xingzuo = (TextView) findViewById(R.id.tv_xingzuo);
        btn_xingzuo = (Button) findViewById(R.id.btn_xingzuo);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBookPresenter.getSearchBook("金瓶梅",null,0,1);
            }
        });

        btn_xingzuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBookPresenter.getConstellation("摩羯座","today");
            }
        });
        mBookPresenter.onCreate();
        mBookPresenter.attachView(mBookView);

        //todo gson 这两句代码必须的，为的是初始化出来gson这个对象，才能拿来用
        builder=new GsonBuilder();
        gson=builder.create();
    }


    private BookView mBookView = new BookView() {
        @Override
        public void onSuccess(Book mBook) {
            Log.e("log","mbook"+mBook.toString());
            text.setText(mBook.toString());
        }

        @Override
        public void onSuccess1(constellation mconstellation) {
            tv_xingzuo.setText(mconstellation.toString());
            /**将实体类转化为json字符串*/
            jsonTest = gson.toJson(mconstellation, com.shura.rmct2.netconnect.model.constellation.class);
            Log.e("json-constellation",jsonTest);
//            {"QFriend":"处女座","all":"20%","color":"黄色","date":20180201,"datetime":"2018年02月01日",
//             "error_code":0,"health":"40%","love":"20%","money":"20%","name":"摩羯座","number":5,
//             "resultcode":"200","summary":"今天的你比较容易看不惯工作中的一些人，容易主动找别人茬，易怒。
//             身体今天会有些不舒服。","work":"20%"}

            /**将json字符串转化为实体类*/
            constellation mcon = gson.fromJson(jsonTest,constellation.class);
            Log.e("entity-constellation",mcon.getDatetime()+""+mcon.getSummary());

            try {
                JSONObject resp = new JSONObject(jsonTest);
                String qfriemd = resp.getString("QFriend");
                String datetime = resp.getString("datetime");
                String summary = resp.getString("summary");
                Log.e("summary",summary);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        @Override
        public void onError(String result) {
            Toast.makeText(BookActivity.this,result, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBookPresenter.onStop();
    }

    public void initTitle(){
        CommonToolBar toolbar = (CommonToolBar) findViewById(R.id.ctb_book);
        toolbar.setCenterTitle("网络请求", 17, R.color.white);
        toolbar.setImmerseState(this,true);
        toolbar.setNavIcon(R.drawable.white_back_icon);
        toolbar.setNavigationListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setRightTitle("完成", 14, R.color.white).setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"点击了完成",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BookActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
