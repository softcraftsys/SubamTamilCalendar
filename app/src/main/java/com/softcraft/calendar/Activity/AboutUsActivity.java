package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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

public class AboutUsActivity extends AppCompatActivity
{
    private TextView mText,tContent,textViewweb;
    Context mContext;
    Intent intent;
    LinearLayout linearad;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    AdView adview;
    Button btnRate,btnMail,btnShare,btnApp;
    private AdBannerListener adBannerListener;
    MiddlewareInterface AMI = MiddlewareInterface.GetInstance();
    private NativeExpressAdView viewNativeAd;
    String version = "";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        try {
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }

            mText = (TextView) findViewById(R.id.mail);
            textViewweb = (TextView) findViewById(R.id.websiteid);
            tContent = (TextView) findViewById(R.id.contents);
            btnRate = (Button) findViewById(R.id.btnrate);
            btnMail = (Button) findViewById(R.id.maildeveloper);
            btnShare = (Button) findViewById(R.id.facebook);
            btnApp = (Button) findViewById(R.id.ourapp);

            try {
                TextView versionTv = (TextView) findViewById(R.id.supporttxt2);
                try {
                    PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
                    version = pInfo.versionName;
                    versionTv.setText("Version " + version);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            btnRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(AboutUsActivity.this, btnRate);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.softcraft.calendar"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent);
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
            btnMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(AboutUsActivity.this, btnMail);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                //rate the app
                                String[] mailto = {"support@softcraftsystems.com"};
//                String[] mailto = { "softcraftsystems@gmail.com" };
                                String PhoneModel = Build.MODEL;
                                String AndroidVersion = Build.VERSION.RELEASE;
                                String strInfo = "I am using Subam Tamil Calendar - " + version + " on an " + PhoneModel + " running android version " + AndroidVersion + "<br><br><br>";
                                final Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                                sendIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
                                sendIntent.putExtra(Intent.EXTRA_SUBJECT,
                                        "Feedback:Subam Tamil Calendar - " + version + "!");
                                sendIntent.putExtra(Intent.EXTRA_TEXT,
                                        Html.fromHtml(strInfo));
                                startActivity(Intent.createChooser(sendIntent, "Subam Tamil Calendar"));
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
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(AboutUsActivity.this, btnShare);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                Intent sendIntentt = new Intent();
                                sendIntentt.setAction(Intent.ACTION_SEND);
                                sendIntentt.putExtra(Intent.EXTRA_TEXT, "To Download Subam Tamil Calendar app https://play.google.com/store/apps/details?id=com.softcraft.calendar");
                                sendIntentt.setType("text/plain");
                                Intent intentpass = Intent.createChooser(sendIntentt, "Share Subam Tamil Calendar");
                                intentpass.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intentpass);
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
            btnApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(AboutUsActivity.this, btnApp);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:SOFTCRAFT"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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
            linearad = (LinearLayout) findViewById(R.id.adview);
            // setAdvertise();

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
            final TextView tHeader = (TextView) findViewById(R.id.show_title_aboutus);
            TextView tCalendarHeading = (TextView) findViewById(R.id.supporttxt1);

            try {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
                tHeader.startAnimation(animation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(AboutUsActivity.this, tHeader);
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


            if (MiddlewareInterface.bRendering) {
                tHeader.setText((getResources().getString(R.string.aboutus)));
                tContent.setText((getResources().getString(R.string.about_tamil_calendar)));
                tCalendarHeading.setText((getResources().getString(R.string.app_name_aboutus)));
                tHeader.setTypeface(Typeface.DEFAULT);
                tContent.setTypeface(Typeface.DEFAULT);
                tCalendarHeading.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                tHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.aboutus)));
                tContent.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.about_tamil_calendar)));
                tCalendarHeading.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.app_name_aboutus)));
                tHeader.setTypeface(MiddlewareInterface.tf_mylai);
                tContent.setTypeface(MiddlewareInterface.tf_mylai);
                tCalendarHeading.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);
            }
            final ImageView imgBack = (ImageView) findViewById(R.id.about_imgback);
            imgBack.setColorFilter(Color.WHITE);
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(AboutUsActivity.this, imgBack);
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
            mContext = this;
            mText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(AboutUsActivity.this, mText);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                String[] mailto = {"softcraftsystems@gmail.com"};
                                final Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"));
                                sendIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
                                sendIntent.putExtra(Intent.EXTRA_SUBJECT,
                                        "Subam Tamil Calendar 1.5");

                                sendIntent.putExtra(Intent.EXTRA_TEXT,
                                        Html.fromHtml(""));
                                startActivity(Intent.createChooser(sendIntent, "Subam Tamil Calendar 1.7"));
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
            textViewweb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(AboutUsActivity.this, textViewweb);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                String strHome = "http://www.softcraftsystems.com/index.php";
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(strHome));
                                startActivity(intent);
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
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




