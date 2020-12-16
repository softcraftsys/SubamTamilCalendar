package com.softcraft.calendar.ServiceAndOthers;

import android.app.Application;

public class InternetCheck extends Application {

    private static InternetCheck mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized InternetCheck getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}