package com.ashokcouhan.blooduser;


public class MyOrder
{
   private String bankid,bankname,group,units,status;

    public MyOrder(String bankid, String bankname, String group, String units) {
        this.bankid = bankid;
        this.bankname = bankname;
        this.group = group;
        this.units = units;
        this.status="0";
    }

    public MyOrder(String bankid, String bankName, String group, String units, String status) {
        this.bankid = bankid;
        this.bankname = bankName;
        this.group = group;
        this.units = units;
        this.status = status;
    }

    public MyOrder() {
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBankName() {
        return bankname;
    }

    public void setBankName(String bankname) {
        this.bankname = bankname;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
