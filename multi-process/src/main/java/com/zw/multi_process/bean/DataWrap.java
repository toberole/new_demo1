package com.zw.multi_process.bean;

import com.zw.multi_process.Data;

public class DataWrap {
    public byte[] bytes;
    public int curLen;

    public DataWrap(Data data) {
        bytes = new byte[data.totalLen];
        System.arraycopy(data.bytes, 0, bytes, 0, data.bytes.length);
        curLen = data.bytes.length;
    }

    public void addBytes(Data data) {
        if (data != null && data.bytes.length != 0) {
            System.arraycopy(data.bytes, 0, bytes, curLen, data.bytes.length);
            curLen += data.bytes.length;
        }
    }

    public boolean isFullPCK() {
        return bytes != null && curLen == bytes.length;
    }
}