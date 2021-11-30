package com.zw.rxjava_demo.activity.test;

import android.annotation.SuppressLint;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class Test4 {
    private static final String TAG = Test4.class.getSimpleName();

    /**
     * 组合 / 合并操作符
     * 组合 多个被观察者（Observable） & 合并需要发送的事件
     * <p>
     * 应用场景
     * 组合多个被观察者
     * 合并多个事件
     * 发送事件前追加发送事件
     * 统计发送事件数量
     */
    public static void test1() {
        // https://www.jianshu.com/p/cd984dd5aae8
        // https://blog.csdn.net/jdsjlzx/article/details/51505053
    }

    @SuppressLint("CheckResult")
    public static void test2() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello " + System.currentTimeMillis());
            }
        });

        Consumer<String> c1 = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "c1 s: " + s);
            }
        };

        Consumer<String> c2 = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "c2 s: " + s);
            }
        };

        observable.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c1);

        observable.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c2);
    }

    public static void test3() {

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "subscribe Thread: " + Thread.currentThread().getId());
                emitter.onNext("Hello " + System.currentTimeMillis());
            }
        });

        Consumer<String> c1 = new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "c1 s: " + s + ",Thread: " + Thread.currentThread().getId());
            }
        };

        // 默认"订阅者"和"被订阅者"运行在同一个线程中
        observable.subscribe(c1);


    }
}
