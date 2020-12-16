package com.softcraft.calendar.History;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import androidx.multidex.MultiDex;

import java.util.Calendar;

public class TamilCalendarApplication extends Application {

    public boolean isDayMode;


    public void onCreate() {
        super.onCreate();
        StrictMode.setVmPolicy(new Builder().build());
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
        Calendar instance = Calendar.getInstance();
        Calendar instance2 = Calendar.getInstance();
        Calendar instance3 = Calendar.getInstance();
//        instance2.set(11, 6);
//        instance2.set(12, 30);
//        boolean z = false;
//        instance2.set(13, 0);
//        instance3.set(11, 18);
//        instance3.set(12, 30);
//        instance3.set(13, 0);
//        long timeInMillis = instance.getTimeInMillis();
//        if (timeInMillis >= instance2.getTimeInMillis() && timeInMillis < instance3.getTimeInMillis()) {
//            z = true;
//        }
//        this.isDayMode = z;
    }

}
