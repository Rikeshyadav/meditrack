package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class AddPatientPage extends AppCompatActivity {
TextView name,dob,gender;
AppCompatButton add;
SharedPreferences sp;
String doctorph="",patientph="";
String dname="",dclinic="",specification="",qualification="",issue="";
ImageView search;
EditText ph;
RelativeLayout layout;
ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_page);
        add=findViewById(R.id.search_add_patient);
        ph=findViewById(R.id.addPatient_searchBar);
        name=findViewById(R.id.spatient_name2);
        pb=findViewById(R.id.spb);
        dob=findViewById(R.id.spatient_dob2);
        gender=findViewById(R.id.spatient_gender2);
        sp=getSharedPreferences("user", Context.MODE_PRIVATE);
        search=findViewById(R.id.search_but_add);

        doctorph=sp.getString("phone","");
        dname=sp.getString("name","");
        specification=sp.getString("speciality","");
        qualification=sp.getString("qualification","");
        dclinic=sp.getString("clinic_name","");

        patientph=ph.getText().toString().trim();
        pb.setVisibility(View.GONE);
        layout=findViewById(R.id.slayout);
        layout.setVisibility(View.GONE);

      search.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              pb.setVisibility(View.VISIBLE);
              layout.setVisibility(View.GONE);
              String phone=ph.getText().toString().trim();
              if(!phone.equals("") && phone.length()==10){
                  searchPatient(phone);
              }
              else{
                  Toast.makeText(getApplicationContext(),"invalid number",Toast.LENGTH_SHORT).show();
                  pb.setVisibility(View.GONE);
                  layout.setVisibility(View.GONE);
              }
          }
      });

      add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
String phone=ph.getText().toString().trim();

if(!phone.equals("")) {
    updateDoctor(phone);
}else{
    Toast.makeText(getApplicationContext(),"empty phone number",Toast.LENGTH_SHORT).show();
}

          }
      });

    }


    public void searchPatient(String ph){
            String temp = "https://demo-uw46.onrender.com/api/patient/getDetails";
        try {
            //  pb.setVisibility(View.VISIBLE);
            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("phone", ph);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            pb.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            name.setText(response.getString("username"));
                            /*email.setText(response.getString("email"));
                            phone.setText(ph);
                            address.setText(response.getString("address"));
                            if(doctororpatient.equals("Doctor")) {
                                hosparent.setVisibility(View.VISIBLE);
                                hospital.setText(response.getString("speciality"));
                            }
*/
                            name.setText(response.getString("username"));
                            dob.setText(response.getString("dob"));
                            gender.setText(response.getString("gender"));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"patient doesn't exists",Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                            layout.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        layout.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(AddPatientPage.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            pb.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            //       pb.setVisibility(View.GONE);

        }

    }




    public void updateDoctor(String ph){
        pb.setVisibility(View.VISIBLE);
        String temp = "https://demo-uw46.onrender.com/api/doctor/addPatient/"+doctorph;
        try {

            JSONObject jj=new JSONObject();
            jj.put("phone",ph);
            jj.put("pending","true");

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp,jj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {

                            String phone=sp.getString("phone","").trim();
                            if(!phone.equals("")) {
                                  updatePatient(ph);
                                Toast.makeText(getApplicationContext(),"sent request to patient",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"invalid user",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"patient already added",Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(AddPatientPage.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            pb.setVisibility(View.GONE);
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            //       pb.setVisibility(View.GONE);

        }

    }


    public void updatePatient(String ph){  //ph is patient contact
        pb.setVisibility(View.VISIBLE);
        String temp = "https://demo-uw46.onrender.com/api/patient/addDoctor/"+ph;
        try {
            JSONObject jj=new JSONObject();
            jj.put("clinic_name",dclinic);
            jj.put("username",dname);
            jj.put("speciality",specification);
            jj.put("qualification",qualification);
            jj.put("issue",issue);
            jj.put("phone",sp.getString("phone",""));
            jj.put("pending","true");
            jj.put("about",sp.getString("about",""));
            jj.put("state",sp.getString("state",""));
            jj.put("city",sp.getString("city",""));
            jj.put("photo",sp.getString("photo",""));
            jj.put("sign",sp.getString("photosign",""));

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp,jj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (Boolean.parseBoolean(response.getString("success"))) {
                            pb.setVisibility(View.GONE);
                            add.setText("added");
                            add.setBackgroundDrawable(getDrawable(R.drawable.green_button));
                            Toast.makeText(getApplicationContext(),"added successfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),response.getString("msg"),Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(AddPatientPage.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            pb.setVisibility(View.GONE);

            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();


        }

    }

}