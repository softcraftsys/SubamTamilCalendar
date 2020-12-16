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
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class ShareMarketActivity extends AppCompatActivity {
    private NativeExpressAdView viewNativeAd;
    LinearLayout linearad;
    AdView adview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_market);
        try{
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            onInitializeItems();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void onInitializeItems() {
        try {
            ImageView ivBack = (ImageView) findViewById(R.id.back_arrow);
            TextView tvHeader = (TextView) findViewById(R.id.title_header);
            linearad = (LinearLayout) findViewById(R.id.notification_adview);
            ViewPager viewPager = (ViewPager) findViewById(R.id.shareMarketViewpager);

            viewPager.setAdapter(new ViewPagerAdapter(getApplicationContext()));

            ivBack.setOnClickListener(clickListener);
            tvHeader.setOnClickListener(clickListener);

            try {
                if (MiddlewareInterface.bRendering) {
                    tvHeader.setText(getResources().getString(R.string.share_market));
                    tvHeader.setTypeface(Typeface.DEFAULT);
                } else {
                    tvHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.share_market)));
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

    private class ViewPagerAdapter extends PagerAdapter {
        Context context;
        private LayoutInflater layoutInflater;

        public ViewPagerAdapter(Context ctx) {
            this.context = ctx;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = layoutInflater.inflate(R.layout.samples, container, false);
            try {
                TextView titleTv = (TextView) itemView.findViewById(R.id.itemTitleTv);
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

    private void setAnimationFunc(View view) {
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(ShareMarketActivity.this, view);
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
}
