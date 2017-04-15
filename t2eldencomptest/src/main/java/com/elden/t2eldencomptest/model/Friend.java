package com.elden.t2eldencomptest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by elden_000 on 4/14/2017.
 */

public class Friend {
    int id;
    @SerializedName("first_name")
    String firstName;
    @SerializedName("last_name")
    String lastName;
    String status;
    String img;
    boolean available;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
