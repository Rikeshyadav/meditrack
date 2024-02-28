package com.example.trackhealth;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class EditprofileFragment extends Fragment {
    TextView name, email, phone, state,city, hospital,hosparent;
    ProgressBar pb;
    ImageView re;
   AppCompatButton editbut;
   SharedPreferences sp;
   RelativeLayout layout;
    String doctororpatient="",ph="",pass="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
editbut=view.findViewById(R.id.peditbutton);
sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        name = view.findViewById(R.id.pname);
        pb=view.findViewById(R.id.profile_progress);
        pb.setVisibility(View.VISIBLE);
        layout=view.findViewById(R.id.pro_layout);
        layout.setVisibility(View.GONE);
        email = view.findViewById(R.id.pemail);
        hosparent=view.findViewById(R.id.hosparent);
        re=view.findViewById(R.id.pretry);
        re.setVisibility(View.GONE);
        hosparent.setVisibility(View.GONE);
        phone = view.findViewById(R.id.pphone);
        city= view.findViewById(R.id.pacity);
        hospital = view.findViewById(R.id.phospital);
        ph = sp.getString("phone","");
        doctororpatient=sp.getString("identity","");
        pass =sp.getString("pass","");

        re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                re.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);
                getvalues();
            }
        });

editbut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getActivity(), editProfile.class);
        i.putExtra("ph",ph);
        i.putExtra("identity",doctororpatient);
        i.putExtra("email",email.getText().toString().trim());
        i.putExtra("city",city.getText().toString().trim());
        i.putExtra("name",name.getText().toString().trim());
        startActivity(i);
    }
});
getvalues();
        return view;
    }

    public void getvalues() {
        String temp = "";
        if (doctororpatient.equals("Patient")) {
            temp = "https://demo-uw46.onrender.com/api/patient/auth";
        } else {
            temp = "https://demo-uw46.onrender.com/api/doctor/auth";
        }
        try {
          //  pb.setVisibility(View.VISIBLE);
            HashMap<String, String> jsonobj = new HashMap<>();

            jsonobj.put("phone", ph);
            jsonobj.put("password", pass);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            pb.setVisibility(View.GONE);
                            re.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            name.setText(response.getString("username"));
                      email.setText(response.getString("email"));
                       phone.setText(ph);
                       city.setText(response.getString("city"));
                       if(doctororpatient.equals("Doctor")) {
                           hosparent.setVisibility(View.VISIBLE);
                            hospital.setText(response.getString("speciality"));
                       }

                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "error"+e, Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        re.setVisibility(View.VISIBLE);
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.setVisibility(View.GONE);
                    re.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(requireActivity());
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);
            q.add(j);


        } catch (
                Exception e) {
            pb.setVisibility(View.GONE);
            re.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
     //       pb.setVisibility(View.GONE);

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pb.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
        getvalues();
    }
}