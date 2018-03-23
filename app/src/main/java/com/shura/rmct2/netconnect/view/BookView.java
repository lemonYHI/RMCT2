package com.shura.rmct2.netconnect.view;

import com.shura.rmct2.netconnect.model.Book;
import com.shura.rmct2.netconnect.model.constellation;

/**
 * Created by lemon on 2017/11/20.
 */

public interface BookView extends View {
    /**
     * 如果presenter请求成功，将向该方法传入请求下来的实体类，
     * 也就是Book，view拿到这个数据实体类后，就可以进行关于这个数据的展示或其他的一些操作
     * @param mBook
     */
    void onSuccess(Book mBook);


    void onSuccess1(constellation mconstellation);

    /**
     * 如果请求失败，就会向这个view传入失败信息，你可以弹个Toast来提示请求失败
     * @param result
     */
    void onError(String result);
}
