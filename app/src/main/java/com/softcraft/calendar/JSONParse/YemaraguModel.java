package com.softcraft.calendar.JSONParse;

public class YemaraguModel
{
    private   String Rahu;
    private   String Yema;
    private   String Kuligai;
    private   String Soolam;
    private   String Day;
    private   String NNMor;
    private   String NNEve;
    public static final String KEY_RAHU = "Rahu";
    public static final String KEY_YEMA = "Yema";
    public static final String KEY_KULIGAI = "kuligai";
    public static final String KEY_SOOLAM = "Soolam";
    public static final String KEY_DAY = "Day";
    public static final String KEY_NNMOR = "NNMor";
    public static final String KEY_NNEVE = "NNEve";

    public void setRahu(String score)
    {
        this.Rahu = score;
    }
    public void setYema(String score)
    {
        this.Yema = score;
    }
    public void setKuligai(String score)
    {
        this.Kuligai = score;
    }
    public void setSoolam(String score)
    {
        this.Soolam = score;
    }
    public void setDay(String score)
    {
        this.Day = score;
    }
    public void setNNMor(String score)
    {
        this.NNMor = score;
    }
    public void setNNEve(String score)
    {
        this.NNEve = score;
    }


    public  String getRahu()
    {
        return this.Rahu;
    }
    public  String getYema()
    {
        return this.Yema;
    }
    public  String getKuligai()
    {
        return this.Kuligai;
    }
    public  String getSoolam()
    {
        return this.Soolam;
    }
    public  String getDay()
    {
        return this.Day;
    }
    public  String getNNMor()
    {
        return this.NNMor;
    }
    public  String getNNEve()
    {
        return this.NNEve;
    }
}
