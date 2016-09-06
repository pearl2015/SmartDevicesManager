package com.pearl.smartdevicemanager.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 27/07/2016.
 */
public class IoTUser extends BmobObject implements Serializable{

    private static final long serialVersionUID = -7060210544600464481L;
    private String username;
    private String email;
    private String password;

    public IoTUser (String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public IoTUser(){

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
