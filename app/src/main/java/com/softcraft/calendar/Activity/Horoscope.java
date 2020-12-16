package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
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
import com.softcraft.calendar.Adapter.CustomGrid;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class Horoscope extends Activity
{
    GridView grid;
     CustomGrid adapter;
    TextView tMonthView,tYearView;
    LinearLayout linearad;
    AdView adview;
    IMBanner bannerAdView;
    private AdBannerListener adBannerListener;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    String[] web;
    String strSelectionView;
    int[] imageId = {
            R.drawable.mesham,
            R.drawable.rishabam,
            R.drawable.methunam,
            R.drawable.kadagam,
            R.drawable.simmam,
            R.drawable.kanni,
            R.drawable.thulaam,
            R.drawable.viruchigam,
            R.drawable.dhanusu,
            R.drawable.magaram,
            R.drawable.kumbam,
            R.drawable.meenam
    };
    private NativeExpressAdView viewNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rasipalan);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
        try {

            MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        web=getResources().getStringArray(R.array.horoscope_title);
        linearad=(LinearLayout)findViewById(R.id.rasipalan_adview);
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
        final ImageView backImg=(ImageView)findViewById(R.id.rasipalan_backArrowImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(Horoscope.this, backImg);
                zoomAnimation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        try {
//                            finish();
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
        final TextView tRasipalanHeader=(TextView)findViewById(R.id.rasipalan_header);
        try {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
            tRasipalanHeader.startAnimation(animation);
        } catch (Exception e) {
            e.printStackTrace();
        }


        tRasipalanHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(Horoscope.this, tRasipalanHeader);
                zoomAnimation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        try {
//                            finish();
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
        //get value from intent
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            strSelectionView = extras.getString("isClick");
        }
        if(strSelectionView!=null)
        {
            if(strSelectionView.equalsIgnoreCase("Day"))
            {
            if (MiddlewareInterface.bRendering) {
                tRasipalanHeader.setText(getResources().getString(R.string.today_horoscope));
                tRasipalanHeader.setTypeface(Typeface.DEFAULT);
            } else {
                tRasipalanHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.today_horoscope)));
                tRasipalanHeader.setTypeface(MiddlewareInterface.tf_mylai);
            }
        }
        else if(strSelectionView.equalsIgnoreCase("Month"))
        {
            if (MiddlewareInterface.bRendering)
            {
                tRasipalanHeader.setText(getResources().getString(R.string.mathapalan));
                tRasipalanHeader.setTypeface(Typeface.DEFAULT);
            }
            else
            {
                tRasipalanHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.mathapalan)));
                tRasipalanHeader.setTypeface(MiddlewareInterface.tf_mylai);
            }
        }
        else if(strSelectionView.equalsIgnoreCase("Year"))
        {
            if (MiddlewareInterface.bRendering)
            {
                tRasipalanHeader.setText(getResources().getString(R.string.yearpalan));
                tRasipalanHeader.setTypeface(Typeface.DEFAULT);
            }
            else
            {
                tRasipalanHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.yearpalan)));
                tRasipalanHeader.setTypeface(MiddlewareInterface.tf_mylai);
            }
        }
        }


        adapter = new CustomGrid(Horoscope.this, web, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
            {
                try
                {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(Horoscope.this, view);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }
                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext()))
                                {
//                Toast.makeText(Horoscope.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                                    adapter.toggleSelection(position);
                                    Intent intent = new Intent(Horoscope.this, HoroscopeURL.class);
                                    intent.putExtra("gridPosition", position);
                                    intent.putExtra("Check", strSelectionView);
                                    startActivity(intent);
                                }
                                else
                                {
//                        Toast.makeText(Horoscope.this, "No internet connections...", Toast.LENGTH_SHORT).show();
                                    adapter.toggleSelection(position);
                                    Intent intent = new Intent(Horoscope.this, HoroscopeURL.class);
                                    intent.putExtra("gridPosition", position);
                                    intent.putExtra("Check", strSelectionView);
                                    startActivity(intent);
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
                catch (Exception e)
                {
                    e.printStackTrace();
                }


            }
        });

        GotoDeeplinking();

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
    @Override
    protected void onResume() {
        super.onResume();
        adapter.removeSelection();
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
               String strId= getBannerAd;
                if(MiddlewareInterface.bAdFree)
                    return;
                adview= new AdView(this);
                adview.setAdSize(AdSize.BANNER);
                adview.setAdUnitId(strId);
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

    private  void GotoDeeplinking(){
        try{
            String strRasipalanId = getIntent().getStringExtra("rasipalanId");
            int position = Integer.parseInt(strRasipalanId);
            try {
                if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext()))
                {
//                Toast.makeText(Horoscope.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                    adapter.toggleSelection(position);
                    Intent intent = new Intent(Horoscope.this, HoroscopeURL.class);
                    intent.putExtra("gridPosition", position);
                    intent.putExtra("Check", strSelectionView);
                    startActivity(intent);
                }
                else
                {
//                        Toast.makeText(Horoscope.this, "No internet connections...", Toast.LENGTH_SHORT).show();
                    adapter.toggleSelection(position);
                    Intent intent = new Intent(Horoscope.this, HoroscopeURL.class);
                    intent.putExtra("gridPosition", position);
                    intent.putExtra("Check", strSelectionView);
                    startActivity(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
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