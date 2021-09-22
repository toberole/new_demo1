package com.zw.retrofit_demo.test;

import android.util.Log;

import com.zw.retrofit_demo.retrofit.api.StudentApi3;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Test1 {
    private static String TAG = Test1.class.getSimpleName();
    private static final String BASE_URL = "http://11.2.169.179:8001";

    public static void test1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // https://www.jianshu.com/p/308f3c54abdd
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
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
}
