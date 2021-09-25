package com.zw.new_demo1.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonWrapper {
    private static final String TAG = GsonWrapper.class.getSimpleName();
    private static Gson gson;

    public static GsonWrapper getInstance() {
        return GsonWrapperHolder.instance;
    }

    public static Gson get() {
        return gson;
    }

    private GsonWrapper() {
        gson = new GsonBuilder()
                .serializeSpecialFloatingPointValues()
                .disableHtmlEscaping()
                .create();
    }

    private static class GsonWrapperHolder {
        public static GsonWrapper instance = new GsonWrapper();
    }
}