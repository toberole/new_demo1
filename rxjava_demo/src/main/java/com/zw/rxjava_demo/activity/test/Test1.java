package com.zw.rxjava_demo.activity.test;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.IoScheduler;

public class Test1 {
    private static final String TAG = Test1.class.getSimpleName();

    public static void test1() {
        // 创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {

                Log.i(TAG, "subscribe " + Thread.currentThread().getName());
                emitter.onNext("Hello");
            }
        });

        // 创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe ...... " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, Thread.currentThread().getName() + " onNext ...... " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError ......");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete ......");
            }
        };

        observable
                .subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void test2() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("" + System.currentTimeMillis());
                emitter.onComplete();
            }
        }).subscribeOn(new IoScheduler()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                // d.dispose(); 取消请求 即使被观察者发出了事件 onNext也不会执行
                Log.i(TAG, "onSubscribe ......");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext: " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError ......");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete ......");
            }
        });
    }

    /**
     * 被观察者Obaservable的subscribe具有多个重载的方法
     * //观察者不对被观察者发送的事件做出响应(但是被观察者还可以继续发送事件)
     * public final Disposable subscribe()
     * <p>
     * //观察者对被观察者发送的任何事件都做出响应
     * public final void subscribe(Observer<? super T> observer)
     * <p>
     * //表示观察者只对被观察者发送的Next事件做出响应
     * public final Disposable subscribe(Consumer<? super T> onNext)
     * <p>
     * //表示观察者只对被观察者发送的Next & Error事件做出响应
     * public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError)
     * <p>
     * //表示观察者只对被观察者发送的Next & Error & Complete事件做出响应
     * public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
     * Action onComplete)
     * <p>
     * //表示观察者只对被观察者发送的Next & Error & Complete & onSubscribe事件做出响应
     * public final Disposable subscribe(Consumer<? super T> onNext, Consumer<? super Throwable> onError,
     * Action onComplete, Consumer<? super Disposable> onSubscribe)
     */
    public static void test3() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "test3 subscribe ......");
                emitter.onNext("subscribe " + System.currentTimeMillis());
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "test3 onSubscribe ......");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "test3 onNext ......");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "test3 onError ......");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "test3 onComplete ......");
            }
        });
    }

    public static void test4() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("hello");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "accept: " + s);
            }
        });
    }

    public static void tets5() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "subscribe ......");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "accept ......");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "Consumer accept ...... " + throwable.getMessage());
            }
        });
    }

    public static void test6() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "subscribe ......");
                throw new RuntimeException("test6 RuntimeException ......");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "Consumer 1 accept ......");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "Consumer 2 accept ......");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                Log.i(TAG, "Action run ......");
            }
        });
    }
} 