package com.softcraft.calendar.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.softcraft.calendar.JSONParse.DhinapalanModel;
import com.softcraft.calendar.JSONParse.DhinapalanTextModel;
import com.softcraft.calendar.JSONParse.KirthigaiModel;
import com.softcraft.calendar.Middleware.MiddlewareInterface;
import com.softcraft.calendar.JSONParse.MugurthamModel;
import com.softcraft.calendar.JSONParse.OtherdetailsModel;
import com.softcraft.calendar.ServiceAndOthers.SQLiteAssetException;
import com.softcraft.calendar.SplashScreen.SplashScreen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context mycontext;
    private String DB_PATH = "/data/data/com.softcraft.calendar/databases/";
    private static String DB_NAME = "calendar_db.sqlite";
    private static String TABLE_NOTES = "notes_table";
    private static String KEY_NOTE_ID = "note_id";
    private static String KEY_NOTE_DATE = "note_date";
    private static String KEY_NOTE_TITLE = "note_title";
    private static String KEY_NOTE_DESC = "note_desc";
    private static String KEY_DATE = "date";
    private static String KEY_REMINDER_DATE = "note_reminder_date";
    private static String KEY_REMIN_DATE_TIME_ORG = "note_date_time_org";
    private static final String KEY_ID = "id";

    private static String TABLE_TELUGU = "telugu_table";
    private static String KEY_1 = "s1";
    private static String KEY_2 = "s2";
    private static String KEY_3 = "s3";
    private static String KEY_4 = "s4";
    private static String KEY_5 = "s5";
    private static String KEY_6 = "s6";
    private static String KEY_7 = "s7";
    private static String KEY_8 = "s8";
    private static String KEY_9 = "s9 ";
    private static String KEY_10 = "s10";
    private static String KEY_11 = "s11";
    private static String KEY_12 = "s12";
    private static String KEY_13 = "s13";
    private static String KEY_14 = "s14";
    private static String KEY_15 = "s15";
    private static String KEY_16 = "s16";
    private static String KEY_17 = "s17";
    private static String KEY_18 = "s18";
    private static String KEY_19 = "s19";
    private static String KEY_20 = "s20";
    private static String KEY_21 = "s21";
    private static String KEY_22 = "s22";
    private static String KEY_23 = "s23";
    private static String KEY_25 = "s25";
    private static String KEY_26 = "s26";
    private static String KEY_27 = "s27";
    private static String KEY_28 = "s28";
    private static String KEY_29 = "s29";
    private static String KEY_30 = "s30";
    private static String KEY_31 = "s31";
    private static String KEY_32 = "s32";
    private static String KEY_33 = "s33";
    private static String KEY_34 = "s34";
    private static String KEY_35 = "s35";
    private static String KEY_36 = "s36";
    private static String KEY_37 = "s37";
    private static String KEY_38 = "s38";


    public SQLiteDatabase myDataBase;
    MiddlewareInterface AMI = MiddlewareInterface.GetInstance();

    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 11);
        this.mycontext = context;
        boolean dbexist = checkdatabase();
        if (!dbexist) {
            createdatabase();
        } else {
            opendatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (!dbexist) {
            try {
                copydatabase();
                opendatabase();
                initdatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        } else {
            this.getReadableDatabase();
            try {
                opendatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initdatabase() {
        try {
            SQLiteDatabase db = myDataBase;

            String CREATE_CONTACTS_TABLE2 = "CREATE TABLE " + TABLE_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOTE_ID + " TEXT," + KEY_DATE + " TEXT," + KEY_NOTE_TITLE + " TEXT," + KEY_NOTE_DESC + " TEXT," + KEY_NOTE_DATE + " TEXT," + KEY_REMINDER_DATE + " TEXT," + KEY_REMIN_DATE_TIME_ORG + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE2);

            String CREATE_CONTACTS_TABLE3 = "CREATE TABLE " + TABLE_TELUGU + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_1 + " TEXT,"
                    + KEY_2 + " TEXT,"
                    + KEY_3 + " TEXT,"
                    + KEY_4 + " TEXT,"
                    + KEY_5 + " TEXT,"
                    + KEY_6 + " TEXT,"
                    + KEY_7 + " TEXT,"
                    + KEY_8 + " TEXT,"
                    + KEY_9 + " TEXT,"
                    + KEY_10 + " TEXT,"
                    + KEY_11 + " TEXT,"
                    + KEY_12 + " TEXT,"
                    + KEY_13 + " TEXT,"
                    + KEY_14 + " TEXT,"
                    + KEY_15 + " TEXT,"
                    + KEY_16 + " TEXT,"
                    + KEY_17 + " TEXT,"
                    + KEY_18 + " TEXT,"
                    + KEY_19 + " TEXT,"
                    + KEY_20 + " TEXT,"
                    + KEY_21 + " TEXT,"
                    + KEY_22 + " TEXT,"
                    + KEY_23 + " TEXT,"
                    + KEY_25 + " TEXT,"
                    + KEY_26 + " TEXT,"
                    + KEY_27 + " TEXT,"
                    + KEY_28 + " TEXT,"
                    + KEY_29 + " TEXT,"
                    + KEY_30 + " TEXT,"
                    + KEY_31 + " TEXT,"
                    + KEY_32 + " TEXT,"
                    + KEY_33 + " TEXT,"
                    + KEY_34 + " TEXT,"
                    + KEY_35 + " TEXT,"
                    + KEY_36 + " TEXT,"
                    + KEY_37 + " TEXT,"
                    + KEY_38 + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE3);


        } catch (Exception e) {
            Log.d("DH-initdatabase", e.toString());
        }
    }

    private boolean checkdatabase() {
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {

        String mDatabasePath = mycontext.getApplicationInfo().dataDir + "/databases";
        File f = new File(mDatabasePath + "/");
        if (!f.exists()) {
            f.mkdir();
        }
        OutputStream outs = new FileOutputStream(mDatabasePath + "/" + "calendar_db.sqlite");

        InputStream zipFileStream = mycontext.getAssets().open("databases/calendar_db.zip");
        ZipInputStream zis = getFileFromZip(zipFileStream);
        if (zis == null) {
            throw new SQLiteAssetException("Archive is missing a SQLite database file");
        }
        byte[] buffer = new byte[1024];
        int length;
        while ((length = zis.read(buffer)) > 0) {
            outs.write(buffer, 0, length);
        }
        zis.close();
        outs.flush();
        outs.close();
        zipFileStream.close();
        Log.w("TAG", "database copy complete");
    }

    public void opendatabase() throws SQLException {
        try {
            String mypath = DB_PATH + DB_NAME;
            myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public synchronized void close() {
        try {
            super.close();
            if (myDataBase != null) {
                myDataBase.close();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            try {
                String mypath = "/data/data/com.softcraft.calendar/databases/" + DB_NAME;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    db.deleteDatabase(new File(mypath));
                }
                createdatabase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.e("oldversion", "" + oldVersion);
        Log.e("newversion", "" + newVersion);
    }

    private ZipInputStream getFileFromZip(InputStream zipFileStream) throws FileNotFoundException, IOException {
        ZipInputStream zis = new ZipInputStream(zipFileStream);
        ZipEntry ze = null;
        while ((ze = zis.getNextEntry()) != null) {
            Log.w("TAG", "extracting file: '" + ze.getName() + "'...");
            return zis;
        }
        return null;
    }

    //Insert and retrieve data from database
    public ArrayList<String> dGetDate() {
        String date;
        Cursor c;
        try {
            ArrayList<String> list = new ArrayList<String>();
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT date FROM otherdetails", null);
            if (c.moveToFirst()) {
                do {
                    date = c.getString(c.getColumnIndex("date"));
                    list.add(date);
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            eb.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> dGetOtherdetails(String getdate) {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("select *from otherdetails where date='" + getdate + "'", null);
            ArrayList<String> list = new ArrayList<String>();
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(0));
                    list.add(c.getString(1));
                    list.add(c.getString(2));
                    list.add(c.getString(3));
                    list.add(c.getString(4));
                    list.add(c.getString(5));
                    list.add(c.getString(6));
                    list.add(c.getString(7));
                    list.add(c.getString(8));
                    list.add(c.getString(9));
                    list.add(c.getString(10));
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }


    public ArrayList<String> mGetKuralDetails(int kuralId) {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("select *from kuralforsqlite where id='" + kuralId + "'", null);
            ArrayList<String> list = new ArrayList<String>();
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(0));
                    list.add(c.getString(1));
                    list.add(c.getString(2));
                    list.add(c.getString(3));
                    list.add(c.getString(4));
                    list.add(c.getString(5));
                    list.add(c.getString(6));
                    list.add(c.getString(7));
                    list.add(c.getString(8));
                    list.add(c.getString(9));
                    list.add(c.getString(10));
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            eb.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }

    public String dGetKirthigai(String getdate) {
        Cursor c = null;
        try {
            String date = null;
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("select *from kirthigai where kirthigai='" + getdate + "'", null);
            if (c.moveToFirst()) {
                do {
                    date = c.getString(0);
                }
                while (c.moveToNext());
            }
            return date;
        } catch (Exception eb) {
            eb.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }

    public String getRasiPalan(int count) {
        Cursor c = null;     //// select content from dhinapalantext where id = '11'
        try {
            String date = null;
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("select data from dhinapalan where daycount ='" + count + "'", null);
            if (c.moveToFirst()) {
                do {
                    date = c.getString(0);
                }
                while (c.moveToNext());
            }
            return date;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public String dGetAmavasai(String getdate) {
        Cursor c = null;
        try {
            String date = null;
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("select *from kirthigai where amavasai='" + getdate + "'", null);
            if (c.moveToFirst()) {
                do {
                    date = c.getString(1);
                }
                while (c.moveToNext());
            }
            return date;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public String dGetPournami(String getdate) {
        Cursor c = null;
        try {
            String date = null;
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("select *from kirthigai where pournami='" + getdate + "'", null);
            if (c.moveToFirst()) {
                do {
                    date = c.getString(2);
                }
                while (c.moveToNext());
            }
            return date;
        } catch (Exception eb) {
            eb.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }

    public String dGetMugurtham(String getdate) {
        Cursor c = null;
        try {
            String date = null;
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("select *from mugurtham where date='" + getdate + "'", null);
            if (c.moveToFirst()) {
                do {
                    date = c.getString(0);
                }
                while (c.moveToNext());
            }
            return date;
        } catch (Exception eb) {
            eb.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<String> dGetYemaRagu(String weekday) {
        Cursor c = null;
        try {
            ArrayList<String> list = new ArrayList<String>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("select *from yemaRagu where Day='" + weekday + "'", null);
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(0));
                    list.add(c.getString(1));
                    list.add(c.getString(2));
                    list.add(c.getString(3));
                    list.add(c.getString(4));
                    list.add(c.getString(5));
                    list.add(c.getString(6));
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            eb.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<String> GetNallaNeram(String date) {
        Cursor c = null;
        try {
            ArrayList<String> list = new ArrayList<String>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("select *from nallaneram where date='" + date + "'", null);
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(0));
                    list.add(c.getString(1));
                    list.add(c.getString(2));
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            eb.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }


    public ArrayList<ArrayList<String>> dGetFestivals(int festivalvalue) {
        String festival;
        String date;
        Cursor c = null;
        ArrayList<ArrayList<String>> getDateFestival = new ArrayList<>();
        try {
            String setYear;

            int month;
            if (festivalvalue <= 12) {
                setYear = String.valueOf(SplashScreen.iFromYear);
                month = festivalvalue;
            } else {
                setYear = String.valueOf(SplashScreen.iToYear);
                month = festivalvalue - 12;
            }
            String strMonth = month+"";

            if(month<10){
                strMonth = "0"+month;
            }


            ArrayList<String> lDate = new ArrayList<String>();
            ArrayList<String> lFestival = new ArrayList<String>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("select * from otherdetails where date like '%." + strMonth + "." + setYear + "%'  ", null);
            if (c.moveToFirst()) {
                do {
                    if (c.getString(6) != null) {
                        festival = c.getString(6);
                        if (!festival.equalsIgnoreCase("")) {
                            if (!festival.equalsIgnoreCase("null")) {
                                date = c.getString(8);
                                lFestival.add(festival);
                                lDate.add(date);
                            }
                        }
                    }
                }
                while (c.moveToNext());
                getDateFestival.add(lDate);
                getDateFestival.add(lFestival);
            }
            return getDateFestival;
        } catch (Exception eb) {
            eb.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> GetFastingDetails(String month, String year) {
        String englishdate, tamildate, day, specialdays;
        Cursor c = null;
        ArrayList<ArrayList<String>> GetFestival = new ArrayList<>();
        try {
            ArrayList<String> EnglishDate = new ArrayList<String>();
            ArrayList<String> Day = new ArrayList<String>();
            ArrayList<String> tamilDate = new ArrayList<>();
            ArrayList<String> SpecialDay = new ArrayList<>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM otherdetails", null);
            if (c.moveToFirst()) {
                do {
                    try {
                        if (!c.getString(8).equalsIgnoreCase((""))) {

                            String strDate = c.getString(8);
                            String[] date = strDate.split("\\.");
                            if (date[2].equalsIgnoreCase(year)) {

                                if (Integer.parseInt(date[1]) == Integer.parseInt(month)) {
                                    specialdays = c.getString(7);
                                    if (!specialdays.equalsIgnoreCase("")) {
                                        englishdate = c.getString(8);
                                        tamildate = c.getString(4);
                                        day = c.getString(5);
                                        Day.add(day);
                                        EnglishDate.add(englishdate);
                                        tamilDate.add(tamildate);
                                        SpecialDay.add(specialdays);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                while (c.moveToNext());

                GetFestival.add(EnglishDate);
                GetFestival.add(tamilDate);
                GetFestival.add(Day);
                GetFestival.add(SpecialDay);
            }
            return GetFestival;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }


    public ArrayList<String> getFastingDetails() {
        String englishdate, specialdays;
        Cursor c = null;
        try {
            ArrayList<String> EnglishDate = new ArrayList<String>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM otherdetails", null);
            if (c.moveToFirst()) {
                do {
                    try {
                        if (!c.getString(8).equalsIgnoreCase((""))) {
                            String strDate = c.getString(8);
                            String[] date = strDate.split("\\.");
                            if (date[2].equalsIgnoreCase(String.valueOf(SplashScreen.iFromYear))) {
                                specialdays = c.getString(7);
                                if (!specialdays.equalsIgnoreCase("")) {
                                    englishdate = c.getString(8);
                                    EnglishDate.add(englishdate);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                while (c.moveToNext());

            }
            return EnglishDate;
        } catch (Exception eb) {
            eb.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }


    public ArrayList<ArrayList<String>> GetPournamiAmavasai(String month, String year) {
        String englishdate, tamildate, day, specialdays;
        Cursor c = null;
        ArrayList<ArrayList<String>> GetFestival = new ArrayList<>();
        try {
            ArrayList<String> EnglishDate = new ArrayList<>();
            ArrayList<String> Day = new ArrayList<>();
            ArrayList<String> tamilDate = new ArrayList<>();
            ArrayList<String> SpecialDay = new ArrayList<>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM otherdetails", null);
            if (c.moveToFirst()) {
                do {
                    try {
                        if (!c.getString(8).equalsIgnoreCase((""))) {

                            String strDate = c.getString(8);
                            String[] date = strDate.split("\\.");
                            if (date[2].equalsIgnoreCase(year)) {

                                if (Integer.parseInt(date[1]) == Integer.parseInt(month)) {
                                    specialdays = c.getString(1);
                                    if (specialdays.contains("பௌர்ணமி") || specialdays.contains("அமாவாசை")) {
                                        englishdate = c.getString(8);
                                        tamildate = c.getString(4);
                                        day = c.getString(5);


                                        Day.add(day);
                                        EnglishDate.add(englishdate);
                                        tamilDate.add(tamildate);
                                        SpecialDay.add(specialdays);
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                while (c.moveToNext());

                GetFestival.add(EnglishDate);
                GetFestival.add(tamilDate);
                GetFestival.add(Day);
                GetFestival.add(SpecialDay);
            }
            return GetFestival;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }


    public ArrayList<String> getPournamiAmavasai() {

        String englishdate, specialdays;
        Cursor c = null;
        try {
            ArrayList<String> EnglishDate = new ArrayList<String>();

            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM otherdetails", null);
            if (c.moveToFirst()) {
                do {
                    try {
                        if (!c.getString(8).equalsIgnoreCase((""))) {

                            String strDate = c.getString(8);
                            String[] date = strDate.split("\\.");
                            if (date[2].equalsIgnoreCase(String.valueOf(SplashScreen.iFromYear))) {
                                specialdays = c.getString(1);
                                if (specialdays.contains("பௌர்ணமி") || specialdays.contains("அமாவாசை")) {
                                    englishdate = c.getString(8);
                                    EnglishDate.add(englishdate);
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                while (c.moveToNext());
            }
            return EnglishDate;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }


    public ArrayList<ArrayList<String>> dGetYearFestivals() {
        String festival;
        String date, year, checkYear;
        Cursor c = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date d = new Date();
        String currentYear = sdf.format(d);
        ArrayList<ArrayList<String>> getFullYearFestival = new ArrayList<>();
        try {
            ArrayList<String> lDate = new ArrayList<String>();
            ArrayList<String> lFestival = new ArrayList<String>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM otherdetails", null);
            if (c.moveToFirst()) {
                do {
                    year = c.getString(8);
                    String[] splitYear = year.split("\\.");
                    checkYear = splitYear[2];
                    if (currentYear.equalsIgnoreCase(checkYear)) {
                        date = c.getString(8);
                        if (c.getString(6) != null) {
                            festival = c.getString(6);
                            if (!festival.equals("")) {
                                if (!festival.equals("null")) {
                                    String getFes = festival;
                                    lFestival.add(getFes);
                                    lDate.add(date);
                                }
                            }
                        }
                    }
                }
                while (c.moveToNext());
                getFullYearFestival.add(lDate);
                getFullYearFestival.add(lFestival);
            }
            return getFullYearFestival;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public String getTamilDate(String date, String month, String year) {
        Cursor c = null;
        try {
            String tamildate = null;
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM otherdetails where date = '" + date + "." + month + "." + year + "'", null);
            if (c.moveToFirst()) {
                do {

                    tamildate = c.getString(4);
                }
                while (c.moveToNext());
            }
            return tamildate;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public String imgKirthigai(String date, String month, String year) {
        Cursor c = null;
        try {
            String kirthigai = null;
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM kirthigai where kirthigai = '" + date + "." + month + "." + year + "'", null);
            if (c.moveToFirst()) {
                do {

                    kirthigai = c.getString(0);
                }
                while (c.moveToNext());
            }

            return kirthigai;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public String imgAmavasai(String date, String month, String year) {
        Cursor c = null;
        try {
            String amavasai = null;
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM kirthigai where amavasai = '" + date + "." + month + "." + year + "'", null);
            if (c.moveToFirst()) {
                do {
                    amavasai = c.getString(1);
                }
                while (c.moveToNext());
            }
            return amavasai;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public String imgPournami(String date, String month, String year) {
        Cursor c = null;
        try {
            String pournami = null;
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM kirthigai where pournami = '" + date + "." + month + "." + year + "'", null);
            if (c.moveToFirst()) {
                do {
                    pournami = c.getString(2);
                }
                while (c.moveToNext());
            }
            return pournami;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;

    }


    public String getHolidays(String date, String month, String year) {
        Cursor c = null;
        try {
            String holiday = null;
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM otherdetails where date = '" + date + "." + month + "." + year + "'", null);
            if (c.moveToFirst()) {
                do {
                    holiday = c.getString(9);
                }
                while (c.moveToNext());
            }
            return holiday;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public String getMugurthamDate(String date, String month, String year) {
        Cursor c = null;
        try {
            if(date.length() == 1){
                date = "0"+date;
            }

            if(month.length() == 1){
                month = "0"+month;
            }


            String mugurthamdate = null;
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM mugurtham where date = '" + date + "." + month + "." + year + "'", null);
            if (c.moveToFirst()) {
                do {
                    mugurthamdate = c.getString(0);
                }
                while (c.moveToNext());
            }
            return mugurthamdate;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public String getFestivalDate(String date, String month, String year) {
        Cursor c = null;
        try {
            String festivaldate = null;
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM otherdetails where date = '" + date + "." + month + "." + year + "'", null);
            if (c.moveToFirst()) {
                do {
                    festivaldate = c.getString(6);
                }
                while (c.moveToNext());
            }
            return festivaldate;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public String getManaiyadiSasthiram() {
        Cursor c = null;
        try {
            String manai = null;
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM manai", null);
            if (c.moveToFirst()) {
                do {
                    manai = c.getString(0);
                }
                while (c.moveToNext());
            }
            return manai;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> dGetMugurthamDetails() {
        String date, checkYear, year;
        String day;
        String time;
        Cursor c = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date d = new Date();
        String currentYear = sdf.format(d);
        ArrayList<ArrayList<String>> dGetMugurtham = new ArrayList<>();
        try {
            ArrayList<String> lDate = new ArrayList<String>();
            ArrayList<String> lDay = new ArrayList<String>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM mugurtham", null);

            if (c.moveToFirst()) {
                do {
                    year = c.getString(0);
                    String[] splitYear = year.split("\\.");
                    checkYear = splitYear[2];
                    if (currentYear.equalsIgnoreCase(checkYear)) {
                        date = c.getString(0);
                        day = c.getString(1);
                        lDate.add(date);
                        lDay.add(day);
                    }
                }
                while (c.moveToNext());
                dGetMugurtham.add(lDate);
                dGetMugurtham.add(lDay);
            }
            return dGetMugurtham;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    //set database values from JSON
    public void setKirthigaiFromJson(ArrayList<KirthigaiModel> kirthigaiFromJson) {
        if (kirthigaiFromJson != null && kirthigaiFromJson.size() > 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            for (int i = 0; i < kirthigaiFromJson.size(); i++) {
                KirthigaiModel kirthigaiModel = (KirthigaiModel) kirthigaiFromJson.get(i);
                Cursor mCur = null;
                ContentValues values = new ContentValues();
                values.put(kirthigaiModel.KEY_KIRTHIGAI, kirthigaiModel.getKirthigai());
                values.put(kirthigaiModel.KEY_AMAVASAI, kirthigaiModel.getAmavasai());
                values.put(kirthigaiModel.KEY_POURNAMI, kirthigaiModel.getPournami());
                db.insert(kirthigaiModel.KEY_KIRTHIGAI, null, values);
                Log.d("kirthigai", String.valueOf(values));
            }
            db.close();
        }
    }

    public void setMugurthamFromJson(ArrayList<MugurthamModel> mugurthamFromJson) {
        if (mugurthamFromJson != null && mugurthamFromJson.size() > 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            for (int i = 0; i < mugurthamFromJson.size(); i++) {
                MugurthamModel mugurthamModel = (MugurthamModel) mugurthamFromJson.get(i);
                Cursor cursor = null;
                ContentValues contentValues = new ContentValues();
                contentValues.put(mugurthamModel.KEY_DATE, mugurthamModel.getDate());
                contentValues.put(mugurthamModel.KEY_DAY, mugurthamModel.getDay());
//				contentValues.put(mugurthamModel.KEY_TIME,mugurthamModel.getTime());
                db.insert("mugurtham", null, contentValues);
            }
            db.close();
        }
    }

    public void setDhinapalanTextFromJsopn(ArrayList<DhinapalanTextModel> dhinapalantextFromJson) {
        if (dhinapalantextFromJson != null && dhinapalantextFromJson.size() > 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            for (int i = 0; i < dhinapalantextFromJson.size(); i++) {
                DhinapalanTextModel dhinapalantextModel = (DhinapalanTextModel) dhinapalantextFromJson.get(i);
                Cursor cursor = null;
                ContentValues contentValues = new ContentValues();
                contentValues.put(dhinapalantextModel.KEY_ID, dhinapalantextModel.getId());
                contentValues.put(dhinapalantextModel.KEY_CONTENT, dhinapalantextModel.getContent());
//				contentValues.put(mugurthamModel.KEY_TIME,mugurthamModel.getTime());
                db.insert("dhinapalantext", null, contentValues);
            }
            db.close();
        }
    }

    public void setDhinapalanFromJson(ArrayList<DhinapalanModel> dhinapalanFromJson) {
        if (dhinapalanFromJson != null && dhinapalanFromJson.size() > 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            for (int i = 0; i < dhinapalanFromJson.size(); i++) {
                DhinapalanModel dhinapalanModel = (DhinapalanModel) dhinapalanFromJson.get(i);
                Cursor cursor = null;
                ContentValues contentValues = new ContentValues();
                contentValues.put(dhinapalanModel.KEY_DAYCOUNT, dhinapalanModel.getDaycount());
                contentValues.put(dhinapalanModel.KEY_DATA, dhinapalanModel.getData());
                contentValues.put(dhinapalanModel.KEY_YEAR, dhinapalanModel.getYear());
                db.insert("dhinapalan", null, contentValues);
            }
            db.close();
        }
    }

//    public ArrayList<ArrayList<String>> GetFestivalDetails() {
//        String englishdate, tamildate, day, specialdays;
//        Cursor c = null;
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
////		Date d = new Date();
////		String currentYear = sdf.format(d);
//        ArrayList<ArrayList<String>> GetFestival = new ArrayList<>();
//        try {
//            ArrayList<String> EnglishDate = new ArrayList<String>();
//            ArrayList<String> Day = new ArrayList<String>();
//            ArrayList<String> tamilDate = new ArrayList<>();
//            ArrayList<String> SpecialDay = new ArrayList<>();
//            SQLiteDatabase db = myDataBase;
//            c = db.rawQuery("SELECT * FROM FestivalDays", null);
//
//            if (c.moveToFirst()) {
//                do {
//                    englishdate = c.getString(0);
//                    tamildate = c.getString(1);
//                    day = c.getString(2);
//                    specialdays = c.getString(3);
//
//                    Day.add(day);
//                    EnglishDate.add(englishdate);
//                    tamilDate.add(tamildate);
//                    SpecialDay.add(specialdays);
//
//
//                }
//                while (c.moveToNext());
//
//                GetFestival.add(EnglishDate);
//                GetFestival.add(tamilDate);
//                GetFestival.add(Day);
//                GetFestival.add(SpecialDay);
//            }
//            return GetFestival;
//        } catch (Exception eb) {
//            Log.d("selected words", eb.toString());
//        } finally {
//            c.close();
//        }
//        return null;
//    }

    public ArrayList<ArrayList<String>> GetFestivalDetails(String month, String year) {

        String englishdate, tamildate, day, specialdays;
        Cursor c = null;

        ArrayList<ArrayList<String>> GetFestival = new ArrayList<>();
        try {
            ArrayList<String> EnglishDate = new ArrayList<String>();
            ArrayList<String> Day = new ArrayList<String>();
            ArrayList<String> tamilDate = new ArrayList<>();
            ArrayList<String> SpecialDay = new ArrayList<>();
            SQLiteDatabase db = myDataBase;

            c = db.rawQuery("SELECT * FROM otherdetails", null);

//            String query = "SELECT * FROM otherdetails WHERE festival !=" + null;
//              c = db.rawQuery(query,null);


            if (c.moveToFirst()) {
                do {
                    try {
                        if (c.getString(6) != null && !c.getString(6).equalsIgnoreCase((""))) {

                            String strDate = c.getString(8);
                            String[] date = strDate.split("\\.");
                            if (date[2].equalsIgnoreCase(year)) {

                                if (Integer.parseInt(date[1])== Integer.parseInt(month)) {
                                    englishdate = c.getString(8);
                                    tamildate = c.getString(4);
                                    day = c.getString(5);
                                    specialdays = c.getString(6);

                                    Day.add(day);
                                    EnglishDate.add(englishdate);
                                    tamilDate.add(tamildate);
                                    SpecialDay.add(specialdays);
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                while (c.moveToNext());

                GetFestival.add(EnglishDate);
                GetFestival.add(tamilDate);
                GetFestival.add(Day);
                GetFestival.add(SpecialDay);
            }
            return GetFestival;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> GetHolidaysDetails(String month, String year) {

        String englishdate, tamildate, day, specialdays;
        Cursor c = null;

        ArrayList<ArrayList<String>> GetFestival = new ArrayList<>();
        try {
            ArrayList<String> EnglishDate = new ArrayList<String>();
            ArrayList<String> Day = new ArrayList<String>();
            ArrayList<String> tamilDate = new ArrayList<>();
            ArrayList<String> SpecialDay = new ArrayList<>();
            SQLiteDatabase db = myDataBase;

            c = db.rawQuery("SELECT * FROM otherdetails", null);

//            String query = "SELECT * FROM otherdetails WHERE festival !=" + null;
//              c = db.rawQuery(query,null);


            if (c.moveToFirst()) {
                do {
                    try {
                        if (c.getString(9) != null && !c.getString(9).equalsIgnoreCase((""))) {

                            String strDate = c.getString(8);
                            String[] date = strDate.split("\\.");
                            if (date[2].equalsIgnoreCase(year)) {

                                if (Integer.parseInt(date[1]) == Integer.parseInt(month)) {

                                    if (c.getString(9).equalsIgnoreCase("1")) {
                                        englishdate = c.getString(8);
                                        tamildate = c.getString(4);
                                        day = c.getString(5);
                                        specialdays = c.getString(6);

                                        if (specialdays != null && !specialdays.equalsIgnoreCase("")) {

                                            Day.add(day);
                                            EnglishDate.add(englishdate);
                                            tamilDate.add(tamildate);
                                            SpecialDay.add(specialdays);
                                        }
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                while (c.moveToNext());

                GetFestival.add(EnglishDate);
                GetFestival.add(tamilDate);
                GetFestival.add(Day);
                GetFestival.add(SpecialDay);
            }
            return GetFestival;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> GetSubhaMugurthamDetails(String month, String year) {
        String englishdate, tamildate, day, time;
        Cursor c = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//		Date d = new Date();
//		String currentYear = sdf.format(d);
        try {
            ArrayList<ArrayList<String>> GetSubhaMugurtham = new ArrayList<>();
            ArrayList<String> englishDate = new ArrayList<String>();
            ArrayList<String> Day = new ArrayList<String>();
            ArrayList<String> tamilDate = new ArrayList<>();
            ArrayList<String> Time = new ArrayList<>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM subhamugurthamDays", null);

            if (c.moveToFirst()) {
                do {
                    String strDate = c.getString(0);
                    String[] date = strDate.split(" ");
                    if (date[0].contains(month) && date[0].contains(year)) {

//                        if (date[1].equalsIgnoreCase(month)) {
                        englishdate = c.getString(0);
                        tamildate = c.getString(1);
                        day = c.getString(2);
                        time = c.getString(3);

                        Day.add(day);
                        englishDate.add(englishdate);
                        tamilDate.add(tamildate);
                        Time.add(time);
//                        }
                    }


                }
                while (c.moveToNext());

                GetSubhaMugurtham.add(englishDate);
                GetSubhaMugurtham.add(tamilDate);
                GetSubhaMugurtham.add(Day);
                GetSubhaMugurtham.add(Time);
            }
            return GetSubhaMugurtham;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }


    public ArrayList<String> GetSubhaMugurthamDetails1() {
        String englishdate, tamildate, day, time;
        Cursor c = null;
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
//		Date d = new Date();
//		String currentYear = sdf.format(d);
        try {
            ArrayList<ArrayList<String>> GetSubhaMugurtham = new ArrayList<>();
            ArrayList<String> englishDate = new ArrayList<String>();
            ArrayList<String> Day = new ArrayList<String>();
            ArrayList<String> tamilDate = new ArrayList<>();
            ArrayList<String> Time = new ArrayList<>();
            SQLiteDatabase db = myDataBase;
            c = db.rawQuery("SELECT * FROM subhamugurthamDays", null);

            if (c.moveToFirst()) {
                do {
                    englishdate = c.getString(0);
                    englishDate.add(englishdate);
                }
                while (c.moveToNext());
                GetSubhaMugurtham.add(englishDate);
            }
            return englishDate;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<String> dGetDailyRasiTable(String getdate) {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM rasi_table where date = '" + getdate + "'", null);
//            c=db.rawQuery("SELECT * FROM rasitable WITH(INDEX("+getdate+"))",null);
            ArrayList<String> list = new ArrayList<String>();
            if (c.moveToFirst()) {
                do {
//                    list.add(c.getString(0));
                    list.add(c.getString(1));
                    list.add(c.getString(2));
                    list.add(c.getString(3));
                    list.add(c.getString(4));
                    list.add(c.getString(5));
                    list.add(c.getString(6));
                    list.add(c.getString(7));
                    list.add(c.getString(8));
                    list.add(c.getString(9));
                    list.add(c.getString(10));
                    list.add(c.getString(11));
                    list.add(c.getString(12));
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }


//    public ArrayList<ArrayList<String>> getDailyRasipalan(int pos) {
////		String englishdate,tamildate,day,time;
//        String MESAM, RISIBAM, MITHUNAM, KADAKAM, SIMMAM, KANNI, THULAM, VIRUCHAKAM, DHANUSU, MAKARAM, KUMBAM, MEENAM;
//        Cursor c = null;
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
////		Date d = new Date();
////		String currentYear = sdf.format(d);
//        try {
//            ArrayList<ArrayList<String>> GetTodayRasi = new ArrayList<>();
//            ArrayList<String> mesam = new ArrayList<String>();
//            ArrayList<String> risibam = new ArrayList<String>();
//            ArrayList<String> mithunam = new ArrayList<>();
//            ArrayList<String> kadakam = new ArrayList<>();
//            ArrayList<String> simmam = new ArrayList<>();
//            ArrayList<String> kanni = new ArrayList<>();
//            ArrayList<String> thulam = new ArrayList<>();
//            ArrayList<String> viruchakam = new ArrayList<>();
//            ArrayList<String> dhanusu = new ArrayList<>();
//            ArrayList<String> makaram = new ArrayList<>();
//            ArrayList<String> kumbam = new ArrayList<>();
//            ArrayList<String> meenam = new ArrayList<>();
//
//            SQLiteDatabase db = myDataBase;
//            c = db.rawQuery("SELECT * FROM rasi_table", null);
//
//            if (c.moveToFirst()) {
//                do {
//                    MESAM = c.getString(1);
//                    RISIBAM = c.getString(2);
//                    MITHUNAM = c.getString(3);
//                    KADAKAM = c.getString(4);
//                    SIMMAM = c.getString(4);
//                    KANNI = c.getString(5);
//                    THULAM = c.getString(6);
//                    VIRUCHAKAM = c.getString(7);
//                    DHANUSU = c.getString(8);
//                    MAKARAM = c.getString(9);
//                    KUMBAM = c.getString(10);
//                    MEENAM = c.getString(11);
//
//
//                    mesam.add(MESAM);
//                    risibam.add(RISIBAM);
//                    mithunam.add(MITHUNAM);
//                    kadakam.add(KADAKAM);
//                    simmam.add(SIMMAM);
//                    kanni.add(KANNI);
//                    thulam.add(THULAM);
//                    viruchakam.add(VIRUCHAKAM);
//                    dhanusu.add(DHANUSU);
//                    makaram.add(MAKARAM);
//                    kumbam.add(KUMBAM);
//                    meenam.add(MEENAM);
//
//
//                }
//                while (c.moveToNext());
//                GetTodayRasi.add(mesam);
//                GetTodayRasi.add(risibam);
//                GetTodayRasi.add(mithunam);
//                GetTodayRasi.add(kadakam);
//                GetTodayRasi.add(simmam);
//                GetTodayRasi.add(kanni);
//                GetTodayRasi.add(thulam);
//                GetTodayRasi.add(viruchakam);
//                GetTodayRasi.add(dhanusu);
//                GetTodayRasi.add(makaram);
//                GetTodayRasi.add(kumbam);
//                GetTodayRasi.add(meenam);
//
//
//            }
//            return GetTodayRasi;
//        } catch (Exception eb) {
//            Log.d("selected words", eb.toString());
//        } finally {
//            c.close();
//        }
//        return null;
//    }

    public void setOtherdetailsFromJson(ArrayList<OtherdetailsModel> otherdetailsFromJson) {
        if (otherdetailsFromJson != null && otherdetailsFromJson.size() > 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            for (int i = 0; i < otherdetailsFromJson.size(); i++) {
                OtherdetailsModel otherdetailsModel = (OtherdetailsModel) otherdetailsFromJson.get(i);
                Cursor cursor = null;
                ContentValues contentValues = new ContentValues();
                contentValues.put(otherdetailsModel.KEY_NATCHA, otherdetailsModel.getNatcha());
                contentValues.put(otherdetailsModel.KEY_THITHI, otherdetailsModel.getThithi());
                contentValues.put(otherdetailsModel.KEY_YOGAM, otherdetailsModel.getYogam());
                contentValues.put(otherdetailsModel.KEY_CHANDRA, otherdetailsModel.getChandra());
                contentValues.put(otherdetailsModel.KEY_TAMILDATE, otherdetailsModel.getTamildate());
                contentValues.put(otherdetailsModel.KEY_TAMILDAY, otherdetailsModel.getTamilday());
//				if(!otherdetailsModel.getFestival().equalsIgnoreCase(null))
                contentValues.put(otherdetailsModel.KEY_FESTIVAL, otherdetailsModel.getFestival());
//				if(!otherdetailsModel.getViratham().equalsIgnoreCase(null))
                contentValues.put(otherdetailsModel.KEY_VIRATHAM, otherdetailsModel.getViratham());
                contentValues.put(otherdetailsModel.KEY_DATE, otherdetailsModel.getDate());
                contentValues.put(otherdetailsModel.KEY_GOV_HOLIDAY, otherdetailsModel.getGov_holiday());
                contentValues.put(otherdetailsModel.KEY_UPDOWNDAY, otherdetailsModel.getUpdownday());
                db.insert("otherdetails", null, contentValues);
            }
            db.close();
        }
    }

    public ArrayList<ArrayList<String>> getGrahaOraigal() {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM grahaoraigal", null);
            ArrayList<ArrayList<String>> grahaoraigalList = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    ArrayList<String> arrItems = new ArrayList<>();
                    arrItems.add(c.getString(0));
                    arrItems.add(c.getString(1));
                    arrItems.add(c.getString(2));

                    grahaoraigalList.add(arrItems);
                }
                while (c.moveToNext());
            }
            return grahaoraigalList;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> getGowriPanchangamList() {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM gowripanchangam", null);
            ArrayList<ArrayList<String>> gowripanchangamList = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    ArrayList<String> arrItems = new ArrayList<>();
                    arrItems.add(c.getString(0));
                    arrItems.add(c.getString(1));
                    arrItems.add(c.getString(2));
                    gowripanchangamList.add(arrItems);
                }
                while (c.moveToNext());
            }
            return gowripanchangamList;
        } catch (Exception eb) {
            eb.printStackTrace();
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<String> dGetKanavuPalangal() {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM kanavupalan", null);
            ArrayList<String> list = new ArrayList<String>();
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(1));
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<String> dGetAlphabetsOFBabyNames(String strGender, String strReligion) {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("select *from baby_names where Gender = '" + strGender + "' AND " + "Religion" + " = '" + strReligion + "'", null);
            ArrayList<String> list = new ArrayList<String>();
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(3));
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }


    public ArrayList<ArrayList<String>> dGetBabyNamesAndMeaning(String strGender, String strReligion, String strAlphabet) {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("select *from baby_names where Gender = '" + strGender + "' AND " + "Religion" + " = '" + strReligion + "' AND " + "letter" + " = '" + strAlphabet + "'", null);
            ArrayList<ArrayList<String>> list = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    ArrayList<String> arrNameMeaning = new ArrayList<>();
                    arrNameMeaning.add(c.getString(1));
                    arrNameMeaning.add(c.getString(2));
                    arrNameMeaning.add(c.getString(3));
                    arrNameMeaning.add(c.getString(4));
                    arrNameMeaning.add(c.getString(5));
                    arrNameMeaning.add(c.getString(6));
                    list.add(arrNameMeaning);
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<String> mGetQuotes(int quoteId) {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM quote where _id = '" + quoteId + "'", null);
            ArrayList<String> list = new ArrayList<String>();
            if (c.moveToFirst()) {
                do {
                    list.add(c.getString(1));
                    list.add(c.getString(3));
                }
                while (c.moveToNext());
            }
            return list;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public void setSample(String strId, String strDate, String strTitle, String strDesc) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NOTE_ID, strId);
        contentValues.put(KEY_NOTE_DATE, strDate);
        contentValues.put(KEY_NOTE_TITLE, strTitle);
        contentValues.put(KEY_NOTE_DESC, strDesc);
//				contentValues.put(mugurthamModel.KEY_TIME,mugurthamModel.getTime());
        db.insert(TABLE_NOTES, null, contentValues);
        db.close();

    }


    public void addNotes(String strDate, String strId, String strTitle, String strDesc, String strNoteDateAndTime, String strReminderDate, String strDateAndTimeOrg) {
        SQLiteDatabase db = null;
        try {
            Cursor cursor = null;
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            cursor = db.rawQuery("select * from notes_table where date='" + strDate + "'and note_id='" + strId + "'and note_title='" + strTitle + "'and note_desc='" + strDesc + "'and note_date='" + strNoteDateAndTime + "'and note_reminder_date='" + strReminderDate + "'and note_date_time_org='" + strDateAndTimeOrg + "'", null);
            if (cursor.getCount() != 1) {

                contentValues.put(KEY_DATE, strDate);
                contentValues.put(KEY_NOTE_ID, strId);
                contentValues.put(KEY_NOTE_TITLE, strTitle);
                contentValues.put(KEY_NOTE_DESC, strDesc);
                contentValues.put(KEY_NOTE_DATE, strNoteDateAndTime);
                contentValues.put(KEY_REMINDER_DATE, strReminderDate);
                contentValues.put(KEY_REMIN_DATE_TIME_ORG, strDateAndTimeOrg);
                db.insert(TABLE_NOTES, null, contentValues);
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }

    public void addTeluguData(String ds1,
                              String ds2,
                              String ds3,
                              String ds4,
                              String ds5,
                              String ds6,
                              String ds7,
                              String ds8,
                              String ds9,
                              String ds10,
                              String ds11,
                              String ds12,
                              String ds13,
                              String ds14,
                              String ds15,
                              String ds16,
                              String ds17,
                              String ds18,
                              String ds19,
                              String ds20,
                              String ds21,
                              String ds22,
                              String ds23,
                              String ds25,
                              String ds26,
                              String ds27,
                              String ds28,
                              String ds29,
                              String ds30,
                              String ds31,
                              String ds32,
                              String ds33,
                              String ds34,
                              String ds35,
                              String ds36,
                              String ds37, String ds38) {
        SQLiteDatabase db = null;
        try {
            Cursor cursor = null;
            db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            cursor = db.rawQuery("select * from telugu_table where + s1='" + ds1 + "'and s2='" + ds2 + "'and s3='" + ds3 + "'and s4='" + ds4 + "'and s5='" + ds5 + "'and s6='" + ds6 + "'and s7='" + ds7 + "'and s8='" + ds8 + "'and s9='" + ds9 + "'and s10='" + ds10 + "'and s11='" + ds11 + "'and s12='" + ds12 + "'and s13='" + ds13 + "'and s14='" + ds14 + "'and s15='" + ds15 + "'and s16='" + ds16 + "'and s17='" + ds17 + "'and s18='" + ds18 + "'and s19='" + ds19 + "'and s20='" + ds20 + "'and s21='" + ds21 + "'and s22='" + ds22 + "'and s23='" + ds23 + "'and s25='" + ds25 + "'and s26='" + ds26 + "'and s27='" + ds27 + "'and s28='" + ds28 + "'and s29='" + ds29 + "'and s30='" + ds30 + "'and s31='" + ds31 + "'and s32='" + ds32 + "'and s33='" + ds33 + "'and s34='" + ds34 + "'and s35='" + ds35 + "'and s36='" + ds36 + "'and s37='" + ds37 + "'and s38='" + ds38 + "'", null);
            if (cursor.getCount() != 1) {

                contentValues.put(KEY_1, ds1);
                contentValues.put(KEY_2, ds2);
                contentValues.put(KEY_3, ds3);
                contentValues.put(KEY_4, ds4);
                contentValues.put(KEY_5, ds5);
                contentValues.put(KEY_6, ds6);
                contentValues.put(KEY_7, ds7);
                contentValues.put(KEY_8, ds8);
                contentValues.put(KEY_9, ds9);
                contentValues.put(KEY_10, ds10);
                contentValues.put(KEY_11, ds11);
                contentValues.put(KEY_12, ds12);
                contentValues.put(KEY_13, ds13);
                contentValues.put(KEY_14, ds14);
                contentValues.put(KEY_15, ds15);
                contentValues.put(KEY_16, ds16);
                contentValues.put(KEY_17, ds17);
                contentValues.put(KEY_18, ds18);
                contentValues.put(KEY_19, ds19);
                contentValues.put(KEY_20, ds20);
                contentValues.put(KEY_21, ds21);
                contentValues.put(KEY_22, ds22);
                contentValues.put(KEY_23, ds23);
                contentValues.put(KEY_25, ds25);
                contentValues.put(KEY_26, ds26);
                contentValues.put(KEY_27, ds27);
                contentValues.put(KEY_28, ds28);
                contentValues.put(KEY_29, ds29);
                contentValues.put(KEY_30, ds30);
                contentValues.put(KEY_31, ds31);
                contentValues.put(KEY_32, ds32);
                contentValues.put(KEY_33, ds33);
                contentValues.put(KEY_34, ds34);
                contentValues.put(KEY_35, ds35);
                contentValues.put(KEY_36, ds36);
                contentValues.put(KEY_37, ds37);
                contentValues.put(KEY_38, ds38);
                db.insert(TABLE_TELUGU, null, contentValues);
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }


    public int editNotes(String strId, String strDate, String strTitle, String strDesc, String strNoteDateAndTime, String strReminderDate, String strDateAndTimeOrg) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = getReadableDatabase();
        try {
            values.put(KEY_DATE, strDate);
            values.put(KEY_NOTE_ID, strId);
            values.put(KEY_NOTE_TITLE, strTitle);
            values.put(KEY_NOTE_DESC, strDesc);
            values.put(KEY_NOTE_DATE, strNoteDateAndTime);
            values.put(KEY_REMINDER_DATE, strReminderDate);
            values.put(KEY_REMIN_DATE_TIME_ORG, strDateAndTimeOrg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return db.update(TABLE_NOTES, values, KEY_NOTE_ID + " = '" + strId + "'", null);
    }

    public void deleteNotes(String noteId) {
        try {
            SQLiteDatabase db = getReadableDatabase();
//            db.delete(TABLE_NOTES, KEY_NOTE_ID + "=?", new String[]{noteId});

            db.execSQL("delete from " + TABLE_NOTES + " where note_id='" + noteId + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<String>> getSpecificNote(String noteDate) {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM notes_table where date = '" + noteDate + "'", null);
            ArrayList<ArrayList<String>> notesList = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    ArrayList<String> arrNote = new ArrayList<>();
                    arrNote.add(c.getString(1));
                    arrNote.add(c.getString(2));
                    arrNote.add(c.getString(3));
                    arrNote.add(c.getString(4));
                    arrNote.add(c.getString(5));
                    arrNote.add(c.getString(6));
                    arrNote.add(c.getString(7));
                    notesList.add(arrNote);
                }
                while (c.moveToNext());
            }
            return notesList;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<ArrayList<String>> getNotes() {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM notes_table", null);
            ArrayList<ArrayList<String>> notesList = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    ArrayList<String> arrNote = new ArrayList<>();
                    arrNote.add(c.getString(1));
                    arrNote.add(c.getString(2));
                    arrNote.add(c.getString(3));
                    arrNote.add(c.getString(4));
                    arrNote.add(c.getString(5));
                    arrNote.add(c.getString(6));
                    arrNote.add(c.getString(7));
                    notesList.add(arrNote);
                }
                while (c.moveToNext());
            }
            return notesList;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<String> getGuruPeyarchiPalan() {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM gurupeyarchi", null);
            ArrayList<String> palangalList = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    palangalList.add(c.getString(2));
                }
                while (c.moveToNext());
            }
            return palangalList;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }

    public ArrayList<String> getSaniPeyarchiPalan() {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM sanipeyarchi", null);
            ArrayList<String> palangalList = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    palangalList.add(c.getString(2));
                }
                while (c.moveToNext());
            }
            return palangalList;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }


    public ArrayList<ArrayList<String>> getContent() {
        Cursor c = null;
        try {
            SQLiteDatabase db = getWritableDatabase();
            c = db.rawQuery("SELECT * FROM content", null);
            ArrayList<ArrayList<String>> notesList = new ArrayList<>();
            if (c.moveToFirst()) {
                do {
                    ArrayList<String> arrNote = new ArrayList<>();
                    arrNote.add(c.getString(1));
                    arrNote.add(c.getString(2));
                    arrNote.add(c.getString(3));
                    arrNote.add(c.getString(4));
                    arrNote.add(c.getString(5));
                    arrNote.add(c.getString(6));
                    arrNote.add(c.getString(7));
                    arrNote.add(c.getString(8));
                    notesList.add(arrNote);
                }
                while (c.moveToNext());
            }
            return notesList;
        } catch (Exception eb) {
            Log.d("selected words", eb.toString());
        } finally {
            c.close();
        }
        return null;
    }


}









