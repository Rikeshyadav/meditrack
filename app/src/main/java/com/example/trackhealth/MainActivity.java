package com.example.trackhealth;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView t1,t2;
    EditText e1,e2;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=findViewById(R.id.appname);
        t2=findViewById(R.id.regclick);
        e1=findViewById(R.id.emaenter);
        e2=findViewById(R.id.pass);
        b1=findViewById(R.id.login);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent t2 =new Intent(MainActivity.this,register.class);
              startActivity(t2);
            }


        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}