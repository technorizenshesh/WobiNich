package com.my.wobinichapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Preference {

public static final String APP_PREF = "KapsiePreferences";

public static SharedPreferences sp;
public static String KEY_USER_ID = "user_id";
public static String KEY_USE_ID = "user_id";
public static String KEY_USER_status = "user_status";
public static String KEY_USER_image = "user_image";
public static String KEY_grp_id = "grp_id";
public static String KEY_grp_Name= "grp_Name";
public static String KEY_grp_IMage= "grp_IMage";
public static String key_switch_shift_change = "shift_change";
public static String key_Post_count = "Post_count";

private Activity activity;
private Context context;

public Preference(Activity activity){
    this.activity = activity;
    this.context = activity.getApplicationContext();
}

//-----------------------------------


public boolean isNetworkAvailable()
{
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo ni = cm.getActiveNetworkInfo();
    return ni != null;
}

public static String get(Context context , String key) {
    sp = context.getSharedPreferences(APP_PREF, 0);
    String userId = sp.getString(key, "0");
    return userId;
}
public static void save(Context context, String key, String value) {
    sp = context.getSharedPreferences(APP_PREF, 0);
    SharedPreferences.Editor edit = sp.edit();
    edit.putString(key, value);
    edit.commit();
}

public static void saveInt(Context context, String key, int value) {
    sp = context.getSharedPreferences(APP_PREF, 0);
    SharedPreferences.Editor edit = sp.edit();
    edit.putInt(key, value);
    edit.commit();
}
public static void saveFloat(Context context, String key, Float value) {
    sp = context.getSharedPreferences(APP_PREF, 0);
    SharedPreferences.Editor edit = sp.edit();
    edit.putFloat(key, value);
    edit.commit();
}

public static int getInt(Context context , String key) {
    sp = context.getSharedPreferences(APP_PREF, 0);
    int userId = sp.getInt(key,0);
    return userId;
}

public static Float getFloat(Context context , String key) {
    sp = context.getSharedPreferences(APP_PREF, 0);
    Float userId = sp.getFloat(key,0);
    return userId;
}


public static boolean saveBool(Context context, String key, Boolean value) {
    sp = context.getSharedPreferences(APP_PREF, 0);
    SharedPreferences.Editor edit = sp.edit();
    edit.putBoolean(key, value);
    edit.commit();
    return false;
}

public static Boolean getBool(Context context , String key) {
    sp = context.getSharedPreferences(APP_PREF, 0);
    return sp.getBoolean(key,false);
}

public static void clearPreference(Context context) {
    sp = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
    SharedPreferences.Editor edit = sp.edit();
    edit.clear();
    edit.apply();
}


}
