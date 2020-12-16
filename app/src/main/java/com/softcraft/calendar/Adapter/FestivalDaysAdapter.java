package com.softcraft.calendar.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.util.ArrayList;

public class FestivalDaysAdapter extends RecyclerView.Adapter<FestivalDaysAdapter.ViewHolder>
{
    Context context;
    private ArrayList<ArrayList<String>> aGetFestivalDays;
    private int aGetFestivalSize;
    public LinearLayout tablayout;
//    TableLayout tablelayout;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public View mView;
//        public TextView tFestival;
//        public TextView tDate;

        public TextView englishdate_text,tamildate_text,day_text,time_text,headingTV;
        TableLayout tableLayout;

        public ViewHolder(View v)
        {
            super(v);
            mView = v;
//            tDate    = (TextView) v.findViewById(R.id.festival_date);
//            tFestival = (TextView) v.findViewById(R.id.festival_name);

//            if(position%2==0){
//                tablayout.setBackgroundResource(R.color.light_orange_red);
//            }


            tableLayout = v.findViewById(R.id.tableLayout);
            englishdate_text=(TextView)v.findViewById(R.id.englishdate);
            tamildate_text=(TextView)v.findViewById(R.id.tamildate);
            day_text=(TextView)v.findViewById(R.id.day);
            time_text=(TextView)v.findViewById(R.id.time);

        }
    }
    public FestivalDaysAdapter(Context id, ArrayList<ArrayList<String>> getFestivalValue)
    {
        context=id;

        if(getFestivalValue == null){
            aGetFestivalDays = getFestivalValue;
            aGetFestivalSize = 0;
        }else {
            aGetFestivalDays = getFestivalValue;
            aGetFestivalSize = getFestivalValue.get(0).size();
        }
    }
    @Override
    public FestivalDaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trible_column_text_view, parent, false);
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {

        try {
            if(aGetFestivalDays !=null ) {
                String getEnglishDate = aGetFestivalDays.get(0).get(position);
                if(getEnglishDate.contains("."+String.valueOf(SplashScreen.iFromYear)) || getEnglishDate.contains("."+String.valueOf(SplashScreen.iToYear))) {

                }else{
                    getEnglishDate = getEnglishDate.replace(String.valueOf(SplashScreen.iFromYear), "").replace(String.valueOf(SplashScreen.iToYear), "");
                }


                String[] tamildateArr = null;
                String getTamilDatte = null;

                if(aGetFestivalDays.get(1).get(position).contains(",")) {
                    tamildateArr = aGetFestivalDays.get(1).get(position).split(",");
                    getTamilDatte = tamildateArr[1];
                }else{
                    getTamilDatte = aGetFestivalDays.get(1).get(position);
                }

                String getDay = aGetFestivalDays.get(2).get(position);
                String getTime = aGetFestivalDays.get(3).get(position);

                holder.englishdate_text.setText(getEnglishDate);
                holder.tamildate_text.setText(getTamilDatte);
                holder.day_text.setText(getDay);
                holder.time_text.setText(getTime);

//                if(getTime.contains("அஷ்டமி") || getTime.contains("நவமி") || getTime.contains("தசமி") ){
//                    holder.tableLayout.setVisibility(View.GONE);
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount()
    {
        return aGetFestivalSize;
    }
}
