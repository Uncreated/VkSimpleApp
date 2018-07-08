package com.uncreated.vksimpleapp.model.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.uncreated.vksimpleapp.App;

public class NetworkStatus {
    private static Status currentStatus = Status.OFFLINE;

    private static Status getStatus() {
        ConnectivityManager cm = (ConnectivityManager) App.getApp().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                currentStatus = Status.WIFI;
            }

            if (activeNetwork.getType() == ConnectivityManager.TYPE_ETHERNET) {
                currentStatus = Status.ETHERNET;
            }

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                currentStatus = Status.MOBILE;
            }
        } else {
            currentStatus = Status.OFFLINE;
        }

        return currentStatus;

    }

    public static boolean isOnline() {
        getStatus();
        return currentStatus.equals(Status.WIFI) || currentStatus.equals(Status.MOBILE) || currentStatus.equals(Status.ETHERNET);
    }

    public static boolean isOffline() {
        return getStatus().equals(Status.OFFLINE);
    }

    public enum Status {
        WIFI,
        MOBILE,
        ETHERNET,
        OFFLINE
    }
}
