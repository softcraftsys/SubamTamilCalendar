package com.softcraft.calendar.ServiceAndOthers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WidgetProvider extends AppWidgetProvider
{
    public static String getDate;
    public DataBaseHelper db;
    ArrayList<String> getDetails;
    String showMugurtham;
    String dayOfTheWeek;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds)
    {
        try
        {
            db = new DataBaseHelper(context);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        // Create some random data
        int OSVERSION = android.os.Build.VERSION.SDK_INT;

            try
            {
                Bundle b=new Bundle();
                MiddlewareInterface AMI=MiddlewareInterface.GetInstance();

                String Heading=context.getResources().getString(R.string.app_name_aboutus);
                String viewMugurtham=context.getResources().getString(R.string.subamugurtham_day);

                int length=appWidgetIds.length;
                for (int i=0;i<length;i++) {
                    Log.d("i ", i + "test " + appWidgetIds.length + "  ");
                    int widgetId = 0;
                    widgetId = appWidgetIds[i];

                    Date d = new Date();
                    getDate = new SimpleDateFormat("d.M.yyyy").format(d);

                    SimpleDateFormat formatin = new SimpleDateFormat("d.M.yyyy");
                    SimpleDateFormat formatout = new SimpleDateFormat("d MMM, yyyy");
                    SimpleDateFormat getWeek = new SimpleDateFormat("EEEE");
                    Date newDate = null;
                    String setDate = null;
                    try {
                        newDate = formatin.parse(getDate);
                        setDate = formatout.format(newDate);
                        dayOfTheWeek = getWeek.format(newDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    getDetails = db.dGetOtherdetails(getDate);
                    String getTamilDate=null,getThithi=null,getNatchathiram=null;
                    if(getDetails.size()!=0)
                    {
                         getTamilDate = getDetails.get(4);
                         getThithi = getDetails.get(1);
                         getNatchathiram = getDetails.get(0);
                    }
                    showMugurtham = db.dGetMugurtham(getDate);

                    String Heading1=Heading;
                    //OS version check here
                    if (OSVERSION <= Build.VERSION_CODES.KITKAT)
                    {
                         Heading1=UnicodeUtil.unicode2tsc(Heading);
                        getThithi = UnicodeUtil.unicode2tsc(getThithi);
                        getNatchathiram = UnicodeUtil.unicode2tsc(getNatchathiram);
                        getTamilDate = UnicodeUtil.unicode2tsc(getTamilDate);
                        viewMugurtham = UnicodeUtil.unicode2tsc(viewMugurtham);
                        RemoteViews views = new RemoteViews(context.getPackageName(),
                                R.layout.calendar_widget);
                        views.setImageViewBitmap(R.id.heading,buildUpdate1(context,Heading1));
                        views.setImageViewBitmap(R.id.widget_thithi, buildUpdate2(context,getThithi));
                        views.setImageViewBitmap(R.id.widget_natchathiram, buildUpdate2(context,getNatchathiram));
                        views.setImageViewBitmap(R.id.Tamildate, buildUpdate(context,getTamilDate));
                        views.setImageViewBitmap(R.id.Engdate, buildUpdate(context,setDate));

                        if(showMugurtham!=null)
                        {
                            views.setViewVisibility(R.id.widget_mugurtham, View.VISIBLE);
                            views.setImageViewBitmap(R.id.widget_mugurtham, buildUpdate2(context,viewMugurtham));
                        }
                        Intent intent = new Intent(context, SplashScreen.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        views.setOnClickPendingIntent(R.id.widget_layout_click, pendingIntent);
                        appWidgetManager.updateAppWidget(widgetId, views);
                    }
                    else
                    {
                        RemoteViews views = new RemoteViews(context.getPackageName(),
                                R.layout.calendar_widget_above_api);
                        views.setTextViewText(R.id.heading_above,Heading);
                        views.setTextViewText(R.id.widget_thithi_above,getThithi);
                        views.setTextViewText(R.id.widget_natchathiram_above,getNatchathiram);
                        views.setTextViewText(R.id.Tamildate_above,getTamilDate);
                        views.setTextViewText(R.id.Engdate_above,setDate);
                        if(showMugurtham!=null)
                        {
                            views.setViewVisibility(R.id.widget_mugurtham_above, View.VISIBLE);
                        }
                        Intent intent = new Intent(context, SplashScreen.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        views.setOnClickPendingIntent(R.id.widget_layout_click_above, pendingIntent);
                        appWidgetManager.updateAppWidget(widgetId, views);
                    }
                }
            }
            catch (Exception e) {
                // TODO: handle exception
                Log.d("appWidgetIds",e.toString());
            }
    }
    public Bitmap buildUpdate(Context context,String time)
    {
        Bitmap myBitmap = Bitmap.createBitmap(130, 55, Bitmap.Config.ARGB_4444);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
        Typeface clock = Typeface.createFromAsset(context.getAssets(),"fonts/mylai.ttf");
        Typeface bold=Typeface.create(clock,Typeface.BOLD);
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(bold);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(18);
        myCanvas.drawText(time, 0, 40, paint);
        return myBitmap;
    }
    public Bitmap buildUpdate1(Context context,String time)
    {
        Bitmap myBitmap = Bitmap.createBitmap(250,55, Bitmap.Config.ARGB_4444);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
        Typeface clock = Typeface.createFromAsset(context.getAssets(),"fonts/mylai.ttf");
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface( clock);
        paint.setColor(Color.WHITE);
        paint.setTextSize(24);
        myCanvas.drawText(time, 0, 40, paint);
        return myBitmap;
    }
    public Bitmap buildUpdate2(Context context,String time)
    {
        Bitmap bitmap = Bitmap.createBitmap(450, 40, Bitmap.Config.ARGB_4444);
        Canvas myCanvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Typeface clock = Typeface.createFromAsset(context.getAssets(),"fonts/mylai.ttf");
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(clock);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTextSize(16);
        myCanvas.drawText(time, 0, 25, paint);
        return bitmap;
    }
}
