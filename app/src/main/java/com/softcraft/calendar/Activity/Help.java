package com.softcraft.calendar.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.softcraft.calendar.Adapter.HelpAdapter;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

/**
 * Created by Softcraft Systems on 10/28/2016.
 */
public class Help extends Activity
{
    ListView listView;
    Adapter adapter;
    public String[] textId;
    public Integer[] imageId;
    LinearLayout linearad;
    AdView adview;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    private AdBannerListener adBannerListener;
    private NativeExpressAdView viewNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_list_view);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, DayActivity.class));
        linearad=(LinearLayout)findViewById(R.id.help_adview);
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

        setTitle("");
        TextView tHeader=(TextView) findViewById(R.id.help_header);


        tHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if(MiddlewareInterface.bRendering)
        {
            tHeader.setText("Help");
            tHeader.setTypeface(Typeface.DEFAULT);
        }
        else
        {
            tHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.help_heading)));
            tHeader.setTypeface(MiddlewareInterface.tf_mylai);
        }
        ImageView imgBack=(ImageView)findViewById(R.id.help_img_click);
        imgBack.setColorFilter(Color.WHITE);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textId= new String[]{"கிருத்திகை/Kirthigai","அமாவாசை/Amavasai","பௌர்ணமி/Pournami","விரத நாட்கள் /Fasting Days","சுப முகூர்த்த நாள் /Mugurtham days","மேல்நோக்கு நாள் /Mel Nokku Naal","கீழ்நோக்கு நாள் /Kil Nokku Naal","சமநோக்கு நாள் /Sama Nokku Naal"};
        imageId= new Integer[]{R.drawable.star_img,R.drawable.full_moon_image12,R.drawable.moon_img,R.drawable.fasting_days,R.drawable.subamugurtham_img12,R.drawable.up_arrow,R.drawable.down_arrow,R.drawable.equal_arrow};


        listView=(ListView)findViewById(R.id.help_list);
        listView.setAdapter(new HelpAdapter(this,textId,imageId));
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
                /*arrayListad = MiddlewareInterface.listsaddetails;
                getListAds = arrayListad.get(0);
                String strGoogleAd = getListAds.get(1);*/
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
