package com.softcraft.calendar.JSONParse;

public class KirthigaiModel
{
    private   String kirthigai;
    private   String amavasai;
    private   String pournami;
    public static final String KEY_KIRTHIGAI = "kirthigai";
    public static final String KEY_AMAVASAI = "amavasai";
    public static final String KEY_POURNAMI = "pournami";

    public void setKirthigai(String score)
    {
        this.kirthigai = score;
    }
    public void setKeyAmavasai(String score)
    {
        this.amavasai = score;
    }
    public void setKeyPournami(String score)
    {
        this.pournami = score;
    }
    public  String getKirthigai() {
        return this.kirthigai;
    }
    public  String getAmavasai() {
        return this.amavasai;
    }
    public  String getPournami() {
        return this.pournami;
    }
}
