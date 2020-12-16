package com.softcraft.calendar.ServiceAndOthers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.ResultReceiver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;
import android.support.v4.media.MediaBrowserCompat;
import androidx.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import androidx.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.core.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;

import com.softcraft.calendar.Activity.DevotionalSongsActivity;
import com.softcraft.calendar.Activity.MediaPlayerActivity;
import com.softcraft.calendar.Activity.SingersCategorySongsActivity;
import com.softcraft.calendar.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class BackgroundAudioService extends MediaBrowserServiceCompat implements AudioManager.OnAudioFocusChangeListener {

    public static final String COMMAND_EXAMPLE = "command_example";
    public static MediaPlayer mMediaPlayer;
    public  static  int songChanges = 0;
    public static MediaSessionCompat mMediaSessionCompat;
    public static int backService = 0;
    boolean isProperStart = true;
    int flagTest;
    int flagPlayTest = 0;
    public static int currentPos, songDuration;
    private final Handler handler = new Handler();
    public  static String SongTitles,SingerTitle,ImageUrl;

    private BroadcastReceiver mNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            }
        }
    };

    private MediaSessionCompat.Callback mMediaSessionCallback = new MediaSessionCompat.Callback() {

        @Override
        public void onPlay() {

            if (backService == 1) {
            } else {
                if (backService == 3) {
                } else {
                    try {
                        super.onPlay();
                        if (!successfullyRetrievedAudioFocus()) {
                            return;
                        }
                        setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);
                        isProperStart = true;
                        mMediaPlayer.start();
                        try {
                            Runnable notification = new Runnable() {
                                public void run() {
                                    getCurrentPosMediaPlayer();
                                }
                            };
                            handler.postDelayed(notification, 1000);
//                            backService = 3;

                            showPlayingNotification();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        public void getCurrentPosMediaPlayer() {
            try {
                currentPos = mMediaPlayer.getCurrentPosition();
                if (mMediaPlayer.isPlaying()) {
                    Runnable notification = new Runnable() {
                        public void run() {
                            getCurrentPosMediaPlayer();
                        }
                    };
                    handler.postDelayed(notification, 1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPause() {
            if (backService == 3) {
            } else {
                try {
                    if (MediaPlayerActivity.pauseFlag == 1) {
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.pause();
                            showPausedNotification();
                            setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
                            MediaPlayerActivity.pauseFlag = 0;
//                            backService=3;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void onPlayFromMediaId(String mediaId, Bundle extras) {
            super.onPlayFromMediaId(mediaId, extras);

            try {
                AssetFileDescriptor afd = getResources().openRawResourceFd(Integer.valueOf(mediaId));
                if (afd == null) {
                    return;
                }
                try {
                    mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());

                } catch (IllegalStateException e) {
                    mMediaPlayer.release();
                    initMediaPlayer();
                    mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                }

                afd.close();
                initMediaSessionMetadata();
            } catch (IOException e) {
                return;
            }
            try {
                mMediaPlayer.prepare();
            } catch (IOException e) {
            }
            //Work with extras here if you want
        }

        @Override
        public void onPlayFromUri(Uri uri, Bundle extras) {
            try {
                mMediaPlayer.release();
                initMediaPlayer();
                mMediaPlayer.setDataSource(BackgroundAudioService.this, uri);
                mMediaPlayer.prepare();
                mMediaSessionCompat.setActive(true);
                setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);
                isProperStart = true;
                mMediaPlayer.start();
                songDuration = mMediaPlayer.getDuration();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(MediaPlayerActivity.SongPosition==MediaPlayerActivity.arrCount-1){
                            setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
                            showPausedNotification();
                        }else {
                            MediaPlayerActivity.SongPosition = MediaPlayerActivity.SongPosition + 1;
                            HashMap<String, String> currentArrPos = MediaPlayerActivity.songArraylist.get(MediaPlayerActivity.SongPosition);
                            String songName = MediaPlayerActivity.songName = currentArrPos.get(DevotionalSongsActivity.SongTitle);
                            String songUrl = MediaPlayerActivity.songUrl = currentArrPos.get(DevotionalSongsActivity.Songurl);
                            String singerName = MediaPlayerActivity.singername = currentArrPos.get(DevotionalSongsActivity.SingerName);
                            String imageUrl = MediaPlayerActivity.albumImage = currentArrPos.get(DevotionalSongsActivity.SongImgUrl);
                            ContinuePlay(songName,songUrl,singerName,imageUrl);
                        }
                    }
                });

                try {
                    Runnable notification = new Runnable() {
                        public void run() {
                            getCurrentPosMediaPlayer();
                        }
                    };
                    handler.postDelayed(notification, 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                initMediaSessionMetadata();
                showPlayingNotification();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCommand(String command, Bundle extras, ResultReceiver cb) {
            super.onCommand(command, extras, cb);
            if (COMMAND_EXAMPLE.equalsIgnoreCase(command)) {
            }
        }

        @Override
        public void onSeekTo(long pos) {
            try {
                mMediaPlayer.seekTo((int) pos);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void ContinuePlay(String SngName,String Sngurl,String SnName,String songImg){
        try{
            if(mMediaPlayer.isPlaying()){
                mMediaPlayer.stop();
                try {
                    initMediaPlayer();
                    mMediaPlayer.setDataSource(Sngurl);
                    mMediaPlayer.prepare();
                    mMediaSessionCompat.setActive(true);
                    songChanges = 1;
                    songDuration = mMediaPlayer.getDuration();
                    SongTitles = SngName;
                    SingerTitle = SnName;
                    ImageUrl = songImg;
                    setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);
                    isProperStart = true;
                    mMediaPlayer.start();




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    initMediaPlayer();
                    mMediaPlayer.setDataSource(Sngurl);
                    mMediaPlayer.prepare();
                    mMediaSessionCompat.setActive(true);
                    songChanges = 1;
                    songDuration = mMediaPlayer.getDuration();
                    SongTitles = SngName;
                    SingerTitle = SnName;
                    ImageUrl = songImg;
                    setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);
                    isProperStart = true;
                    mMediaPlayer.start();
                    songDuration = mMediaPlayer.getDuration();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(MediaPlayerActivity.SongPosition==MediaPlayerActivity.arrCount-1){
                        setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
                        showPausedNotification();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private PendingIntent createOnDismissedIntent(Context context, int notificationId) {
        Intent intent = new Intent(context, NotificationDismissedReceiver.class);
        intent.putExtra("com.softcraft.calendar", notificationId);

        PendingIntent pendingIntent =
                PendingIntent.getBroadcast(context.getApplicationContext(),
                        notificationId, intent, 0);

        return pendingIntent;
    }

    public void showPlayingNotification() {
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            MediaControllerCompat controller = mMediaSessionCompat.getController();
            MediaMetadataCompat mediaMetadata = controller.getMetadata();
            MediaDescriptionCompat description = mediaMetadata.getDescription();
            if (builder == null) {
                return;
            }
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", MediaButtonReceiver.buildMediaButtonPendingIntent(BackgroundAudioService.this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));
            builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0).setMediaSession(mMediaSessionCompat.getSessionToken()));
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setLargeIcon(description.getIconBitmap());
            builder.setContentTitle(MediaPlayerActivity.songName);
            builder.setContentText(MediaPlayerActivity.singername);
            builder.setAutoCancel(false);
            builder.setOngoing(true);

            NotificationManagerCompat.from(BackgroundAudioService.this).notify(1, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showPausedNotification() {
        try {
            MediaControllerCompat controller = mMediaSessionCompat.getController();
            MediaMetadataCompat mediaMetadata = controller.getMetadata();
            MediaDescriptionCompat description = mediaMetadata.getDescription();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            if (builder == null) {
                return;
            }
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", MediaButtonReceiver.buildMediaButtonPendingIntent(BackgroundAudioService.this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));
            builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0).setMediaSession(mMediaSessionCompat.getSessionToken()));
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setLargeIcon(description.getIconBitmap());
            builder.setContentTitle(MediaPlayerActivity.songName);
            builder.setContentText(MediaPlayerActivity.singername);
            builder.setDeleteIntent(createOnDismissedIntent(BackgroundAudioService.this, 1));
            NotificationManagerCompat.from(BackgroundAudioService.this).notify(1, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {

        if (backService == 3) {
//            backService = 0;
        } else {
            try {
                super.onCreate();
                try {
                    if (mMediaPlayer != null) {
                        if (mMediaPlayer.isPlaying()) {
                            mMediaPlayer.pause();
                            setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
                            showPausedNotification();
                            flagPlayTest = 3;
                        } else {
                            mMediaPlayer.start();
                            flagPlayTest = 3;
                        }
                    }
                } catch (IllegalStateException e) {
                    mMediaPlayer = null;
                    e.printStackTrace();
                }
                if (flagPlayTest == 3) {
                } else {
                    initMediaPlayer();
                    initMediaSession();
                    initNoisyReceiver();
//                    backService=3;

                }
                Intent stickyService = new Intent(this, StickyService.class);
                startService(stickyService);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initNoisyReceiver() {
        //Handles headphones coming unplugged. cannot be done through a manifest receiver
        IntentFilter filter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(mNoisyReceiver, filter);
    }

    @Override
    public void onDestroy() {
        try {
            if (!mMediaPlayer.isPlaying()) {
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.abandonAudioFocus(this);
                unregisterReceiver(mNoisyReceiver);
                mMediaSessionCompat.release();
                NotificationManagerCompat.from(this).cancel(1);
            } else {
                showPlayingNotification();
//                setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
                PlaybackStateCompat.Builder playbackstateBuilder = new PlaybackStateCompat.Builder();
                playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PAUSE);
                playbackstateBuilder.setState(PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0);
                mMediaSessionCompat.setPlaybackState(playbackstateBuilder.build());
                isProperStart = true;
                mMediaPlayer.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelNotify(Context context) {
        try {
            NotificationManagerCompat.from(context).cancel(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMediaPlayer() {
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setVolume(1.0f, 1.0f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMediaSession() {
        try {
            ComponentName mediaButtonReceiver = new ComponentName(getApplicationContext(), MediaButtonReceiver.class);
            mMediaSessionCompat = new MediaSessionCompat(getApplicationContext(), "Tag", mediaButtonReceiver, null);

            mMediaSessionCompat.setCallback(mMediaSessionCallback);
            mMediaSessionCompat.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

            Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
            mediaButtonIntent.setClass(this, MediaButtonReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, 0);
            mMediaSessionCompat.setMediaButtonReceiver(pendingIntent);

            setSessionToken(mMediaSessionCompat.getSessionToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMediaPlaybackState(int state) {
        try {
            PlaybackStateCompat.Builder playbackstateBuilder = new PlaybackStateCompat.Builder();
            if (state == PlaybackStateCompat.STATE_PLAYING) {
                playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PAUSE);
            } else {
                playbackstateBuilder.setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_PLAY);
            }
            playbackstateBuilder.setState(state, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0);
            mMediaSessionCompat.setPlaybackState(playbackstateBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMediaSessionMetadata() {
        try {
            MediaMetadataCompat.Builder metadataBuilder = new MediaMetadataCompat.Builder();
            //Notification icon in card
            metadataBuilder.putBitmap(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
            metadataBuilder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
            if (MediaPlayerActivity.songName != null)
                metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, MediaPlayerActivity.songName);
            if (MediaPlayerActivity.singername != null)
                metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, MediaPlayerActivity.singername);

            mMediaSessionCompat.setMetadata(metadataBuilder.build());

            mMediaSessionCompat.setPlaybackState(new PlaybackStateCompat.Builder()
                    .setActions(PlaybackStateCompat.ACTION_PLAY_PAUSE| PlaybackStateCompat.ACTION_SKIP_TO_NEXT|PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean successfullyRetrievedAudioFocus() {

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        int result = audioManager.requestAudioFocus(this,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        return result == AudioManager.AUDIOFOCUS_GAIN;
    }

    @Nullable
    @Override
    public BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints) {
        if (TextUtils.equals(clientPackageName, getPackageName())) {
            return new BrowserRoot(getString(R.string.app_name), null);
        }
        return null;
    }

    @Override
    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.sendResult(null);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {

        try {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS: {
                    if (mMediaPlayer.isPlaying()) {
                        mMediaPlayer.stop();
                    }
                    break;
                }
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: {
                    mMediaPlayer.pause();
                    break;
                }
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.setVolume(0.3f, 0.3f);
                    }
                    break;
                }
                case AudioManager.AUDIOFOCUS_GAIN: {
                    if (mMediaPlayer != null) {
                        if (!mMediaPlayer.isPlaying()) {
                            isProperStart = true;
                            mMediaPlayer.start();
                        }
                        mMediaPlayer.setVolume(1.0f, 1.0f);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MediaButtonReceiver.handleIntent(mMediaSessionCompat, intent);

        if (backService == 3) {
        } else {
            try {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED);
                    showPausedNotification();
                    flagTest = 1;
                    flagPlayTest = 0;
                    backService = 1;
                    try {
                        if (MediaPlayerActivity.mPlayPauseButton != null)
                            MediaPlayerActivity.mPlayPauseButton.setImageResource(R.drawable.play_btn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (DevotionalSongsActivity.playPauseFab != null) {
                            DevotionalSongsActivity.playImg.setImageResource(R.drawable.ic_ply);
                            DevotionalSongsActivity.playPauseFab.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (SingersCategorySongsActivity.playPauseFab != null) {
                            SingersCategorySongsActivity.playImg.setImageResource(R.drawable.ic_ply);
                            SingersCategorySongsActivity.playPauseFab.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    isProperStart = false;
                    mMediaPlayer.start();
                    flagPlayTest = 0;
                    flagTest = 1;
                    setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING);
                    showPlayingNotification();

                    try {
                        if (MediaPlayerActivity.mPlayPauseButton != null)
                            MediaPlayerActivity.mPlayPauseButton.setImageResource(R.drawable.pause_btn);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (DevotionalSongsActivity.playPauseFab != null) {
                            DevotionalSongsActivity.playImg.setImageResource(R.drawable.ic_paus);
                            DevotionalSongsActivity.playPauseFab.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (SingersCategorySongsActivity.playPauseFab != null) {
                            SingersCategorySongsActivity.playImg.setImageResource(R.drawable.ic_paus);
                            SingersCategorySongsActivity.playPauseFab.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


}

