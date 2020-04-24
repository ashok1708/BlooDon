package com.ashokcouhan.blooduser.Model;

public class Posts {
    String campName,descrip,url;

    public Posts(String campName, String descrip, String url) {
        this.campName = campName;
        this.descrip = descrip;
        this.url = url;
    }

    public Posts() {
    }

    public String getCampName() {
        return campName;
    }

    public void setCampName(String campName) {
        this.campName = campName;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
