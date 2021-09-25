package com.zw.retrofit_demo.retrofit.adapter3;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class MyCallAdapterFactory3 extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (returnType == MyCallDemo3.class) {
            return new MyCallAdapter3(returnType);
        }
        return null;
    }
}