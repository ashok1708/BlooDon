package com.ashokcouhan.blooduser.Model;

public class Requests {
    private String name;
    private String mobile;
    private String address;
    private String group;
    private String unit;
    private String status;



    public Requests() {
    }

    public Requests(String name, String mobile, String address, String group, String unit) {
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.group = group;
        this.unit = unit;
        this.status="0"; //default 0  , 0: pending, 1: accepted

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
