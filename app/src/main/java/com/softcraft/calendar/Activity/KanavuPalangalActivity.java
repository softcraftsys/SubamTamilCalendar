package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.util.ArrayList;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class KanavuPalangalActivity extends AppCompatActivity {
    private NativeExpressAdView viewNativeAd;
    LinearLayout linearad;
    AdView adview;
    ArrayList<String> arrKanavuPalangal;
    int iLastRecyclerPos;
    ViewPager viewPager;
    RelativeLayout rootLayout;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanavu_palangal);
        try {

            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            onInitializeItems();
            ShareFunc();
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void onInitializeItems() {
        try {
            ImageView ivBack = (ImageView) findViewById(R.id.back_arrow);
            TextView tvHeader = (TextView) findViewById(R.id.title_header);
            linearad = (LinearLayout) findViewById(R.id.notication_adview);
            viewPager = (ViewPager) findViewById(R.id.kanavuPalangalViewpager);
            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
            progressBar = (ProgressBar) findViewById(R.id.progress_bar);


            viewPager.setAdapter(new ViewPagerAdapter(getApplicationContext()));

            try {
                DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                arrKanavuPalangal = db.dGetKanavuPalangal();
            } catch (Exception e) {
                e.printStackTrace();
            }

            ivBack.setOnClickListener(clickListener);
            tvHeader.setOnClickListener(clickListener);

            try {
                if (MiddlewareInterface.bRendering) {
                    tvHeader.setText(getResources().getString(R.string.kanavu_palan));
                    tvHeader.setTypeface(Typeface.DEFAULT);
                } else {
                    tvHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.kanavu_palan)));
                    tvHeader.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                try {
                    if (getEnable.equalsIgnoreCase("1")) {
                        if (getBannerType.equalsIgnoreCase("0")) {
                            setAdvertise();
                        } else {
                            setNativeSmall();
                        }
                    }else if (getEnable.equalsIgnoreCase( "4" )) {
                        if (getFacebookBannerType.equalsIgnoreCase( "1" )) {
                            MiddlewareInterface.setNativeFBAD(this,linearad);
                        } else {
                            MiddlewareInterface.setBannerFBAD(this,linearad);
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

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.back_arrow || view.getId() == R.id.title_header) {
                setAnimationFunc(view);
            }
        }
    };

    private void setAnimationFunc(View view) {
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(KanavuPalangalActivity.this, view);
            zoomAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    try {
//                        finish();
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
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    private class ViewPagerAdapter extends PagerAdapter {
        Context context;
        private LayoutInflater layoutInflater;
        private ImageView sleepCoverImage;

        public ViewPagerAdapter(Context ctx) {
            this.context = ctx;
        }

        @Override
        public int getCount() {
            return 11;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = layoutInflater.inflate(R.layout.kanavupalan_viewpager_adapter, container, false);
            try {
                sleepCoverImage = (ImageView) itemView.findViewById(R.id.sleepCoverImage);
                ArrayList<String> arrKanavuPalangalList = arrListSplittedPalangal(position);

                LinearLayout inflateLayout = (LinearLayout) itemView.findViewById(R.id.kanavuInflateLayout);
                for (int i = 0; i < arrKanavuPalangalList.size(); i++) {
                    try {
                        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View inflateView = layoutInflater.inflate(R.layout.kanavupalan_inflate_layout, null);

                        TextView palangalTv = (TextView) inflateView.findViewById(R.id.palangalTv);
                        ImageView pointIV = (ImageView) inflateView.findViewById(R.id.pointIV);

                        if (MiddlewareInterface.bRendering) {
                            palangalTv.setText(arrKanavuPalangalList.get(i));
                            if (i == 0 && position == 0) {
                                sleepCoverImage.setVisibility(View.VISIBLE);
                                palangalTv.setTypeface(Typeface.DEFAULT_BOLD);
                                pointIV.setVisibility(View.GONE);
                            } else {
                                palangalTv.setTypeface(Typeface.DEFAULT);
                                pointIV.setVisibility(View.VISIBLE);
                            }
                        } else {
                            palangalTv.setText(UnicodeUtil.unicode2tsc(arrKanavuPalangalList.get(i)));
                            palangalTv.setTypeface(MiddlewareInterface.tf_mylai);
                        }
                        inflateLayout.addView(inflateView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
    }


    private ArrayList<String> arrListSplittedPalangal(int position) {
        ArrayList<String> arrPalangal = new ArrayList<>();
        try {
            if (position == 0) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(0)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(1)));
            } else if (position == 1) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(2)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(3)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(4)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(5)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(6)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(27)));
            } else if (position == 2) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(7)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(8)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(9)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(10)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(11)));
            } else if (position == 3) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(12)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(13)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(14)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(15)));
            } else if (position == 4) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(16)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(17)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(18)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(19)));
            } else if (position == 5) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(20)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(21)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(22)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(23)));
            } else if (position == 6) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(24)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(25)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(26)));
            } else if (position == 7) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(28)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(29)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(30)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(31)));
            } else if (position == 8) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(32)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(33)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(34)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(35)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(47)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(48)));
            } else if (position == 9) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(36)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(37)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(38)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(39)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(45)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(46)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(49)));
            } else if (position == 10) {
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(40)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(41)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(42)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(43)));
                arrPalangal.add(String.valueOf(arrKanavuPalangal.get(44)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrPalangal;
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
                                    TakeScreenShot(KanavuPalangalActivity.this, rootLayout, 0,22,"0","கனவுப்பலன்கள்",progressBar);
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





