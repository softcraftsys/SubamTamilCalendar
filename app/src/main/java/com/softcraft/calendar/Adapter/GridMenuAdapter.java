package com.softcraft.calendar.Adapter;

import android.content.Context;

import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softcraft.calendar.R;

public class GridMenuAdapter extends BaseAdapter {

    String[] itemName;
    Context context;
    int[] imageId;
    private static LayoutInflater inflater = null;

    public GridMenuAdapter(Context mainActivity, String[] prgmNameList, int[] prgmImages) {
        // TODO Auto-generated constructor stub
        itemName = prgmNameList;
        context = mainActivity;
        imageId = prgmImages;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageId.length;
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

    public class Holder {
        TextView tv;
        ImageView img;
        ImageView tag_img;
        CardView cardView;
        LinearLayout fullLLayout;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        Holder holder = new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.grid_menu_adapter_layout, null);
        holder.tv = (TextView) rowView.findViewById(R.id.grid_text);
        holder.img = (ImageView) rowView.findViewById(R.id.grid_images);
        holder.tag_img = (ImageView) rowView.findViewById(R.id.tag_image);
//        holder.cardView = (CardView) rowView.findViewById(R.id.CardViewGrid);
        holder.fullLLayout =  rowView.findViewById(R.id.fullLLayout);
        holder.img.setImageResource(imageId[position]);
//        String strTitle = itemName[position];
        String strNotif = context.getResources().getString(R.string.notifType);
        String strDevotional = context.getResources().getString(R.string.devotional_wall);

        try {
            if (position == 2) {
                holder.tag_img.setVisibility(View.VISIBLE);
            }
            if (position == 3) {
                holder.tag_img.setVisibility(View.VISIBLE);
            }
            if (position == 4) {
                holder.tag_img.setVisibility(View.VISIBLE);
            }
//            if (position == 5) {
//                holder.tag_img.setVisibility(View.VISIBLE);
//            }
//            if (position == 6) {
//                holder.tag_img.setVisibility(View.VISIBLE);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowView;
    }
}
