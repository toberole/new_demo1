package com.zw.retrofit_demo.retrofit.adapter;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

public class CustomCallAdapter<R, T> implements CallAdapter {

    private final Type responseType;

    // 下面的 responseType 方法需要数据的类型
    CustomCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public CustomCall<T> adapt(Call call) {
        return new CustomCall<T>(call);
    }
}