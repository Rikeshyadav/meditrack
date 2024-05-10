package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
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

public class editProfile extends AppCompatActivity {
EditText name,email,city,state,ph,gender,dob,spec,quali,yoe,hname,htype,hcity,hstate,hphone,about;
SharedPreferences sp;
AppCompatButton save;
TextView user,name2;
    String photo;
    ProgressBar progressBar;
FloatingActionButton addimg;
ImageView profilepic;
String identity="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sp=getSharedPreferences("user",MODE_PRIVATE);
        name=findViewById(R.id.doctor_profileedit_nameValue);
        gender=findViewById(R.id.doctor_profileedit_genderValue);
        dob=findViewById(R.id.doctor_profileedit_dobValue);
        addimg=findViewById(R.id.uplodimage_doctor_profile);
        progressBar=findViewById(R.id.doctor_profile_editprogress);
        profilepic=findViewById(R.id.doctor_editprofile_image);
        email=findViewById(R.id.doctor_profileedit_emailValue);
        state=findViewById(R.id.doctor_profileedit_stateValue);
        city=findViewById(R.id.doctor_profileedit_cityValue);
        about=findViewById(R.id.doctor_profileedit_aboutValue);
        spec=findViewById(R.id.doctor_profileedit_specValue);
        name2=findViewById(R.id.doctor_profile_name2);

        quali=findViewById(R.id.doctor_profileedit_qualiValue);
        yoe=findViewById(R.id.doctor_profileedit_yoeValue);

        //  hospital/clinic

        hname=findViewById(R.id.doctor_profileedit_hosnameValue);
        hstate=findViewById(R.id.doctor_profileedit_hosstateValue);
        hcity=findViewById(R.id.doctor_profileedit_hoscityValue);
        htype=findViewById(R.id.doctor_profileedit_hostypeValue);
        hphone=findViewById(R.id.doctor_profileedit_hosphoneValue);
        ph=findViewById(R.id.doctor_profileedit_phoneValue);
        ph.setText(sp.getString("phone",""));
        ph.setFocusable(false);
        save=findViewById(R.id.doctor_profileedit_updateBut);

        name2.setText("Dr. "+sp.getString("name",""));
        name.setText(sp.getString("name",""));
        email.setText(sp.getString("email",""));
        state.setText(sp.getString("state",""));
        city.setText(sp.getString("city",""));
        gender.setText(sp.getString("gender",""));
        dob.setText(sp.getString("dob",""));

        spec.setText(sp.getString("speciality",""));
        quali.setText(sp.getString("qualification",""));
        yoe.setText(sp.getString("yoe",""));
        about.setText(sp.getString("about",""));

        hname.setText(sp.getString("clinic_name",""));
        htype.setText(sp.getString("clinic_type",""));
        hstate.setText(sp.getString("clinic_state",""));
        hcity.setText(sp.getString("clinic_city",""));
        hphone.setText(sp.getString("clinic_phone",""));
setPhoto();
        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImageCapture();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nm=name.getText().toString().trim();
                String em=email.getText().toString().trim();
                String cityy=city.getText().toString().trim();
                String statee=state.getText().toString().trim();

JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("name",hname.getText().toString());
                jsonObject.put("type",htype.getText().toString());
jsonObject.put("state",hstate.getText().toString());
                jsonObject.put("city",hcity.getText().toString());
                jsonObject.put("phone",hphone.getText().toString());

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                progressBar.setVisibility(View.VISIBLE);
                updateprofile(nm,em,statee,cityy,sp.getString("phone",""),gender.getText().toString(),dob.getText().toString(),spec.getText().toString(),yoe.getText().toString(),quali.getText().toString(),about.getText().toString(),jsonObject);


            }
        });


    }
    public void updateprofile(String photo,Bitmap imagebitmap){
        String temp = "";
        temp = "https://demo-uw46.onrender.com/api/doctor/update/"+ sp.getString("phone","");

        try {

            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("photo",photo);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            Toast.makeText(editProfile.this, "photo updated", Toast.LENGTH_SHORT).show();
                            profilepic.setImageBitmap(imagebitmap);
                            sp.edit().putString("photo",photo).apply();
                        }
                    } catch (JSONException e) {
                        setPhoto();
                        Toast.makeText(editProfile.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setPhoto();
                    Toast.makeText(editProfile.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            });
            RequestQueue q = Volley.newRequestQueue(editProfile.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);

        } catch (
                Exception e) {
            setPhoto();
            Toast.makeText(editProfile.this, e.toString(), Toast.LENGTH_SHORT).show();

        }
    }
    public void updateprofile(String nm,String em,String state,String city,String ph,String gender,String dob,String spec,String yoe,String qualif,String about, JSONObject jsonObject){
        String temp = "";
        if (identity.equals("Patient")) {
            temp = "https://demo-uw46.onrender.com/api/patient/update/"+ph;
        } else {
            temp = "https://demo-uw46.onrender.com/api/doctor/update/"+ph;
        }
        try {
            //  pb.setVisibility(View.VISIBLE);
            JSONObject jsonobj=new JSONObject();
            jsonobj.put("username", nm);
            jsonobj.put("email",em);
            jsonobj.put("phone",ph);
            jsonobj.put("state",state);
            jsonobj.put("city",city);
            jsonobj.put("gender",gender);
            jsonobj.put("dob",dob);
            jsonobj.put("speciality",spec);
            jsonobj.put("yoe",yoe);
            jsonobj.put("about",about);
            jsonobj.put("qualification",qualif);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp, jsonobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            sp.edit().putString("name",nm).apply();
                            sp.edit().putString("email",em).apply();
                            sp.edit().putString("phone",ph).apply();
                            sp.edit().putString("state",state).apply();
                            sp.edit().putString("city",city).apply();
                            sp.edit().putString("gender",gender).apply();
                            sp.edit().putString("dob",dob).apply();
                            sp.edit().putString("speciality",spec).apply();
                            sp.edit().putString("qualification",qualif).apply();
                            sp.edit().putString("about",about).apply();
                            sp.edit().putString("yoe",yoe).apply();
                            name2.setText("Dr. "+sp.getString("name",""));
                            sp.edit().putString("clinic_name",jsonObject.getString("name")).apply();
                            sp.edit().putString("clinic_type",jsonObject.getString("type")).apply();
                            sp.edit().putString("clinic_state",jsonObject.getString("state")).apply();
                            sp.edit().putString("clinic_city",jsonObject.getString("city")).apply();
                            sp.edit().putString("clinic_phone",jsonObject.getString("phone")).apply();




progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "saved", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();
                        //  pb.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(editProfile.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            //       pb.setVisibility(View.GONE);

        }
    }
    public void setPhoto(){
        String photo= sp.getString("photo","");
        if(!photo.equals("")) {
            Bitmap b = getbitmap(photo);
            profilepic.setImageBitmap(b);
        }
        else{
            profilepic.setImageResource(R.drawable.baseline_person_24);
        }
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                photo = String.valueOf(getImageUri(editProfile.this, imageBitmap));
                sp.edit().putString("photo",photo).apply();
                updateprofile(photo,imageBitmap);
            }
            catch (Exception e){
                profilepic.setImageResource(R.drawable.baseline_person_24);
            }

        }

    }
    public String getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] byteImage_photo = bytes.toByteArray();
        return Base64.encodeToString(byteImage_photo,Base64.DEFAULT);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startImageCapture();
            } else {

            }
        }
    }

    private void startImageCapture() {
        if (ContextCompat.checkSelfPermission(editProfile.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent,1);

            } else {

                Toast.makeText(editProfile.this, "No camera app available", Toast.LENGTH_SHORT).show();
            }
        } else {

            ActivityCompat.requestPermissions(editProfile.this, new String[]{android.Manifest.permission.CAMERA}, 1);
        }
    }


    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }


}