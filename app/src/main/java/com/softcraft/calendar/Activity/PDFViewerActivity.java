package com.softcraft.calendar.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.shockwave.pdfium.PdfDocument;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;

import java.io.File;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.isNetworkStatusAvialable;

public class PDFViewerActivity extends AppCompatActivity implements OnPageChangeListener, OnLoadCompleteListener, OnPageErrorListener {

    private PDFView pdfView;
    private Integer pageNumber = 1;
    private String pdfFileName;

    private final static int REQUEST_CODE = 42;
    public static final int PERMISSION_CODE = 42042;
    private AdView viewBannerAd;
    private NativeExpressAdView viewNativeAd;
    private LinearLayout linearad;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_viewer);
        try {

            pdfView = findViewById(R.id.pdfView);
            TextView pageTitle = findViewById(R.id.show_title);
            ImageView backBtn = findViewById(R.id.click_back_image);
            linearad =  findViewById(R.id.adview);
            progressBar =  findViewById(R.id.progress_bar);

            progressBar.setVisibility(View.VISIBLE);

            Uri fileUri = getIntent().getData();
            pdfFileName = getIntent().getStringExtra("SelectedBookTitle");

            pageTitle.setText(pdfFileName);

            if (fileUri != null)
                displayFromUri(fileUri);

            backBtn.setOnClickListener(v -> onBackPressed());
            pageTitle.setOnClickListener(v -> onBackPressed());

            setAd();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayFromUri(Uri uri) {
        try {

            pdfView.fromFile(new File(uri.getPath()))
                    .onPageChange(this)
                    .swipeHorizontal(false)
                    .enableAnnotationRendering(true)
                    .onLoad(this)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .spacing(10) // in dp
                    .onPageError(this)
                    .pageFitPolicy(FitPolicy.BOTH)
                    .load();

//            pdfView.fromUri(uri)
//                    .defaultPage(pageNumber)
//                    .onPageChange(this)
//                    .enableAnnotationRendering(true)
//                    .onLoad(this)
//                    .scrollHandle(new DefaultScrollHandle(this))
//                    .spacing(10) // in dp
//                    .onPageError(this)
//                    .load();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadComplete(int nbPages) {
        try {
            progressBar.setVisibility(View.GONE);
//            PdfDocument.Meta meta = pdfView.getDocumentMeta();
//            printBookmarksTree(pdfView.getTableOfContents(), "-");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        try {
            pageNumber = page;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageError(int page, Throwable t) {
        try {
            progressBar.setVisibility(View.GONE);
            onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAd() {
        try {
            if (isNetworkStatusAvialable(getApplicationContext())) {
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
                viewNativeAd = new NativeExpressAdView(getApplicationContext());
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
                    linearad.setVisibility(View.VISIBLE);
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
                String strId = getBannerAd;
                if (MiddlewareInterface.bAdFree)
                    return;
                viewBannerAd = new AdView(this);
                viewBannerAd.setAdSize(AdSize.BANNER);
                viewBannerAd.setAdUnitId(strId);
                viewBannerAd.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                linearad.addView(viewBannerAd);
                viewBannerAd.loadAd(new AdRequest.Builder().build());
                //linear_ad
            } catch (Exception e) {
                // TODO: handle exception
            }
            viewBannerAd.setAdListener(new AdListener() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

}

