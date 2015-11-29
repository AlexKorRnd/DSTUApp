package com.example.alex.dstuapp;


import android.app.Application;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initFresco();

    }

    private void initFresco() {

    }

    public static App getInstance() {
        return instance;
    }
}
