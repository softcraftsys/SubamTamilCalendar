package com.softcraft.calendar.DevotionalWallpapers;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.softcraft.calendar.ServiceAndOthers.HttpHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class DevotionalWallpapersActivity extends AppCompatActivity {
    private NativeExpressAdView viewNativeAd;
    LinearLayout linearad;
    AdView adview;
    ProgressBar progressBar;
    int iFlag;
    ArrayList<ArrayList<String>> arrWallpapersData = new ArrayList<>();
    String strImagesUrl = "http://adsenseusers.com/scsadcontrol/api/device4/wallpapersdata/format/json";
    RelativeLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devotional_wallpapers);
        try {
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
            progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
            new GetJSONData().execute();
            ShareFunc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onInitializeItems() {
        try {
            ImageView ivBack = (ImageView) findViewById(R.id.back_arrow);
            TextView tvHeader = (TextView) findViewById(R.id.title_header);
            linearad = (LinearLayout) findViewById(R.id.notification_adview);

            ivBack.setOnClickListener(clickListener);
            tvHeader.setOnClickListener(clickListener);

            try {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 3);
                recyclerView.setLayoutManager(mLayoutManager);
                if (isTablet()) {
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(4, dpToPx(2), true));
                } else {
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, dpToPx(2), true));
                }
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(new RecyclerViewAdpater(getApplicationContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            tvHeader.setText(getResources().getString(R.string.devotional_wall));

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

    private boolean isTablet() {
        return (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    private class RecyclerViewAdpater extends RecyclerView.Adapter<RecyclerViewAdpater.MyViewHolder> {

        private Context mContext;
        private int iItemCount;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView ivImageThumb;

            public MyViewHolder(View view) {
                super(view);
                ivImageThumb = (ImageView) view.findViewById(R.id.thumbnail);
            }
        }

        public RecyclerViewAdpater(Context mContext) {
            this.mContext = mContext;
            this.iItemCount = arrWallpapersData.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = null;
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.devotional_wallpaper_cardview_layout, parent, false);
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            try {
//                DevotionalWallpapers devotionalWallpapers = new DevotionalWallpapers();
//                List<Wallpaperdatum> listWallpaperData = devotionalWallpapers.getWallpaperdata();
//                Wallpaperdatum wallpaperdatum = listWallpaperData.get(position);
//                String strImgUrl = wallpaperdatum.getImage();
//                final String strImageName = wallpaperdatum.getTitle();

                final String strImageName = arrWallpapersData.get(position).get(3);
                String strImgUrl = arrWallpapersData.get(position).get(4);

                Glide.with(mContext).load(strImgUrl).into(holder.ivImageThumb);
                holder.ivImageThumb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkInternetConnection(position, strImageName);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return iItemCount;
        }
    }

    public void checkInternetConnection(int position, String strImageName) {
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    Intent intent = new Intent(this, DevotionalWallpepersPreviewActivity.class);
                    intent.putExtra("ItemPosition", position);
                    intent.putExtra("ItemName", strImageName);
                    intent.putExtra("arrWallpapersList", arrWallpapersData);
                    startActivity(intent);
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Intent intent = new Intent(this, DevotionalWallpepersPreviewActivity.class);
                    intent.putExtra("ItemPosition", position);
                    intent.putExtra("ItemName", strImageName);
                    intent.putExtra("arrWallpapersList", arrWallpapersData);
                    startActivity(intent);
                }
            } else {
                Toast toast = Toast.makeText(DevotionalWallpapersActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                toast.show();
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

    private class GetJSONData extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(strImagesUrl);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray jaWallpaperData = jsonObj.getJSONArray("wallpaperdata");
                    for (int i = 0; i < jaWallpaperData.length(); i++) {
                        JSONObject jsonObject = jaWallpaperData.getJSONObject(i);
                        try {
                            String id = jsonObject.getString("id");
                            String cat = jsonObject.getString("cat");
                            String subcat = jsonObject.getString("subcat");
                            String title = jsonObject.getString("title");
                            String image = jsonObject.getString("image");
                            String imagesize = jsonObject.getString("imagesize");
                            String screentype = jsonObject.getString("screentype");
                            String dimension = jsonObject.getString("dimension");

//                            Wallpaperdatum wallpaperdatum = new Wallpaperdatum();
//                            wallpaperdatum.setId(id);
//                            wallpaperdatum.setCat(cat);
//                            wallpaperdatum.setSubcat(subcat);
//                            wallpaperdatum.setTitle(title);
//                            wallpaperdatum.setImage(image);
//                            wallpaperdatum.setImagesize(imagesize);
//                            wallpaperdatum.setScreentype(screentype);
//                            wallpaperdatum.setDimension(dimension);

                            ArrayList<String> arrImageData = new ArrayList<>();
                            arrImageData.add(id);
                            arrImageData.add(cat);
                            arrImageData.add(subcat);
                            arrImageData.add(title);
                            arrImageData.add(image);
                            arrImageData.add(imagesize);
                            arrImageData.add(screentype);
                            arrImageData.add(dimension);

                            arrWallpapersData.add(arrImageData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (final JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            try {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                onInitializeItems();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setAnimationFunc(View view) {
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DevotionalWallpapersActivity.this, view);
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

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
                                    TakeScreenShot(DevotionalWallpapersActivity.this,rootLayout,0,25,"0","தெய்வீகப் படங்கள்",progressBar);
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




