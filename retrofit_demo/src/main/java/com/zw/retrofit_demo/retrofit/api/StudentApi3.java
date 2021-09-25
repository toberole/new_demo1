package com.zw.retrofit_demo.retrofit.api;

import com.zw.retrofit_demo.retrofit.adapter3.MyCallDemo3;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface StudentApi3 {
    @GET("/toberole/new_demo1/blob/main/data.txt")
    Call<ResponseBody> test1();

    @GET("/")
    MyCallDemo3 test2();
} 