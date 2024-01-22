package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class editProfile extends AppCompatActivity {
EditText name,email,address,ph;
SharedPreferences sp;
AppCompatButton save;
String identity="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sp=getSharedPreferences("user",MODE_PRIVATE);
        name=findViewById(R.id.epname);
        email=findViewById(R.id.epemail);
        address=findViewById(R.id.epaddress);
        name.setText(getIntent().getStringExtra("name"));
        address.setText(getIntent().getStringExtra("address"));
        email.setText(getIntent().getStringExtra("email"));
        ph=findViewById(R.id.epphone);
        ph.setText(getIntent().getStringExtra("ph"));
        identity=getIntent().getStringExtra("identity");
        ph.setFocusable(false);
        save=findViewById(R.id.epbutton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm=name.getText().toString().trim();
                String em=email.getText().toString().trim();
                String ad=address.getText().toString().trim();
                updateprofile(nm,em,ad,ph.getText().toString().trim());


            }
        });


    }

    public void updateprofile(String nm,String em,String ad,String ph){
        String temp = "";
        if (identity.equals("Patient")) {
            temp = "https://demo-uw46.onrender.com/api/patient/update/"+ph;
        } else {
            temp = "https://demo-uw46.onrender.com/api/doctor/update/"+ph;
        }
        try {
            //  pb.setVisibility(View.VISIBLE);
            HashMap<String, String> jsonobj = new HashMap<>();

            jsonobj.put("username", nm);
            jsonobj.put("email",em);
            jsonobj.put("phone",ph);
            jsonobj.put("address",ad);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
sp.edit().putString("name",nm).apply();

                            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();
                        //  pb.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(editProfile.this);

            q.add(j);


        } catch (
                Exception e) {
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            //       pb.setVisibility(View.GONE);

        }
    }


}