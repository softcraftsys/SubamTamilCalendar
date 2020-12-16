package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.BackgroundAudioService;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.softcraft.calendar.DevotionalWallpapers.DevotionalWallpepersPreviewActivity.REQUEST_WRITE_PERMISSION;

public class MediaPlayerActivity extends AppCompatActivity {

    public static ImageButton mPlayPauseButton;
    SeekBar seekBarProgress;
    private int mediaFileLengthInMilliseconds;
    ImageButton frwdBtn, backwrdBtn, songImage;
    ImageView backBtn, downloadSongBtn;
    int currentPos;
    public TextView currentLength, fullLength, SongTitle, SingerName, HeadTitle;
    private final Handler handler = new Handler();
    public static String albumImage, singerImage, songUrl, songName, singername, songId;
    private static final int STATE_PAUSED = 0;
    private static final int STATE_PLAYING = 1;
    private int mCurrentState;
    private ProgressBar mProgress;
    private MediaBrowserCompat mMediaBrowserCompat;
    private MediaControllerCompat mMediaControllerCompat;
    public static int pauseFlag, flagDuration, arrCount, lengthFlag, SongPosition;
    private HashMap<String, String> resultp;
    public static ArrayList<HashMap<String, String>> songArraylist;
    int ArrayFlag;

    TextView percentageTv;
    ProgressBar progressBar;
    ProgressBar percentageProgress;

    private MediaBrowserCompat.ConnectionCallback mMediaBrowserCompatConnectionCallback = new MediaBrowserCompat.ConnectionCallback() {
        @Override
        public void onConnected() {
            super.onConnected();
            try {
                mMediaControllerCompat = new MediaControllerCompat(MediaPlayerActivity.this, mMediaBrowserCompat.getSessionToken());
                mMediaControllerCompat.registerCallback(mMediaControllerCompatCallback);
                try {
                    MediaControllerCompat.setMediaController(MediaPlayerActivity.this, mMediaControllerCompat);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Uri uri = Uri.parse(songUrl);
                MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().playFromUri(uri, null);

                Runnable notification = new Runnable() {
                    public void run() {
                        songDurationSet();
                        primarySeekBarProgressUpdater();
                    }
                };
                handler.postDelayed(notification, 2000);

            } catch (RemoteException e) {

            }
        }
    };

    private MediaControllerCompat.Callback mMediaControllerCompatCallback = new MediaControllerCompat.Callback() {

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
            if (state == null) {
                return;
            }
            switch (state.getState()) {
                case PlaybackStateCompat.STATE_PLAYING: {
                    mPlayPauseButton.setImageResource(R.drawable.pause_btn);
                    mCurrentState = STATE_PLAYING;
                    if (BackgroundAudioService.songChanges == 1) {
                        songDurationSet();
                    }
                    primarySeekBarProgressUpdater();
                    break;
                }
                case PlaybackStateCompat.STATE_PAUSED: {
                    mPlayPauseButton.setImageResource(R.drawable.play_btn);
                    mCurrentState = STATE_PAUSED;
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_player_activity);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
        try {

            singerImage = getIntent().getExtras().getString("imgUrl");
            lengthFlag = getIntent().getExtras().getInt("lengthFlag");
            SongPosition = getIntent().getExtras().getInt("Position");
            ArrayFlag = getIntent().getExtras().getInt("arrayflag");
            if (ArrayFlag == 1) {
                songArraylist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("NewSongsArr");
            } else if (ArrayFlag == 2) {
                songArraylist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("MostSongsArr");
            } else {
                songArraylist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("SingerSongsArr");
            }


            resultp = songArraylist.get(SongPosition);
            songName = resultp.get(DevotionalSongsActivity.SongTitle);
            singername = resultp.get(DevotionalSongsActivity.SingerName);
            albumImage = resultp.get(DevotionalSongsActivity.SongImgUrl);
            songId = resultp.get(DevotionalSongsActivity.SongID);
            arrCount = songArraylist.size();
            songUrl = resultp.get(DevotionalSongsActivity.Songurl);


            frwdBtn = (ImageButton) findViewById(R.id.fwrdBtn);
            songImage = (ImageButton) findViewById(R.id.SongImage);
            backBtn = (ImageView) findViewById(R.id.backImgBtn);
            backwrdBtn = (ImageButton) findViewById(R.id.backwrdBtn);
            currentLength = (TextView) findViewById(R.id.currentLength);
            fullLength = (TextView) findViewById(R.id.songLength);
            mPlayPauseButton = (ImageButton) findViewById(R.id.pausePlayBtn);
            SongTitle = (TextView) findViewById(R.id.SongTitle);
            SingerName = (TextView) findViewById(R.id.SingerName);
            SingerName = (TextView) findViewById(R.id.SingerName);
            HeadTitle = (TextView) findViewById(R.id.songsPage_header);
            seekBarProgress = (SeekBar) findViewById(R.id.SeekBarTestPlay);
            downloadSongBtn = findViewById(R.id.downloadSongBtn);
            percentageTv = (TextView) findViewById(R.id.percentageTv);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            percentageProgress = (ProgressBar) findViewById(R.id.progressBar_percentage);

            if (MiddlewareInterface.isMp3FileAvaliable(MiddlewareInterface.getMp3FilePath(songId + ".mp3"))) {
                songUrl = MiddlewareInterface.getMp3FilePath(songId + ".mp3");
                downloadSongBtn.setVisibility(View.GONE);
            }

            seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int totalDuration = BackgroundAudioService.mMediaPlayer.getDuration();
                    double val = ((double) seekBar.getProgress()) / ((double) seekBar.getMax());
                    int currentDuration = (int) (val * totalDuration);
                    MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().seekTo(currentDuration);
//                    BackgroundAudioService.mMediaPlayer.seekTo(currentDuration);
                    primarySeekBarProgressUpdater();

                }
            });

            downloadSongBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                        } else {
                            MiddlewareInterface.DownloadMp3File(songUrl, songId + ".mp3", getApplicationContext(), percentageTv, progressBar, percentageProgress, downloadSongBtn);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            try {
                if (MiddlewareInterface.bRendering) {
                    SongTitle.setText(songName);
                    SingerName.setText(singername);
                    HeadTitle.setText(getResources().getString(R.string.devotional_head));
                    SongTitle.setTypeface(Typeface.DEFAULT);
                    SingerName.setTypeface(Typeface.DEFAULT);
                    HeadTitle.setTypeface(Typeface.DEFAULT);
                } else {
                    SongTitle.setText(UnicodeUtil.unicode2tsc(songName));
                    SingerName.setText(UnicodeUtil.unicode2tsc(singername));
                    HeadTitle.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.devotional_head)));
                    SongTitle.setTypeface(MiddlewareInterface.tf_mylai);
                    SingerName.setTypeface(MiddlewareInterface.tf_mylai);
                    HeadTitle.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Glide.with(this)
                    .load(albumImage)
                    .into(songImage);

            BackgroundAudioService.backService = 0;
            mMediaBrowserCompat = new MediaBrowserCompat(this, new ComponentName(this, BackgroundAudioService.class), mMediaBrowserCompatConnectionCallback, savedInstanceState);
            mMediaBrowserCompat.connect();
            mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MediaPlayerActivity.this, mPlayPauseButton);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    if (mCurrentState == STATE_PLAYING) {
                                        try {

                                            mPlayPauseButton.setImageResource(R.drawable.play_btn);
                                            pauseFlag = 1;
                                            BackgroundAudioService.backService = 0;
                                            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().pause();
                                            mCurrentState = STATE_PAUSED;
                                            MiddlewareInterface.isAudioPlaying = false;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    } else if (mCurrentState == 0) {
                                        try {
                                            mPlayPauseButton.setImageResource(R.drawable.pause_btn);
                                            BackgroundAudioService.backService = 0;
                                            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().play();
                                            mCurrentState = STATE_PLAYING;
                                            MiddlewareInterface.isAudioPlaying = true;
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {
                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            backwrdBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MediaPlayerActivity.this, backwrdBtn);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                if (mProgress != null)
                                    mProgress.setVisibility(View.VISIBLE);

                                if (SongPosition == 0) {
                                    SongPosition = arrCount - 1;
                                    HashMap<String, String> currentArrPos = songArraylist.get(SongPosition);
                                    songName = currentArrPos.get(DevotionalSongsActivity.SongTitle);
                                    songUrl = currentArrPos.get(DevotionalSongsActivity.Songurl);
                                    singername = currentArrPos.get(DevotionalSongsActivity.SingerName);
                                    albumImage = currentArrPos.get(DevotionalSongsActivity.SongImgUrl);
                                    ContinuePlay();

                                } else {
                                    SongPosition = SongPosition - 1;
                                    HashMap<String, String> currentArrPos = songArraylist.get(SongPosition);
                                    songName = currentArrPos.get(DevotionalSongsActivity.SongTitle);
                                    songUrl = currentArrPos.get(DevotionalSongsActivity.Songurl);
                                    singername = currentArrPos.get(DevotionalSongsActivity.SingerName);
                                    albumImage = currentArrPos.get(DevotionalSongsActivity.SongImgUrl);
                                    ContinuePlay();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {
                        }
                    });

                }
            });

            HeadTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MediaPlayerActivity.this, HeadTitle);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                onBackPressed();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {
                        }
                    });
                }
            });

            frwdBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MediaPlayerActivity.this, frwdBtn);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                if (mProgress != null)
                                    mProgress.setVisibility(View.VISIBLE);

                                if (SongPosition == arrCount - 1) {
                                    SongPosition = arrCount - SongPosition - 1;
                                    HashMap<String, String> currentArrPos = songArraylist.get(SongPosition);
                                    songName = currentArrPos.get(DevotionalSongsActivity.SongTitle);
                                    songUrl = currentArrPos.get(DevotionalSongsActivity.Songurl);
                                    singername = currentArrPos.get(DevotionalSongsActivity.SingerName);
                                    albumImage = currentArrPos.get(DevotionalSongsActivity.SongImgUrl);
                                    ContinuePlay();

                                } else {
                                    SongPosition = SongPosition + 1;
                                    HashMap<String, String> currentArrPos = songArraylist.get(SongPosition);
                                    songName = currentArrPos.get(DevotionalSongsActivity.SongTitle);
                                    songUrl = currentArrPos.get(DevotionalSongsActivity.Songurl);
                                    singername = currentArrPos.get(DevotionalSongsActivity.SingerName);
                                    albumImage = currentArrPos.get(DevotionalSongsActivity.SongImgUrl);
                                    ContinuePlay();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {
                        }
                    });


                }
            });
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MediaPlayerActivity.this, backBtn);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                onBackPressed();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {
                        }
                    });

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            MiddlewareInterface.DownloadMp3File(songUrl, songId + ".mp3", getApplicationContext(), percentageTv, progressBar, percentageProgress, downloadSongBtn);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d("Test", "Back button pressed!");
            try {
                if (mCurrentState == STATE_PLAYING)
                    gotoSongSelectionPage(2);
                else
                    gotoSongSelectionPage(1);
                super.onBackPressed();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            mPlayPauseButton.setImageResource(R.drawable.play_btn);
            pauseFlag = 1;
            BackgroundAudioService.backService = 0;
            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().pause();
            mCurrentState = STATE_PAUSED;
            MiddlewareInterface.isAudioPlaying = false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void gotoSongSelectionPage(int flag) {
        try {
            Intent back = new Intent();
            setResult(flag, back);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ContinuePlay() {
        try {
            if (BackgroundAudioService.mMediaPlayer.isPlaying()) {
                BackgroundAudioService.mMediaPlayer.stop();
                try {
                    Uri uri = Uri.parse(songUrl);
                    MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().playFromUri(uri, null);
                    SongTitle.setText(songName);
                    SingerName.setText(singername);
                    try {
                        Glide.with(this)
                                .load(albumImage)
                                .into(songImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Runnable notification = new Runnable() {
                            public void run() {
                                songDurationSet();
                            }
                        };
                        handler.postDelayed(notification, 2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    primarySeekBarProgressUpdater();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Uri uri = Uri.parse(songUrl);
                    MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().playFromUri(uri, null);
                    SongTitle.setText(songName);
                    SingerName.setText(singername);
                    try {
                        Glide.with(this)
                                .load(albumImage)
                                .into(songImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Runnable notification = new Runnable() {
                            public void run() {
                                songDurationSet();
                            }
                        };
                        handler.postDelayed(notification, 2000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    primarySeekBarProgressUpdater();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mProgress.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void songDurationSet() {
        try {
            if (BackgroundAudioService.songChanges == 1) {
                SongTitle.setText(BackgroundAudioService.SongTitles);
                SingerName.setText(BackgroundAudioService.SingerTitle);
                try {
                    Glide.with(this)
                            .load(BackgroundAudioService.ImageUrl)
                            .into(songImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaFileLengthInMilliseconds = BackgroundAudioService.songDuration;
                String str = String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(mediaFileLengthInMilliseconds),
                        TimeUnit.MILLISECONDS.toSeconds(mediaFileLengthInMilliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaFileLengthInMilliseconds)));
                fullLength.setText(str);
                BackgroundAudioService.songChanges = 0;
            }
            mediaFileLengthInMilliseconds = BackgroundAudioService.songDuration;

            String str = String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(mediaFileLengthInMilliseconds),
                    TimeUnit.MILLISECONDS.toSeconds(mediaFileLengthInMilliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaFileLengthInMilliseconds)));
            fullLength.setText(str);
            flagDuration = mediaFileLengthInMilliseconds - 2000;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void primarySeekBarProgressUpdater() {
        try {
            if (lengthFlag == 0) {
                int CrntPosMillisec = 0;
                seekBarProgress.setProgress((int) (((float) 0 / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
                final String str1 = String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(CrntPosMillisec), TimeUnit.MILLISECONDS.toSeconds(CrntPosMillisec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(CrntPosMillisec)));
                currentLength.setText(str1);
            } else {
                currentPos = BackgroundAudioService.currentPos;
                seekBarProgress.setProgress((int) (((float) currentPos / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
                int CrntPosMillisec = currentPos;
                final String str1 = String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(CrntPosMillisec), TimeUnit.MILLISECONDS.toSeconds(CrntPosMillisec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(CrntPosMillisec)));
                currentLength.setText(str1);
            }
            if (lengthFlag != -1) {
                Runnable notification = new Runnable() {
                    public void run() {
                        primarySeekBarProgressUpdater();
                        lengthFlag = 1;
                    }
                };
                handler.postDelayed(notification, 1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        mPlayPauseButton.setImageResource(R.drawable.play_btn);
//        pauseFlag = 1;
//        BackgroundAudioService.backService = 0;
//        MediaControllerCompat.getMediaController(MediaPlayerActivity.this).getTransportControls().pause();
//        mCurrentState = STATE_PAUSED;
//        MiddlewareInterface.isAudioPlaying = false;
//    }
}
