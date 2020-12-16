package com.softcraft.calendar.MarriageMatch;

public class NatchathiramVO {
    private Integer natchathiramID;
    private String natchathiramName;

    public NatchathiramVO(Integer num, String str) {
        this.natchathiramID = num;
        this.natchathiramName = str;
    }

    public Integer getNatchathiramID() {
        return this.natchathiramID;
    }

    public void setNatchathiramID(Integer num) {
        this.natchathiramID = num;
    }

    public String getNatchathiramName() {
        return this.natchathiramName;
    }

    public void setNatchathiramName(String str) {
        this.natchathiramName = str;
    }

    public String toString() {
        return this.natchathiramName;
    }
}
