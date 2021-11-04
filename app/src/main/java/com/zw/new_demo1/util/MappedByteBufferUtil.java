package com.zw.new_demo1.util;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBufferUtil {
    private static final String CACHE_PATH = Environment.getExternalStorageDirectory() + "/_java_mmap_cache_.cache";
    private static final String LOG_PATH = Environment.getExternalStorageDirectory() + "/_java_mmap_.log";

    private int map_size = 1024 * 4;

    private int cur_len = 0;

    private MappedByteBuffer buffer;
    private FileOutputStream fileOutputStream;

    public void init() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(CACHE_PATH, "rw");
            //position映射文件的起始位置，size映射文件的大小
            buffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, map_size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String msg) {
        if (null != buffer) {
            byte[] bytes = msg.getBytes();
            if (bytes.length + cur_len >= map_size) {
                flush();
                cur_len = 0;
            }
            buffer.put(msg.getBytes(), cur_len, bytes.length);
            cur_len += bytes.length;
        }
    }

    private void flush() {
        try {
            if (null == fileOutputStream) {
                File file = new File(LOG_PATH);
                fileOutputStream = new FileOutputStream(file, true);
            }
            fileOutputStream.write(buffer.array());
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MappedByteBufferUtil getInstance() {
        return MappedByteBufferHolder.instance;
    }

    private static class MappedByteBufferHolder {
        public static MappedByteBufferUtil instance = new MappedByteBufferUtil();
    }

    private MappedByteBufferUtil() {
    }
} 