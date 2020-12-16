package com.softcraft.calendar.MarriageMatch;


import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import androidx.core.view.PointerIconCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

//import com.whiture.apps.tamil.calendar.data.NatchathiramVO;
//import com.whiture.apps.tamil.calendar.util.ThirumanaPoruthamUtil;
//import com.whiture.apps.tamil.calendar.view.NatchathiramDropDownAdapter;

public class MarriageMatchActivity extends AppCompatActivity implements OnItemSelectedListener {
    //    private TamilCalendarApplication application;
    Button btnPoruthamSumbit;
    TextView dhinaporuththam;
    Spinner femaleNatchathiram;
    NatchathiramVO femaleNatchathiramVO;
    Spinner femaleRaasi;
    private final int lightGreen = Color.parseColor("#B5D887");
    private final int lightRed = Color.parseColor("#F1A0A7");
    Spinner maleNatchathiram;
    NatchathiramVO maleNatchathiramVO;
    Spinner maleRaasi;
    MarriageMatchActivity porutham;
    private Integer[] poruthamList;
    private boolean userIsInteracting;
    ProgressBar progressBar;
    LinearLayout linearad;
    AdView adview;
    private NativeExpressAdView viewNativeAd;

    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    protected void onCreate(Bundle bundle) {
//        this.application = (TamilCalendarApplication) getApplication();
//        setTheme(this.application.getCurrentThemeId());
        super.onCreate(bundle);
        setContentView((int) R.layout.marriage_match_layout);
        try {

            final ImageView imgBack = (ImageView) findViewById(R.id.backImg);
            final TextView titleTxt = (TextView) findViewById(R.id.show_title_aboutus);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);
            linearad = (LinearLayout) findViewById(R.id.adview);


            imgBack.setColorFilter(Color.WHITE);
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MarriageMatchActivity.this, imgBack);
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
            titleTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(MarriageMatchActivity.this, titleTxt);
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

            findViewById(R.id.btn_general_share).setVisibility(View.GONE);
            findViewById(R.id.btn_whatsapp_share).setVisibility(View.GONE);
            this.porutham = this;
//        initializeAds();
//        this.application.setCurrentTheme();
            this.femaleRaasi = (Spinner) findViewById(R.id.spinner_female_raasi);
            this.femaleNatchathiram = (Spinner) findViewById(R.id.spinner_female_natchathiram);
            this.maleRaasi = (Spinner) findViewById(R.id.spinner_male_raasi);
            this.maleNatchathiram = (Spinner) findViewById(R.id.spinner_male_natchathiram);
            this.dhinaporuththam = (TextView) findViewById(R.id.dhinaPoruthamResult);
            this.femaleRaasi.setOnItemSelectedListener(this);
            this.maleRaasi.setOnItemSelectedListener(this);
            this.btnPoruthamSumbit = (Button) findViewById(R.id.btn_submit);
            this.btnPoruthamSumbit.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (MarriageMatchActivity.this.femaleRaasi.getSelectedItemPosition() == 0 || MarriageMatchActivity.this.femaleNatchathiram.getSelectedItemPosition() == 0 || MarriageMatchActivity.this.maleRaasi.getSelectedItemPosition() == 0 || MarriageMatchActivity.this.maleNatchathiram.getSelectedItemPosition() == 0) {
                        Toast.makeText(MarriageMatchActivity.this, "திருமணப்  பொருத்தம் பெறத் தேவையான  அனைத்து தவகல்களையும் தருக...", Toast.LENGTH_LONG).show();
                        return;
                    }
                    try {
                        totalCount = 0;

                        ImageView poruthamImg1 = (ImageView) findViewById(R.id.poruthamImg1);
                        ImageView poruthamImg2 = (ImageView) findViewById(R.id.poruthamImg2);
                        ImageView poruthamImg3 = (ImageView) findViewById(R.id.poruthamImg3);
                        ImageView poruthamImg4 = (ImageView) findViewById(R.id.poruthamImg4);
                        ImageView poruthamImg5 = (ImageView) findViewById(R.id.poruthamImg5);
                        ImageView poruthamImg6 = (ImageView) findViewById(R.id.poruthamImg6);
                        ImageView poruthamImg7 = (ImageView) findViewById(R.id.poruthamImg7);
                        ImageView poruthamImg8 = (ImageView) findViewById(R.id.poruthamImg8);
                        ImageView poruthamImg9 = (ImageView) findViewById(R.id.poruthamImg9);
                        ImageView poruthamImg10 = (ImageView) findViewById(R.id.poruthamImg10);

                        ImageView poruthambox1 = (ImageView) findViewById(R.id.poruthambox1);
                        ImageView poruthambox2 = (ImageView) findViewById(R.id.poruthambox2);
                        ImageView poruthambox3 = (ImageView) findViewById(R.id.poruthambox3);
                        ImageView poruthambox4 = (ImageView) findViewById(R.id.poruthambox4);
                        ImageView poruthambox5 = (ImageView) findViewById(R.id.poruthambox5);
                        ImageView poruthambox6 = (ImageView) findViewById(R.id.poruthambox6);
                        ImageView poruthambox7 = (ImageView) findViewById(R.id.poruthambox7);
                        ImageView poruthambox8 = (ImageView) findViewById(R.id.poruthambox8);
                        ImageView poruthambox9 = (ImageView) findViewById(R.id.poruthambox9);
                        ImageView poruthambox10 = (ImageView) findViewById(R.id.poruthambox10);


                        MarriageMatchActivity.this.femaleNatchathiramVO = (NatchathiramVO) MarriageMatchActivity.this.femaleNatchathiram.getSelectedItem();
                        MarriageMatchActivity.this.maleNatchathiramVO = (NatchathiramVO) MarriageMatchActivity.this.maleNatchathiram.getSelectedItem();
                        MarriageMatchActivity.this.poruthamList = new ThirumanaPoruthamUtil().getOverAllScore(MarriageMatchActivity.this.femaleRaasi.getSelectedItemPosition(), MarriageMatchActivity.this.femaleNatchathiramVO.getNatchathiramID().intValue(), MarriageMatchActivity.this.maleRaasi.getSelectedItemPosition(), MarriageMatchActivity.this.maleNatchathiramVO.getNatchathiramID().intValue());
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.dhinaPoruthamResult), MarriageMatchActivity.this.poruthamList[1].intValue(), poruthamImg1, poruthambox1);
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.ganaPoruthamResult), MarriageMatchActivity.this.poruthamList[2].intValue(), poruthamImg2, poruthambox2);
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.mahendhraPoruthamResult), MarriageMatchActivity.this.poruthamList[3].intValue(), poruthamImg3, poruthambox3);
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.sthreegaPoruthamResult), MarriageMatchActivity.this.poruthamList[4].intValue(), poruthamImg4, poruthambox4);
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.yoniPoruthamResult), MarriageMatchActivity.this.poruthamList[5].intValue(), poruthamImg5, poruthambox5);
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.raasiPoruthamResult), MarriageMatchActivity.this.poruthamList[6].intValue(), poruthamImg6, poruthambox6);
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.raasiAdhipathiPoruthamResult), MarriageMatchActivity.this.poruthamList[7].intValue(), poruthamImg7, poruthambox7);
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.vasiyaPoruthamResult), MarriageMatchActivity.this.poruthamList[8].intValue(), poruthamImg8, poruthambox8);
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.rajjuPoruthamResult), MarriageMatchActivity.this.poruthamList[9].intValue(), poruthamImg9, poruthambox9);
                        MarriageMatchActivity.this.setResultSetAttributes((TextView) MarriageMatchActivity.this.findViewById(R.id.vedhaiPoruthamResult), MarriageMatchActivity.this.poruthamList[10].intValue(), poruthamImg10, poruthambox10);
                        TextView textView = (TextView) MarriageMatchActivity.this.findViewById(R.id.textView);
                        TextView numporuthmTV = (TextView) MarriageMatchActivity.this.findViewById(R.id.numporuthmTV);

                        numporuthmTV.setText(totalCount + "");

                        String[] stringArray = MarriageMatchActivity.this.getResources().getStringArray(R.array.porutham_array);
                        int ceil = (int) Math.ceil((double) (((float) MarriageMatchActivity.this.poruthamList[0].intValue()) / 10.0f));
                        if (ceil > 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("திருமணப் பொருத்தம் ");
                            stringBuilder.append(String.valueOf(ceil * 10));
                            stringBuilder.append("% சதவீதம் நன்றாக உள்ளது.");
                            textView.setText(stringBuilder.toString());
                            return;
                        }
                        textView.setText(stringArray[ceil - 1]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if (!getSharedPreferences(AppConstants.APP_PREF_KEY, 0).getBoolean(AppConstants.MARRIAGE_MATCHING_DICLAIMER_SHOWN_STATUS, false)) {
                showDisclaimer();
            }
//        if (!this.application.getActivityUsedStatus(AppConstants.ACTIVITY_PREFERENCE_KEYS[7])) {
//            this.application.setActivityUsedStatus(AppConstants.ACTIVITY_PREFERENCE_KEYS[7]);
//        }
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

    private void ShareFunc(){
        try{
            final RelativeLayout rootLayout = findViewById(R.id.root_marriage_match_layout);

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
                                    TakeScreenShot(MarriageMatchActivity.this,rootLayout,0,10,"0","திருமணப்பொருத்தம்",progressBar);
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

    private void showDisclaimer() {
        Builder builder = new Builder(this);
        View inflate = getLayoutInflater().inflate(R.layout.layout_dialog_marriage_match_disclaimer, null);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        inflate.findViewById(R.id.btn_dialog_accept).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Editor edit = MarriageMatchActivity.this.getSharedPreferences(AppConstants.APP_PREF_KEY, 0).edit();
                edit.putBoolean(AppConstants.MARRIAGE_MATCHING_DICLAIMER_SHOWN_STATUS, true);
                edit.apply();
                create.dismiss();
            }
        });
        create.show();
    }

    public void showThirumanaPoruthamTips(View view) {
        Intent intent = new Intent(this, HTMLViewActivity.class);
        intent.putExtra("TITLE", "Marriage Matching Tips");
        intent.putExtra("HTML_URL", "best_match_tips.html");
        startActivity(intent);
    }

    public void onUserInteraction() {
        super.onUserInteraction();
        this.userIsInteracting = true;
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
        if (this.userIsInteracting) {
            int id = adapterView.getId();
            SpinnerAdapter natchathiramDropDownAdapter;
            if (id == R.id.spinner_female_raasi) {
                natchathiramDropDownAdapter = new NatchathiramDropDownAdapter(this.porutham, 17367049, getDataForNatchathiramDropDowns(Integer.valueOf(adapterView.getSelectedItemPosition())), getResources());
//                natchathiramDropDownAdapter.setDropDownViewResource(17367049);
                this.femaleNatchathiram.setAdapter(natchathiramDropDownAdapter);
            } else if (id == R.id.spinner_male_raasi) {
                natchathiramDropDownAdapter = new NatchathiramDropDownAdapter(this.porutham, 17367049, getDataForNatchathiramDropDowns(Integer.valueOf(adapterView.getSelectedItemPosition())), getResources());
//                natchathiramDropDownAdapter.setDropDownViewResource(17367049);
//                natchathiramDropDownAdapter.
                this.maleNatchathiram.setAdapter(natchathiramDropDownAdapter);
            }
        }
    }

    int totalCount = 0;

    private void setResultSetAttributes(TextView textView, int i, ImageView imageView, ImageView poruthambox) {
        if (i == 10) {
            textView.setBackgroundColor(this.lightGreen);
            textView.setText(R.string.porutham_ok);
            textView.setVisibility(View.GONE);
            imageView.setImageResource(R.drawable.greentick);
            poruthambox.setImageResource(R.drawable.greenporutham);
            totalCount++;
        } else if (i == 0) {
            textView.setBackgroundColor(this.lightRed);
            textView.setVisibility(View.GONE);
            textView.setText(R.string.porutham_not_ok);
            imageView.setImageResource(R.drawable.red_tick);
            poruthambox.setImageResource(R.drawable.redporutham);
        } else if (i == 5) {
            textView.setBackgroundColor(this.lightGreen);
            textView.setVisibility(View.GONE);
            totalCount++;
            textView.setText(R.string.porutham_equal);
            imageView.setImageResource(R.drawable.greentick);
            poruthambox.setImageResource(R.drawable.greenporutham);
        }
    }

    private ArrayList<NatchathiramVO> getDataForNatchathiramDropDowns(Integer num) {
        String[] stringArray = getResources().getStringArray(R.array.natchathiram_array);
        ArrayList<NatchathiramVO> arrayList = new ArrayList();
        switch (num.intValue()) {
            case 0:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                break;
            case 1:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(1), stringArray[1]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(2), stringArray[2]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(3), stringArray[3]));
                break;
            case 2:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(3), stringArray[3]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(4), stringArray[4]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(5), stringArray[5]));
                break;
            case 3:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(5), stringArray[5]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(6), stringArray[6]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(7), stringArray[7]));
                break;
            case 4:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(7), stringArray[7]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(8), stringArray[8]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(9), stringArray[9]));
                break;
            case 5:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(10), stringArray[10]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(11), stringArray[11]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(12), stringArray[12]));
                break;
            case 6:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(12), stringArray[12]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(13), stringArray[13]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(14), stringArray[14]));
                break;
            case 7:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(14), stringArray[14]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(15), stringArray[15]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(16), stringArray[16]));
                break;
            case 8:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(16), stringArray[16]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(17), stringArray[17]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(18), stringArray[18]));
                break;
            case 9:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(19), stringArray[19]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(20), stringArray[20]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(21), stringArray[21]));
                break;
            case 10:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(21), stringArray[21]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(22), stringArray[22]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(23), stringArray[23]));
                break;
            case 11:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(23), stringArray[23]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(24), stringArray[24]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(25), stringArray[25]));
                break;
            case 12:
                arrayList.add(new NatchathiramVO(Integer.valueOf(0), stringArray[0]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(25), stringArray[25]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(26), stringArray[26]));
                arrayList.add(new NatchathiramVO(Integer.valueOf(27), stringArray[27]));
                break;
        }
        return arrayList;
    }

    private int getDialogTheme() {
//        switch (this.application.theme.value) {
//            case 1:
//                return R.style.DialogThemeBlue;
//            case 2:
//                return R.style.DialogThemeGreen;
//            case 3:
//                return R.style.DialogThemePurple;
//            case 4:
//                return R.style.DialogThemeYellow;
//            default:
//                return R.style.DialogThemeRed;
//        }
        return 0;
    }

    public void shareImage(View view, ProgressDialog progressDialog, View view2) {
        if (view != null) {
            findViewById(R.id.layout_sudhakar_share).setVisibility(View.VISIBLE);
            Parcelable imageUri = getImageUri(getBitmapFromView(view, view.getWidth(), view.getHeight()));
            if (imageUri != null) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/*");
                intent.putExtra("android.intent.extra.STREAM", imageUri);
//                intent.setFlags(1);
                if (view2.getId() == R.id.btn_whatsapp_share) {
                    intent.setPackage("com.whatsapp");
                    if (isWhatsAppInstalled()) {
                        startActivityForResult(intent, PointerIconCompat.TYPE_CONTEXT_MENU);
                    } else {
                        Toast.makeText(this, "Whatsapp not installed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    startActivityForResult(intent, PointerIconCompat.TYPE_CONTEXT_MENU);
                }
            }
            progressDialog.dismiss();
        }
    }

    @SuppressLint("WrongConstant")
    private boolean isWhatsAppInstalled() {
        try {
            getPackageManager().getPackageInfo("com.whatsapp", 1);
            return true;
        } catch (NameNotFoundException unused) {
            return false;
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            File externalCacheDir = getExternalCacheDir();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(String.valueOf(currentTimeMillis));
            stringBuilder.append(".png");
            File file = new File(externalCacheDir, stringBuilder.toString());
            OutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            file.setReadable(true, false);
            return Uri.fromFile(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap getBitmapFromView(View view, int i, int i2) {
        Bitmap createBitmap;
        Exception e;
        try {
            createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
            try {
                view.draw(new Canvas(createBitmap));
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            createBitmap = null;
            e.printStackTrace();
            return createBitmap;
        }
        return createBitmap;
    }

    public void onShareMarriageMatch(final View view) {
        if (((TextView) findViewById(R.id.dhinaPoruthamResult)).length() > 0) {
            final ProgressDialog progressDialog = new ProgressDialog(this, getDialogTheme());
            final View findViewById = findViewById(R.id.tbl_root_marriage_match_layout);
            findViewById(R.id.layout_sudhakar_share).setVisibility(View.VISIBLE);
            progressDialog.setMessage("படம் தயாராகுகிறது...");
            progressDialog.show();
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    MarriageMatchActivity.this.shareImage(findViewById, progressDialog, view);
                }
            }, 1500);
            return;
        }
        Toast.makeText(this, "திருமணப்  பொருத்தம் பெறத் தேவையான  அனைத்து தவகல்களையும் சமர்ப்பிக்கவும்", Toast.LENGTH_LONG).show();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == PointerIconCompat.TYPE_CONTEXT_MENU) {
            findViewById(R.id.layout_sudhakar_share).setVisibility(View.VISIBLE);
        }
    }
}
