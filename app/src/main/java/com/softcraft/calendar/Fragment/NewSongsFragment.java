package com.softcraft.calendar.Fragment;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.support.v4.media.MediaBrowserCompat;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.softcraft.calendar.Activity.DevotionalSongsActivity;
import com.softcraft.calendar.Activity.MediaPlayerActivity;
import com.softcraft.calendar.ServiceAndOthers.BackgroundAudioService;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NewSongsFragment extends Fragment {
    View rootView;
    private RecyclerView listView;
    Context context;
    private SongsFragmentAdapter listAdapter;
    private ArrayList<HashMap<String, String>> data;
    private HashMap<String, String> resultp;
    public ArrayList<HashMap<String, String>> songArraylist;
    static int exceptionValue;
    static String strRet = "";
    public static String PACKAGE_NAME, version, deviceId, deviceModel;
    private static String postApi = "http://adsenseusers.com/scsadcontrol/api/device4/updatesonglisten/format/json";
    private MediaBrowserCompat mMediaBrowserCompat;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_songs_layout, container, false);
        progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);
        progressBar.setVisibility(View.VISIBLE);
        listView = (RecyclerView) rootView.findViewById(R.id.fragmentListView);


        try {
            PACKAGE_NAME = getContext().getPackageName();
            version = Build.VERSION.RELEASE;
            deviceId = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            deviceModel = Build.MODEL;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        listViewOnClick();

        return rootView;
    }

    private void listViewOnClick() {
        try {
            songArraylist = new ArrayList<HashMap<String, String>>();
            resultp = new HashMap<String, String>();

            songArraylist = ((DevotionalSongsActivity) getActivity()).getSongArrayList();
            try {
                Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.list_item_anim);
                listView.setLayoutAnimation(new LayoutAnimationController(animation));
            } catch (Exception e) {
                e.printStackTrace();
            }
            listAdapter = new SongsFragmentAdapter(getContext(), songArraylist);
            listView.setLayoutManager(new LinearLayoutManager(listView.getContext()));
            listView.setAdapter(listAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkInternetConnection(Bundle myInfo, String songId) {
        try {

            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    gotoPlayerPage(myInfo);
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    gotoPlayerPage(myInfo);
                }
            } else {
                if (!MiddlewareInterface.isMp3FileAvaliable(MiddlewareInterface.getMp3FilePath(songId + ".mp3"))) {
                    Toast toast = Toast.makeText(getContext(), "Song Not Avaliable for offline", Toast.LENGTH_LONG);
                    toast.show();
                } else {
                    gotoPlayerPage(myInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void gotoPlayerPage(Bundle bundle) {
        try {
            Intent intent = new Intent(getContext(), MediaPlayerActivity.class);
            intent.putExtra("NewSongsArr", songArraylist);
            startActivityForResult(intent.putExtras(bundle), 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void post(String endpoint, Map<String, String> params) throws IOException {
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=').append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v("ServerUtillis", "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
            Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                strRet = response.toString();
            }
            // JSONObject jsonObject =new JSONObject(response.toString());
            exceptionValue = status;
            Log.d("statusss", status + "");
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public class SongsFragmentAdapter extends RecyclerView.Adapter<SongsFragmentAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        Context context;
        LayoutInflater inflater;
        ArrayList<HashMap<String, String>> data;
        HashMap<String, String> resultp = new HashMap<>();
        Context mContext;
        View viewSub;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView SongTitle, SongSize, SongId, SingerName, SongDuration, CategoryId;
            private ImageView thumbImg, offlinePlayImg;
            CardView cardViewItem;

            public ViewHolder(View view) {
                super(view);
                SongTitle = (TextView) itemView.findViewById(R.id.title_txt);
                SongSize = (TextView) itemView.findViewById(R.id.Songsize);
//         SongId = (TextView) itemView.findViewById(R.id.SongId);
                SingerName = (TextView) itemView.findViewById(R.id.artistName);
                SongDuration = (TextView) itemView.findViewById(R.id.Songduration);
                CategoryId = (TextView) itemView.findViewById(R.id.CategoryId);
                thumbImg = (ImageView) itemView.findViewById(R.id.song_img);
                cardViewItem = (CardView) itemView.findViewById(R.id.songsItemCardView);
                offlinePlayImg = itemView.findViewById(R.id.offlinePlayIcon);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + SongTitle.getText();
            }
        }

        public SongsFragmentAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                data = arraylist;
                mContext = context;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public SongsFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_songs_adapter, parent, false);
            viewSub = view;
            view.setBackgroundResource(mBackground);
            return new SongsFragmentAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SongsFragmentAdapter.ViewHolder holder, final int itemPosition) {
            try {
                resultp = data.get(itemPosition);

                holder.SongSize.setText(resultp.get(DevotionalSongsActivity.SongSize));
//        holder.SongId.setText(resultp.get(DevotionalSongsActivity.SongID));
                holder.CategoryId.setText(resultp.get(DevotionalSongsActivity.CategoryId));
                holder.SongDuration.setText(resultp.get(DevotionalSongsActivity.SongDuration));

                try {
                    if (MiddlewareInterface.bRendering) {
                        holder.SingerName.setText(resultp.get(DevotionalSongsActivity.SingerName));
                        holder.SongTitle.setText(resultp.get(DevotionalSongsActivity.SongTitle));
                        holder.SingerName.setTypeface(Typeface.DEFAULT);
                        holder.SongTitle.setTypeface(Typeface.DEFAULT);
                    } else {
                        holder.SingerName.setText(UnicodeUtil.unicode2tsc(resultp.get(DevotionalSongsActivity.SingerName)));
                        holder.SongTitle.setText(UnicodeUtil.unicode2tsc(resultp.get(DevotionalSongsActivity.SongTitle)));
                        holder.SingerName.setTypeface(MiddlewareInterface.tf_mylai);
                        holder.SongTitle.setTypeface(MiddlewareInterface.tf_mylai);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Glide.with(mContext)
                            .load(resultp.get(DevotionalSongsActivity.SongImgUrl))
                            .into(holder.thumbImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                holder.cardViewItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {

                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(getActivity(), v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        if (BackgroundAudioService.mMediaPlayer != null) {
                                            BackgroundAudioService.mMediaPlayer.reset();
                                            BackgroundAudioService.mMediaPlayer.release();
                                        }
                                    } catch (Exception e) {

                                        e.printStackTrace();
                                    }
                                    BackgroundAudioService.currentPos = 0;
                                    resultp = songArraylist.get(itemPosition);
                                    String keySongName = resultp.get(DevotionalSongsActivity.SongTitle);
                                    String keySongUrl = resultp.get(DevotionalSongsActivity.Songurl);
                                    String keySingerName = resultp.get(DevotionalSongsActivity.SingerName);
                                    String keyImgUrl = resultp.get(DevotionalSongsActivity.SongImgUrl);
                                    String songId = resultp.get(DevotionalSongsActivity.SongID);
//                                    Map<String, String> updateListenSongs = new HashMap<String, String>();
//                                    updateListenSongs.put("songid", songId);
//                                    updateListenSongs.put("devicetype", deviceModel);
//                                    updateListenSongs.put("deviceid", deviceId);
//                                    updateListenSongs.put("deviceos", "Android");
//                                    updateListenSongs.put("osversion", version);
//                                    try {
//                                        post(postApi, updateListenSongs);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }

                                    try {
                                        if (keySongUrl.equals("")) {
                                            Toast toast = Toast.makeText(getContext(), "Song Not Avaliable", Toast.LENGTH_LONG);
                                            toast.show();
                                        } else {
                                            Bundle myInfo = new Bundle();
                                            myInfo.putInt("Position", itemPosition);
                                            myInfo.putInt("arrayflag", 1);
                                            checkInternetConnection(myInfo, songId);
                                            progressBar.setVisibility(View.GONE);
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

                String songId = resultp.get(DevotionalSongsActivity.SongID);
                ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                if (activeNetwork == null && MiddlewareInterface.isMp3FileAvaliable(MiddlewareInterface.getMp3FilePath(songId + ".mp3"))) {
                    holder.offlinePlayImg.setVisibility(View.VISIBLE);
                } else {
                    holder.offlinePlayImg.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
