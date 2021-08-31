package com.zw.new_demo1

import android.app.Application

class App: Application() {
    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}