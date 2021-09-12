package com.zw.retrofit_demo.retrofit.api;

import com.zw.retrofit_demo.retrofit.adapter.call_adapter.CustomCall;
import com.zw.retrofit_demo.bean.Student;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StudentApi {
    // @GET("test1" /* test1加在baseurl后面构成访问路径  */)
    @GET("/")
    Call<Student> test1(@Query("id") long id, @Query("name") String name, @Query("age") int age);

    @GET("/")
    Call<String> test2(@Query("id") long id, @Query("name") String name, @Query("age") int age);

    @GET("/")
    CustomCall<String> test3(@Query("id") long id, @Query("name") String name, @Query("age") int age);
}
