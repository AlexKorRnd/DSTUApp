package com.example.alex.dstuapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Prefs {

    public static final String PREF_COOKIES = "PREF_COOKIES";
    public static final String PREF_IS_LOGGED_IN = "PREF_IS_LOGGED_IN";
    public static final String PREF_CURRENT_USER_ID = "PREF_CURRENT_USER_ID";

    private static Context context;
    private static SharedPreferences settings;
    private static SharedPreferences.Editor editor;

    public static void init(Context context) {
        Prefs.context = context.getApplicationContext();
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        editor = settings.edit();
        setDefaults();
    }

    private static void setDefaults() {

    }

    public static SharedPreferences getSharedPreferences() {
        return settings;
    }

    // устанавливаем статус авторизован ли пользователь
    public static void setLoggedIn(boolean isLoggedIn) {
        editor.putBoolean(PREF_IS_LOGGED_IN, isLoggedIn).apply();
    }

    public static boolean isLoggedIn() {
        return settings.getBoolean(PREF_IS_LOGGED_IN, false);
    }

    public static void saveCookies(String cookies) {
        editor.putString(PREF_COOKIES, cookies).apply();
    }

    public static String getCookies() {
        return settings.getString(PREF_COOKIES, null);
    }

    public static void saveCurrentUserId(long id) {
        editor.putLong(PREF_CURRENT_USER_ID, id).apply();
    }

    public static long getCurrentUserId() {
        return settings.getLong(PREF_CURRENT_USER_ID, -1);
    }
}
