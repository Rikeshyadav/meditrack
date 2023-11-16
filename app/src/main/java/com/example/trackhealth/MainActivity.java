package com.example.trackhealth;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView t1,t2;
    EditText e1,e2;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=findViewById(R.id.appname);
        t2=findViewById(R.id.regclick);
        e1=findViewById(R.id.email);
        e2=findViewById(R.id.pass);
        login=findViewById(R.id.login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent t2 =new Intent(MainActivity.this, RegisterPage.class);
              startActivity(t2);
            }


        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e1.getText().toString();
                String pass = e2.getText().toString();
                if (email.equals("") && pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "credential required!", Toast.LENGTH_SHORT).show();
                }
                else if (email.equals("")) {
                    Toast.makeText(getApplicationContext(), "enter username!", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "enter password!", Toast.LENGTH_SHORT).show();
                } else {

                    Intent b1 = new Intent(MainActivity.this, HomePage.class);
                    Toast.makeText(getApplicationContext(), "welcome "+email, Toast.LENGTH_SHORT).show();
                    b1.putExtra("username",email);
                    startActivity(b1);
                }
            }
        });

    }

    public void onBackPressed(){
        super.onBackPressed();
        finishAffinity();
    }

}