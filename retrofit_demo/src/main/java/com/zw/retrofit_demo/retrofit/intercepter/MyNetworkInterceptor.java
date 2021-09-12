package com.zw.retrofit_demo.retrofit.intercepter;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class MyNetworkInterceptor implements Interceptor {
    public static final String TAG = MyNetworkInterceptor.class.getSimpleName() + "-xxx";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.i(TAG, "MyNetworkInterceptor#intercept ......");
        return chain.proceed(chain.request());
    }
}
