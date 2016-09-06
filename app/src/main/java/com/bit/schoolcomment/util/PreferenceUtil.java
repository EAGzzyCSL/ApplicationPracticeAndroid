package com.bit.schoolcomment.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bit.schoolcomment.MyApplication;

public class PreferenceUtil {

    private static SharedPreferences sPreferences;

    static {
        sPreferences = PreferenceManager.getDefaultSharedPreferences(MyApplication.getDefault());
    }

    public static boolean contains(String key) {
        return sPreferences.contains(key);
    }

    public static int getInt(String key) {
        return sPreferences.getInt(key, 0);
    }

    public static String getString(String key) {
        return sPreferences.getString(key, null);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
