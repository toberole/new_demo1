package com.zw.new_demo1.util;

import android.os.Environment;

import java.io.RandomAccessFile;


public class MMap {
    public static final String CACHE_FILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache_mmap.txt";
    public static final int CACHE_SIZE = 1024 * 4;

    public native static byte[] native_read(String path);

    public native static int native_write(String path, byte[] bs);

    static {
        try {
            RandomAccessFile raf = new RandomAccessFile(CACHE_FILE, "rw");
            raf.setLength(CACHE_SIZE);
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}