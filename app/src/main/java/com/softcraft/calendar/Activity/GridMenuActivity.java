package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.firebase.analytics.FirebaseAnalytics;

import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.DevotionalWallpapers.DevotionalWallpapersActivity;
import com.softcraft.calendar.History.CommonCalendarData;
import com.softcraft.calendar.History.HistoryOfTodayActivity;
import com.softcraft.calendar.MarriageMatch.MarriageMatchActivity;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Adapter.GridMenuAdapter;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.StoriesAndArticals.StoriesAndArticlesActivity;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

import me.grantland.widget.AutofitTextView;

import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getVersionExpiry;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.isNotesIntent;

public class GridMenuActivity extends AppCompatActivity {
    private ExpandableHeightGridView gridView;
    String clickDay = "Day", clickMonth = "Month", clickYear = "Year";
    GridMenuAdapter adapter;
    String[] arrItemName;
    public int[] arrItemImage;
    private NativeExpressAdView viewNativeAd;
    LinearLayout linearad;
    AdView adview;
    public ArrayList<HashMap<String, String>> UtilityArrayList;
    DataBaseHelper db;
    RelativeLayout exitAlertBackgroundLayout;
    String rasipalanId;
    int catId, pagerPos;
    RelativeLayout todayThought_BGLayout, rateAppBackgroundLayout;
    ProgressBar progressBar;
    String crntDate;
    private long mLastClickTime = 0;
    private RelativeLayout updateBackgroundLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_menu_activity);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));

        try {
            try {
                MiddlewareInterface.tf_didot = Typeface.createFromAsset(getAssets(), "fonts/TheanoDidot-Regular.ttf");
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");

                callShortCutPage();
            } catch (Exception e) {
                e.printStackTrace();
            }

            exitAlertBackgroundLayout = (RelativeLayout) findViewById(R.id.exitAlertBackgroundLayout);
            db = new DataBaseHelper(getApplicationContext());
            showCrntDate();
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            inflateNotesLayout();

            UtilityArrayList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("UtilityArray");
            catId = getIntent().getIntExtra("categoryId", -1);
            pagerPos = getIntent().getIntExtra("viewpagerPos", -1);
            rasipalanId = getIntent().getStringExtra("rasipalan");
            ratetheAppFunction();
            todaysThoughtFunc();
            todaysThoughtFunc1();

            gridView = findViewById(R.id.gridview);
            gridView.setExpanded(true);
            final TextView HeadTitle = findViewById(R.id.songsPage_header);
            final ImageView backBtn = findViewById(R.id.news_imgback_click1);
            linearad = findViewById(R.id.notication_adview);
            if (isTablet()) {
                gridView.setNumColumns(4);
            }
            updateBackgroundLayout = (RelativeLayout) findViewById(R.id.updateBackgroundLayout);
            final ScrollView scroll_home = findViewById(R.id.scroll_home);
            scroll_home.pageScroll(View.FOCUS_UP);
            scroll_home.smoothScrollTo(0, 0);
            scroll_home.fullScroll(ScrollView.FOCUS_UP);
            scroll_home.scrollTo(0, 0);
            scroll_home.smoothScrollBy(0, 0);
            scroll_home.post(new Runnable() {
                @Override
                public void run() {
                    scroll_home.pageScroll(View.FOCUS_UP);
                    scroll_home.smoothScrollTo(0, 0);
                    scroll_home.fullScroll(ScrollView.FOCUS_UP);
                    scroll_home.scrollTo(0, 0);
                    scroll_home.smoothScrollBy(0, 0);
                }
            });
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
            HeadTitle.startAnimation(animation);

            try {
                if (MiddlewareInterface.bRendering) {
                    HeadTitle.setText(getResources().getString(R.string.app_head));
                    HeadTitle.setTypeface(Typeface.DEFAULT);
                } else {
                    HeadTitle.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.app_head)));
                    HeadTitle.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                try {
                    if (getEnable != null && getEnable.equalsIgnoreCase("1")) {
                        if (getBannerType.equalsIgnoreCase("0")) {
                            setAdvertise();
                        } else {
                            setNativeSmall();
                        }
                    } else if (getEnable != null && getEnable.equalsIgnoreCase("4")) {
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

            HeadTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, HeadTitle);
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

            if (MiddlewareInterface.SharedPreferenceUtility.getInstance(getApplicationContext()).getString(MiddlewareInterface.strLocation).equalsIgnoreCase("")) {
                arrItemName = getResources().getStringArray(R.array.nav_drawer_items);
                arrItemImage = new int[]{/*R.drawable.home_selector,*/ R.drawable.nalcalendarnew, R.drawable.monthcalendarnew, R.drawable.fastingnew, R.drawable.amavasipournaminew, R.drawable.holidaysnew, R.drawable.graha_orai_ic, R.drawable.gowri_panch_ic, R.drawable.gurupeyarchi_ic, R.drawable.sanipyearchi_ic, R.drawable.thiurmanaporuthamnew, R.drawable.historynew, R.drawable.festivalnew, R.drawable.manayadisasthiramnew, R.drawable.submugurthamnew, R.drawable.todayraasinew,
                        R.drawable.monthrassinew, R.drawable.yearraasinew, R.drawable.newsnew, R.drawable.godsongsnews, R.drawable.magalasongnew, R.drawable.storiesnew, R.drawable.dreamnew, R.drawable.pallipalannew, R.drawable.childnamenew, R.drawable.wallpapersnew, R.drawable.notesnew, R.drawable.notificationsettingnew, R.drawable.helpnew, R.drawable.aboutusnew, R.drawable.sharenew};
            } else {
                arrItemName = getResources().getStringArray(R.array.nav_drawer_items1);
                arrItemImage = new int[]{/*R.drawable.home_selector, */R.drawable.nalcalendarnew, R.drawable.monthcalendarnew, R.drawable.fastingnew, R.drawable.amavasipournaminew, R.drawable.holidaysnew, R.drawable.graha_orai_ic, R.drawable.gowri_panch_ic, R.drawable.gurupeyarchi_ic, R.drawable.sanipyearchi_ic, R.drawable.thiurmanaporuthamnew, R.drawable.historynew, R.drawable.festivalnew, R.drawable.manayadisasthiramnew, R.drawable.submugurthamnew, R.drawable.todayraasinew,
                        R.drawable.monthrassinew, R.drawable.yearraasinew, R.drawable.newsnew, R.drawable.godsongsnews, R.drawable.magalasongnew, R.drawable.storiesnew, R.drawable.dreamnew, R.drawable.pallipalannew, R.drawable.childnamenew, R.drawable.wallpapersnew, R.drawable.notesnew, R.drawable.notificationsettingnew, R.drawable.helpnew, R.drawable.aboutusnew, R.drawable.sharenew};
            }
            adapter = new GridMenuAdapter(GridMenuActivity.this, arrItemName, arrItemImage);
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    try {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, view);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {

                                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                                        return;
                                    }
                                    mLastClickTime = SystemClock.elapsedRealtime();
                                    selectItem(position);

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

            try {
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grid_item_anim);
                GridLayoutAnimationController controller = new GridLayoutAnimationController(animation1, .2f, .2f);
                gridView.setLayoutAnimation(controller);
            } catch (Exception e) {
                e.printStackTrace();
            }
            scroll_home.pageScroll(View.FOCUS_UP);
            scroll_home.smoothScrollTo(0, 0);
            scroll_home.fullScroll(ScrollView.FOCUS_UP);
            scroll_home.scrollTo(0, 0);
            scroll_home.smoothScrollBy(0, 0);
            scroll_home.post(new Runnable() {
                @Override
                public void run() {
                    scroll_home.pageScroll(View.FOCUS_UP);
                    scroll_home.smoothScrollTo(0, 0);
                    scroll_home.fullScroll(ScrollView.FOCUS_UP);
                    scroll_home.scrollTo(0, 0);
                    scroll_home.smoothScrollBy(0, 0);
                }
            });
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, backBtn);
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

            GotoDeeplinkingCategory();

            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                String versionName = packageInfo.versionName;
                double verName = Double.parseDouble(versionName);
                double verFromFeed = Double.parseDouble(MiddlewareInterface.getversion);
                if (verName < verFromFeed && getVersionExpiry.equalsIgnoreCase("1"))
                    updateAlertFunction();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callShortCutPage() {
        try {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String shortcutKey = getIntent().getStringExtra("shortcutKey");
                    Intent intent = null;

                    if (shortcutKey != null && shortcutKey.equalsIgnoreCase(SplashScreen.SHORTCUT_ACTION_1)) { // daily calendar
                        gotoDayActivity(intent);

                    } else if (shortcutKey != null && shortcutKey.equalsIgnoreCase(SplashScreen.SHORTCUT_ACTION_2)) { // monthly calendar
                        gotoMonthActivity(intent);

                    } else if (shortcutKey != null && shortcutKey.equalsIgnoreCase(SplashScreen.SHORTCUT_ACTION_3)) { // fasting days
                        gotoFastingDaysActivity(intent);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAlertFunction() {
        try {
            final RelativeLayout updateBtnLayout = (RelativeLayout) findViewById(R.id.updateBtnLayout);
            final ImageView closePopupImg = (ImageView) findViewById(R.id.updateCloseImg);
            TextView updateTv = (TextView) findViewById(R.id.updateTextTv);
            TextView updateInfoTv = (TextView) findViewById(R.id.infoTv);
            TextView updateDescTv = (TextView) findViewById(R.id.descTv);
            int updateUrlKey = -1;
            if (getVersionExpiry != null) {
                updateUrlKey = Integer.parseInt(getVersionExpiry);
            }
            final SharedPreferences update_pref = PreferenceManager.getDefaultSharedPreferences(GridMenuActivity.this);
            final int updateValue = update_pref.getInt("updateAlert", updateUrlKey);
            if (updateUrlKey == 1) {
                updateBackgroundLayout.setVisibility(View.VISIBLE);
            } else {
                updateBackgroundLayout.setVisibility(View.GONE);
            }

            updateBackgroundLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SplashScreen.isForceUpdate != 1) { // 1 - forceupdate, 0- not a force update
                        updateBackgroundLayout.setVisibility(View.GONE);
                    }
                }
            });

            closePopupImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, closePopupImg);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                updateBackgroundLayout.setVisibility(View.GONE);
                                SharedPreferences.Editor editor = update_pref.edit();
                                editor.putInt("updateAlert", updateValue);
                                editor.apply();
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
            updateBtnLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, updateBtnLayout);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.softcraft.calendar"));
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
            try {
                if (MiddlewareInterface.bRendering) {
                    updateTv.setText((getResources().getString(R.string.updateText)));
                    updateInfoTv.setText((getResources().getString(R.string.updateInfo)));
                    updateDescTv.setText((getResources().getString(R.string.updateDesc)));
                    updateTv.setTypeface(Typeface.DEFAULT);
                    updateInfoTv.setTypeface(Typeface.DEFAULT);
                    updateDescTv.setTypeface(Typeface.DEFAULT);
                } else {
                    updateTv.setText(UnicodeUtil.unicode2tsc((getResources().getString(R.string.updateText))));
                    updateInfoTv.setText(UnicodeUtil.unicode2tsc((getResources().getString(R.string.updateInfo))));
                    updateDescTv.setText(UnicodeUtil.unicode2tsc((getResources().getString(R.string.updateDesc))));
                    updateTv.setTypeface(MiddlewareInterface.tf_mylai);
                    updateInfoTv.setTypeface(MiddlewareInterface.tf_mylai);
                    updateDescTv.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void inflateNotesLayout() {
        try {
            Date d = new Date();
            String getcurrentdate = new SimpleDateFormat("dd.MM.yyyy").format(d);
            ArrayList<String> getCount = db.dGetDate();
            int crntDatePos = 0;
            for (int i = 0; i < getCount.size(); i++) {
                if (getCount.get(i).equals(getcurrentdate)) {
                    crntDatePos = i;
                }
            }
            String df = getCount.get(crntDatePos);
            ArrayList<String> setOtherdetails = db.dGetOtherdetails(df);
            String strCrntdate = setOtherdetails.get(8);
            ArrayList<ArrayList<String>> arrNotesList = db.getSpecificNote(strCrntdate);

            RelativeLayout homeNotesReminderLayout = findViewById(R.id.homeNotesReminderLayout);
            TextView homeNotesTitleTv = findViewById(R.id.homeNotesTitleTv);
            TextView homeNotesDescTv = findViewById(R.id.homeNotesDescTv);

            if (arrNotesList.size() == 0)
                homeNotesReminderLayout.setVisibility(View.GONE);

            for (int i = 0; i < arrNotesList.size(); i++) {
                try {
                    if (!arrNotesList.get(i).get(5).equalsIgnoreCase("")) {
                        ArrayList<String> noteList = arrNotesList.get(i);
                        if (!noteList.get(3).equalsIgnoreCase(""))
                            homeNotesTitleTv.setText(noteList.get(2) + " - ");
                        else
                            homeNotesTitleTv.setText(noteList.get(2));
                        homeNotesDescTv.setText(noteList.get(3));
                        homeNotesReminderLayout.setVisibility(View.VISIBLE);
                        homeNotesReminderLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(final View v) {
                                try {
                                    Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                                    startActivityForResult(intent, 11);
                                    progressBar.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showCrntDate() {
        try {
            Date d = new Date();
            String getcurrentdate = new SimpleDateFormat("dd.MM.yyyy").format(d);
            ArrayList<String> getCount = db.dGetDate();
            int crntDatePos = 0;
            for (int i = 0; i < getCount.size(); i++) {
                if (getCount.get(i).equals(getcurrentdate)) {
                    crntDatePos = i;
                }
            }
            final String df = getCount.get(crntDatePos);
            crntDate = df;
            ArrayList<String> setOtherdetails = db.dGetOtherdetails(df);


            TextView tMonthYear = (TextView) findViewById(R.id.tamil_month_year);
            TextView tMonth = (TextView) findViewById(R.id.tamil_month);
            TextView tamilDate = (TextView) findViewById(R.id.tamilDate);
            TextView tCurrentMonth = (TextView) findViewById(R.id.current_Emonth);
            TextView tWeekOfMonth = (TextView) findViewById(R.id.current_weekofmonth);
            TextView tTamilday = (TextView) findViewById(R.id.tamil_day);
            TextView tCurrentdate = (TextView) findViewById(R.id.tcurrent_date);
            TextView tStar = (TextView) findViewById(R.id.star_text);
            TextView tThithi = (TextView) findViewById(R.id.thithi_text);
            TextView tYoham = (TextView) findViewById(R.id.yoham_text);
            TextView tChandrastam = (TextView) findViewById(R.id.chandrastam_text);

            RelativeLayout topRlayout = findViewById(R.id.topRlayout);
            RelativeLayout dayLayout = findViewById(R.id.dayLayout);
            final TableLayout tableLayouts = findViewById(R.id.tableLayouts);
            final ImageView downup_arrow_Img = findViewById(R.id.downup_arrow_Img);

            topRlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getApplicationContext(), DayActivity.class);
                        intent.putExtra("UtilityArray", UtilityArrayList);
                        intent.putExtra("categoryId", catId);
                        intent.putExtra("viewpagerPos", pagerPos);
                        intent.putExtra("rasipalan", rasipalanId);
                        intent.putExtra("crntDate", df);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            dayLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

//                        if (tableLayouts.getVisibility() == View.VISIBLE) {
//                            RotateAnimation rotate = new RotateAnimation(180, 0, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
//                            rotate.setDuration(300);
//                            rotate.setInterpolator(new LinearInterpolator());
//                            downup_arrow_Img.startAnimation(rotate);
//                            downup_arrow_Img.setImageResource(R.drawable.down_arrow_white);
//                            collapse(tableLayouts);
//                        } else {
//                            RotateAnimation rotate = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                            rotate.setDuration(300);
//                            rotate.setInterpolator(new LinearInterpolator());
//                            downup_arrow_Img.startAnimation(rotate);
//                            downup_arrow_Img.setImageResource(R.drawable.up_arrow_white);
//                            expand(tableLayouts);
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            String[] arrTamilYearMonth = setOtherdetails.get(4).split(",");
            String splittamil = arrTamilYearMonth[1];
            String splitDate = setOtherdetails.get(8);

            String[] getTamilmonth = splittamil.split(" ");
            String[] arrDate = splitDate.split("\\.");

            int date = Integer.parseInt(arrDate[0]);
            int month = Integer.parseInt(arrDate[1]);
            month = month - 1;

            List<String> historyList = getTodayHistory(month, date);

            final TextView historyTodayTV = findViewById(R.id.historyTodayTV);
            RelativeLayout historyFullLayout = findViewById(R.id.historyFullLayout);

            final ImageView downuparrowImg1 = findViewById(R.id.downuparrowImg1);
            try {
                String strHistory = "";
                for (int i = 0; i < historyList.size(); i++) {
                    int num = i + 1;
                    if (historyList.size() != num)
                        strHistory += num + "." + historyList.get(i) + "<BR/><BR/>";
                    else
                        strHistory += num + "." + historyList.get(i);
                }
//            strHistory = strHistory.replace(null,"");
                historyTodayTV.setText(Html.fromHtml(strHistory));
                historyTodayTV.setMaxLines(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            historyFullLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        int maxlines = historyTodayTV.getMaxLines();
                        if (maxlines == 2) {
                            RotateAnimation rotate = new RotateAnimation(180, 0, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
                            rotate.setDuration(100);
                            rotate.setInterpolator(new LinearInterpolator());
                            downuparrowImg1.startAnimation(rotate);
                            downuparrowImg1.setImageResource(R.drawable.up_arw_blue_ic);
                            historyTodayTV.setMaxLines(Integer.MAX_VALUE);
                        } else {
                            RotateAnimation rotate = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                            rotate.setDuration(100);
                            rotate.setInterpolator(new LinearInterpolator());
                            downuparrowImg1.startAnimation(rotate);
                            downuparrowImg1.setImageResource(R.drawable.down_arw_blue_ic);
                            historyTodayTV.setMaxLines(2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            String setTamilmonth = getTamilmonth[0];
            String setTamilday1 = getTamilmonth[1];
            String setTamilday = arrDate[0];
            String[] split = df.split("\\.");
            int getSplitMonth = Integer.parseInt(split[1]);
            String getCurrentmonth = null;
            if (getSplitMonth == 1)
                getCurrentmonth = "ஜனவரி";
            else if (getSplitMonth == 2)
                getCurrentmonth = "பிப்ரவரி";
            else if (getSplitMonth == 3)
                getCurrentmonth = "மார்ச்";
            else if (getSplitMonth == 4)
                getCurrentmonth = "ஏப்ரல்";
            else if (getSplitMonth == 5)
                getCurrentmonth = "மே";
            else if (getSplitMonth == 6)
                getCurrentmonth = "ஜூன்";
            else if (getSplitMonth == 7)
                getCurrentmonth = "ஜூலை";
            else if (getSplitMonth == 8)
                getCurrentmonth = "ஆகஸ்ட்";
            else if (getSplitMonth == 9)
                getCurrentmonth = "செப்டம்பர்";
            else if (getSplitMonth == 10)
                getCurrentmonth = "அக்டோபர்";
            else if (getSplitMonth == 11)
                getCurrentmonth = "நவம்பர்";
            else if (getSplitMonth == 12)
                getCurrentmonth = "டிசம்பர்";


            String setWeekday = null;
            String setCurrentWeek = setOtherdetails.get(5);
            if (setCurrentWeek.equalsIgnoreCase("திங்கட்கிழமை"))
                setWeekday = "திங்கள் ";
            else if (setCurrentWeek.equalsIgnoreCase("செவ்வாய்க்கிழமை"))
                setWeekday = "செவ்வாய்";
            else if (setCurrentWeek.equalsIgnoreCase("புதன்கிழமை"))
                setWeekday = "புதன்";
            else if (setCurrentWeek.equalsIgnoreCase("வியாழக்கிழமை"))
                setWeekday = "வியாழன்";
            else if (setCurrentWeek.equalsIgnoreCase("வெள்ளிக்கிழமை"))
                setWeekday = "வெள்ளி";
            else if (setCurrentWeek.equalsIgnoreCase("சனிக்கிழமை"))
                setWeekday = "சனி";
            else if (setCurrentWeek.equalsIgnoreCase("ஞாயிற்றுக்கிழமை"))
                setWeekday = "ஞாயிறு";

            String setDate;
            Date newDate;
            String dayOfTheWeek;

            SimpleDateFormat getWeek = new SimpleDateFormat("EEEE");
            SimpleDateFormat formatin = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat formatout = new SimpleDateFormat("MMMM , yyyy");

            newDate = formatin.parse(df);
            setDate = formatout.format(newDate);
            dayOfTheWeek = getWeek.format(newDate);


            try {
                ImageView subamugurthamImg = findViewById(R.id.subamugurthamImg);
                ImageView fastingImg = findViewById(R.id.fastingImg);
                ImageView pournamiamavasaiImg = findViewById(R.id.pournamiamavasaiImg);
                ImageView arrow_icons = findViewById(R.id.arrow_icons);
                TextView weatherTV = findViewById(R.id.weatherTV);
                RelativeLayout weatherLayout = findViewById(R.id.weatherLayout);
                subamugurthamImg.setVisibility(View.GONE);
                fastingImg.setVisibility(View.GONE);
                pournamiamavasaiImg.setVisibility(View.GONE);
                arrow_icons.setVisibility(View.GONE);
                weatherLayout.setVisibility(View.GONE);
                boolean isValueavailable = false;

                String showKirthigai = db.dGetKirthigai(df);
                String showAmavasai = db.dGetAmavasai(df);
                String showPournami = db.dGetPournami(df);
                String showUpdownday = setOtherdetails.get(10);

                if (showUpdownday.equalsIgnoreCase("0")) {
                    arrow_icons.setImageResource(R.drawable.samnokunal_white_icon);
                    arrow_icons.setVisibility(View.VISIBLE);
                    isValueavailable = true;
                } else if (showUpdownday.equalsIgnoreCase("1")) {
                    arrow_icons.setImageResource(R.drawable.up_white_icon);
                    arrow_icons.setVisibility(View.VISIBLE);
                    isValueavailable = true;
                } else if (showUpdownday.equalsIgnoreCase("2")) {
                    arrow_icons.setImageResource(R.drawable.down_white_icon);
                    arrow_icons.setVisibility(View.VISIBLE);
                    isValueavailable = true;
                }

                arrow_icons.setTag(R.id.arrow_img, showUpdownday);
                arrow_icons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String strArrow = (String) view.getTag(R.id.arrow_img);
                            if (strArrow.equalsIgnoreCase("0")) {
                                Toast.makeText(GridMenuActivity.this, "இன்று சமநோக்கு நாள் ", Toast.LENGTH_LONG).show();
                            } else if (strArrow.equalsIgnoreCase("1")) {
                                Toast.makeText(GridMenuActivity.this, "இன்று மேல்நோக்கு நாள் ", Toast.LENGTH_LONG).show();
                            } else if (strArrow.equalsIgnoreCase("2")) {
                                Toast.makeText(GridMenuActivity.this, "இன்று கீழ்நோக்கு நாள் ", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                int posPournami = 0;
                if (showKirthigai != null) {
                    posPournami = 1;
                    pournamiamavasaiImg.setImageResource(R.drawable.kirthikai_white_icon);
                    pournamiamavasaiImg.setVisibility(View.VISIBLE);
                    isValueavailable = true;
                } else if (showAmavasai != null) {
                    posPournami = 2;
                    pournamiamavasaiImg.setImageResource(R.drawable.pournami_white_icon);
                    pournamiamavasaiImg.setVisibility(View.VISIBLE);
                    isValueavailable = true;
                } else if (showPournami != null) {
                    posPournami = 3;
                    pournamiamavasaiImg.setImageResource(R.drawable.amavasai_white_icon);
                    pournamiamavasaiImg.setVisibility(View.VISIBLE);
                    isValueavailable = true;
                }


                if (posPournami != 0)
                    pournamiamavasaiImg.setTag(R.id.valarpirai, posPournami);
                pournamiamavasaiImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            int value = (int) v.getTag(R.id.valarpirai);
                            if (value == 1)
                                Toast.makeText(GridMenuActivity.this, getResources().getString(R.string.kirthigai), Toast.LENGTH_LONG).show();
                            else if (value == 2)
                                Toast.makeText(GridMenuActivity.this, getResources().getString(R.string.amavasai), Toast.LENGTH_LONG).show();
                            else if (value == 3)
                                Toast.makeText(GridMenuActivity.this, getResources().getString(R.string.pournami), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                ArrayList<String> subamugurthamList = db.GetSubhaMugurthamDetails1();
                ArrayList<String> fastingList = db.getFastingDetails();
                ArrayList<String> pournamiAmavasaiList = db.getPournamiAmavasai();

                String engDate = getCurrentmonth + " " + setTamilday;


                for (int i = 0; i < subamugurthamList.size(); i++) {
                    if (engDate.equalsIgnoreCase(subamugurthamList.get(i))) {
                        subamugurthamImg.setVisibility(View.VISIBLE);
                        isValueavailable = true;
                    }
                }
                for (int i = 0; i < fastingList.size(); i++) {
                    if (splitDate.equalsIgnoreCase(fastingList.get(i))) {
                        fastingImg.setVisibility(View.VISIBLE);
                        isValueavailable = true;
                    }
                }
//                for (int i = 0; i < pournamiAmavasaiList.size(); i++) {
//                    if (splitDate.equalsIgnoreCase(pournamiAmavasaiList.get(i))) {
//                        pournamiamavasaiImg.setVisibility(View.VISIBLE);
//                        isValueavailable = true;
//                    }
//                }

                if (isValueavailable)
                    weatherLayout.setVisibility(View.VISIBLE);

                subamugurthamImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            progressBar.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(GridMenuActivity.this, SubamugurthamDays.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                fastingImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            progressBar.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(GridMenuActivity.this, FastingDays.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                pournamiamavasaiImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            progressBar.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(GridMenuActivity.this, PournamiAmavasai.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

//            String[] dateSplit = setDate.split(",");
//            String strDate = dateSplit[0];
//            String[] splitSpace = strDate.split(" ");
//            setDate = splitSpace[0] + "," + dateSplit[1];

            if (setOtherdetails.get(1).contains(getResources().getString(R.string.pournami))) {
                tThithi.setText(setOtherdetails.get(1));
                tThithi.setTextColor(Color.parseColor("#9966CC"));
                tThithi.setTypeface(Typeface.DEFAULT_BOLD);
            } else if (setOtherdetails.get(1).contains(getResources().getString(R.string.amavasai_db))) {
                tThithi.setText(setOtherdetails.get(1));
                tThithi.setTextColor(Color.parseColor("#9966CC"));
                tThithi.setTypeface(Typeface.DEFAULT_BOLD);
            } else if (setOtherdetails.get(1).contains(getResources().getString(R.string.yehadasi))) {
                tThithi.setText(setOtherdetails.get(1));
                tThithi.setTextColor(Color.parseColor("#9966CC"));
                tThithi.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                tThithi.setText(setOtherdetails.get(1));
                tThithi.setTextColor(Color.BLACK);
                tThithi.setTypeface(Typeface.DEFAULT);
            }


            tTamilday.setText(setTamilday);
            tTamilday.setTypeface(MiddlewareInterface.tf_didot);
            tCurrentdate.setText(dayOfTheWeek + " | " + setDate + "");
            tCurrentdate.setTypeface(Typeface.DEFAULT_BOLD);
            tStar.setText(setOtherdetails.get(0));
            tStar.setTypeface(Typeface.DEFAULT);
            tYoham.setText(setOtherdetails.get(2));
            tYoham.setTypeface(Typeface.DEFAULT);
            tChandrastam.setText(setOtherdetails.get(3));
            tChandrastam.setTypeface(Typeface.DEFAULT);

            tamilDate.setText(setTamilday1);
            tamilDate.setTypeface(MiddlewareInterface.tf_didot, Typeface.BOLD);

            if (MiddlewareInterface.bRendering) {

                tMonthYear.setText(arrTamilYearMonth[0]);

                tMonthYear.setTypeface(Typeface.DEFAULT);

                tMonth.setText(setTamilmonth);

                tMonth.setTypeface(Typeface.DEFAULT);

                tCurrentMonth.setText(getCurrentmonth);
                tCurrentMonth.setTypeface(Typeface.DEFAULT);

                tWeekOfMonth.setText(setWeekday);
                tWeekOfMonth.setTypeface(Typeface.DEFAULT);

            } else {

                Boolean isDateAfter = MiddlewareInterface.DateComparator(df);
                if (isDateAfter) {
                    tMonthYear.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.currentYear1)));
                } else {
                    tMonthYear.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.currentYear)));
                }

                tMonthYear.setTypeface(MiddlewareInterface.tf_mylai);

                tMonth.setText(UnicodeUtil.unicode2tsc(setTamilmonth));
                tMonth.setTypeface(MiddlewareInterface.tf_mylai);

                tCurrentMonth.setText(UnicodeUtil.unicode2tsc(getCurrentmonth));
                tCurrentMonth.setTypeface(MiddlewareInterface.tf_mylai);

                tWeekOfMonth.setText(UnicodeUtil.unicode2tsc(setWeekday));
                tWeekOfMonth.setTypeface(MiddlewareInterface.tf_mylai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void expand(final TableLayout v) {
        int matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(((View) v.getParent()).getWidth(), View.MeasureSpec.EXACTLY);
        int wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? TableLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Expansion speed of 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final TableLayout v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // Collapse speed of 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    private void exitAlertAction() {
        try {
            final RelativeLayout exitContentLayout = (RelativeLayout) findViewById(R.id.exitContentLayout);
            TextView appTitleTv = (TextView) findViewById(R.id.appTitleTv);
            TextView quoteTv = (TextView) findViewById(R.id.quoteTv);
            TextView authorNameTv = (TextView) findViewById(R.id.quoteAuthorNameTv);
            TextView exitAlertTv = (TextView) findViewById(R.id.exitAlertTv);
            final TextView yesTv = (TextView) findViewById(R.id.yesTv);
            final TextView noTv = (TextView) findViewById(R.id.noTv);
            exitAlertBackgroundLayout.setVisibility(View.VISIBLE);

            exitContentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            exitAlertBackgroundLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    exitAlertBackgroundLayout.setVisibility(View.GONE);
                }
            });
            yesTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, yesTv);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
//                                onBackPressed();
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
            noTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, noTv);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                exitAlertBackgroundLayout.setVisibility(View.GONE);
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

            Calendar calendar = Calendar.getInstance();
            int currentdayOfyear = calendar.get(Calendar.DAY_OF_YEAR);
            try {
                int iQuoteId = 1;
                try {
                    int min = 1;
                    int max = 830;
                    Random r = new Random();
                    iQuoteId = r.nextInt(max - min + 1) + min;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayList<String> arrQuoteList = db.mGetQuotes(iQuoteId);

                try {
                    if (MiddlewareInterface.bRendering) {
                        appTitleTv.setText((getResources().getString(R.string.app_head)));
                        quoteTv.setText("\"" + arrQuoteList.get(1) + "\"");
                        authorNameTv.setText("- " + arrQuoteList.get(0));
                        exitAlertTv.setText((getResources().getString(R.string.exitAlertTex)));
                        yesTv.setText((getResources().getString(R.string.yesText)));
                        noTv.setText((getResources().getString(R.string.noText)));
                        appTitleTv.setTypeface(Typeface.DEFAULT);
                        quoteTv.setTypeface(Typeface.DEFAULT_BOLD);
                        authorNameTv.setTypeface(Typeface.DEFAULT_BOLD);
                        exitAlertTv.setTypeface(Typeface.DEFAULT);
                        noTv.setTypeface(Typeface.DEFAULT);
                        yesTv.setTypeface(Typeface.DEFAULT);
                    } else {
                        appTitleTv.setText(UnicodeUtil.unicode2tsc((getResources().getString(R.string.app_head))));
                        quoteTv.setText(UnicodeUtil.unicode2tsc(arrQuoteList.get(1)));
                        authorNameTv.setText(UnicodeUtil.unicode2tsc("- " + arrQuoteList.get(0)));
                        exitAlertTv.setText(UnicodeUtil.unicode2tsc((getResources().getString(R.string.exitAlertTex))));
                        yesTv.setText(UnicodeUtil.unicode2tsc((getResources().getString(R.string.yesText))));
                        noTv.setText(UnicodeUtil.unicode2tsc((getResources().getString(R.string.noText))));
                        appTitleTv.setTypeface(MiddlewareInterface.tf_mylai);
                        quoteTv.setTypeface(MiddlewareInterface.tf_mylai);
                        authorNameTv.setTypeface(MiddlewareInterface.tf_mylai);
                        exitAlertTv.setTypeface(MiddlewareInterface.tf_mylai);
                        noTv.setTypeface(MiddlewareInterface.tf_mylai);
                        yesTv.setTypeface(MiddlewareInterface.tf_mylai);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        try {
            if (updateBackgroundLayout.getVisibility() == View.VISIBLE) {
                if (SplashScreen.isForceUpdate != 1) {
                    updateBackgroundLayout.setVisibility(View.GONE);
                } else {
                    super.onBackPressed();
                }

            } else {
                if (rateAppBackgroundLayout.getVisibility() == View.VISIBLE) {
                    rateAppBackgroundLayout.setVisibility(View.GONE);
                } else if (todayThought_BGLayout.getVisibility() == View.VISIBLE) {
                    todayThought_BGLayout.setVisibility(View.GONE);
                } else if (exitAlertBackgroundLayout.getVisibility() == View.VISIBLE) {
                    exitAlertBackgroundLayout.setVisibility(View.GONE);
                } else
                    exitAlertAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isTablet() {
        return (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public void checkInternetConnectionStories() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                    Intent intent = new Intent(this, StoriesAndArticlesActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Intent intent = new Intent(this, StoriesAndArticlesActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                Toast toast = Toast.makeText(GridMenuActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                toast.show();
                progressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
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

    private void setAdvertise() {
        try {
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


    private void firebaseAnalytics(int pos) {
        try {
//            if (newsTitle != null) {
            String title = arrItemName[pos];
//                title = title.replace("null", "");
            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
            firebaseAnalytics.setAnalyticsCollectionEnabled(true);
            firebaseAnalytics.setSessionTimeoutDuration(5000);
            firebaseAnalytics.setCurrentScreen(this, title, null);
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, title);
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, title);
            bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, title);
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 11) {
            inflateNotesLayout();
        }
    }

    @Override
    protected void onResume() {
        try {
            super.onResume();
            if (progressBar != null)
                progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoDayActivity(Intent intent) {
        try {
            intent = new Intent(this, DayActivity.class);
            intent.putExtra("UtilityArray", UtilityArrayList);
            intent.putExtra("categoryId", catId);
            intent.putExtra("viewpagerPos", pagerPos);
            intent.putExtra("rasipalan", rasipalanId);
            intent.putExtra("rasipalan", rasipalanId);
            intent.putExtra("crntDate", crntDate);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoMonthActivity(Intent intent) {
        try {
            intent = new Intent(this, MonthActivity.class);
            intent.putExtra("UtilityArray", UtilityArrayList);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoFastingDaysActivity(Intent intent) {
        try {
            intent = new Intent(this, FastingDays.class);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectItem(int position) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            firebaseAnalytics(position);
            try {
                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = settings.edit();
                int val = settings.getInt("interstitialcount", 0);
                val++;
                editor.putInt("interstitialcount", val);
                editor.apply();
                if (val == 4) {
                    editor.putInt("interstitialcount", 0);
                    editor.apply();
                    MiddlewareInterface.LoadInterstitialAd(getApplicationContext());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (adapter != null) {
                Intent intent = null;
                if (adapter.getCount() == 28) {
                    switch (position) {
                        case 0:
                            gotoDayActivity(intent);
                            break;
                        case 1:
                            gotoMonthActivity(intent);
                            break;
                        case 2:
                            gotoFastingDaysActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(this, PournamiAmavasai.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(this, Holidays.class);
                            startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(this, GowriPanchangam.class);
                            intent.putExtra("categoryId", 1);
                            startActivity(intent);
                            break;
                        case 6:
                            intent = new Intent(this, GowriPanchangam.class);
                            intent.putExtra("categoryId", 2);
                            startActivity(intent);
                            break;

                        case 7:
                            intent = new Intent(this, GurupeyarchiPalangalActivity.class);
                            intent.putExtra("flag", 1);
                            startActivity(intent);
                            break;

                        case 8:
                            intent = new Intent(this, GurupeyarchiPalangalActivity.class);
                            intent.putExtra("flag", 2);
                            startActivity(intent);
                            break;
                        case 9:
                            intent = new Intent(this, MarriageMatchActivity.class);
                            startActivity(intent);
                            break;
                        case 10:
                            intent = new Intent(this, HistoryOfTodayActivity.class);
                            startActivity(intent);
                            break;
                        case 11:
                            intent = new Intent(this, FestivalDays.class);
                            startActivity(intent);
                            break;
                        case 12:
                            intent = new Intent(this, ManaiyadiSasthiram.class);
                            startActivity(intent);
                            break;
                        case 13:
                            intent = new Intent(this, SubamugurthamDays.class);
                            startActivity(intent);
                            break;
                        case 14:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickDay);
                            startActivity(intent);
                            break;
                        case 15:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickMonth);
                            startActivity(intent);
                            break;
                        case 16:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickYear);
                            startActivity(intent);
                            break;
                        case 17:
                            intent = new Intent(this, NewsActivity.class);
                            startActivity(intent);
                            break;
                        case 18:
                            checkInternetConnection();
                            break;
                        case 19:
                            checkInternetConnectionUtility();
                            break;
                        case 20:
                            checkInternetConnectionStories();
                            break;
                        case 21:
                            intent = new Intent(this, KanavuPalangalActivity.class);
                            startActivity(intent);
                            break;
                        case 22:
                            intent = new Intent(this, PallivizhumPalangalActivity.class);
                            startActivity(intent);
                            break;
                        case 23:
                            intent = new Intent(this, BabyNamesActivity.class);
                            startActivity(intent);
                            break;
                        case 24:
                            if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
                                intent = new Intent(this, DevotionalWallpapersActivity.class);
                                startActivity(intent);
                            } else {
                                Toast toast = Toast.makeText(this, "Need Internet Connection", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            break;
                        case 25:
                            intent = new Intent(getApplicationContext(), NotesActivity.class);
                            startActivityForResult(intent, 11);
                            isNotesIntent = true;
                            break;
//                        case 25:
//                            intent = new Intent(getApplicationContext(), WeatherCheck.class);
//                            startActivity(intent);
//                            break;
                        case 26:
                            intent = new Intent(this, NotificationSettings.class);
                            startActivity(intent);
                            break;
                        case 27:
                            intent = new Intent(this, Help.class);
                            startActivity(intent);
                            break;
                        case 28:
                            intent = new Intent(this, AboutUsActivity.class);
                            startActivity(intent);
                            break;
                        case 29:
                            Intent sendIntentt = new Intent();
                            sendIntentt.setAction(Intent.ACTION_SEND);
                            sendIntentt.putExtra(Intent.EXTRA_TEXT, "அன்றாட நாட்களுக்கான தகவல்கள், பண்டிகைகள் நாட்கள், விடுமுறை நாட்கள், முக்கிய தினங்கள், இராசி பலன்கள் மற்றும் பல தகவல்கள் அடங்கிய இந்த சுபம் நாட்காட்டியின் செயலியை கீழ் கண்ட லிங்கை கிளிக் செய்து பதிவிறக்கம் செய்து கொள்ளவும். \n\n https://play.google.com/store/apps/details?id=com.softcraft.calendar&hl=en");
                            sendIntentt.setType("text/plain");
                            Intent intentpass = Intent.createChooser(sendIntentt, "Share Subam Tamil Calendar");
                            intentpass.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentpass);
                            break;
                        default:
                            break;
                    }
                } else {
                    switch (position) {
                        case 0:
                            intent = new Intent(this, DayActivity.class);
                            intent.putExtra("UtilityArray", UtilityArrayList);
                            intent.putExtra("categoryId", catId);
                            intent.putExtra("viewpagerPos", pagerPos);
                            intent.putExtra("rasipalan", rasipalanId);
                            intent.putExtra("crntDate", crntDate);
                            startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(this, MonthActivity.class);
                            intent.putExtra("UtilityArray", UtilityArrayList);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(this, FastingDays.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(this, PournamiAmavasai.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(this, Holidays.class);
                            startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(this, GowriPanchangam.class);
                            intent.putExtra("categoryId", 1);
                            startActivity(intent);
                            break;
                        case 6:
                            intent = new Intent(this, GowriPanchangam.class);
                            intent.putExtra("categoryId", 2);
                            startActivity(intent);
                            break;

                        case 7:
                            intent = new Intent(this, GurupeyarchiPalangalActivity.class);
                            intent.putExtra("flag", 1);
                            startActivity(intent);
                            break;
                        case 8:
                            intent = new Intent(this, GurupeyarchiPalangalActivity.class);
                            intent.putExtra("flag", 2);
                            startActivity(intent);
                            break;
                        case 9:
                            intent = new Intent(this, MarriageMatchActivity.class);
                            startActivity(intent);
                            break;
                        case 10:
                            intent = new Intent(this, HistoryOfTodayActivity.class);
                            startActivity(intent);
                            break;
                        case 11:
                            intent = new Intent(this, FestivalDays.class);
                            startActivity(intent);
                            break;
                        case 12:
                            intent = new Intent(this, ManaiyadiSasthiram.class);
                            startActivity(intent);
                            break;
                        case 13:
                            intent = new Intent(this, SubamugurthamDays.class);
                            startActivity(intent);
                            break;
                        case 14:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickDay);
                            startActivity(intent);
                            break;
                        case 15:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickMonth);
                            startActivity(intent);
                            break;
                        case 16:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickYear);
                            startActivity(intent);
                            break;
                        case 17:
                            intent = new Intent(this, NewsActivity.class);
                            startActivity(intent);
                            break;
                        case 18:
                            checkInternetConnection();
                            break;
                        case 19:
                            checkInternetConnectionUtility();
                            break;
                        case 20:
                            checkInternetConnectionStories();
                            break;
                        case 21:
                            intent = new Intent(this, KanavuPalangalActivity.class);
                            startActivity(intent);
                            break;
                        case 22:
                            intent = new Intent(this, PallivizhumPalangalActivity.class);
                            startActivity(intent);
                            break;
                        case 23:
                            intent = new Intent(this, BabyNamesActivity.class);
                            startActivity(intent);
                            break;
                        case 24:
                            if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
                                intent = new Intent(this, DevotionalWallpapersActivity.class);
                                startActivity(intent);
                            } else {
                                Toast toast = Toast.makeText(this, "Need Internet Connection", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            break;
                        case 25:
                            intent = new Intent(getApplicationContext(), NotesActivity.class);
                            startActivityForResult(intent, 11);
                            isNotesIntent = true;
                            break;
//                        case 25:
//                            intent = new Intent(getApplicationContext(), WeatherCheck.class);
//                            startActivity(intent);
//                            break;
                        case 26:
                            intent = new Intent(this, NotificationSettings.class);
                            startActivity(intent);
                            break;
                        case 27:
                            intent = new Intent(this, Help.class);
                            startActivity(intent);
                            break;
                        case 28:
                            intent = new Intent(this, AboutUsActivity.class);
                            startActivity(intent);
                            break;
                        case 29:
                            Intent sendIntentt = new Intent();
                            sendIntentt.setAction(Intent.ACTION_SEND);
                            sendIntentt.putExtra(Intent.EXTRA_TEXT, "அன்றாட நாட்களுக்கான தகவல்கள், பண்டிகைகள் நாட்கள், விடுமுறை நாட்கள், முக்கிய தினங்கள், இராசி பலன்கள் மற்றும் பல தகவல்கள் அடங்கிய இந்த சுபம் நாட்காட்டியின் செயலியை கீழ் கண்ட லிங்கை கிளிக் செய்து பதிவிறக்கம் செய்து கொள்ளவும். \n\n https://play.google.com/store/apps/details?id=com.softcraft.calendar&hl=en");
                            sendIntentt.setType("text/plain");
                            Intent intentpass = Intent.createChooser(sendIntentt, "Share Subam Tamil Calendar");
                            intentpass.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentpass);
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<String> getTodayHistory(int month, int date) {
        List<String> historyList = CommonCalendarData.getDayHistoryDetails(month, date);
        return historyList;
    }


    private void ratetheAppFunction() {
        try {
            rateAppBackgroundLayout = (RelativeLayout) findViewById(R.id.rateAppBackgroundLayout);
            final RelativeLayout rateNowLayout = (RelativeLayout) findViewById(R.id.rateNowLayout);
            final RelativeLayout remindMeLaterLayout = (RelativeLayout) findViewById(R.id.remindMeLaterLayout);
            final RelativeLayout noThanksLayout = (RelativeLayout) findViewById(R.id.noThanksLayout);
            Calendar calendar = Calendar.getInstance();
            int currentdayOfyear = calendar.get(Calendar.DAY_OF_YEAR);

            final SharedPreferences rateAlert_pref = PreferenceManager.getDefaultSharedPreferences(GridMenuActivity.this);
            int getAlertDate = rateAlert_pref.getInt("nextAlertShowDate", 0);
            if (getAlertDate == 0) {
                int putAlertDate = currentdayOfyear + 7;
                SharedPreferences.Editor editor = rateAlert_pref.edit();
                editor.putInt("nextAlertShowDate", putAlertDate);
                editor.apply();
            } else if (currentdayOfyear == getAlertDate) {
                rateAppBackgroundLayout.setVisibility(View.VISIBLE);
                int nextAlertShowDate = currentdayOfyear + 7;
                SharedPreferences.Editor editor = rateAlert_pref.edit();
                editor.putInt("nextAlertShowDate", nextAlertShowDate);
                editor.apply();
            }
            rateNowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, rateNowLayout);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.softcraft.calendar"));
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
            remindMeLaterLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, remindMeLaterLayout);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                rateAppBackgroundLayout.setVisibility(View.GONE);
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
            noThanksLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GridMenuActivity.this, noThanksLayout);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                rateAppBackgroundLayout.setVisibility(View.GONE);
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

    private void todaysThoughtFunc() {
        try {
            todayThought_BGLayout = (RelativeLayout) findViewById(R.id.todaysThought_BGLayout);
            final SharedPreferences rateAlert_pref = PreferenceManager.getDefaultSharedPreferences(this);
            String lastdate = rateAlert_pref.getString("PreviousDate", "1/1/1990");

            Calendar currentDate = Calendar.getInstance();
            String strCurrrentDate = currentDate.get(Calendar.DATE) + "/" + currentDate.get(Calendar.MONTH) + "/" + currentDate.get(Calendar.YEAR);

            if (!strCurrrentDate.equalsIgnoreCase(lastdate)) {
                RelativeLayout todayThought_CntLayout = (RelativeLayout) findViewById(R.id.todaysThought_CNTLayout);
                todayThought_BGLayout.setVisibility(View.VISIBLE);

                AutofitTextView kuralTv = (AutofitTextView) findViewById(R.id.kuralTV);

                TextView kuralVilakkamTv = (TextView) findViewById(R.id.kuralVilakkamTv);


                todayThought_BGLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (todayThought_BGLayout.getVisibility() == View.VISIBLE) {
                                todayThought_BGLayout.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                todayThought_CntLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                int kuralId = -1;
                try {
                    int min = 1;
                    int max = 1080;
                    Random r = new Random();
                    kuralId = r.nextInt(max - min + 1) + min;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ArrayList<String> dailyQuoteList = db.mGetKuralDetails(kuralId);
                String strDailyKural = dailyQuoteList.get(7);
                String[] arrKural = strDailyKural.split(" ");

                String sFirstLine = arrKural[0] + " " + arrKural[1] + " " + arrKural[2] + " " + arrKural[3];
                String sSecondLine = arrKural[4] + " " + arrKural[5] + " " + arrKural[6];

                strDailyKural = sFirstLine + "\n" + sSecondLine;

                try {
                    if (MiddlewareInterface.bRendering) {
                        kuralTv.setText(strDailyKural);

                        kuralVilakkamTv.setText(dailyQuoteList.get(8));

                        kuralTv.setTypeface(Typeface.DEFAULT_BOLD);

                        kuralVilakkamTv.setTypeface(Typeface.DEFAULT);

                    } else {
                        kuralTv.setText(UnicodeUtil.unicode2tsc(strDailyKural));

                        kuralVilakkamTv.setText(UnicodeUtil.unicode2tsc((dailyQuoteList.get(8))));

                        kuralTv.setTypeface(MiddlewareInterface.tf_mylai);

                        kuralVilakkamTv.setTypeface(MiddlewareInterface.tf_mylai);

                    }

                    try {
                        SharedPreferences.Editor editor = rateAlert_pref.edit();
                        editor.putString("PreviousDate", strCurrrentDate);
                        editor.apply();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void todaysThoughtFunc1() {
        try {

            final TextView kuralVilakkamTv1 = findViewById(R.id.kuralVilakkamTv1);
            final ImageView downuparrowImg = findViewById(R.id.downuparrowImg);
            AutofitTextView kuralTV1 = findViewById(R.id.kuralTV1);
            RelativeLayout todayMsgFullLayout = findViewById(R.id.todayMsgFullLayout);
            int kuralId = -1;
            try {
                int min = 1;
                int max = 1080;
                Random r = new Random();
                kuralId = r.nextInt(max - min + 1) + min;
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<String> dailyQuoteList = db.mGetKuralDetails(kuralId);
            String strDailyKural = dailyQuoteList.get(7);
            String[] arrKural = strDailyKural.split(" ");

            String sFirstLine = arrKural[0] + " " + arrKural[1] + " " + arrKural[2] + " " + arrKural[3];
            String sSecondLine = arrKural[4] + " " + arrKural[5] + " " + arrKural[6];
            strDailyKural = sFirstLine + "\n" + sSecondLine;
            try {
                if (MiddlewareInterface.bRendering) {
                    kuralTV1.setText(strDailyKural);
                    kuralVilakkamTv1.setText(Html.fromHtml(getResources().getString(R.string.desc_title) + "\n" + dailyQuoteList.get(8).replace("மு.வ :", "(மு.வ) ")));
                    kuralTV1.setTypeface(Typeface.DEFAULT_BOLD);
                    kuralVilakkamTv1.setTypeface(Typeface.DEFAULT);
                } else {
                    kuralTV1.setText(UnicodeUtil.unicode2tsc(strDailyKural));
                    kuralVilakkamTv1.setText(Html.fromHtml(UnicodeUtil.unicode2tsc(getResources().getString(R.string.desc_title) + "\n" + dailyQuoteList.get(8))));
                    kuralTV1.setTypeface(MiddlewareInterface.tf_mylai);
                    kuralVilakkamTv1.setTypeface(MiddlewareInterface.tf_mylai);
                }


                kuralVilakkamTv1.setMaxLines(0);
                todayMsgFullLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            int maxlines = kuralVilakkamTv1.getMaxLines();
                            if (maxlines == 0) {
                                RotateAnimation rotate = new RotateAnimation(180, 0, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
                                rotate.setDuration(100);
                                rotate.setInterpolator(new LinearInterpolator());
                                downuparrowImg.startAnimation(rotate);
                                downuparrowImg.setImageResource(R.drawable.up_arw_green_ic);
                                kuralVilakkamTv1.setMaxLines(Integer.MAX_VALUE);
                            } else {
                                RotateAnimation rotate = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                                rotate.setDuration(100);
                                rotate.setInterpolator(new LinearInterpolator());
                                downuparrowImg.startAnimation(rotate);
                                downuparrowImg.setImageResource(R.drawable.down_arw_green_ic);
                                kuralVilakkamTv1.setMaxLines(0);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
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
                    progressBar.setVisibility(View.GONE);
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Intent intent = new Intent(this, DevotionalSongsActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String jsonStr = app_preferences.getString("devotionalSongsResponse", "");
                if (jsonStr.equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(GridMenuActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                    toast.show();
                    progressBar.setVisibility(View.GONE);
                } else {
                    Intent intent = new Intent(this, DevotionalSongsActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }


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
                if (UtilityArrayList != null && UtilityArrayList.size() == 0) {
                    Toast toast = Toast.makeText(GridMenuActivity.this, "Please ReLaunch the app to load", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Intent intent = new Intent(this, UtilitySongsActivity.class);
                    intent.putExtra("UtilityArray", UtilityArrayList);
                    startActivity(intent);

                }
            } else {

                SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String jsonStr = app_preferences.getString("utilitySongsResponse", "");
                if (jsonStr.equalsIgnoreCase("")) {
                    Toast toast = Toast.makeText(GridMenuActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    Intent intent = new Intent(this, UtilitySongsActivity.class);
                    intent.putExtra("UtilityArray", UtilityArrayList);
                    startActivity(intent);
                }


            }
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
            progressBar.setVisibility(View.GONE);
        }
    }

    private void GotoDeeplinkingCategory() {
        try {
            int catId = getIntent().getIntExtra("categoryId", -1);
            int pagerPos = getIntent().getIntExtra("viewpagerPos", -1);
            String rasipalan = getIntent().getStringExtra("rasipalan");

            if (catId == 1) {
                Intent intent = new Intent(getApplicationContext(), DayActivity.class);
                intent.putExtra("viewpagerPos", pagerPos);
                startActivity(intent);
            } else if (catId == 2) {
                Intent intent = new Intent(getApplicationContext(), MonthActivity.class);
                intent.putExtra("viewpagerPos", pagerPos);
                startActivity(intent);
            } else if (catId == 3) {
                Intent intent = new Intent(getApplicationContext(), FastingDays.class);
                startActivity(intent);
            } else if (catId == 4) {
                Intent intent = new Intent(getApplicationContext(), PournamiAmavasai.class);
                startActivity(intent);
            } else if (catId == 5) {
                Intent intent = new Intent(getApplicationContext(), Holidays.class);
                startActivity(intent);
            } else if (catId == 6) {
                Intent intent = new Intent(getApplicationContext(), GowriPanchangam.class);
                intent.putExtra("categoryId", 2);
                startActivity(intent);
            } else if (catId == 7) {
                Intent intent = new Intent(getApplicationContext(), GowriPanchangam.class);
                intent.putExtra("categoryId", 1);
                startActivity(intent);
            } else if (catId == 8) {
                Intent intent = new Intent(getApplicationContext(), GurupeyarchiPalangalActivity.class);
                intent.putExtra("flag", 1);
                startActivity(intent);
            } else if (catId == 9) {
                Intent intent = new Intent(getApplicationContext(), GurupeyarchiPalangalActivity.class);
                intent.putExtra("flag", 2);
                startActivity(intent);
            } else if (catId == 10) {
                Intent intent = new Intent(getApplicationContext(), MarriageMatchActivity.class);
                startActivity(intent);
            } else if (catId == 11) {
                Intent intent = new Intent(getApplicationContext(), HistoryOfTodayActivity.class);
                startActivity(intent);
            } else if (catId == 12) {
                Intent intent = new Intent(getApplicationContext(), FestivalDays.class);
                startActivity(intent);
            } else if (catId == 13) {
                Intent intent = new Intent(getApplicationContext(), ManaiyadiSasthiram.class);
                startActivity(intent);
            } else if (catId == 14) {
                Intent intent = new Intent(getApplicationContext(), SubamugurthamDays.class);
                startActivity(intent);
            } else if (catId == 16) {
                String[] strArrRasipalan = rasipalan.split("-");

                if (strArrRasipalan[0].equalsIgnoreCase("Month")) {
                    Intent intent = new Intent(this, HoroscopeURL.class);
                    intent.putExtra("isClick", strArrRasipalan[0]);
                    intent.putExtra("rasipalanId", strArrRasipalan[1]);
                    startActivity(intent);

                }
            } else if (catId == 17) {
                String[] strArrRasipalan = rasipalan.split("-");

                if (strArrRasipalan[0].equalsIgnoreCase("Year")) {
                    Intent intent = new Intent(this, HoroscopeURL.class);
                    intent.putExtra("isClick", strArrRasipalan[0]);
                    intent.putExtra("rasipalanId", strArrRasipalan[1]);
                    startActivity(intent);
                }

            } else if (catId == 19) {
                checkInternetConnectionDevotional();
            } else if (catId == 20) {
                checkInternetConnectionUtility();
            } else if (catId == 21) {
                checkInternetConnectionStories();
            } else if (catId == 22) {
                Intent intent = new Intent(getApplicationContext(), KanavuPalangalActivity.class);
                startActivity(intent);
            } else if (catId == 23) {
                Intent intent = new Intent(getApplicationContext(), PallivizhumPalangalActivity.class);
                startActivity(intent);
            } else if (catId == 24) {
                Intent intent = new Intent(getApplicationContext(), BabyNamesActivity.class);
                startActivity(intent);
            } else if (catId == 25) {
                if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
                    Intent intent = new Intent(this, DevotionalWallpapersActivity.class);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(this, "Need Internet Connection", Toast.LENGTH_LONG);
                    toast.show();
                }
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
                    progressBar.setVisibility(View.GONE);
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Intent intent = new Intent(this, DevotionalSongsActivity.class);
                    startActivity(intent);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                Toast toast = Toast.makeText(GridMenuActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                toast.show();
                progressBar.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



