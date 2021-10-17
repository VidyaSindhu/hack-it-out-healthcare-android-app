package com.example.hack_it_out_app;

import com.google.gson.annotations.SerializedName;

public class AppointmentHistoryModel {
    @SerializedName("id")
    int id;

    @SerializedName("specialitst")
    String specialist;

    @SerializedName("appointment_on")
    String appointmentOn;

    @SerializedName("service")
    String service;

    @SerializedName("description")
    String description;

    @SerializedName("status")
    String status;

    @SerializedName("price")
    double price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public String getAppointmentOn() {
        return appointmentOn;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAppointmentOn(String appointmentOn) {
        this.appointmentOn = appointmentOn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
