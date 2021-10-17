package com.example.hack_it_out_app;

import com.google.gson.annotations.SerializedName;

public class TokenClass {
    @SerializedName("token")
    String token;

    public TokenClass(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
