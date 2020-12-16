package com.softcraft.calendar.Episodes;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class EpisodesMainCategoryActivity extends AppCompatActivity {
    private NativeExpressAdView viewNativeAd;
    LinearLayout linearad;
    AdView adview;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes_main_category);
        try{
            progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
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

            try {
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.Episodes_RecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            ivBack.setOnClickListener(clickListener);
            tvHeader.setOnClickListener(clickListener);

            try {
                if (MiddlewareInterface.bRendering) {
                    tvHeader.setText(getResources().getString(R.string.episodes_title));
                    tvHeader.setTypeface(Typeface.DEFAULT);
                } else {
                    tvHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.episodes_title)));
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

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        Context mContext;
        View viewSub;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvCategoryName;
            private ImageView ivCoverImage;
            CardView cardView;

            public ViewHolder(View view) {
                super(view);
                ivCoverImage = (ImageView) view.findViewById(R.id.categoryCoverImage);
                tvCategoryName = (TextView) view.findViewById(R.id.categoryNameTv);
                cardView = (CardView) view.findViewById(R.id.cardViewCategory);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + tvCategoryName.getText();
            }
        }

        public RecyclerViewAdapter(Context context) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                mContext = context;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adpater_episode_category_layout, parent, false);
            viewSub = view;
            view.setBackgroundResource(mBackground);
            return new RecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int itemPosition) {
            try {
                if(itemPosition==0 || itemPosition==2){
                    try {
                        holder.tvCategoryName.setText("ராமாயணம்");
                        Glide.with(mContext)
                                .load("https://i.ytimg.com/vi/XcILIU0yhm8/maxresdefault.jpg")
                                .into(holder.ivCoverImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else {
                    try {
                        holder.tvCategoryName.setText("மகாபாரதம்");
                        Glide.with(mContext)
                                .load("https://i.ytimg.com/vi/XcILIU0yhm8/maxresdefault.jpg")
                                .into(holder.ivCoverImage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            Intent intent = new Intent(getApplicationContext(),EpisodesDetailPageActivity.class);
                            intent.putExtra("EpisodeTitle",holder.tvCategoryName.getText());
                            startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }

    private void setAnimationFunc(View view) {
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(EpisodesMainCategoryActivity.this, view);
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
