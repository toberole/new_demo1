package com.zw.retrofit_demo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.zw.retrofit_demo.Constant
import com.zw.retrofit_demo.R
import com.zw.retrofit_demo.retrofit.MyIntercepter
import com.zw.retrofit_demo.retrofit.api.StudentApi2
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
        btn_test2.setOnClickListener(this)
        btn_test3.setOnClickListener(this)
        btn_test4.setOnClickListener(this)
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

            R.id.btn_test2 -> {
                test2()
            }
            R.id.btn_test3 -> {
                test3()
            }
            R.id.btn_test4 -> {
                test4()
            }
            R.id.btn_test5 -> {
                test5()
            }
        }
    }

    private fun test5() {

    }

    private fun test4() {

    }

    private fun test3() {

    }

    /**
     * 添加拦截器就是给OkHttpClient
     */
    private fun test2() {
        var okhttp_build =
            OkHttpClient.Builder()
                .addInterceptor(MyIntercepter.create())

        var retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okhttp_build.build())
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

    private fun test1() {
        GlobalScope.launch(Dispatchers.IO) {
            Log.i(TAG, "test1 ......")

            var retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .build()
            var api = retrofit.create(StudentApi2::class.java)
            var call = api.test1(1, "hello", 110)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    var body = response.body()?.string()
                    Log.i(TAG, "body: $body")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.i(TAG, "Throwable: ${t.message}")
                }
            })
        }
    }
}