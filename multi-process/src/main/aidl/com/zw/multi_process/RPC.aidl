package com.zw.multi_process;

import com.zw.multi_process.Data;

interface RPC {
    int init(out Data data);
    int process(out Data data);
    int stop();
    int release();
}