package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class NotificationSettings extends AppCompatActivity
{
    LinearLayout linearad;
    AdView adview;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    private AdBannerListener adBannerListener;
    private NativeExpressAdView viewNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
//            setContentView(R.layout.notification_settings);
            setContentView(R.layout.test_notify);
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, DayActivity.class));

            final ImageView backImg=(ImageView)findViewById(R.id.backArrowImg) ;
            CheckBox festivalDayTV=(CheckBox)findViewById(R.id.festivalCheckBox);
            CheckBox priorfestivalCheckBox=(CheckBox)findViewById(R.id.priorfestivalCheckBox);
            CheckBox auspiciousCheckBox=(CheckBox)findViewById(R.id.auspiciousCheckBox);
            CheckBox priorauspiciousCheckBox=(CheckBox)findViewById(R.id.priorauspiciousCheckBox);
            CheckBox kirthigaiCheckBox=(CheckBox)findViewById(R.id.kirthigaiCheckBox);
            CheckBox priorkirthigaiCheckBox=(CheckBox)findViewById(R.id.priorkirthigaiCheckBox);
//            CheckBox amavasaiCheckBox=(CheckBox)findViewById(R.id.amavasaiCheckBox);
//            CheckBox prioramavasaiCheckBox=(CheckBox)findViewById(R.id.prioramavasaiCheckBox);
//            CheckBox pournamiCheckBox=(CheckBox)findViewById(R.id.pournamiCheckBox);
//            CheckBox priorpournamiCheckBox=(CheckBox)findViewById(R.id.priorpournamiCheckBox);
            CheckBox soundCheckBox=(CheckBox)findViewById(R.id.soundCheckBox);
            final TextView tHeader=(TextView) findViewById(R.id.titleTV);

            tHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(NotificationSettings.this, tHeader);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                finish();
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

            linearad=(LinearLayout)findViewById(R.id.notication_adview);
            // setAdvertise();
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD)
            {
                try
                {
                    if(getEnable.equalsIgnoreCase("1"))
                    {
                        if(getBannerType.equalsIgnoreCase("0"))
                        {
                            setAdvertise();
                        }
                        else
                        {
                            setNativeSmall();
                        }
                    }else if (getEnable.equalsIgnoreCase( "4" )) {
                        if (getFacebookBannerType.equalsIgnoreCase( "1" )) {
                            MiddlewareInterface.setNativeFBAD(this,linearad);
                        } else {
                            MiddlewareInterface.setBannerFBAD(this,linearad);
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

/*
            if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            {
                try {
                    ArrayList<List<String>> arrayListadAll = MiddlewareInterface.listsaddetails;
                    List<String> checkGoAdsList;
                    List<String> checkInAdsList;
                    checkGoAdsList = arrayListadAll.get(0);
                    checkInAdsList = arrayListadAll.get(1);
                    String checkGads = checkGoAdsList.get(0);
                    String checkIads = checkInAdsList.get(0);
                    if (checkGads.equalsIgnoreCase("1"))
                        setAdvertise();
                    else if (checkIads.equalsIgnoreCase("1"))
                        setAdvertiseInmobi();
                    else
                        setAdvertise();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
*/

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            final SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("festivalDay", true);
            editor.putBoolean("priorfestivalDay", true);
            editor.putBoolean("auspicious", true);
            editor.putBoolean("priorauspicious", true);
            editor.putBoolean("kirthigai", true);
            editor.putBoolean("priorkirthigai", true);
            editor.putBoolean("amavasai", true);
            editor.putBoolean("prioramavasai", true);
            editor.putBoolean("pournami", true);
            editor.putBoolean("priorpournami", true);
            editor.putBoolean("notificationsound", true);
            editor.apply();

            backImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(NotificationSettings.this, backImg);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                finish();
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

//festival
            if (festivalDayTV != null) {
                festivalDayTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked())
                            editor.putBoolean("festivalDay", true);
                        else
                            editor.putBoolean("festivalDay", false);

                        editor.apply();
                    }
                });
            }
            if (priorfestivalCheckBox != null) {
                priorfestivalCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked())
                            editor.putBoolean("priorfestivalDay", true);
                        else
                            editor.putBoolean("priorfestivalDay", false);

                        editor.apply();
                    }
                });
            }
//auspicious
            if (auspiciousCheckBox != null) {
                auspiciousCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked())
                            editor.putBoolean("auspicious", true);
                        else
                            editor.putBoolean("auspicious", false);

                        editor.apply();
                    }
                });
            }
            if (priorauspiciousCheckBox != null) {
                priorauspiciousCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked())
                            editor.putBoolean("priorauspicious", true);
                        else
                            editor.putBoolean("priorauspicious", false);

                        editor.apply();
                    }
                });
            }
//kirthigai
            if (kirthigaiCheckBox != null) {
                kirthigaiCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked())
                            editor.putBoolean("kirthigai", true);
                        else
                            editor.putBoolean("kirthigai", false);

                        editor.apply();
                    }
                });
            }
            if (priorkirthigaiCheckBox != null) {
                priorkirthigaiCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked())
                            editor.putBoolean("priorkirthigai", true);
                        else
                            editor.putBoolean("priorkirthigai", false);

                        editor.apply();
                    }
                });
            }
//amavasai
//            if (amavasaiCheckBox != null) {
//                amavasaiCheckBox.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (((CheckBox) v).isChecked())
//                            editor.putBoolean("amavasai", true);
//                        else
//                            editor.putBoolean("amavasai", false);
//
//                        editor.apply();
//                    }
//                });
//            }
//            if (prioramavasaiCheckBox != null) {
//                prioramavasaiCheckBox.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (((CheckBox) v).isChecked())
//                            editor.putBoolean("prioramavasai", true);
//                        else
//                            editor.putBoolean("prioramavasai", false);
//
//                        editor.apply();
//                    }
//                });
//            }
////pournami
//            if (pournamiCheckBox != null) {
//                pournamiCheckBox.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (((CheckBox) v).isChecked())
//                            editor.putBoolean("pournami", true);
//                        else
//                            editor.putBoolean("pournami", false);
//
//                        editor.apply();
//                    }
//                });
//            }
//            if (priorpournamiCheckBox != null) {
//                priorpournamiCheckBox.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (((CheckBox) v).isChecked())
//                            editor.putBoolean("priorpournami", true);
//                        else
//                            editor.putBoolean("priorpournami", false);
//
//                        editor.apply();
//                    }
//                });
//            }
            if (soundCheckBox != null) {
                soundCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((CheckBox) v).isChecked())
                            editor.putBoolean("notificationsound", true);
                        else
                            editor.putBoolean("notificationsound", false);

                        editor.apply();
                    }
                });
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setNativeSmall()
    {
        try{
            try{
                String strId = getNativeSmall;
                if(MiddlewareInterface.bAdFree)
                    return;
                viewNativeAd=new NativeExpressAdView(this) ;
                viewNativeAd.setAdSize(AdSize.LARGE_BANNER);
                viewNativeAd.setAdUnitId(strId);
                viewNativeAd.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearad.addView(viewNativeAd);
                AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
                viewNativeAd.loadAd(adRequestBuilder.build());
            }catch (Exception e) {
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
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void setAdvertise()
    {
        try{

            try{
                String strGoogleAd = getBannerAd;
                if(MiddlewareInterface.bAdFree)
                    return;

                adview= new AdView(this);
                adview.setAdSize(AdSize.BANNER);
                adview.setAdUnitId(strGoogleAd);
                adview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                linearad.addView(adview);
                adview.loadAd(new AdRequest.Builder().build());
                //linear_ad
            }catch (Exception e) {
                // TODO: handle exception
            }
            adview.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // TODO Auto-generated method stub
                    Log.d("On Fail called","Ad");
                    linearad.setVisibility(View.GONE);
                    super.onAdFailedToLoad(errorCode);
                }
                @Override
                public void onAdLoaded() {
                    // TODO Auto-generated method stub
                    Log.d("On Load called","Ad");
                    linearad.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                    //RefreshAds();
                }
            });

        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    private void setAdvertiseInmobi()
    {
        try{

            try{
                arrayListad = MiddlewareInterface.listsaddetails;
                getListAds = arrayListad.get(1);
                String strInmobiAds = getListAds.get(1);
                if(MiddlewareInterface.bAdFree)
                    return;

                bannerAdView =new IMBanner(this, null);
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

            }catch (Exception e) {
                e.printStackTrace();
                // TODO: handle exception
            }

        }catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (MiddlewareInterface.interstitialAds != null) {
                if (MiddlewareInterface.interstitialAds.isLoaded()) {
                    if (!MiddlewareInterface.isBackgroundRunning(getApplicationContext()))
                        MiddlewareInterface.interstitialAds.show();
                }
            }
            super.onBackPressed();
        }catch (Exception e){
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

}
