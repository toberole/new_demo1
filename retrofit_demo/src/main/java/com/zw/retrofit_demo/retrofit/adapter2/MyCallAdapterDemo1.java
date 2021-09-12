package com.zw.retrofit_demo.retrofit.adapter2;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

public class MyCallAdapterDemo1<R> implements CallAdapter<R, MyCallDemo1> {
    private final Type responseType;

    // 下面的 responseType 方法需要数据的类型
    MyCallAdapterDemo1(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public MyCallDemo1 adapt(Call<R> call) {
        return new MyCallDemo1(call);
    }
}
