package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LabAssistant extends AppCompatActivity {
    boolean back = false;
    Toolbar toolbar;
    String phone="";
    List<List> arr=new ArrayList<>();
    EditText patienttxt;
    patient_homeAdapter adapter;
    RecyclerView recyclerView;
    ImageView search;
    SharedPreferences sp;
    RelativeLayout layout;

    ProgressBar pb;
    TextView name,dob,gender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_assistant);
        sp = getSharedPreferences("user", MODE_PRIVATE);
        toolbar = findViewById(R.id.toolbar_assistant);
        patienttxt=findViewById(R.id.addPatient_searchBar_assistant);
        search=findViewById(R.id.search_but_add_assistant);
        recyclerView=findViewById(R.id.labassistantrec);
        recyclerView.setLayoutManager(new LinearLayoutManager(LabAssistant.this,LinearLayoutManager.VERTICAL,false));
       adapter = new patient_homeAdapter(arr, LabAssistant.this,"lab");
layout=findViewById(R.id.slayout_parent_assistant);
        recyclerView.setAdapter(adapter);


        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                String ph=patienttxt.getText().toString();
                if(!ph.equals("")) {
                    layout.setVisibility(View.GONE);
                    phone=ph;
                    pb.setVisibility(View.VISIBLE);
                    searchPatient(ph);

                }
                else{
                    Toast.makeText(LabAssistant.this,"invalid number",Toast.LENGTH_SHORT).show();
                }
            }
        });
        setSupportActionBar(toolbar);

        pb=findViewById(R.id.spb_assistant);
        name=findViewById(R.id.spatient_name2_assitant);
        dob=findViewById(R.id.spatient_dob2_assistant);
        gender=findViewById(R.id.spatient_gender2_assistant);
        layout=findViewById(R.id.slayout_parent_assistant);


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
                            dob.setText(response.getString("dob"));
                            gender.setText(response.getString("gender"));
                            layout.setVisibility(View.VISIBLE);
                            JSONArray ja = response.getJSONArray("doctoradd");
                            if (ja.length() > 0) {
                                arr = filterArray(response.getJSONArray("doctoradd"));
                                if (arr.size() > 0) {

                                    pb.setVisibility(View.GONE);
                                    //refresh.setVisibility(View.GONE);
                                    //empty.setVisibility(View.GONE);
                                    //nodata.setVisibility(View.GONE);
                                    adapter=new patient_homeAdapter(arr,LabAssistant.this,"lab");
                         recyclerView.setAdapter(adapter);

                                }
                            }
                        }
                        else{
                            pb.setVisibility(View.GONE);
                         //   layout.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(),"patient doesn't exist", Toast.LENGTH_SHORT).show();
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
            RequestQueue q = Volley.newRequestQueue(LabAssistant.this);
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






  /*    public void retrievePDF(View view) {
            try {
                startActivity(new Intent(LabAssistant.this, Readfile.class));
            } catch (ActivityNotFoundException e) {
                Toast.makeText(LabAssistant.this, "Readfile activity not found", Toast.LENGTH_SHORT).show();
            }


        }*/
    @Override
    public void onBackPressed() {

        if (back) {
            finishAffinity();
        } else {

            back = true;
            Toast.makeText(LabAssistant.this, "Press one more time to exit", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        back = false;
    }

    public void setAlert(String msg, String pos, String neg, String flag) {

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(msg);
        b.setPositiveButton(pos, (DialogInterface.OnClickListener) (dialog, which) -> {
            if (flag.equals("exit"))
                finishAffinity();
            else if (flag.equals("logout")) {
                Intent i = new Intent(this, LoginActivity.class);
                sp.edit().putBoolean("islogged", false).apply();
                startActivity(i);
                Toast.makeText(this, "logging out", Toast.LENGTH_SHORT).show();
            }

        });
        b.setNegativeButton(neg, (DialogInterface.OnClickListener) (dialog, which) -> {

            dialog.cancel();
        });
        AlertDialog ad = b.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                ad.getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.black));
            }
        });

        ad.show();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.assitant_logout) {
            setAlert("Do you want to logout?", "yes", "no", "logout");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assistant_menu, menu);
        return true;
    }

    public List filterArray(JSONArray jsonArray) throws JSONException {
        List<List> outer=new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){

            JSONObject j=jsonArray.getJSONObject(i);
            List<String> inner=new ArrayList<>();
            if(j.getString("pending").equals("false")){
                try {
                    inner.add(j.getString("photo"));
                }
                catch (Exception e){
                    inner.add("");
                }
                inner.add(j.getString("username"));
                inner.add(j.getString("speciality"));
                try {
                    inner.add(j.getString("clinic_name"));
                }catch(Exception e){
                    inner.add("");
                }
                inner.add(j.getString("qualification"));
                inner.add(j.getString("about"));
                inner.add(j.getString("phone"));


                inner.add(j.getString("state"));
                inner.add(j.getString("city"));
                if(!sp.getString("identity","").equals("Doctor")) {
                    inner.add(j.getString("qualification"));
                    inner.add(j.getString("speciality"));
                    inner.add(j.getString("clinic_name"));
                }
inner.add(phone);

                outer.add(inner);

            }

        }

        return outer;
    }
}