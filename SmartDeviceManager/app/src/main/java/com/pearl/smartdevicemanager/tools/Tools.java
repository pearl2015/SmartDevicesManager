package com.pearl.smartdevicemanager.tools;

import android.content.SharedPreferences;

import com.pearl.smartdevicemanager.beans.IoTUser;


/**
 * Created by Administrator on 08/08/2016.
 */
public class Tools {
    public static IoTUser readfromSharedPreferences(SharedPreferences sp){
        SharedPreferences spw = sp;
        //SharedPreferences.Editor editor = sp.edit();
        String name = sp.getString("username", null);
        String email = sp.getString("email",null);
        String pwd = sp.getString("password", null);
        IoTUser user = new IoTUser(name,email,pwd);
        return user;
    }
    public static void writetoSharedPreferences(SharedPreferences sp,String username, String email, String pwd){
        SharedPreferences spw = sp;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("password", pwd);
        editor.commit();
    }

    public static void writetoSharedPreferences(SharedPreferences sp, String email, String pwd){
        SharedPreferences spw = sp;
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("email", email);
        editor.putString("password", pwd);
        editor.commit();
    }


    public static void writeCheckStatetoSharedPreferences(SharedPreferences sp, String tag, boolean is_check){
        SharedPreferences spw = sp;
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(tag, is_check);
        editor.commit();
    }
}
