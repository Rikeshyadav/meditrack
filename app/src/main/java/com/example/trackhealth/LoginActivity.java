package com.example.trackhealth;
import androidx.appcompat.app.AppCompatActivity;
import static android.widget.Toast.makeText;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    TextView t1,t2;
    EditText e1,e2;
    Button login;
    ProgressBar progressBar;

     String patientMsg ="";
     boolean bol=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        t1=findViewById(R.id.appname);
        t2=findViewById(R.id.regclick);
        e1=findViewById(R.id.email);
        e2=findViewById(R.id.pass);
        progressBar=findViewById(R.id.progress);
        login=findViewById(R.id.login);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent t2 =new Intent(LoginActivity.this, RegisterPage.class);
              startActivity(t2);
            }


        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = e1.getText().toString();
                String pass = e2.getText().toString();
                if (phone.equals("") && pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "credential required!", Toast.LENGTH_SHORT).show();
                }
                else if (phone.equals("")) {
                    Toast.makeText(getApplicationContext(), "enter phone no.!", Toast.LENGTH_SHORT).show();
                } else if (pass.equals("")) {
                    Toast.makeText(getApplicationContext(), "enter password!", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                          authPatient(phone,pass);
                }
            }
        });

    }

    public boolean authPatient(String phone,String password){
        String temp="https://demo-uw46.onrender.com/api/patient/auth";
        final boolean[] patientAuth = {false};
        HashMap<String,String> jsonobj=new HashMap<>();
        jsonobj.put("password", password);
        jsonobj.put("phone", phone);
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp,new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
progressBar.setVisibility(View.GONE);
                try {
                    if(Boolean.parseBoolean(response.getString("success"))){
                        Intent b1 = new Intent(LoginActivity.this, HomePage.class);
                        Toast.makeText(getApplicationContext(),response.getString("msg"), Toast.LENGTH_SHORT).show();
                        b1.putExtra("username", response.getString("username"));
                        startActivity(b1);
                    }
                    else{

                        Toast.makeText(getApplicationContext(),response.getString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("sorry : "+error);
            }
        });
        RequestQueue q= Volley.newRequestQueue(LoginActivity.this);

        q.add(j);
        bol=patientAuth[0];
return patientAuth[0];
    }

    public void onBackPressed(){
        super.onBackPressed();
        finishAffinity();
    }

}