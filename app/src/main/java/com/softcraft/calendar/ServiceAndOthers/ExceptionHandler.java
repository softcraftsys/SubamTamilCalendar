package com.softcraft.calendar.ServiceAndOthers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionHandler implements
        java.lang.Thread.UncaughtExceptionHandler {
    private final Context myContext;
    private final Class<?> myActivityClass;

    public ExceptionHandler(Context context, Class<?> c) {
        myContext = context;
        myActivityClass = c;
    }

    public void uncaughtException(Thread thread, Throwable exception) {
        try{
            writeLog(exception.toString());
            StringWriter stackTrace = new StringWriter();
            exception.printStackTrace(new PrintWriter(stackTrace));
            //System.err.println(stackTrace);// You can use LogCat too
            writeLog(stackTrace.toString());

            // MiddlewareInterface JMI=MiddlewareInterface.GetInstance();
            // JMI.init(myContext);

            //if req ccall SplashAcivity
            //Intent itemintent = new Intent(myContext,SplashActivity.class);
            //itemintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //ApplicationController.getInstance().startActivity(itemintent);

            /*StringWriter stackTrace = new StringWriter();
            exception.printStackTrace(new PrintWriter(stackTrace));
            System.err.println(stackTrace);// You can use LogCat too
            Intent intent = new Intent(myContext, myActivityClass);
            String s = stackTrace.toString();
            //you can use this String to know what caused the exception and in which Activity
            intent.putExtra("uncaughtException",
                    "Exception is: " + stackTrace.toString());
            intent.putExtra("stacktrace", s);
            myContext.startActivity(intent);
            //for restarting the Activity
            System.exit(0);*/

            Log.d("UCEH", "uncaughtException called");

        }catch (Exception ex){
            writeLog(ex.toString());
        }

    }
    private void writeLog(String str)
    {
        try{
            Intent mStartActivity = new Intent(myContext, SplashScreen.class);
            mStartActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mStartActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(myContext, mPendingIntentId, mStartActivity,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager) myContext.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            BackgroundAudioService backgroundAudioService=new BackgroundAudioService();
            backgroundAudioService.cancelNotify(myContext);
            System.exit(0);
        }catch (Exception ignored){}
    }
}
