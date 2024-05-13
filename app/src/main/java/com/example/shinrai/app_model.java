package com.example.shinrai;

import android.graphics.drawable.Drawable;

import java.lang.reflect.Constructor;

public class app_model {

    String appname;

    Drawable appicon;
    int status; //0 if app is unlocked and 1 if app is locked
    String packagename;


    public app_model(String appname, Drawable appicon, int status, String packagename){
        this.appname = appname;
        this.appicon =appicon;
        this.status=status;
        this.packagename=packagename;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }
}
