package com.softcraft.calendar.StoriesAndArticals;

//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import com.nbsp.materialfilepicker.MaterialFilePicker;
//import com.softcraft.calendar.R;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.regex.Pattern;
//
//
//public class StoryPage extends AppCompatActivity {
//    private ViewPager viewPager;
//    private ArrayList<Bitmap> itemData;
//    private VigerAdapter adapter;
//    private Button btnFromFile, btnFromNetwork, btnCancle;
//    private VigerPDF vigerPDF;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        try {
//            setContentView(R.layout.activity_pdf);
//            checkPermission();
//            viewPager = (ViewPager) findViewById(R.id.viewPager);
//            btnFromFile = (Button) findViewById(R.id.btnFile);
//            btnFromNetwork = (Button) findViewById(R.id.btnNetwork);
//            btnCancle = (Button) findViewById(R.id.btnCancle);
//            vigerPDF = new VigerPDF(this);
//
//            Bundle b;
//            b = getIntent().getExtras();
//
//            final String strURL = (String) b.get("strURL");
//
//
//            btnCancle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    vigerPDF.cancle();
//                }
//            });
//            btnFromFile.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    new MaterialFilePicker()
//                            .withActivity(StoryPage.this)
//                            .withRequestCode(100)
//                            .withFilter(Pattern.compile(".*\\.pdf$"))
//                            .start();
//                }
//            });
//
//            btnFromNetwork.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        itemData.clear();
//                        adapter.notifyDataSetChanged();
//                        fromNetwork(strURL);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
////                itemData.clear();
////                adapter.notifyDataSetChanged();
////                fromNetwork("http://www.pdf995.com/samples/pdf.pdf");
//                }
//            });
//
//
//            itemData = new ArrayList<>();
//            adapter = new VigerAdapter(getApplicationContext(), itemData);
//            viewPager.setAdapter(adapter);
//
////            if (itemData != null)
////                itemData.clear();
////            adapter.notifyDataSetChanged();
////            fromNetwork(strURL);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //viewPager.setPageTransformer(true, new StackTransformer());
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//            itemData.clear();
//            adapter.notifyDataSetChanged();
////            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
//            String filePath = data.getStringExtra("result_file_path");
//            fromFile(filePath);
//        }
//    }
//
//    private void fromNetwork(String endpoint) {
//        itemData.clear();
//        adapter.notifyDataSetChanged();
//        vigerPDF.cancle();
//        vigerPDF.initFromNetwork(endpoint, new OnResultListener() {
//            @Override
//            public void resultData(Bitmap data) {
//                try {
//                    Log.e("data", "run");
//                    itemData.add(data);
//                    adapter.notifyDataSetChanged();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void progressData(int progress) {
//                try {
//                    Log.e("data", "" + progress);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void failed(Throwable t) {
//                try {
//                    String strFailed = t.getMessage();
//                    Log.e("data", "" + strFailed);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }
//
//
//    private void checkPermission() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//
//            } else {
//                //            Log.v(TAG,"Permission is revoked");
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            }
//        }
//    }
//
//    private void fromFile(String path) {
//        itemData.clear();
//        adapter.notifyDataSetChanged();
//        File file = new File(path);
//        vigerPDF.cancle();
//        vigerPDF.initFromFile(file, new OnResultListener() {
//            @Override
//            public void resultData(Bitmap data) {
//                itemData.add(data);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void progressData(int progress) {
//                Log.e("data", "" + progress);
//
//            }
//
//            @Override
//            public void failed(Throwable t) {
//
//            }
//
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (vigerPDF != null) vigerPDF.cancle();
//    }
//}
//
//

//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.os.Build;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//
//import com.nbsp.materialfilepicker.MaterialFilePicker;
//import com.nbsp.materialfilepicker.ui.FilePickerActivity;
//import com.necistudio.vigerpdf.adapter.VigerAdapter;
//import com.necistudio.vigerpdf.manage.OnResultListener;
//import com.necistudio.vigerpdf.VigerPDF;
//import com.softcraft.calendar.R;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.regex.Pattern;
//
//public class StoryPage extends AppCompatActivity {
//
//    private ViewPager viewPager;
//    private ArrayList<Bitmap> itemData;
//    private VigerAdapter adapter;
//    private Button btnFromFile, btnFromNetwork, btnCancle;
//    private VigerPDF vigerPDF;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pdf);
//        checkPermission();
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        btnFromFile = (Button) findViewById(R.id.btnFile);
//        btnFromNetwork = (Button) findViewById(R.id.btnNetwork);
//        btnCancle = (Button) findViewById(R.id.btnCancle);
//        vigerPDF = new VigerPDF(this);
//
//
//        Bundle b;
//        b = getIntent().getExtras();
//        final String strURL = (String) b.get("strURL");
//
//        btnCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                vigerPDF.cancle();
//            }
//        });
//        btnFromFile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new MaterialFilePicker()
//                        .withActivity(StoryPage.this)
//                        .withRequestCode(100)
//                        .withFilter(Pattern.compile(".*\\.pdf$"))
//                        .start();
//            }
//        });
//
//        btnFromNetwork.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemData.clear();
//                adapter.notifyDataSetChanged();
////                fromNetwork("http://www.pdf995.com/samples/pdf.pdf");
//                fromNetwork(strURL);
//            }
//        });
//        itemData = new ArrayList<>();
//        adapter = new VigerAdapter(getApplicationContext(), itemData);
//        viewPager.setAdapter(adapter);
//        //viewPager.setPageTransformer(true, new StackTransformer());
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//            itemData.clear();
//            adapter.notifyDataSetChanged();
//            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
//            fromFile(filePath);
//        }
//    }
//
//    private void fromNetwork(String endpoint) {
//        itemData.clear();
//        adapter.notifyDataSetChanged();
//        vigerPDF.cancle();
//        vigerPDF.initFromNetwork(endpoint, new OnResultListener() {
//            @Override
//            public void resultData(Bitmap data) {
//                Log.e("data", "run");
//                itemData.add(data);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void progressData(int progress) {
//                Log.e("data", "" + progress);
//            }
//
//            @Override
//            public void failed(Throwable t) {
//                String strFailed = t.getMessage();
//                Log.e("data", "" + strFailed);
//            }
//
//        });
//    }
//
//
//    private void checkPermission() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//
//            } else {
//                //            Log.v(TAG,"Permission is revoked");
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            }
//        }
//    }
//
//    private void fromFile(String path) {
//        itemData.clear();
//        adapter.notifyDataSetChanged();
//        File file = new File(path);
//        vigerPDF.cancle();
//        vigerPDF.initFromFile(file, new OnResultListener() {
//            @Override
//            public void resultData(Bitmap data) {
//                itemData.add(data);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void progressData(int progress) {
//                Log.e("data", "" + progress);
//
//            }
//
//            @Override
//            public void failed(Throwable t) {
//
//            }
//
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (vigerPDF != null) vigerPDF.cancle();
//    }
//}

