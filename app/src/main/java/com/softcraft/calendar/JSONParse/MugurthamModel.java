package com.softcraft.calendar.JSONParse;

public class MugurthamModel
{
    private   String date;
    private   String day;
//    private   String time;
    public static final String KEY_DATE = "date";
    public static final String KEY_DAY = "day";
//    public static final String KEY_TIME = "time";

    public void setDate(String score)
    {
        this.date = score;
    }
    public void setDay(String score)
    {
        this.day = score;
    }
//    public void setTime(String score)
//    {
//        this.time = score;
//    }
    public  String getDate() {
        return this.date;
    }
    public  String getDay() {
        return this.day;
    }
//    public  String getTime() {
//        return this.time;
//    }
}
