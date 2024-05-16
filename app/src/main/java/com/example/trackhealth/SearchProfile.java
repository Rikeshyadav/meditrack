package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class SearchProfile extends AppCompatActivity {
    TextView name, email, phone, state, city, hospital, hosparent, about, gender, dob, age, quali, spec, yoe, hname, htype, hstate, hcity, hphone;
    boolean adhosactive=false,adyoeactive=false,adspecactive=false,adqualiactive=false;
    String pp="";
    LinearLayoutManager l;
    TextView quadesc,yeardesc;
    ReviewAdapter adapter;
    RadioGroup radio;
    RadioButton all,five,four,three,two,one;
    RecyclerView review;

    ProgressBar pb;
    List<List> arr=new ArrayList<>();
    RelativeLayout ll,ll2;
    ImageView editbut,addqua,addspec,addyoe,addhos,profilepic,retry;
    SharedPreferences sp;
    String doctororpatient = "", ph = "", pass = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_profile);
        profilepic=findViewById(R.id.sdoctor_profile_image);
        editbut =findViewById(R.id.sedit_profile_doctor_icon);
        sp =getSharedPreferences("user", Context.MODE_PRIVATE);
        editbut.setVisibility(View.GONE);
        addqua=findViewById(R.id.sdoctor_profile_qualicardadd);
        retry=findViewById(R.id.searchprofile_retry);
review=findViewById(R.id.sdiprecreview);
        addspec=findViewById(R.id.sdoctor_profile_speccardadd);
        addyoe=findViewById(R.id.sdoctor_profile_yoecardadd);
        addhos=findViewById(R.id.sdoctor_profile_hoscardadd);
        quadesc=findViewById(R.id.spdoctor_profile_qualicard1);
        yeardesc=findViewById(R.id.spdoctor_profile_yearcard1);
        name = findViewById(R.id.spdoctor_profile_dnamecard1);
        about = findViewById(R.id.sdoctor_profile_aboutvalue);
        state = findViewById(R.id.sdoctor_profile_stateValue);
        ll=findViewById(R.id.shosll_deditprofile);
        ll2=findViewById(R.id.searchprofile_ll);
        pb=findViewById(R.id.sprofile_progress);
        ll2.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
        city = findViewById(R.id.sdoctor_profile_cityValue);
        phone = findViewById(R.id.sdoctor_profile_phoneValue);
        email = findViewById(R.id.sdoctor_profile_emailValue);
        gender = findViewById(R.id.sdoctor_profile_genderValue);
        dob = findViewById(R.id.sdoctor_profile_dobValue);
        age = findViewById(R.id.sdoctor_profile_ageValue);

        quali =findViewById(R.id.sdoctor_profile_qualivalue);
        spec = findViewById(R.id.sdoctor_profile_specvalue);
        yoe = findViewById(R.id.sdoctor_profile_yoevalue);

        hname = findViewById(R.id.sdoctor_profile_hosnameValue);
        htype = findViewById(R.id.sdoctor_profile_hostypeValue);
        hstate = findViewById(R.id.sdoctor_profile_hosstateValue);
        hcity = findViewById(R.id.sdoctor_profile_hoscityValue);
        hphone = findViewById(R.id.sdoctor_profile_hosphoneValue);
        radio=findViewById(R.id.sdipradio);
        all=findViewById(R.id.sdipall);
        five=findViewById(R.id.sdip5);
        four=findViewById(R.id.sdip4);
        three=findViewById(R.id.sdip3);
        two=findViewById(R.id.sdip2);
        one=findViewById(R.id.sdip1);

        all.setSelected(true);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,7);


            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,5);


            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,4);


            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,3);


            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,2);


            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,1);


            }
        });



        addhos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adhosactive){
                    ll.setVisibility(View.GONE);
                    adhosactive = false;
                    addhos.setImageDrawable(getResources().getDrawable(R.drawable.add_plus));
                }
                else {
                    ll.setVisibility(View.VISIBLE);
                    adhosactive = true;
                    addhos.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                }
            }
        });


        addqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adqualiactive){
                    quali.setVisibility(View.GONE);
                    adqualiactive = false;
                    addqua.setImageDrawable(getResources().getDrawable(R.drawable.add_plus));
                }
                else {
                    quali.setVisibility(View.VISIBLE);
                    adqualiactive = true;
                    addqua.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                }
            }
        });
retry.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        retry.setVisibility(View.GONE);
        getvalues(getIntent().getStringExtra("nonuserprofile"));
    }
});

        addspec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adspecactive){
                    spec.setVisibility(View.GONE);
                    adspecactive = false;
                    addspec.setImageDrawable(getResources().getDrawable(R.drawable.add_plus));
                }
                else {
                    spec.setVisibility(View.VISIBLE);
                    adspecactive = true;
                    addspec.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                }
            }
        });


        addyoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adyoeactive){
                    yoe.setVisibility(View.GONE);
                    adyoeactive = false;
                    addyoe.setImageDrawable(getResources().getDrawable(R.drawable.add_plus));
                }
                else {
                    yoe.setVisibility(View.VISIBLE);
                    adyoeactive = true;
                    addyoe.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                }
            }
        });
        getvalues(getIntent().getStringExtra("nonuserprofile"));
        editbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchProfile.this, editProfile.class);
                startActivity(i);
            }
        });

    }

    String getAge(String birthDate) {
        int year= Calendar.getInstance().get(Calendar.YEAR);
        int year2=year-Integer.parseInt(birthDate.substring(6));

        return year2+" years";
    }

    public void getList(List<List> arr,int key){
        List<List> outer=new ArrayList<>();

        for(int i=0;i<arr.size();i++) {
            List<String> inner = new ArrayList<>();
            int a = (int) Float.parseFloat(arr.get(i).get(3).toString());


            if (a==key) {
                try {
                    inner.add(arr.get(i).get(0).toString());

                } catch (Exception e) {
                    inner.add("");
                }
                inner.add(arr.get(i).get(1).toString());
                inner.add(arr.get(i).get(2).toString());
                inner.add(arr.get(i).get(3).toString());
                inner.add(arr.get(i).get(4).toString());
                inner.add(arr.get(i).get(5).toString());
                outer.add(inner);
            }
            else if(key==7){
                try {
                    inner.add(arr.get(i).get(0).toString());

                } catch (Exception e) {
                    inner.add("");
                }
                inner.add(arr.get(i).get(1).toString());
                inner.add(arr.get(i).get(2).toString());
                inner.add(arr.get(i).get(3).toString());
                inner.add(arr.get(i).get(4).toString());
                inner.add(arr.get(i).get(5).toString());
                outer.add(inner);
            }
        }


        if (outer.size() > 0) {
            review.setVisibility(View.VISIBLE);
            l = new LinearLayoutManager(SearchProfile.this, LinearLayoutManager.VERTICAL, false);
            review.setLayoutManager(l);
            adapter.notifyDataSetChanged();
            adapter= new ReviewAdapter(outer,SearchProfile.this);
            review.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else{
            review.setVisibility(View.GONE);
        }


    }

    public void getvalues(String ph) {
        String temp ="https://demo-uw46.onrender.com/api/doctor/getDetails";
        try {

            HashMap<String, String> jsonobj = new HashMap<>();

            jsonobj.put("phone",ph);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            ll2.setVisibility(View.VISIBLE);
                            pb.setVisibility(View.GONE);
                            retry.setVisibility(View.GONE);





                            try {
                                setPhoto(response.getString("photo"));
                            }catch (Exception e){

                            }

                            yeardesc.setText(response.getString("yoe")+" Yrs Practice");
                            quadesc.setText(response.getString("qualification")+" - "+response.getString("speciality"));
                            name.setText(response.getString("username"));
                            email.setText(response.getString("email"));
                            state.setText(response.getString("state"));
                            city.setText(response.getString("city"));
                            gender.setText(response.getString("gender"));
                            age.setText(getAge(response.getString("dob")));
                            dob.setText(response.getString("dob"));
                            phone.setText(response.getString("phone"));
                            spec.setText(response.getString("speciality"));
                            quali.setText(response.getString("qualification"));
                            yoe.setText(response.getString("yoe")+" years");
                            about.setText(response.getString("about"));
                            JSONObject jobj=response.getJSONObject("clinic_hospital");
                            hname.setText(jobj.getString("name"));
                            htype.setText(jobj.getString("type"));
                            hstate.setText(jobj.getString("state"));
                            hcity.setText(jobj.getString("city"));
                            hphone.setText(jobj.getString("phone"));




                            JSONArray ja = response.getJSONArray("reviews");
                            if (ja.length()>0) {
                                arr = filterArray(response.getJSONArray("reviews"));
                                if (arr.size() > 0) {
                                   adapter = new ReviewAdapter(arr, SearchProfile.this);
                                    review.setLayoutManager(new LinearLayoutManager(SearchProfile.this, LinearLayoutManager.VERTICAL, false));
                                    review.setAdapter(adapter);
                                   // progressBar.setVisibility(View.GONE);
                                    review.setVisibility(View.VISIBLE);
                                    review.setHasFixedSize(true);
                                }
                            }

                        }
                    } catch (JSONException e) {

                        Toast.makeText(SearchProfile.this,e.toString(),Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        retry.setVisibility(View.VISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(SearchProfile.this,error.toString(),Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
                }
            });
            RequestQueue q = Volley.newRequestQueue(SearchProfile.this);
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
            retry.setVisibility(View.VISIBLE);
            Toast.makeText(SearchProfile.this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public List filterArray(JSONArray jsonArray) throws JSONException {
        List<List> outer=new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){

            JSONObject j=jsonArray.getJSONObject(i);
            List<String> inner=new ArrayList<>();

            try {
                inner.add(j.getString("photo"));
            } catch (Exception e) {
                inner.add("");
            }
            inner.add(j.getString("username"));
            inner.add(j.getString("review"));
            inner.add(j.getString("star"));
            inner.add(j.getString("date"));
            inner.add(j.getString("time"));


            outer.add(inner);

        }

        return outer;
    }


    public void getvalues2() {
        setPhoto();
        yeardesc.setText(sp.getString("yoe","")+" Yrs Practice");
        quadesc.setText(sp.getString("qualification","")+" - "+sp.getString("speciality",""));
        name.setText(sp.getString("name", ""));
        email.setText(sp.getString("email", ""));
        state.setText(sp.getString("state", ""));
        city.setText(sp.getString("city", ""));
        gender.setText(sp.getString("gender", ""));
        age.setText(getAge(sp.getString("dob","")));
        dob.setText(sp.getString("dob", ""));
        phone.setText(sp.getString("phone",""));
        spec.setText(sp.getString("speciality", ""));
        quali.setText(sp.getString("qualification", ""));
        yoe.setText(sp.getString("yoe", "")+" years");
        about.setText(sp.getString("about", ""));
        hname.setText(sp.getString("clinic_name", ""));
        htype.setText(sp.getString("clinic_type", ""));
        hstate.setText(sp.getString("clinic_state", ""));
        hcity.setText(sp.getString("clinic_city", ""));
        hphone.setText(sp.getString("clinic_phone", ""));





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

    public void setPhoto(String photo){
        if(!photo.equals("")) {
            Bitmap b = getbitmap(photo);
            profilepic.setImageBitmap(b);
        }
        else{
            profilepic.setImageResource(R.drawable.baseline_person_24);
        }
    }




    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }


    @Override
    public void onResume() {
        super.onResume();
        getvalues(getIntent().getStringExtra("nonuserprofile"));
    }}