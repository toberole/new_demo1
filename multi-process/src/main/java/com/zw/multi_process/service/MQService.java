package com.zw.multi_process.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.zw.multi_process.Data;
import com.zw.multi_process.MQ;
import com.zw.multi_process.MQCallback;
import com.zw.multi_process.bean.DataWrap;

import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class MQService extends Service {
    private static final String TAG = MQService.class.getSimpleName();

    private final Map<String, DataWrap> tempData = new ConcurrentHashMap<>();

    private final BlockingDeque<Data> deque = new LinkedBlockingDeque<>();

    private RemoteCallbackList<MQCallback> mCallBacks = new RemoteCallbackList<>();

    private final MQ.Stub mqBinder = new MQ.Stub() {
        @Override
        public int sendData(Data data) throws RemoteException {
            dispatchData(data);
            return 0;
        }

        @Override
        public void registerCallback(MQCallback cb) throws RemoteException {
            if (cb != null) {
                mCallBacks.register(cb);
            }
        }

        @Override
        public void unRegisterCallback(MQCallback cb) throws RemoteException {
            if (cb != null) {
                mCallBacks.unregister(cb);
            }
        }
    };

    private void dispatchData(Data data) {
        int num = mCallBacks.beginBroadcast();
        Log.i(TAG, "dispatchData: " + num);

        for (int i = 0; i < num; i++) {
            try {
                mCallBacks.getBroadcastItem(i).onDispatchData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mCallBacks.finishBroadcast();
    }

    public MQService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mqBinder;
    }
}