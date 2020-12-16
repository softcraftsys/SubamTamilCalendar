package com.softcraft.calendar.ServiceAndOthers;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import androidx.annotation.Nullable;


public class StickyService extends Service {
    private SharedPreferences mPrefs;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        try {
            super.onTaskRemoved(rootIntent);
            BackgroundAudioService backgroundAudioService = new BackgroundAudioService();
            backgroundAudioService.cancelNotify(StickyService.this);
            backgroundAudioService.backService = 3;
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}




