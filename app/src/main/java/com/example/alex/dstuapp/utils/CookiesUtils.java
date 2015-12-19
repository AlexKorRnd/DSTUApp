package com.example.alex.dstuapp.utils;

import org.joda.time.DateTime;

public class CookiesUtils {

    public static String generate() {
        DateTime dateTime = new DateTime();
        return dateTime.toString();
    }
}
