package com.softcraft.calendar.Activity;


import android.app.Activity;
import android.os.Bundle;

public class HelperActivity extends Activity {

    private HelperActivity ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        ctx = this;
        String action = (String) getIntent().getExtras().get("DO");
        if (action.equals("pause")) {
            MediaPlayerActivity mediaPause = new MediaPlayerActivity();
//            mediaPause.stopMediaPlay(0);
        } else if (action.equals("close")) {
            MediaPlayerActivity mediaPause = new MediaPlayerActivity();
//            mediaPause.stopMediaPlay(1);
        }

        if (!action.equals("reboot"))
            finish();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
}
