package com.ac.careforsoul.util;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.StringRes;

import java.util.Collection;

public class CommonUtil {

    private Context context;

    @SuppressLint("StaticFieldLeak")
    private static CommonUtil commonUtil;

    private CommonUtil(Context context) {
        this.context = context;
    }

    public static CommonUtil getInstance(Context context) {
        if (commonUtil == null) {
            commonUtil = new CommonUtil(context);
        }
        return commonUtil;
    }

    public String getString(@StringRes int resId){
        return context.getString(resId);
    }


    public boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public boolean isNotNullOrEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

}
