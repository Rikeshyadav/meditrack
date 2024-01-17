package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ForgotPassword extends AppCompatActivity {
   EditText phone,getOtp;
   ProgressBar pb;
   FirebaseAuth auth;
    Spinner spinner;
   String doctororpatient="null",verificationId="";

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
        spinner=findViewById(R.id.forgotSpinner);
        auth= FirebaseAuth.getInstance();
        String[] st = {"Select", "Doctor", "Patient"};
        RegisterSpinnerApdater adapter = new RegisterSpinnerApdater(this, R.layout.spinner1, st);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              String selectedOption = (String) adapterView.getItemAtPosition(i);

              if (selectedOption.equals("Doctor")) {
                  doctororpatient = "Doctor";
              } else if (selectedOption.equals("Patient")) {
                  doctororpatient = "Patient";
              } else if (selectedOption.equals("Select")) {
                  doctororpatient = "null";
              } else {
                  doctororpatient = "null";
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });






    proceed.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String ph = phone.getText().toString().trim();
            if (!ph.equals("") && ph.length() == 10) {
                //pb.setVisibility(View.VISIBLE);
                getpass(ph);

                //sendmsg(ph);

                //but.setVisibility(View.VISIBLE);
                //getOtp.setVisibility(View.VISIBLE);




            } else {
               //pb.setVisibility(View.GONE);
                getOtp.setVisibility(View.GONE);
                but.setVisibility(View.GONE);
                proceed.setVisibility(View.VISIBLE);
                phone.setText(ph);
                Toast.makeText(getApplicationContext(),"invalid number", Toast.LENGTH_SHORT).show();


            }
        }
    });

    but.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            pb.setVisibility(View.VISIBLE);
            if(!getOtp.getText().toString().trim().equals("")) {
                String enterOtp = getOtp.getText().toString().trim();
                signin(enterOtp);
            }
            else{
                pb.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"enter otp", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),"check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue q= Volley.newRequestQueue(ForgotPassword.this);

        q.add(j);
    }


    public void getpass(String myphone){
        myphone=myphone.trim();
        String temp="";
        if(doctororpatient.equals("Patient")) {
            temp = "https://demo-uw46.onrender.com/api/patient/getPassword";
        }
        else{
            temp = "https://demo-uw46.onrender.com/api/doctor/getPassword";
        }
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
                                getotpFirebase(phone.getText().toString().trim());

                            //spinner.setVisibility(View.GONE);
                        }
else{
                              pb.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"user doesn't exists", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
pb.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"check your internet connection", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q= Volley.newRequestQueue(ForgotPassword.this);

            q.add(j);


        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
            pb.setVisibility(View.GONE);

        }
    }


    public void getotpFirebase(String phone2) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phone2, 60, TimeUnit.SECONDS, ForgotPassword.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

pb.setVisibility(View.VISIBLE);
verifyit(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pb.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationId=s;
                //phone.setEnabled(false);
               phone.setFocusable(false);
               //phone.setTextColor(getColor(R.color.hint2));

                spinner.setVisibility(View.GONE);
                getOtp.setVisibility(View.VISIBLE);
                proceed.setVisibility(View.GONE);
                but.setVisibility(View.VISIBLE);
                super.onCodeSent(s, forceResendingToken);
                pb.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "otp sent to +91"+phone.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
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

getotpFirebase(phone.getText().toString());

                           /* if(ContextCompat.checkSelfPermission(ForgotPassword.this, android.Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
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
*/

                        }

                    } catch (JSONException e) {
                        pb.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"error", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"no internet connection", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q= Volley.newRequestQueue(ForgotPassword.this);

            q.add(j);


        }catch (Exception e){
            pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
        }
    }


    public void verifyit(PhoneAuthCredential pac){
        //pb.setVisibility(View.GONE);
        auth.signInWithCredential(pac)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        pb.setVisibility(View.GONE);
                        result.setText("**Your password is "+pass);
                        getOtp.setVisibility(View.GONE);
                        but.setVisibility(View.GONE);
                        phone.setFocusable(true);
                        proceed.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Verification successful", Toast.LENGTH_SHORT).show();
                    } else {
                        pb.setVisibility(View.GONE);

                    }
                });    }

 public void signin(String otp){
        if(!verificationId.equals("")) {
            PhoneAuthCredential p = PhoneAuthProvider.getCredential(verificationId, otp);
            verifyit(p);
        }
        else{
            pb.setVisibility(View.GONE);
            Intent i=new Intent(this, LoginActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "retry again", Toast.LENGTH_SHORT).show();
        }

    }
}