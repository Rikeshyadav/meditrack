package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
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

public class ForgotPassword extends AppCompatActivity {
   EditText phone,getOtp;
   ProgressBar pb;
   TextView result;
   AppCompatButton but,proceed;
    String otp="",pass="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
but=findViewById(R.id.ForgetotpButton);
phone=findViewById(R.id.phoneForgetPage);
result=findViewById(R.id.resultforgotpass);
getOtp=findViewById(R.id.otp_ForgotPage);
proceed=findViewById(R.id.ForgetProceedButton);
pb=findViewById(R.id.forgotprogress);


    proceed.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String ph = phone.getText().toString().trim();
            if (!ph.equals("") && ph.length() == 10) {

                getpass(ph);


            } else {
               //pb.setVisibility(View.GONE);
                getOtp.setVisibility(View.GONE);
                but.setVisibility(View.GONE);
                proceed.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"invalid number", Toast.LENGTH_SHORT).show();


            }
        }
    });

    but.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String enterOtp = getOtp.getText().toString().trim();
            if(enterOtp.equals(otp) && !enterOtp.equals("")){
             result.setText("**Your password is "+pass);
                getOtp.setVisibility(View.GONE);
                but.setVisibility(View.GONE);
                proceed.setVisibility(View.VISIBLE);
            }
            else{
                result.setText("otp doesn't matched");
            }
        }
    });
}




    public void getOtp(String phone){
        String token="00501cc79e211e158fb6a251af6c5f32";
        String temp = "https://demo-uw46.onrender.com/api/getotp";
        HashMap<String,String> jsonobj=new HashMap<>();
        jsonobj.put("token",token);
        jsonobj.put("phone", "+91"+phone);

        JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp,new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(Boolean.parseBoolean(response.getString("success"))){

                       otp=response.getString("otp");
                        Toast.makeText(getApplicationContext(),"otp sent", Toast.LENGTH_LONG).show();
                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("sorry : "+error);
            }
        });
        RequestQueue q= Volley.newRequestQueue(ForgotPassword.this);

        q.add(j);
    }


    public void getpass(String myphone){
        myphone=myphone.trim();
        String temp = "https://demo-uw46.onrender.com/api/patient/getPassword";
        try{
            pb.setVisibility(View.VISIBLE);
            HashMap<String,String> jsonobj=new HashMap<>();
            jsonobj.put("phone",myphone);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp,new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if(Boolean.parseBoolean(response.getString("success"))){
                            pass=response.getString("password");

                                pb.setVisibility(View.VISIBLE);
                                sendmsg(phone.getText().toString().trim());
                                pb.setVisibility(View.GONE);

                            pb.setVisibility(View.GONE);
                            getOtp.setVisibility(View.VISIBLE);
                            but.setVisibility(View.VISIBLE);
                            proceed.setVisibility(View.GONE);
                        }
else{

                            Toast.makeText(getApplicationContext(),"user doesn't exists", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {

                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            RequestQueue q= Volley.newRequestQueue(ForgotPassword.this);

            q.add(j);


        }catch (Exception e){

        }
    }


    public void sendmsg(String myphone){
        myphone=myphone.trim();
        if(myphone.equals("")){
            Toast.makeText(getApplicationContext(),"please enter phone no.",Toast.LENGTH_SHORT).show();
        }
        String temp = "https://demo-uw46.onrender.com/api/getotpsms";
        try{
            pb.setVisibility(View.VISIBLE);
            //  HashMap<String,String> jsonobj=new HashMap<>();
            //jsonobj.put("phone",myphone);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, temp,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    pb.setVisibility(View.GONE);
                    try {

                        if(Boolean.parseBoolean(response.getString("success"))){

                            if(ContextCompat.checkSelfPermission(ForgotPassword.this, android.Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                                pb.setVisibility(View.VISIBLE);
                                SmsManager sms=SmsManager.getDefault();
                                otp=response.getString("otp");
                                sms.sendTextMessage(phone.getText().toString().trim(),null,"Your OTP for TrackHealth App verification is "+response.getString("otp"),null,null);
                                pb.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"otp sent",Toast.LENGTH_LONG).show();
                                pb.setVisibility(View.GONE);
                            }else{

                                ActivityCompat.requestPermissions(ForgotPassword.this,new String[]{Manifest.permission.SEND_SMS},100);
                            }


                        }

                    } catch (JSONException e) {
                        pb.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("sorry : "+error);
                }
            });
            RequestQueue q= Volley.newRequestQueue(ForgotPassword.this);

            q.add(j);


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100 && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //sendmsg(e1.getText().toString());

        }
        else{
            getOtp.setVisibility(View.GONE);
            but.setVisibility(View.GONE);
            proceed.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Permission Denied!, kindly allow sms permission", Toast.LENGTH_LONG).show();
        }
    }


}