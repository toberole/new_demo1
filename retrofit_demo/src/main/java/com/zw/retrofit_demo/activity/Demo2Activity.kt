package com.zw.retrofit_demo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.zw.retrofit_demo.Constant
import com.zw.retrofit_demo.R
import com.zw.retrofit_demo.retrofit.adapter2.MyCallAdapterFactoryDemo1
import com.zw.retrofit_demo.retrofit.intercepter.MyIntercepter
import com.zw.retrofit_demo.retrofit.intercepter.MyNetworkInterceptor
import com.zw.retrofit_demo.retrofit.api.StudentApi2
import com.zw.retrofit_demo.retrofit.converter2.MyStringConverterFactoryDemo1
import kotlinx.android.synthetic.main.activity_demo2.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class Demo2Activity : AppCompatActivity(), View.OnClickListener {
    companion object {
        var TAG = Demo2Activity::class.java.simpleName + "-xxx"
    }

    private var test_index = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)

        btn_test1.setOnClickListener(this)
        btn_Interceptor.setOnClickListener(this)
        btn_Converter.setOnClickListener(this)
        btn_Adapter.setOnClickListener(this)
        btn_test5.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        test_index = Integer.parseInt(et_test_index.text.toString())
        when (v?.id) {
            R.id.btn_test1 -> {
                when (test_index) {
                    0 -> {
                        test()
                    }
                    1 -> {
                        test1()
                    }
                }
            }

            R.id.btn_Interceptor -> {
                test_Interceptor()
            }
            R.id.btn_Converter -> {
                test_Converter()
            }
            R.id.btn_Adapter -> {
                test_Adapter()
            }
            R.id.btn_test5 -> {
                test5()
            }
        }
    }

    private fun test5() {

    }

    private fun test_Adapter() {
        var retrofit = Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(MyStringConverterFactoryDemo1.create())
            .addCallAdapterFactory(MyCallAdapterFactoryDemo1.create())
            .build()
        var api = retrofit.create(StudentApi2::class.java)
        var mycall = api.test3(1, "", 1, 1)
        GlobalScope.launch(Dispatchers.IO) {
            Log.i(TAG, "mycall get: ${mycall.get()}")
        }
    }

    private fun test_Converter() {
        var retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(MyStringConverterFactoryDemo1.create())
            .build()
        var api = retrofit.create(StudentApi2::class.java)
        var call = api.test2(1, "hello", 20, 11)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.i(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }
        })
    }

    /**
     * 添加拦截器就是给OkHttpClient
     */
    private fun test_Interceptor() {
        var okhttp_build =
            OkHttpClient.Builder()
                .addInterceptor(MyIntercepter.create())
                .addNetworkInterceptor(MyNetworkInterceptor())

        var retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okhttp_build.build())
            // .addConverterFactory()
            // .addCallAdapterFactory()
            .build()

        var api = retrofit.create(StudentApi2::class.java)
        var call = api.test(1, "hello", 110)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.i(TAG, "onResponse: ${response.body()?.string()}")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.i(TAG, "onFailure: ${call.request().body().toString()}")
            }
        })
    }

    private fun test() {
        var retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            //.addCallAdapterFactory()
            .build()
        var api = retrofit.create(StudentApi2::class.java)
        var call = api.test(1, "hello", 2)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                var str = response.body()?.string()
                var code = response.code()
                var isSuccessful = response.isSuccessful
                var msg = response.message()
                var headers = response.headers()
                Log.i(TAG, "str: $str")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })
    }

    /**
     * 回调在主线程
     */
    private fun test1() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.i(TAG, "test1 ......")

            var retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .build()
            var api = retrofit.create(StudentApi2::class.java)
            var call = api.test1(1, "hello", 110)
            Log.i(TAG, "call: $call")
            call.enqueue(object : Callback<ResponseBody> {
                // 回调在主线程
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.i(TAG, "xxx call: $call")
                    var body = response.body()?.string()
                    Log.i(TAG, "body: $body,thread: ${Thread.currentThread()}")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.i(TAG, "Throwable: ${t.message}")
                }
            })
        }
    }
}