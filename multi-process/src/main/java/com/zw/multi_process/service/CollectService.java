package com.zw.multi_process.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.zw.multi_process.Data;
import com.zw.multi_process.MQ;

public class CollectService extends Service {
    public static final String TAG = CollectService.class.getSimpleName();

    private MQ mq;

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {
            bindMQService();
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                Log.i(TAG, "onServiceConnected");
                mq = MQ.Stub.asInterface(iBinder);
                mq.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    public CollectService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int n = 0;
                while (n < 1000) {
                    if (mq != null) {
                        try {
                            Data d = new Data();
                            d.end = true;
                            d.bytes = ("Hell World! " + System.currentTimeMillis()).getBytes();
                            d.totalLen = d.bytes.length;
                            Log.i(TAG, "sendData");
                            mq.sendData(d);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        Thread.sleep((long) (1000 * Math.random() * 5));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    n++;
                }
            }
        }).start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");
        bindMQService();
        return super.onStartCommand(intent, flags, startId);
    }

    private void bindMQService() {
        Intent intent = new Intent(getApplicationContext(), MQService.class);
        getApplicationContext().startService(intent);
        getApplicationContext().bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}