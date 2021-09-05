package com.zw.new_demo1.net;

import androidx.annotation.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.CallAdapter;
import retrofit2.Retrofit;

public class MyRetrofitFactory extends CallAdapter.Factory {
    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return null;
    }

//    protected static Type getParameterUpperBound(int index, ParameterizedType type) {
//        return Utils.getParameterUpperBound(index, type);
//    }

    /**
     * Extract the raw class type from {@code type}. For example, the type representing {@code
     * List<? extends Runnable>} returns {@code List.class}.
     */
//    protected static Class<?> getRawType(Type type) {
//        return Utils.getRawType(type);
//    }

}
