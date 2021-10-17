package com.example.hack_it_out_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashBoardRV extends AppCompatActivity {
    RecyclerView serviceRecyclerView;
    ImageView profileBtnDashBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_rv);

        serviceRecyclerView = findViewById(R.id.servicesRecyclerView);
        profileBtnDashBoard = findViewById(R.id.profileBtnDashBoard);
//        SaveTokenPreferences saveTokenPreferences = new SaveTokenPreferences();
//        saveTokenPreferences.myPreferenceManager(DashBoardRV.this);
//        String token = saveTokenPreferences.getToken();


        profileBtnDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoardRV.this, PatientProfile.class);
                startActivity(intent);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HealthCareApi healthCareApi = retrofit.create(HealthCareApi.class);
        String token = "Bearer ";


        Call<ArrayList<Service>> getServices = healthCareApi.getServices(token);
        getServices.enqueue(new Callback<ArrayList<Service>>() {
            @Override
            public void onResponse(Call<ArrayList<Service>> call, Response<ArrayList<Service>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Service> services = response.body();
                    DashBoardAdapter dashBoardAdapter = new DashBoardAdapter(services, DashBoardRV.this, new OnItemClickListener() {
                        @Override
                        public void onItemClick(Service service) {
                            Intent intent = new Intent(DashBoardRV.this, SpecialistsRecyclerView.class);
                            intent.putExtra("serviceId", service.getId());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Service>> call, Throwable t) {
                Toast.makeText(DashBoardRV.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        });


    }
}