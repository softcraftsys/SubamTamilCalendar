package com.softcraft.calendar.ServiceAndOthers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

/**
 * Created by Softcraft on 9/1/2017.
 */

public class NotificationDismissedReceiver  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getExtras().getInt("com.softcraft.calendar");
        try {
            PlaybackStateCompat.Builder playbackstateBuilder = new PlaybackStateCompat.Builder();
            playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PLAY);
            playbackstateBuilder.setState(PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0);
            BackgroundAudioService.mMediaSessionCompat.setPlaybackState(playbackstateBuilder.build());
            if(BackgroundAudioService.mMediaPlayer.isPlaying()){
                BackgroundAudioService.mMediaPlayer.stop();
                BackgroundAudioService.mMediaPlayer.reset();
                BackgroundAudioService.mMediaPlayer.release();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        if(notificationId==1){
            Log.d("Cancel",notificationId+"");
        }
      /* Your code to handle the event here */
    }
}
