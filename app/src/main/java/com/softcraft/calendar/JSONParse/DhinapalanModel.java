package com.softcraft.calendar.JSONParse;

public class DhinapalanModel
{
    private   String daycount;
    private   String data;
    private   String year;
    public static final String KEY_DAYCOUNT = "daycount";
    public static final String KEY_DATA = "data";
    public static final String KEY_YEAR = "year";

    public void setDaycount(String score)
    {
        this.daycount = score;
    }
    public void setData(String score)
    {
        this.data = score;
    }
    public void setYear(String score)
    {
        this.year = score;
    }

    public  String getDaycount()
    {
        return this.daycount;
    }
    public  String getData()
    {
        return this.data;
    }
    public  String getYear()
    {
        return this.year;
    }
}
