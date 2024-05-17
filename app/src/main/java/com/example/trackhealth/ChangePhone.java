package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ChangePhone extends AppCompatActivity {
ImageView back;
AppCompatButton verify,change;
    FirebaseAuth auth;
    SharedPreferences sp;
    ProgressBar pb;
    String verificationId="",otp="";
EditText phone,otpedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        auth= FirebaseAuth.getInstance();
        back=findViewById(R.id.changephone_back);
        otpedit=findViewById(R.id.change_phoneedit2);
        verify=findViewById(R.id.changephone_verify);
        pb=findViewById(R.id.change_progress);
        change=findViewById(R.id.changephone_change);
        sp=getSharedPreferences("user",MODE_PRIVATE);
        phone=findViewById(R.id.change_phoneedit);
back.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        onBackPressed();
    }
});

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ph = phone.getText().toString().trim();
                if (!ph.equals("") && ph.length() == 10) {
                    if(!sp.getString("phone","").equals(ph)) {
                        pb.setVisibility(View.VISIBLE);
                        getotpFirebase(phone.getText().toString().trim());
                    }else{
                        Toast.makeText(getApplicationContext(), "Enter other than old Phone Number", Toast.LENGTH_SHORT).show();
                    }

                    //sendmsg(ph);

                    //but.setVisibility(View.VISIBLE);
                    //getOtp.setVisibility(View.VISIBLE);




                } else {
                    //pb.setVisibility(View.GONE);
                   change.setVisibility(View.GONE);
                    phone.setText(ph);
                    Toast.makeText(getApplicationContext(),"invalid number", Toast.LENGTH_SHORT).show();


                }
            }
        });


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!otpedit.getText().toString().trim().equals("")) {
                    String enterOtp = otpedit.getText().toString().trim();
                    pb.setVisibility(View.VISIBLE);
                    signin(enterOtp);
                }
                else{
                    Toast.makeText(getApplicationContext(),"enter otp", Toast.LENGTH_LONG).show();
                }

            }
        });

    }



    public void getotpFirebase(String phone2) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phone2, 60, TimeUnit.SECONDS, ChangePhone.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

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


                super.onCodeSent(s, forceResendingToken);
              verify.setVisibility(View.GONE);
              change.setVisibility(View.VISIBLE);
              otpedit.setVisibility(View.VISIBLE);
              phone.setFocusable(false);
pb.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "otp sent to +91"+phone.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }



    public void verifyit(PhoneAuthCredential pac){
        //pb.setVisibility(View.GONE);
        auth.signInWithCredential(pac)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                       // phone.setFocusable(true);
                     //   proceed.setVisibility(View.VISIBLE);
                        updatePhone(phone.getText().toString());
                        Toast.makeText(getApplicationContext(), "Verification successful", Toast.LENGTH_SHORT).show();
                    } else {
                       // pb.setVisibility(View.GONE);

                    }
                });    }


    public void updatePhone(String newphone) {

        newphone = newphone.trim();
        String temp = "";
        if (sp.getString("identity","").equals("Patient")) {
            temp = "https://demo-uw46.onrender.com/api/patient/updatephone";
        } else {
            temp = "https://demo-uw46.onrender.com/api/doctor/updatephone";
        }
        try {
            HashMap<String, String> jsonobj = new HashMap<>();

            jsonobj.put("oldPhone", sp.getString("phone",""));
            jsonobj.put("newPhone", newphone);
            Toast.makeText(getApplicationContext(),newphone+" "+sp.getString("phone",""), Toast.LENGTH_SHORT).show();
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
pb.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Phone number successfully changed", Toast.LENGTH_SHORT).show();

                            //spinner.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
pb.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
pb.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(ChangePhone.this);

            q.add(j);


        } catch (Exception e) {
            pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();


        }
    }


    public void signin(String otp){
        if(!verificationId.equals("")) {
            PhoneAuthCredential p = PhoneAuthProvider.getCredential(verificationId, otp);
            verifyit(p);
        }
        else{
           pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "retry again", Toast.LENGTH_SHORT).show();
        }

    }
}