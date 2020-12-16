package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import androidx.core.app.NotificationManagerCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import androidx.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;
import com.softcraft.calendar.Adapter.SingersCategoryActivityAdapter;
import com.softcraft.calendar.ServiceAndOthers.BackgroundAudioService;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.ServiceAndOthers.NotificationDismissedReceiver;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class SingersCategorySongsActivity extends AppCompatActivity {
    private ListView listView;
    private SingersCategoryActivityAdapter listAdapter;
    private ArrayList<HashMap<String, String>> SingerSongArraylist;
    private HashMap<String, String> resultp;
    private int SingerIdInt;
    LinearLayout linearad;
    AdView adview;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    Context context;
    static int exceptionValue;
    static String strRet = "";
    public static String PACKAGE_NAME, version, deviceId, deviceModel;
    private AdBannerListener adBannerListener;
    private NativeExpressAdView viewNativeAd;

    public static RelativeLayout playPauseFab;
    public static ImageView playImg;
    private static String postApi = "http://adsenseusers.com/scsadcontrol/api/device4/updatesonglisten/format/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singers_album_layout);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
        try {
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String singerImageUrl = getIntent().getExtras().getString("imgUrl");
            String strSingername = getIntent().getExtras().getString("singerName");
            String strSingerId = getIntent().getExtras().getString("keySingerId");

            try {
                PACKAGE_NAME = getApplicationContext().getPackageName();
                version = Build.VERSION.RELEASE;
                deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                deviceModel = Build.MODEL;
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            playPauseFab = (RelativeLayout) findViewById(R.id.fabPlayPause);
            playImg = (ImageView) findViewById(R.id.playImg);
            playPauseFab.setVisibility(View.GONE);
            try{
                if(BackgroundAudioService.mMediaPlayer !=null){
                    if(BackgroundAudioService.mMediaPlayer.isPlaying()){
                        playPauseFab.setVisibility(View.VISIBLE);
                        playImg.setImageResource(R.drawable.ic_paus);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            SingerSongArraylist = new ArrayList<>();

            ArrayList<HashMap<String, String>> singerLists = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("singers");
            HashMap<String, String> resultp;
            for (int i = 0; i < singerLists.size(); i++) {
                resultp = singerLists.get(i);
                String keySingerId = resultp.get(DevotionalSongsActivity.SingerId);
                if (strSingerId.equalsIgnoreCase(keySingerId)) {

                    String SongImgUrl = resultp.get(DevotionalSongsActivity.SongImgUrl);
                    String SongTitle = resultp.get(DevotionalSongsActivity.SongTitle);
                    String SongUrl = resultp.get(DevotionalSongsActivity.Songurl);
                    String SingerName = resultp.get(DevotionalSongsActivity.SingerName);
                    String SongId = resultp.get(DevotionalSongsActivity.SongID);
                    String SingerId = resultp.get(DevotionalSongsActivity.SingerId);
                    String SongSize = resultp.get(DevotionalSongsActivity.SongSize);
                    String SongDuration = resultp.get(DevotionalSongsActivity.SongDuration);


                    Map<String, String> updateListenSongs = new HashMap<String, String>();
                    updateListenSongs.put("songid", SongId);
                    updateListenSongs.put("devicetype", deviceModel);
                    updateListenSongs.put("deviceid", deviceId);
                    updateListenSongs.put("deviceos", "Android");
                    updateListenSongs.put("osversion", version);
                    try {
                        post(postApi, updateListenSongs);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    HashMap<String, String> SingerAlbums = new HashMap<>();

                    SingerAlbums.put("id", SongId);
                    SingerAlbums.put("songtitle", SongTitle);
                    SingerAlbums.put("singerid", SingerId);
                    SingerAlbums.put("singername", SingerName);
                    SingerAlbums.put("thumbimage", SongImgUrl);
                    SingerAlbums.put("songfile", SongUrl);
                    SingerAlbums.put("songfilesize", SongSize);
                    SingerAlbums.put("songfilelength", SongDuration);

                    SingerSongArraylist.add(SingerAlbums);

                }
            }



            SingerIdInt = Integer.parseInt(strSingerId);
            int totalSongsList = SingerSongArraylist.size();
            String strTotalSongs = String.valueOf(totalSongsList);
            String strSongs = getResources().getString(R.string.songText);

            ImageView singerImage = (ImageView) findViewById(R.id.SingerImage);
            TextView singerName = (TextView) findViewById(R.id.HeadingSingerName);
            final ImageView backImg = (ImageView) findViewById(R.id.news_imgback_click1);
            TextView totalSongs = (TextView) findViewById(R.id.totalSongs);
            TextView songsTv = (TextView) findViewById(R.id.Songs);
            final TextView HeadTitle = (TextView) findViewById(R.id.songsPage_header);
            linearad = (LinearLayout) findViewById(R.id.notication_adview);
            totalSongs.setText(strTotalSongs);
            try {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
                HeadTitle.startAnimation(animation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HeadTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.anim.zoomin_out_animation);
                    zoomAnimation.setTarget(HeadTitle);
                    zoomAnimation.start();
                    onBackPressed();
                }
            });

            try {
                if (MiddlewareInterface.bRendering)
                {
                    singerName.setText(strSingername);
                    songsTv.setText(strSongs);
                    HeadTitle.setText(getResources().getString(R.string.devotional_head));
                    singerName.setTypeface(Typeface.DEFAULT);
                    songsTv.setTypeface(Typeface.DEFAULT);
                    HeadTitle.setTypeface(Typeface.DEFAULT);
                }
                else
                {
                    singerName.setText(UnicodeUtil.unicode2tsc(strSingername));
                    songsTv.setText(UnicodeUtil.unicode2tsc(strSongs));
                    HeadTitle.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.devotional_head)));
                    singerName.setTypeface(MiddlewareInterface.tf_mylai);
                    songsTv.setTypeface(MiddlewareInterface.tf_mylai);
                    HeadTitle.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Glide.with(this)
                    .load(singerImageUrl)
                    .into(singerImage);

            backImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(SingersCategorySongsActivity.this, backImg);
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

            listViewActions();
            fabOnclickAction();


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                try {
                    if (getEnable.equalsIgnoreCase("1")) {
                        if (getBannerType.equalsIgnoreCase("0")) {
                            setAdvertise();
                        } else {
                            setNativeSmall();
                        }
                    }else if (getEnable.equalsIgnoreCase( "4" )) {
                        if (getFacebookBannerType.equalsIgnoreCase( "1" )) {
                            MiddlewareInterface.setNativeFBAD(this,linearad);
                        } else {
                            MiddlewareInterface.setBannerFBAD(this,linearad);
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

    private void fabOnclickAction(){
        try{
            playPauseFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(SingersCategorySongsActivity.this, playPauseFab);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }
                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    if (BackgroundAudioService.mMediaPlayer.isPlaying()) {
                                        BackgroundAudioService.mMediaPlayer.pause();
                                        BackgroundAudioService backgroundAudioService=new BackgroundAudioService();
                                        backgroundAudioService.cancelNotify(SingersCategorySongsActivity.this);
                                        playImg.setImageResource(R.drawable.ic_ply);
//                            playPauseFab.setVisibility(View.GONE);
                                        showPausedNotification();
                                    }
                                    else{
                                        BackgroundAudioService.mMediaPlayer.start();
                                        playImg.setImageResource(R.drawable.ic_paus);
                                        showPlayingNotification();
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

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch(Exception e){
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
            MediaControllerCompat controller = BackgroundAudioService.mMediaSessionCompat.getController();
            MediaMetadataCompat mediaMetadata = controller.getMetadata();
            MediaDescriptionCompat description = mediaMetadata.getDescription();
            if (builder == null) {
                return;
            }
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", MediaButtonReceiver.buildMediaButtonPendingIntent(SingersCategorySongsActivity.this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));
            builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0).setMediaSession(BackgroundAudioService.mMediaSessionCompat.getSessionToken()));
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setLargeIcon(description.getIconBitmap());
            builder.setContentTitle(MediaPlayerActivity.songName);
            builder.setContentText(MediaPlayerActivity.singername);
            builder.setAutoCancel(false);
            builder.setOngoing(true);

            NotificationManagerCompat.from(SingersCategorySongsActivity.this).notify(1, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showPausedNotification() {
        try {
            MediaControllerCompat controller = BackgroundAudioService.mMediaSessionCompat.getController();
            MediaMetadataCompat mediaMetadata = controller.getMetadata();
            MediaDescriptionCompat description = mediaMetadata.getDescription();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            if (builder == null) {
                return;
            }
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", MediaButtonReceiver.buildMediaButtonPendingIntent(SingersCategorySongsActivity.this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));
            builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0).setMediaSession(BackgroundAudioService.mMediaSessionCompat.getSessionToken()));
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setLargeIcon(description.getIconBitmap());
            builder.setContentTitle(MediaPlayerActivity.songName);
            builder.setContentText(MediaPlayerActivity.singername);
            builder.setDeleteIntent(createOnDismissedIntent(SingersCategorySongsActivity.this, 1));
            NotificationManagerCompat.from(SingersCategorySongsActivity.this).notify(1, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (resultCode == 2) {
            playPauseFab.setVisibility(View.VISIBLE);
            playPauseFab.setImageResource(R.drawable.ic_paus);

        } else {
            playPauseFab.setVisibility(View.GONE);
        }*/
        try {
            if (BackgroundAudioService.mMediaPlayer.isPlaying()) {
                playPauseFab.setVisibility(View.VISIBLE);
                playImg.setImageResource(R.drawable.ic_paus);
            } else {
                playPauseFab.setVisibility(View.GONE);
                playImg.setImageResource(R.drawable.ic_ply);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void listViewActions() {
        try {
            listView = (ListView) findViewById(R.id.singerSongsInfo);
            try {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.list_item_anim);
                listView.setLayoutAnimation(new LayoutAnimationController(animation));
            }catch (Exception e){
                e.printStackTrace();
            }
            listAdapter = new SingersCategoryActivityAdapter(getApplicationContext(), SingerSongArraylist);
            listView.setAdapter(listAdapter);
            resultp = new HashMap<String, String>();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {

                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(getApplicationContext(), view);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }
                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                try {
                                    if (BackgroundAudioService.mMediaPlayer != null) {
                                        BackgroundAudioService.mMediaPlayer.reset();
                                        BackgroundAudioService.mMediaPlayer.release();
                                    }
                                } catch (IllegalStateException e) {
                                    e.printStackTrace();
                                    BackgroundAudioService.mMediaPlayer=null;
                                }
                                BackgroundAudioService.currentPos=0;
                                resultp = SingerSongArraylist.get(position);
                                String keySongName = resultp.get(DevotionalSongsActivity.SongTitle);
                                String keySongUrl = resultp.get(DevotionalSongsActivity.Songurl);
                                String keySingerName = resultp.get(DevotionalSongsActivity.SingerName);
                                String keyImgUrl = resultp.get(DevotionalSongsActivity.SongImgUrl);
                                String songId = resultp.get(DevotionalSongsActivity.SongID);

                                try{
                                    if (keySongUrl.equals("")) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Songs Not Avaliable", Toast.LENGTH_LONG);
                                        toast.show();
                                    }else{
                                        Bundle myInfo = new Bundle();
                                        myInfo.putInt("Position", position);
                                        myInfo.putInt("arrayflag", 3);
//                            myInfo.putString("songName", keySongName);
//                            myInfo.putString("image", keyImgUrl);
//                            myInfo.putString("songUrl", keySongUrl);
//                            myInfo.putString("singerName", keySingerName);
//                            myInfo.putInt("SingerAct", 1);
                                        checkInternetConnection(myInfo, songId);
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInternetConnection(Bundle myInfo, String songId) {
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    gotoPlayerPage(myInfo);
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    gotoPlayerPage(myInfo);
                }
            } else {
                if (!MiddlewareInterface.isMp3FileAvaliable(MiddlewareInterface.getMp3FilePath(songId + ".mp3"))) {
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

    private void gotoPlayerPage(Bundle bundle){
        try{
            Intent intent = new Intent(getApplicationContext(), MediaPlayerActivity.class);
            intent.putExtra("SingerSongsArr", SingerSongArraylist);
            startActivityForResult(intent.putExtras(bundle),1);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void post(String endpoint, Map<String, String> params) throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=').append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v("ServerUtillis", "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                strRet = response.toString();
            }
            // JSONObject jsonObject =new JSONObject(response.toString());
            exceptionValue = status;
            Log.d("statusss", status + "");
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private class AdBannerListener implements IMBannerListener {
        @Override
        public void onBannerRequestFailed(IMBanner imBanner, IMErrorCode imErrorCode) {
            linearad.setVisibility(View.GONE);
        }

        @Override
        public void onBannerRequestSucceeded(IMBanner imBanner) {
            linearad.setVisibility(View.VISIBLE);
        }

        @Override
        public void onBannerInteraction(IMBanner imBanner, Map<String, String> map) {
            linearad.setVisibility(View.GONE);
        }

        @Override
        public void onShowBannerScreen(IMBanner imBanner) {
            linearad.setVisibility(View.VISIBLE);
        }

        @Override
        public void onDismissBannerScreen(IMBanner imBanner) {
            linearad.setVisibility(View.GONE);
        }

        @Override
        public void onLeaveApplication(IMBanner imBanner) {
            linearad.setVisibility(View.GONE);
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

    private void gotoSongSelectionPage(int flag) {
        try {
            Intent back = new Intent();
            setResult(flag, back);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        try {
            if (playPauseFab.getVisibility() == View.VISIBLE)
                gotoSongSelectionPage(2);
            else
                gotoSongSelectionPage(3);
            super.onBackPressed();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
