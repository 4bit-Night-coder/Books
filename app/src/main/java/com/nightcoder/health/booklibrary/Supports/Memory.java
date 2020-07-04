package com.nightcoder.health.booklibrary.Supports;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class Memory {
    private static final String USER_PREF = "User pref";

    public static boolean getBool(Context context, String key, boolean defVal) {
        SharedPreferences prefs = context.getSharedPreferences(USER_PREF, MODE_PRIVATE);
        return prefs.getBoolean(key, defVal);
    }

    public static void putString(Context context, String key, String val) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREF, MODE_PRIVATE).edit();
        editor.putString(key, val);
        editor.apply();
    }

    public static void putBool(Context context, String key, boolean val) {
        SharedPreferences.Editor editor = context.getSharedPreferences(USER_PREF, MODE_PRIVATE).edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public static String getString(Context context, String key, String defVal) {
        SharedPreferences prefs = context.getSharedPreferences(USER_PREF, MODE_PRIVATE);
        return prefs.getString(key, defVal);
    }


}
