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
import com.zw.multi_process.MQCallback;

public class SaveService extends Service {
    private static final String TAG = SaveService.class.getSimpleName();

    public SaveService() {
    }

    private MQ mq;

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            bindMQService();
        }
    };

    private MQCallback.Stub mqCallback = new MQCallback.Stub() {
        @Override
        public void onDispatchData(Data data) throws RemoteException {
            Log.i(TAG, "onDispatchData: " + new String(data.bytes));
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                mq = MQ.Stub.asInterface(iBinder);
                mq.registerCallback(mqCallback);
                mq.asBinder().linkToDeath(deathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
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