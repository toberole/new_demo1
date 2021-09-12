package com.zw.retrofit_demo.bean;

public class Student {
    public long id;
    public String name;
    public int age;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
