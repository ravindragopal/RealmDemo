package com.example.chaitanya.realmdemo;

import io.realm.RealmObject;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 24/7/18,5:55 PM.
 * For : ISS 24/7, Pune.
 */
public class UserInfo extends RealmObject {

    private String name;
    private int age;
    private int mobile;
    private boolean status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
