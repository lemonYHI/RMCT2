package com.shura.rmct2;



import com.shura.rmct2.fence.model.MyDataBean;
import com.shura.rmct2.netconnect.model.Book;
import com.shura.rmct2.netconnect.model.constellation;
import com.shura.rmct2.personal.Model.healthbean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lemon on 2017/11/15.
 * 网络请求用到的url
 */

public interface RetrofitService {
    /**
     //拼接一个URL然后进行网络请求
     //这里我们拼接的URL就是 完整URL:https://api.douban.com/v2/book/search?q=金瓶梅&tag=&start=0&count=1;
     //每个URL前面都是以http://www.jianshu.com/开头,我们把这个不变的部分，也叫做baseUrl提出来，放到另一个地方,在下面我们会提到
     */

    //将返回值call改为observable  网络请求里面
    @GET("book/search")
    Observable<Book> getSearchBook(@Query("q") String name,
                                   @Query("tag") String tag,
                                   @Query("start") int start,
                                   @Query("count") int count
                             );

    //lemon 2018.1.29  网络请求里面
    //接口完整地址：http://web.juhe.cn:8080/constellation/getAll?
    // consName=%E6%91%A9%E7%BE%AF%E5%BA%A7&type=today&key=be2cbd130ee98a5e05d521db9596a8f5
    @GET("constellation/getAll?key=be2cbd130ee98a5e05d521db9596a8f5")
    Observable<constellation> getConstellationData(
            @Query("consName") String consname,
            @Query("type") String type
    );



    //lemon 2018.3.22  围栏设置RecyclerView+点击事件
    //接口完整地址：http://120.76.205.241:8000/news/sogou?kw=%E7%99%BD%E7%99%BE%E5%90%88&site=qq.com&
    // apikey=UzsrS4SYjH9Hh8A8En1F5PcWkKiXk1UVQvLQ8DyA1Kao7CUx9fbFRQcokyr15Kq7
    @GET("news/sogou?kw=%E7%99%BD%E7%99%BE%E5%90%88&\n" +
            "site=qq.com&apikey=UzsrS4SYjH9Hh8A8En1F5PcWkKiXk1UVQvLQ8DyA1Kao7CUx9fbFRQcokyr15Kq7")
    Observable<MyDataBean> getfenceData();


    //接口地址  个人中心 - 健康课堂   这个已经把参数固定死了
    //https://api.kkmh.com/v1/daily/comic_lists/0?since=0&gender=0
    @GET("v1/daily/comic_lists/0?since=0&gender=0")
    Observable<healthbean> getHealthData();












/**
 * 在每个入参前还多了一个注解。比如第一个入参@Query("q") String name，Query表示把你传入的字段拼接起来
 * 这个url需要几个入参你就在这个方法中定义几个入参，每个入参前都要加上Query注解
 *
 * @GET("book/search")
   Call<Book> getSearchBook(@Query("q") String name);//name由调用者传入
   @GET("book/search?q=name")
   Call<Book> getSearchBook();
  **********************************************************
   如果入参比较多，就可以把它们都放在Map中
   @GET("book/search")
   Call<Book> getSearchBook(@QueryMap Map<String, String> options);
   ***********************************************************
   用于替换url中某个字段
 @GET("group/{id}/users")
 Call<Book> groupList(@Path("id") int groupId);
 *******************************************************
 指定一个对象作为HTTP请求体
 @POST("users/new")
 Call<User> createUser(@Body User user);
 它会把我们传入的User实体类转换为用于传输的HTTP请求体，进行网络请求
 **********************************************************
 传送表单数据
 @FormUrlEncoded
 @POST("user/edit")
 Call<User> updateUser(@Field("first_name") String first, @Field("last_name") String last);
 */
}


