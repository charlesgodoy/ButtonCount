package com.cdgodoy.buttoncount.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {

    private static final String MY_PREFS = "my_prefs";

    private SharedPreferences prefs;

    public SharedPrefUtil(Context context) {
        prefs = context.getSharedPreferences(MY_PREFS, Context.MODE_PRIVATE);
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return prefs.getInt(key, -1);
    }
}
