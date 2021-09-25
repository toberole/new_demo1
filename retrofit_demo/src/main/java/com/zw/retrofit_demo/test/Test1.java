package com.zw.retrofit_demo.test;

import android.util.Log;

import com.zw.retrofit_demo.retrofit.adapter3.MyCallAdapterFactory3;
import com.zw.retrofit_demo.retrofit.adapter3.MyCallDemo3;
import com.zw.retrofit_demo.retrofit.api.StudentApi3;
import com.zw.retrofit_demo.retrofit.converter3.MyConverterFactory3;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Test1 {
    private static String TAG = Test1.class.getSimpleName();
    // https://github.com/toberole/new_demo1/blob/main/data.txt
    private static final String BASE_URL = "http://192.168.8.205:8001";

    /**
     * 手动组合OkHttp和Retrofit
     */
    public static void test1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // https://www.jianshu.com/p/308f3c54abdd
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Log.i(TAG, "intercept ......");
                                return chain.proceed(chain.request());
                            }
                        }).addNetworkInterceptor(new Interceptor() {
                            @Override
                            public okhttp3.Response intercept(Chain chain) throws IOException {
                                Log.i(TAG, "NetworkInterceptor intercept ......");
                                return chain.proceed(chain.request());
                            }
                        })
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .callFactory(okHttpClient)
                        .build();
                StudentApi3 studentApi3 = retrofit.create(StudentApi3.class);
                Call<ResponseBody> call = studentApi3.test1();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i(TAG, "onResponse: " + response.message());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i(TAG, "onFailure: " + t.getMessage());
                    }
                });
            }
        }).start();
    }

    public static void test2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addCallAdapterFactory(new MyCallAdapterFactory3())
                            .addConverterFactory(new MyConverterFactory3())
                            .build();
                    StudentApi3 studentApi3 = retrofit.create(StudentApi3.class);
                    MyCallDemo3<String> myCallDemo3 = studentApi3.test2();
                    String str = myCallDemo3.get();
                    Log.i(TAG, "str: " + str);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "Exception: " + e.getMessage());
                }

                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(new MyConverterFactory3())
                            .build();
                    StudentApi3 studentApi3 = retrofit.create(StudentApi3.class);
                    Call<String> call = studentApi3.test3();
                    String str = call.execute().message();
                    Log.i(TAG, "str: " + str);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "Exception: " + e.getMessage());
                }

            }
        }).start();
    }

    // https://zhuanlan.zhihu.com/p/69108165
    private void a() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .build();
            StudentApi3 studentApi3 = retrofit.create(StudentApi3.class);
            Call<ResponseBody> call = studentApi3.test1();
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
            call.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
