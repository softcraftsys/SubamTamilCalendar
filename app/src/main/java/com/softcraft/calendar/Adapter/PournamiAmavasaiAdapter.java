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

        import java.util.ArrayList;

public class PournamiAmavasaiAdapter extends RecyclerView.Adapter<PournamiAmavasaiAdapter.ViewHolder>
{
    Context context;
    private ArrayList<ArrayList<String>> aGetFestivalDays;
    private ArrayList<String> aGetFestivalSize;
    public LinearLayout tablayout;
    TableLayout tablelayout;



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public View mView;
//        public TextView tFestival;
//        public TextView tDate;

        public TextView englishdate_text,tamildate_text,day_text,time_text;

        public ViewHolder(View v)
        {
            super(v);
            mView = v;
//            tDate    = (TextView) v.findViewById(R.id.festival_date);
//            tFestival = (TextView) v.findViewById(R.id.festival_name);

//            if(position%2==0){
//                tablayout.setBackgroundResource(R.color.light_orange_red);
//            }


            englishdate_text=(TextView)v.findViewById(R.id.englishdate);
            tamildate_text=(TextView)v.findViewById(R.id.tamildate);
            day_text=(TextView)v.findViewById(R.id.day);
            time_text=(TextView)v.findViewById(R.id.time);
        }
    }
    public PournamiAmavasaiAdapter(Context id, ArrayList<ArrayList<String>> getFestivalValue)
    {
        context=id;
        aGetFestivalDays=getFestivalValue;
        aGetFestivalSize=getFestivalValue.get(0);
    }
    @Override
    public PournamiAmavasaiAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pournamiamavasai_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);


        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       /* String getDate=aGetFestivalDays.get(0).get(position);
        SimpleDateFormat formatin = new SimpleDateFormat("d.M.yyyy");
        SimpleDateFormat formatout = new SimpleDateFormat("d/M/yyyy");
        Date newDate=null;
        String setDate=null;
        try {
            newDate = formatin.parse(getDate);
            setDate = formatout.format(newDate);

        } catch (ParseException e)
        {
            e.printStackTrace();
        }*/
//        holder.tDate.setText(setDate);
//        holder.tDate.setTypeface(Typeface.DEFAULT);

        String getEnglishDate=aGetFestivalDays.get(0).get(position);
        String getTamilDatte=aGetFestivalDays.get(1).get(position);
        String getDay=aGetFestivalDays.get(2).get(position);
        String getTime=aGetFestivalDays.get(3).get(position);
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


        if ( iPos % 2 == 0 ) {

            holder.englishdate_text.setBackgroundResource(R.color.media_background);
            holder.tamildate_text.setBackgroundResource(R.color.media_background);
            holder.day_text.setBackgroundResource(R.color.media_background);
            holder.time_text.setBackgroundResource(R.color.media_background);


        } else {
            holder.englishdate_text.setBackgroundResource(R.color.white);
            holder.tamildate_text.setBackgroundResource(R.color.white);
            holder.day_text.setBackgroundResource(R.color.white);
            holder.time_text.setBackgroundResource(R.color.white);

        }


    }

    @Override
    public int getItemCount()
    {
        return aGetFestivalSize.size();
    }
}
