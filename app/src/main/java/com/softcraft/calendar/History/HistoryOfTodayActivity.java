package com.softcraft.calendar.History;


import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.softcraft.calendar.Database.DataBaseHelper;

import com.softcraft.calendar.Fragment.HistoryFragment;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;
import static java.util.Calendar.DAY_OF_MONTH;

//import com.whiture.apps.tamil.calendar.data.CommonCalendarData;
//import com.whiture.apps.tamil.calendar.view.CommonAdapter;

public class HistoryOfTodayActivity extends AppCompatActivity implements OnItemClickListener {
    private int[] headerIndices;
    private int monthIndex;
    private List<String> sections;
    private TypeOfDaysShown typeOfDaysShown;
    ViewPager viewPager;
    DataBaseHelper db;
    TabLayout tabLayout;
    RelativeLayout rootLayout;
    ProgressBar progressBar;
    LinearLayout linearad;
    AdView adview;
    private NativeExpressAdView viewNativeAd;

    private enum TypeOfDaysShown {
        MONTHS,
        DAYS,
        DETAILS
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            setContentView(R.layout.thatday_list_layout);
            this.sections = new ArrayList();

            rootLayout = (RelativeLayout) findViewById(R.id.root_thatday_list);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            viewPager = findViewById(R.id.viewPager);
            linearad = (LinearLayout) findViewById(R.id.adview);
            ImageView click_back_image = findViewById(R.id.click_back_image);
            TextView show_title = findViewById(R.id.show_title);
            show_title.setText("வரலாற்றில் இன்று ");
            show_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            click_back_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            try {
                db = new DataBaseHelper(this);
                tabLayout = findViewById(R.id.tabs);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            displayMonths();

            setAdapter();
            ShareFunc();

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

    private void setAdapter() {
        try {
            ArrayList<String> tamilDaysList = new ArrayList<>();
            tamilDaysList.add("ஜனவரி");
            tamilDaysList.add("பிப்ரவரி");
            tamilDaysList.add("மார்ச்");
            tamilDaysList.add("ஏப்ரல்");
            tamilDaysList.add("மே");
            tamilDaysList.add("ஜூன்");
            tamilDaysList.add("ஜூலை");
            tamilDaysList.add("ஆகஸ்ட்");
            tamilDaysList.add("செப்டம்பர்");
            tamilDaysList.add("அக்டோபர்");
            tamilDaysList.add("நவம்பர்");
            tamilDaysList.add("டிசம்பர்");

            ArrayList<String> dateList = new ArrayList<>();
            ArrayList<String> monthList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            ArrayList<String> listMonth = new ArrayList<>();
            ArrayList<Integer> listNumOfDay = new ArrayList<>();
            ArrayList<List<String>> monthLists = new ArrayList<>();
            ArrayList<List<String>> monthLists2 = new ArrayList<>();
            ArrayList<ArrayList<List<String>>> monthLists1 = new ArrayList<>();
            ArrayList<List<String>> listH = new ArrayList<>();
            ArrayList<List<String>> listH1 = new ArrayList<>();
            ArrayList<ArrayList<List<String>>> listH2 = new ArrayList<>();
            List<String> historyList;
            for (int i = 0; i < 12; i++) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, i);
                int dayOfMonth = calendar.getActualMaximum(DAY_OF_MONTH);
                listNumOfDay.add(dayOfMonth);

                listMonth = new ArrayList<>();
                monthLists2 = new ArrayList<>();
                for (int jj = 1; jj <= dayOfMonth; jj++) {
                    String doubleDigitMonth = String.valueOf(i);
                    String doubleDigitDate = String.valueOf(jj);
                    String strDate = doubleDigitDate;
                    String strMonth = doubleDigitMonth;
                    dateList.add(strDate);
                    monthList.add(strMonth);

                    int j = jj - 1;
                    String ii = i + "";
                    if (ii.equalsIgnoreCase("0")) {
                        listMonth.add("ஜனவரி" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("1")) {
                        listMonth.add("பிப்ரவரி" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("2")) {
                        listMonth.add("மார்ச்" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("3")) {
                        listMonth.add("ஏப்ரல்" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("4")) {
                        listMonth.add("மே" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("5")) {
                        listMonth.add("ஜூன்" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("6")) {
                        listMonth.add("ஜூலை" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("7")) {
                        listMonth.add("ஆகஸ்ட்" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("8")) {
                        listMonth.add("செப்டம்பர்" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("9")) {
                        listMonth.add("அக்டோபர்" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("10")) {
                        listMonth.add("நவம்பர்" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    } else if (ii.equalsIgnoreCase("11")) {
                        listMonth.add("டிசம்பர்" + dateList.get(j));
                        monthLists.add(listMonth);
                        monthLists2.add(listMonth);
                    }
                }
                monthLists1.add(monthLists2);
            }

            for (int i = 0; i < dateList.size(); i++) {
                int date = Integer.parseInt(dateList.get(i));
                int month1 = Integer.parseInt(monthList.get(i));
                historyList = getTodayHistory(month1, date);
                try {
                    listH.add(historyList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            List<String> historyList1;
            for (int i = 0; i < listNumOfDay.size(); i++) {
                int day = listNumOfDay.get(i);
                listH1 = new ArrayList<>();
                for (int j = 0; j < day; j++) {
                    int date = Integer.parseInt(dateList.get(j));
//                    int month1 = Integer.parseInt(monthList.get(i));
                    int month1 = i;

                    historyList = getTodayHistory(month1, date);
                    listH1.add(historyList);
                }
                listH2.add(listH1);
            }

//            }
            ViewPagerPeyarchiPalanAdapter mAdapter = new ViewPagerPeyarchiPalanAdapter(getSupportFragmentManager(), getApplicationContext(), tamilDaysList, monthLists, listH, listH2, monthLists1);
            mAdapter.addFragment(new HistoryFragment(), tamilDaysList);
            viewPager.setAdapter(mAdapter);
            tabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                tab.setCustomView(mAdapter.getTabView(i));
            }
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
//                    tabPs = tab.getPosition();
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> getTodayHistory(int month, int date) {
        List<String> historyList = CommonCalendarData.getDayHistoryDetails(month, date);
        return historyList;
    }

    public void finish() {
        setResult(-1, new Intent());
        super.finish();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (!isInHeaderIndices(i)) {
            switch (this.typeOfDaysShown) {
                case MONTHS:
                    this.monthIndex = i - 1;
                    displayDays(this.monthIndex);
                    return;
                case DAYS:
                    displayDetails(i - 1);
                    return;
                default:
                    return;
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
//        switch (this.typeOfDaysShown) {
//            case MONTHS:
//
//                return;
//            case DAYS:
//                displayMonths();
////                findViewById(R.id.layout_andru).setVisibility(0);
////                findViewById(R.id.that_days_lv).setVisibility(8);
//                return;
//            case DETAILS:
//                addMonthsToSections();
//                displayDays(this.monthIndex);
//                return;
//            default:
//                return;
//        }
    }

    public void onMonthClick(View view) {
        int i;
        switch (view.getId()) {
            case R.id.andru_april:
                i = 4;
                break;
            case R.id.andru_august:
                i = 8;
                break;
            case R.id.andru_december:
                i = 12;
                break;
            case R.id.andru_febraury:
                i = 2;
                break;
            case R.id.andru_january:
                i = 1;
                break;
            case R.id.andru_july:
                i = 7;
                break;
            case R.id.andru_june:
                i = 6;
                break;
            case R.id.andru_march:
                i = 3;
                break;
            case R.id.andru_may:
                i = 5;
                break;
            case R.id.andru_november:
                i = 11;
                break;
            case R.id.andru_october:
                i = 10;
                break;
            case R.id.andru_september:
                i = 9;
                break;
            default:
                i = 0;
                break;
        }
        displayDays(i - 1);
//        findViewById(R.id.layout_andru).setVisibility(8);
//        findViewById(R.id.that_days_lv).setVisibility(0);
    }

    private void displayDays(int i) {
        this.typeOfDaysShown = TypeOfDaysShown.DAYS;
        setTitle("இந்த நாள் அந்த வருடம்");
        int[] iArr = new int[]{0};
        String str = (String) this.sections.get(i + 1);
        this.sections.clear();
        List list = this.sections;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" மாத நாட்களின் வரிசைப்படி");
        list.add(stringBuilder.toString());
        switch (i) {
            case 0:
                this.sections.add("ஜனவரி 1 - 5");
                this.sections.add("ஜனவரி 6 - 10");
                this.sections.add("ஜனவரி 11 - 15");
                this.sections.add("ஜனவரி 16 - 20");
                this.sections.add("ஜனவரி 21 - 25");
                this.sections.add("ஜனவரி 26 - 31");
                break;
            case 1:
                this.sections.add("பிப்ரவரி 1 - 5");
                this.sections.add("பிப்ரவரி 6 - 10");
                this.sections.add("பிப்ரவரி 11 - 15");
                this.sections.add("பிப்ரவரி 16 - 20");
                this.sections.add("பிப்ரவரி 21 - 25");
                this.sections.add("பிப்ரவரி 26 - 29");
                break;
            case 2:
                this.sections.add("மார்ச் 1 - 5");
                this.sections.add("மார்ச் 6 - 10");
                this.sections.add("மார்ச் 11 - 15");
                this.sections.add("மார்ச் 16 - 20");
                this.sections.add("மார்ச் 21 - 25");
                this.sections.add("மார்ச் 26 - 31");
                break;
            case 3:
                this.sections.add("ஏப்ரல் 1 - 5");
                this.sections.add("ஏப்ரல் 6 - 10");
                this.sections.add("ஏப்ரல் 11 - 15");
                this.sections.add("ஏப்ரல் 16 - 20");
                this.sections.add("ஏப்ரல் 21 - 25");
                this.sections.add("ஏப்ரல் 26 - 30");
                break;
            case 4:
                this.sections.add("மே 1 - 5");
                this.sections.add("மே 6 - 10");
                this.sections.add("மே 11 - 15");
                this.sections.add("மே 16 - 20");
                this.sections.add("மே 21 - 25");
                this.sections.add("மே 26 - 31");
                break;
            case 5:
                this.sections.add("ஜூன் 1 - 5");
                this.sections.add("ஜூன் 6 - 10");
                this.sections.add("ஜூன் 11 - 15");
                this.sections.add("ஜூன் 16 - 20");
                this.sections.add("ஜூன் 21 - 25");
                this.sections.add("ஜூன் 26 - 30");
                break;
            case 6:
                this.sections.add("ஜூலை 1 - 5");
                this.sections.add("ஜூலை 6 - 10");
                this.sections.add("ஜூலை 11 - 15");
                this.sections.add("ஜூலை 16 - 20");
                this.sections.add("ஜூலை 21 - 25");
                this.sections.add("ஜூலை 26 - 31");
                break;
            case 7:
                this.sections.add("ஆகஸ்ட் 1 - 5");
                this.sections.add("ஆகஸ்ட் 6 - 10");
                this.sections.add("ஆகஸ்ட் 11 - 15");
                this.sections.add("ஆகஸ்ட் 16 - 20");
                this.sections.add("ஆகஸ்ட் 21 - 25");
                this.sections.add("ஆகஸ்ட் 26 - 31");
                break;
            case 8:
                this.sections.add("செப்டம்பர் 1 - 5");
                this.sections.add("செப்டம்பர் 6 - 10");
                this.sections.add("செப்டம்பர் 11 - 15");
                this.sections.add("செப்டம்பர் 16 - 20");
                this.sections.add("செப்டம்பர் 21 - 25");
                this.sections.add("செப்டம்பர் 26 - 30");
                break;
            case 9:
                this.sections.add("அக்டோபர் 1 - 5");
                this.sections.add("அக்டோபர் 6 - 10");
                this.sections.add("அக்டோபர் 11 - 15");
                this.sections.add("அக்டோபர் 16 - 20");
                this.sections.add("அக்டோபர் 21 - 25");
                this.sections.add("அக்டோபர் 26 - 31");
                break;
            case 10:
                this.sections.add("நவம்பர் 1 - 5");
                this.sections.add("நவம்பர் 6 - 10");
                this.sections.add("நவம்பர் 11 - 15");
                this.sections.add("நவம்பர் 16 - 20");
                this.sections.add("நவம்பர் 21 - 25");
                this.sections.add("நவம்பர் 26 - 30");
                break;
            case 11:
                this.sections.add("டிசம்பர் 1 - 5");
                this.sections.add("டிசம்பர் 6 - 10");
                this.sections.add("டிசம்பர் 11 - 15");
                this.sections.add("டிசம்பர் 16 - 20");
                this.sections.add("டிசம்பர் 21 - 25");
                this.sections.add("டிசம்பர் 26 - 31");
                break;
        }
        ListView listView = (ListView) findViewById(R.id.that_days_lv);
        listView.setAdapter(new CommonAdapter(this, (TamilCalendarApplication) getApplication(), this.sections, iArr));
        listView.setOnItemClickListener(this);
        this.headerIndices = iArr;
    }

    private void addDisplayDetails(String str, int i, int[] iArr, int i2) {
        List list = this.sections;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        stringBuilder.append(" ");
        stringBuilder.append(i);
        list.add(stringBuilder.toString());
        iArr[i2] = this.sections.size() - 1;
        this.sections.addAll(CommonCalendarData.getDayHistoryDetails(this.monthIndex, i));
    }

    private void displayDetails(int i) {
        this.typeOfDaysShown = TypeOfDaysShown.DETAILS;
        setTitle("இந்த நாள் அந்த வருடம்");
        int[] iArr = new int[]{0, 1, 2, 3, 4, 5};
        String str = (String) this.sections.get(i + 1);
        this.sections.clear();
        this.sections.add(str);
        str = str.split(" ")[0];
        iArr[0] = 0;
        switch (i) {
            case 0:
                addDisplayDetails(str, 1, iArr, 1);
                addDisplayDetails(str, 2, iArr, 2);
                addDisplayDetails(str, 3, iArr, 3);
                addDisplayDetails(str, 4, iArr, 4);
                addDisplayDetails(str, 5, iArr, 5);
                break;
            case 1:
                addDisplayDetails(str, 6, iArr, 1);
                addDisplayDetails(str, 7, iArr, 2);
                addDisplayDetails(str, 8, iArr, 3);
                addDisplayDetails(str, 9, iArr, 4);
                addDisplayDetails(str, 10, iArr, 5);
                break;
            case 2:
                addDisplayDetails(str, 11, iArr, 1);
                addDisplayDetails(str, 12, iArr, 2);
                addDisplayDetails(str, 13, iArr, 3);
                addDisplayDetails(str, 14, iArr, 4);
                addDisplayDetails(str, 15, iArr, 5);
                break;
            case 3:
                addDisplayDetails(str, 16, iArr, 1);
                addDisplayDetails(str, 17, iArr, 2);
                addDisplayDetails(str, 18, iArr, 3);
                addDisplayDetails(str, 19, iArr, 4);
                addDisplayDetails(str, 20, iArr, 5);
                break;
            case 4:
                addDisplayDetails(str, 21, iArr, 1);
                addDisplayDetails(str, 22, iArr, 2);
                addDisplayDetails(str, 23, iArr, 3);
                addDisplayDetails(str, 24, iArr, 4);
                addDisplayDetails(str, 25, iArr, 5);
                break;
            case 5:
                if (this.monthIndex != 1) {
                    if (this.monthIndex != 0 && this.monthIndex != 2 && this.monthIndex != 4 && this.monthIndex != 6 && this.monthIndex != 7 && this.monthIndex != 9 && this.monthIndex != 11) {
                        addDisplayDetails(str, 26, iArr, 1);
                        addDisplayDetails(str, 27, iArr, 2);
                        addDisplayDetails(str, 28, iArr, 3);
                        addDisplayDetails(str, 29, iArr, 4);
                        addDisplayDetails(str, 30, iArr, 5);
                        break;
                    }
                    iArr = new int[]{0, 1, 2, 3, 4, 5, 6};
                    addDisplayDetails(str, 26, iArr, 1);
                    addDisplayDetails(str, 27, iArr, 2);
                    addDisplayDetails(str, 28, iArr, 3);
                    addDisplayDetails(str, 29, iArr, 4);
                    addDisplayDetails(str, 30, iArr, 5);
                    addDisplayDetails(str, 31, iArr, 6);
                    break;
                }
                iArr = new int[]{0, 1, 2, 3, 4};
                addDisplayDetails(str, 26, iArr, 1);
                addDisplayDetails(str, 27, iArr, 2);
                addDisplayDetails(str, 28, iArr, 3);
                addDisplayDetails(str, 29, iArr, 4);
                break;
//            break;
        }
        ListView listView = (ListView) findViewById(R.id.that_days_lv);
        listView.setAdapter(new CommonAdapter(this, (TamilCalendarApplication) getApplication(), this.sections, iArr));
        listView.setOnItemClickListener(this);
        this.headerIndices = iArr;
    }

    private void displayMonths() {
        this.typeOfDaysShown = TypeOfDaysShown.MONTHS;
        this.headerIndices = new int[]{0};
        addMonthsToSections();
        ListView listView = (ListView) findViewById(R.id.that_days_lv);
        listView.setAdapter(new CommonAdapter(this, (TamilCalendarApplication) getApplication(), this.sections, this.headerIndices));
        listView.setOnItemClickListener(this);
        setTitle("தமிழ் காலெண்டர் 2017-18");
    }

    private void addMonthsToSections() {
        this.sections.clear();
        this.sections.add("இந்த நாள் அந்த வருடம் - மாதங்கள்");
        this.sections.add("ஜனவரி");
        this.sections.add("பிப்ரவரி");
        this.sections.add("மார்ச்");
        this.sections.add("ஏப்ரல்");
        this.sections.add("மே");
        this.sections.add("ஜூன்");
        this.sections.add("ஜூலை");
        this.sections.add("ஆகஸ்ட்");
        this.sections.add("செப்டம்பர்");
        this.sections.add("அக்டோபர்");
        this.sections.add("நவம்பர்");
        this.sections.add("டிசம்பர்");
    }

    private boolean isInHeaderIndices(int i) {
        for (int i2 : this.headerIndices) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }


    class ViewPagerPeyarchiPalanAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        Context context;
        ArrayList<List<String>> dateList;

        ArrayList<String> tamilDaysList;
        ArrayList<List<String>> listH;
        ArrayList<ArrayList<List<String>>> listH2, monthLists1;

        public ViewPagerPeyarchiPalanAdapter(FragmentManager manager, Context ctx, ArrayList<String> tamilDaysList, ArrayList<List<String>> dateList, ArrayList<List<String>> listH, ArrayList<ArrayList<List<String>>> listH2, ArrayList<ArrayList<List<String>>> monthLists1) {
            super(manager);
            context = ctx;
            this.tamilDaysList = tamilDaysList;
            this.dateList = dateList;
            this.listH = listH;
            this.listH2 = listH2;
            this.monthLists1 = monthLists1;
        }

        @Override
        public Fragment getItem(int position) {
            return HistoryFragment.newInstance(position, dateList, listH, listH2, monthLists1);
        }

        @Override
        public int getCount() {
            return tamilDaysList.size();
        }

        public void addFragment(Fragment fragment, ArrayList<String> title) {
            mFragmentList.add(fragment);
            tamilDaysList = title;
        }

        public View getTabView(int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.tablayout_view, null);
            TextView textView = (TextView) view.findViewById(R.id.textViewTab);
            textView.setText(tamilDaysList.get(position));
            return view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tamilDaysList.get(position);
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
                                    TakeScreenShot(HistoryOfTodayActivity.this,rootLayout,0,11,"0","வரலாற்றில் இன்று",progressBar);
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
