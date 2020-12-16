package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

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
import com.softcraft.calendar.Adapter.FestivalAdapter;
import com.softcraft.calendar.Middleware.MiddlewareInterface;

import com.softcraft.calendar.R;
import com.softcraft.calendar.Fragment.RecyclerViewFragment;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.StoriesAndArticals.StoriesAndArticlesActivity;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.ServiceAndOthers.ZoomOutPageTransformer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class MonthActivity extends AppCompatActivity implements RecyclerViewFragment.CallViewPager {
    ArrayList<ArrayList<String>> setFestivals;
    public DataBaseHelper db;
    public static String POS_KEY_MONTH = "current_pos";
    public static String KEY_MONTH = "current_pos";
    public static String KEY_MONTH_POS = "current_month_pos";
    public ListView mFesivalList;

    protected FrameLayout mFrameLayout;
    private DateFormat df;
    protected String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private int currentposition;
    private String getCurrentmonth;
    private String getCurrentmonthCompare;
    private String getTamilmonth;
    public GregorianCalendar month;
    public Context context;
    public ViewPager mViewpager;
    public ImageView preButton;
    public ImageView nextButton;

    LinearLayout linearad;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    AdView adview;
    TextView tWeatherTemp, tWeatherLocation, tWeatherCondition;
    private AdBannerListener adBannerListener;
    static boolean active = false;
    int checkFlag;
    String getWeatherTemp, getWeatherLocation, getWeatherCondition, clickDay = "Day", clickMonth = "Month", clickYear = "Year";
    ViewFlipper viewFlipper;
    Handler handlerWeather = new Handler();
    ImageButton gridMenu;
    boolean isFirst = false;
    FloatingActionButton refreshmonth;
    TextView refreshText;
    String getcurrentdateMonth;
    private File imagePath;
    LinearLayout showFestivalLayout;
    RelativeLayout weatherLayout;
    private NativeExpressAdView viewNativeAd;
    Animation show;
    Boolean animflag = false;
    public ArrayList<HashMap<String, String>> UtilityArrayList;
    RelativeLayout rootLayout;
    int iViewpagerCount;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
            UtilityArrayList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("UtilityArray");
            mViewpager = (ViewPager) findViewById(R.id.pager);
            linearad = (LinearLayout) findViewById(R.id.adview);
//        weather_layout=(RelativeLayout)findViewById(R.id.weather_show);
            mFrameLayout = (FrameLayout) findViewById(R.id.month_frame_container);

            refreshmonth = (FloatingActionButton) findViewById(R.id.restart_currentmonth);
            refreshText = (TextView) findViewById(R.id.restart_textMonth);
            gridMenu = (ImageButton) findViewById(R.id.gridMenu);
            weatherLayout = (RelativeLayout) findViewById(R.id.weathers_layout);
            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
            tWeatherLocation = (TextView) findViewById(R.id.weatherLocation);
            tWeatherTemp = (TextView) findViewById(R.id.weatherTemp);
            tWeatherCondition = (TextView) findViewById(R.id.weatherCondition);
//        viewFlipper = (ViewFlipper)findViewById(R.id.viewFlipper);
            mViewpager.setPageTransformer(true, new ZoomOutPageTransformer());
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);


            try {
                try {
                    MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                db = new DataBaseHelper(MonthActivity.this);

                try {
                    ArrayList<String> dateList = db.dGetDate();
                    iViewpagerCount = dateList.size();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                final FloatingActionButton shareScreenBtn = (FloatingActionButton) findViewById(R.id.shareFloatingBtn);
                shareScreenBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MonthActivity.this, shareScreenBtn);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    TakeScreenShot(MonthActivity.this, rootLayout, mViewpager.getCurrentItem(), 2, "0", "மாத நாள்காட்டி", progressBar);
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

//            weatherLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MonthActivity.this, weatherLayout);
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
//
//                }
//            });

            gridMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MonthActivity.this, gridMenu);
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

//            try {
//                if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
//                    if (!MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strLocation).equalsIgnoreCase("") && !MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strCondition).equalsIgnoreCase("") && !MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strTemp).equalsIgnoreCase("")) {
//                        handlerWeather.postDelayed(new Runnable() {
//                            public void run() {
//                                try {
//                                    if (animflag == false) {
//                                        show = AnimationUtils.loadAnimation(MonthActivity.this, R.anim.lef_to_right);
//                                        animflag = true;
//                                    } else {
//                                        show = AnimationUtils.loadAnimation(MonthActivity.this, R.anim.right_to_left);
//                                        animflag = false;
//                                    }
//                                    tWeatherLocation.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strLocation));
//                                    tWeatherCondition.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strCondition));
//                                    tWeatherTemp.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strTemp));
//                                    if (weatherLayout != null) {
//                                        weatherLayout.startAnimation(show);
//                                    }
//                                    handlerWeather.postDelayed(this, 10000);
//                                    isFirst = true;
//                                    //add navigation menu show
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, 10000);
//                    }
//                } else {
//                    if (!MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strLocation).equalsIgnoreCase("") && !MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strCondition).equalsIgnoreCase("") && !MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strTemp).equalsIgnoreCase("")) {
//                        handlerWeather.postDelayed(new Runnable() {
//                            public void run() {
//                                try {
//                                    if (animflag == false) {
//                                        show = AnimationUtils.loadAnimation(MonthActivity.this, R.anim.lef_to_right);
//                                        animflag = true;
//                                    } else {
//                                        show = AnimationUtils.loadAnimation(MonthActivity.this, R.anim.right_to_left);
//                                        animflag = false;
//                                    }
//                                    tWeatherLocation.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strLocation));
//                                    tWeatherCondition.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strCondition));
//                                    tWeatherTemp.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getString(MiddlewareInterface.strTemp));
//                                    if (weatherLayout != null) {
//                                        weatherLayout.startAnimation(show);
//                                    }
//                                    handlerWeather.postDelayed(this, 10000);
//                                    isFirst = true;
//                                    //add navigation menu show
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, 10000);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            // load slide menu items

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


            month = (GregorianCalendar) GregorianCalendar.getInstance();
            GregorianCalendar selectedDate = (GregorianCalendar) month.clone();
            month.set(GregorianCalendar.DAY_OF_MONTH, 1);
            TextView monthTitle = (TextView) findViewById(R.id.month_toolbar_title);
            TextView festivalName = (TextView) findViewById(R.id.festival_day);
            if (MiddlewareInterface.bRendering) {
                monthTitle.setText((getResources().getString(R.string.day)));
                festivalName.setText((getResources().getString(R.string.festival_day)));
                refreshText.setText((getResources().getString(R.string.today)));
                monthTitle.setTypeface(Typeface.DEFAULT);
                festivalName.setTypeface(Typeface.DEFAULT);
                refreshText.setTypeface(Typeface.DEFAULT);
            } else {
                monthTitle.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.day)));
                festivalName.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.festival_day)));
                refreshText.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.today)));
                monthTitle.setTypeface(MiddlewareInterface.tf_mylai);
                festivalName.setTypeface(MiddlewareInterface.tf_mylai);
                refreshText.setTypeface(MiddlewareInterface.tf_mylai);
            }
            final LinearLayout goToDayview = (LinearLayout) findViewById(R.id.month_icon_click);
            goToDayview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MonthActivity.this, goToDayview);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                if (active) {
                                    Intent back = new Intent();
                                    setResult(6, back);
                                }
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

            df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String curentDateString = df.format(selectedDate.getTime());
            String[] separatedTime = curentDateString.split("-");
            // taking last part of date. ie; 2 from 2012-12-02
            String gridvalue = separatedTime[2].replaceFirst("^0*", "");
            String Tmonth = separatedTime[1].replaceFirst("^0*", "");
            String Tyear = separatedTime[0].replaceFirst("^0*", "");
//        if(Tyear.equalsIgnoreCase("2017"))
            int viewPagerPos = Integer.parseInt(Tmonth);
            if (Tyear.equalsIgnoreCase(String.valueOf(SplashScreen.iFromYear))) {
                viewPagerPos = viewPagerPos;
                viewPagerPos = viewPagerPos - 1;
            } else if (Tyear.equalsIgnoreCase(String.valueOf(SplashScreen.iToYear))) {
                viewPagerPos = viewPagerPos + 12;
                viewPagerPos = viewPagerPos - 1;
            }
            MiddlewareInterface.SharedPreferenceUtility.getInstance(this).putInt(KEY_MONTH_POS, viewPagerPos);


            preButton = (ImageView) findViewById(R.id.prev_button);
            nextButton = (ImageView) findViewById(R.id.next_button);
            preButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MonthActivity.this, preButton);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                mViewpager.setPageTransformer(true, new ZoomOutPageTransformer());
                                mViewpager.setCurrentItem(currentposition - 1);
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


            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MonthActivity.this, nextButton);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                mViewpager.setPageTransformer(true, new ZoomOutPageTransformer());
                                mViewpager.setCurrentItem(currentposition + 1);
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


            // Viewpager set fragment
            mViewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return RecyclerViewFragment.newInstance(position);
                }

                public int getItemPosition(Object object) {
                    // Causes adapter to reload all Fragments when
                    // notifyDataSetChanged is called
                    return POSITION_NONE;
                }

                @Override
                public int getCount() {
                    return 24;
                }

            });
            mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    try {
                        currentposition = position;
                        RefreshAds();
                        int sendFestivalValue = position + 1;
                        setFestivals = db.dGetFestivals(sendFestivalValue);
//                        setFestivals = db.dGetFestivals(sendFestivalValue);

                        mFesivalList = (ListView) findViewById(R.id.festival_list_view);
                        showFestivalLayout = (LinearLayout) findViewById(R.id.festival_list_show);
                        try {
                            if (setFestivals.get(0).size() != 0) {
                                showFestivalLayout.setVisibility(View.VISIBLE);
                                mFesivalList.setAdapter(new FestivalAdapter(MonthActivity.this, setFestivals));
                                setListViewHeightBasedOnChildren(mFesivalList);
                            } else {
                                showFestivalLayout.setVisibility(View.GONE);
//                        Toast.makeText(context,"hai",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {

                        }

                        if (position == 0) {
                            getCurrentmonth = "ஜனவரி -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -1."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 1) {
                            getCurrentmonth = "பிப்ரவரி -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -2."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 2) {
                            getCurrentmonth = "மார்ச் -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -3."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 3) {
                            getCurrentmonth = "ஏப்ரல் -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -4."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 4) {
                            getCurrentmonth = "மே -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -5."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 5) {
                            getCurrentmonth = "ஜூன் -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -6."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 6) {
                            getCurrentmonth = "ஜூலை -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -7."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 7) {
                            getCurrentmonth = "ஆகஸ்ட் -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -8."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 8) {
                            getCurrentmonth = "செப்டம்பர் -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -9."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 9) {
                            getCurrentmonth = "அக்டோபர் -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -10."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 10) {
                            getCurrentmonth = "நவம்பர் -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -11."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 11) {
                            getCurrentmonth = "டிசம்பர் -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "ஜனவரி -12."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 12) {
                            getCurrentmonth = "ஜனவரி -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "ஜனவரி -1."+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 13) {
                            getCurrentmonth = "பிப்ரவரி -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "பிப்ரவரி -2."+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 14) {
                            getCurrentmonth = "மார்ச் -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "மார்ச் -3."+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 15) {
                            getCurrentmonthCompare = "ஏப்ரல் -4."+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonth = "ஏப்ரல் -"+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 16) {
                            getCurrentmonth = "மே -"+String.valueOf(SplashScreen.iFromYear);
                            getCurrentmonthCompare = "மே -5."+String.valueOf(SplashScreen.iFromYear);
                        } else if (position == 17) {
                            getCurrentmonth = "ஜூன் -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "ஜூன் -6."+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 18) {
                            getCurrentmonth = "ஜூலை -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "ஜூலை -7."+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 19) {
                            getCurrentmonth = "ஆகஸ்ட் -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "ஆகஸ்ட் -8."+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 20) {
                            getCurrentmonth = "செப்டம்பர் -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "செப்டம்பர் -9."+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 21) {
                            getCurrentmonth = "அக்டோபர் -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "அக்டோபர் -10."+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 22) {
                            getCurrentmonth = "நவம்பர் -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "நவம்பர் -11."+String.valueOf(SplashScreen.iToYear);
                        } else if (position == 23) {
                            getCurrentmonth = "டிசம்பர் -"+String.valueOf(SplashScreen.iToYear);
                            getCurrentmonthCompare = "டிசம்பர் -12."+String.valueOf(SplashScreen.iToYear);
                        }

                        final TextView tCurrentMonth = (TextView) findViewById(R.id.current_month);
                        if (position == 0 || position == 12)
                            getTamilmonth = "மார்கழி - தை ";
                        else if (position == 1 || position == 13)
                            getTamilmonth = "தை - மாசி  ";
                        else if (position == 2 || position == 14)
                            getTamilmonth = "மாசி - பங்குனி ";
                        else if (position == 3 || position == 15)
                            getTamilmonth = "பங்குனி - சித்திரை ";
                        else if (position == 4 || position == 16)
                            getTamilmonth = "சித்திரை - வைகாசி ";
                        else if (position == 5 || position == 17)
                            getTamilmonth = "வைகாசி - ஆனி ";
                        else if (position == 6 || position == 18)
                            getTamilmonth = "ஆனி - ஆடி ";
                        else if (position == 7 || position == 19)
                            getTamilmonth = "ஆடி - ஆவணி ";
                        else if (position == 8 || position == 20)
                            getTamilmonth = "ஆவணி - புரட்டாசி ";
                        else if (position == 9 || position == 21)
                            getTamilmonth = "புரட்டாசி - ஐப்பசி ";
                        else if (position == 10 || position == 22)
                            getTamilmonth = "ஐப்பசி - கார்த்திகை ";
                        else if (position == 11 || position == 23)
                            getTamilmonth = "கார்த்திகை - மார்கழி";

                        TextView tTamilMonth = (TextView) findViewById(R.id.tamil_month);
                        if (MiddlewareInterface.bRendering) {
                            tCurrentMonth.setText(getCurrentmonth);
                            tTamilMonth.setText(getTamilmonth);
                            tCurrentMonth.setTypeface(Typeface.DEFAULT);
                            tTamilMonth.setTypeface(Typeface.DEFAULT);
                        } else {
                            tCurrentMonth.setText(UnicodeUtil.unicode2tsc(getCurrentmonth));
                            tTamilMonth.setText(UnicodeUtil.unicode2tsc(getTamilmonth));
                            tCurrentMonth.setTypeface(MiddlewareInterface.tf_mylai);
                            tTamilMonth.setTypeface(MiddlewareInterface.tf_mylai);
                        }
                        String[] str = getCurrentmonthCompare.split("-");
                        getcurrentdateMonth = str[1];
                        if (!getcurrentdateMonth.equalsIgnoreCase(MiddlewareInterface.getCurrentDateMonth())) {
                            refreshmonth.setVisibility(View.VISIBLE);
                            refreshText.setVisibility(View.VISIBLE);
                        } else {
//                    MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).putInt(KEY_MONTH,position);
                            refreshmonth.setVisibility(View.GONE);
                            refreshText.setVisibility(View.GONE);
                        }

                        tCurrentMonth.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {


                                    AlertDialog.Builder yearBuilder = new AlertDialog.Builder(MonthActivity.this);

                                    final ArrayList<String> arrayAdapters = new ArrayList<String>();
                                    arrayAdapters.add(String.valueOf(SplashScreen.iFromYear));
                                    arrayAdapters.add(String.valueOf(SplashScreen.iToYear));

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

                                    yearBuilder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, final int selectedYear) {

                                            AlertDialog.Builder monthBuilder = new AlertDialog.Builder(MonthActivity.this);

                                            final ArrayList<String> arrayAdapters = new ArrayList<String>();
                                            arrayAdapters.add("ஜனவரி ");
                                            arrayAdapters.add("பிப்ரவரி ");
                                            arrayAdapters.add("மார்ச் ");
                                            arrayAdapters.add("ஏப்ரல் ");
                                            arrayAdapters.add("மே ");
                                            arrayAdapters.add("ஜூன் ");
                                            arrayAdapters.add("ஜூலை ");
                                            arrayAdapters.add("ஆகஸ்ட் ");
                                            arrayAdapters.add("செப்டம்பர் ");
                                            arrayAdapters.add("அக்டோபர் ");
                                            arrayAdapters.add("நவம்பர் ");
                                            arrayAdapters.add("டிசம்பர் ");

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

                                                    if (selectedYear == 0) {
                                                        currentposition = selectedMonth;
                                                    } else {
                                                        currentposition = selectedMonth + 12;
                                                    }

                                                    mViewpager.setCurrentItem(currentposition);
                                                    dialog.dismiss();

                                                }
                                            });
                                            monthBuilder.show();
                                        }
                                    });
                                    yearBuilder.show();

                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        });
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
            refreshmonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MonthActivity.this, refreshmonth);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                int getPos = MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getInt(KEY_MONTH_POS);
                                mViewpager.setCurrentItem(getPos);
                                refreshmonth.setVisibility(View.GONE);
                                refreshText.setVisibility(View.GONE);
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

            mViewpager.setCurrentItem(viewPagerPos);
            GotoDeeplinkedPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void setListViewHeightBasedOnChildren(ListView listView) {
        try {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null)
                return;

            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            int totalHeight = 0;
            View view = null;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                view = listAdapter.getView(i, view, listView);
                if (i == 0)
                    view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

                view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            int height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            height = height + 50;
            params.height = height;
            listView.setLayoutParams(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void yourDesiredMethod(final int i) {
        Log.d("relativeLayoutHight", String.valueOf(i));
    }

    public void callback() {
        try {
            Intent intent  = new Intent(MonthActivity.this, DayActivity.class);
            intent.putExtra("edittextvalue", MiddlewareInterface.clickDateFromMonth);
            startActivity(intent);

//            Intent back = new Intent();
//            back.putExtra("edittextvalue", MiddlewareInterface.clickDateFromMonth);
//            setResult(5, back);
//            finish();
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
                Toast toast = Toast.makeText(MonthActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ViewPager getViewpager() {
        return mViewpager;
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


    public void checkInternetConnection() {
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
                Toast toast = Toast.makeText(MonthActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
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
                Toast toast = Toast.makeText(MonthActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdvertise() {
        try {
            try {
//                arrayListad = MiddlewareInterface.listsaddetails;
//                getListAds = arrayListad.get(0);
                String strGoogleAd = getBannerAd;
                ;
                if (MiddlewareInterface.bAdFree)
                    return;
                adview = new AdView(this);
                adview.setAdSize(AdSize.SMART_BANNER);
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


    public Bitmap getScreenShot() {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        rootView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

    private void shareImage() {
        Uri uri = Uri.fromFile(imagePath);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/text");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "சுபம் தமிழ் நாட்காட்டி");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.download_app_text));
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getApplicationContext(), "No App Available", Toast.LENGTH_SHORT).show();
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

    private void RefreshAds() {
        try {
            if (MiddlewareInterface.bAdFree)
                return;

            adview.loadAd(new AdRequest.Builder().build());


        } catch (Exception e) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void GotoDeeplinkedPage() {
        try {
            int pagerPos = getIntent().getIntExtra("viewpagerPos", -1);
            if (pagerPos != -1) {
                mViewpager.setCurrentItem(pagerPos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

