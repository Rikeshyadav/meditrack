package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {
SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
sp=getSharedPreferences("user",MODE_PRIVATE);

boolean islogged=sp.getBoolean("islogged",false);
String user=sp.getString("identity","").trim();


        if(!islogged) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent i = new Intent(SplashScreen.this, DescriptionPage.class);
                    startActivity(i);
                    finish();

            }
        },4000);

    }
        else {

            if (user.equals("Doctor")) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(SplashScreen.this, HomePage_Doctor.class);
                        startActivity(i);
                        finish();
                    }

                }, 1160);
            }
            else if(user.equals("Patient")){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(SplashScreen.this, HomePage_Patient.class);
                        startActivity(i);
                        finish();
                    }

                }, 1160);
            }
            else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i = new Intent(SplashScreen.this, DescriptionPage.class);
                        startActivity(i);
                        finish();

                    }
                },4000);

            }

        }

}}