package com.example.hack_it_out_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PatientProfile extends AppCompatActivity {
    TextView orderHistoryTextView;
    Button signOutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        orderHistoryTextView = findViewById(R.id.orderHistoryTextView);
        signOutBtn = findViewById(R.id.signOutBtn);

        orderHistoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProfile.this, AppointmentHistory.class);
                startActivity(intent);
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SaveTokenPreferences saveTokenPreferences = new SaveTokenPreferences();
                saveTokenPreferences.myPreferenceManager(PatientProfile.this);
                saveTokenPreferences.clearToken();
                Intent intent = new Intent(PatientProfile.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}