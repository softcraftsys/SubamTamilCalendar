package com.softcraft.calendar.JSONParse;

/**
 * Created by Softcraft Systems on 12/27/2016.
 */
public class DhinapalanTextModel
{
    private   String id;
    private   String content;
    //    private   String time;
    public static final String KEY_ID = "id";
    public static final String KEY_CONTENT = "content";
//    public static final String KEY_TIME = "time";

    public void setId(String score)
    {
        this.id = score;
    }
    public void setContent(String score)
    {
        this.content = score;
    }
    //    public void setTime(String score)
//    {
//        this.time = score;
//    }
    public  String getId() {
        return this.id;
    }
    public  String getContent() {
        return this.content;
    }
//    public  String getTime() {
//        return this.time;
//    }
}

