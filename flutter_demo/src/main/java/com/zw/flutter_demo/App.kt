package com.zw.flutter_demo

import android.app.Application
import io.flutter.embedding.engine.FlutterEngineGroup

class App : Application() {
    companion object{
        var flutterEngineGroup: FlutterEngineGroup? = null
    }
    override fun onCreate() {
        super.onCreate()
        flutterEngineGroup = FlutterEngineGroup(this)
    }
}