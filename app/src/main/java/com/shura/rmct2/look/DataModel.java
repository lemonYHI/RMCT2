package com.shura.rmct2.look;

/**
 * Created by lemon on 2018/2/6.
 *
 * recyclerView 的item.
 */

public class DataModel {

    public int type;
    public int avatarColor;
    public String name;
    public String content;
    public int contentColor;
    /**
     * 静态常量去区分类型 , 需要创建三个不同的布局
     */
    public static final int TYPE_ONR = 1;
    public static final int TYPE_TWO = 2;
    public static final int TYPE_THREE = 3;

}
