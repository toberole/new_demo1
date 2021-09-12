package com.zw.retrofit_demo.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyIntercepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = "Token .....";
        Request originalRequest = chain.request();
        if (token == null) {
            return chain.proceed(originalRequest);
        }
        Request authorised = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("timestamp", System.currentTimeMillis() + "")
                .build();
        return chain.proceed(authorised);
    }

    public static MyIntercepter create() {
        return new MyIntercepter();
    }
}

