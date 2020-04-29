package com.ashokcouhan.blooduser.Model;

public class User {
    private String name,password,mobile,fatherName,dob,sex,bloodGroup,address;



    public User(String password, String mobile) {
        this.password = password;
        this.mobile = mobile;
    }

    public User(String name, String password, String mobile, String fatherName, String dob, String sex, String bloodGroup, String address) {
        this.name = name;
        this.password = password;
        this.mobile = mobile;
        this.fatherName = fatherName;
        this.dob = dob;
        this.sex = sex;
        this.bloodGroup = bloodGroup;
        this.address = address;

    }



    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }






}
