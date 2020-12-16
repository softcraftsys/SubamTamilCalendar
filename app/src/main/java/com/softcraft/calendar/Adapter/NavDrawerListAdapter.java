package com.softcraft.calendar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softcraft.calendar.Activity.DayActivity;
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.Activity.NavDrawerItem;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NavDrawerListAdapter extends BaseAdapter {

    Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;
    DataBaseHelper db;
    String getappsharecontentVisit;

    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgIcon;
        ImageView tagimgIcon;
        TextView tMenudate;
        TextView tMenuWeekday;
        TextView tMenuMonth;
        TextView appHead;
        //check the position of menu
        if (position == 0) {
            LayoutInflater headerInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = headerInflater.inflate(R.layout.nav_header_main, null);
            getappsharecontentVisit = context.getPackageName();
            //get current date and day
            final SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM");
            Date d = new Date();
            String dayOfTheWeek = sdf.format(d);
            String date = new SimpleDateFormat("d").format(d);
            String getMonth;
            String getWeekday;
            String setMonth;
            String setWeekday;
            String[] splitdateofweek = dayOfTheWeek.split(" ");
            getWeekday = splitdateofweek[0];
            getMonth = splitdateofweek[1];
            //check weekdays
            if (getWeekday.equals("Monday"))
                setWeekday = "திங்கள் ";
            else if (getWeekday.equals("Tuesday"))
                setWeekday = "செவ்வாய்";
            else if (getWeekday.equals("Wednesday"))
                setWeekday = "புதன்";
            else if (getWeekday.equals("Thursday"))
                setWeekday = "வியாழன்";
            else if (getWeekday.equals("Friday"))
                setWeekday = "வெள்ளி";
            else if (getWeekday.equals("Saturday"))
                setWeekday = "சனி";
            else if (getWeekday.equals("Sunday"))
                setWeekday = "ஞாயிறு";
            else
                setWeekday = "";
            //check month
            if (getMonth.equals("January"))
                setMonth = "ஜனவரி";
            else if (getMonth.equals("February"))
                setMonth = "பிப்ரவரி";
            else if (getMonth.equals("March"))
                setMonth = "மார்ச்";
            else if (getMonth.equals("April"))
                setMonth = "ஏப்ரல்";
            else if (getMonth.equals("May"))
                setMonth = "மே";
            else if (getMonth.equals("June"))
                setMonth = "ஜூன்";
            else if (getMonth.equals("July"))
                setMonth = "ஜூலை";
            else if (getMonth.equals("August"))
                setMonth = "ஆகஸ்ட்";
            else if (getMonth.equals("September"))
                setMonth = "செப்டம்பர்";
            else if (getMonth.equals("October"))
                setMonth = "அக்டோபர்";
            else if (getMonth.equals("November"))
                setMonth = "நவம்பர்";
            else if (getMonth.equals("December"))
                setMonth = "டிசம்பர்";
            else
                setMonth = "";

            tMenudate = (TextView) convertView.findViewById(R.id.menu_cur_date);
            tMenudate.setText(date);
            tMenudate.setTypeface(Typeface.DEFAULT);
            tMenuMonth = (TextView) convertView.findViewById(R.id.menu_month);
            tMenuWeekday = (TextView) convertView.findViewById(R.id.menu_weekdayofmonth);
            appHead = (TextView) convertView.findViewById(R.id.app_head);
            if (MiddlewareInterface.bRendering) {
                tMenuMonth.setText(setMonth);
                tMenuWeekday.setText(setWeekday);
                appHead.setText(context.getResources().getString(R.string.app_head));
                tMenuMonth.setTypeface(Typeface.DEFAULT_BOLD);
                tMenuWeekday.setTypeface(Typeface.DEFAULT_BOLD);
                appHead.setTypeface(Typeface.DEFAULT_BOLD);

            } else {
                tMenuMonth.setText(UnicodeUtil.unicode2tsc(setMonth));
                tMenuWeekday.setText(UnicodeUtil.unicode2tsc(setWeekday));
                appHead.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.app_head)));
                tMenuMonth.setTypeface(MiddlewareInterface.tf_mylai);
                tMenuWeekday.setTypeface(MiddlewareInterface.tf_mylai);
                appHead.setTypeface(MiddlewareInterface.tf_mylai);
            }
        } else {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);

            imgIcon = (ImageView) convertView.findViewById(R.id.icon);
            tagimgIcon = (ImageView) convertView.findViewById(R.id.tag_image);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.title_view);
            imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
            imgIcon.setColorFilter(Color.WHITE);
            String strTitle = navDrawerItems.get(position).getTitle();
            String strNotif = context.getResources().getString(R.string.notifType);
            String strDevotional = context.getResources().getString(R.string.devotional_wall);
            if (MiddlewareInterface.bRendering) {
                txtTitle.setText(strTitle);
                txtTitle.setTypeface(Typeface.DEFAULT_BOLD);

                if (DayActivity.isFirst == true) {
                    if (position == 16) {
                        if (strTitle.equals(strNotif)) {
                            txtTitle.setText(strNotif);
                            txtTitle.setTypeface(Typeface.DEFAULT);
                        }
                    }
                    if (position == 13) {
                        if (strTitle.equals(strDevotional)) {
                            txtTitle.setText(strDevotional);
                            txtTitle.setTypeface(Typeface.DEFAULT_BOLD);
                        }
                    }
                    if(position==13){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==12){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==11){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==10){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==9){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }

                }else if(DayActivity.isFirst == false){
                    if (position == 15) {
                        if (strTitle.equals(strNotif)) {
                            txtTitle.setText(strNotif);
                            txtTitle.setTypeface(Typeface.DEFAULT);
                        }
                    }
                    if (position == 13) {
                        if (strTitle.equals(strDevotional)) {
                            txtTitle.setText(strDevotional);
                            txtTitle.setTypeface(Typeface.DEFAULT);
                        }
                    }
                    if(position==13){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==12){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==11){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==10){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==9){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }

                }
            } else {

                txtTitle.setText(UnicodeUtil.unicode2tsc(strTitle));
                txtTitle.setTypeface(MiddlewareInterface.tf_mylai);

                if (DayActivity.isFirst == true) {
                    if (position == 16) {
                        if (strTitle.equals(strNotif)) {
                            txtTitle.setText(strNotif);
                            txtTitle.setTypeface(Typeface.DEFAULT);
                        }
                    }
                    if (position == 13) {
                        if (strTitle.equals(strDevotional)) {
                            txtTitle.setText(strDevotional);
                            txtTitle.setTypeface(Typeface.DEFAULT);
                        }
                    }
                    if(position==13){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==12){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==11){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==10){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==9){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }

                }else if(DayActivity.isFirst == false){
                    if (position == 15) {
                        if (strTitle.equals(strNotif)) {
                            txtTitle.setText(strNotif);
                            txtTitle.setTypeface(Typeface.DEFAULT);
                        }
                    }
                    if (position == 13) {
                        if (strTitle.equals(strDevotional)) {
                            txtTitle.setText(strDevotional);
                            txtTitle.setTypeface(Typeface.DEFAULT);
                        }
                    }
                    if(position==13){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==12){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==11){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==10){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                    if(position==9){
                        tagimgIcon.setVisibility(View.VISIBLE);
                    }
                }

            }
        }

        return convertView;
    }
}
