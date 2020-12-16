package com.softcraft.calendar.StoriesAndArticals;

import android.Manifest;
import android.animation.Animator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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
import com.softcraft.calendar.Activity.PDFViewerActivity;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.isNetworkStatusAvialable;

public class StoriesAuthorBooksActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    File pdfUrlFile;
    String SelectedBookTitle = "";
    ArrayList<ArrayList<String>> bookTypeList = new ArrayList<>();
    ArrayList<ArrayList<String>> authorList = new ArrayList<>();
    ArrayList<ArrayList<String>> bookDataList = new ArrayList<>();
    ArrayList<ArrayList<String>> sortedBookDataList = new ArrayList<>();
    ArrayList<ArrayList<String>> categoryList = new ArrayList<>();
    HashMap<String, ArrayList> authorDetails = new HashMap<>();
    LinearLayout linearad;
    Boolean isAvaliable;
    AdView adview;
    private NativeExpressAdView viewNativeAd;
    String strUrl = "";
    String strFileName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
        try {
            setContentView(R.layout.stories_author_books_activity);
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            final ImageView backBtn = (ImageView) findViewById(R.id.back_arrow);
            final TextView titleHead = (TextView) findViewById(R.id.title_header);
            progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
            linearad = (LinearLayout) findViewById(R.id.notication_adview);

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

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(StoriesAuthorBooksActivity.this, backBtn);
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
            titleHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(StoriesAuthorBooksActivity.this, titleHead);
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
            try {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.textview_anim);
                titleHead.startAnimation(animation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            try {
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.list_item_anim);
                recyclerView.setLayoutAnimation(new LayoutAnimationController(animation));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Bundle b = new Bundle();
            b = getIntent().getExtras();

            bookTypeList = (ArrayList<ArrayList<String>>) b.getSerializable("bookTypeList");
            authorList = (ArrayList<ArrayList<String>>) b.getSerializable("authorList");
            bookDataList = (ArrayList<ArrayList<String>>) b.getSerializable("bookDataList");
            categoryList = (ArrayList<ArrayList<String>>) b.getSerializable("categoryList");
            String pageTitle = (String) b.get("pageTitle");
            String categoryID = (String) b.get("categoryID");

            if (pageTitle != null)
                titleHead.setText(pageTitle);

            if (authorList != null) {
                for (int i = 0; i < authorList.size(); i++) {
                    ArrayList<String> specificList = authorList.get(i);
                    String authorId = specificList.get(0);
                    authorDetails.put(authorId, specificList);
                }
            }

            isAvaliable = false;
            for (int i = 0; i < bookDataList.size(); i++) {
                ArrayList<String> bookdata = bookDataList.get(i);
                if (categoryID.equalsIgnoreCase(bookdata.get(4))) {
                    isAvaliable = true;
                    sortedBookDataList.add(bookdata);
                }
            }
            if (isAvaliable) {
                if (bookDataList.size() != 0) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), sortedBookDataList));

                } else {
                    onBackPressed();
                }
            } else {
                if (bookDataList.size() != 0) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                    recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), bookDataList));

                } else {
                    onBackPressed();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private ArrayList<ArrayList<String>> bookDataArrList;
        Context mContext;
        View viewSub;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView authorName, bookName, downloadBtn;
            private ImageView authorImage, coverImage;
            CardView cardView;
            RelativeLayout downloadLayout, cardview_layout;

            public ViewHolder(View view) {
                super(view);
                authorImage = (ImageView) view.findViewById(R.id.book_img);
                bookName = (TextView) view.findViewById(R.id.book_name);
                authorName = (TextView) view.findViewById(R.id.author_name);
                downloadBtn = (TextView) view.findViewById(R.id.downloadRead);
                cardView = (CardView) view.findViewById(R.id.cardView);
                downloadLayout = (RelativeLayout) view.findViewById(R.id.downloadLayout);
                cardview_layout = (RelativeLayout) view.findViewById(R.id.cardview_layout);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + bookName.getText();
            }
        }

        public RecyclerViewAdapter(Context context, ArrayList<ArrayList<String>> bookDataList) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                bookDataArrList = bookDataList;
                mContext = context;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_listview_layout, parent, false);
            viewSub = view;
            view.setBackgroundResource(mBackground);
            return new RecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int itemPosition) {
            try {
                ArrayList<String> specificList = bookDataArrList.get(itemPosition);
                try {
                    if (MiddlewareInterface.bRendering) {
                        holder.bookName.setText(specificList.get(3));
                        holder.bookName.setTypeface(Typeface.DEFAULT);
                    } else {
                        holder.bookName.setText(UnicodeUtil.unicode2tsc(specificList.get(3)));
                        holder.bookName.setTypeface(MiddlewareInterface.tf_mylai);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String getBoolean = specificList.get(3);

                String strTypeId = specificList.get(6);

                if (strTypeId.equalsIgnoreCase("4")) {
                    holder.downloadLayout.setVisibility(View.GONE);
                } else {
                    holder.downloadLayout.setVisibility(View.VISIBLE);

                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
                    Boolean isDownloaded = sharedPrefs.getBoolean(getBoolean, false);

                    if (isDownloaded) {
                        holder.downloadBtn.setText("Read");
                    } else {
                        holder.downloadBtn.setText("Download");
                    }
                }

                String strAuthorId = specificList.get(5);
                final ArrayList<String> specAuthorDetail = authorDetails.get(strAuthorId);

                holder.authorName.setText(specAuthorDetail.get(1));
                Glide.with(StoriesAuthorBooksActivity.this)
                        .load(specificList.get(9))
                        .into(holder.authorImage);

                final int typeId = Integer.parseInt(strTypeId);

                holder.cardview_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(getApplicationContext(), holder.cardview_layout);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    gotoDetailPageFunc(typeId, itemPosition, bookDataArrList, specAuthorDetail, mContext);
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

        @Override
        public int getItemCount() {
            return bookDataArrList.size();
        }
    }

    public void download(String pdfLink, String filename) {
//        try {
//            Intent intent = new Intent(StoriesAuthorBooksActivity.this, StoryPage.class);
//            intent.putExtra("strURL", pdfLink);
//            startActivityForResult(intent, 5);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        new DownloadFile().execute(pdfLink, filename);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), sortedBookDataList));
        }
    }

    private void gotoDetailPageFunc(int typeId, int itemPosition, ArrayList<ArrayList<String>> bookDataArrList, ArrayList<String> specAuthorDetail, Context mContext) {
        try {
            if (typeId == 4) {
                ArrayList<String> specCategList = bookDataArrList.get(itemPosition);
                String pageTitle = specCategList.get(3);
                String strTypeId = specCategList.get(6);
                Bundle bundle = new Bundle();
                bundle.putSerializable("bookTypeList", bookTypeList);
                bundle.putSerializable("authorList", authorList);
                bundle.putSerializable("bookDataList", bookDataList);
                bundle.putSerializable("categoryList", categoryList);
                bundle.putString("pageTitle", pageTitle);
                bundle.putString("typeId", strTypeId);
                bundle.putString("authorName", specAuthorDetail.get(1));

                if (isNetworkStatusAvialable(mContext)) {
                    Intent intent = new Intent(mContext, StoriesAndArticlesDetailActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast toast = Toast.makeText(StoriesAuthorBooksActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                    toast.show();
                }

            } else if (typeId == 1) {
                ArrayList<String> specCategList = bookDataArrList.get(itemPosition);
                String pageTitle = specCategList.get(3);
                SelectedBookTitle = pageTitle;
                String url = specCategList.get(11);
                String[] file = url.split("/");
                String fileName = file[8];

                if (isNetworkStatusAvialable(mContext)) {
//                    download(url, fileName);
                    checkPermission(url, fileName);
                } else {
                    Toast toast = Toast.makeText(StoriesAuthorBooksActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                    toast.show();
                }
            } else if (typeId == 2) {
                Toast.makeText(StoriesAuthorBooksActivity.this, "Will be soon", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(StoriesAuthorBooksActivity.this, "Will be soon", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    String strURL;

    private class DownloadFile extends AsyncTask<String, Void, Void> {
        Boolean fileNotExist = false;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
                String fileName = strings[1];  // -> maven.pdf
                strURL = fileUrl;
                final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
                File folder = new File(dirPath, "/Files/");
                folder.mkdir();

                File pdfFile = new File(folder.getAbsolutePath(), fileName);

                if (!pdfFile.exists()) {
                    try {
                        pdfFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        fileNotExist = true;
                    }
                    pdfUrlFile = pdfFile;
                    FileDownloader.downloadFile(fileUrl, pdfFile);
                } else {
                    pdfUrlFile = pdfFile;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (!fileNotExist) {
                pdfReaderAction();
            } else {
                Toast.makeText(StoriesAuthorBooksActivity.this, "Try after some time", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pdfReaderAction() {
        try {
//            File fi = new File(fileStr);

            Uri uri = Uri.parse(pdfUrlFile.getAbsolutePath());
            //Uri uri = Uri.parse("file:///android_asset/" + TEST_FILE_NAME);

            SharedPreferences app_preferences1 = PreferenceManager.getDefaultSharedPreferences(StoriesAuthorBooksActivity.this);
            SharedPreferences.Editor editor = app_preferences1.edit();
            editor.putBoolean(SelectedBookTitle, true);
            editor.apply();

//            Intent intent = new Intent(StoriesAuthorBooksActivity.this, com.artifex.mupdflib.MuPDFActivity.class);
            Intent intent = new Intent(StoriesAuthorBooksActivity.this, PDFViewerActivity.class);
//            Intent intent = new Intent(StoriesAuthorBooksActivity.this, StoryPage.class);
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(uri);
            //if document protected with password
            intent.putExtra("password", "encrypted PDF password");
            //if you need highlight link boxes
            intent.putExtra("linkhighlight", true);
            //if you don't need device sleep on reading document
            intent.putExtra("idleenabled", false);
            String Str2 = "4";
            intent.putExtra("flag", 1);
            //intent.putExtra("rentonlyad", Str2);
            //set true value for horizontal page scrolling, false value for vertical page scrolling
            intent.putExtra("horizontalscrolling", true);
            //document name
            intent.putExtra("docname", "PDF document name");
            intent.putExtra("SelectedBookTitle", SelectedBookTitle);
            intent.putExtra("strURL", strURL);
//            startActivityForResult(intent, 5);
//            startActivity(intent);
            try {
                startActivityForResult(intent, 5);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(StoriesAuthorBooksActivity.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
            }

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

    private void checkPermission(String url, String fileName) {
        try {
            strUrl = url;
            strFileName = fileName;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    download(url, fileName);
                } else {
                    //            Log.v(TAG,"Permission is revoked");
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        try {
            if (requestCode == 1) {
                download(strUrl, strFileName);
            }
            switch (requestCode) {
                case 0:
                    boolean permissionsGranted = true;
                    if (grantResults.length > 0 && permissions.length == grantResults.length) {
                        for (int i = 0; i < permissions.length; i++) {
                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                permissionsGranted = false;
                            }
                        }

                    } else {
                        permissionsGranted = false;
                    }
                    if (permissionsGranted) {
//                    createFile();

                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
