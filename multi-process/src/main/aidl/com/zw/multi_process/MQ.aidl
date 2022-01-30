package com.zw.multi_process;

import com.zw.multi_process.Data;
import com.zw.multi_process.MQCallback;

interface MQ {
    int sendData(out Data data);
    void registerCallback(in MQCallback cb);
    void unRegisterCallback(in MQCallback cb);
}