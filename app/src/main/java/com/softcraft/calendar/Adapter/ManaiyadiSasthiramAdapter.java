package com.softcraft.calendar.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.util.ArrayList;

public class ManaiyadiSasthiramAdapter extends RecyclerView.Adapter<ManaiyadiSasthiramAdapter.ViewHolder> {
    Context context;
    private ArrayList<String> getManai;
    private boolean isExist= false;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView tSetManiNumbers;
        private TextView tSetManaiThougts;
        private TableRow tablerowManaiLayout;
        private View mView;


        public ViewHolder(View v) {
            super(v);
            mView = v;
            tSetManiNumbers = (TextView) v.findViewById(R.id.manai_no);
            tSetManaiThougts = (TextView) v.findViewById(R.id.mani_detail);
            tablerowManaiLayout =  v.findViewById(R.id.tablerowManaiLayout);
        }
    }

    public ManaiyadiSasthiramAdapter(Context context1, ArrayList<String> setManaivalue) {
        context = context1;
        getManai = setManaivalue;
    }

    @Override
    public ManaiyadiSasthiramAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.manaiyadi_sasthiram, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        if (position == 0) {
            String getManainumber = "அடி";
            String getThoughts = "பலன்";
            holder.tSetManiNumbers.setText(getManainumber);
            holder.tSetManiNumbers.setTextColor(Color.parseColor("#ffffff"));
            holder.tSetManaiThougts.setTextColor(Color.parseColor("#ffffff"));
            holder.tSetManiNumbers.setBackgroundColor(Color.parseColor("#9966cc"));
            holder.tSetManaiThougts.setBackgroundColor(Color.parseColor("#9966cc"));

            if (MiddlewareInterface.bRendering) {
                holder.tSetManaiThougts.setText(getThoughts);
                holder.tSetManaiThougts.setTypeface(Typeface.DEFAULT_BOLD);
                holder.tSetManiNumbers.setTypeface(Typeface.DEFAULT_BOLD);
            } else {
                holder.tSetManaiThougts.setText(UnicodeUtil.unicode2tsc(getThoughts));
                holder.tSetManaiThougts.setTypeface(MiddlewareInterface.tf_mylai);
//                holder.tSetManaiThougts.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);
            }

        } else{
            holder.tSetManiNumbers.setTextColor(Color.parseColor("#000000"));
            holder.tSetManaiThougts.setTextColor(Color.parseColor("#000000"));

            holder.tSetManiNumbers.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.tSetManaiThougts.setBackgroundColor(Color.parseColor("#ffffff"));
            String getManaidetails = getManai.get(position);
            String[] splitManaidetails = getManaidetails.split("-");
            String getManainumber = splitManaidetails[0];
            String getThoughts = splitManaidetails[1];
            holder.tSetManiNumbers.setText(getManainumber);
            holder.tSetManiNumbers.setTypeface(Typeface.DEFAULT);
            //set font style
            if (MiddlewareInterface.bRendering) {
                holder.tSetManaiThougts.setText(getThoughts);
                holder.tSetManaiThougts.setTypeface(Typeface.DEFAULT);
            } else {
                holder.tSetManaiThougts.setText(UnicodeUtil.unicode2tsc(getThoughts));
                holder.tSetManaiThougts.setTypeface(MiddlewareInterface.tf_mylai);
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return getManai.size();
    }
}