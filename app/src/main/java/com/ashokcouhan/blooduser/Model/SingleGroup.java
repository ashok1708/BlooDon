package com.ashokcouhan.blooduser.Model;

public class SingleGroup {
    String name,qunatity,group,location,id;

    public SingleGroup(String name, String qunatity,String group,String location,String id) {
        this.name = name;
        this.qunatity = qunatity;
        this.group=group;
        this.location=location;
        this.id=id;
    }

    public SingleGroup() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQunatity() {
        return qunatity;
    }

    public void setQunatity(String qunatity) {
        this.qunatity = qunatity;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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
}
