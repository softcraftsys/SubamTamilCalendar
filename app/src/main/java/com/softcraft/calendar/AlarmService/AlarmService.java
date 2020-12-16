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


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Alarm alarm = new Alarm();
			String str1="06:00";
			alarm.setId(0);
			alarm.setAlarmName("Subam Tamil Calendar");
			alarm.setAlarmTime(str1);
			alarm.schedule(getApplicationContext());
		return START_NOT_STICKY;
	}
}

