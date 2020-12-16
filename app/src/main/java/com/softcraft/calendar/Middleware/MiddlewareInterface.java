package com.softcraft.calendar.Middleware;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadProgressListener;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.softcraft.calendar.BuildConfig;
import com.softcraft.calendar.R;
import com.softcraft.calendar.Screenshot.ScreenshotUtils;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MiddlewareInterface {
    public static Typeface tf_mylai;
    public static Typeface tf_didot;
    public static Typeface tf_baamini;
    public static int currentSelectedColor;
    public static int appActive = -1;
    public static boolean bRendering = true;
    //advertisement starts
    public static Boolean bInterstitial = true;
    private static Timer timer = null;
    private static TimerTask timerTask = null;
    public static PublisherInterstitialAd interstitialAds;
    static public String interstialid;
    public static String getCityName = "getCityLocation", getCountryCode = "getCurrentCountryCode", getPostalCode = "getCurrentPostalCode";
    public static String getversion, getInfo, getEnable, getBannerType, getBannerAd, getNativeSmall, getVersionExpiry;
    public static String getNewsUrl;
    static String strversion;
    static public String forumId;
    public static String appVersion;
    ;
    static public String refeshDate;
    public static String PACKAGE_NAME = null;
    ;
    public static ArrayList<List<String>> listsaddetails = new ArrayList<>();  // list details ad.

    //check weather class
    public static String strLocation = "city";
    public static String strCondition = "weathertext";
    public static String strTemp = "temperature";
    public static String strHumidity = "humidity";
    public static String strPressure = "pressure";
    public static String strWind = "wind";
    public static String strWindDeg = "winddirectiondegree";
    public static String strImage = "weatherimage";
    public static String strSentyear = "getlastyearfromdb";
    public static String strWindDirection = "winddirectiondegree";
    public static String strUpdatedTime = "updatedtime";
    public static String strDayDateTime = "daydatetime";
    public static String strDay1 = "day1";
    public static String strDay2 = "day2";
    public static String strDay3 = "day3";
    public static String strDay4 = "day4";
    public static boolean isNotesIntent = false;

    //    private static String SHARELINK = "https://subamtamilcalendar.page.link/jdF1";
    private static String SHARELINK = "https://subamtamilcalendar.page.link/";

    public static String getFacebookBannerID;
    public static String getFacebookNativeID;
    public static String getFacebookBannerType;


    protected MiddlewareInterface() {
    }

    static public String clickDate = "";
    static public String clickDateFromMonth = "";
    static public boolean bAdFree = false;

    public static boolean isAudioPlaying = false;

    static private MiddlewareInterface AMW = null;

    public static MiddlewareInterface GetInstance() {
        if (null == AMW) {
            AMW = new MiddlewareInterface();
        }
        return AMW;
    }

    public void loadresource(Context context) {
        try {
            try {
                InApp_init(context);
                int OSVERSION = android.os.Build.VERSION.SDK_INT;
                if (OSVERSION >= Build.VERSION_CODES.JELLY_BEAN) {
                    bRendering = true;
                } else
                    bRendering = false;
            } catch (Exception e) {
                // TODO: handle exception
            }
            tf_mylai = Typeface.createFromAsset(context.getAssets(), "fonts/mylai.ttf");
            tf_didot = Typeface.createFromAsset(context.getAssets(), "fonts/TheanoDidot-Regular.ttf");
            tf_baamini = Typeface.createFromAsset(context.getAssets(), "fonts/baaminii.ttf");
            Bundle dataBundle = new Bundle();
            dataBundle.putString("BundleUserName", "Pranay");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static String getCurrentDate() {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("d.M.yyyy");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);
        return filename;
    }

    public static String getCurrentDateMonth() {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("M.yyyy");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);
        return filename;
    }

    public static void isMediaPlayerPlaying(MediaPlayer mMediaPlayer) {
        try {
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ResourceType")
    public static Animator zoomAnimation1(Context context, View view) {
        Animator zoomAnimation = null;
        try {
            zoomAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.anim.zoomin_out_animation);
            zoomAnimation.setTarget(view);
            zoomAnimation.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zoomAnimation;
    }


    public static class SharedPreferenceUtility {
        private static SharedPreferenceUtility singleton;
        private SharedPreferences.Editor mEditor;
        private SharedPreferences mSharedPreference;

        static {
            singleton = null;
        }

        public SharedPreferenceUtility(Context context) {
            this.mSharedPreference = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            this.mEditor = this.mSharedPreference.edit();
        }

        public static SharedPreferenceUtility getInstance(Context context) {
            if (singleton == null) {
                singleton = new SharedPreferenceUtility(context);
            }
            return singleton;
        }

        public void putString(String key, String value) {
            this.mEditor.putString(key, value);
            this.mEditor.apply();
        }

        public String getString(String key) {
            return this.mSharedPreference.getString(key, "");
        }

        public String getString(String key, String defaultValue) {
            return this.mSharedPreference.getString(key, defaultValue);
        }

        public void removeString(String key) {
            this.mEditor.remove(key);
            this.mEditor.apply();
        }

        public void putInt(String key, int value) {
            this.mEditor.putInt(key, value);
            this.mEditor.apply();
        }

        public int getInt(String key) {
            return this.mSharedPreference.getInt(key, 0);
        }

        public int getInt(String key, int defaultKey) {
            return this.mSharedPreference.getInt(key, defaultKey);
        }

        public void setBoolean(String key, boolean value) {
            this.mEditor.putBoolean(key, value);
            this.mEditor.apply();
        }

        public boolean getBoolean(String key) {
            return this.mSharedPreference.getBoolean(key, true);
        }

        public void setBooleanSettings(String key, boolean value) {
            this.mEditor.putBoolean(key, value);
            this.mEditor.apply();
        }

        public boolean getBooleanSettings(String key) {
            return this.mSharedPreference.getBoolean(key, false);
        }
    }

    public void InApp_init(Context context) {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            bAdFree = prefs.getBoolean("adfree", false);
            Log.d(bAdFree + "", "bAdFree");
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /*
        public static boolean isNetwork(Context context) throws IOException
        {
            ConnectivityManager connectivityManagercheck=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManagercheck != null)
            {
                NetworkInfo networkInfo=connectivityManagercheck.getActiveNetworkInfo();
                if(networkInfo!=null)
                {
                    if(networkInfo.isConnected())
                            if(networkInfo.isAvailable())
                            {
                                return true;
                            }
                }
            }
            return false;
        }
    */
    //check network available or not
    public static boolean isNetworkStatusAvialable(Context context) throws IOException {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                if (netInfos.isConnected())
                    if (netInfos.isAvailable()) {
                        return true;
                    }
        }
        return false;
    }

    //advertisement start
    public static void intertiAdsStartTimer(Context cnxt) {

        if (!bInterstitial)
            return;

        timer = new Timer();
        initializeTimerTask(cnxt);
        timer.schedule(timerTask, 5 * 60 * 1000, 5 * 60 * 1000);
    }

    public void ShowToast(Context context, ViewGroup viewGroup, String message) {
        LinearLayout mLinearLayout = new LinearLayout(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = layoutInflater.inflate(R.layout.toast, mLinearLayout, true);
        TextView text = (TextView) layout.findViewById(R.id.text);
        int OSVERSION = android.os.Build.VERSION.SDK_INT;
        if (OSVERSION >= Build.VERSION_CODES.LOLLIPOP) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            text.setText(UnicodeUtil.unicode2tsc(message));
            text.setTypeface(MiddlewareInterface.tf_mylai);
            Toast toast = new Toast(context);
            toast.setGravity(Gravity.BOTTOM, 0, 140);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }
    }

    public static void initializeTimerTask(final Context cnxt) {

        try {
            final Handler handler = new Handler(Looper.getMainLooper());
            timerTask = new TimerTask() {
                public void run() {
                    handler.post(new Runnable() {
                        public void run() {
                            try {
                                LoadInterstitialAd(cnxt);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void LoadInterstitialAd(Context cxt) {
        try {
            if (!bInterstitial)
                return;
            if (isBackgroundRunning(cxt))
                return;
            interstitialAds = new PublisherInterstitialAd(cxt);

            if (MiddlewareInterface.bAdFree == false) {
                interstitialAds.setAdUnitId(interstialid);
            } else {
                interstitialAds.setAdUnitId("");
            }
            interstitialAds.setAdListener(new InterstitialAdListener());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                callInterstitialAd();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isBackgroundRunning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void callInterstitialAd() {
        try {
            PublisherAdRequest adRequest = new PublisherAdRequest.Builder().build();                        // add your test device here
            interstitialAds.loadAd(adRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class InterstitialAdListener extends AdListener {
        @Override
        public void onAdLoaded() {
//            if (interstitialAds.isLoaded()) {
//                interstitialAds.show();
//            } else {
//            }
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            String message = String.format("onAdFailedToLoad (%s)", getErrorReason(errorCode));
        }

        private String getErrorReason(int errorCode) {
            switch (errorCode) {
                case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                    return "Internal error";
                case AdRequest.ERROR_CODE_INVALID_REQUEST:
                    return "Invalid request";
                case AdRequest.ERROR_CODE_NETWORK_ERROR:
                    return "Network Error";
                case AdRequest.ERROR_CODE_NO_FILL:
                    return "No fill";
                default:
                    return "Unknown error";
            }
        }
    }

    public static void TakeScreenShot(Activity activity, RelativeLayout rootLayout, int pagerPos, int catId, String rasipalan, String content, ProgressBar progressBar) {
        try {
            hideShowProgressBar(activity, progressBar, 1);
            Bitmap b = null;
            b = ScreenshotUtils.getScreenShot(rootLayout);
            if (b != null) {
//            showScreenShotImage(b);//show bitmap over imageview
                File saveFile = ScreenshotUtils.getMainDirectoryName(activity);//get the path to save screenshot
                File imagePath = ScreenshotUtils.store(b, "SubamTamilCalendar" + ".jpg", saveFile);//save the screenshot to selected path
                shareFirebaseDeepLink(activity, imagePath, pagerPos, catId, rasipalan, content, progressBar);
            } else {
//            Toast.makeText(this, R.string.screenshot_take_failed, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void TakeScreenShot(Activity activity, RelativeLayout rootLayout, int pagerPos, int catId, String rasipalan, String content, ProgressBar progressBar, int flag) {
//    public static void TakeScreenShot(Activity activity, RelativeLayout rootLayout,int pagerPos,int catId,String rasipalan,String content) {
        try {
            hideShowProgressBar(activity, progressBar, 1);
            Bitmap b = null;
            b = ScreenshotUtils.getScreenShot(rootLayout);
            if (b != null) {
//            showScreenShotImage(b);//show bitmap over imageview
                File saveFile = ScreenshotUtils.getMainDirectoryName(activity);//get the path to save screenshot
                File imagePath = ScreenshotUtils.store(b, "SubamTamilCalendar" + ".jpg", saveFile);//save the screenshot to selected path
                shareFirebaseDeepLink(activity, imagePath, pagerPos, catId, rasipalan, content, progressBar, 1);
            } else {
//            Toast.makeText(this, R.string.screenshot_take_failed, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void shareFirebaseDeepLink(final Activity activity, final File imagePath, int pagerPos, int catId, String rasipalan, final String content, final ProgressBar progressBar) {
        try {
            String strCatId = String.valueOf(catId);
            final String strAppName = activity.getResources().getString(R.string.app_name);
            Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLongLink(Uri.parse(SHARELINK + "?link=http://www.softcraftsystems.com/redirect?" + pagerPos + "~~" + strCatId + "~~" + rasipalan + "&apn=" + MiddlewareInterface.PACKAGE_NAME))
//                    .setLongLink(Uri.parse(SHARELINK + "10" + "~" + strCatId + "&apn=" + MiddlewareInterface.PACKAGE_NAME ))
                    .buildShortDynamicLink()
                    .addOnCompleteListener(activity, new OnCompleteListener<ShortDynamicLink>() {
                        @Override
                        public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                            if (task.isSuccessful()) {
                                final Uri shortLink = task.getResult().getShortLink();
                                Uri flowchartLink = task.getResult().getPreviewLink();
//                                final Uri uri = Uri.fromFile(imagePath);

                                final Uri uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", imagePath);


                                Log.w("Share", String.valueOf(shortLink));
                                final Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("image/*");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "சுபம் தமிழ் நாட்காட்டி");
                                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, String.valueOf(shortLink + "\n\n" + content));
                                activity.startActivity(Intent.createChooser(sharingIntent, "Share"));

                                hideShowProgressBar(activity, progressBar, 0);
//                                final List<ResolveInfo> activities = activity.getPackageManager().queryIntentActivities(sharingIntent, 0);
//                                List<String> appNames = new ArrayList<>();
//                                for (ResolveInfo info : activities) {
//                                    appNames.add(info.loadLabel(activity.getPackageManager()).toString());
//                                }
//                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                                builder.setTitle("Subam Tamil Calendar");
//                                builder.setItems(appNames.toArray(new CharSequence[appNames.size()]), new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int item) {
//                                        ResolveInfo info = activities.get(item);
//                                        if (info.activityInfo.packageName.equals("com.facebook.katana")) {
////                                            sharingIntent.setType("text/plain");
//                                            sharingIntent.setType("image/*");
////                                            sharingIntent.putExtra(Intent.EXTRA_TITLE, shortLink + "\n\n" + strAppName);
//                                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, content);
//                                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, String.valueOf(shortLink + "\n\n" + strAppName));
//                                            activity.startActivity(Intent.createChooser(sharingIntent, "Share"));
//                                        } else {
//                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                                            sharingIntent.setPackage(info.activityInfo.packageName);
//                                            activity.startActivity(Intent.createChooser(sharingIntent, "Share"));
//                                        }
//
//                                    }
//                                });
//                                AlertDialog alert = builder.create();
//                                alert.show();
                            } else {
                                try {
                                    Log.w("Share error", "Caught");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void shareFirebaseDeepLink(final Activity activity, final File imagePath, int pagerPos, int catId, String rasipalan, final String content, final ProgressBar progressBar, int i) {
        try {
            String strCatId = String.valueOf(catId);
            final String strAppName = activity.getResources().getString(R.string.app_name);
            Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLongLink(Uri.parse(SHARELINK + "?link=http://www.softcraftsystems.com/redirect?" + pagerPos + "~~" + strCatId + "~~" + rasipalan + "&apn=" + MiddlewareInterface.PACKAGE_NAME))
//                    .setLongLink(Uri.parse(SHARELINK + "10" + "~" + strCatId + "&apn=" + MiddlewareInterface.PACKAGE_NAME ))
                    .buildShortDynamicLink()
                    .addOnCompleteListener(activity, new OnCompleteListener<ShortDynamicLink>() {
                        @Override
                        public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                            if (task.isSuccessful()) {
                                final Uri shortLink = task.getResult().getShortLink();
                                Uri flowchartLink = task.getResult().getPreviewLink();
//                                final Uri uri = Uri.fromFile(imagePath);

                                final Uri uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", imagePath);


                                Log.w("Share", String.valueOf(shortLink));
                                final Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                                sharingIntent.setType("image/*");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "சுபம் தமிழ் நாட்காட்டி");
                                sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, String.valueOf(shortLink + "\n\n" + content));
                                activity.startActivity(Intent.createChooser(sharingIntent, "Share"));

                                hideShowProgressBar(activity, progressBar, 0);
//                                final List<ResolveInfo> activities = activity.getPackageManager().queryIntentActivities(sharingIntent, 0);
//                                List<String> appNames = new ArrayList<>();
//                                Collections.sort(activities,
//                                        new ResolveInfo.DisplayNameComparator(activity.getPackageManager()));
//                                for (ResolveInfo info : activities) {
//                                    appNames.add(info.loadLabel(activity.getPackageManager()).toString());
//                                }
//                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
//                                builder.setTitle("Subam Tamil Calendar");
//                                builder.setItems(appNames.toArray(new CharSequence[appNames.size()]), new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int item) {
//                                        ResolveInfo info = activities.get(item);
//                                        if (info.activityInfo.packageName.equals("com.facebook.katana")) {
////                                            sharingIntent.setType("text/plain");
//                                            sharingIntent.setType("image/*");
////                                            sharingIntent.putExtra(Intent.EXTRA_TITLE, shortLink + "\n\n" + strAppName);
//                                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, content);
//                                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, String.valueOf(shortLink + "\n\n" + strAppName));
//                                            activity.startActivity(Intent.createChooser(sharingIntent, "Share"));
//                                        } else {
//                                            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                                            sharingIntent.setPackage(info.activityInfo.packageName);
//                                            activity.startActivity(Intent.createChooser(sharingIntent, "Share"));
//                                        }
//
//                                    }
//                                });
//                                AlertDialog alert = builder.create();
//                                alert.show();
                            } else {
                                try {
                                    Log.w("Share error", "Caught");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
            hideShowProgressBar(activity, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void setNativeFBAD(final Context context, final LinearLayout linearad) {
        try {
            if (MiddlewareInterface.bAdFree)
                return;

            final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(context, MiddlewareInterface.getFacebookNativeID);
            nativeAd.setAdListener(new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError error) {
                    try {
                        String strError = error.getErrorMessage();
                        Log.d("Error", strError);
                        if (linearad != null)
                            linearad.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (nativeAd != null) {
                        nativeAd.unregisterView();
                    }

                    LayoutInflater inflater = LayoutInflater.from(context);
                    LinearLayout adView = (LinearLayout) inflater.inflate(R.layout.facebook_nativead_layout, linearad, false);
                    if (linearad != null) {
                        linearad.removeAllViews();
                        linearad.setVisibility(View.VISIBLE);
                        linearad.addView(adView);
                    }

                    AdIconView nativeAdIcon = adView.findViewById(R.id.native_ad_icon);
                    TextView nativeAdTitle = adView.findViewById(R.id.native_ad_title);
                    com.facebook.ads.MediaView nativeAdMedia = adView.findViewById(R.id.native_ad_media);
                    TextView nativeAdSocialContext = adView.findViewById(R.id.native_ad_social_context);
                    TextView nativeAdBody = adView.findViewById(R.id.native_ad_body);
                    Button nativeAdCallToAction = adView.findViewById(R.id.native_ad_call_to_action);

//                    try {
//                        if (MiddlewareInterface.Font_color == 1) {
//                            adView.setBackgroundColor( Color.BLACK );
//                            nativeAdTitle.setTextColor( Color.WHITE );
//                            nativeAdSocialContext.setTextColor( Color.WHITE );
//                            nativeAdBody.setTextColor( Color.WHITE );
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    try {
                        nativeAdCallToAction.setBackgroundColor(Color.GRAY);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    nativeAdTitle.setText(nativeAd.getAdvertiserName());
                    nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                    nativeAdBody.setText(nativeAd.getAdBodyText());
                    nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

                    LinearLayout adChoicesContainer = adView.findViewById(R.id.ad_choices_container);
                    AdChoicesView adChoicesView = new AdChoicesView(context, nativeAd, true);
                    adChoicesContainer.addView(adChoicesView);

                    List<View> clickableViews = new ArrayList<>();
                    clickableViews.add(nativeAdTitle);
                    clickableViews.add(nativeAdCallToAction);
                    nativeAd.registerViewForInteraction(linearad, nativeAdMedia, nativeAdIcon, clickableViews);
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }

            });
            nativeAd.loadAd(com.facebook.ads.NativeAd.MediaCacheFlag.ALL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //facebook banner ad
    public static void setBannerFBAD(final Context context, final LinearLayout linearad) {
        try {

            if (MiddlewareInterface.bAdFree)
                return;

            if (linearad != null)
                linearad.removeAllViews();

            com.facebook.ads.AdView fbBannerAd = new com.facebook.ads.AdView(context, MiddlewareInterface.getFacebookBannerID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            linearad.addView(fbBannerAd);
            fbBannerAd.setAdListener(new com.facebook.ads.AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    try {
                        linearad.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("FB Ad Error", adError.getErrorMessage());
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    try {
                        linearad.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.d("FB Ad Loaded", ad.toString());
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            });

            // Request an ad
            fbBannerAd.loadAd();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideShowProgressBar(Activity activity, final ProgressBar progressBar, final int flag) {
        try {
//            activity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
            if (flag == 0) {
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
            }
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void hideShowProgressBar(Activity activity, final int flag) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Boolean DateComparator(String currentDate) {
        Boolean isDateAfter = false;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("d.M.yyyy", Locale.US);
            Date date1 = sdf.parse("13.4." + String.valueOf(SplashScreen.iToYear));
            Date date2 = sdf.parse(currentDate);

            if (date2.after(date1)) {
                isDateAfter = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDateAfter;

    }

    public static String getMp3FilePath(String fileName) {
        String rootPath = Environment.getExternalStorageDirectory().toString();
        return rootPath + "/SubamTamilCalendar/Songs/" + fileName;
    }

    public static boolean isMp3FileAvaliable(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }


    public static void DownloadMp3File(String url, String filename, final Context context, final TextView percentageTv, final ProgressBar progressBar, final ProgressBar percentageProgress, final ImageView downloadBtn) {
        try {

            AndroidNetworking.initialize(context);
            //Folder Creating Into Phone Storage
            final String dirPath = Environment.getExternalStorageDirectory() + "/SubamTamilCalendar/Songs";
            final String fileName = filename;
            //file Creating With Folder & Fle Name

            final File file = new File(dirPath, fileName);

            if (file.exists()) {
                Toast.makeText(context, "File Already Exists", Toast.LENGTH_SHORT).show();
            } else {

                AndroidNetworking.download(url, dirPath, fileName)
                        .build()
                        .setDownloadProgressListener(new DownloadProgressListener() {
                            private int prevProgress;
                            @Override
                            public void onProgress(long bytesDownloaded, long totalBytes) {
                                final int progress = (int) (bytesDownloaded * 100 / totalBytes);

                                if (progress != prevProgress) {
                                    percentageProgress.setProgress(progress);
                                    percentageTv.setText(progress + " %");
                                    prevProgress = progress;

                                    Log.e("progress", "total = " + totalBytes +
                                            " loaded = " + bytesDownloaded + " percentage = " + progress + "%");
                                }
                            }
                        })
                        .startDownload(new com.androidnetworking.interfaces.DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
//                                progressBar.setVisibility(View.GONE);
                                percentageProgress.setProgress(100);
                                percentageTv.setText("100%");
                                percentageTv.setVisibility(View.GONE);
                                percentageProgress.setVisibility(View.GONE);
                                downloadBtn.setVisibility(View.GONE);
                                Toast.makeText(context, "Download Complete", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onError(ANError anError) {
                                percentageProgress.setVisibility(View.GONE);
                                percentageTv.setVisibility(View.GONE);
//                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(context, "Error occured, Please try again!", Toast.LENGTH_SHORT).show();
                            }
                        });

                percentageTv.setVisibility(View.VISIBLE);
                percentageProgress.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
