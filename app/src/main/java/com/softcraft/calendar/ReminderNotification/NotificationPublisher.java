package com.softcraft.calendar.ReminderNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.softcraft.calendar.Database.DataBaseHelper;

import java.util.ArrayList;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";
    public static String NOTIFICATION1 = "notification";
    public static int NOTIFICATIONID_ = 0;
    DataBaseHelper db;
    Boolean isNottesAvailable = false;

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        isNottesAvailable = false;
        try {
            db = new DataBaseHelper(context);
            ArrayList<ArrayList<String>> arrNotes = db.getNotes();
            String strTitle1 = intent.getExtras().getString("Title");
            String strDesc1 = intent.getExtras().getString("Desc");
            String strDate = intent.getExtras().getString("date");
            int size = arrNotes.size();
            if (size == 0)
                isNottesAvailable = false;
            for (int i = 0; i < arrNotes.size(); i++) {
                ArrayList<String> detailArrayList = arrNotes.get(i);
                String strTitle = detailArrayList.get(2);
                String strDesc = detailArrayList.get(3);
                String date = detailArrayList.get(1);
                if (strTitle1.equalsIgnoreCase(strTitle) && strDesc.equalsIgnoreCase(strDesc1) && date.equalsIgnoreCase(strDate)) {
                    isNottesAvailable = true;
                    db.editNotes(arrNotes.get(i).get(0),arrNotes.get(i).get(1),arrNotes.get(i).get(2),arrNotes.get(i).get(3),arrNotes.get(i).get(4),"","");
                    break;
                } else {
                    isNottesAvailable = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        int id = intent.getIntExtra(NOTIFICATION1, NOTIFICATIONID_);
        NOTIFICATIONID_++;
        NOTIFICATION1 = NOTIFICATION1 + NOTIFICATIONID_;
        int id1 = (int) System.currentTimeMillis();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel("ID", "id", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(mChannel);
        }
        if (isNottesAvailable) {
            notificationManager.notify(id1, notification);
        }
    }
}

