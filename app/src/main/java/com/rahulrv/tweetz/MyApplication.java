package com.rahulrv.tweetz;

import android.app.Application;

import com.rahulrv.tweetz.di.component.AppComponent;
import com.rahulrv.tweetz.di.component.DaggerAppComponent;

/**
 *
 *
 */
public class MyApplication extends Application {

    private AppComponent component;

    @Override public void onCreate() {
        super.onCreate();
        component = DaggerAppComponent.create();
    }

    public AppComponent getComponent() {
        return component;
    }
}
