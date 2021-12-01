package com.zw.new_demo1.activity1;

import android.graphics.drawable.Drawable;

public class ProcessInfo {
    private String name;
    private long memSize;
    private boolean isSys;
    private String processName;
    private Drawable icon;

    public ProcessInfo(String name, Drawable icon,
                       long memSize, boolean isSys,
                       String processName) {
        this.name = name;
        this.icon = icon;
        this.memSize = memSize;
        this.isSys = isSys;
        this.processName = processName;
    }

    @Override
    public String toString() {
        return "ProcessInfo{" +
                "name='" + name + '\'' +
                ", memSize=" + memSize +
                ", isSys=" + isSys +
                ", processName='" + processName + '\'' +
                '}';
    }
}