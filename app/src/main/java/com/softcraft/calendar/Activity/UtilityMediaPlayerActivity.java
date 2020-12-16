package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.media.session.MediaControllerCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softcraft.calendar.ServiceAndOthers.BackgroundAudioService;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.softcraft.calendar.DevotionalWallpapers.DevotionalWallpepersPreviewActivity.REQUEST_WRITE_PERMISSION;

public class UtilityMediaPlayerActivity extends Activity implements View.OnClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {

    public ImageButton mPlayPauseButton;
    SeekBar seekBarProgress;
    ImageButton frwdBtn, backwrdBtn, songImage;
    ImageView backBtn, downloadSongBtn;
    TextView currentLength, fullLength, SongTitle, SingerName, HeadTitle;
    String songUrl, albumImage, singerImage, songId;
    public String songName, singername;
    int lengthFlag, SongPosition, arrCount;
    private MediaPlayer mediaPlayer;
    private ProgressBar mProgress;
    private int mediaFileLengthInMilliseconds;
    private final Handler handler = new Handler();
    private HashMap<String, String> resultp;
    public ArrayList<HashMap<String, String>> songArraylist;
    TextView percentageTv;
    ProgressBar progressBar;
    ProgressBar percentageProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.utility_mediaplayer_activity);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));

        UtilityMediaPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });
    }

    private void initView() {

        try {
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            mProgress = (ProgressBar) findViewById(R.id.progress_bar);
        } catch (Exception e) {
            e.printStackTrace();
        }
        singerImage = getIntent().getExtras().getString("imgUrl");
        lengthFlag = getIntent().getExtras().getInt("lengthFlag");
        SongPosition = getIntent().getExtras().getInt("Position");

        songArraylist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("NewSongsArr");

        resultp = songArraylist.get(SongPosition);
        songName = resultp.get(DevotionalSongsActivity.SongTitle);
        songUrl = resultp.get(DevotionalSongsActivity.Songurl);
        singername = resultp.get(DevotionalSongsActivity.SingerName);
        albumImage = resultp.get(DevotionalSongsActivity.SongImgUrl);
        songId = resultp.get(DevotionalSongsActivity.SongID);
        arrCount = songArraylist.size();

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

        if (MiddlewareInterface.isMp3FileAvaliable(MiddlewareInterface.getMp3FilePath(songName + ".mp3"))) {
            songUrl = MiddlewareInterface.getMp3FilePath(songName + ".mp3");
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
                int totalDuration = mediaPlayer.getDuration();
                double val = ((double) seekBar.getProgress()) / ((double) seekBar.getMax());
                int currentDuration = (int) (val * totalDuration);
                mediaPlayer.seekTo(currentDuration);
                primarySeekBarProgressUpdater();

            }
        });
        mPlayPauseButton.setOnClickListener(this);
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UtilityMediaPlayerActivity.this, mPlayPauseButton);
            zoomAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    try {
                        if (MiddlewareInterface.bRendering) {
                            SongTitle.setText(songName);
                            SingerName.setText(singername);
                            HeadTitle.setText(getResources().getString(R.string.utility_head));
                            SongTitle.setTypeface(Typeface.DEFAULT);
                            SingerName.setTypeface(Typeface.DEFAULT);
                            HeadTitle.setTypeface(Typeface.DEFAULT);
                        } else {
                            SongTitle.setText(UnicodeUtil.unicode2tsc(songName));
                            SingerName.setText(UnicodeUtil.unicode2tsc(singername));
                            HeadTitle.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.utility_head)));
                            SongTitle.setTypeface(MiddlewareInterface.tf_mylai);
                            SingerName.setTypeface(MiddlewareInterface.tf_mylai);
                            HeadTitle.setTypeface(MiddlewareInterface.tf_mylai);
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

        downloadSongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                    } else {
                        MiddlewareInterface.DownloadMp3File(songUrl, songName + ".mp3", getApplicationContext(), percentageTv, progressBar, percentageProgress, downloadSongBtn);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        try {
            Glide.with(this)
                    .load(albumImage)
                    .into(songImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MediaPlayer();
                try {
                    mediaPlayer.setDataSource(songUrl);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    songDurationSet();
                    primarySeekBarProgressUpdater();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        HeadTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UtilityMediaPlayerActivity.this, HeadTitle);
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
                Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UtilityMediaPlayerActivity.this, frwdBtn);
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
        backwrdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UtilityMediaPlayerActivity.this, backwrdBtn);
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UtilityMediaPlayerActivity.this, backBtn);
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            MiddlewareInterface.DownloadMp3File(songUrl, songName + ".mp3", getApplicationContext(), percentageTv, progressBar, percentageProgress, downloadSongBtn);
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mediaPlayer.isPlaying())
//            mediaPlayer.stop();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mediaPlayer.isPlaying())
//            mediaPlayer.stop();
//    }

    private void MediaPlayer() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ContinuePlay() {
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                MediaPlayer();
                try {
                    mediaPlayer.setDataSource(songUrl);
                    mediaPlayer.prepare();
                    SongTitle.setText(songName);
                    SingerName.setText(singername);
                    try {
                        Glide.with(this)
                                .load(albumImage)
                                .into(songImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    songDurationSet();
                    primarySeekBarProgressUpdater();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                MediaPlayer();
                try {
                    mediaPlayer.setDataSource(songUrl);
                    mediaPlayer.prepare();
                    SongTitle.setText(songName);
                    SingerName.setText(singername);
                    try {
                        Glide.with(this)
                                .load(albumImage)
                                .into(songImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    songDurationSet();
                    primarySeekBarProgressUpdater();
                    mediaPlayer.start();
                    songDurationSet();
                    primarySeekBarProgressUpdater();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            mProgress.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void primarySeekBarProgressUpdater() {
        try {
            int CrntPosMillisec = mediaPlayer.getCurrentPosition();
            seekBarProgress.setProgress((int) (((float) CrntPosMillisec / mediaFileLengthInMilliseconds) * 100)); // This math construction give a percentage of "was playing"/"song length"
            final String str1 = String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(CrntPosMillisec), TimeUnit.MILLISECONDS.toSeconds(CrntPosMillisec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(CrntPosMillisec)));
            currentLength.setText(str1);
            if (mediaPlayer.isPlaying()) {
                Runnable notification = new Runnable() {
                    public void run() {
                        primarySeekBarProgressUpdater();
                    }
                };
                handler.postDelayed(notification, 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void songDurationSet() {
        try {
            mediaFileLengthInMilliseconds = mediaPlayer.getDuration();
            String str = String.format("%d : %d ", TimeUnit.MILLISECONDS.toMinutes(mediaFileLengthInMilliseconds),
                    TimeUnit.MILLISECONDS.toSeconds(mediaFileLengthInMilliseconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mediaFileLengthInMilliseconds)));
            fullLength.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.pausePlayBtn) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    mPlayPauseButton.setImageResource(R.drawable.pause_btn);
                } else {
                    mediaPlayer.pause();
                    mPlayPauseButton.setImageResource(R.drawable.play_btn);
                }
                primarySeekBarProgressUpdater();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (SongPosition == arrCount - 1) {
            mPlayPauseButton.setImageResource(R.drawable.play_btn);
        } else {
            SongPosition = SongPosition + 1;
            HashMap<String, String> currentArrPos = songArraylist.get(SongPosition);
            songName = currentArrPos.get(DevotionalSongsActivity.SongTitle);
            songUrl = currentArrPos.get(DevotionalSongsActivity.Songurl);
            singername = currentArrPos.get(DevotionalSongsActivity.SingerName);
            albumImage = currentArrPos.get(DevotionalSongsActivity.SongImgUrl);
            ContinuePlay();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBarProgress.setSecondaryProgress(percent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



