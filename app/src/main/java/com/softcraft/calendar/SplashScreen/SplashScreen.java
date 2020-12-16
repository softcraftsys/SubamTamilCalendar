package com.softcraft.calendar.SplashScreen;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.onesignal.OneSignal;
import com.softcraft.calendar.Activity.GridMenuActivity;
//import com.softcraft.calendar.Activity.UserDetailEntryActivity;
import com.softcraft.calendar.Activity.UserDetailActivity;
import com.softcraft.calendar.AlarmService.AlarmServiceBroadcastReciever;
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.JSONParse.DhinapalanModel;
import com.softcraft.calendar.JSONParse.DhinapalanTextModel;
import com.softcraft.calendar.JSONParse.KirthigaiModel;
import com.softcraft.calendar.JSONParse.MugurthamModel;
import com.softcraft.calendar.JSONParse.OtherdetailsModel;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TimerTask;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getInfo;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNewsUrl;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getVersionExpiry;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getversion;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.interstialid;

public class SplashScreen extends AppCompatActivity {
    MiddlewareInterface AMI = MiddlewareInterface.GetInstance();
    TimerTask task;
    static int exceptionValue;
    protected int _splashTime = 3000;
    ArrayList<List<String>> arrayListad;
    static String strRet = "";
    private Boolean isWeatherLoaded = false;
    String sentYear, getColumnLastYear, getAdYear;
    String adintegrationapi = "http://adsenseusers.com/scsadcontrol/api/device4/appsadhandler/format/json"; //live
//    String adintegrationapi = "http://softcraft.ddns.net/projects_final/adsenseusers.com/scsadcontrol/api/device4/appsadhandler/format/json"; //local
//    String adintegrationapi = "http://192.168.1.100/projects_final/adsenseusers.com/scsadcontrol/api/device4/appsadhandler/format/json"; //new local
    public static String PACKAGE_NAME;
    String version, sentCountryCode, sentCity, sentPostalCode, sentDate;
    public static String getcurrentdate;
    public static String utilSongDuration = "songfilelength";
    public static String utilSingerName = "singername";
    public ArrayList<String> getCount;
    String previousYear = "2016";
    public ArrayList<HashMap<String, String>> UtilityArrayList;
    PackageInfo pInfo = null;
    List<String> nativeAdListItems = new ArrayList<>();
    List<String> googlelistitems = new ArrayList<String>();
    List<String> inmobiadlistitems = new ArrayList<String>();
    List<String> weatherDetails = new ArrayList<String>();
    public static boolean bRendering = false;
    private final int PERMISSION_REQUEST_CODE = 1;
    PendingIntent operations;
    private GoogleApiClient client;
    int appActive = MiddlewareInterface.appActive;
    DataBaseHelper db;
    JSONObject object;
    private LocationManager locationManager;//get location details
    private LocationListener locationListener;
    private Geocoder geocoder;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 1;
    private static final long MIN_TIME_BW_UPDATE = 10 * 60 * 1;
    int pagerPos, catId;
    String rasipalanId;
    public static boolean isLiveFeed = true;
    public static int iFromYear = 2020;
    public static int iToYear = 2021;
    public static int isForceUpdate = 1;

    public final static String SHORTCUT_ACTION_1 = "daily";
    public final static String SHORTCUT_ACTION_2 = "monthly";
    public final static String SHORTCUT_ACTION_3 = "fasting";
    private String shortcutKey;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            // OneSignal Initialization
            OneSignal.startInit(this)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .init();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.splash_screen);
        try {
            MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (adintegrationapi.equalsIgnoreCase("http://adsenseusers.com/scsadcontrol/api/device4/appsadhandler/format/json")) {
                isLiveFeed = true;
            } else {
                isLiveFeed = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        PACKAGE_NAME = getApplicationContext().getPackageName();
        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = pInfo.versionName;
        MiddlewareInterface.appVersion = version;
        try {
            db = new DataBaseHelper(this);
//            loadJSONFromAsset();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date d = new Date();
        geocoder = new Geocoder(this);

        try {
            getcurrentdate = new SimpleDateFormat("dd.MM.yyyy").format(d);
            getCount = db.dGetDate();
            //GET LAST VALUE OF COLUMN
            getColumnLastYear = getCount.get(getCount.size() - 1);
            String[] splitYear = getColumnLastYear.split("\\.");
            sentYear = splitYear[2];
            MiddlewareInterface.SharedPreferenceUtility.getInstance(SplashScreen.this).putString(MiddlewareInterface.strSentyear, sentYear);
        } catch (Exception e) {
            if (sentYear == null) {
                Calendar calendar = Calendar.getInstance();
                int nCrntYear = calendar.get(Calendar.YEAR);
                sentYear = String.valueOf(SplashScreen.iToYear);
                MiddlewareInterface.SharedPreferenceUtility.getInstance(SplashScreen.this).putString(MiddlewareInterface.strSentyear, sentYear);
            }
            e.printStackTrace();
        }
        try {
            DeeplinkingData();
            setNotification();
            new GetDashBoard().execute();

            try {
                MiddlewareInterface.PACKAGE_NAME = getApplicationContext().getPackageName();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class GetDashBoard extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
                    Map<String, String> paramss = new HashMap<String, String>();
                    paramss.put("bundleid", PACKAGE_NAME);
                    paramss.put("version", version);
                    paramss.put("deviceos", "Android");
                    paramss.put("onapp", "live");
                    if (!sentYear.equalsIgnoreCase("")) {
                        paramss.put("year", sentYear);
                    }
                    try {
                        post(adintegrationapi, paramss);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (exceptionValue == 200) {
                        SharedPreferences app_preferences1 = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                        SharedPreferences.Editor editor = app_preferences1.edit();
                        editor.putString("adresponse", strRet);
                        editor.apply();

                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                        String adresponse = app_preferences.getString("adresponse", strRet);
                        boolean dbupdated = app_preferences.getBoolean("dbupdate", false);  //dbupdatedbupdate
                        String currentDBVersion = app_preferences.getString("db_version", version);

                        String newDBVersion = "";
                        JSONObject responsedash = new JSONObject(adresponse);
                        if (responsedash.has("version")) {
                            String VERSION = responsedash.getString("version");                    // version.
                            getversion = VERSION;
                            newDBVersion = VERSION;
                        }

                        if (responsedash.has("fromyear")) {
                            String fromYear = responsedash.getString("fromyear");// version.
                            if(!fromYear.equalsIgnoreCase(""))
                            iFromYear = Integer.parseInt(fromYear);
                        }
                        if (responsedash.has("toyear")) {
                            String toyear = responsedash.getString("toyear");
                            if(!toyear.equalsIgnoreCase(""))// version.
                            iToYear = Integer.parseInt(toyear);
                        }
                        if (responsedash.has("forceupdate")) {
                            String forceUpdate = responsedash.getString("forceupdate");
                            if(!forceUpdate.equalsIgnoreCase(""))// version.// version.
                            isForceUpdate = Integer.parseInt(forceUpdate);
                        }

                        if (responsedash.has("info")) {
                            String info = responsedash.getString("info");
                            getInfo = info;
                        }
                        double dNewDbVersion = Double.parseDouble(newDBVersion);
                        double dCrntDbVersion = Double.parseDouble(currentDBVersion);

                        if (dNewDbVersion > dCrntDbVersion)
                            getRespose(adresponse);

                        //News url
                        if (responsedash.has("newsurl")) {
                            String strNewsUrl = responsedash.getString("newsurl");
                            getNewsUrl = strNewsUrl;
                        }
                        if (responsedash.has("enable")) {
                            String enable = responsedash.getString("enable");
                            getEnable = enable;
                        }
                        if (responsedash.has("versionexpiry")) {
                            String versionexpiry = responsedash.getString("versionexpiry");
                            getVersionExpiry = versionexpiry;
                        }
                        if (responsedash.has("googleadmob")) {
                            JSONObject jsonObjectGoogle = responsedash.getJSONObject("googleadmob");
                            if (jsonObjectGoogle.has("bannertype")) {
                                String bannerType = jsonObjectGoogle.getString("bannertype");
                                getBannerType = bannerType;
                            }
                            if (jsonObjectGoogle.has("Banner Ad")) {
                                JSONObject bannerAd = jsonObjectGoogle.getJSONObject("Banner Ad");
                                {
                                    if (bannerAd.has("banner_ad_unit_id1")) {
                                        String strBanner = bannerAd.getString("banner_ad_unit_id1");
                                        getBannerAd = strBanner;
                                    }
                                }
                            }
                            if (jsonObjectGoogle.has("Interstitial Ad")) {
                                JSONObject jsonObjectInterstitial = jsonObjectGoogle.getJSONObject("Interstitial Ad");
                                if (jsonObjectInterstitial.has("interstitial_ad_unit_id1")) {
                                    String strInterstitial = jsonObjectInterstitial.getString("interstitial_ad_unit_id1");
                                    interstialid = strInterstitial;
                                }
                            }
                            if (jsonObjectGoogle.has("Native Ad")) {
                                JSONObject jsonObjectNative = jsonObjectGoogle.getJSONObject("Native Ad");
                                if (jsonObjectNative.has("small")) {
                                    JSONObject jsonObjectSmall = jsonObjectNative.getJSONObject("small");
                                    getNativeSmall = jsonObjectSmall.getString("native_ad_unit_id1");
                                }
                            }
                        }
                        try {
                            JSONObject facebookadObj = responsedash.getJSONObject("facebookad");
                            if (facebookadObj.has("bannertype")) {
                                MiddlewareInterface.getFacebookBannerType = facebookadObj.getString("bannertype");
                            }
                            JSONObject facebookBanneradObj = facebookadObj.getJSONObject("Banner Ad");
                            String facebookadId1 = "";

                            if (facebookBanneradObj.has("banner_ad_unit_id1")) {
                                facebookadId1 = facebookBanneradObj.getString("banner_ad_unit_id1");
                                MiddlewareInterface.getFacebookBannerID = facebookadId1;
                            }
                            JSONObject facebookNativeadObj = facebookadObj.getJSONObject("Native Ad");
                            JSONObject facebookNativesmallObj = facebookNativeadObj.getJSONObject("small");
                            if (facebookNativesmallObj.has("native_ad_unit_id1")) {
                                MiddlewareInterface.getFacebookNativeID = facebookNativesmallObj.getString("native_ad_unit_id1");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        if (responsedash.has("utility")) {
                            UtilityArrayList = new ArrayList<>();
                            JSONArray jsonArrayUtility = new JSONArray(responsedash.getString("utility"));
                            for (int k = 0; k < jsonArrayUtility.length(); k++) {
                                JSONObject objectdata = jsonArrayUtility.getJSONObject(k);
                                String SongName = objectdata.getString("songtitle");
                                String SongUrl = objectdata.getString("songfile");
                                String SongImage = objectdata.getString("thumbimage");
                                String SingerName = objectdata.getString("singername");
                                String songDuration = objectdata.getString("songfilelength");

                                HashMap<String, String> utilitySongDetails = new HashMap<>();

                                utilitySongDetails.put("songtitle", SongName);
                                utilitySongDetails.put("thumbimage", SongImage);
                                utilitySongDetails.put("songfile", SongUrl);
                                utilitySongDetails.put("songfilelength", songDuration);
                                utilitySongDetails.put("singername", SingerName);

                                UtilityArrayList.add(utilitySongDetails);
                            }

                            editor.putString("utilitySongsResponse", responsedash.getString("utility"));
                            editor.apply();
                        }
                    } else {
                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                        String adresponse = app_preferences.getString("adresponse", strRet);

                        if (!adresponse.equalsIgnoreCase("")) {
                            String currentDBVersion = app_preferences.getString("db_version", version);

                            String newDBVersion = "";
                            JSONObject responsedash = new JSONObject(adresponse);
                            if (responsedash.has("version")) {
                                String VERSION = responsedash.getString("version");                    // version.
                                getversion = VERSION;
                                newDBVersion = VERSION;
                            }
                            if (responsedash.has("fromyear")) {
                                String fromYear = responsedash.getString("fromyear");// version.
                                if(!fromYear.equalsIgnoreCase(""))
                                    iFromYear = Integer.parseInt(fromYear);
                            }
                            if (responsedash.has("toyear")) {
                                String toyear = responsedash.getString("toyear");
                                if(!toyear.equalsIgnoreCase(""))// version.
                                    iToYear = Integer.parseInt(toyear);
                            }
                            if (responsedash.has("forceupdate")) {
                                String forceUpdate = responsedash.getString("forceupdate");
                                if(!forceUpdate.equalsIgnoreCase(""))// version.// version.
                                    isForceUpdate = Integer.parseInt(forceUpdate);
                            }

                            if (responsedash.has("info")) {
                                String info = responsedash.getString("info");
                                getInfo = info;
                            }
                            double dNewDbVersion = Double.parseDouble(newDBVersion);
                            double dCrntDbVersion = Double.parseDouble(currentDBVersion);

                            if (dNewDbVersion > dCrntDbVersion)
                                getRespose(adresponse);

                            //News url
                            if (responsedash.has("newsurl")) {
                                String strNewsUrl = responsedash.getString("newsurl");
                                getNewsUrl = strNewsUrl;
                            }
                            if (responsedash.has("enable")) {
                                String enable = responsedash.getString("enable");
                                getEnable = enable;
                            }
                            if (responsedash.has("versionexpiry")) {
                                String versionexpiry = responsedash.getString("versionexpiry");
                                getVersionExpiry = versionexpiry;
                            }
                            if (responsedash.has("googleadmob")) {
                                JSONObject jsonObjectGoogle = responsedash.getJSONObject("googleadmob");
                                if (jsonObjectGoogle.has("bannertype")) {
                                    String bannerType = jsonObjectGoogle.getString("bannertype");
                                    getBannerType = bannerType;
                                }
                                if (jsonObjectGoogle.has("Banner Ad")) {
                                    JSONObject bannerAd = jsonObjectGoogle.getJSONObject("Banner Ad");
                                    {
                                        if (bannerAd.has("banner_ad_unit_id1")) {
                                            String strBanner = bannerAd.getString("banner_ad_unit_id1");
                                            getBannerAd = strBanner;
                                        }
                                    }
                                }
                                if (jsonObjectGoogle.has("Interstitial Ad")) {
                                    JSONObject jsonObjectInterstitial = jsonObjectGoogle.getJSONObject("Interstitial Ad");
                                    if (jsonObjectInterstitial.has("interstitial_ad_unit_id1")) {
                                        String strInterstitial = jsonObjectInterstitial.getString("interstitial_ad_unit_id1");
                                        interstialid = strInterstitial;
                                    }
                                }
                                if (jsonObjectGoogle.has("Native Ad")) {
                                    JSONObject jsonObjectNative = jsonObjectGoogle.getJSONObject("Native Ad");
                                    if (jsonObjectNative.has("small")) {
                                        JSONObject jsonObjectSmall = jsonObjectNative.getJSONObject("small");
                                        getNativeSmall = jsonObjectSmall.getString("native_ad_unit_id1");
                                    }
                                }

                            }

                            if (responsedash.has("utility")) {
                                UtilityArrayList = new ArrayList<>();
                                JSONArray jsonArrayUtility = new JSONArray(responsedash.getString("utility"));
                                for (int k = 0; k < jsonArrayUtility.length(); k++) {
                                    JSONObject objectdata = jsonArrayUtility.getJSONObject(k);
                                    String SongName = objectdata.getString("songtitle");
                                    String SongUrl = objectdata.getString("songfile");
                                    String SongImage = objectdata.getString("thumbimage");
                                    String SingerName = objectdata.getString("singername");
                                    String songDuration = objectdata.getString("songfilelength");

                                    HashMap<String, String> utilitySongDetails = new HashMap<>();

                                    utilitySongDetails.put("songtitle", SongName);
                                    utilitySongDetails.put("thumbimage", SongImage);
                                    utilitySongDetails.put("songfile", SongUrl);
                                    utilitySongDetails.put("songfilelength", songDuration);
                                    utilitySongDetails.put("singername", SingerName);

                                    UtilityArrayList.add(utilitySongDetails);
                                }

                                SharedPreferences.Editor editor = app_preferences.edit();
                                editor.putString("utilitySongsResponse", responsedash.getString("utility"));
                                editor.apply();
                            }
                        } else {
                            interstialid = "ca-app-pub-3295908036015866/3077839431";
                            getNativeSmall = ("ca-app-pub-3295908036015866/5500211038");
                            getBannerAd = "ca-app-pub-3295908036015866/9124373032";
                            getEnable = "1";
//                            getNewsUrl = "http://www.adsenseusers.com/news.php";
                            getNewsUrl = "http://adsenseusers.com/calender/news/";
                            getBannerType = "1";
                        }
                    }
                } else {
                    SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                    String adresponse = app_preferences.getString("adresponse", strRet);

                    if (!adresponse.equalsIgnoreCase("")) {
                        JSONObject responsedash = new JSONObject(adresponse);
                        if (responsedash.has("version")) {
                            String VERSION = responsedash.getString("version");                    // version.
                            getversion = VERSION;
                        }
                        if (responsedash.has("fromyear")) {
                            String fromYear = responsedash.getString("fromyear");// version.
                            if(!fromYear.equalsIgnoreCase(""))
                                iFromYear = Integer.parseInt(fromYear);
                        }
                        if (responsedash.has("toyear")) {
                            String toyear = responsedash.getString("toyear");
                            if(!toyear.equalsIgnoreCase(""))// version.
                                iToYear = Integer.parseInt(toyear);
                        }
                        if (responsedash.has("forceupdate")) {
                            String forceUpdate = responsedash.getString("forceupdate");
                            if(!forceUpdate.equalsIgnoreCase(""))// version.// version.
                                isForceUpdate = Integer.parseInt(forceUpdate);
                        }

                        if (responsedash.has("info")) {
                            String info = responsedash.getString("info");
                            getInfo = info;
                        }
                        //News url
                        if (responsedash.has("newsurl")) {
                            String strNewsUrl = responsedash.getString("newsurl");
                            getNewsUrl = strNewsUrl;
                        }
                        if (responsedash.has("enable")) {
                            String enable = responsedash.getString("enable");
                            getEnable = enable;
                        }
                        if (responsedash.has("googleadmob")) {
                            JSONObject jsonObjectGoogle = responsedash.getJSONObject("googleadmob");
                            if (jsonObjectGoogle.has("bannertype")) {
                                String bannerType = jsonObjectGoogle.getString("bannertype");
                                getBannerType = bannerType;
                            }
                            if (jsonObjectGoogle.has("Banner Ad")) {
                                JSONObject bannerAd = jsonObjectGoogle.getJSONObject("Banner Ad");
                                {
                                    if (bannerAd.has("banner_ad_unit_id1")) {
                                        String strBanner = bannerAd.getString("banner_ad_unit_id1");
                                        getBannerAd = strBanner;
                                    }
                                }
                            }
                            if (jsonObjectGoogle.has("Interstitial Ad")) {
                                JSONObject jsonObjectInterstitial = jsonObjectGoogle.getJSONObject("Interstitial Ad");
                                if (jsonObjectInterstitial.has("interstitial_ad_unit_id1")) {
                                    String strInterstitial = jsonObjectInterstitial.getString("interstitial_ad_unit_id1");
                                    interstialid = strInterstitial;
                                }
                            }
                            if (jsonObjectGoogle.has("Native Ad")) {
                                JSONObject jsonObjectNative = jsonObjectGoogle.getJSONObject("Native Ad");
                                if (jsonObjectNative.has("small")) {
                                    JSONObject jsonObjectSmall = jsonObjectNative.getJSONObject("small");
                                    getNativeSmall = jsonObjectSmall.getString("native_ad_unit_id1");
                                }
                            }

                        }

                        if (responsedash.has("utility")) {
                            UtilityArrayList = new ArrayList<>();

                            JSONArray jsonArrayUtility = new JSONArray(responsedash.getString("utility"));
                            for (int k = 0; k < jsonArrayUtility.length(); k++) {
                                JSONObject objectdata = jsonArrayUtility.getJSONObject(k);
                                String SongName = objectdata.getString("songtitle");
                                String SongUrl = objectdata.getString("songfile");
                                String SongImage = objectdata.getString("thumbimage");
                                String SingerName = objectdata.getString("singername");
                                String songDuration = objectdata.getString("songfilelength");

                                HashMap<String, String> utilitySongDetails = new HashMap<>();

                                utilitySongDetails.put("songtitle", SongName);
                                utilitySongDetails.put("thumbimage", SongImage);
                                utilitySongDetails.put("songfile", SongUrl);
                                utilitySongDetails.put("songfilelength", songDuration);
                                utilitySongDetails.put("singername", SingerName);

                                UtilityArrayList.add(utilitySongDetails);
                            }

                            SharedPreferences.Editor editor = app_preferences.edit();
                            editor.putString("utilitySongsResponse", responsedash.getString("utility"));
                            editor.apply();
                        }

                    } else {
                        interstialid = "ca-app-pub-3295908036015866/3077839431";
                        getNativeSmall = ("ca-app-pub-3295908036015866/5500211038");
                        getBannerAd = "ca-app-pub-3295908036015866/9124373032";
                        getEnable = "1";
//                        getNewsUrl = "http://www.adsenseusers.com/news.php";
                        getNewsUrl = "http://adsenseusers.com/calender/news/";
                        getBannerType = "1";
                    }
                }
            } catch (Exception e) {
                Log.e("HttpManager", "ClientProtocolException thrown" + e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try {

                try{
                    try {
                        String shortCutAction = Objects.requireNonNull(getIntent().getAction());
                        if (shortCutAction != null) {
                            switch (shortCutAction) {
                                case SHORTCUT_ACTION_1:
                                    shortcutKey = SHORTCUT_ACTION_1;
                                    break;
                                case SHORTCUT_ACTION_2:
                                    shortcutKey = SHORTCUT_ACTION_2;
                                    break;
                                case SHORTCUT_ACTION_3:
                                    shortcutKey = SHORTCUT_ACTION_3;
                                    break;
                                default:
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Boolean showUserEntry = sharedPreferences.getBoolean("showUserEntry", true);

                if (showUserEntry) {
                    Intent intent = new Intent(getApplicationContext(), UserDetailActivity.class);
                    intent.putExtra("UtilityArray", UtilityArrayList);
                    intent.putExtra("categoryId", catId);
                    intent.putExtra("viewpagerPos", pagerPos);
                    intent.putExtra("rasipalan", rasipalanId);
                    if (shortcutKey != null && !shortcutKey.equalsIgnoreCase(""))
                        intent.putExtra("shortcutKey", shortcutKey);
                    startActivityForResult(intent, 1);
                    SplashScreen.this.finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), GridMenuActivity.class);
                    intent.putExtra("UtilityArray", UtilityArrayList);
                    intent.putExtra("categoryId", catId);
                    intent.putExtra("viewpagerPos", pagerPos);
                    intent.putExtra("rasipalan", rasipalanId);
                    if (shortcutKey != null && !shortcutKey.equalsIgnoreCase(""))
                        intent.putExtra("shortcutKey", shortcutKey);
                    startActivityForResult(intent, 1);
                    SplashScreen.this.finish();
                }
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
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

    private void getRespose(String adresponse) throws ParseException, JSONException {
        try {
            String strDBVersion = "";

            JSONObject responsedash = new JSONObject(adresponse);
            if (responsedash.has("version")) {
                String VERSION = responsedash.getString("version");                    // version.
                strDBVersion = VERSION;

            }
            if (responsedash.has("panchangamdbdata")) {
                String year, dbchange;
                JSONArray panchangamdbdata = new JSONArray(responsedash.getString("panchangamdbdata"));

                for (int k = 0; k < panchangamdbdata.length(); k++) {
                    JSONObject panchangamdbdatas = panchangamdbdata.getJSONObject(k);
                    year = panchangamdbdatas.getString("year");
                    dbchange = panchangamdbdatas.getString("dbchange");

                    JSONArray item = new JSONArray(panchangamdbdatas.getString("item"));
                    int itemLength = item.length();
                    for (int n = 0; n < item.length(); n++) {
                        object = item.getJSONObject(n);
                        String tablename = object.getString("tablename");

                        if (tablename.equalsIgnoreCase("kirthigai")) {
                            try {

                                JSONArray data = new JSONArray(object.getString("data"));   //data
                                ArrayList<KirthigaiModel> kirthigaiModels = new ArrayList<>();

                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject objectdata = data.getJSONObject(i);
                                    KirthigaiModel kirthigaiModel = new KirthigaiModel();
                                    kirthigaiModel.setKirthigai(objectdata.getString(kirthigaiModel.KEY_KIRTHIGAI));
                                    kirthigaiModel.setKeyAmavasai(objectdata.getString(kirthigaiModel.KEY_AMAVASAI));
                                    kirthigaiModel.setKeyPournami(objectdata.getString(kirthigaiModel.KEY_POURNAMI));
                                    kirthigaiModels.add(kirthigaiModel);
                                }
                                db.setKirthigaiFromJson(kirthigaiModels);
                            }catch (Exception e){e.printStackTrace();}
                        }
                        if (tablename.equalsIgnoreCase("mugurtham")) {
                            try{
                            JSONArray data = new JSONArray(object.getString("data"));   //data
                            ArrayList<MugurthamModel> mugurthamModels = new ArrayList<>();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject objectdata = data.getJSONObject(i);
                                MugurthamModel mugurthamModel = new MugurthamModel();
                                mugurthamModel.setDate(objectdata.getString(mugurthamModel.KEY_DATE));
                                mugurthamModel.setDay(objectdata.getString(mugurthamModel.KEY_DAY));
                                mugurthamModels.add(mugurthamModel);
                            }
                            db.setMugurthamFromJson(mugurthamModels);
                            }catch (Exception e){e.printStackTrace();}
                        }
                        if (tablename.equalsIgnoreCase("otherdetails")) {
                            try{
                            JSONArray data = new JSONArray(object.getString("data"));//data
                            ArrayList<OtherdetailsModel> otherdetailsModels = new ArrayList<>();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject objectdata = data.getJSONObject(i);
                                OtherdetailsModel otherdetailsModel = new OtherdetailsModel();
                                otherdetailsModel.setNatchathiram(objectdata.getString(otherdetailsModel.KEY_NATCHA));
                                otherdetailsModel.setThithi(objectdata.getString(otherdetailsModel.KEY_THITHI));
                                otherdetailsModel.setYogam(objectdata.getString(otherdetailsModel.KEY_YOGAM));
                                otherdetailsModel.setChandra(objectdata.getString(otherdetailsModel.KEY_CHANDRA));
                                otherdetailsModel.setTamildate(objectdata.getString(otherdetailsModel.KEY_TAMILDATE));
                                otherdetailsModel.setTamilday(objectdata.getString(otherdetailsModel.KEY_TAMILDAY));
                                if (objectdata.getString(otherdetailsModel.KEY_FESTIVAL).equalsIgnoreCase("null"))
                                    otherdetailsModel.setFestival("");
                                else
                                    otherdetailsModel.setFestival(objectdata.getString(otherdetailsModel.KEY_FESTIVAL));

                                if (objectdata.getString(otherdetailsModel.KEY_VIRATHAM).equalsIgnoreCase("null"))
                                    otherdetailsModel.setViratham("");
                                else
                                    otherdetailsModel.setViratham(objectdata.getString(otherdetailsModel.KEY_VIRATHAM));

                                otherdetailsModel.setDate(objectdata.getString(otherdetailsModel.KEY_DATE));
                                otherdetailsModel.setGov_holiday(objectdata.getString(otherdetailsModel.KEY_GOV_HOLIDAY));
                                otherdetailsModel.setUpdownday(objectdata.getString(otherdetailsModel.KEY_UPDOWNDAY));
//                                otherdetailsModel.setTamilyear(objectdata.getString(otherdetailsModel.KEY_TAMILYEAR));
                                otherdetailsModels.add(otherdetailsModel);
                            }
                            db.setOtherdetailsFromJson(otherdetailsModels);
                            }catch (Exception e){e.printStackTrace();}
                        }
                        if (tablename.equalsIgnoreCase("dhinapalan")) {
                            try{
                            JSONArray data = new JSONArray(object.getString("data"));//data
                            ArrayList<DhinapalanModel> dhinapalanModels = new ArrayList<>();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject objectdata = data.getJSONObject(i);
                                DhinapalanModel dhinapalanModel = new DhinapalanModel();
                                dhinapalanModel.setDaycount(objectdata.getString(dhinapalanModel.KEY_DAYCOUNT));
                                dhinapalanModel.setData(objectdata.getString(dhinapalanModel.KEY_DATA));
                                dhinapalanModel.setYear(objectdata.getString(dhinapalanModel.KEY_YEAR));
                                dhinapalanModels.add(dhinapalanModel);
                            }
                            db.setDhinapalanFromJson(dhinapalanModels);
                            }catch (Exception e){e.printStackTrace();}
                        }
                        if (tablename.equalsIgnoreCase("dhinapalantext")) {

                            try{
                            JSONArray data = new JSONArray(object.getString("data"));   //data
                            ArrayList<DhinapalanTextModel> dhinapalanTextModels = new ArrayList<>();

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject objectdata = data.getJSONObject(i);
                                DhinapalanTextModel dhinapalanTextModel = new DhinapalanTextModel();
                                dhinapalanTextModel.setId(objectdata.getString(dhinapalanTextModel.KEY_ID));
                                dhinapalanTextModel.setContent(objectdata.getString(dhinapalanTextModel.KEY_CONTENT));
                                dhinapalanTextModels.add(dhinapalanTextModel);
                            }
                            db.setDhinapalanTextFromJsopn(dhinapalanTextModels);
                            }catch (Exception e){e.printStackTrace();}
                        }
                    }
                    SharedPreferences app_preferences1 = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                    SharedPreferences.Editor editor = app_preferences1.edit();
                    sentYear = year;
                    int getDbchange = Integer.parseInt(dbchange);
                    editor.putString("currentyear", sentYear);
                    editor.putInt("checkdbchange", getDbchange);
                    editor.putBoolean("dbupdate", true);
                    editor.putString("db_version", strDBVersion);
                    editor.apply();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNotification() {

        SharedPreferences checkAlarm = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
        Boolean isAlarm = checkAlarm.getBoolean("isAlarm", false);
        if (!isAlarm) {
            Intent mathAlarmServiceIntent = new Intent(SplashScreen.this, AlarmServiceBroadcastReciever.class);
            sendBroadcast(mathAlarmServiceIntent, null);
        }
        SharedPreferences.Editor editor = checkAlarm.edit();
        editor.putBoolean("isAlarm", true);
        editor.apply();

    }

    private void DeeplinkingData() {
        try {
            FirebaseDynamicLinks.getInstance()
                    .getDynamicLink(getIntent())
                    .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                        @Override
                        public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                            if (pendingDynamicLinkData != null) {
                                String strLink = String.valueOf(pendingDynamicLinkData.getLink());
                                try {
                                    String[] strLinkArr = strLink.split("http://www.softcraftsystems.com/redirect\\?");
                                    if (strLinkArr.length > 1) {
                                        String strLnk = strLinkArr[1];

                                        String[] strSplitLink = strLnk.split("~~");
                                        if (strSplitLink.length > 2) {

                                            String strpagerPos = strSplitLink[0];
                                            String strcatId = strSplitLink[1];

                                            pagerPos = Integer.parseInt(strpagerPos);
                                            catId = Integer.parseInt(strcatId);
                                            rasipalanId = strSplitLink[2];

                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Log.w("Deeplink data", strLink);
                            }
                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w("Failure result", "getDynamicLink:onFailure", e);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public String loadJSONFromAsset() {
//        String json = null;
//        try {
//            InputStream is = getAssets().open("Calendas_Data.json");
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//            int i=0;
//
//            JSONObject responsedash = new JSONObject(json);
//
//            JSONArray data = new JSONArray(responsedash.getString("Telugu"));//data
//            ArrayList<String> teluguData = new ArrayList<>();
//
//            for (int j = 0; j < data.length(); j++) {
//                JSONObject objectdata = data.getJSONObject(j);
//
//                String strData1 = objectdata.getString("Abhijith");
//                String strData2 = objectdata.getString("Amavasya/Pornami");
//                String strData3 = objectdata.getString("Amruthkalam");
//                String strData4 = objectdata.getString("Anandadiyoga");
//                String strData5 = objectdata.getString("Date");
//                String strData6 = objectdata.getString("Dhanussu");
//                String strData7 = objectdata.getString("Dhurmuhurth");
//                String strData8 = objectdata.getString("Festivals");
//                String strData9 = objectdata.getString("Gulika");
//                String strData10 = objectdata.getString("Karanam");
//                String strData11 = objectdata.getString("Karkatakam");
//                String strData12 = objectdata.getString("Kumbham");
//                String strData13 = objectdata.getString("Makaram");
//                String strData14 = objectdata.getString("Masam");
//                String strData15 = objectdata.getString("Meenam");
//                String strData16 = objectdata.getString("Mesham");
//                String strData17 = objectdata.getString("Mithunam");
//                String strData18 = objectdata.getString("Month");
//                String strData19 = objectdata.getString("MonthInFestival");
//                String strData20 = objectdata.getString("Moonrise");
//                String strData21 = objectdata.getString("Moonset");
//                String strData22 = objectdata.getString("Moonsign");
//                String strData23 = objectdata.getString("Nakshatram");
//                String strData25 = objectdata.getString("Paksham");
//                String strData26 = objectdata.getString("Rahuv");
//                String strData27 = objectdata.getString("Simham");
//                String strData28 = objectdata.getString("Sunrise");
//                String strData29 = objectdata.getString("Sunset");
//                String strData30 = objectdata.getString("Sunsign");
//                String strData31 = objectdata.getString("Thidi");
//                String strData32 = objectdata.getString("Thula");
//                String strData33 = objectdata.getString("Varjyam");
//                String strData34 = objectdata.getString("VrushaBham");
//                String strData35 = objectdata.getString("Vrushcikam");
//                String strData36 = objectdata.getString("Week");
//                String strData37 = objectdata.getString("Yama");
//                String strData38 = objectdata.getString("Yogam");
//
//
//
//                db.addTeluguData(strData1, strData2 , strData3 , strData4 , strData5 , strData6 ,strData7 , strData8 , strData9,
//                        strData10, strData11, strData12 , strData13 , strData14 , strData15 , strData16,
//                        strData17, strData18, strData19 , strData20 , strData21 , strData22 , strData23 , strData25,
//                        strData26, strData27, strData28 , strData29 , strData30 , strData31 , strData32 , strData33,
//                        strData34, strData35, strData36 , strData37 , strData38);
//            }
//
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return json;
//    }


}
