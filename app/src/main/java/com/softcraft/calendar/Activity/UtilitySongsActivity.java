package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.inmobi.monetization.IMBanner;
import com.softcraft.calendar.ServiceAndOthers.BackgroundAudioService;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.Adapter.UtilitySongAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class UtilitySongsActivity extends Activity implements View.OnClickListener, View.OnTouchListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener {

    ListView listView;
    UtilitySongAdapter adapter;
    ImageView backIcon;
    AdView adview;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    private NativeExpressAdView viewNativeAd;
    HashMap<String, String> resultp = new HashMap<>();
    int lastPosition = -1;
    Context context;
    RelativeLayout playPauseFab;
    ImageView playImg;
    MediaPlayer mediaPlayer;
    LinearLayout linearad;
    TextView HeadTitle;
    ArrayList<HashMap<String, String>> utilitySongArraylist;
    public static String UtlitySongName = "songtitle";
    public static String UtlitySongImage = "thumbimage";
    public static String UtlitySongUrl = "songfile";
    public static String UtlitySongId = "id";
    View circlePG;
    RelativeLayout rootLayout;
    ProgressBar progressBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utility_songs);
        try {

            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                linearad = (LinearLayout) findViewById(R.id.notication_adview);
                circlePG = findViewById(R.id.circlePG_news);
                playPauseFab = (RelativeLayout) findViewById(R.id.fabPlayPause);
                playImg = (ImageView) findViewById(R.id.playImg);
                backIcon = (ImageView) findViewById(R.id.news_imgback_click);
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnBufferingUpdateListener(this);
                mediaPlayer.setOnCompletionListener(this);
                utilitySongArraylist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("UtilityArray");
                playPauseFab.setOnClickListener(this);
                HeadTitle = (TextView) findViewById(R.id.title_head);
                adapter = new UtilitySongAdapter(getApplicationContext(), utilitySongArraylist);
                listView = (ListView) findViewById(R.id.listviewUtility);
                rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
                progressBar = (ProgressBar) findViewById(R.id.progress_bar);

                try {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
                    HeadTitle.startAnimation(animation);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Animation animation = AnimationUtils.loadAnimation(this, R.anim.list_item_anim);
                    listView.setLayoutAnimation(new LayoutAnimationController(animation));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                listView.setAdapter(adapter);

                playPauseFab.setVisibility(View.GONE);

                try {
                    if (MiddlewareInterface.bRendering) {
                        HeadTitle.setText(getResources().getString(R.string.utility_head));
                        HeadTitle.setTypeface(Typeface.DEFAULT);
                    } else {
                        HeadTitle.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.utility_head)));
                        HeadTitle.setTypeface(MiddlewareInterface.tf_mylai);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                try {
                    if (getEnable.equalsIgnoreCase("1")) {
                        if (getBannerType.equalsIgnoreCase("0")) {
                            setAdvertise();
                        } else {
                            setNativeSmall();
                        }
                    } else if (getEnable.equalsIgnoreCase("4")) {
                        if (getFacebookBannerType.equalsIgnoreCase("1")) {
                            MiddlewareInterface.setNativeFBAD(this, linearad);
                        } else {
                            MiddlewareInterface.setBannerFBAD(this, linearad);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UtilitySongsActivity.this, view);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                circlePG.setVisibility(View.VISIBLE);
                                checkConnectivity(position);
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

            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UtilitySongsActivity.this, backIcon);
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
            HeadTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UtilitySongsActivity.this, HeadTitle);
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
            ShareFunc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkConnectivity(int position) {
        try {
//            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
//            if (activeNetwork != null) { // connected to the internet
//                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
            listviewOnclick(position);
//                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                    listviewOnclick(position);
//                }
//            } else {
//                if (!MiddlewareInterface.isMp3FileAvaliable(MiddlewareInterface.getMp3FilePath(songId + ".mp3"))) {
//                    Toast toast = Toast.makeText(getContext(), "Song Not Avaliable for offline", Toast.LENGTH_LONG);
//                    toast.show();
//                } else {
//                    gotoPlayerPage(myInfo);
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void listviewOnclick(int position) {
        try {

            try {
                if (BackgroundAudioService.mMediaPlayer != null) {
                    if (BackgroundAudioService.mMediaPlayer.isPlaying()) {
                        BackgroundAudioService.mMediaPlayer.pause();
                        try {
                            BackgroundAudioService backgroundAudioService = new BackgroundAudioService();
                            backgroundAudioService.cancelNotify(UtilitySongsActivity.this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            resultp = utilitySongArraylist.get(position);
            String keySongName = resultp.get(UtilitySongsActivity.UtlitySongName);
            String keySongUrl = resultp.get(UtilitySongsActivity.UtlitySongUrl);
            String keySingerName = resultp.get(SplashScreen.utilSingerName);
            String keyImgUrl = resultp.get(UtilitySongsActivity.UtlitySongImage);
            String songId = resultp.get(UtilitySongsActivity.UtlitySongId);

            try {
                if (keySongUrl.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Songs Not Avaliable", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Bundle myInfo = new Bundle();
//                    myInfo.putString("songName", keySongName);
//                    myInfo.putString("image", keyImgUrl);
//                    myInfo.putString("songUrl", keySongUrl);
//                    myInfo.putString("singerName", keySingerName);
                    myInfo.putInt("Position", position);
                    checkInternetConnection(myInfo, keySongName);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInternetConnection(Bundle myInfo, String keySongName) {
        try {
            circlePG.setVisibility(View.GONE);
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                gotoPlayerPage(myInfo);
            } else {
                if (!MiddlewareInterface.isMp3FileAvaliable(MiddlewareInterface.getMp3FilePath(keySongName + ".mp3"))) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Song Not Avaliable for offline", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    gotoPlayerPage(myInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoPlayerPage(Bundle bundle) {
        try {
            Intent intent = new Intent(UtilitySongsActivity.this, UtilityMediaPlayerActivity.class);
            intent.putExtra("NewSongsArr", utilitySongArraylist);
            startActivity(intent.putExtras(bundle));
            circlePG.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        try {
            playImg.setImageResource(R.drawable.ic_ply);
            playPauseFab.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == R.id.fabPlayPause) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    playImg.setImageResource(R.drawable.ic_paus);
                } else {
                    mediaPlayer.pause();
                    playImg.setImageResource(R.drawable.ic_ply);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    private void setNativeSmall() {
        try {
            try {
                String strId = getNativeSmall;
                if (MiddlewareInterface.bAdFree)
                    return;
                viewNativeAd = new NativeExpressAdView(this);
                viewNativeAd.setAdSize(AdSize.LARGE_BANNER);
                viewNativeAd.setAdUnitId(strId);
                viewNativeAd.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearad.addView(viewNativeAd);
                AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
                viewNativeAd.loadAd(adRequestBuilder.build());
            } catch (Exception e) {
                // TODO: handle exception
            }

            viewNativeAd.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // TODO Auto-generated method stub
                    Log.d("On Fail called", "Ad");
                    linearad.setVisibility(View.GONE);
                    super.onAdFailedToLoad(errorCode);
                }

                @Override
                public void onAdLoaded() {
                    // TODO Auto-generated method stub
                    Log.d("On Load called", "Ad");
                    linearad.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void setAdvertise() {
        try {
            try {
                String strGoogleAd = getBannerAd;
                if (MiddlewareInterface.bAdFree)
                    return;

                adview = new AdView(this);
                adview.setAdSize(AdSize.BANNER);
                adview.setAdUnitId(strGoogleAd);
                adview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                linearad.addView(adview);
                adview.loadAd(new AdRequest.Builder().build());
                //linear_ad
            } catch (Exception e) {
                // TODO: handle exception
            }
            adview.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // TODO Auto-generated method stub
                    Log.d("On Fail called", "Ad");
                    linearad.setVisibility(View.GONE);
                    super.onAdFailedToLoad(errorCode);
                }

                @Override
                public void onAdLoaded() {
                    // TODO Auto-generated method stub
                    Log.d("On Load called", "Ad");
                    linearad.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                    //RefreshAds();
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                playPauseFab.setVisibility(View.GONE);
                lastPosition = -1;
            } else if (playPauseFab != null && playPauseFab.getVisibility() == View.VISIBLE) {
                playPauseFab.setVisibility(View.GONE);
                mediaPlayer.reset();
                lastPosition = -1;
            } else {
                try {
                    if (MiddlewareInterface.interstitialAds != null) {
                        if (MiddlewareInterface.interstitialAds.isLoaded()) {
                            if (!MiddlewareInterface.isBackgroundRunning(getApplicationContext()))
                                MiddlewareInterface.interstitialAds.show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ShareFunc() {
        try {
            ImageView ShareImg = (ImageView) findViewById(R.id.shareImg);
            ShareImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(getApplicationContext(), view);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    TakeScreenShot(UtilitySongsActivity.this, rootLayout, 0, 20, "0", "மங்கள ஒலிகள்", progressBar);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
