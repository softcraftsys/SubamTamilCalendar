package com.softcraft.calendar.Adapter;


import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softcraft.calendar.Activity.DevotionalSongsActivity;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

public class SingersCategoryActivityAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();
    public SingersCategoryActivityAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.singers_category_activity_adapter, parent, false);
        resultp = data.get(position);
        TextView SongTitle = (TextView) itemView.findViewById(R.id.title_txt);
        TextView SongSize = (TextView) itemView.findViewById(R.id.Songsize);
        TextView SongDuration = (TextView) itemView.findViewById(R.id.Songduration);
//        TextView SongId = (TextView) itemView.findViewById(R.id.SongId);
        TextView SingerName = (TextView) itemView.findViewById(R.id.artistName);
        TextView CategoryId = (TextView) itemView.findViewById(R.id.CategoryId);
        ImageView thumbImg = (ImageView) itemView.findViewById(R.id.song_img);
        ImageView offlinePlayImg = (ImageView) itemView.findViewById(R.id.offlinePlayIcon);

        SongDuration.setText(resultp.get(DevotionalSongsActivity.SongDuration));


        SongSize.setText(resultp.get(DevotionalSongsActivity.SongSize));
//        SongId.setText(resultp.get(DevotionalSongsActivity.SongID));

        CategoryId.setText(resultp.get(DevotionalSongsActivity.CategoryId));

        try
        {
            if (MiddlewareInterface.bRendering)
            {
                SingerName.setTypeface(Typeface.DEFAULT);
                SongTitle.setTypeface(Typeface.DEFAULT);
            }
            else
            {
                SingerName.setTypeface(MiddlewareInterface.tf_mylai);
                SongTitle.setTypeface(MiddlewareInterface.tf_mylai);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        try {
            if (MiddlewareInterface.bRendering)
            {
                SingerName.setText(resultp.get(DevotionalSongsActivity.SingerName));
                SongTitle.setText(resultp.get(DevotionalSongsActivity.SongTitle));
                SingerName.setTypeface(Typeface.DEFAULT);
                SongTitle.setTypeface(Typeface.DEFAULT);
            }
            else
            {
                SingerName.setText(UnicodeUtil.unicode2tsc(resultp.get(DevotionalSongsActivity.SingerName)));
                SongTitle.setText(UnicodeUtil.unicode2tsc(resultp.get(DevotionalSongsActivity.SongTitle)));
                SingerName.setTypeface(MiddlewareInterface.tf_mylai);
                SongTitle.setTypeface(MiddlewareInterface.tf_mylai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Glide.with(context)
                    .load(resultp.get(DevotionalSongsActivity.SongImgUrl))
                    .into(thumbImg);


            String songId = resultp.get(DevotionalSongsActivity.SongID);
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork == null && MiddlewareInterface.isMp3FileAvaliable(MiddlewareInterface.getMp3FilePath(songId + ".mp3"))) {
                offlinePlayImg.setVisibility(View.VISIBLE);
            } else {
                offlinePlayImg.setVisibility(View.GONE);
            }
        }catch(Exception e){
            e.printStackTrace();
        }



        return itemView;
    }
}

