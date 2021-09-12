package com.zw.new_demo1.util;

import android.util.Log;

import java.io.File;
import java.util.List;

public class FileUtil {
    public static final String TAG = FileUtil.class.getSimpleName();

    public static void logFiles(String path, List<String> paths_res) {
        if (path == null) return;

        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] fs = file.listFiles();
                if (fs != null && fs.length > 0) {
                    for (File f : fs) {
                        if (f.isDirectory()) {
                            logFiles(f.getAbsolutePath(), paths_res);
                        } else {
                            paths_res.add(f.getAbsolutePath());
                        }
                    }
                }
            } else {
                paths_res.add(file.getAbsolutePath());
            }
        }
    }
} 