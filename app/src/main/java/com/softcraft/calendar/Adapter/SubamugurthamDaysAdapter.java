package com.softcraft.calendar.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softcraft.calendar.R;

import java.util.ArrayList;

public class SubamugurthamDaysAdapter extends RecyclerView.Adapter<SubamugurthamDaysAdapter.ViewHolder>
{
    Context context;
    ArrayList<ArrayList<String>> setSubaMugurtham;
    private ArrayList<String> getSubaMugurthamSize;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
//        public TextView tDate;
//       public TextView tDay;
////        public TextView tTime;
        public View mView;

        public TextView englishdate_text,tamildate_text,day_text,time_text;

        public ViewHolder(View v)
        {
            super(v);

//          tDate = (TextView) v.findViewById(R.id.date_text);
////
//      tDay = (TextView) v.findViewById(R.id.day_text);

            englishdate_text=(TextView)v.findViewById(R.id.englishdate);
            tamildate_text=(TextView)v.findViewById(R.id.tamildate);
            day_text=(TextView)v.findViewById(R.id.day);
            time_text=(TextView)v.findViewById(R.id.time);

        }
    }
    public SubamugurthamDaysAdapter(Context context1, ArrayList<ArrayList<String>> getSubaMugurtham)
    {
        context = context1;
        setSubaMugurtham = getSubaMugurtham;
        getSubaMugurthamSize=setSubaMugurtham.get(0);
    }
    @Override
    public SubamugurthamDaysAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trible_column_text_view, parent, false);

//
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.subhamuhurthamdays, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        String getDate=setSubaMugurtham.get(0).get(position);
//        String getWeekday=setSubaMugurtham.get(1).get(position);
//        SimpleDateFormat formatin = new SimpleDateFormat("d.M.yyyy");
//        SimpleDateFormat formatout = new SimpleDateFormat("d/M/yyyy");
//        Date newDate=null;
//        String setDate=null;

        String getEnglishDate=setSubaMugurtham.get(0).get(position);
        String getTamilDatte=setSubaMugurtham.get(1).get(position);
        String getDay=setSubaMugurtham.get(2).get(position);
        String getTime=setSubaMugurtham.get(3).get(position);
//
        holder.englishdate_text.setText(getEnglishDate);
        holder.tamildate_text.setText(getTamilDatte);
        holder.day_text.setText(getDay);
        holder.time_text.setText(getTime);

        int iPos = position + 1;
//        iPos = iPos / 2;
//        if ( iPos % 2 == 0 )
//            System.out.println("You entered an even number.");
//        else
//            System.out.println("You entered an odd number.");


//        if ( iPos % 2 == 0 ) {
//
//            holder.englishdate_text.setBackgroundResource(R.color.media_background);
//            holder.tamildate_text.setBackgroundResource(R.color.media_background);
//            holder.day_text.setBackgroundResource(R.color.media_background);
//            holder.time_text.setBackgroundResource(R.color.media_background);
//
//        } else {
//            holder.englishdate_text.setBackgroundResource(R.color.white);
//            holder.tamildate_text.setBackgroundResource(R.color.white);
//            holder.day_text.setBackgroundResource(R.color.white);
//            holder.time_text.setBackgroundResource(R.color.white);
//        }

//     try {
//            newDate = formatin.parse(getDate);
//            setDate = formatout.format(newDate);
//
//        } catch (ParseException e)
//        {
//            e.printStackTrace();
//        }
//
//        String setWeekday="";
//        if(getWeekday.equalsIgnoreCase("Monday"))
//            setWeekday="திங்கள் ";
//        else if(getWeekday.equalsIgnoreCase("Tuesday"))
//            setWeekday="செவ்வாய்";
//        else if(getWeekday.equalsIgnoreCase("Wednesday"))
//            setWeekday="புதன்";
//        else if(getWeekday.equalsIgnoreCase("Thursday"))
//            setWeekday="வியாழன்";
//        else if(getWeekday.equalsIgnoreCase("Friday"))
//            setWeekday="வெள்ளி";
//        else if(getWeekday.equalsIgnoreCase("Saturday"))
//            setWeekday="சனி";
//        else if(getWeekday.equalsIgnoreCase("Sunday"))
//            setWeekday="ஞாயிறு";
//        holder.tDate.setText(setDate);
////        holder.tTime.setText(setSubaMugurtham.get(2).get(position));
//        holder.tDate.setTypeface(Typeface.DEFAULT);
////        holder.tTime.setTypeface(Typeface.DEFAULT);
//        if(MiddlewareInterface.bRendering)
//        {
//            holder.tDay.setText(setWeekday);
//            holder.tDay.setTypeface(Typeface.DEFAULT);
//        }
//        else
//        {
//            holder.tDay.setText(UnicodeUtil.unicode2tsc(setWeekday));
//            holder.tDay.setTypeface(MiddlewareInterface.tf_mylai);
//        }
    }
    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return getSubaMugurthamSize.size();
    }
}

