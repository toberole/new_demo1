package com.zw.rxjava_demo.activity.test;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Test3 {
    private static String TAG = Test3.class.getSimpleName();

    public static void test1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onComplete();
                Log.i(TAG, "subscribe");
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "accept: " + integer);
            }
        });

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {

            }
        }).retryWhen(new MyRetryWhen()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {

            }
        });
    }

    private static class MyRetryWhen implements io.reactivex.functions.Function<Observable<Throwable>, io.reactivex.ObservableSource<String>> {
        @Override
        public ObservableSource<String> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
            return Observable.never();
        }
    }

    public static void test2() {

        Observer<String> observer = new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i(TAG, "onNext " + s + " " + Thread.currentThread().getName());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) {
                Log.i(TAG, "subscribe--运行线程：" + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                //FlatMap变换
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) {
                        Log.i(TAG, "flatMap--运行线程：" + Thread.currentThread().getName());

                        //将int类型参数转换为string类型参数，然后用just操作符将其重新发射出去
                        return Observable.just("flatMap " + String.valueOf(integer));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
