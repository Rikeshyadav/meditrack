package com.example.trackhealth;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import static android.widget.Toast.makeText;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;

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

        String[] st = {"Select", "Doctor", "Patient","Lab Assistant"};
        RegisterSpinnerApdater adapter = new RegisterSpinnerApdater(this, R.layout.spinner_login, st);
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
                else if (selectedOption.equals("Lab Assistant"))
                {
                    doctororpatient="Lab Assistant";
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
                    if(phone.length()==10) {
                        if (!doctororpatient.equals("null")) {

                            progressBar.setVisibility(View.VISIBLE);
                            authPatient(phone, pass, doctororpatient);
                            //progressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getApplicationContext(), "empty selection!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"invalid phone number",Toast.LENGTH_SHORT).show();
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
            temp = "https://demo-uw46.onrender.com/api/assistant/auth";
        }
        HashMap<String,String> jsonobj=new HashMap<>();
        jsonobj.put("phone", phone);
        jsonobj.put("password",password);

        JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp,new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
progressBar.setVisibility(View.GONE);
                try {

                    if(Boolean.parseBoolean(response.getString("success"))){
                        Intent b1;
if(doctororpatient.equals("Patient")) {
    b1 = new Intent(LoginActivity.this, HomePage_Patient.class);
}
                        else if(doctororpatient.equals("Lab Assistant")) {
                            b1 = new Intent(LoginActivity.this,LabAssistant.class);
                        }
else{
    b1 = new Intent(LoginActivity.this, HomePage_Doctor.class);
}
                        Toast.makeText(getApplicationContext(),response.getString("msg"), Toast.LENGTH_SHORT).show();

                       sp.edit().putString("name",response.getString("username")).apply();
                       sp.edit().putBoolean("islogged",true).apply();
                        sp.edit().putString("email", response.getString("email")).apply();

                        sp.edit().putString("phone", phone).apply();
                        sp.edit().putString("pass", password).apply();
                        sp.edit().putString("identity",doctororpatient).apply();
                        sp.edit().putString("dob",response.getString("dob")).apply();
                        sp.edit().putString("gender",response.getString("gender")).apply();
                        try {
                            if(!response.getString("photo").equals("")) {
                                sp.edit().putString("photo", response.getString("photo")).apply();
                            }
                            else{
                                sp.edit().putString("photo","").apply();
                            }
                        }
                        catch (Exception e){
                            sp.edit().putString("photo","").apply();
                        }
                        sp.edit().putString("city", response.getString("city")).apply();
                        sp.edit().putString("state", response.getString("state")).apply();
                        if(identity.equals("Doctor")) {
                         sp.edit().putString("speciality",response.getString("speciality")).apply();
                            sp.edit().putString("yoe",response.getString("yoe")).apply();
                            sp.edit().putString("qualification",response.getString("qualification")).apply();
                            sp.edit().putString("about",response.getString("about")).apply();
                            sp.edit().putString("photosign",response.getString("sign")).apply();
                            JSONObject jo=new JSONObject();
                            jo=response.getJSONObject("clinic_hospital");
                            String clinic_name=jo.getString("name");
                            String clinic_type=jo.getString("type");
                            String clinic_state=jo.getString("state");
                            String clinic_city=jo.getString("city");
                            String clinic_phone=jo.getString("phone");
                            sp.edit().putString("clinic_name",clinic_name).apply();
                            sp.edit().putString("clinic_type",clinic_type).apply();
                            sp.edit().putString("clinic_state",clinic_state).apply();
                            sp.edit().putString("clinic_city",clinic_city).apply();
                            sp.edit().putString("clinic_phone",clinic_phone).apply();

                        }

                        startActivity(b1);
                    }
                    else{
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(),response.getString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                //authPatient(phone,password,identity);
            }
        });
        RequestQueue q= Volley.newRequestQueue(LoginActivity.this);
        RetryPolicy retryPolicy = new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        );
        j.setRetryPolicy(retryPolicy);
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

    @Override
    protected void onResume() {
        super.onResume();

    }

}