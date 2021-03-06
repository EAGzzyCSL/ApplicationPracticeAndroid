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

    public static int getInt(String key, int defValue) {
        return sPreferences.getInt(key, defValue);
    }

    public static String getString(String key, String defValue) {
        return sPreferences.getString(key, defValue);
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

    public static void remove(String key) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}
