package com.pearl.smartdevicemanager.beans;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 27/07/2016.
 */
public class IoTUsers extends BmobObject {

    private String username;
    private String email;
    private String password;

    public IoTUsers(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
