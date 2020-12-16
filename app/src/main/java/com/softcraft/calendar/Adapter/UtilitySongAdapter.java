package com.softcraft.calendar.Adapter;


import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.softcraft.calendar.Activity.UtilitySongsActivity;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

public class UtilitySongAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<HashMap<String, String>> data;
    TextView SongTitle,SingerName,songDuration;
    ImageView songImage;
    View itemView;
    HashMap<String, String> resultp = new HashMap<>();

    public UtilitySongAdapter(Context context, List<HashMap<String, String>> arraylist) {
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

        itemView = inflater.inflate(R.layout.utility_songs_adapter, parent, false);
        try {
            resultp = data.get(position);
            SongTitle  = (TextView) itemView.findViewById(R.id.UtilSongName);
            songImage = (ImageView) itemView.findViewById(R.id.UtilSongimage);
            songDuration = (TextView) itemView.findViewById(R.id.SongDuration);
            SingerName = (TextView) itemView.findViewById(R.id.artistName);
            String songName = resultp.get(UtilitySongsActivity.UtlitySongName);
            String songDurations = resultp.get(SplashScreen.utilSongDuration);
            String singerName = resultp.get(SplashScreen.utilSingerName);
            songDuration.setText(songDurations);

            try {
                if (MiddlewareInterface.bRendering)
                {
                    SongTitle.setText(songName);
                    SingerName.setText(singerName);
                    SongTitle.setTypeface(Typeface.DEFAULT);
                    SingerName.setTypeface(Typeface.DEFAULT);
                }
                else
                {
                    SongTitle.setText(UnicodeUtil.unicode2tsc(songName));
                    SingerName.setText(UnicodeUtil.unicode2tsc(singerName));
                    SongTitle.setTypeface(MiddlewareInterface.tf_mylai);
                    SingerName.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Glide.with(context)
                        .load(resultp.get(UtilitySongsActivity.UtlitySongImage))
                        .into(songImage);

            }catch(Exception e){
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemView;
    }
}

