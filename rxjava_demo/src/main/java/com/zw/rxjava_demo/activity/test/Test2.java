package com.zw.rxjava_demo.activity.test;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;

public class Test2 {
    private static final String TAG = Test2.class.getSimpleName();

    public static void test1() {
        // Observable的种类
        /**
         * 1、Cool Observable
         *    Cool Observable必须等到有Observer去subscribe（订阅）才发送数据。
         *    Cool Observable对每个观察者都是独立的一个发送通道，即使他们的数据是一样的。
         *    即使这个Observable已经发送了很多事件，再来一个Observer进行subscribe，所有的数据都会重放给这个新来的Observer。（类似Android中的粘性广播）
         *
         * 2、Hot Observable
         *  Hot Observable，发送数据需要一个Connect操作符才开始发送事件，所以它更灵活，可以手动控制什么时候开始发送。如果不调用，则一直都不会发送（就算有再多的Observer订阅），还有就是Hot Observable就算没有观察者也可以发送数据，而Cool Observable需要等待到观察者订阅才发送。
         *  Hot Observable对每个观察者发送的事件是“共享”的，所以如果Hot Observable已经发送了很多事件，再来一个Observer进行subscribe，这个Observer会收不到之前发送的事件。（类似Android中的普通广播）
         *
         * Hot Observable也称作为可连接的Observable。Cool Observable就称作为普通的Observable]
         *
         * publish和connect操作符
         * Publish和Connect一般一起使用
         * Publish：将普通的Observable转换为可连接的Observable。
         * Connect：让一个可连接的Observable开始发射数据给订阅者。Observable调用Connect后，返回一个Disposable，使用Disposable可让Observable终止。（不调用就不用终止，就算所有观察者都取消注册，它依然不会终止，除非手动调用）
         *
         */
        ConnectableObservable<Long> observable = Observable.interval(1, TimeUnit.SECONDS)
                //转换为可连接的Observable
                .publish();
        observable.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long value) throws Exception {
                System.out.println("value 1-> " + value);
            }
        });
        //开始发送
        Disposable disposable = observable.connect();
        observable
                //延迟2秒钟注册
                .delaySubscription(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long value) throws Exception {
                        //收不到2秒之前发送的数据，收到的数据只有2秒后的数据
                        System.out.println("value 2-> " + value);
                    }
                });
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //6s之后停止发射数据
                disposable.dispose();
            }
        }, 6000);
    }

    public static void test2() {
        ConnectableObservable observable = Observable.
                interval(1, TimeUnit.SECONDS)
                .publish();
        observable.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG, "1 " + o);
            }
        });
        // 触发发送事件
        Disposable disposable = observable.connect();

        observable.delaySubscription(5, TimeUnit.SECONDS).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG, "2 " + o);
                disposable.dispose();
            }
        });
    }

    static int count = 0;

    public static void test3() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                if (count++ < 2) throw new RuntimeException("Hello Retry");
                emitter.onNext("Hello ......");
            }
        }).retryWhen(new RetryWithDelay())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, s);
                    }
                });
    }

    public static void test4() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello " + System.currentTimeMillis());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void test5() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello");
            }
        }).map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                return s + System.currentTimeMillis();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, s);
            }
        });
    }

    /**
     * https://www.cnblogs.com/fomin/p/8534139.html
     * <p>
     * RxJava在1.0只有一个个观察者模式，只能部分支持背压：
     * <p>
     * Observable(被观察者)/Observer（观察者）
     * Observable(被观察者)/Subscriber(观察者)
     * RxJava在2.0出现了两个观察者模式，新增Flowable支持背压，而Observable不支持背压：
     * <p>
     * Observable(被观察者)/Observer（观察者）
     * Flowable(被观察者)/Subscriber(观察者)
     * 注：背压是指在异步场景中，被观察者发送事件速度远快于观察者的处理速度的情况下，一种告诉上游的被观察者降低发送速度的策略，简而言之，背压是流速控制的一种策略。
     */
    public static void test6() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello");
                emitter.onComplete();
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "Observer " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        observable.subscribe(observer);

        Flowable<String> flowable = new Flowable<String>() {
            @Override
            protected void subscribeActual(Subscriber<? super String> s) {
                Log.i(TAG, "flowable Subscriber subscribeActual: " + s.toString());
                s.onNext("hello");
            }
        };
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "flowable Subscriber onNext: " + s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        Log.i(TAG, "flowable Subscriber: " + subscriber);

        flowable.subscribe(subscriber);
    }

    public static void test7() {
        // https://www.jianshu.com/p/a406b94f3188
    }
}
