package com.zw.retrofit_demo.retrofit.adapter2;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;

public class MyCallAdapterDemo2<R> implements CallAdapter<R, MyCallDemo2> {
    private final Type responseType;

    // 下面的 responseType 方法需要数据的类型
    MyCallAdapterDemo2(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public MyCallDemo2 adapt(Call<R> call) {
        return new MyCallDemo2(call);
    }
}
