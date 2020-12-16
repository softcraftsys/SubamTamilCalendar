package com.softcraft.calendar.Activity;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ReminderNotification.NotificationPublisher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.softcraft.calendar.DevotionalWallpapers.DevotionalWallpepersPreviewActivity.REQUEST_WRITE_PERMISSION;
import static java.util.Calendar.DAY_OF_MONTH;

public class UserDetailActivity extends AppCompatActivity {

    EditText userNameEdtTv, userDobEdtTv, userRasiEdtTv, userStarEdtTv;
    TextView userInfoSkipBtn, RasiTitleTv;
    Button userInfoOkBtn;
    RecyclerView RaasiGridRecycler, NaatchathiramGridRecycler;
    public int RaasiRecyclerPos = -1;
    RelativeLayout RaasiNatchaMainLayout;
    public ArrayList<HashMap<String, String>> UtilityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        try {
            InitItems();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitItems() {
        try {

            userNameEdtTv = findViewById(R.id.userNameEdtTv);
            userDobEdtTv = findViewById(R.id.userDobEdtTv);
            userRasiEdtTv = findViewById(R.id.userRasiEdtTv);
            userStarEdtTv = findViewById(R.id.userStarEdtTv);
            RaasiNatchaMainLayout = findViewById(R.id.RaasiNatchaMainLayout);
            RasiTitleTv = findViewById(R.id.RasiTitleTv);

            RaasiGridRecycler = findViewById(R.id.RaasiRecyclerView);
            NaatchathiramGridRecycler = findViewById(R.id.NatchathiramRecyclerView);

            userInfoSkipBtn = findViewById(R.id.userInfoSkipBtn);
            userInfoOkBtn = findViewById(R.id.userInfoOkBtn);

            userInfoOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemOnlickAnim(view);
                }
            });
            userInfoSkipBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ItemOnlickAnim(view);
                }
            });

            InitializeRaasiRecycler();


            userRasiEdtTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideKeyboard(UserDetailActivity.this);

                    RasiTitleTv.setText("உங்கள் ராசி");
                    RaasiNatchaMainLayout.setVisibility(View.VISIBLE);
                    RaasiGridRecycler.setVisibility(View.VISIBLE);
                    NaatchathiramGridRecycler.setVisibility(View.GONE);

                }
            });
            userStarEdtTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideKeyboard(UserDetailActivity.this);

                    if (RaasiRecyclerPos != -1) {
                        String[] rasiArr = getResources().getStringArray(R.array.horoscope_title);
                        RasiTitleTv.setText(rasiArr[RaasiRecyclerPos] + " - " + "உங்கள் நட்சத்திரம்");
                        InitializeNatchathiramRecycler();
                        NaatchathiramGridRecycler.setVisibility(View.VISIBLE);
                        RaasiGridRecycler.setVisibility(View.GONE);

                    } else {
                        RaasiGridRecycler.setVisibility(View.VISIBLE);
                        NaatchathiramGridRecycler.setVisibility(View.GONE);
                    }
                    RaasiNatchaMainLayout.setVisibility(View.VISIBLE);
                }
            });
            RaasiNatchaMainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RaasiNatchaMainLayout.setVisibility(View.GONE);
                }
            });

            userDobEdtTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hideKeyboard(UserDetailActivity.this);
                    showDatePicker();
                }
            });

            UtilityArrayList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("UtilityArray");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ItemOnlickAnim(final View view) {
        try {
            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(getApplicationContext(), view);
            zoomAnimation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    try {
                        if (view.getId() == R.id.userInfoSkipBtn) {
                            skipUserInfo();
                        } else if (view.getId() == R.id.userInfoOkBtn) {
                            saveUserInfo();
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

    private void showDatePicker() {
        try {

            final Calendar calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

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

//                            reminderDate[0] = dayOfMonth + " - " + monthName + " - " + year;
//                            reminderDateTv.setText(reminderDate[0]);

                            userDobEdtTv.setText(dayOfMonth + " " + (monthName) + " " + year);
                            setAlarmAction(monthOfYear, dayOfMonth);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            datePickerDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void saveUserInfo() {
        try {
            Boolean isFilled = true;

            for (int i = 0; i < 4; i++) {

                if (userNameEdtTv.getText().toString().equalsIgnoreCase("")) {
                    isFilled = false;
                    break;
                }
                if (userDobEdtTv.getText().toString().equalsIgnoreCase("")) {
                    isFilled = false;
                    break;
                }
                if (userRasiEdtTv.getText().toString().equalsIgnoreCase("")) {
                    isFilled = false;
                    break;
                }
                if (userStarEdtTv.getText().toString().equalsIgnoreCase("")) {
                    isFilled = false;
                    break;
                }

            }

            if (isFilled) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("userName", userNameEdtTv.getText().toString());
                editor.putString("userDOB", userDobEdtTv.getText().toString());
                editor.putString("userRasi", userRasiEdtTv.getText().toString());
                editor.putString("userNatchathiram", userStarEdtTv.getText().toString());
                editor.putBoolean("showUserEntry", false);
                editor.apply();

                skipUserInfo();

            } else {
                Toast toast = Toast.makeText(UserDetailActivity.this, "Please fill all fields", Toast.LENGTH_LONG);
                toast.show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void skipUserInfo() {
        try {

            int catId = getIntent().getIntExtra("categoryId", -1);
            int pagerPos = getIntent().getIntExtra("viewpagerPos", -1);
            String rasipalan = getIntent().getStringExtra("rasipalan");
            String shortcutKey = getIntent().getStringExtra("shortcutKey");

            Intent intent = new Intent(getApplicationContext(), GridMenuActivity.class);
            intent.putExtra("UtilityArray", UtilityArrayList);
            intent.putExtra("categoryId", catId);
            intent.putExtra("viewpagerPos", pagerPos);
            intent.putExtra("rasipalan", rasipalan);
            if (shortcutKey != null && !shortcutKey.equalsIgnoreCase(""))
                intent.putExtra("shortcutKey", shortcutKey);
            startActivity(intent);
            UserDetailActivity.this.finish();
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("showUserEntry", false);
            editor.apply();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAlarmAction(int month, int date) {
        try {
            String strTitleNote = "Hi" + " " + userNameEdtTv.getText().toString();
            String strDescriptionNote = "இனிய பிறந்தநாள் நல்வாழ்த்துக்கள்";

            Calendar crntcalendar1 = Calendar.getInstance();
            Calendar crntcalendar = Calendar.getInstance();

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MONTH, month - 1);
            calendar.set(Calendar.YEAR, crntcalendar1.get(Calendar.YEAR));
            calendar.set(DAY_OF_MONTH, date);

            int crntYEAR = crntcalendar1.get(Calendar.YEAR);
            int crntMonth = crntcalendar1.get(Calendar.MONTH);

            long crntmillis = crntcalendar.getTimeInMillis();

//            calendar.set(Calendar.HOUR_OF_DAY, hour);

            calendar.set(Calendar.HOUR, 6);
            calendar.set(Calendar.MINUTE, 0);

            try {
                calendar.set(Calendar.AM_PM, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            long millis = calendar.getTimeInMillis();

            long millidelay = 0;
            if (millis > crntcalendar.getTimeInMillis()) {
                millidelay = millis - crntmillis;
            } else {
                crntcalendar.set(Calendar.MONTH, month - 1);
                crntcalendar.set(Calendar.YEAR, (crntcalendar1.get(Calendar.YEAR) + 1));
                crntcalendar.set(DAY_OF_MONTH, date);

                millidelay = crntcalendar1.getTimeInMillis() - crntcalendar.getTimeInMillis();
            }

            Notification notification = getNotification(strTitleNote, strDescriptionNote, calendar);

            scheduleNotification(notification, millidelay, strTitleNote, strDescriptionNote);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void scheduleNotification(Notification notification, long delay, String strTitle, String strDesc) {
        try {
            Intent notificationIntent = new Intent(this, NotificationPublisher.class);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(UserDetailActivity.this);
            int notificId = prefs.getInt("notifID", 0);

            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION1, notificId);
            SharedPreferences.Editor prefEditor = prefs.edit();
            prefEditor.putInt("notifID", notificId + 1);
            prefEditor.apply();
            notificationIntent.putExtra("Title", strTitle);
            notificationIntent.putExtra("Desc", strDesc);
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

    private void InitializeRaasiRecycler() {
        try {
            GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 4, GridLayoutManager.VERTICAL, false);
            RaasiGridRecycler.setLayoutManager(mLayoutManager);
            String[] raasiArr = getResources().getStringArray(R.array.raasi_Array);
            RasiRecyclerAdapter recyclerAdapter = new RasiRecyclerAdapter(getApplicationContext(), true, raasiArr);
            RaasiGridRecycler.setAdapter(recyclerAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitializeNatchathiramRecycler() {
        try {

            String[] starsArr = null;
            if (RaasiRecyclerPos == 0) {
                starsArr = getResources().getStringArray(R.array.Mesha_stars);
            } else if (RaasiRecyclerPos == 1) {
                starsArr = getResources().getStringArray(R.array.Vrishabha_stars);
            } else if (RaasiRecyclerPos == 2) {
                starsArr = getResources().getStringArray(R.array.Mithuna_stars);
            } else if (RaasiRecyclerPos == 3) {
                starsArr = getResources().getStringArray(R.array.Kataka_stars);
            } else if (RaasiRecyclerPos == 4) {
                starsArr = getResources().getStringArray(R.array.Simha_stars);
            } else if (RaasiRecyclerPos == 5) {
                starsArr = getResources().getStringArray(R.array.Kanya_stars);
            } else if (RaasiRecyclerPos == 6) {
                starsArr = getResources().getStringArray(R.array.Thula_stars);
            } else if (RaasiRecyclerPos == 7) {
                starsArr = getResources().getStringArray(R.array.Vrischika_stars);
            } else if (RaasiRecyclerPos == 8) {
                starsArr = getResources().getStringArray(R.array.Dhanus_stars);
            } else if (RaasiRecyclerPos == 9) {
                starsArr = getResources().getStringArray(R.array.Makara_stars);
            } else if (RaasiRecyclerPos == 10) {
                starsArr = getResources().getStringArray(R.array.Kumbha_stars);
            } else if (RaasiRecyclerPos == 11) {
                starsArr = getResources().getStringArray(R.array.Meena_stars);
            }

            GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
            NaatchathiramGridRecycler.setLayoutManager(mLayoutManager);
            RasiRecyclerAdapter recyclerAdapter = new RasiRecyclerAdapter(getApplicationContext(), false, starsArr);
            NaatchathiramGridRecycler.setAdapter(recyclerAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class RasiRecyclerAdapter extends RecyclerView.Adapter<RasiRecyclerAdapter.ViewHolder> {
        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        int lastItemPosition = 0;
        int showItemPosition = -1;
        Context mContext;
        Boolean isRaasi = false;
        String[] contentList;

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView ItemImgView;
            TextView ItemTxtView;

            public ViewHolder(View view) {
                super(view);
                ItemImgView = (ImageView) view.findViewById(R.id.itemsImgView);
                ItemTxtView = (TextView) view.findViewById(R.id.itemsTxtView);
            }

//            @Override
//            public String toString() {
//                return super.toString() + " '" + TitleTv.getText();
//            }
        }

        public RasiRecyclerAdapter(Context context, Boolean isRaasiBool, String[] arrContent) {
            try {
                context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
                mBackground = mTypedValue.resourceId;
                mContext = context;
                isRaasi = isRaasiBool;
                contentList = arrContent;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            View view = null;
            try {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rasi_star_adapter_layout, parent, false);
                view.setBackgroundResource(mBackground);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ViewHolder(view);
        }

        private void initDrawableRasiImage(){
            try{

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int itemPosition) {
            try {

                if (isRaasi) {
                    String strImgName = "rasi" + (itemPosition + 1);
                    int id = mContext.getResources().getIdentifier(strImgName, "id", mContext.getPackageName());

                    if(itemPosition == 0){
                        holder.ItemImgView.setImageResource(R.drawable.rasi1);
                    }else if(itemPosition == 1){
                        holder.ItemImgView.setImageResource(R.drawable.rasi2);
                    } else if(itemPosition == 2){
                        holder.ItemImgView.setImageResource(R.drawable.rasi3);
                    } else if(itemPosition == 3){
                        holder.ItemImgView.setImageResource(R.drawable.rasi4);
                    } else if(itemPosition == 4){
                        holder.ItemImgView.setImageResource(R.drawable.rasi5);
                    } else if(itemPosition == 5){
                        holder.ItemImgView.setImageResource(R.drawable.rasi6);
                    } else if(itemPosition == 6){
                        holder.ItemImgView.setImageResource(R.drawable.rasi7);
                    } else if(itemPosition == 7){
                        holder.ItemImgView.setImageResource(R.drawable.rasi8);
                    } else if(itemPosition == 8){
                        holder.ItemImgView.setImageResource(R.drawable.rasi9);
                    } else if(itemPosition == 9){
                        holder.ItemImgView.setImageResource(R.drawable.rasi10);
                    } else if(itemPosition == 10){
                        holder.ItemImgView.setImageResource(R.drawable.rasi11);
                    } else if(itemPosition == 11){
                        holder.ItemImgView.setImageResource(R.drawable.rasi12);
                    }

                    holder.ItemImgView.setVisibility(View.VISIBLE);
                    holder.ItemTxtView.setVisibility(View.GONE);


                    if (RaasiGridRecycler != null)
                        RaasiGridRecycler.setVisibility(View.VISIBLE);

                    if (NaatchathiramGridRecycler != null)
                        NaatchathiramGridRecycler.setVisibility(View.GONE);

                } else {
                    holder.ItemImgView.setVisibility(View.GONE);
                    holder.ItemTxtView.setVisibility(View.VISIBLE);
                    holder.ItemTxtView.setText(contentList[itemPosition]);

                    if (RaasiGridRecycler != null)
                        RaasiGridRecycler.setVisibility(View.GONE);

                    if (NaatchathiramGridRecycler != null)
                        NaatchathiramGridRecycler.setVisibility(View.VISIBLE);
                }

                holder.ItemImgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UserDetailActivity.this, v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        RaasiRecyclerPos = itemPosition;
                                        userRasiEdtTv.setText(contentList[itemPosition]);
                                        userStarEdtTv.setText("");
                                        RaasiNatchaMainLayout.setVisibility(View.GONE);
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

                holder.ItemTxtView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(UserDetailActivity.this, v);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        userStarEdtTv.setText(contentList[itemPosition]);
                                        RaasiNatchaMainLayout.setVisibility(View.GONE);

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
            return contentList.length;
        }
    }

    @Override
    public void onBackPressed() {

        if (RaasiNatchaMainLayout.getVisibility() == View.VISIBLE) {
            RaasiNatchaMainLayout.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}
