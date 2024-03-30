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

public class Patient_profile_editpage extends AppCompatActivity {
    SharedPreferences sp2;
    FloatingActionButton fbut;
    TextView username;
    String photo;
    ImageView imageView,back;
    EditText state, city, dob, phone, gender, name;
    ProgressBar progressBar;
    AppCompatButton update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile_editpage);
        fbut=findViewById(R.id.uplodimage_doctor_profile);
        imageView=findViewById(R.id.prof_image2_patient);
        sp2 = getSharedPreferences("user", Context.MODE_PRIVATE);
        username=findViewById(R.id.doctor_profile_name2);
        back=findViewById(R.id.edit_profile_patient_backicon);
        username.setText(sp2.getString("name",""));
        progressBar = findViewById(R.id.patient_profileedit_progress);
        state = findViewById(R.id.patient_profileedit_stateValue);
        city = findViewById(R.id.patient_profileedit_cityValue);
        dob = findViewById(R.id.patient_profileedit_dobValue);
        gender = findViewById(R.id.patient_profileedit_genderValue);
        phone = findViewById(R.id.patient_profileedit_phoneValue);
        name = findViewById(R.id.patient_profileedit_nameValue);
        update = findViewById(R.id.patient_profileedit_updatebut);
        state.setText(sp2.getString("state", ""));
        city.setText(sp2.getString("city", ""));
        gender.setText(sp2.getString("gender", ""));
        phone.setText(sp2.getString("phone", ""));
        dob.setText(sp2.getString("dob", ""));
        name.setText(sp2.getString("name", ""));
        setPhoto();

        fbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startImageCapture();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateprofile(name.getText().toString(), gender.getText().toString(), dob.getText().toString(), state.getText().toString(), city.getText().toString(), phone.getText().toString());
            }
        });
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                photo = String.valueOf(getImageUri(Patient_profile_editpage.this, imageBitmap));
                sp2.edit().putString("photo",photo).apply();
                updateprofile(photo,imageBitmap);
            }
            catch (Exception e){
                imageView.setImageResource(R.drawable.baseline_person_24);
            }

        }

    }
    public void updateprofile(String photo,Bitmap imagebitmap){
        String temp = "";
        temp = "https://demo-uw46.onrender.com/api/patient/update/"+ sp2.getString("phone","");

        try {

            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("photo",photo);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            Toast.makeText(Patient_profile_editpage.this, "photo updated", Toast.LENGTH_SHORT).show();
                            imageView.setImageBitmap(imagebitmap);
                            sp2.edit().putString("photo",photo).apply();
                        }
                    } catch (JSONException e) {
                        setPhoto();
                        Toast.makeText(Patient_profile_editpage.this, e.toString(), Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setPhoto();
                    Toast.makeText(Patient_profile_editpage.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            });
            RequestQueue q = Volley.newRequestQueue(Patient_profile_editpage.this);
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
            Toast.makeText(Patient_profile_editpage.this, e.toString(), Toast.LENGTH_SHORT).show();

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

    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }
    private void startImageCapture() {
        if (ContextCompat.checkSelfPermission(Patient_profile_editpage.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent,1);

            } else {

                Toast.makeText(Patient_profile_editpage.this, "No camera app available", Toast.LENGTH_SHORT).show();
            }
        } else {

            ActivityCompat.requestPermissions(Patient_profile_editpage.this, new String[]{android.Manifest.permission.CAMERA}, 1);
        }
    }

    public void updateprofile(String name, String gender, String dob, String state, String city, String ph) {
        String temp = "";
        progressBar.setVisibility(View.VISIBLE);
        temp = "https://demo-uw46.onrender.com/api/patient/update/" + ph;

        try {
            //  pb.setVisibility(View.VISIBLE);
            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("username", name);
            jsonobj.put("phone", ph);
            jsonobj.put("state", state);
            jsonobj.put("city", city);
            jsonobj.put("dob", dob);
            jsonobj.put("gender", gender);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {

                            sp2.edit().putString("name", name).apply();
                            sp2.edit().putString("phone", ph).apply();
                            sp2.edit().putString("state", state).apply();
                            sp2.edit().putString("city", city).apply();
                            sp2.edit().putString("gender", gender).apply();
                            sp2.edit().putString("dob", dob).apply();
                            username.setText(name);
                            Toast.makeText(Patient_profile_editpage.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(Patient_profile_editpage.this, "error  -  " + e, Toast.LENGTH_SHORT).show();
                        //  pb.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  pb.setVisibility(View.GONE);
                    Toast.makeText(Patient_profile_editpage.this, "try again -  " + error, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
            RequestQueue q = Volley.newRequestQueue(Patient_profile_editpage.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            Toast.makeText(Patient_profile_editpage.this, e.toString(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);

        }
    }


}