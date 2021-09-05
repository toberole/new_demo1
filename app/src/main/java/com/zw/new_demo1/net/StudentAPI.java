package com.zw.new_demo1.net;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 方法的参数中使用了@Query注解，
 * 除此之外还可以使用
 *
 * @QueryMap、@Path、@Body、
 * @FormUrlEncoded/@Field、
 * @Multipart/@Part、@Header/@Headers
 */

/**
 * Retrofit支持的序列化库：
 * <p>
 * Gson: com.squareup.retrofit2:converter-gson
 * <p>
 * Jackson: com.squareup.retrofit2:converter-jackson
 * <p>
 * Moshi: com.squareup.retrofit2:converter-moshi
 * <p>
 * Protobuf: com.squareup.retrofit2:converter-protobuf
 * <p>
 * Wire: com.squareup.retrofit2:converter-wire
 * <p>
 * Simple XML: com.squareup.retrofit2:converter-simplexml
 * <p>
 * Scalars (primitives, boxed, and String): com.squareup.retrofit2:converter-scalars
 */
public interface StudentAPI {
    /**
     * GET请求方式
     * “@Query”表示参数拼接到url上
     */
    @GET("test1" /* test1加在baseurl后面构成访问路径  */)
    Call<Student> register(@Query("id") long id, @Query("name") String name, @Query("age") int age);

    @POST("test1")
    Call<Student> test2(@Body Student student);

    @POST("test1")
    Observable<Student> test3(@Body Student student);
}
