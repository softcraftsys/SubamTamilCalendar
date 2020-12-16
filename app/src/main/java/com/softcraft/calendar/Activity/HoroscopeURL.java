package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMBannerListener;
import com.inmobi.monetization.IMErrorCode;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;

public class HoroscopeURL extends AppCompatActivity {
    private WebView mWebView;
    private ProgressBar mProgress;
    private Handler mHandler = new Handler();
    MiddlewareInterface AMI = MiddlewareInterface.GetInstance();
    LinearLayout linearad;
    AdView adview;
    IMBanner bannerAdView;
    //    private AdBannerListener adBannerListener;
    ArrayList<List<String>> arrayListad;
    List<String> getListAds;
    String setDate, setMonth, setYear;
    String strSelectionView;
    ImageView backimg, imageView;
    TextView ml_title1;
    int lIntflag;
    Context context;
    private AdBannerListener adBannerListener;
    RelativeLayout rootLayout;
    FloatingActionButton ShareImg;

    @SuppressLint("SetJavaScriptEnabled")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.view_rasipalan);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, DayActivity.class));
        try {
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            lIntflag = getIntent().getExtras().getInt("gridPosition");
            ml_title1 = (TextView) findViewById(R.id.view_rasipalan_title);
            backimg = (ImageView) findViewById(R.id.view_rasipalan_backArrowImg);
            imageView = (ImageView) findViewById(R.id.view_rasi_image);
            linearad = (LinearLayout) findViewById(R.id.rasipalan_click_adview);
            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
            ShareImg = (FloatingActionButton) findViewById(R.id.shareImg);
//            imageView.setColorFilter(Color.WHITE);
            backimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(HoroscopeURL.this, backimg);
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
            ml_title1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(HoroscopeURL.this, ml_title1);
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
                ml_title1.setTypeface(Typeface.DEFAULT);
            } else {
                ml_title1.setTypeface(MiddlewareInterface.tf_mylai);
            }
            // setAdvertise();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
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
                } catch (Exception e) {
                    setAdvertise();
                    e.printStackTrace();
                }
            }
            //get value from intent
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                strSelectionView = extras.getString("Check");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date d = new Date();
            String getCurrentdate = sdf.format(d);
            String[] splitAll = getCurrentdate.split("\\.");
            setDate = splitAll[0];
            setMonth = splitAll[1];
            setYear = splitAll[2];
//            setYear="2017";
            mProgress = (ProgressBar) findViewById(R.id.progress_bar);
            mWebView = (WebView) findViewById(R.id.webview);
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setSavePassword(false);
            webSettings.setSaveFormData(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(false);
            mProgress.setVisibility(View.VISIBLE);
            webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
            webSettings.setAllowFileAccess(true);
            webSettings.setAppCacheEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default
            mWebView.setWebViewClient(new HelloWebViewClient());
            if (!MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) { // loading offline
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
            if (strSelectionView.equalsIgnoreCase("Day")) {
                if (ShareImg != null) {
                    ShareImg.setVisibility(View.GONE);
                }
                if (lIntflag == 0) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.mesham));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.mesham)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=1");
                    imageView.setImageResource(R.drawable.mesham);
                    imageView.setColorFilter(Color.WHITE);
//                    mWebView.loadUrl("javascript:document.getElementById('http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=\"+setYear+\"&month=\"+setMonth+\"&date=\"+setDate+\"&rasi=1')style.fontFamily ='"+fontName+"');
                } else if (lIntflag == 1) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.rishabam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.rishabam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=2");
                    imageView.setImageResource(R.drawable.rishabam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 2) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.midhunam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.midhunam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=3");
                    imageView.setImageResource(R.drawable.methunam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 3) {

                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.kadagam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kadagam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=4");
                    imageView.setImageResource(R.drawable.kadagam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 4) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.simmam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.simmam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=5");
                    imageView.setImageResource(R.drawable.simmam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 5) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.kanni));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kanni)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=6");
                    imageView.setImageResource(R.drawable.kanni);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 6) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.thulaam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.thulaam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=7");
                    imageView.setImageResource(R.drawable.thulaam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 7) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.viruchigam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.viruchigam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=8");
                    imageView.setImageResource(R.drawable.viruchigam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 8) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.dhanusu));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.dhanusu)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=9");
                    imageView.setImageResource(R.drawable.dhanusu);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 9) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.magaram));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.magaram)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=10");
                    imageView.setImageResource(R.drawable.magaram);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 10) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.kumbam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kumbam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=11");
                    imageView.setImageResource(R.drawable.kumbam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 11) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.meenam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.meenam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=3&year=" + setYear + "&month=" + setMonth + "&date=" + setDate + "&rasi=12");
                    imageView.setImageResource(R.drawable.meenam);
                    imageView.setColorFilter(Color.WHITE);
                }
            } else if (strSelectionView.equalsIgnoreCase("Month")) {
                if (lIntflag == 0) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.mesham));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.mesham)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=1");
                    imageView.setImageResource(R.drawable.mesham);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 1) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.rishabam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.rishabam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=2");
                    imageView.setImageResource(R.drawable.rishabam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 2) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.midhunam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.midhunam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=3");
                    imageView.setImageResource(R.drawable.methunam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 3) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.kadagam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kadagam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=4");
                    imageView.setImageResource(R.drawable.kadagam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 4) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.simmam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.simmam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=5");
                    imageView.setImageResource(R.drawable.simmam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 5) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.kanni));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kanni)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=6");
                    imageView.setImageResource(R.drawable.kanni);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 6) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.thulaam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.thulaam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=7");
                    imageView.setImageResource(R.drawable.thulaam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 7) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.viruchigam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.viruchigam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=8");
                    imageView.setImageResource(R.drawable.viruchigam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 8) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.dhanusu));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.dhanusu)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=9");
                    imageView.setImageResource(R.drawable.dhanusu);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 9) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.magaram));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.magaram)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=10");
                    imageView.setImageResource(R.drawable.magaram);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 10) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.kumbam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kumbam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=11");
                    imageView.setImageResource(R.drawable.kumbam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 11) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.meenam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.meenam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=2&year=" + setYear + "&month=" + setMonth + "&rasi=12");
                    imageView.setImageResource(R.drawable.meenam);
                    imageView.setColorFilter(Color.WHITE);
                }

            } else if (strSelectionView.equalsIgnoreCase("Year")) {
                if (lIntflag == 0) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.mesham));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.mesham)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=1");
//                    mWebView.loadUrl("http://192.168.1.12/scsadcontrol/rasipalan.php?action=1&year="+setYear+"&rasi=1");
                    imageView.setImageResource(R.drawable.mesham);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 1) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.rishabam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.rishabam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=2");
                    imageView.setImageResource(R.drawable.rishabam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 2) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.midhunam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.midhunam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=3");
                    imageView.setImageResource(R.drawable.methunam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 3) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.kadagam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kadagam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=4");
                    imageView.setImageResource(R.drawable.kadagam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 4) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.simmam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.simmam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=5");
                    imageView.setImageResource(R.drawable.simmam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 5) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.kanni));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kanni)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=6");
                    imageView.setImageResource(R.drawable.kanni);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 6) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.thulaam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.thulaam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=7");
                    imageView.setImageResource(R.drawable.thulaam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 7) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.viruchigam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.viruchigam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=8");
                    imageView.setImageResource(R.drawable.viruchigam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 8) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.dhanusu));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.dhanusu)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=9");
                    imageView.setImageResource(R.drawable.dhanusu);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 9) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.magaram));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.magaram)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=10");
                    imageView.setImageResource(R.drawable.magaram);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 10) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.kumbam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kumbam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=11");
                    imageView.setImageResource(R.drawable.kumbam);
                    imageView.setColorFilter(Color.WHITE);
                } else if (lIntflag == 11) {
                    if (MiddlewareInterface.bRendering) {
                        ml_title1.setText(getResources().getString(R.string.meenam));
                    } else {
                        ml_title1.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.meenam)));
                    }
                    mWebView.loadUrl("http://adsenseusers.com/scsadcontrol/rasipalan.php?action=1&year=" + setYear + "&rasi=12");
                    imageView.setImageResource(R.drawable.meenam);
                    imageView.setColorFilter(Color.WHITE);
                }
            }
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setSupportZoom(true);


        } catch (Exception e) {
            Log.d("SSSS", e.toString());
        }
        mWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                // do your stuff here
                //	 EDCActivity.RefreshAds();
                mProgress.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.i("WEB_VIEW_TEST", "error code:" + errorCode);
                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebView.loadUrl("about:blank");
            }


        });

        ShareFunc();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Provides a hook for calling "alert" from javascript. Useful for
     * debugging your javascript.
     */
    final class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            result.confirm();
            return true;
        }
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            mProgress.setVisibility(View.VISIBLE);

        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            mProgress.setVisibility(View.GONE);

        }

        public void onPageFinished(WebView view, String url) {


            mProgress.setVisibility(View.GONE);

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        //Toast.makeText(getApplicationContext(), "old="+oldScale+"new="+newScale, Toast.LENGTH_SHORT).show();
        //Log.d("scale","old="+oldScale+"new="+newScale);
    }

    private void getURL(boolean isMonth) {
        try {
//            if(isMonth)//Month
//                mWebView.loadUrl(MiddlewareInterface.strForumUrl);
//                else//Month
//                    mWebView.loadUrl(MiddlewareInterface.strForumUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setAdvertise() {
        try {

            try {
                String strId = getBannerAd;
                if (MiddlewareInterface.bAdFree)
                    return;
                adview = new AdView(this);
                adview.setAdSize(AdSize.BANNER);
                adview.setAdUnitId(strId);
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

    //    private void setAdvertise()
//    {
//        try{
//
//            try{
//                arrayListad = MiddlewareInterface.listsaddetails;
//                getListAds = arrayListad.get(0);
//                String strGoogleAd = getListAds.get(1);
//                if(MiddlewareInterface.bAdFree)
//                    return;
//                adview= new AdView(this);
//                adview.setAdSize(AdSize.SMART_BANNER);
//                adview.setAdUnitId(strGoogleAd);
//                adview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                linearad.addView(adview);
//                adview.loadAd(new AdRequest.Builder().build());
//                //linear_ad
//            }catch (Exception e) {
//                // TODO: handle exception
//            }
//            adview.setAdListener(new AdListener() {
//                @Override
//                public void onAdFailedToLoad(int errorCode) {
//                    // TODO Auto-generated method stub
//                    Log.d("On Fail called","Ad");
//                    linearad.setVisibility(View.GONE);
//                    super.onAdFailedToLoad(errorCode);
//                }
//                @Override
//                public void onAdLoaded() {
//                    // TODO Auto-generated method stub
//                    Log.d("On Load called","Ad");
//                    linearad.setVisibility(View.VISIBLE);
//                    super.onAdLoaded();
//                    //RefreshAds();
//                }
//            });
//
//        }catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }

    private void ShareFunc() {
        try {

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
                                    String strYearOrMonth = "";
                                    String strYearOrMonthTxt = "";
                                    if (strSelectionView.equalsIgnoreCase("Year")) {
                                        strYearOrMonth = "Year";
                                        strYearOrMonth = strYearOrMonth + "-" + lIntflag;
                                        strYearOrMonthTxt = "வருட ராசிபலன்";
                                        TakeScreenShot(HoroscopeURL.this, rootLayout, 0, 17, strYearOrMonth, strYearOrMonthTxt, mProgress);
                                    } else if (strSelectionView.equalsIgnoreCase("Month")) {
                                        strYearOrMonth = "Month";
                                        strYearOrMonth = strYearOrMonth + "-" + lIntflag;
                                        strYearOrMonthTxt = "மாத ராசிபலன்";
                                        TakeScreenShot(HoroscopeURL.this, rootLayout, 0, 16, strYearOrMonth, strYearOrMonthTxt, mProgress);
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


}
