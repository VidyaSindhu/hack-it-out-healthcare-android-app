package com.example.hack_it_out_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import at.markushi.ui.CircleButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterNew extends AppCompatActivity {
    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText addressEditText;
    Button registerBtn;
    CircleButton backBtnRegister;
    ImageView showPasswordImageView0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new);


        nameEditText = (EditText)findViewById(R.id.nameEditText);
        emailEditText = (EditText)findViewById(R.id.emailEditText);
        passwordEditText = (EditText)findViewById(R.id.passwordEditText);
        addressEditText = (EditText)findViewById(R.id.addressEditText);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        backBtnRegister = (CircleButton)findViewById(R.id.backBtnRegister);
        showPasswordImageView0 = findViewById(R.id.showPasswordImageView0);

        showPasswordImageView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                    showPasswordImageView0.setAlpha(1f);
                    showPasswordImageView0.setImageResource(R.drawable.icon_show_password);

                    //Show Password
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    showPasswordImageView0.setAlpha(0.5f);
                    showPasswordImageView0.setImageResource(R.drawable.icon_show_password);

                    //Hide Password
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
                passwordEditText.setSelection(passwordEditText.getText().length());
            }
        });

        addressEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    registerBtn.performClick();
                }
                return false;
            }
        });

        backBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name  = nameEditText.getText().toString();
                String user_email  = emailEditText.getText().toString();
                String user_password  = passwordEditText.getText().toString();
                String user_address  = addressEditText.getText().toString();
                final Customer customer = new Customer(user_name, user_email, user_password, user_address);
                if(user_name.isEmpty() || user_email.isEmpty() || user_password.isEmpty() || user_address.isEmpty()) {
                    Toast.makeText(RegisterNew.this, "Fill every fields above", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(isValidEmail(user_email)){
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(getString(R.string.BASE_URL))
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        HealthCareApi HealthCareApi = retrofit.create(HealthCareApi.class);
                        Call<String> checkEmail = HealthCareApi.checkEmail(customer.getUser_email());
                        checkEmail.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.isSuccessful())
                                {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body());
                                        boolean registered = jsonObject.getBoolean("exists");
                                        if(!registered)
                                        {
                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(getResources().getString(R.string.BASE_URL))
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();
                                            final HealthCareApi HealthCareApi = retrofit.create(HealthCareApi.class);
                                                Call<ResponseBody> signUp = HealthCareApi.newCustomer(customer);
                                                signUp.enqueue(new Callback<ResponseBody>() {

                                                    @Override
                                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                        if(response.isSuccessful()){
                                                            SaveTokenPreferences saveTokenPreferences = new SaveTokenPreferences();
                                                            saveTokenPreferences.myPreferenceManager(RegisterNew.this);

                                                            try {
                                                                JSONObject jsonObject = new JSONObject(response.body().string());

                                                                boolean everything_ok = jsonObject.getBoolean("success");
                                                                if(everything_ok){
                                                                    String token = jsonObject.getString("token");
                                                                    saveTokenPreferences.saveToken(token);
                                                                    Intent intent = new Intent(RegisterNew.this, DashBoardRV.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                    intent.putExtra("EXIT", true);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                                else{
                                                                    Toast.makeText(RegisterNew.this,"Something went wrong", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } catch (JSONException | IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                        Toast.makeText(RegisterNew.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        }
                                        else {
                                            Toast.makeText(RegisterNew.this, "Email is already registered. Please Login", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(RegisterNew.this, "Something went wrong. Please Try Again Later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
//                                    Toast.makeText(RegisterNew.this, "Something went wrong. Please Try Again Later", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(RegisterNew.this, "Something went wrong. Please Try Again Later", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else{
                        Toast.makeText(RegisterNew.this, "Enter a valid email address", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
    public final static boolean isValidEmail(String target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}