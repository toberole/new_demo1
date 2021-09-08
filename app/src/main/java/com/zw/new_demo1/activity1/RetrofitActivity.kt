package com.zw.new_demo1.activity1

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.zw.new_demo1.R
import com.zw.new_demo1.net.Student
import com.zw.new_demo1.net.StudentAPI
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.internal.schedulers.IoScheduler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        var base_url = "http://192.168.8.205:8001/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_test1.setOnClickListener(this)
        btn_test2.setOnClickListener(this)
        btn_test3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_test3 -> {
                test3()
            }
            R.id.btn_test1 -> {
                test1()
            }

            R.id.btn_test2 -> {
                test2()
            }
        }
    }

    private fun test3() {
        var retrofit =
            Retrofit.Builder().baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
                .build()
        var api = retrofit.create(StudentAPI::class.java)
        var stu = Student()
        stu.id = System.currentTimeMillis()
        stu.name = "Hello_${System.currentTimeMillis()}"
        stu.age = 11
        api.test3(stu)
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(IoScheduler())
            .subscribe(Consumer {
                Log.i("test-xxx", "$it")
                Toast.makeText(this@RetrofitActivity, "$it", Toast.LENGTH_SHORT).show()
            })


    }

    private fun test2() {
        var b =
            Retrofit.Builder().baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                //.addCallAdapterFactory(MyRetrofitFactory())
                .build()
        var api = b.create(StudentAPI::class.java)
        var stu = Student()
        stu.id = System.currentTimeMillis()
        stu.name = "Hello_${System.currentTimeMillis()}"
        stu.age = 11
        var call = api.test2(stu)
        call.enqueue(object : Callback<Student> {
            override fun onResponse(call: Call<Student>, response: Response<Student>) {

            }

            override fun onFailure(call: Call<Student>, t: Throwable) {

            }

        })
    }

    private fun test1() {
        GlobalScope.launch(Dispatchers.IO) {
            var retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.8.205:8001/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            var api = retrofit.create(StudentAPI::class.java)
            var call = api.register(110, "Hello World!", 1)
            call.enqueue(object : Callback<Student> {
                override fun onResponse(call: Call<Student>, response: Response<Student>) {
                    Log.i("test-xxx", "onResponse ${response.body()}")
                }

                override fun onFailure(call: Call<Student>, t: Throwable) {
                    Log.i("test-xxx", "onFailure ${t.message}")
                }
            })
        }
    }

    private fun testx(){
//        var ctx:Context? = null
//        ctx？.startInstrumentation()
    }
}