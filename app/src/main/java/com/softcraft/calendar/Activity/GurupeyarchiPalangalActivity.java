package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class GurupeyarchiPalangalActivity extends AppCompatActivity {
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
    int flag = 1;
    public boolean isGurupeyarchi = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gurupeyarchi_palan_layout);
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
//            TextView headingTV = (TextView) findViewById(R.id.headingTV);
//            headingTV.setText(getResources().getString(R.string.gurupeyarchi_palan_title));
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
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GurupeyarchiPalangalActivity.this, imgBack);
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
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GurupeyarchiPalangalActivity.this, tHeader);
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
            flag = getIntent().getIntExtra("flag", 1);
            if (MiddlewareInterface.bRendering) {
                if (flag == 1) {
                    tHeader.setText((getResources().getString(R.string.gurupeyarchi_palan_title)));
                } else {
                    tHeader.setText((getResources().getString(R.string.sanipeyarchi_palan_title)));
                }
                tHeader.setTypeface(Typeface.DEFAULT);
            } else {
                if (flag == 1) {
                    tHeader.setText((getResources().getString(R.string.gurupeyarchi_palan_title)));
                } else {
                    tHeader.setText((getResources().getString(R.string.sanipeyarchi_palan_title)));
                }
                tHeader.setTypeface(MiddlewareInterface.tf_mylai);
            }
            try {
                db = new DataBaseHelper(this);
            } catch (Exception e) {
                e.printStackTrace();
            }

            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

            InitRasiBarItems();

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
                                    if (isGurupeyarchi)
                                        TakeScreenShot(GurupeyarchiPalangalActivity.this, rootLayout, 0, 8, "0", getResources().getString(R.string.gurupeyarchi_palan_title), progressBar);
                                    else
                                        TakeScreenShot(GurupeyarchiPalangalActivity.this, rootLayout, 0, 9, "0", getResources().getString(R.string.gurupeyarchi_palan_title), progressBar);

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

    private void InitRasiBarItems() {
        try {

            final ImageView leftArwImg = findViewById(R.id.leftArrowImg);
            final ImageView rightArwImg = findViewById(R.id.rightArrowImg);
            final TextView rasiTitleTv = findViewById(R.id.rasiTitleTv);
            final ViewPager rasiItemsViewpager = findViewById(R.id.rasiItemsViewpager);

            ArrayList<String> palangalList = new ArrayList<>();

            if (flag == 1) {
                isGurupeyarchi = true;
                palangalList = db.getGuruPeyarchiPalan();
            } else {
                isGurupeyarchi = false;
                palangalList = db.getSaniPeyarchiPalan();
            }


            int userRasiPos = 0;
            try {
                final SharedPreferences shared_pref = PreferenceManager.getDefaultSharedPreferences(GurupeyarchiPalangalActivity.this);
                String userRasi = shared_pref.getString("userRasi", "");
                String[] rasiArr = getResources().getStringArray(R.array.raasi_Array);
                for (int i = 0; i < rasiArr.length; i++) {
                    if (userRasi.equalsIgnoreCase(rasiArr[i])) {
                        userRasiPos = i;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            RasiItemsViewpagerAdpater viewpagerAdpater = new RasiItemsViewpagerAdpater(getApplicationContext(), 12, palangalList);
            rasiItemsViewpager.setAdapter(viewpagerAdpater);
            rasiItemsViewpager.setCurrentItem(userRasiPos);


            rasiTitleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        try {

                            AlertDialog.Builder monthBuilder = new AlertDialog.Builder(GurupeyarchiPalangalActivity.this);

                            final ArrayList<String> arrayAdapters = new ArrayList<String>();
                            arrayAdapters.add("மேஷம்");
                            arrayAdapters.add("ரிஷபம்");
                            arrayAdapters.add("மிதுனம்");
                            arrayAdapters.add("கடகம்");
                            arrayAdapters.add("சிம்மம்");
                            arrayAdapters.add("கன்னி");
                            arrayAdapters.add("துலாம்");
                            arrayAdapters.add("விருச்சிகம்");
                            arrayAdapters.add("தனுசு");
                            arrayAdapters.add("மகரம்");
                            arrayAdapters.add("கும்பம்");
                            arrayAdapters.add("மீனம்");

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
                                public void onClick(DialogInterface dialog, int selectedRasi) {

//                                    if (selectedYear == 0) {
//                                        currentposition = selectedMonth;
//                                    } else {
//                                        currentposition = selectedMonth + 12;
//                                    }

                                    rasiItemsViewpager.setCurrentItem(selectedRasi);
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
                    if (rasiItemsViewpager.getCurrentItem() > 0) {
                        rasiItemsViewpager.setCurrentItem(rasiItemsViewpager.getCurrentItem() - 1);
                        leftArwImg.setVisibility(View.VISIBLE);
                    } else {
                        leftArwImg.setVisibility(View.GONE);
                    }
                }
            });

            rightArwImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rasiItemsViewpager.getCurrentItem() < 11) {
                        rasiItemsViewpager.setCurrentItem(rasiItemsViewpager.getCurrentItem() + 1);
                        rightArwImg.setVisibility(View.VISIBLE);
                    } else {
                        rightArwImg.setVisibility(View.GONE);
                    }
                }
            });

            rasiItemsViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    try {
                        String[] monthArr = getResources().getStringArray(R.array.raasi_Array);
                        rasiTitleTv.setText(monthArr[position]);
                        leftArwImg.setVisibility(View.VISIBLE);
                        rightArwImg.setVisibility(View.VISIBLE);

                        if (position == 0) {
                            leftArwImg.setVisibility(View.GONE);
                        } else if (position == 11) {
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


    class RasiItemsViewpagerAdpater extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        ArrayList<String> guruPalangalist;
        int monthCount = 0;

        public RasiItemsViewpagerAdpater(Context context, int count, ArrayList<String> palangalList) {
            mContext = context;
            monthCount = count;
            guruPalangalist = palangalList;
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

        public int[] startEndPosition(String sentence, String word) {
            int[] arrPos = new int[2];
            int startingPosition = sentence.indexOf(word);
            int endingPosition = startingPosition + word.length();
            arrPos[0] = startingPosition;
            arrPos[1] = endingPosition;
            return arrPos;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.gurupeyarchi_viewpager_layout, container, false);

            try {
                TextView itemTv = itemView.findViewById(R.id.palangalTv);
                ImageView itemImg = itemView.findViewById(R.id.coverImage);

                if (flag == 1) {
                    if (position == 0) {
                        itemImg.setImageResource(R.drawable.guru_mesham_cover);
                    } else if (position == 1) {
                        itemImg.setImageResource(R.drawable.guru_rishabam_cover);
                    } else if (position == 2) {
                        itemImg.setImageResource(R.drawable.guru_mithunam_cover);
                    } else if (position == 3) {
                        itemImg.setImageResource(R.drawable.guru_kadagam_cover);
                    } else if (position == 4) {
                        itemImg.setImageResource(R.drawable.guru_simmam_cover);
                    } else if (position == 5) {
                        itemImg.setImageResource(R.drawable.guru_kanni_cover);
                    } else if (position == 6) {
                        itemImg.setImageResource(R.drawable.guru_thulam_cover);
                    } else if (position == 7) {
                        itemImg.setImageResource(R.drawable.guru_viruchigam_cover);
                    } else if (position == 8) {
                        itemImg.setImageResource(R.drawable.guru_dhausu_cover);
                    } else if (position == 9) {
                        itemImg.setImageResource(R.drawable.guru_magaram_cover);
                    } else if (position == 10) {
                        itemImg.setImageResource(R.drawable.guru_kumbam_cover);
                    } else if (position == 11) {
                        itemImg.setImageResource(R.drawable.guru_meenam_cover);
                    }
                } else {
                    if (position == 0) {
                        itemImg.setImageResource(R.drawable.mesham_sani);
                    } else if (position == 1) {
                        itemImg.setImageResource(R.drawable.rishabam_sani);
                    } else if (position == 2) {
                        itemImg.setImageResource(R.drawable.mithnam_sani);
                    } else if (position == 3) {
                        itemImg.setImageResource(R.drawable.kadagam_sani);
                    } else if (position == 4) {
                        itemImg.setImageResource(R.drawable.simmam_sani);
                    } else if (position == 5) {
                        itemImg.setImageResource(R.drawable.kanni_sani);
                    } else if (position == 6) {
                        itemImg.setImageResource(R.drawable.thulam_sani);
                    } else if (position == 7) {
                        itemImg.setImageResource(R.drawable.viruchigam_sani);
                    } else if (position == 8) {
                        itemImg.setImageResource(R.drawable.danusu_sani);
                    } else if (position == 9) {
                        itemImg.setImageResource(R.drawable.magaram_sani);
                    } else if (position == 10) {
                        itemImg.setImageResource(R.drawable.kumbam_sani);
                    } else if (position == 11) {
                        itemImg.setImageResource(R.drawable.meenam_sani);
                    }
                }

                try {

                    SpannableString spannableString = new SpannableString(guruPalangalist.get(position));

                    if (guruPalangalist.get(position).contains("குருபகவானின் சஞ்சார பலன்கள்:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "குருபகவானின் சஞ்சார பலன்கள்:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("15.11.2020 முதல் 5.1.2021 வரை")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "15.11.2020 முதல் 5.1.2021 வரை");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.BLACK);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("6.1.2021 முதல் 4.3.2021 வரை")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "6.1.2021 முதல் 4.3.2021 வரை");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.BLACK);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("23.5.2021 முதல் 22.7.2021 வரை")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "23.5.2021 முதல் 22.7.2021 வரை");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.BLACK);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("6.4.2021 முதல் 14.9.2021 வரை")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "6.4.2021 முதல் 14.9.2021 வரை");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.BLACK);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("5.3.2021 முதல் 22.5.2021 வரை மற்றும் 23.7.2021 முதல் 13.11.2021 வரை")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "5.3.2021 முதல் 22.5.2021 வரை மற்றும் 23.7.2021 முதல் 13.11.2021 வரை");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.BLACK);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("வியாபாரிகளுக்கு:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "வியாபாரிகளுக்கு:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("உத்தியோகஸ்தர்களுக்கு:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "உத்தியோகஸ்தர்களுக்கு:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("பரிகாரம்:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "பரிகாரம்:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.GREEN);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("குடும்பம்:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "குடும்பம்:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

//                    int lastEndPosition = 0;
//                        if (guruPalangalist.get(position).contains("பரிகாரங்கள்:")) {
//                            int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "பரிகாரங்கள்:");
//                            lastEndPosition = arrWordPos[1];
//                            ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.GREEN);
//                            spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                            spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
//                        }


                    if (guruPalangalist.get(position).contains("உடல் நலம்:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "உடல் நலம்:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("உடல்நலம்:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "உடல்நலம்:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("பொருளாதாரம்:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "பொருளாதாரம்:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("தொழில்:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "தொழில்:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("கல்வி:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "கல்வி:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }

                    if (guruPalangalist.get(position).contains("பொது பரிகாரம்:")) {
                        int[] arrWordPos = startEndPosition(guruPalangalist.get(position), "பொது பரிகாரம்:");
                        ForegroundColorSpan fgcrs = new ForegroundColorSpan(Color.RED);
                        spannableString.setSpan(fgcrs, arrWordPos[0], arrWordPos[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new StyleSpan(Typeface.BOLD), arrWordPos[0], arrWordPos[1], 0);
                    }


                    if (isGurupeyarchi)
                        itemTv.setText(spannableString);
                    else
                        itemTv.setText(spannableString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

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

        private int getRepetativeCount(String text) {
            int count = 0;
            try {
                List<String> list = Arrays.asList(text.split("பரிகாரங்கள்:"));
                Set<String> uniqueWords = new HashSet<String>(list);
                for (String word : uniqueWords) {
                    count = count + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return count;
        }

    }
}