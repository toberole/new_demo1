package com.zw.rxjava_demo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.zw.rxjava_demo.R
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.activity_launch.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.Flow

class LaunchActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        btn_test1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test1 -> {
                test1()
            }
        }
    }

    private fun test1() {
        // 观察者
        var o1 = object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.i("test-xxx", "onSubscribe ")
            }

            override fun onNext(t: String) {
                Log.i("test-xxx", "onNext $t")
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
                Log.i("test-xxx", "onComplete ")
            }
        }
        // 被观察者
        var o2 = Observable.create(ObservableOnSubscribe<String> {
            it.onNext("hello ${System.currentTimeMillis()}")
            it.onComplete()
        })

        o2.observeOn(IoScheduler())
            .subscribeOn(IoScheduler())
            .subscribe(o1)

        /**
         * 除了 Observer 接口之外
         * RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。
         * Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的：
         */
        val subscriber: Subscriber<String> = object : Subscriber<String> {
            override fun onSubscribe(s: Subscription?) {

            }

            override fun onNext(t: String?) {

            }

            override fun onError(t: Throwable?) {

            }

            override fun onComplete() {

            }
        }
    }
}