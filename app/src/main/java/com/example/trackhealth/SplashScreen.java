package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
sp=getSharedPreferences("boot",MODE_PRIVATE);
boolean islogged=sp.getBoolean("islogged",false);

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
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i = new Intent(SplashScreen.this, HomePage.class);
                    startActivity(i);
                    finish();
                }

            },1160);
        }


}}