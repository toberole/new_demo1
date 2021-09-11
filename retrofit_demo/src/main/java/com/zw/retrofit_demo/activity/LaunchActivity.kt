package com.zw.retrofit_demo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.zw.retrofit_demo.api.converter.MyStringConverterFactory
import com.zw.retrofit_demo.api.Student
import com.zw.retrofit_demo.api.StudentApi
import com.zw.retrofit_demo.api.call_adapter.CustomCallAdapterFactory
import com.zw.retrofit_demo.service.MyService
import com.zw.rxjava_demo.R
import kotlinx.android.synthetic.main.activity_launch.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/*
Retrofit提供的Converter
    Gson	    com.squareup.retrofit2:converter-gson:2.0.2
    Jackson	    com.squareup.retrofit2:converter-jackson:2.0.2
    Moshi	    com.squareup.retrofit2:converter-moshi:2.0.2
    Protobuf	com.squareup.retrofit2:converter-protobuf:2.0.2
    Wire	    com.squareup.retrofit2:converter-wire:2.0.2
    Simple XML	com.squareup.retrofit2:converter-simplexml:2.0.2
    Scalars	    com.squareup.retrofit2:converter-scalars:2.0.2

Retrofit提供的CallAdapter:
    guava	com.squareup.retrofit2:adapter-guava:2.0.2
    Java8	com.squareup.retrofit2:adapter-java8:2.0.2
    rxjava	com.squareup.retrofit2:adapter-rxjava:2.0.2

 */
class LaunchActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        var PS = arrayOf<String>(
            android.Manifest.permission.INTERNET
        )
        var base_url = "http://192.168.8.205:8001/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        ActivityCompat.requestPermissions(this@LaunchActivity, PS, 110)
        btn_test1.setOnClickListener(this)
        btn_test2.setOnClickListener(this)
        btn_test3.setOnClickListener(this)
        btn_service.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_service -> {
                var i = Intent(this@LaunchActivity, MyService::class.java)
                startService(i)
            }
            R.id.btn_test1 -> {
                var builder = Retrofit.Builder()
                    .baseUrl(base_url)
                    // Converter用于转换原始的ResponseBody
                    .addConverterFactory(GsonConverterFactory.create())
                    // CallAdapter用于适配Call
                    // 针对rxjava1
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    // 针对rxjava2.x
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                var api = builder.create(StudentApi::class.java)
                var call = api.test1(1, "Hello", 11)
                call.enqueue(object : Callback<Student> {
                    override fun onFailure(call: Call<Student>, t: Throwable) {
                        Log.i("test-xxx", "onFailure ${call}")
                    }

                    override fun onResponse(call: Call<Student>, response: Response<Student>) {
                        Log.i("test-xxx", "onResponse ${response.body()}")
                    }
                })
            }

            R.id.btn_test2 -> {
                var builder = Retrofit.Builder()
                    .baseUrl(base_url)
                    // Converter用于转换原始的ResponseBody
                    // 我们自定义的一定要放在Gson这类的Converter前面
                    .addConverterFactory(MyStringConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var api = builder.create(StudentApi::class.java)
                var call = api.test2(1, "Hello", 11)
                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Log.i("test-xxx", "response: ${response.body()}")
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {

                    }

                })
            }

            R.id.btn_test3 -> {
                GlobalScope.launch(Dispatchers.IO) {
                    var builder = Retrofit.Builder()
                        .baseUrl(base_url)
                        .addConverterFactory(MyStringConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        // CallAdapter 将原始的Call转换为目标Call
                        .addCallAdapterFactory(CustomCallAdapterFactory.INSTANCE)
                        .build()
                    var api = builder.create(StudentApi::class.java)
                    var call = api.test3(1, "Hello", 11)
                    var s = call.get()
                    Log.i("test-xxx", "$s")
                }
            }
        }
    }
}