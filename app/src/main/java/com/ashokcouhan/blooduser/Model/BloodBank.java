package com.ashokcouhan.blooduser.Model;

import java.util.ArrayList;

public class BloodBank {
    private String Name,password,location,id;
    private ArrayList<Group> group ;

    public BloodBank(String password, String id) {
        this.password = password;
        this.id = id;
    }

    public BloodBank() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Group> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<Group> group) {
        this.group = group;
    }
}
