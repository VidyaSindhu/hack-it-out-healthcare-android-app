package com.example.hack_it_out_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class AppointmentHistory extends AppCompatActivity {
    RecyclerView appointmentHistoryRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);

        appointmentHistoryRecyclerView = findViewById(R.id.appointmentHistoryRecyclerView);


    }
}