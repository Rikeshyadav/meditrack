package com.example.trackhealth;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Calendar;

public class patient_edit_profile extends Fragment {

    ImageView imageView;
String photo="";

 SharedPreferences sp2,sp;
 int year,month,date;
 TextView user;
    ImageView edit;
    AppCompatButton update,cancel;
    ProgressBar p1,p2,p3,p4,p5,p6,p7;
    TextView state,city,phone,gender,dob,email,age;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_patient_edit_profile, container, false);
        imageView=view.findViewById(R.id.prof_image);

        user=view.findViewById(R.id.patient_profile_name);
        edit=view.findViewById(R.id.edit_profile_patient_icon);
        sp2 =view.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
        user.setText(sp2.getString("name",""));



        state=view.findViewById(R.id.patient_profile_stateValue);
        city=view.findViewById(R.id.patient_profile_cityValue);
        phone=view.findViewById(R.id.patient_profile_phoneValue);
        p1=view.findViewById(R.id.patient_profile_p1);
        p2=view.findViewById(R.id.patient_profile_p2);
        p3=view.findViewById(R.id.patient_profile_p3);
        p4=view.findViewById(R.id.patient_profile_p4);
        p5=view.findViewById(R.id.patient_profile_p5);
        p6=view.findViewById(R.id.patient_profile_p6);
        p7=view.findViewById(R.id.patient_profile_p7);
        Calendar c=Calendar.getInstance();
        year=c.get(Calendar.YEAR);
        month=c.get(Calendar.MONTH);
        date=c.get(Calendar.DATE);
        email=view.findViewById(R.id.patient_profile_emailValue);
        age=view.findViewById(R.id.patient_profile_ageValue);
        gender=view.findViewById(R.id.patient_profile_genderValue);
        sp=getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        dob=view.findViewById(R.id.patient_profile_dobValue);

        getvalues();
        setPhoto();
   edit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       Intent i=new Intent(getActivity(),Patient_profile_editpage.class);
       startActivity(i);

    }
            });


     //   getvalues();

        return view;

    }


    public void getvalues() {
        String temp ="https://demo-uw46.onrender.com/api/patient/getDetails";
        try {

            HashMap<String, String> jsonobj = new HashMap<>();

            jsonobj.put("phone",sp2.getString("phone",""));
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {

                            p3.setVisibility(View.GONE);
                            phone.setText("+91"+response.getString("phone"));
                            phone.setVisibility(View.VISIBLE);
                            sp2.edit().putString("phone",response.getString("phone")).apply();
                            p1.setVisibility(View.GONE);
                            state.setText(response.getString("state"));
                            state.setVisibility(View.VISIBLE);

                            sp2.edit().putString("state",response.getString("state")).apply();
                            p2.setVisibility(View.GONE);
                            city.setText(response.getString("city"));
                            city.setVisibility(View.VISIBLE);
                            sp2.edit().putString("city",response.getString("city")).apply();

                            email.setText(response.getString("email"));
                            p4.setVisibility(View.GONE);
                            email.setVisibility(View.VISIBLE);


                            p5.setVisibility(View.GONE);
                            gender.setText(response.getString("gender"));
                            gender.setVisibility(View.VISIBLE);
                            sp2.edit().putString("gender",response.getString("gender")).apply();

                            p6.setVisibility(View.GONE);
                            dob.setText(response.getString("dob"));
                            dob.setVisibility(View.VISIBLE);
                            sp2.edit().putString("dob",response.getString("dob")).apply();

                            age.setText(getAge(response.getString("dob")));
                            p7.setVisibility(View.GONE);
                            age.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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


        }
    }

    String getAge(String birthDate) {

        int year2=year-Integer.parseInt(birthDate.substring(6));

        return year2+" years";
    }


public void setPhoto(){
    String photo= sp2.getString("photo","");
    if(!photo.equals("")) {
        Bitmap b = getbitmap(photo);
        imageView.setImageBitmap(b);
    }
    else{
        imageView.setImageResource(R.drawable.baseline_person_24);
    }
}




    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }


    public void onResume() {
        super.onResume();
        state.setVisibility(View.GONE);
        city.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        email.setVisibility(View.GONE);
        dob.setVisibility(View.GONE);
        age.setVisibility(View.GONE);
        gender.setVisibility(View.GONE);
        p1.setVisibility(View.VISIBLE);
        p2.setVisibility(View.VISIBLE);
        p3.setVisibility(View.VISIBLE);
        p4.setVisibility(View.VISIBLE);
        p5.setVisibility(View.VISIBLE);
        p6.setVisibility(View.VISIBLE);
        p7.setVisibility(View.VISIBLE);
        getvalues();
        setPhoto();
        user.setText(sp2.getString("name",""));
    }

}