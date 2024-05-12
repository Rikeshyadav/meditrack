package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Panding_Fragment extends Fragment {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LottieAnimationView empty;
    TextView nodata;
    List arr;
    SharedPreferences sp;
    ImageView refresh;
    String patientn="";
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_pending, container, false);
        sp=view.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        progressBar=view.findViewById(R.id.patientpage_pending_progress);
        empty=view.findViewById(R.id.pending_patientpage_lottie);
        progressBar.setVisibility(View.VISIBLE);
        refresh=view.findViewById(R.id.refresh_pending_patientpage);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDoctor(sp.getString("phone",""));
            }
        });
        nodata=view.findViewById(R.id.pending_patientpage_nodata);

        searchDoctor(sp.getString("phone",""));
        return view;
    }

    public void searchDoctor(String ph){
        String temp = "https://demo-uw46.onrender.com/api/patient/getDetails";

        try {
            progressBar.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("phone", ph);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            empty.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                          patientn=response.getString("username");
                          arr=filterArray(response.getJSONArray("doctoradd"));
                          if(arr.size()>0) {
                              recyclerView = (RecyclerView) view.findViewById(R.id.doctor_home_rec);
                              pendingAdapter adapter = new pendingAdapter(arr, getActivity(), sp);
                              recyclerView.setHasFixedSize(true);
                              progressBar.setVisibility(View.GONE);
                              recyclerView.setAdapter(adapter);
                          }
                          else{
                              progressBar.setVisibility(View.GONE);
                              empty.setVisibility(View.VISIBLE);
                              nodata.setVisibility(View.VISIBLE);
                          }
                        }
                        else{
                          Toast.makeText(getActivity(),"empty request",Toast.LENGTH_SHORT).show();
                          progressBar.setVisibility(View.GONE);
                          empty.setVisibility(View.GONE);
                          nodata.setVisibility(View.GONE);
                          refresh.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        empty.setVisibility(View.GONE);
                        nodata.setVisibility(View.GONE);
                        refresh.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    nodata.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    refresh.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"check internet connectivity", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(requireActivity());
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
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




    public List filterArray(JSONArray jsonArray) throws JSONException {
    List<List> outer=new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++){

            JSONObject j=jsonArray.getJSONObject(i);
            List<String> inner=new ArrayList<>();
            if(j.getString("pending").equals("true")){
                inner.add(j.getString("clinic_name"));
                inner.add(j.getString("username"));
                inner.add(j.getString("speciality"));
                inner.add(j.getString("qualification"));
                inner.add(j.getString("issue"));
                inner.add(j.getString("phone"));
                outer.add(inner);

            }

        }

        return outer;
}



}