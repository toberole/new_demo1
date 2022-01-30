package com.zw.multi_process;

import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
    public String id;
    public byte[] bytes;
    public int totalLen;
    public boolean end;

    public Data(byte[] data) {
        this.bytes = data;
    }

    public Data() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeByteArray(this.bytes);
        dest.writeInt(this.totalLen);
        dest.writeByte(this.end ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.id = source.readString();
        this.bytes = source.createByteArray();
        this.totalLen = source.readInt();
        this.end = source.readByte() != 0;
    }

    protected Data(Parcel in) {
        this.id = in.readString();
        this.bytes = in.createByteArray();
        this.totalLen = in.readInt();
        this.end = in.readByte() != 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel source) {
            return new Data(source);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };
}