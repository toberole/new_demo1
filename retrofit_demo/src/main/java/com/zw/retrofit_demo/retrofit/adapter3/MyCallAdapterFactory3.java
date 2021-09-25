package com.zw.retrofit_demo.retrofit.adapter3;

import android.util.Log;

import com.zw.retrofit_demo.retrofit.adapter2.MyCallAdapterDemo2;
import com.zw.retrofit_demo.retrofit.adapter2.MyCallDemo2;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class MyCallAdapterFactory3 extends CallAdapter.Factory {
    public static final String TAG = MyCallAdapterFactory3.class.getSimpleName();

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Log.i(TAG, "returnType: " + returnType);
//        Class<?> returnType_rawType = getRawType(returnType);
//
//        if (returnType == MyCallDemo3.class) {
//            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
//            return new MyCallAdapter3(callReturnType);
//        }
//        return null;
        // 获取原始类型
        Class<?> returnType_rawType = getRawType(returnType);
        if (returnType_rawType == MyCallDemo3.class && returnType instanceof ParameterizedType) {
            Type callReturnType = getParameterUpperBound(0, (ParameterizedType) returnType);
            return new MyCallAdapter3(callReturnType);
        }
        return null;
    }
}