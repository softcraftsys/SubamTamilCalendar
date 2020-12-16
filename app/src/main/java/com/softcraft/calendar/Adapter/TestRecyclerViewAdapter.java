package com.softcraft.calendar.Adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;

import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softcraft.calendar.Activity.MonthActivity;
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

//import github.vatsal.easyweather.Helper.TempUnitConverter;

public class TestRecyclerViewAdapter extends RecyclerView.Adapter<TestRecyclerViewAdapter.MyViewHolder> {
    public interface IMethodCaller {
        void setClickDateFromMonth(String setCurrentdate);
    }

    List<String> contents;
    private Context mContext;
    private Calendar month;
    private GregorianCalendar pmonth; // calendar instance for previous month
    private GregorianCalendar pmonthmaxset;
    private GregorianCalendar selectedDate;
    private int firstDay;
    private int maxWeeknumber;
    private int maxP;
    private int calMaxP;
    private int mnthlength;
    private String itemvalue, curentDateString, setClickDateFromMonth;
    private DateFormat df;
    private ArrayList<String> items;
    private List<String> dayString, dayStringTamil, dayMugurtham, dayHolidays, dayKirthigai, dayAmavasai, dayPournami, dayFestival, getClickDateFromMonth;
    private View viewFestivalLine;
    private TextView dayView, tamilDayView;
    private ImageView ImageMugurtham, ImageKirthigai, ImagePournami;
    DataBaseHelper db;
    String curentDateStringForSelected;
    private RelativeLayout relativeBottomLayout;
    private RelativeLayout monthLayout;
    ViewPager viewPager;

    public TestRecyclerViewAdapter(Context context, List<String> contents, GregorianCalendar monthCalendar, int monthposition, ViewPager view_pager) {

        this.contents = contents;
        this.mContext = context;
        this.viewPager = view_pager;

        try {
            db = new DataBaseHelper(mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dayString = new ArrayList<>();
        dayStringTamil = new ArrayList<>();
        dayMugurtham = new ArrayList<>();
        dayKirthigai = new ArrayList<>();
        dayAmavasai = new ArrayList<>();
        dayPournami = new ArrayList<>();
        dayHolidays = new ArrayList<>();
        dayFestival = new ArrayList<>();
        getClickDateFromMonth = new ArrayList<>();
        Locale.setDefault(Locale.US);
        month = monthCalendar;
        selectedDate = (GregorianCalendar) monthCalendar.clone();
        mContext = context;
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        this.items = new ArrayList<String>();
        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        curentDateString = df.format(selectedDate.getTime());
        DateFormat currentdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentdate = new Date();
        curentDateStringForSelected = currentdf.format(currentdate);
        refreshDays();
        Log.d("gridValue", String.valueOf(mnthlength));

    }

    public void refreshDays() {
        // clear items
        items.clear();
        dayString.clear();
        Locale.setDefault(Locale.US);
        pmonth = (GregorianCalendar) month.clone();
        // month start day. ie; sun, mon, etc
        firstDay = month.get(GregorianCalendar.DAY_OF_WEEK);
        // finding number of weeks in current month.
        maxWeeknumber = month.getActualMaximum(GregorianCalendar.WEEK_OF_MONTH);
        // allocating maximum row number for the gridview.
        mnthlength = maxWeeknumber * 7;
        maxP = getMaxP(); // previous month maximum day 31,30....
        calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
        /**
         * Calendar instance for getting a complete gridview including the three
         * month's (previous,current,next) dates.
         */
        pmonthmaxset = (GregorianCalendar) pmonth.clone();
        /**
         * setting the start date as previous month's required date.
         */
        pmonthmaxset.set(GregorianCalendar.DAY_OF_MONTH, calMaxP + 1);

        Calendar calendar = Calendar.getInstance();
        int months = calendar.get(Calendar.MONTH);
        if (months == 5 && mnthlength == 28) {
            mnthlength = 42;
        }

        for (int n = 0; n < mnthlength; n++) {
            itemvalue = df.format(pmonthmaxset.getTime());
            String[] separatedTime = itemvalue.split("-");
            // taking last part of date. ie; 2 from 2012-12-02
            String gridvalue = separatedTime[2].replaceFirst("^0*", "");
            String Tmonth = separatedTime[1].replaceFirst("^0*", "");
            String Tyear = separatedTime[0].replaceFirst("^0*", "");
            //get value from database
            dayStringTamil.add(db.getTamilDate(gridvalue, Tmonth, Tyear));
            dayMugurtham.add(db.getMugurthamDate(gridvalue, Tmonth, Tyear));
            dayHolidays.add(db.getHolidays(gridvalue, Tmonth, Tyear));
            dayKirthigai.add(db.imgKirthigai(gridvalue, Tmonth, Tyear));
            dayAmavasai.add(db.imgAmavasai(gridvalue, Tmonth, Tyear));
            dayPournami.add(db.imgPournami(gridvalue, Tmonth, Tyear));
            dayFestival.add(db.getFestivalDate(gridvalue, Tmonth, Tyear));

            int idate = Integer.parseInt(gridvalue);
            int iMonth = Integer.parseInt(Tmonth);
            if (idate < 10) {
                gridvalue = "0" + gridvalue;
            }
            if (iMonth < 10) {
                Tmonth = "0" + Tmonth;
            }

            getClickDateFromMonth.add(gridvalue.concat(".").concat(Tmonth).concat(".").concat(Tyear));

            pmonthmaxset.add(GregorianCalendar.DATE, 1);
            dayString.add(itemvalue);
        }
    }

    private int getMaxP() {
        int maxP;
        if (month.get(GregorianCalendar.MONTH) == month.getActualMinimum(GregorianCalendar.MONTH)) {
            pmonth.set((month.get(GregorianCalendar.YEAR) - 1),
                    month.getActualMaximum(GregorianCalendar.MONTH), 1);
        } else {
            pmonth.set(GregorianCalendar.MONTH,
                    month.get(GregorianCalendar.MONTH) - 1);
        }
        maxP = pmonth.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);

        return maxP;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView gridDate, year, genre;

        public MyViewHolder(View view) {
            super(view);
            int getVAlue = view.getHeight();
//            gridDate = (TextView) view.findViewById(R.id.grid_id);
            ImagePournami = (ImageView) view.findViewById(R.id.moon1);
            ImageMugurtham = (ImageView) view.findViewById(R.id.mugurtham_image_show);
            ImageMugurtham.setColorFilter(Color.BLACK);
            ImageKirthigai = (ImageView) view.findViewById(R.id.kirthigai_image_show);
            ImageKirthigai.setColorFilter(Color.BLACK);
            viewFestivalLine = (View) view.findViewById(R.id.festival_show_line);
            dayView = (TextView) view.findViewById(R.id.date);
            tamilDayView = (TextView) view.findViewById(R.id.tamil_date_id);
            relativeBottomLayout = (RelativeLayout) view.findViewById(R.id.bottomLayout);
            monthLayout = (RelativeLayout) view.findViewById(R.id.month_layout);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dayString.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.monthlygrid_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        try {
            String[] separatedTime = dayString.get(position).split("-");
            // taking last part of date. ie; 2 from 2012-12-02
            String gridvalue = separatedTime[2].replaceFirst("^0*", "");
            String Tmonth = separatedTime[1].replaceFirst("^0*", "");
            String Tyear = separatedTime[0].replaceFirst("^0*", "");
            tamilDayView.setText(gridvalue);
            dayView.setText(gridvalue);
            setClickDateFromMonth = gridvalue.concat(Tmonth).concat(Tyear);
            // checking whether the day is in current month or not.
            if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
                // setting offdays to white color.
                dayView.setTextColor(Color.parseColor("#EBF3EF"));
                dayView.setClickable(false);
                dayView.setFocusable(false);
                tamilDayView.setVisibility(View.GONE);
                ImagePournami.setVisibility(View.GONE);
                ImageMugurtham.setVisibility(View.GONE);
                ImageKirthigai.setVisibility(View.GONE);
                viewFestivalLine.setVisibility(View.GONE);
            } else if ((Integer.parseInt(gridvalue) < 7) && (position > 28)) {
                dayView.setTextColor(Color.parseColor("#EBF3EF"));
                dayView.setClickable(false);
                dayView.setFocusable(false);
                ImagePournami.setVisibility(View.GONE);
                tamilDayView.setVisibility(View.GONE);
                ImageMugurtham.setVisibility(View.GONE);
                ImageKirthigai.setVisibility(View.GONE);
                viewFestivalLine.setVisibility(View.GONE);
            } else {
                // setting current month's days in blue color.
                tamilDayView.setVisibility(View.VISIBLE);
                tamilDayView.setTextColor(Color.parseColor("#0000FF"));
                ImagePournami.setVisibility(View.VISIBLE);
                ImageMugurtham.setVisibility(View.VISIBLE);
                ImageKirthigai.setVisibility(View.VISIBLE);
                viewFestivalLine.setVisibility(View.VISIBLE);

                dayView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(mContext, dayView);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    String setClickDate = getClickDateFromMonth.get(position);
                                    MiddlewareInterface.clickDateFromMonth = setClickDate;
                                    MiddlewareInterface.clickDate = setClickDate;
                                    MonthActivity Magact = (MonthActivity) mContext;
                                    Magact.callback();
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
                if (dayString.get(position).equals(curentDateStringForSelected)) {
                    monthLayout.setBackgroundColor(Color.parseColor("#FFA500"));
                    monthLayout.setVisibility(View.VISIBLE);

                }
                String strHoliday = dayHolidays.get(position);
                if (strHoliday != null) {
                    if (strHoliday.equalsIgnoreCase("1")) {
                        dayView.setTextColor(Color.parseColor("#FF0000"));
                        dayView.setVisibility(View.VISIBLE);
                    }
                }
            }
            try {
                String strTamil = dayStringTamil.get(position);
                String[] strTamildate = strTamil.split(" ");
                dayView.setText(gridvalue);
                tamilDayView.setText(strTamildate[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String strMugurtham = dayMugurtham.get(position);
                if (strMugurtham != null) {
                    String[] strMugurthamdate = strMugurtham.split("\\.");
                    String getMugurthamdate = strMugurthamdate[0];
                    if (position == Integer.parseInt(getMugurthamdate))
                        ImageMugurtham.setVisibility(View.VISIBLE);
                    ImageMugurtham.setImageResource(R.drawable.subamugurtham_img);
                    ImageMugurtham.setColorFilter(Color.parseColor("#FF0000"));
                }
                String strPournami = dayPournami.get(position);
                if (strPournami != null) {
                    String[] strPournamidate = strPournami.split("\\.");
                    String getPournamedate = strPournamidate[0];
                    if (position == Integer.parseInt(getPournamedate))
                        ImagePournami.setVisibility(View.VISIBLE);
                    ImagePournami.setImageResource(R.drawable.moon_img);
                    ImagePournami.setColorFilter(Color.GRAY);
                }
                String strKirthigai = dayKirthigai.get(position);
                if (strKirthigai != null) {
                    String[] strKirthigaidate = strKirthigai.split("\\.");
                    String getKirthigaidate = strKirthigaidate[0];
                    if (position == Integer.parseInt(getKirthigaidate))
                        ImageKirthigai.setVisibility(View.VISIBLE);
                    ImageKirthigai.setImageResource(R.drawable.star_img);
                    ImageKirthigai.setColorFilter(Color.MAGENTA);
                }
                String strAmavasai = dayAmavasai.get(position);
                if (strAmavasai != null) {
                    String[] strAmavasaidate = strAmavasai.split("\\.");
                    String getAmavasaidate = strAmavasaidate[0];
                    if (position == Integer.parseInt(getAmavasaidate))
                        ImagePournami.setVisibility(View.VISIBLE);
                    ImagePournami.setImageResource(R.drawable.full_moon_image1);
                    ImagePournami.setColorFilter(Color.BLACK);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                String strFestival = dayFestival.get(position);
                if (!strFestival.equalsIgnoreCase("")) {
                    viewFestivalLine.setVisibility(View.VISIBLE);
                    viewFestivalLine.setBackgroundColor(Color.YELLOW);
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//////
/*
package com.softcraft.calendar;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.res.TypedArray;
        import android.graphics.Typeface;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentStatePagerAdapter;
        import android.support.v4.view.ViewPager;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.widget.ViewFlipper;

        import com.google.android.gms.ads.AdListener;
        import com.google.android.gms.ads.AdRequest;
        import com.google.android.gms.ads.AdSize;
        import com.google.android.gms.ads.AdView;
        import com.inmobi.commons.InMobi;
        import com.inmobi.monetization.IMBanner;
        import com.inmobi.monetization.IMBannerListener;
        import com.inmobi.monetization.IMErrorCode;

        import java.io.IOException;
        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.GregorianCalendar;
        import java.util.List;
        import java.util.Locale;
        import java.util.Map;

        import github.vatsal.easyweather.Helper.TempUnitConverter;

public class MonthActivity extends AppCompatActivity implements RecyclerViewFragment.CallViewPager

{
    ArrayList<ArrayList<String>> setFestivals;
    public DataBaseHelper db;
    public static String POS_KEY_MONTH = "current_pos";
    public static String KEY_MONTH = "current_pos";
    public ListView mFesivalList;
    public ListView mDrawerList;
    protected FrameLayout mFrameLayout;
    private DateFormat df;
    protected String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private int currentposition;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter navDrawerListAdapter;
    private String getCurrentmonth;
    private String getCurrentmonthCompare;
    private String getTamilmonth;
    public GregorianCalendar month;
    public Context context;
    public ViewPager mViewpager;
    public ImageView preButton;
    public ImageView nextButton;
    DrawerLayout drawer;
    LinearLayout linearad;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    AdView adview;
    TextView tWeatherTemp,tWeatherLocation,tWeatherCondition;
    private AdBannerListener adBannerListener;
    static boolean active = false;
    int checkFlag;
    String getWeatherTemp,getWeatherLocation,getWeatherCondition,clickDay="Day",clickMonth="Month",clickYear="Year";
    ViewFlipper viewFlipper;
    Handler handlerWeather;
    boolean isFirst=false;
    FloatingActionButton refreshmonth;
    TextView refreshText;
    String getcurrentdateMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewpager = (ViewPager) findViewById(R.id.pager);
        linearad=(LinearLayout)findViewById(R.id.adview);
        mDrawerList = (ListView) findViewById(R.id.month_list_slidermenu);
        mFrameLayout = (FrameLayout) findViewById(R.id.month_frame_container);
        tWeatherLocation=(TextView)findViewById(R.id.weatherLocation);
        tWeatherTemp=(TextView)findViewById(R.id.weatherTemp);
        tWeatherCondition=(TextView)findViewById(R.id.weatherCondition);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        refreshmonth = (FloatingActionButton) findViewById(R.id.restart_currentmonth);
        refreshText = (TextView) findViewById(R.id.restart_textMonth);
        try {
            if(!MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
                if (!MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getString(MiddlewareInterface.strLocation).equalsIgnoreCase("")&&!MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getString(MiddlewareInterface.strCondition).equalsIgnoreCase(""))
                {
                    handlerWeather = new Handler();
                    handlerWeather.postDelayed(new Runnable() {
                        public void run() {
                            try
                            {
                                Animation hide = AnimationUtils.loadAnimation(MonthActivity.this, R.anim.bottom_to_top_animation);
                                tWeatherTemp.setText(TempUnitConverter.convertToCelsius(MiddlewareInterface.strTemp).intValue() + "°C");
                                tWeatherCondition.setText(MiddlewareInterface.strCondition);
                                tWeatherLocation.setText(MiddlewareInterface.strLocation);
                                if (viewFlipper != null)
                                {
                                    viewFlipper.startAnimation(hide);
                                    AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                                }
                                handlerWeather.postDelayed(this, 5000);
                                isFirst=true;
                                //add navigation menu show
                                getNavigationItems();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, 5000);
                }
            }
           *//* else
            {
                tWeatherTemp.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getString(MiddlewareInterface.weatherTemp));
                tWeatherLocation.setText(MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getString(MiddlewareInterface.weatherTV));
            }*//*
        } catch (IOException e) {
            e.printStackTrace();
        }
       *//* //get bundle values
        Bundle extras = getIntent().getExtras();
        if(extras!=null)
        {
            Intent mIntent = getIntent();
            checkFlag = mIntent.getIntExtra("weatherFlag", 0);
            getWeatherTemp = mIntent.getStringExtra("putWeatherTemp");
            getWeatherLocation = mIntent.getStringExtra("putWeatherLocation");
            getWeatherCondition = mIntent.getStringExtra("putWeatherCondition");
            tWeatherLocation.setText(getWeatherLocation);
            tWeatherTemp.setText(getWeatherTemp);
            tWeatherCondition.setText(getWeatherCondition);

            if(checkFlag!=1)
            {
                if (checkFlag == 2)
                {
                    getNavigationItems();
                }
            }
        }
        else
        {*//*
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        navDrawerItems = new ArrayList<>();
        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(9, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(10, -1)));
        // Recycle the typed array
        navMenuIcons.recycle();
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        // setting the nav drawer list adapter
        navDrawerListAdapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(navDrawerListAdapter);
//        }
       *//* //set flipper
        try {
            handlerWeather = new Handler();
            handlerWeather.postDelayed(new Runnable() {
                public void run() {
                    try {
                        Animation hide = AnimationUtils.loadAnimation(MonthActivity.this, R.anim.bottom_to_top_animation);
                        if (viewFlipper != null) {
                            viewFlipper.startAnimation(hide);
                            AnimationFactory.flipTransition(viewFlipper, AnimationFactory.FlipDirection.LEFT_RIGHT);
                        }
                        handlerWeather.postDelayed(this, 5000);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 5000);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*//*
        // setAdvertise();
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

        try
        {
            db = new DataBaseHelper(context);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        month = (GregorianCalendar) GregorianCalendar.getInstance();
        GregorianCalendar selectedDate = (GregorianCalendar) month.clone();
        month.set(GregorianCalendar.DAY_OF_MONTH, 1);
        TextView monthTitle=(TextView)findViewById(R.id.month_toolbar_title);
        TextView festivalName=(TextView)findViewById(R.id.festival_day);
        if(MiddlewareInterface.bRendering)
        {
            monthTitle.setText((getResources().getString(R.string.day)));
            festivalName.setText((getResources().getString(R.string.festival_day)));
            monthTitle.setTypeface(Typeface.DEFAULT);
            festivalName.setTypeface(Typeface.DEFAULT);
        }
        else{
            monthTitle.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.day)));
            festivalName.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.festival_day)));
            monthTitle.setTypeface(MiddlewareInterface.tf_mylai);
            festivalName.setTypeface(MiddlewareInterface.tf_mylai);
        }
        LinearLayout goToDayview = (LinearLayout) findViewById(R.id.month_icon_click) ;
        goToDayview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent= new Intent(MonthActivity.this, DayActivity.class);
                startActivity(intent);
                if( active)
                {
                    Intent back= new Intent();
                    setResult(6,back);
                }
                finish();
            }
        });

        df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String curentDateString = df.format(selectedDate.getTime());
        String[] separatedTime = curentDateString.split("-");
        // taking last part of date. ie; 2 from 2012-12-02
        String gridvalue = separatedTime[2].replaceFirst("^0*", "");
        String Tmonth = separatedTime[1].replaceFirst("^0*", "");
        String Tyear = separatedTime[0].replaceFirst("^0*", "");
        int viewPagerPos= Integer.parseInt(Tmonth);
        viewPagerPos=viewPagerPos-1;




        preButton = (ImageView) findViewById(R.id.prev_button);
        nextButton = (ImageView) findViewById(R.id.next_button);
        preButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mViewpager.setCurrentItem(currentposition -1);

            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mViewpager.setCurrentItem(currentposition +1);
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.month_drawer_layout);
        ImageView menuClick=(ImageView) findViewById(R.id.menu_click);
        menuClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        // Viewpager set fragment
        mViewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position)
            {
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
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentposition=position;
                int sendFestivalValue= position + 1;
                setFestivals = db.dGetFestivals(sendFestivalValue);
                mFesivalList = (ListView) findViewById(R.id.festival_list_view);
                mFesivalList.setAdapter(new FestivalAdapter(MonthActivity.this, setFestivals));
                setListViewHeightBasedOnChildren(mFesivalList);

                if (position == 0){
                    getCurrentmonth = "ஜனவரி -2016";
                    getCurrentmonthCompare = "ஜனவரி -1.2016";
                }

                else if (position == 1){
                    getCurrentmonth = "பிப்ரவரி -2016";
                    getCurrentmonthCompare = "ஜனவரி -2.2016";
                }
                else if (position == 2){
                    getCurrentmonth = "மார்ச் -2016";
                    getCurrentmonthCompare = "ஜனவரி -3.2016";
                }
                else if (position == 3){
                    getCurrentmonth = "ஏப்ரல் -2016";
                    getCurrentmonthCompare = "ஜனவரி -4.2016";
                }

                else if (position == 4){
                    getCurrentmonth = "மே -2016";
                    getCurrentmonthCompare = "ஜனவரி -5.2016";
                }

                else if (position == 5){
                    getCurrentmonth = "ஜூன் -2016";
                    getCurrentmonthCompare = "ஜனவரி -6.2016";
                }

                else if (position == 6){
                    getCurrentmonth = "ஜூலை -2016";
                    getCurrentmonthCompare = "ஜனவரி -7.2016";
                }

                else if (position == 7){
                    getCurrentmonth = "ஆகஸ்ட் -2016";
                    getCurrentmonthCompare = "ஜனவரி -8.2016";
                }

                else if (position == 8){
                    getCurrentmonth = "செப்டம்பர் -2016";
                    getCurrentmonthCompare = "ஜனவரி -9.2016";
                }

                else if (position == 9){
                    getCurrentmonth = "அக்டோபர் -2016";
                    getCurrentmonthCompare = "ஜனவரி -10.2016";
                }

                else if (position == 10){
                    getCurrentmonth = "நவம்பர் -2016";
                    getCurrentmonthCompare = "ஜனவரி -11.2016";
                }

                else if (position == 11){
                    getCurrentmonth = "டிசம்பர் -2016";
                    getCurrentmonthCompare = "ஜனவரி -12.2016";
                }


                else if (position == 12)
                {
                    getCurrentmonth = "ஜனவரி -2017";
                    getCurrentmonthCompare = "ஜனவரி -1.2017";
                }
                else if (position == 13)
                {
                    getCurrentmonth = "பிப்ரவரி -2017";
                    getCurrentmonthCompare = "ஜனவரி -2.2017";
                }
//                    getCurrentmonth = "பிப்ரவரி -2017";
                else if (position == 14){
                    getCurrentmonth = "மார்ச் -2017";
                    getCurrentmonthCompare = "ஜனவரி -3.2017";
                }

                else if (position == 15)
                {
                    getCurrentmonthCompare = "ஜனவரி -4.2017";
                    getCurrentmonth = "ஏப்ரல் -2017";
                }
                else if (position == 16)
                {
                    getCurrentmonth = "மே -2017";
                    getCurrentmonthCompare = "ஜனவரி -5.2017";
                }
                else if (position == 17) {
                    getCurrentmonth = "ஜூன் -2017";
                    getCurrentmonthCompare = "ஜனவரி -6.2017";
                }
                else if (position == 18) {
                    getCurrentmonth = "ஜூலை -2017";
                    getCurrentmonthCompare = "ஜனவரி -7.2017";
                }
                else if (position == 19) {
                    getCurrentmonth = "ஆகஸ்ட் -2017";
                    getCurrentmonthCompare = "ஜனவரி -8.2017";
                }
                else if (position == 20) {
                    getCurrentmonth = "செப்டம்பர் -2017";
                    getCurrentmonthCompare = "ஜனவரி -9.2017";
                }
                else if (position == 21) {
                    getCurrentmonth = "அக்டோபர் -2017";
                    getCurrentmonthCompare = "ஜனவரி -10.2017";
                }
                else if (position == 22) {
                    getCurrentmonth = "நவம்பர் -2017";
                    getCurrentmonthCompare = "ஜனவரி -11.2017";
                }
                else if (position == 23) {
                    getCurrentmonth = "டிசம்பர் -2017";
                    getCurrentmonthCompare = "ஜனவரி -12.2017";
                }

                TextView tCurrentMonth = (TextView) findViewById(R.id.current_month);
                if (position == 0||position == 12)
                    getTamilmonth = "மார்கழி - தை ";
                else if (position == 1||position == 13)
                    getTamilmonth = "தை - மாசி  ";
                else if (position == 2||position == 14)
                    getTamilmonth = "மாசி - பங்குனி ";
                else if (position == 3||position == 15)
                    getTamilmonth = "பங்குனி - சித்திரை ";
                else if (position == 4||position == 16)
                    getTamilmonth = "சித்திரை - வைகாசி ";
                else if (position == 5||position == 17)
                    getTamilmonth = "வைகாசி - ஆனி ";
                else if (position == 6||position == 18)
                    getTamilmonth = "ஆனி - ஆடி ";
                else if (position == 7||position == 19)
                    getTamilmonth = "ஆடி - ஆவணி ";
                else if (position == 8||position == 20)
                    getTamilmonth = "ஆவணி - புரட்டாசி ";
                else if (position == 9||position == 21)
                    getTamilmonth = "புரட்டாசி - ஐப்பசி ";
                else if (position == 10||position == 22)
                    getTamilmonth = "ஐப்பசி - கார்த்திகை ";
                else if (position == 11||position == 23)
                    getTamilmonth = "கார்த்திகை - மார்கழி";

                TextView tTamilMonth = (TextView) findViewById(R.id.tamil_month);
                if(MiddlewareInterface.bRendering)
                {
                    tCurrentMonth.setText(getCurrentmonth);
                    tTamilMonth.setText(getTamilmonth);
                    tCurrentMonth.setTypeface(Typeface.DEFAULT);
                    tTamilMonth.setTypeface(Typeface.DEFAULT);
                }
                else
                {
                    tCurrentMonth.setText(UnicodeUtil.unicode2tsc(getCurrentmonth));
                    tTamilMonth.setText(UnicodeUtil.unicode2tsc(getTamilmonth));
                    tCurrentMonth.setTypeface(MiddlewareInterface.tf_mylai);
                    tTamilMonth.setTypeface(MiddlewareInterface.tf_mylai);
                }
                String[] str = getCurrentmonthCompare.split("-");
                getcurrentdateMonth = str[1];
                if (!getcurrentdateMonth.equalsIgnoreCase(MiddlewareInterface.getCurrentDateMonth()))
                {
                    refreshmonth.setVisibility(View.VISIBLE);
                    refreshText.setVisibility(View.VISIBLE);
                }
                else
                {

                    MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).putInt(KEY_MONTH,position);
                    refreshmonth.setVisibility(View.GONE);
                    refreshText.setVisibility(View.GONE);
                }
//                if (MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getInt(KEY_MONTH) == position)
//                {
//                    refreshmonth.setVisibility(View.GONE);
//                    refreshText.setVisibility(View.GONE);
//                }
//                else {
//                    refreshmonth.setVisibility(View.VISIBLE);
//                    refreshText.setVisibility(View.VISIBLE);
//                }

//                if (!month.equalsIgnoreCase(MiddlewareInterface.getCurrentDate())) {
//                    refreshmonth.setVisibility(View.VISIBLE);
//                    refreshText.setVisibility(View.VISIBLE);
//                }
//                else {
//                    refreshmonth.setVisibility(View.GONE);
//                    refreshText.setVisibility(View.GONE);
//                }

                tCurrentMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(MonthActivity.this);
//                        builderSingle.setIcon(R.drawable.ic_launcher);
//                        builderSingle.setTitle("Select One Name:-");


                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MonthActivity.this, android.R.layout.simple_list_item_1);
                        arrayAdapter.add("ஜனவரி ");
                        arrayAdapter.add("பிப்ரவரி ");
                        arrayAdapter.add("மார்ச் ");
                        arrayAdapter.add("ஏப்ரல் ");
                        arrayAdapter.add("மே ");
                        arrayAdapter.add("ஜூன் ");
                        arrayAdapter.add("ஜூலை ");
                        arrayAdapter.add("ஆகஸ்ட் ");
                        arrayAdapter.add("செப்டம்பர் ");
                        arrayAdapter.add("அக்டோபர் ");
                        arrayAdapter.add("நவம்பர் ");
                        arrayAdapter.add("டிசம்பர் ");
                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(currentposition>11)
                                {
                                    currentposition = which+12;
                                }else{
                                    currentposition = which;
                                }


                                mViewpager.setCurrentItem(currentposition);
                                dialog.dismiss();

//                                String strName = arrayAdapter.getItem(which);
//                                AlertDialog.Builder builderInner = new AlertDialog.Builder(MonthActivity.this);
//                                builderInner.setMessage(strName);
//                                builderInner.setTitle("Your Selected Item is");
//                                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog,int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//                                builderInner.show();
                            }
                        });
                        builderSingle.show();
                    }
                });
                RefreshAds();
            }

            @Override
            public void onPageSelected(int position)
            {

            }
            @Override
            public void onPageScrollStateChanged(int state)
            {
            }

        });

        refreshmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int getPos = MiddlewareInterface.SharedPreferenceUtility.getInstance(MonthActivity.this).getInt(KEY_MONTH);
                mViewpager.setCurrentItem(getPos);
            }
        });

        mViewpager.setCurrentItem(currentposition);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        active = true;
    }
    @Override
    public void onStop()
    {
        super.onStop();
        active = false;
    }
    public void setListViewHeightBasedOnChildren(ListView listView)
    {
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
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void yourDesiredMethod(final int i)
    {
        Log.d("relativeLayoutHight",String.valueOf(i));
    }

    public void callback() {
        Intent back= new Intent();
        back.putExtra("edittextvalue", MiddlewareInterface.clickDateFromMonth);
        setResult(5,back);
        finish();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            if (position == 0)
            {
            } else {
                selectItem(position);
                drawer.closeDrawer(mDrawerList);
            }
        }
    }
    *//**
 * Diplaying fragment view for selected nav drawer list item
 *//*
    private void selectItem(int position)
    {
        try {
            if (navDrawerListAdapter != null) {
                Intent intent = new Intent(this, DayActivity.class);
                if (navDrawerListAdapter.getCount() == 12) {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            intent = new Intent(this, FestivalDays.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(this, ManaiyadiSasthiram.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(this, SubamugurthamDays.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickDay);
                            startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickMonth);
                            startActivity(intent);
                            break;
                        case 6:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickYear);
                            startActivity(intent);
                            break;
                        case 7:
                            intent = new Intent(this, NewsActivity.class);
                            startActivity(intent);
                            break;
                        case 8:
                            intent = new Intent(this, NotificationSettings.class);
                            startActivity(intent);
                            break;
                        case 9:
                            intent = new Intent(this, Help.class);
                            startActivity(intent);
                            break;
                        case 10:
                            intent = new Intent(this, WeatherCheck.class);
                            intent.putExtra("cityname", getWeatherLocation);
                            startActivity(intent);
                            break;
                        case 11:
                            intent = new Intent(this, AboutUsActivity.class);
                            startActivity(intent);
                            break;

                        default:
                            break;
                    }
                }
                else
                {
                    switch (position) {
                        case 0:

                            break;
                        case 1:
                            intent = new Intent(this, FestivalDays.class);
                            startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(this, ManaiyadiSasthiram.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(this, SubamugurthamDays.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickDay);
                            startActivity(intent);
                            break;
                        case 5:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickMonth);
                            startActivity(intent);
                            break;
                        case 6:
                            intent = new Intent(this, Horoscope.class);
                            intent.putExtra("isClick", clickYear);
                            startActivity(intent);
                            break;
                        case 7:
                            intent = new Intent(this, NewsActivity.class);
                            startActivity(intent);
                            break;
                        case 8:
                            intent = new Intent(this, NotificationSettings.class);
                            startActivity(intent);
                            break;
                        case 9:
                            intent = new Intent(this, Help.class);
                            startActivity(intent);
                            break;
                        case 10:
                            intent = new Intent(this, AboutUsActivity.class);
                            startActivity(intent);
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
    public ViewPager getViewpager(){
        return mViewpager;
    }

    private void setAdvertise()
    {
        try{
            try{
                arrayListad = MiddlewareInterface.listsaddetails;
                getListAds = arrayListad.get(0);
                String strGoogleAd = getListAds.get(1);
                if(MiddlewareInterface.bAdFree)
                    return;
                adview= new AdView(this);
                adview.setAdSize(AdSize.SMART_BANNER);
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
    private class AdBannerListener implements IMBannerListener
    {
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
    private void RefreshAds()
    {
        try{
            if(MiddlewareInterface.bAdFree)
                return;

            adview.loadAd(new AdRequest.Builder().build());


        }catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
    }


    private void getNavigationItems() {
        try {
            navDrawerItems = new ArrayList<>();
            navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons1);
            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items1);

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[7], navMenuIcons.getResourceId(7, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(8, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[9], navMenuIcons.getResourceId(9, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[10], navMenuIcons.getResourceId(10, -1)));

            navDrawerItems.add(new NavDrawerItem(navMenuTitles[11], navMenuIcons.getResourceId(11, -1)));
            // Recycle the typed array
            navMenuIcons.recycle();
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
            // setting the nav drawer list adapter
            navDrawerListAdapter = new NavDrawerListAdapter(getApplicationContext(),
                    navDrawerItems);
            mDrawerList.setAdapter(navDrawerListAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}*/

