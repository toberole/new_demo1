package com.zw.retrofit_demo.bean;

public class Student {
    public long id;
    public String name;
    public String name1;
    public int age;
    public int age1;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", name1='" + name1 + '\'' +
                ", age=" + age +
                ", age1=" + age1 +
                '}';
    }
}
