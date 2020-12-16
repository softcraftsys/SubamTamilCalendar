package com.softcraft.calendar.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.util.ArrayList;

public class FestivalAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<ArrayList<String>> getListFestival;
    ArrayList<String> setListFestivalSize;
    ArrayList<String> festivalNameArray;

    public FestivalAdapter(Context context, ArrayList<ArrayList<String>> setFestivals) {
        this.context = context;
        this.getListFestival = setFestivals;
        this.setListFestivalSize = getListFestival.get(0);
    }

    @Override
    public int getCount() {
        return setListFestivalSize.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        TextView tFestivalDate;
        TextView tFestivals;
        view = convertView;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.festival_text_view, null);

        try {

            tFestivalDate = (TextView) view.findViewById(R.id.festival_date_show);
            tFestivals = (TextView) view.findViewById(R.id.festival_name_show);
            String getDate = getListFestival.get(0).get(position);
            String[] splitDate = getDate.split("\\.");
            String getSplitdate = splitDate[0];
            tFestivalDate.setText(getSplitdate);
            tFestivalDate.setTypeface(Typeface.DEFAULT);
            //set font style
            festivalNameArray = getListFestival.get(1);
            String festivals = String.valueOf(festivalNameArray.get(position));

            if (MiddlewareInterface.bRendering) {
                tFestivals.setText(festivals);
                tFestivals.setTypeface(Typeface.DEFAULT);
            } else {
                tFestivals.setText(UnicodeUtil.unicode2tsc(festivals));
                tFestivals.setTypeface(MiddlewareInterface.tf_mylai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}


//public class FestivalAdapter extends RecyclerView.Adapter<FestivalAdapter.ViewHolder> {
//
//    Context context;
//    LayoutInflater inflater;
//    ArrayList<ArrayList<String>> getListFestival;
//    ArrayList<String> setListFestivalSize;
//    ArrayList<String> festivalNameArray;
//
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        TextView tFestivalDate;
//        TextView tFestivals;
//
//        public ViewHolder(View view) {
//            super(view);
//
//            tFestivalDate = (TextView) view.findViewById(R.id.festival_date_show);
//            tFestivals = (TextView) view.findViewById(R.id.festival_name_show);
//        }
//    }
//
//    public FestivalAdapter(Context context, ArrayList<ArrayList<String>> setFestivals) {
//        this.context = context;
//        this.getListFestival = setFestivals;
//        this.setListFestivalSize = getListFestival.get(0);
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.festival_text_view, viewGroup, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
//        try {
//
//            String getDate = getListFestival.get(0).get(position);
//            String[] splitDate = getDate.split("\\.");
//            String getSplitdate = splitDate[0];
//            viewHolder.tFestivalDate.setText(getSplitdate);
//            viewHolder.tFestivalDate.setTypeface(Typeface.DEFAULT);
//            //set font style
//            festivalNameArray = getListFestival.get(1);
//            String festivals = String.valueOf(festivalNameArray.get(position));
//
//            if (MiddlewareInterface.bRendering) {
//                viewHolder.tFestivals.setText(festivals);
//                viewHolder.tFestivals.setTypeface(Typeface.DEFAULT);
//            } else {
//                viewHolder.tFestivals.setText(UnicodeUtil.unicode2tsc(festivals));
//                viewHolder.tFestivals.setTypeface(MiddlewareInterface.tf_mylai);
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return setListFestivalSize.size();
//    }
//}

