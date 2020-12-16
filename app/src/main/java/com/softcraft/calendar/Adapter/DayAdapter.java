package com.softcraft.calendar.Adapter;

import android.animation.Animator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.softcraft.calendar.Activity.DayActivity;
import com.softcraft.calendar.Database.DataBaseHelper;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.R;
import com.softcraft.calendar.ServiceAndOthers.UnicodeUtil;
import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.softcraft.calendar.Activity.DayActivity.POS_KEY;
import static java.util.Calendar.DAY_OF_MONTH;

public class DayAdapter extends PagerAdapter {
    Context context;
    String getCurrentmonth;
    private String setWeekdayName;
    String SoolamTamilName;
    String setWeekday;
    String setCurrentWeek;
    String dayOfTheWeek;
    String englishdate;
    public static String DETAIL_COUNT = "detailCount";
    MiddlewareInterface AMI = MiddlewareInterface.GetInstance();
    private String showFasting, showFestival;
    String showKirthigai;
    private String showAmavasai;
    private String showPournami;
    private String showMugurtham;
    private static String df;
    public LayoutInflater layoutInflater;
    ArrayList<String> setOtherdetails, setDailyRasi, aCountDate, setYemaRagu, setTodayRasi;
    TextView tCurrentdate;
    RelativeLayout layoutTamilMonth;
    RelativeLayout layoutEngMonth;
    RelativeLayout layoutTamilDay;
    Calendar myCalendar;
    int size;
    TableLayout toDayRasi;
    TextView tMesham;
    TextView tRishabam;
    TextView tMithunam;
    TextView tKadakam;
    TextView tSimmam;
    TextView tKanni;
    TextView tThulam;
    TextView tViruchi;
    TextView tDhanusu;
    TextView tMagaram;
    TextView tKumbam;
    TextView tMeenam;
    LinearLayout trFestival;
    ImageView imgMoon;
    ImageView imgUpdownday;
    ImageView imgFasting;
    ImageView imgKirthigai;
    ImageView imgMugurtham;
    TextView strFestivalDayShow;
    TextView tStar;
    TextView tThithi;
    TextView tYoham;
    TextView tChandrastam;
    TextView tRagukalam;
    TextView tEmakandam;
    TextView tKuligai;
    TextView tSoolam;
    TextView tMorning;
    TextView tEvening;
    TextView sStar;
    TextView sThithi;
    TextView sYoham;
    TextView sChandrastam;
    TextView sRagukalam;
    TextView sEmakandam;
    TextView sKulikai;
    TextView sSoolam;
    TextView sMorning;
    TextView sEvening;
    TextView sGoodtime;
    //rasipalan

    TextView sMesham;
    TextView sRishabam;
    TextView sMithunam;
    TextView sKadakam;
    TextView sSimmam;
    TextView sKanni;
    TextView sThulam;
    TextView sViruchi;
    TextView sDhanusu;
    TextView sMagaram;
    TextView sKumbam;
    TextView sMeenam;
    TextView sTodayRasipalan;
    TextView tTamilday;
    TextView tMonthYear;
    TextView tMonth;
    TextView tWeekOfMonth;
    TextView tCurrentMonth;

    ImageView tPreIcon;
    ImageView tNextIcon;
    DatePickerDialog.OnDateSetListener date;
    public DataBaseHelper db;

    public interface IMethodCaller {
        void clickPrevNextButton(int flag, int i);

        void setDate(String setCurrentdate);
    }

    public DayAdapter(Context context, String df, ArrayList<String> getCount) {
        this.context = context;
        aCountDate = getCount;
        englishdate = df;
        try {
            db = new DataBaseHelper(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return aCountDate.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(final ViewGroup viewGroup, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = layoutInflater.inflate(R.layout.content_day_view, viewGroup, false);

        try {
            df = aCountDate.get(position);
            MiddlewareInterface.refeshDate = df;
            SimpleDateFormat formatin = new SimpleDateFormat("dd.MM.yyyy");
            SimpleDateFormat formatforRasi = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatout = new SimpleDateFormat("MMMM , yyyy");
            SimpleDateFormat getWeek = new SimpleDateFormat("EEEE");
            Date newDate = null;
            String setDate = null, strRasiDate = null;
            try {
                newDate = formatin.parse(df);
                setDate = formatout.format(new Date());
                dayOfTheWeek = getWeek.format(newDate);


            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                newDate = formatin.parse(df);
                strRasiDate = formatforRasi.format(newDate);
                inflateNotesLayout(layoutInflater, v, viewGroup, position);

//            dayOfTheWeek = getWeek.format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            tCurrentdate = (TextView) v.findViewById(R.id.tcurrent_date);
            layoutTamilMonth = (RelativeLayout) v.findViewById(R.id.tamil_month_layout);
            layoutTamilDay = (RelativeLayout) v.findViewById(R.id.numberLayout);
            layoutEngMonth = (RelativeLayout) v.findViewById(R.id.eng_month_loyout);
            ImageView home_icon =  v.findViewById(R.id.home_icon);

            home_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        ((Activity)context).finish();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            tCurrentdate.setText(dayOfTheWeek + " | " + setDate + "");

            try {
                SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
                String[] datesArr = df.split("\\.");
                Date myDate = inFormat.parse(datesArr[0] + "-" + datesArr[1] + "-" + datesArr[2]);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
                String day = simpleDateFormat.format(myDate);
                SimpleDateFormat FormDate = new SimpleDateFormat("MMMM , yyyy");
                String strDate = FormDate.format(myDate);
                tCurrentdate.setText(day + " | " + strDate + "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tCurrentdate.setTypeface(Typeface.DEFAULT_BOLD);

            imgFasting = (ImageView) v.findViewById(R.id.month_fasting_day);
            imgKirthigai = (ImageView) v.findViewById(R.id.kirthigai_icon);
            imgMugurtham = (ImageView) v.findViewById(R.id.mugurtham_icon);
            //get data from database
            setOtherdetails = db.dGetOtherdetails(df);
            showKirthigai = db.dGetKirthigai(df);
            showAmavasai = db.dGetAmavasai(df);
            showPournami = db.dGetPournami(df);
            showFasting = setOtherdetails.get(7);
            showFestival = setOtherdetails.get(6);
            showMugurtham = db.dGetMugurtham(df);
            setCurrentWeek = setOtherdetails.get(5);
            //set current week day
            if (setCurrentWeek.equalsIgnoreCase("திங்கட்கிழமை"))
                setWeekdayName = "Monday";
            else if (setCurrentWeek.equalsIgnoreCase("செவ்வாய்க்கிழமை"))
                setWeekdayName = "Tuesday";
            else if (setCurrentWeek.equalsIgnoreCase("புதன்கிழமை"))
                setWeekdayName = "Wednesday";
            else if (setCurrentWeek.equalsIgnoreCase("வியாழக்கிழமை"))
                setWeekdayName = "Thursday";
            else if (setCurrentWeek.equalsIgnoreCase("வெள்ளிக்கிழமை"))
                setWeekdayName = "Friday";
            else if (setCurrentWeek.equalsIgnoreCase("சனிக்கிழமை"))
                setWeekdayName = "Saturday";
            else if (setCurrentWeek.equalsIgnoreCase("ஞாயிற்றுக்கிழமை"))
                setWeekdayName = "Sunday";
            //get weekday name to set yemaRagu database
            setYemaRagu = db.dGetYemaRagu(setWeekdayName);
            //set soolam tamil name
            String setSoolamTamilName = setYemaRagu.get(3);
            if (setSoolamTamilName.equalsIgnoreCase("West"))
                SoolamTamilName = "மேற்கு ";
            else if (setSoolamTamilName.equalsIgnoreCase("East"))
                SoolamTamilName = "கிழக்கு";
            else if (setSoolamTamilName.equalsIgnoreCase("North"))
                SoolamTamilName = "வடக்கு";
            else if (setSoolamTamilName.equalsIgnoreCase("South"))
                SoolamTamilName = "தெற்கு";
            //set festival day show on dayview
            trFestival = (LinearLayout) v.findViewById(R.id.festival_row_show);
            strFestivalDayShow = (TextView) v.findViewById(R.id.day_festival_show);
            try {
                if (!showFestival.equalsIgnoreCase("") && !showFestival.equalsIgnoreCase("<null>")) {
                    trFestival.setVisibility(View.VISIBLE);
                    if (MiddlewareInterface.bRendering) {
                        strFestivalDayShow.setText(showFestival.trim());
                        strFestivalDayShow.setTypeface(Typeface.DEFAULT_BOLD);
                    } else {
                        strFestivalDayShow.setText(UnicodeUtil.unicode2tsc(showFestival.trim()));
                        strFestivalDayShow.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //show icon's are fasting,kirthigai,amavasai,pournami,updown arrows
            try {
                if (showFasting != null && !showFasting.equalsIgnoreCase("") && showFestival != null && !showFestival.equalsIgnoreCase("<null>")) {
                    imgFasting.setTag(R.id.fasting, showFasting);
                    imgFasting.setColorFilter(Color.BLACK);
                    imgFasting.setImageResource(R.drawable.fasting_days);
                    imgFasting.setVisibility(View.VISIBLE);
                    imgFasting.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, imgFasting);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        int startPos = MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getInt(POS_KEY);
                                        String strFasting = (String) v.getTag(R.id.fasting);
                                        if (!strFasting.equalsIgnoreCase(""))
                                            AMI.ShowToast(context, viewGroup, strFasting);
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

                        }
                    });
                } else {
                    imgFasting.setTag(R.id.fasting, showFasting);
                    imgFasting.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                imgFasting.setTag(R.id.fasting, showFasting);
                imgFasting.setVisibility(View.GONE);
                e.printStackTrace();
            }
            //show updown day
            String showUpdownday = setOtherdetails.get(10);
            imgUpdownday = (ImageView) v.findViewById(R.id.arrow_icons);
            imgUpdownday.setColorFilter(Color.BLACK);
            if (showUpdownday.equals("0")) {
                imgUpdownday.setVisibility(View.VISIBLE);
                imgUpdownday.setImageResource(R.drawable.equal_arrow);
                imgUpdownday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, imgUpdownday);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    AMI.ShowToast(context, viewGroup, "இன்று சமநோக்கு நாள் ");
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

                    }
                });
            } else if (showUpdownday.equals("1")) {
                imgUpdownday.setVisibility(View.VISIBLE);
                imgUpdownday.setImageResource(R.drawable.up_arrow);
                imgUpdownday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, imgUpdownday);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    AMI.ShowToast(context, viewGroup, "இன்று மேல்நோக்கு நாள் ");
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

                    }
                });
            } else if (showUpdownday.equals("2")) {

                imgUpdownday.setVisibility(View.VISIBLE);
                imgUpdownday.setImageResource(R.drawable.down_arrow);
                imgUpdownday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, imgUpdownday);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    AMI.ShowToast(context, viewGroup, "இன்று கீழ்நோக்கு நாள் ");
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

                    }
                });
            }
            //show valarpirai
            imgMoon = (ImageView) v.findViewById(R.id.valarpirai_icon);
            if (showKirthigai != null) {
                if (showKirthigai != "") {
                    imgKirthigai.setColorFilter(Color.BLACK);
                    imgKirthigai.setImageResource(R.drawable.star_img);
                    imgKirthigai.setVisibility(View.VISIBLE);
                    imgKirthigai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, imgKirthigai);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        AMI.ShowToast(context, viewGroup, context.getResources().getString(R.string.kirthigai));
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

                        }
                    });
                }
            } else {
            }
            if (showAmavasai != null) {
                imgMoon.setColorFilter(Color.BLACK);
                imgMoon.setImageResource(R.drawable.full_moon_image1);
                imgMoon.setVisibility(View.VISIBLE);
                imgMoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, imgMoon);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    AMI.ShowToast(context, viewGroup, context.getResources().getString(R.string.amavasai));
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

                    }
                });
            } else {
            }
            if (showPournami != null) {
                imgMoon.setColorFilter(Color.BLACK);
                imgMoon.setImageResource(R.drawable.moon1);
                imgMoon.setVisibility(View.VISIBLE);
                imgMoon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, imgMoon);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    AMI.ShowToast(context, viewGroup, context.getResources().getString(R.string.pournami));
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

                    }
                });
            } else {
            }
            if (showMugurtham != null) {
                imgMugurtham.setColorFilter(Color.BLACK);
                imgMugurtham.setImageResource(R.drawable.subamugurtham_img);
                imgMugurtham.setVisibility(View.VISIBLE);
                imgMugurtham.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, imgMugurtham);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    AMI.ShowToast(context, viewGroup, "சுப முகூர்த்த நாள் ");
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

                    }
                });
            }
            try {
                //Datepicker used here
                myCalendar = Calendar.getInstance();
                date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }
                };
                layoutEngMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, layoutEngMonth);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    DatePickerDialog dialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(DAY_OF_MONTH));
                                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                                        dialog.show();
                                    } else {
                                        String dot = "31/12/"+String.valueOf(SplashScreen.iToYear);
//                                    String setEndDayoftheYear = dot.concat(MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getString(MiddlewareInterface.strSentyear));
                                        long mindate = milliseconds("01/01/"+String.valueOf(SplashScreen.iFromYear));
                                        long maxdate = milliseconds(dot);
                                        dialog.getDatePicker().setMaxDate(maxdate);
                                        dialog.getDatePicker().setMinDate(mindate);
                                        dialog.show();
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

                    }
                });
                layoutTamilDay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, layoutTamilDay);
                            zoomAnimation.addListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animator) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animator) {
                                    try {
                                        DatePickerDialog dialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(DAY_OF_MONTH));

                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                                            dialog.show();
                                        } else {
                                            // TODO Auto-generated method stub
                                            String dot = "31/12/"+String.valueOf(SplashScreen.iToYear);
                                            long mindate = milliseconds("01/01/"+String.valueOf(SplashScreen.iFromYear));
                                            long maxdate = milliseconds(dot);
                                            dialog.getDatePicker().setMaxDate(maxdate);
                                            dialog.getDatePicker().setMinDate(mindate);
                                            dialog.show();
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
                layoutTamilMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, layoutTamilMonth);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    DatePickerDialog dialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(DAY_OF_MONTH));
                                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                                        dialog.show();
                                    } else {
                                        // TODO Auto-generated method stub
                                        String dot = "31/12/"+String.valueOf(SplashScreen.iToYear);
                                        long mindate = milliseconds("01/01/"+String.valueOf(SplashScreen.iFromYear));
                                        long maxdate = milliseconds(dot);
                                        dialog.getDatePicker().setMaxDate(maxdate);
                                        dialog.getDatePicker().setMinDate(mindate);
                                        dialog.show();
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


                    }
                });
                tCurrentdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, tCurrentdate);
                        zoomAnimation.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                try {
                                    DatePickerDialog dialog = new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(DAY_OF_MONTH));
                                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                                        dialog.show();
                                    } else {
                                        // TODO Auto-generated method stub
                                        String dot = "31/12/"+String.valueOf(SplashScreen.iToYear);
//                                    String setEndDayoftheYear = dot.concat(MiddlewareInterface.SharedPreferenceUtility.getInstance(context).getString(MiddlewareInterface.strSentyear));
                                        long mindate = milliseconds("01/01/"+String.valueOf(SplashScreen.iFromYear));
                                        long maxdate = milliseconds(dot);
                                        dialog.getDatePicker().setMaxDate(maxdate);
                                        dialog.getDatePicker().setMinDate(mindate);
                                        dialog.show();
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

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            sStar = (TextView) v.findViewById(R.id.star_string);
            sThithi = (TextView) v.findViewById(R.id.thithi_string);
            sYoham = (TextView) v.findViewById(R.id.yoham_string);
            sChandrastam = (TextView) v.findViewById(R.id.chandrastam_string);
            sRagukalam = (TextView) v.findViewById(R.id.ragukalam_string);
            sEmakandam = (TextView) v.findViewById(R.id.emakandam_string);
            sKulikai = (TextView) v.findViewById(R.id.kuligai_string);
            sSoolam = (TextView) v.findViewById(R.id.soolam_string);
            sMorning = (TextView) v.findViewById(R.id.morning_string);
            sEvening = (TextView) v.findViewById(R.id.evening_string);
            sGoodtime = (TextView) v.findViewById(R.id.good_time);
            tStar = (TextView) v.findViewById(R.id.star_text);
            tThithi = (TextView) v.findViewById(R.id.thithi_text);
            tYoham = (TextView) v.findViewById(R.id.yoham_text);
            tChandrastam = (TextView) v.findViewById(R.id.chandrastam_text);
            tRagukalam = (TextView) v.findViewById(R.id.ragukalam_db);
            tEmakandam = (TextView) v.findViewById(R.id.emakandam_db);
            tKuligai = (TextView) v.findViewById(R.id.kuligai_db);
            tSoolam = (TextView) v.findViewById(R.id.soolam_db);
            tMorning = (TextView) v.findViewById(R.id.morning_db);
            tEvening = (TextView) v.findViewById(R.id.evening_db);
            //rasipalan textview id
            sMesham = (TextView) v.findViewById(R.id.mesham_string);
            sRishabam = (TextView) v.findViewById(R.id.rishabam_string);
            sMithunam = (TextView) v.findViewById(R.id.mithunam_string);
            sKadakam = (TextView) v.findViewById(R.id.kadakam_string);
            sSimmam = (TextView) v.findViewById(R.id.simmam_string);
            sKanni = (TextView) v.findViewById(R.id.kanni_string);
            sThulam = (TextView) v.findViewById(R.id.thulam_string);
            sViruchi = (TextView) v.findViewById(R.id.viruchi_string);
            sDhanusu = (TextView) v.findViewById(R.id.dhanusu_string);
            sMagaram = (TextView) v.findViewById(R.id.magaram_string);
            sKumbam = (TextView) v.findViewById(R.id.kumbam_string);
            sMeenam = (TextView) v.findViewById(R.id.meenam_string);

            toDayRasi = (TableLayout) v.findViewById(R.id.todayRasi);
            tMesham = (TextView) v.findViewById(R.id.mesham_db);
            tRishabam = (TextView) v.findViewById(R.id.rishabam_db);
            tMithunam = (TextView) v.findViewById(R.id.mithunam_db);
            tKadakam = (TextView) v.findViewById(R.id.kadakam_db);
            tSimmam = (TextView) v.findViewById(R.id.simmam_db);
            tKanni = (TextView) v.findViewById(R.id.kanni_db);
            tThulam = (TextView) v.findViewById(R.id.thulam_db);
            tViruchi = (TextView) v.findViewById(R.id.viruchi_db);
            tDhanusu = (TextView) v.findViewById(R.id.dhanusu_db);
            tMagaram = (TextView) v.findViewById(R.id.magaram_db);
            tKumbam = (TextView) v.findViewById(R.id.kumbam_db);
            tMeenam = (TextView) v.findViewById(R.id.meenam_db);
            TextView tamilDate = (TextView) v.findViewById(R.id.tamilDate);

            sTodayRasipalan = (TextView) v.findViewById(R.id.today_rasipalan);
            tPreIcon = (ImageView) v.findViewById(R.id.prev_image_click);
            tNextIcon = (ImageView) v.findViewById(R.id.next_image_click);


            tNextIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, tNextIcon);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                if (context instanceof IMethodCaller)
                                    ((IMethodCaller) context).clickPrevNextButton(1, 1);
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

                }
            });
            tPreIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animator zoomAnimation = MiddlewareInterface.zoomAnimation1(context, tPreIcon);
                    zoomAnimation.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            try {
                                if (context instanceof IMethodCaller)
                                    ((IMethodCaller) context).clickPrevNextButton(2, 1);
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


                }
            });

            tTamilday = (TextView) v.findViewById(R.id.tamil_day);
            tMonthYear = (TextView) v.findViewById(R.id.tamil_month_year);
            tMonth = (TextView) v.findViewById(R.id.tamil_month);
            tWeekOfMonth = (TextView) v.findViewById(R.id.current_weekofmonth);
            tCurrentMonth = (TextView) v.findViewById(R.id.current_Emonth);
            RelativeLayout tamilmonth = (RelativeLayout) v.findViewById(R.id.tamil_month_layout);

            //split tamil date and month
            String[] arrTamilYearMonth = setOtherdetails.get(4).split(",");
            String splittamil = arrTamilYearMonth[1];

            String splitDate = setOtherdetails.get(8);
            String[] getTamilmonth = splittamil.split(" ");
            String[] arrDate = splitDate.split("\\.");
            String setTamilmonth = getTamilmonth[0];
            String setTamilday1 = getTamilmonth[1];
            String setTamilday = arrDate[0];
            String[] split = df.split("\\.");
            int getSplitMonth = Integer.parseInt(split[1]);
            if (getSplitMonth == 1)
                getCurrentmonth = "ஜனவரி";
            else if (getSplitMonth == 2)
                getCurrentmonth = "பிப்ரவரி";
            else if (getSplitMonth == 3)
                getCurrentmonth = "மார்ச்";
            else if (getSplitMonth == 4)
                getCurrentmonth = "ஏப்ரல்";
            else if (getSplitMonth == 5)
                getCurrentmonth = "மே";
            else if (getSplitMonth == 6)
                getCurrentmonth = "ஜூன்";
            else if (getSplitMonth == 7)
                getCurrentmonth = "ஜூலை";
            else if (getSplitMonth == 8)
                getCurrentmonth = "ஆகஸ்ட்";
            else if (getSplitMonth == 9)
                getCurrentmonth = "செப்டம்பர்";
            else if (getSplitMonth == 10)
                getCurrentmonth = "அக்டோபர்";
            else if (getSplitMonth == 11)
                getCurrentmonth = "நவம்பர்";
            else if (getSplitMonth == 12)
                getCurrentmonth = "டிசம்பர்";
            //set current tamil week day
            if (setCurrentWeek.equalsIgnoreCase("திங்கட்கிழமை"))
                setWeekday = "திங்கள் ";
            else if (setCurrentWeek.equalsIgnoreCase("செவ்வாய்க்கிழமை"))
                setWeekday = "செவ்வாய்";
            else if (setCurrentWeek.equalsIgnoreCase("புதன்கிழமை"))
                setWeekday = "புதன்";
            else if (setCurrentWeek.equalsIgnoreCase("வியாழக்கிழமை"))
                setWeekday = "வியாழன்";
            else if (setCurrentWeek.equalsIgnoreCase("வெள்ளிக்கிழமை"))
                setWeekday = "வெள்ளி";
            else if (setCurrentWeek.equalsIgnoreCase("சனிக்கிழமை"))
                setWeekday = "சனி";
            else if (setCurrentWeek.equalsIgnoreCase("ஞாயிற்றுக்கிழமை"))
                setWeekday = "ஞாயிறு";

            tTamilday.setText(setTamilday);
            try {
                MiddlewareInterface.tf_didot = Typeface.createFromAsset(context.getAssets(), "fonts/TheanoDidot-Regular.ttf");
            } catch (Exception e) {
                e.printStackTrace();
            }
            tTamilday.setTypeface(MiddlewareInterface.tf_didot);

            tamilDate.setText(setTamilday1);
            tamilDate.setTypeface(MiddlewareInterface.tf_didot, Typeface.BOLD);


            //set font style
            if (MiddlewareInterface.bRendering) {
                tMonthYear.setText(arrTamilYearMonth[0]);
                tMonthYear.setTypeface(Typeface.DEFAULT);
                tMonth.setText(setTamilmonth);
                tMonth.setTypeface(Typeface.DEFAULT);
                tCurrentMonth.setText(getCurrentmonth);
                tCurrentMonth.setTypeface(Typeface.DEFAULT);
                tWeekOfMonth.setText(setWeekday);
                tWeekOfMonth.setTypeface(Typeface.DEFAULT);
                sStar.setText(context.getResources().getString(R.string.star));
                sStar.setTypeface(Typeface.DEFAULT_BOLD);
                sThithi.setText(context.getResources().getString(R.string.thithi));
                sThithi.setTypeface(Typeface.DEFAULT_BOLD);
                sYoham.setText(context.getResources().getString(R.string.yokam));
                sYoham.setTypeface(Typeface.DEFAULT_BOLD);
                sChandrastam.setText(context.getResources().getString(R.string.chanthirastamam));
                sChandrastam.setTypeface(Typeface.DEFAULT_BOLD);
                sRagukalam.setText(context.getResources().getString(R.string.raagu_kaalam));
                sRagukalam.setTypeface(Typeface.DEFAULT);
                sEmakandam.setText(context.getResources().getString(R.string.emakandam));
                sEmakandam.setTypeface(Typeface.DEFAULT);
                sKulikai.setText(context.getResources().getString(R.string.kulikai));
                sKulikai.setTypeface(Typeface.DEFAULT);
                sSoolam.setText(context.getResources().getString(R.string.soolam));
                sSoolam.setTypeface(Typeface.DEFAULT);
                sGoodtime.setText(context.getResources().getString(R.string.good_time));
                sGoodtime.setTypeface(Typeface.DEFAULT_BOLD);
                sMorning.setText(context.getResources().getString(R.string.morning));
                sMorning.setTypeface(Typeface.DEFAULT);
                sEvening.setText(context.getResources().getString(R.string.evening));
                sEvening.setTypeface(Typeface.DEFAULT);
                sMesham.setText(context.getResources().getString(R.string.mesham));
                sRishabam.setText(context.getResources().getString(R.string.rishabam));
                sMithunam.setText(context.getResources().getString(R.string.midhunam));
                sKadakam.setText(context.getResources().getString(R.string.kadagam));
                sSimmam.setText(context.getResources().getString(R.string.simmam));
                sKanni.setText(context.getResources().getString(R.string.kanni));
                sThulam.setText(context.getResources().getString(R.string.thulaam));
                sViruchi.setText(context.getResources().getString(R.string.viruchigam));
                sDhanusu.setText(context.getResources().getString(R.string.dhanusu));
                sMagaram.setText(context.getResources().getString(R.string.magaram));
                sKumbam.setText(context.getResources().getString(R.string.kumbam));
                sMeenam.setText(context.getResources().getString(R.string.meenam));
                sTodayRasipalan.setText(context.getResources().getString(R.string.today_horoscope));
                sMesham.setTypeface(Typeface.DEFAULT);
                sRishabam.setTypeface(Typeface.DEFAULT);
                sMithunam.setTypeface(Typeface.DEFAULT);
                sKadakam.setTypeface(Typeface.DEFAULT);
                sSimmam.setTypeface(Typeface.DEFAULT);
                sKanni.setTypeface(Typeface.DEFAULT);
                sThulam.setTypeface(Typeface.DEFAULT);
                sViruchi.setTypeface(Typeface.DEFAULT);
                sDhanusu.setTypeface(Typeface.DEFAULT);
                sMagaram.setTypeface(Typeface.DEFAULT);
                sKumbam.setTypeface(Typeface.DEFAULT);
                sMeenam.setTypeface(Typeface.DEFAULT);
                sTodayRasipalan.setTypeface(Typeface.DEFAULT_BOLD);

                if (setOtherdetails.get(1).contains(context.getResources().getString(R.string.pournami))) {
                    tThithi.setText(setOtherdetails.get(1));
                    tThithi.setTextColor(Color.parseColor("#9966CC"));
                    tThithi.setTypeface(Typeface.DEFAULT_BOLD);
                } else if (setOtherdetails.get(1).contains(context.getResources().getString(R.string.amavasai_db))) {
                    tThithi.setText(setOtherdetails.get(1));
                    tThithi.setTextColor(Color.parseColor("#9966CC"));
                    tThithi.setTypeface(Typeface.DEFAULT_BOLD);
                } else if (setOtherdetails.get(1).contains(context.getResources().getString(R.string.yehadasi))) {
                    tThithi.setText(setOtherdetails.get(1));
                    tThithi.setTextColor(Color.parseColor("#9966CC"));
                    tThithi.setTypeface(Typeface.DEFAULT_BOLD);
                } else {
                    tThithi.setText(setOtherdetails.get(1));
                    tThithi.setTextColor(Color.BLACK);
                    tThithi.setTypeface(Typeface.DEFAULT);
                }
                tStar.setText(setOtherdetails.get(0));
                tYoham.setText(setOtherdetails.get(2));
                tChandrastam.setText(setOtherdetails.get(3));
                tRagukalam.setText(setYemaRagu.get(0));
                tEmakandam.setText(setYemaRagu.get(1));
                tKuligai.setText(setYemaRagu.get(2));


                try {
                    String[] strDate = df.split("\\.");
                    String strYear = strDate[2];
                    ArrayList<String> nallaNeramList = new ArrayList<>();
                    nallaNeramList = db.GetNallaNeram(df);
                    String[] nallaNeramArr = nallaNeramList.get(1).split("--");
                    tMorning.setText(nallaNeramArr[0]);
                    tEvening.setText(nallaNeramArr[1]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                tRagukalam.setTypeface(Typeface.DEFAULT);
                tEmakandam.setTypeface(Typeface.DEFAULT);
                tKuligai.setTypeface(Typeface.DEFAULT);
                tMorning.setTypeface(Typeface.DEFAULT);
                tEvening.setTypeface(Typeface.DEFAULT);
                tStar.setTypeface(Typeface.DEFAULT);
                tYoham.setTypeface(Typeface.DEFAULT);
                tChandrastam.setTypeface(Typeface.DEFAULT);
                tSoolam.setText(SoolamTamilName);
                tSoolam.setTypeface(Typeface.DEFAULT);

                toDayRasi.setVisibility(View.VISIBLE);
                String getRasi = "";
                getRasi = db.getRasiPalan(position + 1);
                String[] checkRasi = getRasi.split("\\$");
                setDailyRasi = new ArrayList<>();
                for (int k = 0; k < checkRasi.length; k++) {
                    try {

                        setDailyRasi.add(checkRasi[k]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                tMesham.setText(setDailyRasi.get(0));
                tRishabam.setText(setDailyRasi.get(1));
                tMithunam.setText(setDailyRasi.get(2));
                tKadakam.setText(setDailyRasi.get(3));
                tSimmam.setText(setDailyRasi.get(4));
                tKanni.setText(setDailyRasi.get(5));
                tThulam.setText(setDailyRasi.get(6));
                tViruchi.setText(setDailyRasi.get(7));
                tDhanusu.setText(setDailyRasi.get(8));
                tMagaram.setText(setDailyRasi.get(9));
                tKumbam.setText(setDailyRasi.get(10));
                tMeenam.setText(setDailyRasi.get(11));

                tMesham.setTypeface(Typeface.DEFAULT);
                tRishabam.setTypeface(Typeface.DEFAULT);
                tMithunam.setTypeface(Typeface.DEFAULT);
                tKadakam.setTypeface(Typeface.DEFAULT);
                tSimmam.setTypeface(Typeface.DEFAULT);
                tKanni.setTypeface(Typeface.DEFAULT);
                tThulam.setTypeface(Typeface.DEFAULT);
                tViruchi.setTypeface(Typeface.DEFAULT);
                tDhanusu.setTypeface(Typeface.DEFAULT);
                tMagaram.setTypeface(Typeface.DEFAULT);
                tKumbam.setTypeface(Typeface.DEFAULT);
                tMeenam.setTypeface(Typeface.DEFAULT);

            } else {

                try {

                    Boolean isDateAfter = MiddlewareInterface.DateComparator(df);
                    if(isDateAfter){
                        tMonthYear.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.currentYear1)));
                    }else{
                        tMonthYear.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.currentYear)));
                    }

                    tMonthYear.setTypeface(MiddlewareInterface.tf_mylai);
                    tMonth.setText(UnicodeUtil.unicode2tsc(setTamilmonth));
                    tMonth.setTypeface(MiddlewareInterface.tf_mylai);

                    tCurrentMonth.setText(UnicodeUtil.unicode2tsc(getCurrentmonth));
                    tCurrentMonth.setTypeface(MiddlewareInterface.tf_mylai);

                    tWeekOfMonth.setText(UnicodeUtil.unicode2tsc(setWeekday));
                    tWeekOfMonth.setTypeface(MiddlewareInterface.tf_mylai);

                    sStar.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.star)));
                    sStar.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);

                    sThithi.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.thithi)));
                    sThithi.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);

                    sYoham.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.yokam)));
                    sYoham.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);

                    sChandrastam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.chanthirastamam)));
                    sChandrastam.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);

                    sRagukalam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.raagu_kaalam)));
                    sRagukalam.setTypeface(MiddlewareInterface.tf_mylai);

                    sEmakandam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.emakandam)));
                    sEmakandam.setTypeface(MiddlewareInterface.tf_mylai);

                    sKulikai.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.kulikai)));
                    sKulikai.setTypeface(MiddlewareInterface.tf_mylai);

                    sSoolam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.soolam)));
                    sSoolam.setTypeface(MiddlewareInterface.tf_mylai);

                    sGoodtime.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.good_time)));
                    sGoodtime.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);

                    sMorning.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.morning)));
                    sMorning.setTypeface(MiddlewareInterface.tf_mylai);

                    sEvening.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.evening)));
                    sEvening.setTypeface(MiddlewareInterface.tf_mylai);

                    sMesham.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.mesham)));
                    sRishabam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.rishabam)));
                    sMithunam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.midhunam)));
                    sKadakam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.kadagam)));
                    sSimmam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.simmam)));
                    sKanni.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.kanni)));
                    sThulam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.thulaam)));
                    sViruchi.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.viruchigam)));
                    sDhanusu.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.dhanusu)));
                    sMagaram.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.magaram)));
                    sKumbam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.kumbam)));
                    sMeenam.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.meenam)));
                    sTodayRasipalan.setText(UnicodeUtil.unicode2tsc(context.getResources().getString(R.string.today_horoscope)));
                    sMesham.setTypeface(MiddlewareInterface.tf_mylai);
                    sRishabam.setTypeface(MiddlewareInterface.tf_mylai);
                    sMithunam.setTypeface(MiddlewareInterface.tf_mylai);
                    sKadakam.setTypeface(MiddlewareInterface.tf_mylai);
                    sSimmam.setTypeface(MiddlewareInterface.tf_mylai);
                    sKanni.setTypeface(MiddlewareInterface.tf_mylai);
                    sThulam.setTypeface(MiddlewareInterface.tf_mylai);
                    sViruchi.setTypeface(MiddlewareInterface.tf_mylai);
                    sDhanusu.setTypeface(MiddlewareInterface.tf_mylai);
                    sMagaram.setTypeface(MiddlewareInterface.tf_mylai);
                    sKumbam.setTypeface(MiddlewareInterface.tf_mylai);
                    sMeenam.setTypeface(MiddlewareInterface.tf_mylai);
                    sTodayRasipalan.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (setOtherdetails.get(1).contains(context.getResources().getString(R.string.pournami))) {
                    tThithi.setText(UnicodeUtil.unicode2tsc(setOtherdetails.get(1)));
                    tThithi.setTextColor(Color.parseColor("#9966CC"));
                    tThithi.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);
                } else if (setOtherdetails.get(1).contains(context.getResources().getString(R.string.amavasai_db))) {
                    tThithi.setText(UnicodeUtil.unicode2tsc(setOtherdetails.get(1)));
                    tThithi.setTextColor(Color.parseColor("#9966CC"));
                    tThithi.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);
                } else if (setOtherdetails.get(1).contains(context.getResources().getString(R.string.yehadasi))) {
                    tThithi.setText(UnicodeUtil.unicode2tsc(setOtherdetails.get(1)));
                    tThithi.setTextColor(Color.parseColor("#9966CC"));
                    tThithi.setTypeface(MiddlewareInterface.tf_mylai, Typeface.BOLD);
                } else {
                    tThithi.setText(UnicodeUtil.unicode2tsc(setOtherdetails.get(1)));
                    tThithi.setTextColor(Color.BLACK);
                    tThithi.setTypeface(MiddlewareInterface.tf_mylai);
                }
                tStar.setText(UnicodeUtil.unicode2tsc(setOtherdetails.get(0)));
                tYoham.setText(UnicodeUtil.unicode2tsc(setOtherdetails.get(2)));
                tChandrastam.setText(UnicodeUtil.unicode2tsc(setOtherdetails.get(3)));
                tStar.setTypeface(MiddlewareInterface.tf_mylai);
                tThithi.setTypeface(MiddlewareInterface.tf_mylai);
                tYoham.setTypeface(MiddlewareInterface.tf_mylai);
                tChandrastam.setTypeface(MiddlewareInterface.tf_mylai);

                tSoolam.setText(UnicodeUtil.unicode2tsc(SoolamTamilName));
                tRagukalam.setText(UnicodeUtil.unicode2tsc(setYemaRagu.get(0)));
                tEmakandam.setText(UnicodeUtil.unicode2tsc(setYemaRagu.get(1)));
                tKuligai.setText(UnicodeUtil.unicode2tsc(setYemaRagu.get(2)));

                tRagukalam.setTypeface(Typeface.DEFAULT);
                tEmakandam.setTypeface(Typeface.DEFAULT);
                tKuligai.setTypeface(Typeface.DEFAULT);
                tSoolam.setTypeface(MiddlewareInterface.tf_mylai);
                tMorning.setTypeface(Typeface.DEFAULT);
                tEvening.setTypeface(Typeface.DEFAULT);


                try {
                    String[] strDate = df.split("\\.");
                    String strYear = strDate[2];
                    ArrayList<String> nallaNeramList = new ArrayList<>();
                    nallaNeramList = db.GetNallaNeram(df);
                    String[] nallaNeramArr = nallaNeramList.get(1).split("--");
                    tMorning.setText(nallaNeramArr[0]);
                    tEvening.setText(nallaNeramArr[1]);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                toDayRasi.setVisibility(View.VISIBLE);
                String getRasi = "";
                getRasi = db.getRasiPalan(position + 1);
                String[] checkRasi = getRasi.split("\\$");
                setDailyRasi = new ArrayList<>();
                for (int k = 0; k < checkRasi.length; k++) {
                    try {

                        setDailyRasi.add(checkRasi[k]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                tMesham.setText(setDailyRasi.get(0));
                tRishabam.setText(setDailyRasi.get(1));
                tMithunam.setText(setDailyRasi.get(2));
                tKadakam.setText(setDailyRasi.get(3));
                tSimmam.setText(setDailyRasi.get(4));
                tKanni.setText(setDailyRasi.get(5));
                tThulam.setText(setDailyRasi.get(6));
                tViruchi.setText(setDailyRasi.get(7));
                tDhanusu.setText(setDailyRasi.get(8));
                tMagaram.setText(setDailyRasi.get(9));
                tKumbam.setText(setDailyRasi.get(10));
                tMeenam.setText(setDailyRasi.get(11));

                tMesham.setTypeface(Typeface.DEFAULT);
                tRishabam.setTypeface(Typeface.DEFAULT);
                tMithunam.setTypeface(Typeface.DEFAULT);
                tKadakam.setTypeface(Typeface.DEFAULT);
                tSimmam.setTypeface(Typeface.DEFAULT);
                tKanni.setTypeface(Typeface.DEFAULT);
                tThulam.setTypeface(Typeface.DEFAULT);
                tViruchi.setTypeface(Typeface.DEFAULT);
                tDhanusu.setTypeface(Typeface.DEFAULT);
                tMagaram.setTypeface(Typeface.DEFAULT);
                tKumbam.setTypeface(Typeface.DEFAULT);
                tMeenam.setTypeface(Typeface.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) viewGroup).addView(v);
        return v;
    }

    public long milliseconds(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date mDate = null;
            try {
                mDate = sdf.parse(date);
            } catch (java.text.ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            long timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
            return timeInMilliseconds;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);
    }

    private void updateLabel() {
        try {
            String myFormat = "MMM dd,yyyy";
            String setCurrentdate = new SimpleDateFormat("dd.MM.yyyy").format(myCalendar.getTime());
            MiddlewareInterface.clickDate = setCurrentdate;
            if (context instanceof IMethodCaller)
                ((IMethodCaller) context).setDate(setCurrentdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void inflateNotesLayout(LayoutInflater mLayoutInflater, View itemView, final ViewGroup container, int position) {
        try {

            String strCrntdate = aCountDate.get(position);
            ArrayList<ArrayList<String>> arrNotesList = db.getSpecificNote(strCrntdate);

            LinearLayout InflateLayout = (LinearLayout) itemView.findViewById(R.id.notesInflateLayout);
            RelativeLayout NotesLayout = (RelativeLayout) itemView.findViewById(R.id.NotesLayout);

            if (arrNotesList.size() == 0) {
                NotesLayout.setVisibility(View.GONE);
            }
            for (int i = 0; i < arrNotesList.size(); i++) {
                try {
                    if (!arrNotesList.get(i).get(5).equalsIgnoreCase("")) {
                        final View rowLayout = mLayoutInflater.inflate(R.layout.table_notes_layout, container, false);
                        final TextView notesTitleTv = (TextView) rowLayout.findViewById(R.id.notesTitleTv);
                        final TextView notesDescTv = (TextView) rowLayout.findViewById(R.id.notesDescTv);
                        final View lineView = (View) rowLayout.findViewById(R.id.lineView);
                        ArrayList<String> noteList = arrNotesList.get(i);
                        if (isValidReminder(noteList)) {
                            if (i == arrNotesList.size() - 1) {
                                lineView.setVisibility(View.GONE);
                            }
                            notesTitleTv.setText(noteList.get(2) + " - ");
                            notesDescTv.setText(noteList.get(3));
                            InflateLayout.addView(rowLayout);
                            NotesLayout.setVisibility(View.VISIBLE);

                            rowLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    try {
                                        if (context instanceof DayActivity) {
                                            ((DayActivity) context).gotoNotesPage();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                }
                            });

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Boolean isValidReminder(ArrayList<String> noteDetailList){
        Boolean isValid = false;
        try{
            try{
                String[] noteDateAndTime = noteDetailList.get(6).split("-");
                String[] arrDate = noteDateAndTime[0].split("\\.");
                String[] arrTime = noteDateAndTime[1].split(":");
                int day = Integer.parseInt(arrDate[0]);
                int month = Integer.parseInt(arrDate[1]);
                month = month-1;
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
                    isValid = false;
                }else{
                    isValid = true;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return isValid;
    }

}



