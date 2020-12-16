package com.softcraft.calendar.StoriesAndArticals;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.softcraft.calendar.ServiceAndOthers.ExceptionHandler;
import com.softcraft.calendar.ServiceAndOthers.GlideConfiguration;
import com.softcraft.calendar.ServiceAndOthers.HttpHandler;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.TakeScreenShot;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;

public class StoriesAndArticlesActivity extends AppCompatActivity {
    GridView gridView;
    static Context applicationContext;
    File pdfUrlFile;
    ProgressBar progressBar;
    JSONObject jsonObjectResponse;
    ArrayList<ArrayList<String>> categoryList = new ArrayList<>();
    ArrayList<ArrayList<String>> bookTypeList = new ArrayList<>();
    ArrayList<ArrayList<String>> authorList = new ArrayList<>();
    ArrayList<ArrayList<String>> bookDataList = new ArrayList<>();
    LinearLayout linearad;
    AdView adview;
    private NativeExpressAdView viewNativeAd;
    RelativeLayout rootLayout;
    String urlLink = "http://adsenseusers.com/scsadcontrol/api/device4/booksdata/format/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this, SplashScreen.class));
        setContentView(R.layout.stories_and_articles_activity);
        try {
            try {
                MiddlewareInterface.tf_mylai = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/mylai.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            final ImageView backBtn = (ImageView) findViewById(R.id.back_arrow);
            final TextView titleHead = (TextView) findViewById(R.id.title_header);
            progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
            linearad = (LinearLayout) findViewById(R.id.notication_adview);
            rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(StoriesAndArticlesActivity.this, backBtn);
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
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(StoriesAndArticlesActivity.this, titleHead);
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
            gridView = (GridView) findViewById(R.id.gridViewStories);
            linearad = (LinearLayout) findViewById(R.id.notication_adview);

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

            new GetJSONData().execute();
            ShareFunc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class GetJSONData extends AsyncTask<Void, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                progressBar.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlLink);
//            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray categoryDetails = jsonObj.getJSONArray("categorydata");
                    // looping through All songDetails
                    for (int i = 0; i < categoryDetails.length(); i++) {
                        JSONObject jsonObject = categoryDetails.getJSONObject(i);
                        try {
                            ArrayList<String> categoryDetail = new ArrayList<>();
                            if(jsonObject.has("id")) {
                                String id = jsonObject.getString("id");
                                categoryDetail.add(id);
                            }if(jsonObject.has("name")) {
                                String categoryName = jsonObject.getString("name");
                                categoryDetail.add(categoryName);
                            }if(jsonObject.has("thumbimage")) {
                                String thumbImgUrl = jsonObject.getString("thumbimage");
                                categoryDetail.add(thumbImgUrl);
                            }
                            categoryList.add(categoryDetail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    JSONArray bookTypeArray = jsonObj.getJSONArray("booktypedata");
                    for (int i = 0; i < bookTypeArray.length(); i++) {
                        JSONObject jsonObject = bookTypeArray.getJSONObject(i);
                        try {

                            ArrayList<String> bookTypeDetail = new ArrayList<>();

                            if(jsonObject.has("id")) {
                                String id = jsonObject.getString("id");
                                bookTypeDetail.add(id);
                            }if(jsonObject.has("name")) {
                                String categoryName = jsonObject.getString("name");
                                bookTypeDetail.add(categoryName);
                            }if(jsonObject.has("thumbimage")) {
                                String thumbImgUrl = jsonObject.getString("thumbimage");
                                bookTypeDetail.add(thumbImgUrl);
                            }
                            bookTypeList.add(bookTypeDetail);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    JSONArray authorDataArray = jsonObj.getJSONArray("authordata");
                    for (int i = 0; i < authorDataArray.length(); i++) {
                        JSONObject jsonObject = authorDataArray.getJSONObject(i);
                        try {
                            ArrayList<String> authorDetail = new ArrayList<>();

                            if(jsonObject.has("id")) {
                                String id = jsonObject.getString("id");
                                authorDetail.add(id);
                            }if(jsonObject.has("name")) {
                                String categoryName = jsonObject.getString("name");
                                authorDetail.add(categoryName);
                            }if(jsonObject.has("thumbimage")) {
                                String thumbImgUrl = jsonObject.getString("thumbimage");
                                authorDetail.add(thumbImgUrl);
                            }
                            authorList.add(authorDetail);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    JSONArray bookDataArray = jsonObj.getJSONArray("booksdata");
                    for (int i = 0; i < bookDataArray.length(); i++) {
                        JSONObject jsonObject = bookDataArray.getJSONObject(i);
                        try {
                            String id = jsonObject.getString("id");
                            String authorName = jsonObject.getString("name");
                            String version = jsonObject.getString("version");
                            String title = jsonObject.getString("title");
                            String categoryid = jsonObject.getString("categoryid");
                            String authorid = jsonObject.getString("authorid");
                            String typeid = jsonObject.getString("typeid");
                            String content = jsonObject.getString("content");
                            String pubdate = jsonObject.getString("pubdate");
                            String coverimage = jsonObject.getString("coverimage");
                            String bookimage = jsonObject.getString("bookimage");
                            String bookfile = jsonObject.getString("bookfile");
                            String bookfilesize = jsonObject.getString("bookfilesize");
//                            String viewcount = jsonObject.getString("viewcount");

                            ArrayList<String> bookDataDetail = new ArrayList<>();
                            bookDataDetail.add(id);
                            bookDataDetail.add(authorName);
                            bookDataDetail.add(version);
                            bookDataDetail.add(title);
                            bookDataDetail.add(categoryid);
                            bookDataDetail.add(authorid);
                            bookDataDetail.add(typeid);
                            bookDataDetail.add(content);
                            bookDataDetail.add(pubdate);
                            bookDataDetail.add(coverimage);
                            bookDataDetail.add(bookimage);
                            bookDataDetail.add(bookfile);
                            bookDataDetail.add(bookfilesize);
//                            bookDataDetail.add(viewcount);
                            bookDataList.add(bookDataDetail);
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
                if (categoryList.size() != 0) {
                    progressBar.setVisibility(View.GONE);
                    gridView.setAdapter(new CustomAdapter(getApplicationContext()));
//                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
//                    recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), categoryList));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private ArrayList<ArrayList<String>> CategoryArrList;
        View viewSub;
        Context mContext;
        Boolean isAvaliable;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView bookName, authorName;
            private ImageView bookImage;
            CardView cardView;

            public ViewHolder(View view) {
                super(view);
                bookImage = (ImageView) view.findViewById(R.id.book_img);
                bookName = (TextView) view.findViewById(R.id.book_name);
                cardView = (CardView) view.findViewById(R.id.cardView);
                authorName = (TextView) view.findViewById(R.id.author_name);
            }

            @Override
            public String toString()  {
                return super.toString() + " '" + bookName.getText();
            }
        }

        public RecyclerViewAdapter(Context context, ArrayList<ArrayList<String>> categoryList) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                CategoryArrList = categoryList;
                mContext = context;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_category_listiview_layout, parent, false);
            viewSub = view;
            view.setBackgroundResource(mBackground);
            return new RecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int itemPosition) {
            try {
                ArrayList<String> specificList = categoryList.get(itemPosition);
                holder.bookName.setText(specificList.get(1));

                Glide.with(StoriesAndArticlesActivity.this)
                        .load(specificList.get(2))
                        .into(holder.bookImage);


                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(getApplicationContext(), holder.cardView);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }
                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        ArrayList<String> specCategList = categoryList.get(itemPosition);
                                        String categoryID = specCategList.get(0);
                                        isAvaliable = false;
                                        for(int i=0;i<bookDataList.size();i++){
                                            ArrayList<String> bookdata = bookDataList.get(i);
                                            if(categoryID.equalsIgnoreCase(bookdata.get(4))){
                                                isAvaliable = true;
                                            }
                                        }
                                        if(isAvaliable) {
                                            String pageTitle = specCategList.get(1);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("bookTypeList", bookTypeList);
                                            bundle.putSerializable("authorList", authorList);
                                            bundle.putSerializable("bookDataList", bookDataList);
                                            bundle.putSerializable("categoryList", categoryList);
                                            bundle.putString("pageTitle", pageTitle);
                                            Intent intent = new Intent(StoriesAndArticlesActivity.this, StoriesAuthorBooksActivity.class);
                                            intent.putExtras(bundle);
                                            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                                            mContext.startActivity(intent);
                                            ((StoriesAndArticlesActivity) mContext).finish();
                                        }else{
                                            Toast.makeText(mContext, "Will be soon", Toast.LENGTH_SHORT).show();
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

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }
    }

    private void animate(final View view, final int position) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        view.startAnimation(animation);
    }


    public class CustomAdapter extends BaseAdapter{
        String [] result;
        Context mContext;
        int [] imageId;
        Boolean isAvaliable;
        private LayoutInflater inflater=null;
        public CustomAdapter(Context context) {
            // TODO Auto-generated constructor stub
            mContext=context;
            inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return categoryList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public class Holder
        {
            TextView bookName, authorName;
            ImageView bookImage;
            RelativeLayout cardView;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            Holder holder=new Holder();
            View rowView;
            rowView = inflater.inflate(R.layout.recycler_category_listiview_layout, null);
            try {
                holder.bookImage = (ImageView) rowView.findViewById(R.id.book_img);
                holder.bookName = (TextView) rowView.findViewById(R.id.book_name);
                holder.cardView = (RelativeLayout) rowView.findViewById(R.id.cardView);
                holder.authorName = (TextView) rowView.findViewById(R.id.author_name);

                ArrayList<String> specificList = categoryList.get(position);
                try {
                    if (MiddlewareInterface.bRendering) {
                        holder.bookName.setText(specificList.get(1));
                        holder.bookName.setTypeface(Typeface.DEFAULT);
                    } else {
                        holder.bookName.setText(UnicodeUtil.unicode2tsc(specificList.get(1)));
                        holder.bookName.setTypeface(MiddlewareInterface.tf_mylai);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Glide.with(getApplicationContext())
                        .load(specificList.get(2))
                        .into(holder.bookImage);

//                holder.cardView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//
//                    }
//                });
//                try {
//                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.grid_item_anim);
//                    GridLayoutAnimationController controller = new GridLayoutAnimationController(animation, .2f, .2f);
//                    gridView.setLayoutAnimation(controller);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view,final int position, long l) {
                        try {

                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(StoriesAndArticlesActivity.this, view);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }
                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        ArrayList<String> specCategList = categoryList.get(position);
                                        String categoryID = specCategList.get(0);
                                        isAvaliable = false;
                                        for(int i=0;i<bookDataList.size();i++){
                                            ArrayList<String> bookdata = bookDataList.get(i);
                                            if(categoryID.equalsIgnoreCase(bookdata.get(4))){
                                                isAvaliable = true;
                                            }
                                        }
                                        if(isAvaliable) {
                                            String pageTitle = specCategList.get(1);
                                            Bundle bundle = new Bundle();
                                            bundle.putSerializable("bookTypeList", bookTypeList);
                                            bundle.putSerializable("authorList", authorList);
                                            bundle.putSerializable("bookDataList", bookDataList);
                                            bundle.putSerializable("categoryList", categoryList);
                                            bundle.putString("pageTitle", pageTitle);
                                            bundle.putString("categoryID", categoryID);
                                            intentAction(bundle);

                                        }else{
                                            Toast.makeText(mContext, "Will be soon", Toast.LENGTH_SHORT).show();
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
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return rowView;
        }

    }

    private void intentAction(Bundle bundle){
        try{
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    Intent intent = new Intent(StoriesAndArticlesActivity.this, StoriesAuthorBooksActivity.class);
                    intent.putExtras(bundle);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//                    ((StoriesAndArticlesActivity) mContext).finish();
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    Intent intent = new Intent(StoriesAndArticlesActivity.this, StoriesAuthorBooksActivity.class);
                    intent.putExtras(bundle);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
//                    ((StoriesAndArticlesActivity) mContext).finish();
                }
            } else {
                Toast toast = Toast.makeText(StoriesAndArticlesActivity.this, "Need Internet Connection", Toast.LENGTH_LONG);
                toast.show();
            }
        }catch (Exception e){
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
                                    TakeScreenShot(StoriesAndArticlesActivity.this,rootLayout,0,21,"0","கதைகள் மற்றும் கட்டுரைகள்",progressBar);
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
