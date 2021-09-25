package com.zw.retrofit_demo.retrofit.adapter3;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

public class MyCallAdapter3 implements CallAdapter<Call, MyCallDemo3> {

    private Type type;

    public MyCallAdapter3(Type type) {
        this.type = type;
    }

    @Override
    public Type responseType() {
        return type;
    }

    @Override
    public MyCallDemo3 adapt(Call<Call> call) {
        return new MyCallDemo3(call);
    }
}