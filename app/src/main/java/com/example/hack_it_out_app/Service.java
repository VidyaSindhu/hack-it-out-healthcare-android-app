package com.example.hack_it_out_app;

import com.google.gson.annotations.SerializedName;

public class Service {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("price")
    double price;

    @SerializedName("duration")
    int duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
