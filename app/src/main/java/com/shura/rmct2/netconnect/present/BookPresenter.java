package com.shura.rmct2.netconnect.present;

import android.content.Context;
import android.content.Intent;

import com.shura.rmct2.netconnect.model.Book;
import com.shura.rmct2.netconnect.model.DataManager;
import com.shura.rmct2.netconnect.model.constellation;
import com.shura.rmct2.netconnect.view.BookView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lemon on 2017/11/20.
 * 即拥有view层对象，又拥有model层对象
 */

public class BookPresenter implements Presenter {

    private DataManager manager;
    private CompositeSubscription mCompositeSubscription;
    private Context mContext;
    private BookView mBookView;//接口，里面是回掉函数，将presenter网络请求返回的结果，传递到view层去显示。
    private Book mBook;
    private constellation mconstellation;
    private DataManager managerurl;
    private String url ;


    public BookPresenter (Context mContext){
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        manager = new DataManager(mContext);
        //todo add
        managerurl = new DataManager(mContext,url);
        /**
         * CompositeSubscription是用来存放RxJava中的订阅关系的。
         * 注意请求完数据要及时清掉这个订阅关系，不然会发生内存泄漏。
         * 可在onStop中通过调用CompositeSubscription的unsubscribe方法来取消这个订阅关系，
         * 不过一旦调用这个方法，那么这个CompositeSubscription也就无法再用了，要想再用只能重新new一个。
         */
        mCompositeSubscription = new CompositeSubscription();

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        if (mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void pause() {

    }

    //想把请求下来的数据实体类给哪个View就传入哪个View
    //在attachView中，我们把BookView传进去。也就是说我们要把请求下来的实体类交给BookView来处理。
    @Override
    public void attachView(BookView view) {
        mBookView = view;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

    }

    /**自定义函数
     * 名字和入参都和请求接口RetrofitService中的方法相同。这个方法也就是请求的具体实现过程。
     */
    public void getSearchBook(String name,String tag,int start,int count){
        mCompositeSubscription.add(manager.getSearchBooks(name, tag, start, count)
                .subscribeOn(Schedulers.io())  //请求数据的事件发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  //请求完成后在主线程更新UI
                .subscribe(new Observer<Book>() {
                    @Override
                    public void onCompleted() {
                        //所有的事件都完成，可做这些操作
                        //当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。
                        if(mBook != null){
                            mBookView.onSuccess(mBook);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //请求过程中发生错误
                        throwable.printStackTrace();
                        mBookView.onError("请求书本失败！！");
                    }

                    @Override
                    public void onNext(Book book) {
                        //这里的BOOK就是我们请求接口返回的实体类
                        mBook = book;
                    }
                })
        );
    }


    public void getConstellation(String consname,String type){
        mCompositeSubscription.add(managerurl.getConstellation(consname, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<constellation>() {
                    @Override
                    public void onCompleted() {
                        if(mconstellation != null){
                            mBookView.onSuccess1(mconstellation);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                      mBookView.onError("请求星座失败");
                    }

                    @Override
                    public void onNext(constellation constellation) {
                        //相当于onClick() - 请求成功后返回的实体类
                       mconstellation = constellation;
                    }
                })

        );

    }



}
