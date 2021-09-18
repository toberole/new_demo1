package com.zw.rxjava_demo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.zw.rxjava_demo.R
import com.zw.rxjava_demo.activity.test.Test1
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_launch.*
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.lang.RuntimeException

class LaunchActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        btn_test1.setOnClickListener(this)
        btn_BehaviorSubject.setOnClickListener(this)
        btn_Test1.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_BehaviorSubject -> {
                testBehaviorSubject()
            }
            R.id.btn_test1 -> {
                test1()
            }
            R.id.btn_Test1 -> {
                // Test1.test1()
                // Test1.test2()
                Test1.test3()
            }
        }
    }

    /**
     * BehaviorSubject
     * 该类有创建时需要一个默认参数。
     * 并且在发送的时候会先发送离订阅最近的一个值，如果没有值的话就是用这个默认值
     */
    private fun testBehaviorSubject() {
        var observer = object : Observer<Any> {
            override fun onSubscribe(d: Disposable) {
                Log.i("test-xxx", "onSubscribe ......")
            }

            override fun onNext(t: Any) {
                Log.i("test-xxx", "onNext: $t")
            }

            override fun onError(e: Throwable) {
                Log.i("test-xxx", "onError: ${e.message}")
            }

            override fun onComplete() {
                Log.i("test-xxx", "onComplete ......")
            }
        }
        // 订阅之前，最近一个值为空，这里将发送默认值“default”给观察者
        var subject: BehaviorSubject<Any> = BehaviorSubject.create()
        subject.subscribe(observer)
        subject.onNext("one")
        subject.onNext("two")
        subject.onNext("three")
        Log.i("test-xxx", "...... @@@@@@ ......")
        // 订阅之前，最近一个值为“zero”，会先发送zero.因此观察者会接收到“one”、“two”、“three”
        subject = BehaviorSubject.create()
        subject.onNext("zero")
        subject.onNext("one")
        subject.subscribe(observer)
        subject.onNext("two")
        subject.onNext("three")
        Log.i("test-xxx", "...... @@@@@@ ......")
        // 观察者只能接收到onCompleted
        subject = BehaviorSubject.create()
        subject.onNext("zero")
        subject.onNext("one")
        subject.onComplete()
        subject.subscribe(observer)
        Log.i("test-xxx", "...... @@@@@@ ......")
        // 观察者只能接收到onError
        subject = BehaviorSubject.create()
        subject.onNext("zero")
        subject.onNext("one")
        subject.onError(RuntimeException("error"))
        subject.subscribe(observer)
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

        // 类似延迟广播
        val subject: BehaviorSubject<String> = BehaviorSubject.create()
        subject.onNext("Hello")
        subject.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: String) {
                Log.i("test-xxx", "BehaviorSubject s: $t")
            }

            override fun onError(e: Throwable) {
            }

            override fun onComplete() {
            }
        })
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