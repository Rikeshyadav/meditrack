package com.example.trackhealth;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import static android.widget.Toast.makeText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
    TextView t1,t2,forgot;
    EditText e1,e2;
    String doctororpatient="null";
    Button login;
    ProgressBar progressBar;

    SharedPreferences sp,boot;

     String patientMsg ="";
     boolean bol=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sp=getSharedPreferences("user",MODE_PRIVATE);
        boot=getSharedPreferences("boot",MODE_PRIVATE);
        t1=findViewById(R.id.appname);
        t2=findViewById(R.id.regclick);
        e1=findViewById(R.id.email);
        e2=findViewById(R.id.pass);
        Spinner docpat2=findViewById(R.id.loginSpinner);
        progressBar=findViewById(R.id.progress);
        login=findViewById(R.id.login);
        forgot=findViewById(R.id.forgotpass);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String[] st = {"Select", "Doctor", "Patient"};
        RegisterSpinnerApdater adapter = new RegisterSpinnerApdater(this, R.layout.spinner1, st);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        docpat2.setAdapter(adapter);
        docpat2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedOption = (String) adapterView.getItemAtPosition(i);

                if (selectedOption.equals("Doctor")) {
                             doctororpatient="Doctor";
                }
                else if (selectedOption.equals("Patient")) {
                    doctororpatient="Patient";
                }
                else if (selectedOption.equals("Select"))
                {
                    doctororpatient="null";
                }
                else{
                    doctororpatient="null";
                }

            }
              @Override
           public void onNothingSelected(AdapterView<?> adapterView) {
doctororpatient="null";
                                             }
                                         });


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // setAlert("Do you want to recover your password?","yes","no");
Intent i=new Intent(LoginActivity.this,ForgotPassword.class);
startActivity(i);
            }
        });

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
                    if(!doctororpatient.equals("null")) {

                        progressBar.setVisibility(View.VISIBLE);
                        authPatient(phone, pass,doctororpatient);
                        //progressBar.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "empty selection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }




    public void authPatient(String phone, String password, String identity){
        String temp="";
        if(identity.equals("Patient")) {
            temp = "https://demo-uw46.onrender.com/api/patient/auth";
        }
        else{
            temp = "https://demo-uw46.onrender.com/api/doctor/auth";
        }

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
                       sp.edit().putString("name",response.getString("username")).apply();
                       boot.edit().putBoolean("islogged",true).apply();
                        b1.putExtra("email", response.getString("email"));
                        b1.putExtra("phone", phone);
                        b1.putExtra("pass", password);
                        b1.putExtra("identity",doctororpatient);
                        b1.putExtra("address", response.getString("address"));
                        if(identity.equals("Doctor")) {
                         b1.putExtra("speciality",response.getString("speciality"));
                        }
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
                Toast.makeText(getApplicationContext(),"check your internet connection", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
        RequestQueue q= Volley.newRequestQueue(LoginActivity.this);

        q.add(j);

    }
    public void setAlert(String msg,String pos,String neg){
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage(msg);
        b.setPositiveButton(pos,(DialogInterface.OnClickListener) (dialog, which)->{

            if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                progressBar.setVisibility(View.VISIBLE);
               // sendmsg(e1.getText().toString());
                progressBar.setVisibility(View.GONE);
            }else{

                ActivityCompat.requestPermissions(LoginActivity.this,new String[]{Manifest.permission.SEND_SMS},100);
            }
        });
        b.setNegativeButton(neg,(DialogInterface.OnClickListener) (dialog,which)->{
                  dialog.cancel();
        });
        AlertDialog ad=b.create();
        ad.show();

    }


    public void onBackPressed(){
        super.onBackPressed();
        finishAffinity();
    }



}