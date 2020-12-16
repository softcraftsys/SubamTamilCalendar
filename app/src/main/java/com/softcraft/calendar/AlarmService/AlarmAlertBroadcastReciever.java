/* Copyright 2014 Sheldon Neilson www.neilson.co.za
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package com.softcraft.calendar.AlarmService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;

import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.R;
import com.softcraft.calendar.SplashScreen.SplashScreen;
import com.softcraft.calendar.ServiceAndOthers.StaticWakeLock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
public class AlarmAlertBroadcastReciever extends BroadcastReceiver {
	static SQLiteDatabase db;
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent mathAlarmServiceIntent = new Intent(context, AlarmServiceBroadcastReciever.class);
		context.sendBroadcast(mathAlarmServiceIntent, null);
		StaticWakeLock.lockOn(context);
		try {
			String strDesc = getNotification(context);
			String strPriorDesc = getPriorNotification(context);
			String strDescNotify = context.getResources().getString(R.string.today) + " " + strDesc;
			String strPriorDescNotify = context.getResources().getString(R.string.tomorrow) + " " + strPriorDesc;
			String strDescPriorDescNotify = context.getResources().getString(R.string.today) + " " + strDesc + " , " + context.getResources().getString(R.string.tomorrow) + " " + strPriorDesc;
			if (strDesc != null || strPriorDesc != null)
			{
				if (strDesc != null)
				{
					if (strPriorDesc != null)
					{
						showCrntDayNotifivation(context, strDescPriorDescNotify, intent);
					} else {
						showCrntDayNotifivation(context, strDescNotify, intent);
					}
				}
				else
				{
					showCrntDayNotifivation(context, strPriorDescNotify, intent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void showCrntDayNotifivation(Context context,String strDescPriorDesc,Intent intent)
	{
		try {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
				if (strDescPriorDesc != null && !strDescPriorDesc.equalsIgnoreCase(""))
				{
					Resources res = context.getResources();
					intent = new Intent(context, SplashScreen.class);
					PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
					NotificationCompat.Builder b = new NotificationCompat.Builder(context);
					Boolean isSound = preferences.getBoolean("notificationsound", true);
					if (isSound) {
						b.setAutoCancel(true)
								.setDefaults(Notification.DEFAULT_ALL)
								.setWhen(System.currentTimeMillis())
								.setSmallIcon(getNotificationIcon())
								.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
								.setContentTitle(context.getResources().getString(R.string.app_name))
								.setStyle(new NotificationCompat.BigTextStyle().bigText(strDescPriorDesc))
								.setContentText(strDescPriorDesc)
								.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
								.setContentIntent(contentIntent);
					} else {
						b.setAutoCancel(true)
								.setDefaults(Notification.DEFAULT_ALL)
								.setWhen(System.currentTimeMillis())
								.setSmallIcon(getNotificationIcon())
								.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
								.setContentTitle(context.getResources().getString(R.string.app_name))
								.setStyle(new NotificationCompat.BigTextStyle().bigText(strDescPriorDesc))
								.setContentText(strDescPriorDesc)
								.setContentIntent(contentIntent);
					}
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					int id = (int) System.currentTimeMillis();
					notificationManager.notify(id, b.build());
				}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	private String getNotification(Context context){
		try{
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			Boolean isFestivalDay=preferences.getBoolean("festivalDay",true);
			Boolean isAuspicious=preferences.getBoolean("auspicious",true);
			Boolean isKirthigai=preferences.getBoolean("kirthigai",true);
			String strFasting=null,strFestival=null,strKirthigai=null,strMugurtham=null,strAddAll=null;
			DataBaseHelper db=new DataBaseHelper(context);
			Date d = new Date();
			String strCrntDate = new SimpleDateFormat("dd.MM.yyyy").format(d);
			ArrayList<String> totalCountList=db.dGetDate();
			for(int i=0; i<totalCountList.size(); i++)
			{
				if(totalCountList.get(i).equals(strCrntDate))
				{
					String strDate = totalCountList.get(i);
					ArrayList<String> fastingList=db.dGetOtherdetails(strDate);
					if(isAuspicious)
						strMugurtham=db.dGetMugurtham(strDate);
					if(isKirthigai)
						strKirthigai=db.dGetKirthigai(strDate);
					if(fastingList.size() > 6)
						strFasting = fastingList.get(7);
					if(isFestivalDay)
						if (fastingList.size() > 5)
							strFestival = fastingList.get(6);
					try
					{
						if (strKirthigai != null&&strKirthigai!="")
						{
							strAddAll += context.getResources().getString(R.string.kirthigai) ;
							if (strMugurtham != null)
								strAddAll += "," + context.getResources().getString(R.string.submugurthanal);
							if(!strFasting.equalsIgnoreCase("null")&&!strFasting.equalsIgnoreCase(""))
								strAddAll += "," + strFasting;
							if(!strFestival.equalsIgnoreCase("null")&&!strFestival.equalsIgnoreCase(""))
								strAddAll += "," + strFestival;
						}
						else if (strMugurtham != null&&strMugurtham!="")
						{
							strAddAll += context.getResources().getString(R.string.submugurthanal);
							if(!strFasting.equalsIgnoreCase("null")&&!strFasting.equalsIgnoreCase(""))
								strAddAll += "," + strFasting;
							if(!strFestival.equalsIgnoreCase("null")&&!strFestival.equalsIgnoreCase(""))
								strAddAll += "," + strFestival;
						}
						else if(!strFasting.equalsIgnoreCase("null")&&!strFasting.equalsIgnoreCase(""))
						{
							strAddAll += strFasting;
							if(!strFestival.equalsIgnoreCase("null")&&!strFestival.equalsIgnoreCase(""))
								strAddAll += "," + strFestival;
						}
						else if(!strFestival.equalsIgnoreCase("null")&&!strFestival.equalsIgnoreCase(""))
							strAddAll += strFestival;
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					strAddAll=strAddAll.replace("null","");
					return strAddAll;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	private String getPriorNotification(Context context){
		try{
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			Boolean isPriorFestival=preferences.getBoolean("priorfestivalDay",true);
			Boolean isPriorAuspicious=preferences.getBoolean("priorauspicious",true);
			Boolean isPriorKirthigai=preferences.getBoolean("priorkirthigai",true);
			String strFasting=null,strFestival=null,strKirthigai=null,strMugurtham=null,strAddAll=null;
			DataBaseHelper db=new DataBaseHelper(context);
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, +1);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
			String strPriorDate = dateFormat.format(calendar.getTime());
			ArrayList<String> totalCountList=db.dGetDate();
			for(int i=0; i<totalCountList.size(); i++)
			{
				if(totalCountList.get(i).equals(strPriorDate))
				{
					String strDate = totalCountList.get(i);
					ArrayList<String> fastingList=db.dGetOtherdetails(strDate);
					if(isPriorAuspicious)
						strMugurtham=db.dGetMugurtham(strDate);
					if(isPriorKirthigai)
						strKirthigai=db.dGetKirthigai(strDate);
					if(fastingList.size() > 6)
						strFasting = fastingList.get(7);
					if(isPriorFestival)
						if (fastingList.size() > 5)
							strFestival = fastingList.get(6);
					try
					{
						if (strKirthigai!=null&&strKirthigai!="")
						{
							if(isPriorKirthigai)
							{
								strAddAll += context.getResources().getString(R.string.kirthigai);
								if (strMugurtham != null)
									strAddAll += ","+ context.getResources().getString(R.string.submugurthanal);
								if(!strFasting.equalsIgnoreCase("null")&&!strFasting.equalsIgnoreCase(""))
									strAddAll += ","+ strFasting;
								if(!strFestival.equalsIgnoreCase("null")&&!strFestival.equalsIgnoreCase(""))
									strAddAll += ","+ strFestival;
							}
						}
						else if (strMugurtham != null&&strMugurtham!="")
						{
							if (strMugurtham != null)
								strAddAll += context.getResources().getString(R.string.submugurthanal);
							if(!strFasting.equalsIgnoreCase("null")&&!strFasting.equalsIgnoreCase(""))
								strAddAll += ","+ strFasting;
							if(!strFestival.equalsIgnoreCase("null")&&!strFestival.equalsIgnoreCase(""))
								strAddAll += ","+ strFestival;
						}
						else if(!strFasting.equalsIgnoreCase("null")&&!strFasting.equalsIgnoreCase(""))
						{
							strAddAll += strFasting;
							if(!strFestival.equalsIgnoreCase("null")&&!strFestival.equalsIgnoreCase(""))
								strAddAll += ","+ strFestival;
						}
						else if(!strFestival.equalsIgnoreCase("null")&&!strFestival.equalsIgnoreCase(""))
							strAddAll += strFestival;
					}catch (Exception e){
						e.printStackTrace();
					}
					strAddAll=strAddAll.replace("null","");
					return strAddAll;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	private  int getNotificationIcon()
	{
		boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
		return useWhiteIcon ? R.mipmap.calendar_icon_notify : R.drawable.ic_launcher;
	}
}