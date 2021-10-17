package com.example.hack_it_out_app;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable{
    @SerializedName("name")
    String user_name;
    @SerializedName("password")
    String user_password;
    @SerializedName("email")
    String user_email;
    @SerializedName("address")
    String user_address;

    public Customer(String user_name, String user_email, String user_password, String user_address) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_email = user_email;
        this.user_address = user_address;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }
}
