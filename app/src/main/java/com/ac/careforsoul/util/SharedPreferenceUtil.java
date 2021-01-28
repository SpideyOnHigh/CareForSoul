package com.ac.careforsoul.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.ac.careforsoul.util.constant.AppConstant;


public class SharedPreferenceUtil {

    private SharedPreferences mSharedPreferences;
    private static SharedPreferenceUtil sharedPreferenceUtil;

    private SharedPreferenceUtil(Context context) {
        mSharedPreferences = context.getSharedPreferences("PrefApp", Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(Context context) {
        if (sharedPreferenceUtil == null) {
            sharedPreferenceUtil = new SharedPreferenceUtil(context);
        }
        return sharedPreferenceUtil;
    }

    public void putData(String key, int data) {
        mSharedPreferences.edit().putInt(key, data).apply();
    }

    public void putData(String key, long data) {
        mSharedPreferences.edit().putLong(key, data).apply();
    }

    public void putData(String key, float data) {
        mSharedPreferences.edit().putFloat(key, data).apply();
    }

    public void putData(String key, String data) {
        mSharedPreferences.edit().putString(key, data).apply();
    }

    public void putData(String key, boolean data) {
        mSharedPreferences.edit().putBoolean(key, data).apply();
    }

    public int getInt(String key) {
        return mSharedPreferences.getInt(key, 0);
    }

    public long getLong(String key) {
        return mSharedPreferences.getLong(key, 0L);
    }

    public float getFloat(String key) {
        return mSharedPreferences.getFloat(key, 0f);
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, AppConstant.EMPTY_TEXT);
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }
}
