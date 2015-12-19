package com.example.alex.dstuapp;


import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.activeandroid.ActiveAndroid;
import com.example.alex.dstuapp.ui.NavigationDrawerActivity;
import com.example.alex.dstuapp.ui.StartActivity;
import com.example.alex.dstuapp.utils.Prefs;
import com.facebook.drawee.backends.pipeline.Fresco;

import net.danlew.android.joda.JodaTimeAndroid;

public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Prefs.init(this);
        JodaTimeAndroid.init(this);
        initFresco();
        ActiveAndroid.initialize(this);
    }


    private void initFresco() {
        Fresco.initialize(this);
    }

    public static App getInstance() {
        return instance;
    }

    public Intent getStartActivityIntent(Context context) {

         if (Prefs.isLoggedIn()) {
            return new Intent(context, NavigationDrawerActivity.class);
        } else {
            return new Intent(context, StartActivity.class);
        }
    }
}
