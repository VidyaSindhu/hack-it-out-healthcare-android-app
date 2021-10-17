package com.example.hack_it_out_app;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveTokenPreferences {
    private static final String STATIC_PREFERS = "momsPizzaToken";
    private static final String TOKEN = "token";
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public void myPreferenceManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(STATIC_PREFERS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public void saveToken(String token){
        editor.putString(TOKEN, token);
        editor.apply();
    }

    public String getToken(){
        return sharedPreferences.getString(TOKEN, "");
    }

    public void clearToken(){
        editor.remove(TOKEN);
        editor.apply();
    }
}
