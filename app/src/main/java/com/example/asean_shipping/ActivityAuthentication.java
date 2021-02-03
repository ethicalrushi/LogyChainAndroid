package com.example.asean_shipping;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.asean_shipping.model.auth.LoginPayload;
import com.example.asean_shipping.model.auth.LoginResponse;
import com.example.asean_shipping.model.auth.SignupPayload;
import com.example.asean_shipping.model.auth.UserDetailResponse;
import com.example.asean_shipping.restApi.APIServices;
import com.example.asean_shipping.restApi.AppClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityAuthentication extends BaseActivity {

    private ScrollView signInScrollView, signUpScrollView;
    private TextView textSignIn, textSignUp;
    private Button authDropDown, authProceedButton;
    private boolean showSignup = true;
    private TextInputEditText signupname, signupCompanyName, signInContact, signInPass, signupCity, signupAddress, signupState, signupZip, signupPublicAddress;
    private TextInputEditText signuppassword, signupLicenseNumber;
    private TextInputEditText signupcontact;
    private Map<String, String> usertypechoices;
    private CardView authProceedLay;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
        setUpOnClickListeners();
    }

    private void initViews() {
        signInScrollView = findViewById(R.id.auth_scroll_sign_in);
        signUpScrollView = findViewById(R.id.auth_scroll_sign_up);

        textSignIn = findViewById(R.id.auth_text_sign_in);
        textSignUp = findViewById(R.id.auth_text_sign_up);

        authProceedButton = findViewById(R.id.auth_proceed_btn);
        authProceedLay = findViewById(R.id.auth_proceed);

        signupname = findViewById(R.id.signupname);
        signupCompanyName = findViewById(R.id.signupCompanyName);
        signupcontact = findViewById(R.id.signupcontact);
        signuppassword = findViewById(R.id.signuppassword);
        signupAddress = findViewById(R.id.signupaddress);
        signupCity = findViewById(R.id.signupcity);
        signupState = findViewById(R.id.signupstate);
        signupZip = findViewById(R.id.signupZip);
        signupPublicAddress = findViewById(R.id.signupPublicAddress);
        signupLicenseNumber = findViewById(R.id.signupLicenseNumber);


        signInContact = findViewById(R.id.sign_in_contact);
        signInPass = findViewById(R.id.sign_in_pass);

        usertypechoices = new HashMap<>();
        usertypechoices.put("Shipper/Receiver", "SHP");
        usertypechoices.put("Shipment Agency", "SPM");
        usertypechoices.put("Intermediate Handler", "INT");

        authDropDown = findViewById(R.id.auth_menu_drop_down);

        authProceedButton.setOnClickListener(v -> {
            if (showSignup) dosignupcall();
            else dologincall();

            authProceedLay.setVisibility(View.GONE);
        });
    }

    void dosignupcall() {
        SignupPayload payload = new SignupPayload();

        payload.name = signupname.getText().toString();
        payload.companyName = signupCompanyName.getText().toString();
        payload.password = signuppassword.getText().toString();
        payload.contact = signupcontact.getText().toString();
        payload.role = usertypechoices.get(authDropDown.getText().toString());
        payload.city = signupCity.getText().toString();
        payload.state = signupState.getText().toString();
        payload.address = signupAddress.getText().toString();
        payload.zipcode = signupZip.getText().toString();
        payload.publicAddress = signupPublicAddress.getText().toString();
        payload.licenseNumber = signupLicenseNumber.getText().toString();

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<SignupPayload> call = apiServices.sendSignupRequest(payload);

        call.enqueue(new Callback<SignupPayload>() {
            @Override
            public void onResponse(Call<SignupPayload> call, Response<SignupPayload> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ActivityAuthentication.this, "Signup success. Please login", Toast.LENGTH_LONG).show();
                    toLogin();

                } else {
                    Toast.makeText(ActivityAuthentication.this, "Signup failed", Toast.LENGTH_LONG).show();
                    authProceedLay.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<SignupPayload> call, Throwable t) {
                Toast.makeText(ActivityAuthentication.this, t.getMessage(), Toast.LENGTH_LONG).show();
                authProceedLay.setVisibility(View.VISIBLE);
            }
        });

    }

    void goToDash(String role) {
        switch (role) {

            default:
                Intent intent = new Intent(ActivityAuthentication.this, Dashboard.class);
                startActivity(intent);
                finish();
        }
    }

    void routetodashboard() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        apiServices.getUserDetail(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", ""))
                .enqueue(new Callback<UserDetailResponse>() {
                    @Override
                    public void onResponse(Call<UserDetailResponse> call, Response<UserDetailResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                            editor.putString("role", response.body().getRole());
                            editor.putString("name", response.body().getName());
                            editor.apply();

                            goToDash(response.body().getRole());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDetailResponse> call, Throwable t) {
                        Toast.makeText(ActivityAuthentication.this, "something went wrong", Toast.LENGTH_LONG).show();
                    }
                });
    }

    void dologincall() {
        LoginPayload payload = new LoginPayload();

        payload.setUsername(signInContact.getText().toString());
        payload.setPassword(signInPass.getText().toString());

        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<LoginResponse> call = apiServices.sendLoginRequest(payload);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ActivityAuthentication.this, "login success", Toast.LENGTH_LONG).show();

                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                    editor.putString("token", "Token " + response.body().getToken());
                    editor.putBoolean("logged_in", true);
                    editor.apply();
//
                    Log.d("token", "onResponse() returned: " + response.body().getToken());

                    routetodashboard();

                } else {
                    Toast.makeText(ActivityAuthentication.this, "login failed", Toast.LENGTH_LONG).show();

                    if(authProceedButton.getVisibility() == View.GONE)
                        authProceedButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(ActivityAuthentication.this, t.getMessage(), Toast.LENGTH_LONG).show();

                if(authProceedButton.getVisibility() == View.GONE)
                    authProceedButton.setVisibility(View.VISIBLE);
            }
        });
    }

    void toLogin() {
        signInScrollView.setVisibility(View.VISIBLE);

        textSignIn.setTextColor(getResources().getColor(android.R.color.black));
        textSignUp.setTextColor(getResources().getColor(android.R.color.darker_gray));
        signUpScrollView.animate().alpha(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
        signInScrollView.animate().alpha(1.0f).setDuration(300).setInterpolator(new AccelerateInterpolator()).start();
        showSignup = false;

        signUpScrollView.setVisibility(View.GONE);
    }

    private void setUpOnClickListeners() {
        textSignIn.setOnClickListener(view -> toLogin());

        textSignUp.setOnClickListener(view -> {
            signUpScrollView.setVisibility(View.VISIBLE);

            textSignIn.setTextColor(getResources().getColor(android.R.color.darker_gray));
            textSignUp.setTextColor(getResources().getColor(android.R.color.black));
            signUpScrollView.animate().alpha(1.0f).setDuration(300).setInterpolator(new AccelerateInterpolator()).start();
            signInScrollView.animate().alpha(0).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
            showSignup = true;

            signInScrollView.setVisibility(View.GONE);
        });

        authDropDown.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(ActivityAuthentication.this, authDropDown);
            popupMenu.getMenuInflater().inflate(R.menu.menu_auth_choice, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                authDropDown.setText(menuItem.getTitle());
                return true;
            });

            popupMenu.show();
        });
    }
}