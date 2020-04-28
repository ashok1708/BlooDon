package com.ashokcouhan.blooduser.Model;

public class Mydonation {
    private String date,camp,location;

    public Mydonation() {
    }

    public Mydonation(String date, String camp, String location) {
        this.date = date;
        this.camp = camp;
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCamp() {
        return camp;
    }

    public void setCamp(String camp) {
        this.camp = camp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
