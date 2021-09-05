package com.zw.new_demo1.activity1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zw.new_demo1.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.schedulers.IoScheduler;

/**
 * RxJava 的观察者模式
 * RxJava 有四个基本概念：
 * Observable (可观察者，即被观察者)、 Observer (观察者)、 subscribe (订阅)、事件。
 * Observable 和 Observer 通过 subscribe() 方法实现订阅关系，
 * 从而 Observable 可以在需要的时候发出事件来通知 Observer。
 * 与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，
 * 还定义了两个特殊的事件：onCompleted() 和 onError()。
 * 与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick() / onEvent()）之外，还定义了两个特殊的事件：onCompleted() 和 onError()。
 * <p>
 * onCompleted(): 事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。RxJava 规定，当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。
 * onError(): 事件队列异常。在事件处理过程中出异常时，onError() 会被触发，同时队列自动终止，不允许再有事件发出。
 * 在一个正确运行的事件序列中, onCompleted() 和 onError() 有且只有一个，并且是事件序列中的最后一个。需要注意的是，onCompleted() 和 onError() 二者也是互斥的，
 * 即在队列中调用了其中一个，就不应该再调用另一个。
 */
public class RxjavaActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        Button btn = findViewById(R.id.btn_test1);
        btn.setOnClickListener(this);
        btn = findViewById(R.id.btn_test2);
        btn.setOnClickListener(this);
        btn = findViewById(R.id.btn_test3);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test3:
                test3();
                break;
            case R.id.btn_test1:
                test1();
                break;
            case R.id.btn_test2:
                test2();
                break;
        }
    }

    /**
     * RxJava 提供了对事件序列进行变换的支持，这是它的核心功能之一，也是大多数人说『RxJava 真是太好用了』的最大原因。
     * 所谓变换，就是将事件序列中的对象或整个序列进行加工处理，转换成不同的事件或事件序列。
     */
    private void test3() {
        String arr[] = new String[]{"hello1", "Hello2"};
        Observable.fromArray(arr).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Throwable {
                Log.i("test-xxx", "s: " + s);
                if (s.equals("hello1")) {
                    return s + "......";
                }
                return s + "@@@@@@";
            }
        }).subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        if (!TextUtils.isEmpty(s)) {
                            Toast.makeText(RxjavaActivity.this, s, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void test1() {
        // 1  创建 Observer
        // Observer 即观察者，它决定事件触发的时候将有怎样的行为
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("test-xxx", "onSubscribe ......");
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i("test-xxx", "onNext ...... " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("test-xxx", "onError ...... " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.i("test-xxx", "onComplete ......");
            }
        };

        /**
         * 除了 Observer 接口之外
         * RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。
         * Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的：
         */
        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(String s) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };

        // 2) 创建 Observable
        // Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件。
        // RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则：
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            /**
             * Observable 被订阅的时候 该方法会被调用
             */
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("Hello");
            }
        });

        /**
         * create() 方法是 RxJava 最基本的创造事件序列的方法。
         * 基于这个方法， RxJava 还提供了一些方法用来快捷创建事件队列
         */
        // 将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();
        // just(T...): 将传入的参数依次发送出来。
        Observable observable1 = Observable.just("Hello", "Hi", "Aloha");

        // from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来
        String[] words = {"Hello", "Hi", "Aloha"};
        // 将会依次调用：
        // onNext("Hello");
        // onNext("Hi");
        // onNext("Aloha");
        // onCompleted();
        Observable observable2 = Observable.fromArray(words);

        // 3) Subscribe (订阅)
        // api设计是反的： 被观察者 “观察” 观察者
        observable.subscribe(observer);
    }

    /**
     * 链式调用
     */
    private void test2() {
//        Observable.fromArray(folders)
//                .flatMap(new Func1<File, Observable<File>>() {
//                    @Override
//                    public Observable<File> call(File file) {
//                        return Observable.from(file.listFiles());
//                    }
//                })
//                .filter(new Func1<File, Boolean>() {
//                    @Override
//                    public Boolean call(File file) {
//                        return file.getName().endsWith(".png");
//                    }
//                })
//                .map(new Func1<File, Bitmap>() {
//                    @Override
//                    public Bitmap call(File file) {
//                        return getBitmapFromFile(file);
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) {
//                        imageCollectorView.addImage(bitmap);
//                    }
//                });
        String arr[] = new String[]{"hello1", "hello2"};
        // Observable.fromArray(arr).flatMap(new Function1<String>(),Observable<String>);
        Observable.fromArray(arr)
                .subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        Toast.makeText(RxjavaActivity.this, s, Toast.LENGTH_SHORT).show();
                        Log.i("test-xxx", s);
                    }
                });

        Observable.fromArray(arr)
                .subscribeOn(new IoScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i("test-xxx", "onSubscribe " + Thread.currentThread());
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.i("test-xxx", "onNext " + Thread.currentThread() + "," + s);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i("test-xxx", "onError " + Thread.currentThread());

                    }

                    @Override
                    public void onComplete() {
                        Log.i("test-xxx", "onComplete " + Thread.currentThread());

                    }
                });
    }
}