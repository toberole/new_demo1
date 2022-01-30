package com.zw.multi_process.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UploadService extends Service {
    public UploadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}