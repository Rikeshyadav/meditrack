package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Patient_HomeFragment extends Fragment {
    RecyclerView recyclerView;
    Toolbar toolbar;
    ImageView refresh;
    ProgressBar progressBar;
    TextView nodata;
    LottieAnimationView empty;
    patient_homeAdapter adapter;
    List arr=new ArrayList<>();
    DrawerLayout drawerLayout;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_patient__home, container, false);

        recyclerView=view.findViewById(R.id.patient_recyclview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        sp= requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);


        progressBar=view.findViewById(R.id.patientpage_home_progress);
        progressBar.setVisibility(View.VISIBLE);
        empty=view.findViewById(R.id.home_patientpage_lottie);
        nodata=view.findViewById(R.id.home_patientpage_nodata);
        refresh=view.findViewById(R.id.refresh_home_patientpage);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDoctors(sp.getString("phone",""));
            }
        });
        toolbar = view.findViewById(R.id.customtool);
        ((AppCompatActivity) getActivity()).setSupportActionBar(null);
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
         toggle.syncState();
        searchDoctors(sp.getString("phone",""));
        return view;
    }


    public void searchDoctors(String ph){
        String temp = "https://demo-uw46.onrender.com/api/patient/getDetails";
        try {
            progressBar.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("phone", ph);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            JSONArray ja = response.getJSONArray("doctoradd");
                            if (ja.length()>0) {
                                arr = filterArray(response.getJSONArray("doctoradd"));
                                if (arr.size() > 0) {
                                    adapter = new patient_homeAdapter(arr);
                                    recyclerView.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);
                                    refresh.setVisibility(View.GONE);
                                    empty.setVisibility(View.GONE);
                                    nodata.setVisibility(View.GONE);
                                    recyclerView.setHasFixedSize(true);
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    empty.setVisibility(View.VISIBLE);
                                    refresh.setVisibility(View.GONE);
                                    nodata.setVisibility(View.VISIBLE);
                                }
                            }else{
                                progressBar.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                                refresh.setVisibility(View.GONE);
                                nodata.setVisibility(View.VISIBLE);
                            }
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            empty.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);
                            refresh.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(),"failed to load",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        empty.setVisibility(View.GONE);
                        nodata.setVisibility(View.GONE);
                        refresh.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    nodata.setVisibility(View.GONE);
                    refresh.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"check internet connectivity", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(getActivity());
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            //   pb.setVisibility(View.GONE);
            //       pb.setVisibility(View.GONE);

        }

    }



    public List filterArray(JSONArray jsonArray) throws JSONException {
        List<List> outer=new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){

            JSONObject j=jsonArray.getJSONObject(i);
            List<String> inner=new ArrayList<>();
            if(j.getString("pending").equals("false")){
                inner.add("");
                inner.add(j.getString("doctor_name"));
                inner.add(j.getString("specification"));
                try {
                    inner.add(j.getString("clinic_name"));
                }catch(Exception e){
                    inner.add(j.getString("doctor_name"));
                }
                inner.add(j.getString("qualification"));
                outer.add(inner);

            }

        }

        return outer;
    }


}