package com.zw.rxjava_demo.activity.test;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.schedulers.IoScheduler;
import io.reactivex.schedulers.Schedulers;

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

    public static void test7() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@androidx.annotation.NonNull ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "subscribe ...... " + Thread.currentThread().getName());
                emitter.onNext("Hello");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@androidx.annotation.NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe ...... " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(@androidx.annotation.NonNull String s) {
                        Log.i(TAG, "onNext ...... " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(@androidx.annotation.NonNull Throwable e) {
                        Log.i(TAG, "onError ......");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete ......");
                    }
                });
    }

    public static void test8() {
        Observable.error(new RuntimeException("Test error"))
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@androidx.annotation.NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe ......");
                    }

                    @Override
                    public void onNext(@androidx.annotation.NonNull Object o) {

                    }

                    @Override
                    public void onError(@androidx.annotation.NonNull Throwable e) {
                        Log.i(TAG, "onError ......" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void test9() {
        // Observable.never().publish()
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, integer + "");
            }
        });
    }

    public static void test10() {
        // Observable.defer 直到有Observer观察者订阅时，
        // 才会通过Observeable的工厂方法动态创建Observeable，并且发送事件
        int i = 100;
        Observable<Integer> o1 = Observable.defer(new Callable<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> call() throws Exception {
                return Observable.just(i);
            }
        });
        o1.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "integer: " + integer);
            }
        });


        Observable.timer(1000, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "aLong: " + aLong);
                    }
                });

        Observable.interval(1000, TimeUnit.MILLISECONDS, Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "x: " + aLong + "," + System.currentTimeMillis() / 1000);
                    }
                });

    }

    public static void test11() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@androidx.annotation.NonNull ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG, "subscribe ......");
                emitter.onNext("Hello ...... 1");
            }
        }).publish(new Function<Observable<String>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@androidx.annotation.NonNull Observable<String> stringObservable) throws Exception {
                Log.i(TAG, "apply ......");
                return stringObservable;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.i(TAG, "s: " + s);
            }
        });
    }

    public static void test12(){
        // https://www.jianshu.com/p/853024705eec/
        // https://blog.csdn.net/zhangphil/article/details/67640325
        // https://www.jianshu.com/p/e19f8ed863b1
        // https://blog.csdn.net/yuzhiqiang666/article/details/51539708
    }
} 