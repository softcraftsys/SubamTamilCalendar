package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import androidx.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.NotificationCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.BackgroundAudioService;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.ServiceAndOthers.HttpHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.Fragment.MostViewedSongsFragment;
import com.softcraft.calendar.Fragment.NewSongsFragment;
import com.softcraft.calendar.ServiceAndOthers.NotificationDismissedReceiver;
import com.softcraft.calendar.Fragment.SingersCategoryFragment;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.ServiceAndOthers.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;


public class DevotionalSongsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TextView HeadTitle;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView backBTn, coverImge;
    public ArrayList<HashMap<String, String>> singerArraylist;
    public ArrayList<HashMap<String, String>> songArraylist;
    public ArrayList<HashMap<String, String>> categoryArraylist;
    HashMap<String, ArrayList<ArrayList<String>>> popularList;
    HashMap<String, ArrayList<ArrayList<String>>> popularList1;
    private ProgressDialog mProgressDialog;
    LinearLayout linearad;
    AdView adview;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    private NativeExpressAdView viewNativeAd;
    private ArrayList<HashMap<String, String>> data;
    public static String CoverImageUrl;
    public static String SongTitle = "songtitle";
    public static String SongSize = "songfilesize";
    public static String SongImgUrl = "thumbimage";
    public static String SongID = "id";
    public static String SingerId = "singerid";
    public static String SingerIdDetail = "id";
    public static String SingerName = "singername";
    public static String SingerNameDetail = "name";
    public static String Songurl = "songfile";
    public static String CategoryId = "categoryid";
    public static String SingerImg = "thumbimage";
    public static String SongListenCoun = "listencount";
    public static String SongDuration = "songfilelength";
    private static String url = "http://adsenseusers.com/scsadcontrol/api/device4/songsdata/format/json";
    ArrayList<String> popularSongCountList;
    public static RelativeLayout playPauseFab;
    public static ImageView playImg;
    Boolean sameCount = true;
    public int sortAll;
    ArrayList<String> songIds;
    ArrayList<ArrayList<String>> allSongsDataList1 = new ArrayList<>();
    RelativeLayout rootLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devotional_songs_layout);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
        try {
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            tabLayout = (TabLayout) findViewById(R.id.tabs);
            backBTn = (ImageView) findViewById(R.id.news_imgback_click);
            coverImge = (ImageView) findViewById(R.id.WelcomeImage);
            HeadTitle = (TextView) findViewById(R.id.songsPage_header);
            linearad = (LinearLayout) findViewById(R.id.notication_adview);
            playPauseFab = (RelativeLayout) findViewById(R.id.fabPlayPause);
            playImg = (ImageView) findViewById(R.id.playImg);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);

            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
            HeadTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DevotionalSongsActivity.this, HeadTitle);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
//                                finish();
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
            try {
                viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
                HeadTitle.startAnimation(animation);
            } catch (Exception e) {
                e.printStackTrace();
            }


            playPauseFab.setVisibility(View.GONE);
            try {
                if (BackgroundAudioService.mMediaPlayer != null) {
                    if (BackgroundAudioService.mMediaPlayer.isPlaying()) {
                        playPauseFab.setVisibility(View.VISIBLE);
                        playImg.setImageResource(R.drawable.ic_paus);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            fabOnclickAction(savedInstanceState);

            try {
                if (MiddlewareInterface.bRendering) {
                    HeadTitle.setText(getResources().getString(R.string.devotional_head));
                    HeadTitle.setTypeface(Typeface.DEFAULT);
                } else {
                    HeadTitle.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.devotional_head)));
                    HeadTitle.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            new GetJSONData().execute();
            backBTn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DevotionalSongsActivity.this, backBTn);
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
            ShareFunc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (BackgroundAudioService.mMediaPlayer != null) {
//            BackgroundAudioService.mMediaPlayer.pause();
//            BackgroundAudioService backgroundAudioService = new BackgroundAudioService();
//            backgroundAudioService.cancelNotify(DevotionalSongsActivity.this);
//            playImg.setImageResource(R.drawable.ic_ply);
////                            playPauseFab.setVisibility(View.GONE);
//            showPausedNotification();
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (BackgroundAudioService.mMediaPlayer != null) {
//            BackgroundAudioService.mMediaPlayer.pause();
//            BackgroundAudioService backgroundAudioService = new BackgroundAudioService();
//            backgroundAudioService.cancelNotify(DevotionalSongsActivity.this);
//            playImg.setImageResource(R.drawable.ic_ply);
////                            playPauseFab.setVisibility(View.GONE);
//            showPausedNotification();
//        }
//    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            if (MiddlewareInterface.interstitialAds != null) {
                if (MiddlewareInterface.interstitialAds.isLoaded()) {
                    if (!MiddlewareInterface.isBackgroundRunning(getApplicationContext()))
                        MiddlewareInterface.interstitialAds.show();
                }
            }
            if (BackgroundAudioService.mMediaPlayer != null && BackgroundAudioService.mMediaPlayer.isPlaying()) {
                BackgroundAudioService.mMediaPlayer.pause();
                BackgroundAudioService backgroundAudioService = new BackgroundAudioService();
                backgroundAudioService.cancelNotify(DevotionalSongsActivity.this);
                playImg.setImageResource(R.drawable.ic_ply);
//                            playPauseFab.setVisibility(View.GONE);
                showPausedNotification();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fabOnclickAction(final Bundle bundle) {
        try {
            playPauseFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DevotionalSongsActivity.this, playPauseFab);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    if (BackgroundAudioService.mMediaPlayer.isPlaying()) {
                                        BackgroundAudioService.mMediaPlayer.pause();
                                        BackgroundAudioService backgroundAudioService = new BackgroundAudioService();
                                        backgroundAudioService.cancelNotify(DevotionalSongsActivity.this);
                                        playImg.setImageResource(R.drawable.ic_ply);
//                            playPauseFab.setVisibility(View.GONE);
                                        showPausedNotification();
                                    } else {
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
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
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_pause, "Pause", MediaButtonReceiver.buildMediaButtonPendingIntent(DevotionalSongsActivity.this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));
            builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0).setMediaSession(BackgroundAudioService.mMediaSessionCompat.getSessionToken()));
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setLargeIcon(description.getIconBitmap());
            builder.setContentTitle(MediaPlayerActivity.songName);
            builder.setContentText(MediaPlayerActivity.singername);
            builder.setAutoCancel(false);
            builder.setOngoing(true);

            NotificationManagerCompat.from(DevotionalSongsActivity.this).notify(1, builder.build());
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
            builder.addAction(new NotificationCompat.Action(android.R.drawable.ic_media_play, "Play", MediaButtonReceiver.buildMediaButtonPendingIntent(DevotionalSongsActivity.this, PlaybackStateCompat.ACTION_PLAY_PAUSE)));
            builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0).setMediaSession(BackgroundAudioService.mMediaSessionCompat.getSessionToken()));
            builder.setSmallIcon(R.drawable.ic_launcher);
            builder.setLargeIcon(description.getIconBitmap());
            builder.setContentTitle(MediaPlayerActivity.songName);
            builder.setContentText(MediaPlayerActivity.singername);
            builder.setDeleteIntent(createOnDismissedIntent(DevotionalSongsActivity.this, 1));
            NotificationManagerCompat.from(DevotionalSongsActivity.this).notify(1, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager() {
        try {
            String newSongsTab = getResources().getString(R.string.newSongsTabTxt);
            String mostViewedSongsTab = getResources().getString(R.string.famousSongsTabText);
            String singersalbumTab = getResources().getString(R.string.singersSongTabText);

            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new NewSongsFragment(), newSongsTab);
            adapter.addFragment(new MostViewedSongsFragment(), mostViewedSongsTab);
            adapter.addFragment(new SingersCategoryFragment(), singersalbumTab);
            viewPager.setAdapter(adapter);

            tabLayout.setupWithViewPager(viewPager);

        } catch (IllegalStateException e) {
            setupViewPager();
            e.printStackTrace();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == 3) {

            } else {

                if (BackgroundAudioService.mMediaPlayer.isPlaying()) {
                    playPauseFab.setVisibility(View.VISIBLE);
                    playImg.setImageResource(R.drawable.ic_paus);
                } else {
                    playPauseFab.setVisibility(View.GONE);
                    playImg.setImageResource(R.drawable.ic_ply);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class GetJSONData extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                // Create a progressdialog
                mProgressDialog = new ProgressDialog(DevotionalSongsActivity.this);
                // Set progressdialog title
                mProgressDialog.setTitle("Please wait");
                // Set progressdialog message
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                // Show progressdialog
                mProgressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(DevotionalSongsActivity.this);
            HttpHandler sh = new HttpHandler();


            popularList = new HashMap<>();
            popularList1 = new HashMap<>();

            songArraylist = new ArrayList<>();
            songIds = new ArrayList<>();
            singerArraylist = new ArrayList<>();
            categoryArraylist = new ArrayList<>();

            String jsonStr = app_preferences.getString("devotionalSongsResponse", "");
            if (jsonStr.equalsIgnoreCase("")) {
                jsonStr = sh.makeServiceCall(url);
            }

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray categoryDetails = jsonObj.getJSONArray("categorydata");

                    // looping through All songDetails
                    for (int i = 0; i < categoryDetails.length(); i++) {
                        JSONObject jsonObject = categoryDetails.getJSONObject(i);
                        try {

                            String id = jsonObject.getString("id");
                            String categoryName = jsonObject.getString("name");
                            String thumbImgUrl = jsonObject.getString("thumbimage");

                            CoverImageUrl = thumbImgUrl;

                            HashMap<String, String> categoryDetail = new HashMap<>();

                            categoryDetail.put("id", id);
                            categoryDetail.put("name", categoryName);
                            categoryDetail.put("thumbimage", thumbImgUrl);

                            categoryArraylist.add(categoryDetail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    // Getting JSON Array node
                    popularSongCountList = new ArrayList<>();
                    JSONArray songsDetails = jsonObj.getJSONArray("songsdata");

                    // looping through All songDetails
                    for (int i = 0; i < songsDetails.length(); i++) {
                        JSONObject jsonObject = songsDetails.getJSONObject(i);
                        try {

                            ArrayList<String> songsList = new ArrayList<>();

                            ArrayList<ArrayList<String>> allSongsDataList = new ArrayList<>();

                            String id = jsonObject.getString("id");
                            String categoryId = jsonObject.getString("categoryid");
                            String songName = jsonObject.getString("songtitle");
                            String singerId = jsonObject.getString("singerid");
                            String singerName = jsonObject.getString("singername");
                            String thumbImgUrl = jsonObject.getString("thumbimage");
                            String songUrl = jsonObject.getString("songfile");
                            String songSize = jsonObject.getString("songfilesize");
                            String songListenCount = jsonObject.getString("listencount");
                            String songDuration = jsonObject.getString("songfilelength");
                            String sngId = jsonObject.getString("id");

                         /*   if (!popularSongCountList.contains(songListenCount))
                                popularSongCountList.add(Integer.valueOf(songListenCount));
                            else
                                popularSongCountList.add(Integer.valueOf(songListenCount));*/


                            if (!popularSongCountList.contains(songListenCount)) {
                                popularSongCountList.add(songListenCount);
                                songsList.add(songName);
                                songsList.add(singerName);
                                songsList.add(thumbImgUrl);
                                songsList.add(songSize);
                                songsList.add(songUrl);
                                songsList.add(songDuration);
                                songsList.add(sngId);
                                allSongsDataList.add(songsList);
                                songIds.add(sngId);
                                popularList.put(songListenCount, allSongsDataList);
                                allSongsDataList1 = allSongsDataList;
                                popularList1.put(sngId, allSongsDataList1);
                            } else {

                                if (sameCount) {
                                    songsList.add(songName);
                                    songsList.add(singerName);
                                    songsList.add(thumbImgUrl);
                                    songsList.add(songSize);
                                    songsList.add(songUrl);
                                    songsList.add(songDuration);
                                    songsList.add(sngId);
                                    allSongsDataList.add(songsList);
                                    popularSongCountList.add(songListenCount + "~");
                                    songListenCount = songListenCount + "~";
                                    popularList.put(songListenCount, allSongsDataList);
                                    songIds.add(sngId);
                                    sameCount = false;
                                    allSongsDataList1 = allSongsDataList;
                                    popularList1.put(sngId, allSongsDataList1);
                                } else if (sameCount == false) {
                                    songsList.add(songName);
                                    songsList.add(singerName);
                                    songsList.add(thumbImgUrl);
                                    songsList.add(songSize);
                                    songsList.add(songUrl);
                                    songsList.add(songDuration);
                                    songsList.add(sngId);
                                    allSongsDataList.add(songsList);
                                    songListenCount = sngId;
                                    songIds.add(sngId);
                                    popularSongCountList.add(songListenCount);
                                    sameCount = false;
                                    allSongsDataList1 = allSongsDataList;
                                    popularList.put(songListenCount, allSongsDataList);
                                    sortAll = 1;
                                    popularList1.put(sngId, allSongsDataList1);
                                }

                            }

                            HashMap<String, String> songDetail = new HashMap<>();
                            songDetail.put("id", id);
                            songDetail.put("categoryid", categoryId);
                            songDetail.put("songtitle", songName);
                            songDetail.put("singerid", singerId);
                            songDetail.put("singername", singerName);
                            songDetail.put("thumbimage", thumbImgUrl);
                            songDetail.put("songfile", songUrl);
                            songDetail.put("songfilesize", songSize);
                            songDetail.put("listencount", songListenCount);
                            songDetail.put("songfilelength", songDuration);

                            songArraylist.add(songDetail);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    JSONArray singerDetails = jsonObj.getJSONArray("singerdata");
                    // looping through All songDetails
                    for (int i = 0; i < singerDetails.length(); i++) {
                        JSONObject jsonObject = singerDetails.getJSONObject(i);
                        try {
                            String id = jsonObject.getString("id");
                            String singerName = jsonObject.getString("name");
                            String thumbimage = jsonObject.getString("thumbimage");
                            HashMap<String, String> singerDetail = new HashMap<>();
                            singerDetail.put("id", id);
                            singerDetail.put("name", singerName);
                            singerDetail.put("thumbimage", thumbimage);
                            singerArraylist.add(singerDetail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                } catch (final JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }


                SharedPreferences.Editor editor = app_preferences.edit();
                editor.putString("devotionalSongsResponse", jsonStr);
                editor.apply();

            } else {
//                Log.e(TAG, "Couldn't get json from server.");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Please try again!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            try {
                if (mProgressDialog != null)
                    mProgressDialog.dismiss();

                setupViewPager();
                String str = CoverImageUrl;
                Glide.with(DevotionalSongsActivity.this)
                        .load(str)
                        .into(coverImge);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<HashMap<String, String>> getSongArrayList() {
        return songArraylist;
    }

    public ArrayList<HashMap<String, String>> getSingerArrayList() {
        return singerArraylist;
    }

    public ArrayList<String> getPopularSongCountList() {
        return popularSongCountList;
    }

    public ArrayList<String> getSongIds() {
        return songIds;
    }


    public HashMap<String, ArrayList<ArrayList<String>>> getPopularSongAllDetails() {
        return popularList;
    }

    public HashMap<String, ArrayList<ArrayList<String>>> getPopularList1() {
        return popularList1;
    }

    public int getSortInt() {
        return sortAll;
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
                                    TakeScreenShot(DevotionalSongsActivity.this, rootLayout, 0, 19, "0", "தெய்வீகப்பாடல்கள்", progressBar);
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
