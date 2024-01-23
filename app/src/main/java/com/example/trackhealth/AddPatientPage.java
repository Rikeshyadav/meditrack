package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

public class AddPatientPage extends AppCompatActivity {
TextView name,dob,gender;
AppCompatButton add;
ImageView search;
SharedPreferences sp;
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
                  updateContent(phone);
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
              add.setText("added");
          }
      });

    }


    public void updateContent(String ph){
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

            q.add(j);


        } catch (
                Exception e) {
            pb.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            //       pb.setVisibility(View.GONE);

        }

    }
}