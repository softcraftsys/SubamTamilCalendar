package com.softcraft.calendar.DevotionalWallpapers;

import android.animation.Animator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class DevotionalWallpepersPreviewActivity extends AppCompatActivity {
    RelativeLayout hideShowWallpaperMenuLayout, rDownloadAndsetWallpaperLayout;
    LinearLayout mWallpaperTopBarLayout;
    Boolean isVisible = true;
    ArrayList<ArrayList<String>> arrAllWallpapersDataList;
    ProgressBar progressBar, percentageProgress;
    int iDownloadStatus = 0;
    TextView percentageTv;
    Boolean setWallpaper = false;
    ViewPager viewPager;
    public static final int REQUEST_WRITE_PERMISSION = 786;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devotional_wallpepers_preview);
        try {
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            initItems();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initItems() {
        try {
            viewPager = (ViewPager) findViewById(R.id.WallPrevPager);
            int iImagePagerPos = this.getIntent().getExtras().getInt("ItemPosition");
            String strImageName = this.getIntent().getExtras().getString("ItemName");
            ImageView ivBack = (ImageView) findViewById(R.id.back_arrow);
            TextView tvHeader = (TextView) findViewById(R.id.title_header);
            percentageTv = (TextView) findViewById(R.id.percentageTv);
            progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
            percentageProgress = (ProgressBar) findViewById(R.id.progressBar_percentage);

            hideShowWallpaperMenuLayout = (RelativeLayout) findViewById(R.id.wallMenuLayout);
            RelativeLayout rDownloadImageLayout = (RelativeLayout) findViewById(R.id.downloadImageLayout);
            RelativeLayout rSetWallpaperLayout = (RelativeLayout) findViewById(R.id.setWallImageLayout);
            rDownloadAndsetWallpaperLayout = (RelativeLayout) findViewById(R.id.downLoadAndSetWallLayout);
            mWallpaperTopBarLayout = (LinearLayout) findViewById(R.id.wallpaperTopBarLayout);

            rDownloadImageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DevotionalWallpepersPreviewActivity.this, v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        setWallpaper = false;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                                        } else {
                                            downLoadWallpapersFunc(viewPager.getCurrentItem(), 0);
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
            });
            rSetWallpaperLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DevotionalWallpepersPreviewActivity.this, v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        setWallpaper = true;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                                        } else {
                                            downLoadWallpapersFunc(viewPager.getCurrentItem(), 1);
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
            });

            arrAllWallpapersDataList = (ArrayList<ArrayList<String>>) getIntent().getSerializableExtra("arrWallpapersList");

            ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getApplicationContext());
            viewPager.setAdapter(pagerAdapter);
            viewPager.setCurrentItem(iImagePagerPos);

            ivBack.setOnClickListener(clickListener);
            tvHeader.setOnClickListener(clickListener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.back_arrow || view.getId() == R.id.title_header) {
                try {
                    if (MiddlewareInterface.isNetworkStatusAvialable(getApplicationContext())) {
                        setAnimationFunc(view);
                    } else {
                        Toast.makeText(DevotionalWallpepersPreviewActivity.this, "Need internet connection", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (setWallpaper) {
                downLoadWallpapersFunc(viewPager.getCurrentItem(), 1);
            } else {
                downLoadWallpapersFunc(viewPager.getCurrentItem(), 0);
            }
        }
    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void downLoadWallpapersFunc(int pagerPos, final int setWallpaperFlag) {
        try {
//            DevotionalWallpapers devotionalWallpapers = new DevotionalWallpapers();
//            List<Wallpaperdatum> listWallpaperData = devotionalWallpapers.getWallpaperdata();
//            Wallpaperdatum wallpaperdatum = listWallpaperData.get(pagerPos);
//            String strImgUrl = wallpaperdatum.getImage();
//            String strImgName = wallpaperdatum.getSubcat();

            String strImgUrl = arrAllWallpapersDataList.get(pagerPos).get(4);
            String strImgName = arrAllWallpapersDataList.get(pagerPos).get(3);

            AndroidNetworking.initialize(getApplicationContext());
            //Folder Creating Into Phone Storage
            final String dirPath = Environment.getExternalStorageDirectory() + "/SubamTamilCalendar/Images";
            final String fileName = strImgName;
            //file Creating With Folder & Fle Name

            final File file = new File(dirPath, fileName);
            final String url = strImgUrl;

            if (file.exists() && setWallpaperFlag == 0) {
                Toast.makeText(DevotionalWallpepersPreviewActivity.this, "File Already Exists", Toast.LENGTH_SHORT).show();
            } else {
                AndroidNetworking.download(url, dirPath, fileName)
                        .build()
                        .startDownload(new com.androidnetworking.interfaces.DownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                if (setWallpaperFlag == 1) {

                                    WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());

                                    try {
                                        InputStream fileInputStream = new FileInputStream(file);
                                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                                        bitmapOptions.inJustDecodeBounds = false;
                                        Bitmap wallpaperBitmap = BitmapFactory.decodeStream(fileInputStream, null, bitmapOptions);
                                        Bitmap scaledBitMap = getScaledBitMapBaseOnScreenSize(wallpaperBitmap);
                                        myWallpaperManager.setBitmap(scaledBitMap);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(DevotionalWallpepersPreviewActivity.this, "Wallpaper changed Successfully", Toast.LENGTH_SHORT).show();

                                    } catch (IOException e) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(DevotionalWallpepersPreviewActivity.this, "Wallpaper changed Unsuccessfull", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                } else {
                                    percentageProgress.setProgress(100);
                                    percentageTv.setText("100%");
                                    percentageTv.setVisibility(View.GONE);
                                    percentageProgress.setVisibility(View.GONE);
                                    Toast.makeText(DevotionalWallpepersPreviewActivity.this, "Download Complete", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                percentageProgress.setVisibility(View.GONE);
                                percentageTv.setVisibility(View.GONE);
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(DevotionalWallpepersPreviewActivity.this, "DownLoad Complete", Toast.LENGTH_SHORT).show();
                            }
                        });

                if (setWallpaperFlag == 0) {
                    percentageProgress.setProgress(100);
                    percentageProgress.setProgress(100);
                    percentageTv.setText("100%");
                    percentageProgress.setVisibility(View.VISIBLE);
                    percentageTv.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap getScaledBitMapBaseOnScreenSize(Bitmap bitmapOriginal) {

        Bitmap scaledBitmap = null;
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);


            int width = bitmapOriginal.getWidth();
            int height = bitmapOriginal.getHeight();

            float scaleWidth = metrics.scaledDensity;
            float scaleHeight = metrics.scaledDensity;

            // create a matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);

            // recreate the new Bitmap
            scaledBitmap = Bitmap.createBitmap(bitmapOriginal, 0, 0, width, height, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scaledBitmap;
    }


    private class ViewPagerAdapter extends PagerAdapter {
        Context context;
        private LayoutInflater layoutInflater;

        public ViewPagerAdapter(Context ctx) {
            this.context = ctx;
        }

        @Override
        public int getCount() {
            return arrAllWallpapersDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = layoutInflater.inflate(R.layout.devotional_wallpaper_preview_adpater_layout, container, false);
            try {

                String strImgUrl = arrAllWallpapersDataList.get(position).get(4);

                ImageView ivAlbumImage = (ImageView) itemView.findViewById(R.id.albumImgview);
                Glide.with(context).load(strImgUrl).into(ivAlbumImage);

                ivAlbumImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (!isVisible) {
                                showMenu(v);
                            } else {
                                hideMenu(v);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
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

    public void showMenu(View view) {
        try {
            isVisible = true;
            Animation anim = new TranslateAnimation(0, 0, -mWallpaperTopBarLayout.getHeight(), 0);
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    mWallpaperTopBarLayout.setVisibility(View.VISIBLE);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                }
            });
            mWallpaperTopBarLayout.startAnimation(anim);

            anim = new TranslateAnimation(0, 0, rDownloadAndsetWallpaperLayout.getHeight(), 0);
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    rDownloadAndsetWallpaperLayout.setVisibility(View.VISIBLE);
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                }
            });
            rDownloadAndsetWallpaperLayout.startAnimation(anim);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideMenu(View view) {
        try {
            isVisible = false;
            Animation anim = new TranslateAnimation(0, 0, 0, -mWallpaperTopBarLayout.getHeight());
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    mWallpaperTopBarLayout.setVisibility(View.INVISIBLE);
                }
            });
            mWallpaperTopBarLayout.startAnimation(anim);

            anim = new TranslateAnimation(0, 0, 0, rDownloadAndsetWallpaperLayout.getHeight());
            anim.setDuration(200);
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    rDownloadAndsetWallpaperLayout.setVisibility(View.INVISIBLE);
                }
            });
            rDownloadAndsetWallpaperLayout.startAnimation(anim);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAnimationFunc(View view) {
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(DevotionalWallpepersPreviewActivity.this, view);
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

}
