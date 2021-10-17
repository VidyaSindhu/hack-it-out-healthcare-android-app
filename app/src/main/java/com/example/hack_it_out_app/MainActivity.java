package com.example.hack_it_out_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private MaterialButton loginButton;
    String user_email, user_password;
    ImageView showPasswordImageViewLogin;
    private TextView forgotPasswordTextView;
    //private MaterialButton buttonSelect;

    private static final int RC_APP_UPDATE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        BASE_URL = getResources().getString(R.string.BASE_URL);
        emailEditText = (EditText) findViewById(R.id.emailEditTextLogin);
        passwordEditText = (EditText) findViewById(R.id.passwordEditTextLogin);
        loginButton = (MaterialButton) findViewById(R.id.loginButton);
        showPasswordImageViewLogin = findViewById(R.id.showPasswordImageViewLogin);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);


        showPasswordImageViewLogin.setOnClickListener(v -> {
            if (passwordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                showPasswordImageViewLogin.setAlpha(1f);
                showPasswordImageViewLogin.setImageResource(R.drawable.icon_show_password);
                //Show Password
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                showPasswordImageViewLogin.setAlpha(0.5f);
                showPasswordImageViewLogin.setImageResource(R.drawable.icon_show_password);
                //Hide Password
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            passwordEditText.setSelection(passwordEditText.getText().length());
        });


        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if(result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)){
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }

                }
            }
        });



//        forgotPasswordTextView.setOnClickListener(v -> {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(getResources().getString(R.string.BASE_URL))
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//            HealthCareApi HealthCareApi = retrofit.create(HealthCareApi.class);
//
//            if (emailEditText.getText().toString().isEmpty()) {
//                Toast.makeText(MainActivity.this, "Please enter the email address", Toast.LENGTH_SHORT).show();
//            } else {
//                if (isValidEmail(emailEditText.getText().toString())) {
//                    Call<ResponseBody> resetPassword = HealthCareApi.resetPassword(emailEditText.getText().toString());
//                    resetPassword.enqueue(new Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            if(response.isSuccessful()){
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response.body().string());
//                                    boolean success = jsonObject.getBoolean("success");
//                                    String details = jsonObject.getString("details");
//
//                                    if(success){
//                                        Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            if(response.code() == 404){
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
//                                    boolean exists = jsonObject.getBoolean("exists");
//                                    if(!exists){
//                                        Toast.makeText(MainActivity.this, "No user has been registered with that email", Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                            if(response.code()==400){
//                                try {
//                                    JSONObject jsonObject = new JSONObject(response.errorBody().string());
//                                    boolean success = jsonObject.getBoolean("success");
//                                    if(!success){
//                                        Toast.makeText(MainActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Toast.makeText(MainActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                } else {
//                    Toast.makeText(MainActivity.this, getString(R.string.not_valid_email_address), Toast.LENGTH_SHORT).show();
//                }
//            }

//        });
        final SaveTokenPreferences saveTokenPreferences = new SaveTokenPreferences();
        saveTokenPreferences.myPreferenceManager(MainActivity.this);
        String token = saveTokenPreferences.getToken();
        //saveTokenPreferences.clearToken();
        if (!token.isEmpty()) {
            TokenClass tokenClass = new TokenClass(token);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.BASE_URL))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
            HealthCareApi HealthCareApi = retrofit.create(HealthCareApi.class);
            Call<ResponseBody> verifyToken = HealthCareApi.verify(tokenClass);
            verifyToken.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200){
                        Intent intent = new Intent(MainActivity.this, DashBoardRV.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Login session expired, please login again", Toast.LENGTH_SHORT).show();
                }
            });


        }


        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    //do what you want on the press of 'done'
                    loginButton.performClick();
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DashBoardRV.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                }
        });
    }


    public void newAccount(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterNew.class);
        startActivity(intent);
    }

    public final static boolean isValidEmail(String target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}