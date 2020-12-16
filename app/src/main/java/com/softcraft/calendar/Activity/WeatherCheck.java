package com.softcraft.calendar.Activity;

import android.Manifest;
import android.animation.Animator;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;
import com.softcraft.calendar.BuildConfig;
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

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
import java.util.Map;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

/*import github.vatsal.easyweather.Helper.ForecastCallback;
import github.vatsal.easyweather.Helper.TempUnitConverter;
import github.vatsal.easyweather.Helper.WeatherCallback;
import github.vatsal.easyweather.WeatherMap;
import github.vatsal.easyweather.retrofit.models.ForecastResponseModel;
import github.vatsal.easyweather.retrofit.models.WeatherResponseModel;*/

public class
WeatherCheck extends AppCompatActivity {
    public final String APP_ID = BuildConfig.OWM_API_KEY;
    String city = null;
    View circlePG;
    LinearLayout linearad;
    AdView adview;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    private AdBannerListener adBannerListener;
    private NativeExpressAdView viewNativeAd;
    private LocationManager locationManager;//get location details
    private LocationListener locationListener;
    private Geocoder geocoder;
    String version, sentCountryCode, sentCity, sentDate, sentPostalCode;
    String adintegrationapi = "http://adsenseusers.com/scsadcontrol/api/device4/appsadhandler/format/json";
    public static String PACKAGE_NAME;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 1;
    private static final long MIN_TIME_BW_UPDATE = 10 * 60 * 1;
    static int exceptionValue;
    static String strRet = "";
    TextView tvLocation, tvTemperature, tvCondition, tvDateAndDay, tvHumidity, tvWind, tvTitleHeader, tvWindDeg, tvPressure, tvLastUpdatedTimeTv;
    ImageView ivBackArrowImg, ivWeatherImg, ivHumidityIcon, ivWindIcon, ivUpdateWeather;
    DataBaseHelper db;
    PackageInfo pInfo = null;
    CoordinatorLayout mainLayout;
    RelativeLayout rootLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, DayActivity.class));
        setContentView(R.layout.weather_check);
        try {

            try {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            circlePG = findViewById(R.id.circlePG_weather);
            tvLocation = (TextView) findViewById(R.id.LocationTv);
            tvTemperature = (TextView) findViewById(R.id.temperatureTv);
            tvCondition = (TextView) findViewById(R.id.weather_condition);
            tvDateAndDay = (TextView) findViewById(R.id.dateAndDay_TV);
            tvHumidity = (TextView) findViewById(R.id.humidity_result_tv);
            tvWind = (TextView) findViewById(R.id.wind_result_tv);
            tvTitleHeader = (TextView) findViewById(R.id.weather_header);
            tvWindDeg = (TextView) findViewById(R.id.windDeg_result_tv);
            tvPressure = (TextView) findViewById(R.id.pressure_result_tv);
            tvLastUpdatedTimeTv = (TextView) findViewById(R.id.lastUpdatedTimeTv);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);



            ivBackArrowImg = (ImageView) findViewById(R.id.weather_backArrowImg);
            ivWeatherImg = (ImageView) findViewById(R.id.weather_Img);
            ivHumidityIcon = (ImageView) findViewById(R.id.humidity_icon_Img);
            ivWindIcon = (ImageView) findViewById(R.id.wind_icon_Img);
            ivUpdateWeather = (ImageView) findViewById(R.id.updateWeatherImg);
            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

//            GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(ivWindIcon);
//            Glide.with(this).load(R.drawable.wind_turbine_gif).asGif().into(ivWindIcon);

            mainLayout = (CoordinatorLayout) findViewById(R.id.coOrdinatorLaout);

            geocoder = new Geocoder(this);
            try {
                PACKAGE_NAME = getApplicationContext().getPackageName();
                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                version = pInfo.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            db = new DataBaseHelper(getApplicationContext());

            tvTitleHeader.setOnClickListener(clickListener);
            ivBackArrowImg.setOnClickListener(clickListener);
            ivUpdateWeather.setOnClickListener(clickListener);

            if (!MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strLocation).equalsIgnoreCase("")) {
                mainLayout.setVisibility(View.VISIBLE);
                onInitializeItemsFunc();
            } else {
                getLocationFromLocationManager();
            }
            ShareFunc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void forecastInflateFunc() {
        try {
            try {
                String[] forecastArr = null;

                // forecase fays = 4;
                for (int i = 0; i < 4; i++) {
                    if (i == 0) {
                        forecastArr = MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strDay1).split("#");

                        TextView tvCond = (TextView) findViewById(R.id.nextDayConditionTv_1);
                        TextView tvTempr = (TextView) findViewById(R.id.nextDayTempTv_1);
                        TextView tvDay = (TextView) findViewById(R.id.nextDayTv_1);
                        ImageView ivWeatherIc = (ImageView) findViewById(R.id.nextDayImg_1);

                        setValueForForcastFunc(tvDay, tvCond, tvTempr, ivWeatherIc, forecastArr);

                    } else if (i == 1) {
                        forecastArr = MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strDay2).split("#");
                        TextView tvCond2 = (TextView) findViewById(R.id.nextDayConditionTv_2);
                        TextView tvTempr2 = (TextView) findViewById(R.id.nextDayTempTv_2);
                        TextView tvDay2 = (TextView) findViewById(R.id.nextDayTv_2);
                        ImageView ivWeatherIc2 = (ImageView) findViewById(R.id.nextDayImg_2);

                        setValueForForcastFunc(tvDay2, tvCond2, tvTempr2, ivWeatherIc2, forecastArr);

                    } else if (i == 2) {
                        forecastArr = MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strDay3).split("#");

                        TextView tvCond3 = (TextView) findViewById(R.id.nextDayConditionTv_3);
                        TextView tvTempr3 = (TextView) findViewById(R.id.nextDayTempTv_3);
                        TextView tvDay3 = (TextView) findViewById(R.id.nextDayTv_3);
                        ImageView ivWeatherIc3 = (ImageView) findViewById(R.id.nextDayImg_3);

                        setValueForForcastFunc(tvDay3, tvCond3, tvTempr3, ivWeatherIc3, forecastArr);

                    } else if (i == 3) {
                        forecastArr = MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strDay4).split("#");

                        TextView tvCond4 = (TextView) findViewById(R.id.nextDayConditionTv_4);
                        TextView tvTempr4 = (TextView) findViewById(R.id.nextDayTempTv_4);
                        TextView tvDay4 = (TextView) findViewById(R.id.nextDayTv_4);
                        ImageView ivWeatherIc4 = (ImageView) findViewById(R.id.nextDayImg_4);

                        setValueForForcastFunc(tvDay4, tvCond4, tvTempr4, ivWeatherIc4, forecastArr);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValueForForcastFunc(TextView tvDay, TextView tvCond, TextView tvTempr, ImageView ivWeatherIc, String[] forecastArr) {
        try {
            tvDay.setText(forecastArr[3]);
            tvCond.setText(forecastArr[5]);
            try {
                String[] gettemp = forecastArr[0].split("\\°");
                String splittemp = gettemp[0];
                int convertInt = Integer.parseInt(splittemp);
                int celciuos = (convertInt - 32);
                int celciuos1 = (celciuos * 5);
                String getcelciuos = String.valueOf(celciuos1 / 9) + "°C";

                String[] gettemp1 = forecastArr[0].split("\\°");
                String splittemp1 = gettemp1[0];
                int convertInt1 = Integer.parseInt(splittemp1);
                int celciuos11 = (convertInt1 - 32);
                int celciuos12 = (celciuos11 * 5);
                String getcelciuos1 = String.valueOf(celciuos12 / 9) + "°C";
                tvTempr.setText(getcelciuos + " - " + getcelciuos1);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Glide.with(this)
                        .load(forecastArr[4])
                        .into(ivWeatherIc);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onInitializeItemsFunc() {
        try {
            try {
                if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
                    onPageFinished();

                    tvLocation.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strLocation));
                    tvCondition.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strCondition));
                    tvTemperature.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strTemp));
                    tvHumidity.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strHumidity));
                    tvPressure.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strPressure));
                    tvWind.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strWind));
                    tvWindDeg.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strWindDeg));
                    tvDateAndDay.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strDayDateTime));
                    tvLastUpdatedTimeTv.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strUpdatedTime));

                    String strWeatherImgURl = MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strImage);
                    try {
                        Glide.with(this)
                                .load(strWeatherImgURl)
                                .into(ivWeatherImg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    onPageFinished();

                    tvLocation.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strLocation));
                    tvCondition.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strCondition));
                    tvTemperature.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strTemp));
                    tvHumidity.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strHumidity));
                    tvPressure.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strPressure));
                    tvWind.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strWind));
                    tvWindDeg.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strWindDeg));
                    tvDateAndDay.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strDayDateTime));
                    tvLastUpdatedTimeTv.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strUpdatedTime));

                    String strWeatherImgURl = MiddlewareInterface.SharedPreferenceUtility.getInstance(this).getString(MiddlewareInterface.strImage);
                    try {
                        Glide.with(this)
                                .load(strWeatherImgURl)
                                .into(ivWeatherImg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            linearad = (LinearLayout) findViewById(R.id.weather_adview);
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

            if (MiddlewareInterface.bRendering) {
                tvTitleHeader.setText((getResources().getString(R.string.weather)));
                tvTitleHeader.setTypeface(Typeface.DEFAULT);
            } else {
                tvTitleHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.weather)));
                tvTitleHeader.setTypeface(MiddlewareInterface.tf_mylai);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        forecastInflateFunc();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            try {
                Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(WeatherCheck.this, tvTitleHeader);
                zoomAnimation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        try {
                            if (view.getId() == R.id.updateWeatherImg) {
                                getLocationFromLocationManager();
                            } else {
                                onBackPressed();
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
    };


    public void onPageFinished() {
        try {
            if (circlePG.getVisibility() == View.VISIBLE) {
                circlePG.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdvertise() {
        try {
            if (linearad != null) {
                linearad.removeAllViews();
            }
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

    private void setAdvertiseInmobi() {
        try {

            try {
                arrayListad = MiddlewareInterface.listsaddetails;
                getListAds = arrayListad.get(1);
                String strInmobiAds = getListAds.get(1);
                if (MiddlewareInterface.bAdFree)
                    return;

                bannerAdView = new IMBanner(this, null);
                InMobi.initialize(this, strInmobiAds);
                bannerAdView.setAppId(strInmobiAds);

                LinearLayout.LayoutParams adParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                adParams.gravity = Gravity.CENTER_HORIZONTAL;
                bannerAdView.setLayoutParams(adParams);

                adBannerListener = new AdBannerListener();
                bannerAdView.setIMBannerListener(adBannerListener);
                linearad.addView(bannerAdView);
                bannerAdView.loadBanner();

            } catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }

        } catch (Exception e) {
            // TODO: handle exception
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


    public void getLocationFromLocationManager() {
        circlePG.setVisibility(View.VISIBLE);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, (LocationListener) this);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                getAddress(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        };

    }

    private void getAddress(Location location) {
        try {
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address address = addressList.get(0);
            //get current location
            sentCity = address.getLocality();
            sentCountryCode = address.getCountryCode();
            sentPostalCode = address.getPostalCode();
            MiddlewareInterface.SharedPreferenceUtility.getInstance(getApplicationContext()).putString(MiddlewareInterface.getCityName, sentCity);
            MiddlewareInterface.SharedPreferenceUtility.getInstance(getApplicationContext()).putString(MiddlewareInterface.getCountryCode, sentCountryCode);
            MiddlewareInterface.SharedPreferenceUtility.getInstance(getApplicationContext()).putString(MiddlewareInterface.getPostalCode, sentPostalCode);
            new GetWeather().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class GetWeather extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            arrayListad = new ArrayList<>();
            try {
                if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
//                    SharedPreferences app_preferences2 = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
//                    String requestYear = app_preferences2.getString("currentyear", sentYear);
                    Map<String, String> paramss = new HashMap<String, String>();
                    Date strdate = new Date();
                    String crntDate = new SimpleDateFormat("yyyy-M-d").format(strdate);
                    if (!sentCountryCode.equalsIgnoreCase("") && !crntDate.equalsIgnoreCase("") && !MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).getString(MiddlewareInterface.getCityName).equalsIgnoreCase("")) {
                        paramss.put("bundleid", PACKAGE_NAME);
                        paramss.put("version", version);
                        paramss.put("deviceos", "Android");
                        paramss.put("currentccode", sentCountryCode);
                        paramss.put("currentdate", crntDate);
                        paramss.put("currentcity", MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).getString(MiddlewareInterface.getPostalCode));
                    }
                    try {
                        postWeather(adintegrationapi, paramss);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (exceptionValue == 200) {
                        SharedPreferences app_preferences1 = PreferenceManager.getDefaultSharedPreferences(WeatherCheck.this);
                        SharedPreferences.Editor editor = app_preferences1.edit();
                        editor.putString("adresponse", strRet);
                        editor.apply();
                        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(WeatherCheck.this);
                        String adresponse = app_preferences.getString("adresponse", strRet);
                        JSONObject responsedash = new JSONObject(adresponse);
                        if (responsedash.has("weatherdata")) {
                            JSONObject weatherAd = responsedash.getJSONObject("weatherdata");
                            String wDate = "", wCity = "", wCountrycode = "", wHumidity = "", wUpdatedtime = "", wWinddiretiontext = "", wWinddirectiondegree = "", wDaydatetime = "", wPressure = "", wWindspeed = "", wWinddirection = "", wWinddegree = "", wTemperature = "", wCondition = "", wImage = "";
                            if (weatherAd.has("city")) {
                                wCity = weatherAd.getString("city");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strLocation, wCity);
//                            MiddlewareInterface.strLocation=wCity;
                            }
                            if (weatherAd.has("updatedtime")) {
                                wUpdatedtime = weatherAd.getString("updatedtime");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strUpdatedTime, wUpdatedtime);
//                            MiddlewareInterface.strHumidity=wHumidity;
                            }
                            if (weatherAd.has("daydatetime")) {
                                wDaydatetime = weatherAd.getString("daydatetime");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strDayDateTime, wDaydatetime);
//                            MiddlewareInterface.strHumidity=wHumidity;
                            }
                            if (weatherAd.has("humidity")) {
                                wHumidity = weatherAd.getString("humidity");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strHumidity, wHumidity);
//                            MiddlewareInterface.strHumidity=wHumidity;
                            }
                            if (weatherAd.has("pressure")) {
                                wPressure = weatherAd.getString("pressure");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strPressure, wPressure);
//                            MiddlewareInterface.strPressure=wPressure;
                            }
                            if (weatherAd.has("windspeed")) {
                                wWindspeed = weatherAd.getString("windspeed");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strWind, wWindspeed);
//                            MiddlewareInterface.strPressure=wWindspeed;
                            }
//                            if (weatherAd.has("winddegree")) {
//                                wWinddegree = weatherAd.getString("winddegree");
//                                MiddlewareInterface.SharedPreferenceUtility.getInstance(SplashScreen.this).putString(MiddlewareInterface.strWindDeg, wWinddegree);
////                            MiddlewareInterface.strWindDeg=wWinddegree;
//                            }
                            if (weatherAd.has("winddirectiontext")) {
                                wWinddiretiontext = weatherAd.getString("winddirectiontext");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strWindDirection, wWinddiretiontext);
//                            MiddlewareInterface.strWindDeg=wWinddegree;
                            }
                            if (weatherAd.has("winddirectiondegree")) {
                                wWinddirectiondegree = weatherAd.getString("winddirectiondegree");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strWindDeg, wWinddirectiondegree);
//                            MiddlewareInterface.strWindDeg=wWinddegree;
                            }
                            if (weatherAd.has("temperature")) {
                                try {
                                    wTemperature = weatherAd.getString("temperature");

//                                    String[] gettemp=wTemperature.split("//°");
//                                    String[] gettempdd=wTemperature.split("°");
                                    String[] gettemp = wTemperature.split("\\°");
                                    String splittemp = gettemp[0];
                                    int convertInt = Integer.parseInt(splittemp);
                                    int celciuos = (convertInt - 32);
                                    int celciuos1 = (celciuos * 5);
                                    String getcelciuos = String.valueOf(celciuos1 / 9) + "°C";
                                    MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strTemp, getcelciuos);
//                            MiddlewareInterface.strTemp=wTemperature;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (weatherAd.has("weathertext")) {
                                wCondition = weatherAd.getString("weathertext");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strCondition, wCondition);
//                            MiddlewareInterface.strCondition=wCondition;
                            }
                            if (weatherAd.has("weatherimage")) {
                                wImage = weatherAd.getString("weatherimage");
                                MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strImage, wImage);
//                            MiddlewareInterface.strImage=wImage;
                            }
                            if (weatherAd.has("forecastdata")) {
                                JSONArray foreCastArr = weatherAd.getJSONArray("forecastdata");
                                for (int i = 0; i < foreCastArr.length(); i++) {
                                    try {
                                        JSONObject forecastObj = foreCastArr.getJSONObject(i);

                                        ArrayList<String> forecastArrList = new ArrayList<>();

                                        String forecastlow = forecastObj.getString("forecastlow");

                                        String forecastdate = forecastObj.getString("forecastdate");

                                        String forecasthigh = forecastObj.getString("forecasthigh");

                                        String forecastday = forecastObj.getString("forecastday");

                                        String forecastimage = forecastObj.getString("forecastimage");

                                        String forecasttext = forecastObj.getString("forecasttext");

                                        if (i == 0) {
                                            String day1 = forecastlow + "#" + forecastdate + "#" + forecasthigh + "#" + forecastday + "#" + forecastimage + "#" + forecasttext;
                                            MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strDay1, day1);
                                        } else if (i == 1) {
                                            String day2 = forecastlow + "#" + forecastdate + "#" + forecasthigh + "#" + forecastday + "#" + forecastimage + "#" + forecasttext;
                                            MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strDay2, day2);
                                        } else if (i == 2) {
                                            String day3 = forecastlow + "#" + forecastdate + "#" + forecasthigh + "#" + forecastday + "#" + forecastimage + "#" + forecasttext;
                                            MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strDay3, day3);
                                        } else if (i == 3) {
                                            String day4 = forecastlow + "#" + forecastdate + "#" + forecasthigh + "#" + forecastday + "#" + forecastimage + "#" + forecasttext;
                                            MiddlewareInterface.SharedPreferenceUtility.getInstance(WeatherCheck.this).putString(MiddlewareInterface.strDay4, day4);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
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
                circlePG.setVisibility(View.GONE);
                onInitializeItemsFunc();
                mainLayout.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void postWeather(String endpoint, Map<String, String> params) throws IOException {
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

    private void ShareFunc(){
        try{
            ImageView ShareImg = (ImageView) findViewById(R.id.shareImg);
            ShareImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(getApplicationContext(), view);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    String strContent = "Babynames";
                                    TakeScreenShot(WeatherCheck.this,rootLayout,0,13,"0","வானிலை",progressBar);
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
