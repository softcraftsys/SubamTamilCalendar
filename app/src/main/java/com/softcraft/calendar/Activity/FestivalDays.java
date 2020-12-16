package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Adapter.FestivalDaysAdapter;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class FestivalDays extends AppCompatActivity {
    Context context;
    //    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static DataBaseHelper db;
    int iColor = MiddlewareInterface.currentSelectedColor;
    LinearLayout linearad;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    AdView adview;
    private AdBannerListener adBannerListener;
    private NativeExpressAdView viewNativeAd;
    RelativeLayout rootLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle_view);
        try {

            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, DayActivity.class));
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            final ImageView imgBack = (ImageView) findViewById(R.id.click_back_image);
            //ad code insert
            linearad = (LinearLayout) findViewById(R.id.adview);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            TextView headingTV = (TextView) findViewById(R.id.headingTV);
            headingTV.setText("பண்டிகை");
       /* if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
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
        }*/
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
            imgBack.setColorFilter(Color.WHITE);
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(FestivalDays.this, imgBack);
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
            setTitle(null);
            final TextView tHeader = (TextView) findViewById(R.id.show_title);

            try {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
                tHeader.startAnimation(animation);
            } catch (Exception e) {
                e.printStackTrace();
            }

            tHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(FestivalDays.this, tHeader);
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
                tHeader.setText((getResources().getString(R.string.festival_day)));
                tHeader.setTypeface(Typeface.DEFAULT);
            } else {
                tHeader.setText(UnicodeUtil.unicode2tsc(
                        getResources().getString(R.string.festival_day)));
                tHeader.setTypeface(MiddlewareInterface.tf_mylai);
            }
            try {
                db = new DataBaseHelper(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

            InitMonthBarItems();

//        ArrayList<ArrayList<String>> getFestivalDays =  db.dGetYearFestivals();
//        mAdapter = new FestivalDaysAdapter(context,getFestivalDays);
//        mRecyclerView.setAdapter(mAdapter);
            ShareFunc();
        } catch (Exception e) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
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
                /*arrayListad = MiddlewareInterface.listsaddetails;
                getListAds = arrayListad.get(0);
                String strGoogleAd = getListAds.get(1);*/
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
        } catch (Exception e) {
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
                                    TakeScreenShot(FestivalDays.this, rootLayout, 0, 3, "0", "பண்டிகை நாட்கள்", progressBar);
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

    private void InitMonthBarItems() {
        try {

            final ImageView leftArwImg = findViewById(R.id.leftArrowImg);
            final ImageView rightArwImg = findViewById(R.id.rightArrowImg);
            final TextView monthTitleTv = findViewById(R.id.rasiTitleTv);
            final ViewPager monthItemsViewpager = findViewById(R.id.rasiItemsViewpager);


            MonthItemsViewpagerAdpater viewpagerAdpater = new MonthItemsViewpagerAdpater(getApplicationContext(), 24);
            monthItemsViewpager.setAdapter(viewpagerAdpater);
            Calendar calendar  = Calendar.getInstance();
            int currentMonth = calendar.get(Calendar.MONTH);
            monthItemsViewpager.setCurrentItem(currentMonth);


            monthTitleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        try {

                            AlertDialog.Builder monthBuilder = new AlertDialog.Builder(FestivalDays.this);

                            final ArrayList<String> arrayAdapters = new ArrayList<String>();
                            arrayAdapters.add("ஜனவரி "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("பிப்ரவரி "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("மார்ச் "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("ஏப்ரல் "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("மே "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("ஜூன் "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("ஜூலை "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("ஆகஸ்ட் "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("செப்டம்பர் "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("அக்டோபர் "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("நவம்பர் "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("டிசம்பர் "+String.valueOf(SplashScreen.iFromYear));
                            arrayAdapters.add("ஜனவரி "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("பிப்ரவரி "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("மார்ச் "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("ஏப்ரல் "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("மே "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("ஜூன் "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("ஜூலை "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("ஆகஸ்ட் "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("செப்டம்பர் "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("அக்டோபர் "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("நவம்பர் "+String.valueOf(SplashScreen.iToYear));
                            arrayAdapters.add("டிசம்பர் "+String.valueOf(SplashScreen.iToYear));

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arrayAdapters) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View view = super.getView(position, convertView, parent);
                                    TextView text = (TextView) view.findViewById(android.R.id.text1);
                                    text.setTextColor(Color.BLACK);

                                    //text.setTypeface(typeface);

                                    if (MiddlewareInterface.bRendering) {
                                        text.setText(arrayAdapters.get(position));
                                        text.setTypeface(Typeface.DEFAULT);
                                    } else {
                                        text.setText(UnicodeUtil.unicode2tsc(arrayAdapters.get(position)));
                                        text.setTypeface(MiddlewareInterface.tf_mylai);
                                    }
                                    return view;
                                }
                            };

                            monthBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int selectedMonth) {

//                                    if (selectedYear == 0) {
//                                        currentposition = selectedMonth;
//                                    } else {
//                                        currentposition = selectedMonth + 12;
//                                    }

                                    monthItemsViewpager.setCurrentItem(selectedMonth);
                                    dialog.dismiss();

                                }
                            });
                            monthBuilder.show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            leftArwImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (monthItemsViewpager.getCurrentItem() > 0) {
                        monthItemsViewpager.setCurrentItem(monthItemsViewpager.getCurrentItem() - 1);
                        leftArwImg.setVisibility(View.VISIBLE);
                    } else {
                        leftArwImg.setVisibility(View.GONE);
                    }
                }
            });

            rightArwImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (monthItemsViewpager.getCurrentItem() < 23) {
                        monthItemsViewpager.setCurrentItem(monthItemsViewpager.getCurrentItem() + 1);
                        rightArwImg.setVisibility(View.VISIBLE);
                    } else {
                        rightArwImg.setVisibility(View.GONE);
                    }
                }
            });

            monthItemsViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    try {
                        String[] monthArr = getResources().getStringArray(R.array.tamilmonth_array_subamukoortham);
                        monthTitleTv.setText(monthArr[position]);
                        leftArwImg.setVisibility(View.VISIBLE);
                        rightArwImg.setVisibility(View.VISIBLE);

                        if (position == 0) {
                            leftArwImg.setVisibility(View.GONE);
                        } else if (position == monthArr.length-1) {
                            rightArwImg.setVisibility(View.GONE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class MonthItemsViewpagerAdpater extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        ArrayList<ArrayList<String>> arrMonthItems;
        int monthCount = 0;

        public MonthItemsViewpagerAdpater(Context context, int count) {
            mContext = context;
            monthCount = count;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return monthCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.month_items_viewpager_layout, container, false);

            try {

                RecyclerView mRecyclerView = itemView.findViewById(R.id.my_recycler_view);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                mRecyclerView.setLayoutManager(mLayoutManager);

                // specify an adapter (see also next example)
                String monthPos = String.valueOf(position + 1);
                String year = String.valueOf(SplashScreen.iFromYear);
                if(position>11){
                    year = String.valueOf(SplashScreen.iToYear);
                    monthPos = String.valueOf(position-11);
                }

                ArrayList<ArrayList<String>> getFestivalDays = db.GetFestivalDetails(monthPos,year);

                FestivalDaysAdapter mAdapter = new FestivalDaysAdapter(context, getFestivalDays);
                mRecyclerView.setAdapter(mAdapter);

            } catch (Exception e) {
                e.printStackTrace();
            }


            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }
}