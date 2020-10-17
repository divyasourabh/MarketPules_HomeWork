package com.ds.assignment.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;
import static com.ds.assignment.utils.Constants.USER_AUTH_TOKEN;
import static com.ds.assignment.utils.Constants.USER_SHARED_PREFERENCES;

public class SharedPreferencesUtils {

    public static SharedPreferences getUserSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_SHARED_PREFERENCES, MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void putToken(Context context, String token) {
        if (context != null) {
            SharedPreferences preferences = getUserSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USER_AUTH_TOKEN, token);
            editor.apply();
        }
    }

    public static String getToken(Context context) {
        if (context != null) {
            SharedPreferences preferences = getUserSharedPreferences(context);
            if (preferences != null) {
                return preferences.getString(USER_AUTH_TOKEN, "");
            }

        }
        return "";
    }
}
