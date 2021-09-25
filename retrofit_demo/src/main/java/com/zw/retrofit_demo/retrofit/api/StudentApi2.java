package com.zw.retrofit_demo.retrofit.api;

import com.zw.retrofit_demo.retrofit.adapter2.MyCallDemo2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 一、@Path
 * 一般是配合url中带有{}使用的。比如：
 *
 * @GET("/article/listproject/{page}/json") 那么在使用@Path的时候，就需要@Path("page")
 * 二、@Query
 * 一般是把key-value拼接到url的后面，?key=value&key1=value1
 * 三、@Field
 * 配合@FormUrlEncoded使用，其实就是表单提交的方式。
 */
public interface StudentApi2 {
    @POST("/")
    @FormUrlEncoded
    /**
     * FormUrlEncoded 表单方式提交数据 必须配合POST Field
     */
    Call<ResponseBody> test(
            @Field("id") long id,
            @Field("name") String name,
            @Field("age") int age);

    @GET("/")
    Call<ResponseBody> test1(
            @Query("id") long id,
            @Query("name") String name,
            @Query("age") int age
    );

    @GET("/")
    Call<String> test2(
            @Query("id") long id,
            @Query("name") String name,
            @Query("age") int age,
            @Query("age1") int age1
    );

    @GET("/")
    MyCallDemo2<String> test3(
            @Query("id") long id,
            @Query("name") String name,
            @Query("age") int age,
            @Query("age1") int age1
    );
}
