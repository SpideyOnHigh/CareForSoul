package com.ac.careforsoul.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    private final ConnectivityManager connectivityManager;
    private static NetworkUtil networkUtil;

    private NetworkUtil(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static NetworkUtil getInstance(Context context) {
        if (networkUtil == null) {
            networkUtil = new NetworkUtil(context);
        }
        return networkUtil;
    }
    
    public boolean isNetworkAvailable() {
        boolean isNetworkAvailable = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isNetworkAvailable = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return isNetworkAvailable;
    }

}
