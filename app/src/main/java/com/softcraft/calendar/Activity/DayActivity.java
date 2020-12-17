package com.softcraft.calendar.Activity;

import android.Manifest;
import android.animation.Animator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;
import com.softcraft.calendar.BuildConfig;
import com.softcraft.calendar.ServiceAndOthers.ConnectivityReceiver;
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.Adapter.DayAdapter;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.StoriesAndArticals.StoriesAndArticlesActivity;
import com.softcraft.calendar.Adapter.TestRecyclerViewAdapter;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.ServiceAndOthers.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.isNetworkStatusAvialable;


public class DayActivity extends AppCompatActivity implements DayAdapter.IMethodCaller, TestRecyclerViewAdapter.IMethodCaller, ConnectivityReceiver.ConnectivityReceiverListener, LocationListener {
    public static Context context;
    public static String getcurrentdate;
    public static String POS_KEY = "current_pos";
    public static String DETAIL_COUNT = "detailCount";

    public ViewPager viewPager;
    public int currentPosition;
    public PagerAdapter pagerAdapter;
    public ArrayList<String> getCount;
    public int loopvalue;
    DataBaseHelper db;
    LinearLayout linearad;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    AdView viewBannerAd;
    NativeExpressAdView viewNativeAd;
    FloatingActionButton refresh;
    TextView refreshText;
    static boolean active = false;

    View help_image_view;
    ImageView gridMenu;
    View circlePG;
    protected int HelpImageTime = 5000;
//    private LocationManager locationManager;
//    private LocationListener locationListener;
    private Geocoder geocoder;
    LinearLayout weatherLayout;
    TextView wLocation, wCondition, wTemp, tToolbartitle;
    String clickDay = "Day", clickMonth = "Month", clickYear = "Year";
    public final String APP_ID = BuildConfig.OWM_API_KEY;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 1;
    private static final long MIN_TIME_BW_UPDATE = 10 * 60 * 1;
    public static boolean isFirst = false;
    public static final String inputFormat = "HH:mm";
    String sentCountryCode, sentCity, sentDate, version;
    public static String PACKAGE_NAME;
    PackageInfo pInfo = null;
    static int exceptionValue;
    static String strRet = "";
    Animation show;
    Boolean animflag = false;
    public ArrayList<HashMap<String, String>> UtilityArrayList;
    String adintegrationapi = "http://adsenseusers.com/scsadcontrol/api/device4/appsadhandler/format/json";
    String sentPostalCode;
    RelativeLayout rootLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
        try {
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }


            initItems();

//            try {
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
//
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }


//            initOPPO();

//            getWeatherAndSetFunc();
//            checkPermission();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initOPPO() {
        try {
            if (Build.BRAND.equalsIgnoreCase("xiaomi")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setComponent(new ComponentName("com.oppo.safe", "com.oppo.safe.permission.floatwindow.FloatWindowListActivity"));
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Intent intent = new Intent("action.coloros.safecenter.FloatWindowListActivity");
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.floatwindow.FloatWindowListActivity"));
                startActivity(intent);
            } catch (Exception ee) {
                ee.printStackTrace();
                try {
                    Intent i = new Intent("com.coloros.safecenter");
                    i.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity"));
                    startActivity(i);
                } catch (Exception e1) {

                    e1.printStackTrace();
                }
            }
        }
    }

    private void getWeatherAndSetFunc() {
        try {
            if (isNetworkStatusAvialable(getApplicationContext())) {
//                getLocationFromLocationManager();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initItems() {
        try {


            weatherLayout = (LinearLayout) findViewById(R.id.weather_layout);
            linearad = (LinearLayout) findViewById(R.id.adview);
            final LinearLayout goToMonthview = (LinearLayout) findViewById(R.id.day_icon_click);


            circlePG = (View) findViewById(R.id.circlePG);
            help_image_view = (View) findViewById(R.id.help_image_view);

            wLocation = (TextView) findViewById(R.id.dayWLocation);
            wCondition = (TextView) findViewById(R.id.dayWCondition);
            wTemp = (TextView) findViewById(R.id.dayWTemp);
            tToolbartitle = (TextView) findViewById(R.id.day_toolbar_title);
            refreshText = (TextView) findViewById(R.id.restart_text);

            final ImageView menuClick = (ImageView) findViewById(R.id.menu_click);
            gridMenu = (ImageButton) findViewById(R.id.gridMenu);

            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            final FloatingActionButton shareScreenBtn = (FloatingActionButton) findViewById(R.id.shareFloatingBtn);
            refresh = (FloatingActionButton) findViewById(R.id.restart_currentdate);

            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

            geocoder = new Geocoder(this, Locale.getDefault());

            Date strdate = new Date();
            sentDate = new SimpleDateFormat("yyyy-M-d").format(strdate);
            UtilityArrayList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("UtilityArray");

            try {
                PACKAGE_NAME = getApplicationContext().getPackageName();
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                version = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            try {
                db = new DataBaseHelper(getApplicationContext());
            } catch (IOException e) {
                e.printStackTrace();
            }

//            weatherLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DayActivity.this, weatherLayout);
//                    zoomAnimation.addListener(new Animator.AnimatorListener() {
//                        @Override
//                        public void onAnimationStart(Animator animator) {
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animator animator) {
//                            try {
//                                if (!MiddlewareInterface.SharedPreferenceUtility.getInstance(getApplicationContext()).getString(MiddlewareInterface.strLocation).equalsIgnoreCase("")) {
//                                    Intent intent = new Intent(getApplicationContext(), WeatherCheck.class);
//                                    startActivity(intent);
//                                } else {
//                                    ActivityCompat.requestPermissions(DayActivity.this,
//                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onAnimationCancel(Animator animator) {
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animator animator) {
//                        }
//                    });
//                }
//            });
            help_image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    help_image_view.setVisibility(View.GONE);
                }
            });

            try {
                shareScreenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DayActivity.this, shareScreenBtn);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    TakeScreenShot(DayActivity.this, rootLayout, viewPager.getCurrentItem(), 1, "0", "தினசரி நாள்காட்டி", progressBar);
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
            showHelpImage(help_image_view);

            try {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        help_image_view.setVisibility(View.GONE);
                    }
                }, HelpImageTime);

            } catch (Exception e) {
                e.printStackTrace();
            }

            goToMonthview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DayActivity.this, goToMonthview);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                circlePG.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(DayActivity.this, MonthActivity.class);
                                intent.putExtra("UtilityArray", UtilityArrayList);
                                startActivityForResult(intent, 5);
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

            viewPagerInit();

            gridMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DayActivity.this, gridMenu);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                Bundle bundle = new Bundle();

                                if (isFirst == true) {
                                    bundle.putInt("itemFlag", 1);
                                    Intent intent = new Intent(getApplicationContext(), GridMenuActivity.class);
                                    intent.putExtra("UtilityArray", UtilityArrayList);
                                    startActivity(intent.putExtras(bundle));

                                } else {
                                    bundle.putInt("itemFlag", 0);
                                    Intent intent = new Intent(getApplicationContext(), GridMenuActivity.class);
                                    intent.putExtra("UtilityArray", UtilityArrayList);
                                    startActivity(intent.putExtras(bundle));
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
            SharedPreferences app_prefs = PreferenceManager.getDefaultSharedPreferences(DayActivity.this);
            Boolean isUpdate = app_prefs.getBoolean("firstupdate", false);

            if (isUpdate) {
                SharedPreferences.Editor editor = app_prefs.edit();
                editor.putBoolean("firstupdate", true);
                editor.apply();
                Intent i = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
//            try {
//                Handler handler = new Handler();
//                Runnable r = new Runnable() {
//                    public void run() {
//                        setWeather();
//                    }
//                };
//                handler.postDelayed(r, 1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            setAd();

            String monthSelect = getIntent().getStringExtra("edittextvalue");
            String crntDate = getIntent().getStringExtra("crntDate");
            if (monthSelect != null && !monthSelect.equalsIgnoreCase("")) {
                getcurrentdate = MiddlewareInterface.clickDate;
                getCount = db.dGetDate();

                for (int i = 0; i < getCount.size(); i++) {
                    if (getCount.get(i).equals(getcurrentdate)) {
                        loopvalue = i;
                        break;
                    }
                }
                int startPos = MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getInt(POS_KEY);
                viewPager.setCurrentItem(loopvalue);
                //Todo arun
                refresh.setVisibility(View.GONE);
                refreshText.setVisibility(View.GONE);
            }

            if (crntDate != null && !crntDate.equalsIgnoreCase("")) {
                getcurrentdate = crntDate;
                getCount = db.dGetDate();

                for (int i = 0; i < getCount.size(); i++) {
                    if (getCount.get(i).equals(getcurrentdate)) {
                        loopvalue = i;
                        break;
                    }
                }
                int startPos = MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getInt(POS_KEY);
                viewPager.setCurrentItem(loopvalue);
                //Todo arun
                refresh.setVisibility(View.GONE);
                refreshText.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean getLocationAddress() {
        Boolean getAddress = true;
        try {
            if (isNetworkStatusAvialable(getApplicationContext())) {
                GpsTracker gps = new GpsTracker(this);
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    final Geocoder gcd = new Geocoder(getApplicationContext(), Locale.getDefault());

                    List<Address> addresses = gcd.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {
                        sentPostalCode = addresses.get(0).getPostalCode();
                        sentCountryCode = addresses.get(0).getCountryCode();
                        sentCity = addresses.get(0).getLocality();

                        if (sentPostalCode != null && !sentPostalCode.equalsIgnoreCase("")) {
                            getAddress = false;
                            return getAddress;
                        }
                        MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.getCityName, sentCity);
                        MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.getCountryCode, sentCountryCode);
                        MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.getPostalCode, sentPostalCode);
//                        new GetWeather().execute();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            getAddress = false;
            return getAddress;
        }
        return getAddress;
    }
//
//    private void setWeather() {
//        try {
//            String isLocation = MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getString(MiddlewareInterface.strLocation);
//            String isCondition = MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getString(MiddlewareInterface.strCondition);
//            String isTemp = MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getString(MiddlewareInterface.strTemp);
//            if (isLocation != null && isCondition != null && isTemp != null && !isLocation.equalsIgnoreCase("") && !isCondition.equalsIgnoreCase("") && !isTemp.equalsIgnoreCase("")) {
//                try {
//                    setWeatherText();
//                    isFirst = true;
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else {
//                final Handler handler = new Handler();
//                Runnable r = new Runnable() {
//                    public void run() {
//                        try {
//                            if (isNetworkStatusAvialable(getApplicationContext())) {
//                                Boolean hasWeather = getLocationAddress();
//                                if (hasWeather) {
//                                    setWeather();
//                                }
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                handler.postDelayed(r, 10000);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setWeatherText() {
//        try {
//            Handler handler = new Handler();
//            Runnable r = new Runnable() {
//                public void run() {
//                    if (animflag == false) {
//                        show = AnimationUtils.loadAnimation(DayActivity.this, R.anim.lef_to_right);
//                        animflag = true;
//                    } else {
//                        show = AnimationUtils.loadAnimation(DayActivity.this, R.anim.right_to_left);
//                        animflag = false;
//                    }
//                    wLocation.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getString(MiddlewareInterface.strLocation));
//                    wCondition.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getString(MiddlewareInterface.strCondition));
//                    wTemp.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getString(MiddlewareInterface.strTemp));
//                    if (weatherLayout != null) {
//                        weatherLayout.startAnimation(show);
//                    }
//                    setWeatherText();
//                }
//            };
//            handler.postDelayed(r, 10000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void setNativeSmall() {
        try {
            try {
                String strId = getNativeSmall;
                if (MiddlewareInterface.bAdFree)
                    return;
                viewNativeAd = new NativeExpressAdView(getApplicationContext());
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
                    linearad.setVisibility(View.VISIBLE);
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

    private void setAd() {
        try {
            if (isNetworkStatusAvialable(getApplicationContext())) {
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void viewPagerInit() {
        try {

            int deepLinkViewpagerPos = getIntent().getIntExtra("viewpagerPos", -1);

            viewPager = (ViewPager) findViewById(R.id.day_pager);
            viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
            Date d = new Date();
            getcurrentdate = new SimpleDateFormat("dd.MM.yyyy").format(d);
            getCount = db.dGetDate();

            pagerAdapter = new DayAdapter(DayActivity.this, getcurrentdate, getCount);
            try {
                if (MiddlewareInterface.bRendering) {
                    refreshText.setText((getResources().getString(R.string.today)));
                    tToolbartitle.setText((getResources().getString(R.string.month)));
                    refreshText.setTypeface(Typeface.DEFAULT);
                    tToolbartitle.setTypeface(Typeface.DEFAULT);
                } else {
                    refreshText.setText(UnicodeUtil.unicode2tsc((getResources().getString(R.string.today))));
                    tToolbartitle.setText(UnicodeUtil.unicode2tsc((getResources().getString(R.string.month))));
                    refreshText.setTypeface(MiddlewareInterface.tf_mylai);
                    tToolbartitle.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < getCount.size(); i++) {
                if (getCount.get(i).equals(getcurrentdate)) {
                    loopvalue = i;
                    break;
                }
            }

            viewPager.setAdapter(pagerAdapter);


            if (deepLinkViewpagerPos != -1) {
                viewPager.setCurrentItem(deepLinkViewpagerPos);

            } else {
                viewPager.setCurrentItem(loopvalue);
            }

            if (MiddlewareInterface.clickDate.equalsIgnoreCase(""))
                MiddlewareInterface.SharedPreferenceUtility.getInstance(this).putInt(POS_KEY, loopvalue);
            // Attach the page change listener inside the activity
            if (!getcurrentdate.equalsIgnoreCase(MiddlewareInterface.getCurrentDate())) {
                refresh.setVisibility(View.VISIBLE);
                refreshText.setVisibility(View.VISIBLE);
            } else {
                refresh.setVisibility(View.GONE);
                refreshText.setVisibility(View.GONE);
            }
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    try {
                        viewPager.setCurrentItem(position);
                        currentPosition = position;
                        RefreshAds();
                        if (MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getInt(POS_KEY) == position) {
                            refresh.setVisibility(View.GONE);
                            refreshText.setVisibility(View.GONE);
                        } else {
                            refresh.setVisibility(View.VISIBLE);
                            refreshText.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            refresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DayActivity.this, gridMenu);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                int startPos = MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getInt(POS_KEY);
                                viewPager.setCurrentItem(startPos);
                                refresh.setVisibility(View.GONE);
                                refreshText.setVisibility(View.GONE);
                                RefreshAds();
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
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        try {
            if (isConnected) {
            } else {
                Toast.makeText(getApplicationContext(), "Need Internet Connection", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        getAddress(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }


    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }


    @Override
    public void clickPrevNextButton(int iFlag, int i) {
        try {
            getCount = db.dGetDate();

            int startPos = MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getInt(POS_KEY);
            if (iFlag == 1) {
                viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                int Position = getItemNext(i);
                viewPager.setCurrentItem(Position);
            } else if (iFlag == 2) {
                viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                int Position = getItemPrev(i);
                viewPager.setCurrentItem(Position);
            } else
                viewPager.setCurrentItem(startPos);

            if (MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getInt(POS_KEY) == currentPosition) {
                refresh.setVisibility(View.GONE);
                refreshText.setVisibility(View.GONE);
            } else {
                refresh.setVisibility(View.VISIBLE);
                refreshText.setVisibility(View.VISIBLE);
            }
            int loopValue = viewPager.getCurrentItem();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setDate(String setCurrentdate) {
        try {
            getcurrentdate = setCurrentdate;
            getCount = db.dGetDate();

            for (int i = 0; i < getCount.size(); i++) {
                if (getCount.get(i).equals(getcurrentdate)) {
                    loopvalue = i;
                    break;
                }
            }
            pagerAdapter = new DayAdapter(DayActivity.this, getcurrentdate, getCount);
            viewPager.setAdapter(pagerAdapter);
            viewPager.setCurrentItem(loopvalue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setClickDateFromMonth(String setdate) {
        try {
            getcurrentdate = setdate;
            getCount = db.dGetDate();
            for (int i = 0; i < getCount.size(); i++) {
                if (getCount.get(i).equals(getcurrentdate)) {
                    loopvalue = i;
                    break;
                }
            }
            pagerAdapter = new DayAdapter(DayActivity.this, getcurrentdate, getCount);
            viewPager.setAdapter(pagerAdapter);
            viewPager.setCurrentItem(loopvalue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInternetConnectionStories() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                    Intent intent = new Intent(this, StoriesAndArticlesActivity.class);
                    startActivity(intent);
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Intent intent = new Intent(this, StoriesAndArticlesActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast toast = Toast.makeText(DayActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInternetConnectionDevotional() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    Intent intent = new Intent(this, DevotionalSongsActivity.class);
                    startActivity(intent);
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Intent intent = new Intent(this, DevotionalSongsActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast toast = Toast.makeText(DayActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInternetConnectionUtility() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    Intent intent = new Intent(this, UtilitySongsActivity.class);
                    intent.putExtra("UtilityArray", UtilityArrayList);
                    startActivity(intent);
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Intent intent = new Intent(this, UtilitySongsActivity.class);
                    intent.putExtra("UtilityArray", UtilityArrayList);
                    startActivity(intent);
                }
            } else {
                Toast toast = Toast.makeText(DayActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getItemNext(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public int getItemPrev(int i) {
        return viewPager.getCurrentItem() - i;
    }

    private void setAdvertise() {
        try {

            try {
                String strId = getBannerAd;
                if (MiddlewareInterface.bAdFree)
                    return;
                viewBannerAd = new AdView(this);
                viewBannerAd.setAdSize(AdSize.BANNER);
                viewBannerAd.setAdUnitId(strId);
                viewBannerAd.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                linearad.addView(viewBannerAd);
                viewBannerAd.loadAd(new AdRequest.Builder().build());
                //linear_ad
            } catch (Exception e) {
                // TODO: handle exception
            }
            viewBannerAd.setAdListener(new AdListener() {
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

    private void RefreshAds() {
        try {
            viewBannerAd.loadAd(new AdRequest.Builder().build());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 5) {
            getcurrentdate = MiddlewareInterface.clickDate;
            getCount = db.dGetDate();

            for (int i = 0; i < getCount.size(); i++) {
                if (getCount.get(i).equals(getcurrentdate)) {
                    loopvalue = i;
                    break;
                }
            }
            int startPos = MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getInt(POS_KEY);
            viewPager.setCurrentItem(loopvalue);
            //Todo arun
            refresh.setVisibility(View.GONE);
            refreshText.setVisibility(View.GONE);
        } else {
            int startPos = MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getInt(POS_KEY);
            viewPager.setCurrentItem(startPos);
            refresh.setVisibility(View.GONE);
            refreshText.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        circlePG.setVisibility(View.GONE);

    }

    private void showHelpImage(View downloadhelp) {
        try {
            int lintIcount = MiddlewareInterface.SharedPreferenceUtility.getInstance(getApplicationContext()).getInt(DETAIL_COUNT, 0);
            lintIcount = lintIcount + 1;
            if (lintIcount <= 2) {
                try {
                    downloadhelp.setVisibility(View.VISIBLE);
                } catch (Exception ignored) {
                }
            }
            MiddlewareInterface.SharedPreferenceUtility.getInstance(getApplicationContext()).putInt(DETAIL_COUNT, lintIcount);
        } catch (Exception ignored) {
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 10:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    locationManager.requestLocationUpdates(
//                            LocationManager.NETWORK_PROVIDER,
//                            MIN_TIME_BW_UPDATE,
//                            MIN_DISTANCE_CHANGE_FOR_UPDATE, locationListener);
//                }
//                break;
//        }
//        return;
//    }


//    private void getLocationFromLocationManager() {
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
//
//
//        locationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                getAddress(location);
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//            }
//
//            @Override
//            public void onProviderEnabled(String provider) {
//            }
//
//            @Override
//            public void onProviderDisabled(String provider) {
//                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//            }
//        };
//
//    }

    private void getAddress(Location location) {
        try {
            Double latitude = location.getLatitude();
            Double langnitude = location.getLongitude();

            List<Address> addressList = geocoder.getFromLocation(latitude, langnitude, 1);
            Address address = addressList.get(0);
            //get current location
            sentCity = address.getLocality();
            sentCountryCode = address.getCountryCode();
            sentPostalCode = address.getPostalCode();
            MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.getCityName, sentCity);
            MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.getCountryCode, sentCountryCode);
            MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.getPostalCode, sentPostalCode);
//            new GetWeather().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    private class GetWeather extends android.os.AsyncTask<String, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            try {
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            arrayListad = new ArrayList<>();
//            try {
//                if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
//                    Map<String, String> paramss = new HashMap<String, String>();
//                    Date strdate = new Date();
//                    String crntDate = new SimpleDateFormat("yyyy-M-d").format(strdate);
//                    if (!sentCountryCode.equalsIgnoreCase("") && !crntDate.equalsIgnoreCase("") && !MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getString(MiddlewareInterface.getCityName).equalsIgnoreCase("")) {
//                        paramss.put("bundleid", PACKAGE_NAME);
//                        paramss.put("version", version);
//                        paramss.put("deviceos", "Android");
//                        paramss.put("currentccode", sentCountryCode);
//                        paramss.put("currentdate", crntDate);
//                        paramss.put("currentcity", MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).getString(MiddlewareInterface.getPostalCode));
//                    }
//                    try {
//                        post(adintegrationapi, paramss);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    if (exceptionValue == 200) {
//                        SharedPreferences app_preferences1 = PreferenceManager.getDefaultSharedPreferences(DayActivity.this);
//                        SharedPreferences.Editor editor = app_preferences1.edit();
//                        editor.putString("adresponse", strRet);
//                        editor.apply();
//                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(DayActivity.this);
//                        String adresponse = app_preferences.getString("adresponse", strRet);
//                        JSONObject responsedash = new JSONObject(adresponse);
//                        if (responsedash.has("weatherdata")) {
//                            JSONObject weatherAd = responsedash.getJSONObject("weatherdata");
//                            String wDate = "", wCity = "", wCountrycode = "", wHumidity = "", wUpdatedtime = "", wWinddiretiontext = "", wWinddirectiondegree = "", wDaydatetime = "", wPressure = "", wWindspeed = "", wWinddirection = "", wWinddegree = "", wTemperature = "", wCondition = "", wImage = "";
//                            if (weatherAd.has("city")) {
//                                wCity = weatherAd.getString("city");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strLocation, wCity);
//                            }
//                            if (weatherAd.has("updatedtime")) {
//                                wUpdatedtime = weatherAd.getString("updatedtime");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strUpdatedTime, wUpdatedtime);
//                            }
//                            if (weatherAd.has("daydatetime")) {
//                                wDaydatetime = weatherAd.getString("daydatetime");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strDayDateTime, wDaydatetime);
//                            }
//                            if (weatherAd.has("humidity")) {
//                                wHumidity = weatherAd.getString("humidity");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strHumidity, wHumidity);
//                            }
//                            if (weatherAd.has("pressure")) {
//                                wPressure = weatherAd.getString("pressure");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strPressure, wPressure);
//                            }
//                            if (weatherAd.has("windspeed")) {
//                                wWindspeed = weatherAd.getString("windspeed");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strWind, wWindspeed);
//                            }
//                            if (weatherAd.has("winddirectiontext")) {
//                                wWinddiretiontext = weatherAd.getString("winddirectiontext");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strWindDirection, wWinddiretiontext);
//                            }
//                            if (weatherAd.has("winddirectiondegree")) {
//                                wWinddirectiondegree = weatherAd.getString("winddirectiondegree");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strWindDeg, wWinddirectiondegree);
//                            }
//                            if (weatherAd.has("temperature")) {
//                                try {
//                                    wTemperature = weatherAd.getString("temperature");
//                                    String[] gettemp = wTemperature.split("\\°");
//                                    String splittemp = gettemp[0];
//                                    int convertInt = Integer.parseInt(splittemp);
//                                    int celciuos = (convertInt - 32);
//                                    int celciuos1 = (celciuos * 5);
//                                    String getcelciuos = String.valueOf(celciuos1 / 9) + "°C";
//                                    MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strTemp, getcelciuos);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            if (weatherAd.has("weathertext")) {
//                                wCondition = weatherAd.getString("weathertext");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strCondition, wCondition);
//                            }
//                            if (weatherAd.has("weatherimage")) {
//                                wImage = weatherAd.getString("weatherimage");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strImage, wImage);
//                            }
//
//                            if (weatherAd.has("forecastdata")) {
//                                JSONArray foreCastArr = weatherAd.getJSONArray("forecastdata");
//                                for (int i = 0; i < foreCastArr.length(); i++) {
//                                    try {
//                                        JSONObject forecastObj = foreCastArr.getJSONObject(i);
//
//                                        ArrayList<String> forecastArrList = new ArrayList<>();
//
//                                        String forecastlow = forecastObj.getString("forecastlow");
//
//                                        String forecastdate = forecastObj.getString("forecastdate");
//
//                                        String forecasthigh = forecastObj.getString("forecasthigh");
//
//                                        String forecastday = forecastObj.getString("forecastday");
//
//                                        String forecastimage = forecastObj.getString("forecastimage");
//
//                                        String forecasttext = forecastObj.getString("forecasttext");
//
//                                        if (i == 0) {
//                                            String day1 = forecastlow + "#" + forecastdate + "#" + forecasthigh + "#" + forecastday + "#" + forecastimage + "#" + forecasttext;
//                                            MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strDay1, day1);
//                                        } else if (i == 1) {
//                                            String day2 = forecastlow + "#" + forecastdate + "#" + forecasthigh + "#" + forecastday + "#" + forecastimage + "#" + forecasttext;
//                                            MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strDay2, day2);
//                                        } else if (i == 2) {
//                                            String day3 = forecastlow + "#" + forecastdate + "#" + forecasthigh + "#" + forecastday + "#" + forecastimage + "#" + forecasttext;
//                                            MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strDay3, day3);
//                                        } else if (i == 3) {
//                                            String day4 = forecastlow + "#" + forecastdate + "#" + forecasthigh + "#" + forecastday + "#" + forecastimage + "#" + forecasttext;
//                                            MiddlewareInterface.SharedPreferenceUtility.getInstance(DayActivity.this).putString(MiddlewareInterface.strDay4, day4);
//                                        }
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//                }
//            } catch (Exception e) {
//                Log.e("HttpManager", "ClientProtocolException thrown" + e);
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//                setWeather();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

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


    public void gotoNotesPage() {
        try {
            Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
            startActivityForResult(intent, 11);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        try {

//                exitAlertAction();
            if (MiddlewareInterface.interstitialAds != null) {
                if (MiddlewareInterface.interstitialAds.isLoaded()) {
                    if (!MiddlewareInterface.isBackgroundRunning(getApplicationContext()))
                        MiddlewareInterface.interstitialAds.show();
                }
            }
            finish();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.e("Storage", "Granted");
            } else {
                Log.v("Storage", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }


}





