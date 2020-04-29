package com.ashokcouhan.blooduser.Model;

public class Myorder {

    private String bankid,bankname,group,units,status;

    public Myorder() {
    }

    public Myorder(String bankid, String bankname, String group, String units) {
        this.bankid = bankid;
        this.bankname = bankname;
        this.group = group;
        this.units = units;
        this.status="0";
    }

    public String getBankid() {
        return bankid;
    }

    public void setBankid(String bankid) {
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
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

    public String getGroupName()
    {
        String type="";

        if(this.group.equals("Aposi")){
            type="A+";
        }

        if(this.group.equals("ABposi")){
            type="AB+";
        }
        if(this.group.equals("Aneg")){
            type="A-";
        }if(this.group.equals("Bposi")){
        type="B+";
    }if(this.group.equals("Bneg")){
        type="B-";
    }if(this.group.equals("Oposi")){
        type="O+";
    }
        if(this.group.equals("Oneg")){
            type="O-";
        }
        return type;
    }
}
