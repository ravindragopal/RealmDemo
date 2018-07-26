package com.example.chaitanya.realmdemo.Model;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * @author : Chaitanya Tarole, Pune.
 * @since : 24/7/18,5:55 PM.
 * For : ISS 24/7, Pune.
 */
public class UserInfo extends RealmObject {

    @PrimaryKey
    private String name;

    private int age;

    private String mobile;

    private String Email;

    private String bloodgroup;

    private boolean status;

    RealmList<HobbiesModel> hobbies;

    public RealmList<HobbiesModel> getHobbies() {
        return hobbies;
    }

    public void setHobbies(RealmList<HobbiesModel> hobbies) {
        this.hobbies = hobbies;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
