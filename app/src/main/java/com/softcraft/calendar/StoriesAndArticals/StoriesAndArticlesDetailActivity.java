package com.softcraft.calendar.StoriesAndArticals;

import android.animation.Animator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class StoriesAndArticlesDetailActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    String typeId, strHeading;
    ImageView gotoTopBtn, shareBtn;
    ArrayList<ArrayList<String>> bookTypeList = new ArrayList<>();
    ArrayList<ArrayList<String>> authorList = new ArrayList<>();
    ArrayList<ArrayList<String>> bookDataList = new ArrayList<>();
    ArrayList<ArrayList<String>> categoryList = new ArrayList<>();
    HashMap<String, ArrayList> itemsDataList = new HashMap();
    LinearLayout linearad;
    private File imagePath;
    private NativeExpressAdView viewNativeAd;
    AdView adview;
    private TextView contentTv;
    private ImageView contentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
        setContentView(R.layout.stories_and_articles_detail_activity);
        try {
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            onIntializeItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onIntializeItems() {
        final ImageView backBtn = (ImageView) findViewById(R.id.back_arrow);
        gotoTopBtn = (ImageView) findViewById(R.id.gotoTopImg);
        shareBtn = (ImageView) findViewById(R.id.shareImg);
        final TextView titleHead = (TextView) findViewById(R.id.title_header);
//        viewPager = (ViewPager) findViewById(R.id.viewPagerStoriesDetail);
        linearad = (LinearLayout) findViewById(R.id.notication_adview);
        contentImage = (ImageView) findViewById(R.id.contentImage);
        final NestedScrollView nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        contentTv = (TextView) findViewById(R.id.contentTV);

        backBtn.setOnClickListener(clickListener);
        titleHead.setOnClickListener(clickListener);
        try {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
            titleHead.startAnimation(animation);
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

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        bookDataList = (ArrayList<ArrayList<String>>) b.getSerializable("bookDataList");
        strHeading = (String) b.get("pageTitle");
        String authorName = (String) b.get("authorName");
        typeId = (String) b.get("typeId");

        final String sharecontent = strHeading + "-" + authorName;
        shareBtn.setOnClickListener(clickListener);

        if (authorName != null) {
            try {
                if (MiddlewareInterface.bRendering) {
                    titleHead.setText(authorName);
                    titleHead.setTypeface(Typeface.DEFAULT);
                } else {
                    titleHead.setText(UnicodeUtil.unicode2tsc(authorName));
                    titleHead.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (bookDataList.size() != 0) {
            for (int i = 0; i < bookDataList.size(); i++) {
                ArrayList<String> bookData = bookDataList.get(i);
                String keyTypeId = bookData.get(6);
                itemsDataList.put(keyTypeId, bookData);
            }
        }

        try {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
            contentTv.startAnimation(animation);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> contentList = itemsDataList.get(typeId);

        try {
            if (MiddlewareInterface.bRendering) {
                contentTv.setText(contentList.get(7));
                contentTv.setTypeface(Typeface.DEFAULT);
            } else {
                contentTv.setText(UnicodeUtil.unicode2tsc(contentList.get(7)));
                contentTv.setTypeface(MiddlewareInterface.tf_mylai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Glide.with(StoriesAndArticlesDetailActivity.this)
                .load(contentList.get(9))
                .into(contentImage);

        gotoTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(StoriesAndArticlesDetailActivity.this, gotoTopBtn);
                zoomAnimation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        try {
                            nestedScrollView.fullScroll(NestedScrollView.FOCUS_UP);
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

//        setupviewPager();
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            setAnimation(view);
        }
    };

    private void setAnimation(final View view) {
        try {
            try {
                Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(StoriesAndArticlesDetailActivity.this, shareBtn);
                zoomAnimation.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        try {
                            if (view.getId() == R.id.back_arrow || view.getId() == R.id.title_header) {
                                onBackPressed();
                            } else if (view.getId() == R.id.shareImg) {
                                Bitmap bitmap = getScreenShot();
                                saveBitmap(bitmap);
                                if (bitmap != null) {
                                    shareImage();
                                }
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
        } catch (Exception e) {
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

    private void setupviewPager() {
        try {
            ImageView coverImage = (ImageView) findViewById(R.id.coverImage);
            ArrayList<String> List = itemsDataList.get(typeId);
            Glide.with(StoriesAndArticlesDetailActivity.this)
                    .load(List.get(10))
                    .into(coverImage);
            tabLayout = (TabLayout) findViewById(R.id.tabLayoutView);
//            ViewPagerAdapter mAdapter = new ViewPagerAdapter(getSupportFragmentManager(),getApplicationContext(), List);
            ViewPagerAdapter mAdapter = new ViewPagerAdapter(getApplicationContext(), List);
//            mAdapter.addFragment(new StoriesDetailFragment());
            viewPager.setAdapter(mAdapter);
            tabLayout.setupWithViewPager(viewPager);
            TabLayout.Tab tab = tabLayout.getTabAt(0);
            tab.setCustomView(getTabView());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public View getTabView() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_layout_view, null);
        TextView textView = (TextView) view.findViewById(R.id.textViewTab);
        textView.setText(strHeading);
        return view;
    }

    private class ViewPagerAdapter extends PagerAdapter {
        //        private final List<Fragment> mFragmentList = new ArrayList<>();
        Context context;
        ArrayList<String> contentList;
        private LayoutInflater layoutInflater;
        private TextView contentTv, authorName;
        private ImageView contentImage;

        public ViewPagerAdapter(Context ctx, ArrayList<String> content) {
            this.context = ctx;
            this.contentList = content;
        }

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            View view = layoutInflater.inflate(R.layout.stories_detail_recycler_content_layout, container, false);
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = layoutInflater.inflate(R.layout.stories_detail_recycler_content_layout, container, false);

            contentImage = (ImageView) itemView.findViewById(R.id.contentImage);
            final NestedScrollView nestedScrollView = (NestedScrollView) itemView.findViewById(R.id.nestedScrollView);
            contentTv = (TextView) itemView.findViewById(R.id.contentTV);
            try {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
                contentTv.startAnimation(animation);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (MiddlewareInterface.bRendering) {
                    contentTv.setText(UnicodeUtil.unicode2tsc(contentList.get(7)));
                    contentTv.setTypeface(Typeface.DEFAULT);
                } else {
                    contentTv.setText(UnicodeUtil.unicode2tsc(contentList.get(7)));
                    contentTv.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            contentTv.setText(contentList.get(7));
            Glide.with(StoriesAndArticlesDetailActivity.this)
                    .load(contentList.get(9))
                    .into(contentImage);

            gotoTopBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(StoriesAndArticlesDetailActivity.this, gotoTopBtn);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                nestedScrollView.fullScroll(NestedScrollView.FOCUS_UP);
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

            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }

//    class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        Context context;
//        ArrayList<String> samplecontent;
//
//        public ViewPagerAdapter(FragmentManager manager, Context ctx, ArrayList<String> content) {
//            super(manager);
//            context = ctx;
//            samplecontent = content;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return StoriesDetailFragment.newInstance(position);
//        }
//
//        @Override
//        public int getCount() {
//            return 5;
//        }
//
//        public void addFragment(Fragment fragment) {
//            mFragmentList.add(fragment);
//        }
//
//        public View getTabView(int position) {
//            View view = LayoutInflater.from(context).inflate(R.layout.tab_layout_view, null);
//            TextView textView = (TextView) view.findViewById(R.id.textViewTab);
//            textView.setText("sample heading");
//            return view;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return null;
//        }
//    }

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
}
