package com.zw.multi_process;

import com.zw.multi_process.Data;

interface MQCallback {
    void onDispatchData(in Data data);
}