package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Patient_Testfragment extends Fragment {
    TextView textView;
    TextView textView1;
    RecyclerView recyclerView;
    List<List> arr = new ArrayList<>();
    SharedPreferences sp;
    Testrecadapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_patient__testfragment, container, false);
sp= getContext().getSharedPreferences("issue", Context.MODE_PRIVATE);
        recyclerView = view.findViewById(R.id.prestestrec);
getdata();
        return view;
    }


    public void getdata() {

        String temp = "https://demo-uw46.onrender.com/api/issue/getPrescriptions/" + sp.getString("idno", "") + "/" + sp.getString("issueid", "");
        System.out.println("haare--" + temp);
        try {

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, temp, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            JSONArray jsonArray = response.getJSONArray("prescriptions");
                            arr = getfilterarray(jsonArray);
                            if (arr.size() > 0) {
                                adapter = new Testrecadapter(getActivity(), arr,"general");
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                recyclerView.setAdapter(adapter);
                            }

                        }
                    } catch (JSONException e) {
                        //progressBar.setVisibility(View.GONE);
                        //retry.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

        /*        progressBar.setVisibility(View.GONE);
                empty.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);*/
                    //progressBar.setVisibility(View.GONE);
                    //retry.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
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
 /*       progressBar.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        nodata.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);*/
            //   progressBar.setVisibility(View.GONE);
            // retry.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "fail to load - " + e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    public List<List> getfilterarray(JSONArray jsonArray2) {
        int m = 1;
        List<List> outer = new ArrayList<>();
        JSONObject kk;
        JSONArray jsonArray;
        for (int jj = 0; jj < jsonArray2.length(); jj++) {
            try {
                kk = jsonArray2.getJSONObject(jj);
                jsonArray = kk.getJSONArray("test");

            } catch (JSONException e) {

                throw new RuntimeException(e);
            }

            try {


                for (int i = 0; i < jsonArray.length(); i++) {

                    List<String> inner = new ArrayList<>();
                    JSONObject j = jsonArray.getJSONObject(i);

                    inner.add(m + ".  " + j.getString("name"));

                    inner.add(j.getString("status"));

                    outer.add(inner);
m=m+1;
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        return outer;
    }



}

