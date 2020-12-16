package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.inmobi.monetization.IMBanner;
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.Fragment.GowripanchagamFragment;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.util.ArrayList;
import java.util.List;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;


public class GowriPanchangam extends AppCompatActivity {
    Context context;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static DataBaseHelper db;
    int iColor = MiddlewareInterface.currentSelectedColor;
    LinearLayout linearad;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    IMBanner bannerAdView;
    AdView adview;
    private NativeExpressAdView viewNativeAd;
    RelativeLayout rootLayout;
    ProgressBar progress_bar;
    TabLayout tabLayout;
    ViewPager viewPager;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.gowripanchagam);

            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, DayActivity.class));
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            final ImageView imgBack = findViewById(R.id.click_back_image);
            linearad = findViewById(R.id.adview);
            progress_bar = findViewById(R.id.progress_bar);
            tabLayout = findViewById(R.id.tabs);
            viewPager = findViewById(R.id.viewPager);
            rootLayout = findViewById(R.id.rootLayout);

            flag = getIntent().getIntExtra("categoryId", -1);

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
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GowriPanchangam.this, imgBack);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
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
            final TextView tHeader = findViewById(R.id.show_title);
            try {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
                tHeader.startAnimation(animation);
            } catch (Exception e) {
                e.printStackTrace();
            }

            tHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(GowriPanchangam.this, tHeader);
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

            if (MiddlewareInterface.bRendering) {
                if (flag == 2)
                    tHeader.setText((getResources().getString(R.string.gowripanchagam)));
                else
                    tHeader.setText((getResources().getString(R.string.giragaooraigal)));
                tHeader.setTypeface(Typeface.DEFAULT);
            } else {
                if (flag == 2)
                    tHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.gowripanchagam)));
                else
                    tHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.giragaooraigal)));
                tHeader.setTypeface(MiddlewareInterface.tf_mylai);
            }
            try {
                db = new DataBaseHelper(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ShareFunc();
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {
        try {
            ArrayList<String> tamilDaysList = new ArrayList<>();
            tamilDaysList.add("ஞாயிறு");
            tamilDaysList.add("திங்கள்");
            tamilDaysList.add("செவ்வாய்");
            tamilDaysList.add("புதன்");
            tamilDaysList.add("வியாழன்");
            tamilDaysList.add("வெள்ளி");
            tamilDaysList.add("சனி");
            ArrayList<ArrayList<String>> gowriPanchagamList;
            if (flag == 2)
                gowriPanchagamList = db.getGowriPanchangamList();
            else
                gowriPanchagamList = db.getGrahaOraigal();

            ViewPagerPeyarchiPalanAdapter mAdapter = new ViewPagerPeyarchiPalanAdapter(getSupportFragmentManager(), getApplicationContext(), tamilDaysList, gowriPanchagamList);
            mAdapter.addFragment(new GowripanchagamFragment(), tamilDaysList);
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

    class ViewPagerPeyarchiPalanAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        Context context;
        ArrayList<ArrayList<String>> gowriPanchagamList;
        ArrayList<String> titleList;
        ArrayList<String> tamilDaysList;

        public ViewPagerPeyarchiPalanAdapter(FragmentManager manager, Context ctx, ArrayList<String> tamilDaysList, ArrayList<ArrayList<String>> gowriPanchagamList) {
            super(manager);
            context = ctx;
            this.tamilDaysList = tamilDaysList;
            this.gowriPanchagamList = gowriPanchagamList;
        }

        @Override
        public Fragment getItem(int position) {
            return GowripanchagamFragment.newInstance(position, gowriPanchagamList);
        }

        @Override
        public int getCount() {
            return tamilDaysList.size();
        }

        public void addFragment(Fragment fragment, ArrayList<String> title) {
            mFragmentList.add(fragment);
            titleList = title;
        }

        public View getTabView(int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.tablayout_view, null);
            TextView textView = (TextView) view.findViewById(R.id.textViewTab);
            textView.setText(titleList.get(position));
            return view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
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

                                    if (flag == 2) {
                                        TakeScreenShot(GowriPanchangam.this, rootLayout, 0, 6, "0", "கிரக ஓரைகள்", progress_bar);
                                    } else {
                                        TakeScreenShot(GowriPanchangam.this, rootLayout, 0, 7, "0", "கௌரி பஞ்சாங்கம்", progress_bar);
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

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}