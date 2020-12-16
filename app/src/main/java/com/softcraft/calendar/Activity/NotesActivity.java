package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ReminderNotification.NotificationPublisher;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.util.ArrayList;
import java.util.Calendar;

import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerAd;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getEnable;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getFacebookBannerType;
import static com.softcraft.calendar.Middleware.MiddlewareInterface.getNativeSmall;
import static java.util.Calendar.DAY_OF_MONTH;

public class NotesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RelativeLayout notesBackgroundLayout;
    private NativeExpressAdView viewNativeAd;
    LinearLayout linearad;
    AdView adview;
    EditText titleEdtTv, descEdtTv;
    DataBaseHelper db;
    int iDay, iMonth, iYear;
    String strDay, strMonth, strYear;
    ArrayList<ArrayList<String>> arrNotesList;
    RecyclerAdapter recyclerAdapter;
    String strOldNoteTitle, strOldNoteDescription, strOldNoteId;
    Boolean isNoteEdit = false;
    Boolean isNoteHaveReminder = false;
    Boolean isNoteAdd = false;

    private SignInButton signInButton;
    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;
    private int SIGN_IN = 30;

    LinearLayout noteReminderLayout;
    TextView reminderTimeTv, reminderDateTv, remindedDateAndTimeTv;
    String sReminderDate = "";
    String sReminderTime = "";
    public Boolean isReminded = false;
    public Boolean isAlreadyReminded = false;
    String sAmPm = "";
    public Calendar noteEditReminderCalendar;
    SwipeRefreshLayout swipeRefresh;
    int colorId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        try {
            getCurrentDateAndTime();
            InitItems();
            swipeRefresh = findViewById(R.id.swipe_refresh);
            swipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN);
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {
                        arrNotesList = db.getNotes();
                        GridLayoutManager mLayoutManager = new GridLayoutManager(NotesActivity.this, 2, GridLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerAdapter = new RecyclerAdapter(getApplicationContext());
                        recyclerView.setAdapter(recyclerAdapter);
                        swipeRefresh.setRefreshing(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCurrentDateAndTime() {
        try {
            Calendar calendar = Calendar.getInstance();
            iDay = calendar.get(Calendar.DAY_OF_MONTH);
            iMonth = calendar.get(Calendar.MONTH);
            iYear = calendar.get(Calendar.YEAR);

            if (iDay < 9) {
                strDay = "0" + iDay;
            } else {
                strDay = String.valueOf(iDay);
            }
            String[] arrEngMonth = getResources().getStringArray(R.array.english_month);
            strMonth = arrEngMonth[iMonth];
            strYear = String.valueOf(iYear);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitItems() {
        try {
            recyclerView = (RecyclerView) findViewById(R.id.notesRecyclerView);
            notesBackgroundLayout = (RelativeLayout) findViewById(R.id.notesBackgroundLayout);
            RelativeLayout notesContentLayout = (RelativeLayout) findViewById(R.id.notesContentLayout);
            titleEdtTv = (EditText) findViewById(R.id.titleEditTv);
            descEdtTv = (EditText) findViewById(R.id.descEditTv);
            Button saveBtn = (Button) findViewById(R.id.saveBtn);
            Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
            db = new DataBaseHelper(getApplicationContext());

            notesContentLayout.setOnClickListener(clickListener);
            saveBtn.setOnClickListener(clickListener);
            cancelBtn.setOnClickListener(clickListener);

            ImageView ivBack = (ImageView) findViewById(R.id.back_arrow);
            TextView tvHeader = (TextView) findViewById(R.id.title_header);
            ImageView addNotesBtn = (ImageView) findViewById(R.id.addNoteImg);
            linearad = (LinearLayout) findViewById(R.id.notification_adview);

            TextView remindMeTv = (TextView) findViewById(R.id.remindmeTv);
            reminderTimeTv = (TextView) findViewById(R.id.reminderTimeTv);
            reminderDateTv = (TextView) findViewById(R.id.reminderDateTv);
            remindedDateAndTimeTv = (TextView) findViewById(R.id.remindedDateAndTimeTv);

            noteReminderLayout = (LinearLayout) findViewById(R.id.noteReminderLayout);
            LinearLayout reminderDateLayout = (LinearLayout) findViewById(R.id.reminderDateLayout);
            LinearLayout reminderTimeLayout = (LinearLayout) findViewById(R.id.reminderTimeLayout);

            ivBack.setOnClickListener(clickListener);
            tvHeader.setOnClickListener(clickListener);
            addNotesBtn.setOnClickListener(clickListener);
            notesBackgroundLayout.setOnClickListener(clickListener);

            remindMeTv.setOnClickListener(clickListener);
            reminderDateLayout.setOnClickListener(clickListener);
            reminderTimeLayout.setOnClickListener(clickListener);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                try {
                    if (getEnable.equalsIgnoreCase("1")) {
                        if (getBannerType.equalsIgnoreCase("0")) {
                            setAdvertise();
                        } else {
                            setNativeSmall();
                        }
                    } else if (getEnable.equalsIgnoreCase("4")) {
                        if (getFacebookBannerType.equalsIgnoreCase("1")) {
                            MiddlewareInterface.setNativeFBAD(this, linearad);
                        } else {
                            MiddlewareInterface.setBannerFBAD(this, linearad);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                if (MiddlewareInterface.bRendering) {
                    tvHeader.setText(getResources().getString(R.string.notes_title));
                    tvHeader.setTypeface(Typeface.DEFAULT);
                } else {
                    tvHeader.setText(UnicodeUtil.unicode2tsc(getResources().getString(R.string.notes_title)));
                    tvHeader.setTypeface(MiddlewareInterface.tf_mylai);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                arrNotesList = db.getNotes();
                GridLayoutManager mLayoutManager = new GridLayoutManager(NotesActivity.this, 2, GridLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerAdapter = new RecyclerAdapter(getApplicationContext());
                recyclerView.setAdapter(recyclerAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.back_arrow || view.getId() == R.id.title_header) {
                setAnimationFunc(view);
            } else if (view.getId() == R.id.saveBtn) {
                if (!titleEdtTv.getText().toString().equalsIgnoreCase("")) {
                    try {
                        hideSoftKeyboard();
                        if (isNoteEdit) {
                            if (noteReminderLayout.getVisibility() == View.VISIBLE) {
                                isReminded = true;
                            } else {
                                isReminded = false;
                            }
                            if (isReminded) {
                                try {
                                    String[] arrDate = sReminderDate.split("\\.");
                                    String[] arrTime = sReminderTime.split(":");
                                    int day = Integer.parseInt(arrDate[0]);
                                    int month = Integer.parseInt(arrDate[1]);
                                    month = month - 1;
                                    int year = Integer.parseInt(arrDate[2]);
                                    int hour = Integer.parseInt(arrTime[0]);
                                    int min = Integer.parseInt(arrTime[1]);

                                    Boolean isValidTime = CheckIsValidTimeToReminder(day, month, year, hour, min);
                                    if (isValidTime) {
                                        noteEditFunc();
                                    } else {
                                        Toast toast = Toast.makeText(NotesActivity.this, "Cannot set reminder for previous date or time", Toast.LENGTH_LONG);
                                        toast.show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                isReminded = false;
                                noteEditFunc();
                            }

                        } else {
                            try {
                                if (noteReminderLayout.getVisibility() == View.VISIBLE) {
                                    isReminded = true;
                                } else {
                                    isReminded = false;
                                }
                                if (isReminded) {
                                    try {
                                        String[] arrDate = sReminderDate.split("\\.");
                                        String[] arrTime = sReminderTime.split(":");
                                        int day = Integer.parseInt(arrDate[0]);
                                        int month = Integer.parseInt(arrDate[1]);
                                        month = month - 1;
                                        int year = Integer.parseInt(arrDate[2]);
                                        int hour = Integer.parseInt(arrTime[0]);
                                        int min = Integer.parseInt(arrTime[1]);

                                        Boolean isValidTime = CheckIsValidTimeToReminder(day, month, year, hour, min);
                                        if (isValidTime) {
                                            saveNotesFunc();
                                        } else {
                                            Toast toast = Toast.makeText(NotesActivity.this, "Cannot set reminder for previous date or time", Toast.LENGTH_LONG);
                                            toast.show();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    isReminded = false;
                                    saveNotesFunc();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(NotesActivity.this, "Note is empty", Toast.LENGTH_LONG).show();
                }
            } else if (view.getId() == R.id.cancelBtn) {
                notesBackgroundLayout.setVisibility(View.GONE);
                noteReminderLayout.setVisibility(View.GONE);
                clearText();
                hideSoftKeyboard();
            }
//            else if (view.getId() == R.id.notesBackgroundLayout) {
//                notesBackgroundLayout.setVisibility(View.GONE);
//            }
            else if (view.getId() == R.id.addNoteImg) {
                isNoteEdit = false;
                isReminded = false;
                clearText();
                noteReminderLayout.setVisibility(View.GONE);
                notesBackgroundLayout.setVisibility(View.VISIBLE);

            } else if (view.getId() == R.id.remindmeTv) {
                try {
                    setDefaultDateAndTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (view.getId() == R.id.reminderDateLayout) {
                pickReminderDate();

            } else if (view.getId() == R.id.reminderTimeLayout) {
                pickReminderTime();
            }
        }
    };

    private void pickReminderDate() {
        final String[] reminderDate = {""};
        try {

            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            if (isNoteEdit && isNoteHaveReminder) {
                mYear = noteEditReminderCalendar.get(noteEditReminderCalendar.YEAR);
                mMonth = noteEditReminderCalendar.get(noteEditReminderCalendar.MONTH);
                mDay = noteEditReminderCalendar.get(noteEditReminderCalendar.DAY_OF_MONTH);
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String monthName = "";
                            String[] monthArr = getResources().getStringArray(R.array.english_month);
                            for (int i = 0; i < monthArr.length; i++) {
                                try {
                                    if (monthOfYear == i) {
                                        monthName = monthArr[i];
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            reminderDate[0] = dayOfMonth + " " + monthName + " " + year;
                            reminderDateTv.setText(reminderDate[0]);

                            sReminderDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;

                            pickReminderTime();

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pickReminderTime() {
        final String[] reminderTime = {""};
        try {

            final Calendar calendar = Calendar.getInstance();
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = calendar.get(Calendar.MINUTE);

            int delayMinute = mMinute + 5;

            if (delayMinute > 59) {
                mHour = mHour + 1;
                mMinute = mMinute - delayMinute;
            } else {
                mMinute = mMinute + 5;
            }


            if (isNoteEdit && isNoteHaveReminder) {
                if (calendar.getTimeInMillis() < noteEditReminderCalendar.getTimeInMillis()) {
                    mHour = noteEditReminderCalendar.get(Calendar.HOUR_OF_DAY);
                    mMinute = noteEditReminderCalendar.get(Calendar.MINUTE);
                }
            }

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hour,
                                              int minute) {

                            int hourOfDay = hour;

                            if (hourOfDay > 12) {
                                hourOfDay = hourOfDay - 12;
                                String Hour = String.valueOf(hourOfDay);
                                String Min = String.valueOf(minute);
                                if (hourOfDay < 10) {
                                    Hour = "0" + hourOfDay;
                                }
                                if (minute < 10) {
                                    Min = "0" + minute;
                                }

                                reminderTime[0] = Hour + ":" + Min + " " + "pm";
                                sAmPm = "pm";
                                reminderTimeTv.setText(reminderTime[0]);
                                sReminderTime = hour + ":" + minute;
                            } else {
                                String Hour = String.valueOf(hourOfDay);
                                String Min = String.valueOf(minute);
                                if (hourOfDay < 10) {
                                    Hour = "0" + hourOfDay;
                                }
                                if (minute < 10) {
                                    Min = "0" + minute;
                                }

                                if (hourOfDay == 12) {
                                    reminderTime[0] = Hour + ":" + Min + " " + "pm";
                                    reminderTimeTv.setText(reminderTime[0]);
                                    sReminderTime = hourOfDay + ":" + minute;
                                    sAmPm = "pm";
                                } else {
                                    reminderTime[0] = Hour + ":" + Min + " " + "am";
                                    reminderTimeTv.setText(reminderTime[0]);
                                    sReminderTime = hour + ":" + minute;
                                    sAmPm = "am";
                                }
                            }

                            remindedDateAndTimeTv.setText(reminderDateTv.getText().toString() + "  -  " + reminderTimeTv.getText().toString());

//                            if (noteReminderLayout.getVisibility() == View.VISIBLE) {
//                                noteReminderLayout.setVisibility(View.GONE);
//                            } else {
                                noteReminderLayout.setVisibility(View.VISIBLE);
//                            }


                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearText() {
        try {
            if (titleEdtTv != null) {
                titleEdtTv.getText().clear();
                descEdtTv.getText().clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private long getNoteId() {
        long id = 0;
        Calendar calendar = Calendar.getInstance();
        id = calendar.getTimeInMillis();
        return id;
    }

    private String getTime() {
        String time = "";
        Calendar calendar = Calendar.getInstance();
        int Hour = calendar.get(Calendar.HOUR_OF_DAY);
        int Min = calendar.get(Calendar.MINUTE);
        String strAmPm = String.valueOf(calendar.get(Calendar.AM_PM));

        String strHour = "";
        String strMin;
        if (Hour > 12) {
            int hr = Hour - 12;
            if (hr < 10) {
                strHour = "0" + hr;
            } else {
                strHour = String.valueOf(hr);
            }
        } else {
            if (Hour < 10) {
                strHour = "0" + Hour;
            } else {
                strHour = String.valueOf(Hour);
            }
        }
        if (Min < 10) {
            strMin = "0" + Min;
        } else {
            strMin = String.valueOf(Min);
        }
        if (strAmPm.equalsIgnoreCase("0")) {
            strAmPm = "am";
            sAmPm = "am";
        } else {
            strAmPm = "pm";
            sAmPm = "pm";
        }

        time = strHour + ":" + strMin + " " + strAmPm;
        return time;
    }

    private Boolean CheckIsValidTimeToReminder(int iDay, int iMonth, int iYear, int iHour, int iMin) {
        Boolean isValidTime = false;
        try {
            Calendar crntDate = Calendar.getInstance();
            long crntTime = crntDate.getTimeInMillis();


            Calendar remindedDate = Calendar.getInstance();
            remindedDate.set(DAY_OF_MONTH, iDay);
            remindedDate.set(Calendar.MONTH, iMonth);
            remindedDate.set(Calendar.YEAR, iYear);
            remindedDate.set(Calendar.HOUR_OF_DAY, iHour);
            remindedDate.set(Calendar.MINUTE, iMin);

            long remindedTime = remindedDate.getTimeInMillis();

            if (remindedTime > crntTime) {
                isValidTime = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValidTime;
    }

    private void saveNotesFunc() {
        try {
            isNoteAdd = true;
            String strTitle = String.valueOf(titleEdtTv.getText());
            String strDesc = String.valueOf(descEdtTv.getText());
            String noteID = String.valueOf(getNoteId());
            String strTime = getTime();

            String noteDateAndTime = strDay + " " + strMonth + " " + strYear + " " + strTime;
            String reminderDate = reminderDateTv.getText().toString() + " " + reminderTimeTv.getText().toString();
            String noteDateAndTimeOrg = sReminderDate + "-" + sReminderTime;
            String dateToAddNotes = sReminderDate;


            if (!isReminded) {
                reminderDate = "";
                noteDateAndTimeOrg = "";
                dateToAddNotes = iDay + "." + (iMonth + 1) + "." + iYear;
            } else {
                noteDateAndTime = strDay + " " + strMonth + " " + strYear + " " + strTime;
                reminderDate = reminderDateTv.getText().toString() + " " + reminderTimeTv.getText().toString();
                noteDateAndTimeOrg = sReminderDate + "-" + sReminderTime;
                dateToAddNotes = sReminderDate;
            }


            db.addNotes(dateToAddNotes, noteID, strTitle, strDesc, noteDateAndTime, reminderDate, noteDateAndTimeOrg);
            if (isReminded) {
                String[] arrDate = sReminderDate.split("\\.");
                String[] arrTime = sReminderTime.split(":");
                int day = Integer.parseInt(arrDate[0]);
                int month = Integer.parseInt(arrDate[1]);
                int year = Integer.parseInt(arrDate[2]);
                int hour = Integer.parseInt(arrTime[0]);
                int min = Integer.parseInt(arrTime[1]);

                setAlarmAction(year, month, day, hour, min, sReminderDate);
            }
            arrNotesList = db.getNotes();
            notesBackgroundLayout.setVisibility(View.GONE);
            noteReminderLayout.setVisibility(View.GONE);
            recyclerAdapter.notifyDataSetChanged();
            colorId = 0;
            clearText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void noteEditFunc() {
        try {
            String strTime = getTime();
            String noteDateAndTime = strDay + " " + strMonth + " " + strYear + " " + strTime;
            String strNewTitle = String.valueOf(titleEdtTv.getText());
            String strNewDesc = String.valueOf(descEdtTv.getText());

            String reminderDate = reminderDateTv.getText().toString() + " " + reminderTimeTv.getText().toString();
            String noteDateAndTimeOrg = sReminderDate + "-" + sReminderTime;
            String dateToAddNotes = sReminderDate;


            if (!isReminded) {
                reminderDate = "";
                noteDateAndTimeOrg = "";
                dateToAddNotes = iDay + "." + (iMonth + 1) + "." + iYear;
            } else {
                reminderDate = reminderDateTv.getText().toString() + " " + reminderTimeTv.getText().toString();
                noteDateAndTimeOrg = sReminderDate + "-" + sReminderTime;
                dateToAddNotes = sReminderDate;
            }

            db.editNotes(strOldNoteId, dateToAddNotes, strNewTitle, strNewDesc, noteDateAndTime, reminderDate, noteDateAndTimeOrg);
            if (isReminded) {
                try {
                    String[] arrDate = sReminderDate.split("\\.");
                    String[] arrTime = sReminderTime.split(":");
                    int day = Integer.parseInt(arrDate[0]);
                    int month = Integer.parseInt(arrDate[1]);
                    int year = Integer.parseInt(arrDate[2]);
                    int hour = Integer.parseInt(arrTime[0]);
                    int min = Integer.parseInt(arrTime[1]);

                    setAlarmAction(year, month, day, hour, min, sReminderDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            arrNotesList = db.getNotes();
            clearText();
            notesBackgroundLayout.setVisibility(View.GONE);
            noteReminderLayout.setVisibility(View.GONE);
            recyclerAdapter.notifyDataSetChanged();
            colorId = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAnimationFunc(View view) {
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(NotesActivity.this, view);
            zoomAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    try {
                        onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setNativeSmall() {
        try {
            try {
                String strId = getNativeSmall;
                if (MiddlewareInterface.bAdFree)
                    return;
                viewNativeAd = new NativeExpressAdView(this);
                viewNativeAd.setAdSize(AdSize.LARGE_BANNER);
                viewNativeAd.setAdUnitId(strId);
                viewNativeAd.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearad.addView(viewNativeAd);
                AdRequest.Builder adRequestBuilder = new AdRequest.Builder();
                viewNativeAd.loadAd(adRequestBuilder.build());
            } catch (Exception e) {
                // TODO: handle exception
            }

            viewNativeAd.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // TODO Auto-generated method stub
                    Log.d("On Fail called", "Ad");
                    linearad.setVisibility(View.GONE);
                    super.onAdFailedToLoad(errorCode);
                }

                @Override
                public void onAdLoaded() {
                    // TODO Auto-generated method stub
                    Log.d("On Load called", "Ad");
                    linearad.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    private void setAdvertise() {
        try {
            try {
                String strGoogleAd = getBannerAd;
                if (MiddlewareInterface.bAdFree)
                    return;

                adview = new AdView(this);
                adview.setAdSize(AdSize.BANNER);
                adview.setAdUnitId(strGoogleAd);
                adview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                linearad.addView(adview);
                adview.loadAd(new AdRequest.Builder().build());
            } catch (Exception e) {
                // TODO: handle exception
            }
            adview.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // TODO Auto-generated method stub
                    Log.d("On Fail called", "Ad");
                    linearad.setVisibility(View.GONE);
                    super.onAdFailedToLoad(errorCode);
                }

                @Override
                public void onAdLoaded() {
                    // TODO Auto-generated method stub
                    Log.d("On Load called", "Ad");
                    linearad.setVisibility(View.VISIBLE);
                    super.onAdLoaded();
                }
            });

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        int lastItemPosition = 0;
        int showItemPosition = -1;
        Context mContext;

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView TitleTv, DescTv, DateAndTimeTv, reminderDateAndTimeTv;
            CardView noteListCardView;
            ImageView optionsMenuBtn, deleteNoteBtn, editNoteBtn, cancelNoteBtn, reminderIcon;
            RelativeLayout menuOptionLayout, NoteMainLayout, noteOptionsLayout,noteDescLayout,noteTitleLayout;
            View viewLine;

            public ViewHolder(View view) {
                super(view);
                TitleTv = (TextView) view.findViewById(R.id.titleNoteTv);
                DescTv = (TextView) view.findViewById(R.id.descNoteTv);
                optionsMenuBtn = (ImageView) view.findViewById(R.id.menuOptionBtn);
                deleteNoteBtn = (ImageView) view.findViewById(R.id.noteDeleteBtn);
                editNoteBtn = (ImageView) view.findViewById(R.id.noteEditBtn);
                cancelNoteBtn = (ImageView) view.findViewById(R.id.noteCancelBtn);
                reminderIcon = (ImageView) view.findViewById(R.id.reminderIcon);
                noteListCardView = (CardView) view.findViewById(R.id.noteListCardView);
                noteOptionsLayout = (RelativeLayout) view.findViewById(R.id.noteOptionsLayout);
                DateAndTimeTv = (TextView) view.findViewById(R.id.noteAddedDateAndTimeTv);
                reminderDateAndTimeTv = (TextView) view.findViewById(R.id.reminderDateAndTimeTv);
                menuOptionLayout = view.findViewById(R.id.menuOptionLayout);
                noteDescLayout = view.findViewById(R.id.noteDescLayout);
                noteTitleLayout = view.findViewById(R.id.noteTitleLayout);
                viewLine = view.findViewById(R.id.view);
                NoteMainLayout = view.findViewById(R.id.NoteMainLayout);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + TitleTv.getText();
            }
        }

        public RecyclerAdapter(Context context) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                mContext = context;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view = null;
            try {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_recycler_adapter_layout, parent, false);
                view.setBackgroundResource(mBackground);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int itemPosition) {
            try {
                final ArrayList<String> noteDetailList = arrNotesList.get(itemPosition);
                holder.TitleTv.setText(noteDetailList.get(2));
                holder.DescTv.setText(noteDetailList.get(3));
//                holder.DateAndTimeTv.setText(noteDetailList.get(4));

                if(noteDetailList.get(3).equalsIgnoreCase("")){
                    holder.noteDescLayout.setVisibility(View.GONE);
                    holder.viewLine.setVisibility(View.GONE);

                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.noteTitleLayout.getLayoutParams();
                    lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                    holder.noteTitleLayout.setLayoutParams(lp);
                }

                if (arrNotesList.size() == 0) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.NoteMainLayout.getLayoutParams();
                    lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                    holder.NoteMainLayout.setLayoutParams(lp);
                }

                try {
                    if (colorId == 0) {
                        holder.noteListCardView.setCardBackgroundColor(Color.parseColor("#f0e8e6"));
                        colorId = colorId + 1;
                    } else if (colorId == 1) {
                        holder.noteListCardView.setCardBackgroundColor(Color.parseColor("#f7d468"));
                        colorId = colorId + 1;
                    } else if (colorId == 2) {
                        holder.noteListCardView.setCardBackgroundColor(Color.parseColor("#fad1c5"));
                        colorId = colorId + 1;
                    } else if (colorId == 3) {
                        holder.noteListCardView.setCardBackgroundColor(Color.parseColor("#d4b1f8"));
                        colorId = 0;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!noteDetailList.get(5).equalsIgnoreCase("")) {
                    holder.reminderDateAndTimeTv.setText(noteDetailList.get(5));
                    holder.reminderDateAndTimeTv.setVisibility(View.VISIBLE);
                    holder.reminderIcon.setVisibility(View.VISIBLE);
                } else {
                    holder.reminderDateAndTimeTv.setVisibility(View.VISIBLE);
                    holder.reminderDateAndTimeTv.setText(noteDetailList.get(4));
                    holder.reminderIcon.setVisibility(View.GONE);
                }

                try {
                    String[] noteDateAndTime = noteDetailList.get(6).split("-");
                    String[] arrDate = noteDateAndTime[0].split("\\.");
                    String[] arrTime = noteDateAndTime[1].split(":");

                    sReminderDate = noteDateAndTime[0];
                    sReminderTime = noteDateAndTime[1];

                    remindedDateAndTimeTv.setText(noteDetailList.get(5));

                    int day = Integer.parseInt(arrDate[0]);
                    int month = Integer.parseInt(arrDate[1]);
                    month = month - 1;
                    int year = Integer.parseInt(arrDate[2]);
                    int hour = Integer.parseInt(arrTime[0]);
                    int min = Integer.parseInt(arrTime[1]);

                    Calendar crntDate = Calendar.getInstance();
                    long crntTime = crntDate.getTimeInMillis();


                    Calendar remindedDate = Calendar.getInstance();
                    remindedDate.set(DAY_OF_MONTH, day);
                    remindedDate.set(Calendar.MONTH, month);
                    remindedDate.set(Calendar.YEAR, year);
                    remindedDate.set(Calendar.HOUR_OF_DAY, hour);
                    remindedDate.set(Calendar.MINUTE, min);

                    long remindedTime = remindedDate.getTimeInMillis();

                    if (remindedTime < crntTime) {
                        holder.reminderDateAndTimeTv.setVisibility(View.GONE);
                        holder.reminderIcon.setVisibility(View.GONE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (showItemPosition != -1 && showItemPosition == itemPosition) {
                    holder.noteOptionsLayout.setVisibility(View.VISIBLE);
//                    recyclerView.smoothScrollToPosition(showItemPosition);
                } else {
                    holder.noteOptionsLayout.setVisibility(View.GONE);
                }

                if (isNoteEdit == true && showItemPosition == itemPosition) {
                    holder.noteOptionsLayout.setVisibility(View.GONE);
                    isNoteEdit = false;
                }
                if (isNoteAdd == true && showItemPosition == itemPosition) {
                    holder.noteOptionsLayout.setVisibility(View.GONE);
                    isNoteAdd = false;
                }

                holder.NoteMainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(NotesActivity.this, v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        if (lastItemPosition == itemPosition) {
                                            if (holder.noteOptionsLayout.getVisibility() == View.VISIBLE) {
                                                holder.noteOptionsLayout.setVisibility(View.GONE);
                                            } else {
                                                holder.noteOptionsLayout.setVisibility(View.VISIBLE);
                                            }
                                            lastItemPosition = itemPosition;
                                        } else if (lastItemPosition != itemPosition) {
                                            showItemPosition = itemPosition;
                                            lastItemPosition = itemPosition;
                                            recyclerAdapter.notifyDataSetChanged();
                                            colorId = 0;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                });
                holder.deleteNoteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(NotesActivity.this, v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        holder.noteOptionsLayout.setVisibility(View.GONE);
                                        String strNoteId = noteDetailList.get(0);
                                        db.deleteNotes(strNoteId);
                                        arrNotesList = db.getNotes();
                                        recyclerAdapter.notifyDataSetChanged();
                                        colorId = 0;
                                        showItemPosition = -1;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                holder.editNoteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(NotesActivity.this, v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        holder.noteOptionsLayout.setVisibility(View.GONE);
                                        isNoteEdit = true;
                                        isNoteHaveReminder = false;
                                        isAlreadyReminded = false;
                                        strOldNoteId = noteDetailList.get(0);
                                        strOldNoteTitle = String.valueOf(holder.TitleTv.getText());
                                        strOldNoteDescription = String.valueOf(holder.DescTv.getText());

                                        if (holder.reminderIcon.getVisibility() == View.GONE) {
                                            isNoteHaveReminder = false;
                                            isAlreadyReminded = false;
                                            noteReminderLayout.setVisibility(View.GONE);
                                        } else {

                                            if (!noteDetailList.get(5).equalsIgnoreCase("")) {
                                                noteReminderLayout.setVisibility(View.VISIBLE);
                                                isNoteHaveReminder = true;
                                                isAlreadyReminded = true;

                                                String[] arrReminderValue = noteDetailList.get(6).split("-");
                                                String[] arrDate = arrReminderValue[0].split("\\.");
                                                String[] arrTime = arrReminderValue[1].split(":");
                                                int day = Integer.parseInt(arrDate[0]);
                                                int month = Integer.parseInt(arrDate[1]);
                                                month = month - 1;
                                                int year = Integer.parseInt(arrDate[2]);
                                                int hour = Integer.parseInt(arrTime[0]);
                                                int min = Integer.parseInt(arrTime[1]);

                                                noteEditReminderCalendar = Calendar.getInstance();
                                                noteEditReminderCalendar.set(DAY_OF_MONTH, day);
                                                noteEditReminderCalendar.set(Calendar.MONTH, month);
                                                noteEditReminderCalendar.set(Calendar.YEAR, year);
                                                noteEditReminderCalendar.set(Calendar.HOUR_OF_DAY, hour);
                                                noteEditReminderCalendar.set(Calendar.MINUTE, min);

                                                String[] arrReminderDateAndTIme = noteDetailList.get(5).split(" ");

                                                reminderDateTv.setText(arrReminderDateAndTIme[0] + " " + arrReminderDateAndTIme[1] + " " + arrReminderDateAndTIme[2]);
                                                reminderTimeTv.setText(arrReminderDateAndTIme[3] + " " + arrReminderDateAndTIme[4]);
                                            } else {
                                                setDefaultDateAndTime();
                                            }
                                        }

                                        titleEdtTv.setText(strOldNoteTitle);
                                        descEdtTv.setText(strOldNoteDescription);
                                        notesBackgroundLayout.setVisibility(View.VISIBLE);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
                holder.cancelNoteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(NotesActivity.this, v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        holder.noteOptionsLayout.setVisibility(View.GONE);
                                        noteReminderLayout.setVisibility(View.GONE);
                                        showItemPosition = -1;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onAnimationCancel(Animator animator) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animator) {
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return arrNotesList.size();
        }
    }

    @Override
    public void onBackPressed() {
        if (notesBackgroundLayout.getVisibility() == View.VISIBLE) {
            notesBackgroundLayout.setVisibility(View.GONE);
        } else {
            MiddlewareInterface.isNotesIntent = false;
            Intent intent = new Intent();
            setResult(11, intent);
            finish();//finishing activity
            super.onBackPressed();
        }

    }

    private void setDefaultDateAndTime() {
        try {
            Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            int mHour = calendar.get(Calendar.HOUR_OF_DAY);
            int mMinute = calendar.get(Calendar.MINUTE);

            int delayMinute = mMinute + 5;

            if (delayMinute > 59) {
                mHour = mHour + 1;
                mMinute = delayMinute - mMinute;
            } else {
                mMinute = mMinute + 5;
            }

            String monthName = "";
            String[] monthArr = getResources().getStringArray(R.array.english_month);
            for (int i = 0; i < monthArr.length; i++) {
                try {
                    if (mMonth == i) {
                        monthName = monthArr[i];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            reminderDateTv.setText(mDay + " " + monthName + " " + mYear);
            sReminderDate = mDay + "." + (mMonth + 1) + "." + mYear;
            sReminderTime = mHour + ":" + mMinute;

            if (mHour > 12) {
                mHour = mHour - 12;
                String Hour = String.valueOf(mHour);
                String Min = String.valueOf(mMinute);
                if (mHour < 10) {
                    Hour = "0" + mHour;
                }
                if (mMinute < 10) {
                    Min = "0" + mMinute;
                }

//                        reminderTime[0] = Hour + ":" + Min+" "+"pm";
                reminderTimeTv.setText(Hour + ":" + Min + " " + "pm");
                sAmPm = "pm";
            } else {
                String Hour = String.valueOf(mHour);
                String Min = String.valueOf(mMinute);
                if (mHour < 10) {
                    Hour = "0" + mHour;
                }
                if (mMinute < 10) {
                    Min = "0" + mMinute;
                }

                if (mHour == 12) {
                    reminderTimeTv.setText(Hour + ":" + Min + " " + "pm");
                    sAmPm = "pm";
                } else {
                    reminderTimeTv.setText(Hour + ":" + Min + " " + "am");
                    sAmPm = "am";
                }

            }

            pickReminderDate();

//            if (noteReminderLayout.getVisibility() == View.VISIBLE) {
//                noteReminderLayout.setVisibility(View.GONE);
//            } else {
//                noteReminderLayout.setVisibility(View.VISIBLE);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAlarmAction(int year, int month, int date, int hour, int minute, String strDate) {
        try {
            String strTitleNote = String.valueOf(titleEdtTv.getText());
            String strDescriptionNote = String.valueOf(descEdtTv.getText());

            Calendar crntcalendar1 = Calendar.getInstance();
            Calendar crntcalendar = Calendar.getInstance();

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.YEAR, year);
            calendar.set(DAY_OF_MONTH, date);

            int crntYEAR = crntcalendar1.get(Calendar.YEAR);
            long crntmillis = crntcalendar.getTimeInMillis();

//            calendar.set(Calendar.HOUR_OF_DAY, hour);
            if (hour > 12) {
                hour = hour - 12;
            }
            calendar.set(Calendar.HOUR, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 5);

            try {
                int ampm = 0;
                if (sAmPm.equalsIgnoreCase("am"))
                    ampm = 0;
                else
                    ampm = 1;
                calendar.set(Calendar.AM_PM, ampm);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long millis = calendar.getTimeInMillis();
//          long millidelay = millis - crntmillis;
            long millidelay = 0;
            if (crntYEAR == SplashScreen.iFromYear && year == SplashScreen.iFromYear)
                millidelay = millis - crntmillis;
            else if (crntYEAR == SplashScreen.iToYear && year == SplashScreen.iToYear)
                millidelay = millis - crntmillis;
            else {
                millidelay = millis;
            }
            int seconds = (int) millidelay;
            Notification notification = getNotification(strTitleNote, strDescriptionNote, calendar);
            String strTitle = titleEdtTv.getText().toString();
            String strDesc = descEdtTv.getText().toString();
            scheduleNotification(notification, millidelay, strTitle, strDesc, strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleNotification(Notification notification, long delay, String strTitle, String strDesc, String strDate) {
        try {
            Intent notificationIntent = new Intent(this, NotificationPublisher.class);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(NotesActivity.this);
            int notificId = prefs.getInt("notifID", 0);

            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION1, notificId);
            SharedPreferences.Editor prefEditor = prefs.edit();
            prefEditor.putInt("notifID", notificId + 1);
            prefEditor.apply();
            notificationIntent.putExtra("Title", strTitle);
            notificationIntent.putExtra("Desc", strDesc);
            notificationIntent.putExtra("date", strDate);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

//        long futureInMillis = calendar.getTimeInMillis();
            long futureInMillis = SystemClock.elapsedRealtime() + delay;
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Notification getNotification(String ntitle, String ndesc, Calendar calendar) {
        Resources res = getApplicationContext().getResources();
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(ntitle);
        builder.setContentText(ndesc);
        builder.setSmallIcon(getNotificationIcon());
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                builder.setChannelId("ID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        builder.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher));
        Intent notificationIntent = null;
        notificationIntent = new Intent(getApplicationContext(), NotesActivity.class);
        notificationIntent.putExtra("local", true);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        builder.setWhen(calendar.getTimeInMillis());
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        return builder.build();
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.ic_launcher : R.drawable.ic_launcher;
    }


}
