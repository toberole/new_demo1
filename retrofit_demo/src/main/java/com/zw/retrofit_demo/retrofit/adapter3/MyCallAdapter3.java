package com.zw.retrofit_demo.retrofit.adapter3;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

public class MyCallAdapter3<R> implements CallAdapter<R, MyCallDemo3> {

    private Type type;

    public MyCallAdapter3(Type type) {
        this.type = type;
    }

    @Override
    public Type responseType() {
        return type;
    }

    @Override
    public MyCallDemo3 adapt(Call<R> call) {
        return new MyCallDemo3(call);
    }
}