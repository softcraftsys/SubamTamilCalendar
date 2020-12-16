package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import java.util.HashSet;
import java.util.Set;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.tf_baamini;

public class BabyNamesActivity extends AppCompatActivity {
    private NativeExpressAdView viewNativeAd;
    RelativeLayout gridBackgroundLayout;
    LinearLayout linearad;
    AdView adview;
    String strDBReligion, strDBGender, strDBLetter;
    DataBaseHelper db;
    Boolean isSecondTime = false;
    ArrayList<String> filteredLetterArrList = new ArrayList<>();
    RelativeLayout rootLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_names);
        try {

            try {
                tf_baamini = Typeface.createFromAsset(getAssets(), "fonts/baaminii.ttf");
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
                                    TakeScreenShot(BabyNamesActivity.this, rootLayout, 0, 24, "0", "குழந்தைப்பெயர்கள்", progressBar);
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

    private void onInitializeItems() {
        try {
            db = new DataBaseHelper(getApplicationContext());
            ImageView ivBack = (ImageView) findViewById(R.id.back_arrow);
            TextView getNameTv = (TextView) findViewById(R.id.getNamesTv);
            TextView tvHeader = (TextView) findViewById(R.id.title_header);
            final TextView alphabetTv = (TextView) findViewById(R.id.alphabetTv);
            linearad = (LinearLayout) findViewById(R.id.notification_adview);
            Spinner spinReligion = (Spinner) findViewById(R.id.religionSpin);
            final GridView gvBabyNames = (GridView) findViewById(R.id.babyNamesGrid);
            Spinner spinMaleFemale = (Spinner) findViewById(R.id.maleFemaleSpin);

            progressBar = (ProgressBar) findViewById(R.id.progress_bar);

            final RecyclerView rvBabyNames = (RecyclerView) findViewById(R.id.babyNamesRecycler);
            gridBackgroundLayout = (RelativeLayout) findViewById(R.id.backgroundGridLayout);
            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);
            RelativeLayout alphabetLayout = (RelativeLayout) findViewById(R.id.alphabetSpin);
            RelativeLayout getNameLayout = (RelativeLayout) findViewById(R.id.getnameLayout);

            String[] strArrGernderTamil = getResources().getStringArray(R.array.gender_tamil);
            String[] strArrReligionTamil = getResources().getStringArray(R.array.religion_tamil);

            ArrayAdapter regionSpinAdpater = new ArrayAdapter(this, R.layout.spinner_custom_layout, strArrReligionTamil);
            spinReligion.setAdapter(regionSpinAdpater);
            ArrayAdapter malefemaleSpinAdapter = new ArrayAdapter(this, R.layout.spinner_custom_layout, strArrGernderTamil);
            spinMaleFemale.setAdapter(malefemaleSpinAdapter);

            spinMaleFemale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String[] strArrGernderBaamini = getResources().getStringArray(R.array.gender_baamini);
                        strDBGender = strArrGernderBaamini[position];
                        if (isSecondTime) {

                            final ArrayList<String> nameAlphabetsArrList = db.dGetAlphabetsOFBabyNames(strDBGender, strDBReligion);
                            try {
                                try {
                                    Set<String> hs = new HashSet<>();
                                    hs.addAll(nameAlphabetsArrList);
                                    nameAlphabetsArrList.clear();
                                    nameAlphabetsArrList.addAll(hs);
                                    filteredLetterArrList = nameAlphabetsArrList;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                AlphabetGridAdapter gridViewAdapter = new AlphabetGridAdapter(getApplicationContext(), nameAlphabetsArrList);
                                gvBabyNames.setAdapter(gridViewAdapter);

                                int iFirstLetterPos = 0;
                                for (int i = 0; i < nameAlphabetsArrList.size(); i++) {
                                    if (nameAlphabetsArrList.get(i).equalsIgnoreCase("m")) {
                                        iFirstLetterPos = i;
                                        strDBLetter = nameAlphabetsArrList.get(i);
                                    }
                                }
                                alphabetTv.setText(nameAlphabetsArrList.get(iFirstLetterPos));
                                alphabetTv.setTypeface(MiddlewareInterface.tf_baamini);

                                ArrayList<ArrayList<String>> nameAndMeaningArrList = db.dGetBabyNamesAndMeaning(strDBGender, strDBReligion, strDBLetter);
                                rvBabyNames.setAdapter(new BabyNamesRecyclerAdapter(getApplicationContext(), nameAndMeaningArrList));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            spinReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        String[] strArrReligionBaamini = getResources().getStringArray(R.array.religion_baamini);
                        strDBReligion = strArrReligionBaamini[position];
                        if (isSecondTime) {

                            final ArrayList<String> nameAlphabetsArrList = db.dGetAlphabetsOFBabyNames(strDBGender, strDBReligion);
                            try {
                                try {
                                    Set<String> hs = new HashSet<>();
                                    hs.addAll(nameAlphabetsArrList);
                                    nameAlphabetsArrList.clear();
                                    nameAlphabetsArrList.addAll(hs);
                                    filteredLetterArrList = nameAlphabetsArrList;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                AlphabetGridAdapter gridViewAdapter = new AlphabetGridAdapter(getApplicationContext(), nameAlphabetsArrList);
                                gvBabyNames.setAdapter(gridViewAdapter);

                                int iFirstLetterPos = 0;
                                for (int i = 0; i < nameAlphabetsArrList.size(); i++) {
                                    if (nameAlphabetsArrList.get(i).equalsIgnoreCase("m")) {
                                        iFirstLetterPos = i;
                                        strDBLetter = nameAlphabetsArrList.get(i);
                                    }
                                }
                                alphabetTv.setText(nameAlphabetsArrList.get(iFirstLetterPos));
                                alphabetTv.setTypeface(MiddlewareInterface.tf_baamini);

                                ArrayList<ArrayList<String>> nameAndMeaningArrList = db.dGetBabyNamesAndMeaning(strDBGender, strDBReligion, strDBLetter);
                                rvBabyNames.setAdapter(new BabyNamesRecyclerAdapter(getApplicationContext(), nameAndMeaningArrList));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            final ArrayList<String> nameAlphabetsArrList = db.dGetAlphabetsOFBabyNames("Mz;", ",e;J");
            try {
                try {
                    Set<String> hs = new HashSet<>();
                    hs.addAll(nameAlphabetsArrList);
                    nameAlphabetsArrList.clear();
                    nameAlphabetsArrList.addAll(hs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AlphabetGridAdapter gridViewAdapter = new AlphabetGridAdapter(getApplicationContext(), nameAlphabetsArrList);
                gvBabyNames.setAdapter(gridViewAdapter);
//                gvBabyNames.setAdapter(gridViewAdapter);

                int iFirstLetterPos = 0;
                for (int i = 0; i < nameAlphabetsArrList.size(); i++) {
                    if (nameAlphabetsArrList.get(i).equalsIgnoreCase("m")) {
                        iFirstLetterPos = i;
                        strDBLetter = nameAlphabetsArrList.get(i);
                    }
                }
                alphabetTv.setText(nameAlphabetsArrList.get(iFirstLetterPos));
                alphabetTv.setTypeface(MiddlewareInterface.tf_baamini);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (MiddlewareInterface.bRendering) {
                    tvHeader.setText(getResources().getString(R.string.baby_names));
                    getNameTv.setText(getResources().getString(R.string.get_name));
                    tvHeader.setTypeface(Typeface.DEFAULT);
                    getNameTv.setTypeface(Typeface.DEFAULT);
                } else {
                    tvHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.baby_names)));
                    getNameTv.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.get_name)));
                    tvHeader.setTypeface(MiddlewareInterface.tf_mylai);
                    getNameTv.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                ArrayList<ArrayList<String>> nameAndMeaningArrList = db.dGetBabyNamesAndMeaning("Mz;", ",e;J", "m"); // male - hindhu - அ
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                rvBabyNames.setLayoutManager(linearLayoutManager);
                rvBabyNames.setAdapter(new BabyNamesRecyclerAdapter(getApplicationContext(), nameAndMeaningArrList));
                isSecondTime = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            getNameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(BabyNamesActivity.this, v);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    ArrayList<ArrayList<String>> nameAndMeaningArrList = db.dGetBabyNamesAndMeaning(strDBGender, strDBReligion, strDBLetter);
                                    rvBabyNames.setAdapter(new BabyNamesRecyclerAdapter(getApplicationContext(), nameAndMeaningArrList));
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

            gvBabyNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        strDBLetter = filteredLetterArrList.get(position);
                        gridBackgroundLayout.setVisibility(View.GONE);
                        alphabetTv.setText(filteredLetterArrList.get(position));
                        alphabetTv.setTypeface(MiddlewareInterface.tf_baamini);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            ivBack.setOnClickListener(clickListener);
            tvHeader.setOnClickListener(clickListener);
            alphabetLayout.setOnClickListener(clickListener);
            gridBackgroundLayout.setOnClickListener(clickListener);

            setAdFunc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdFunc() {
        try {
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

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.back_arrow || view.getId() == R.id.title_header) {
                setAnimationFunc(view);
            } else if (view.getId() == R.id.alphabetSpin) {
                if (gridBackgroundLayout.getVisibility() == View.VISIBLE) {
                    gridBackgroundLayout.setVisibility(View.GONE);
//                    collapse(gridBackgroundLayout);
                } else {
                    gridBackgroundLayout.setVisibility(View.VISIBLE);
//                    expand(gridBackgroundLayout);
                }
            }
        }
    };

    private void setAnimationFunc(View view) {
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(BabyNamesActivity.this, view);
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
                e.printStackTrace();
            }

            viewNativeAd.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    Log.d("On Fail called", "Ad");
                    linearad.setVisibility(View.GONE);
                    super.onAdFailedToLoad(errorCode);
                }

                @Override
                public void onAdLoaded() {
                    Log.d("On Load called", "Ad");
                    linearad.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                }
            });
        } catch (Exception e) {
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
                e.printStackTrace();
            }
            adview.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    Log.d("On Fail called", "Ad");
                    linearad.setVisibility(View.GONE);
                    super.onAdFailedToLoad(errorCode);
                }

                @Override
                public void onAdLoaded() {
                    Log.d("On Load called", "Ad");
                    linearad.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class BabyNamesRecyclerAdapter extends RecyclerView.Adapter<BabyNamesRecyclerAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private int countItem;
        View view;
        ArrayList<ArrayList<String>> arrNameAndMeaningList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nameTv, meaningTv;

            private ViewHolder(View view) {
                super(view);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + nameTv.getText();
            }
        }

        private BabyNamesRecyclerAdapter(Context context, ArrayList<ArrayList<String>> nameAndMeaningList) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                arrNameAndMeaningList = nameAndMeaningList;
                countItem = arrNameAndMeaningList.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public BabyNamesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.babynames_recycler_adapter_layout, parent, false);
            ViewHolder vh = new ViewHolder(view);
            vh.nameTv = (TextView) view.findViewById(R.id.nameResultTv);
            vh.meaningTv = (TextView) view.findViewById(R.id.meaningResultTv);
            view.setBackgroundResource(mBackground);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int itemPosition) {
            try {
                holder.nameTv.setText(arrNameAndMeaningList.get(itemPosition).get(4));
                holder.meaningTv.setText(arrNameAndMeaningList.get(itemPosition).get(5));
                holder.meaningTv.setTypeface(MiddlewareInterface.tf_baamini);

                if (strDBReligion.equalsIgnoreCase("fpwp];JtH")) {
                    holder.nameTv.setTypeface(Typeface.DEFAULT);
                } else {
                    holder.nameTv.setTypeface(MiddlewareInterface.tf_baamini);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemViewType(int position) {
            return position % 2 * 2;
        }

        @Override
        public int getItemCount() {
            return countItem;
        }
    }


    public class AlphabetGridAdapter extends BaseAdapter {
        Context context;
        ArrayList<String> alphabetsList;
        private LayoutInflater inflater = null;

        private AlphabetGridAdapter(Context mContext, ArrayList<String> arrAlphabetList) {
            alphabetsList = arrAlphabetList;
            context = mContext;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return alphabetsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class Holder {
            TextView alphabetTv;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Holder holder = new Holder();
            View rowView;
            rowView = inflater.inflate(R.layout.babynames_grid_adapter_layout, null);
            holder.alphabetTv = (TextView) rowView.findViewById(R.id.grid_text);
            try {
                holder.alphabetTv.setText(alphabetsList.get(position));
                holder.alphabetTv.setTypeface(MiddlewareInterface.tf_baamini);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return rowView;
        }
    }

    @Override
    public void onBackPressed() {
        if (gridBackgroundLayout.getVisibility() == View.VISIBLE) {
            gridBackgroundLayout.setVisibility(View.GONE);
        } else {
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
    }

    public void expand(final RelativeLayout v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    public void collapse(final RelativeLayout v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}





