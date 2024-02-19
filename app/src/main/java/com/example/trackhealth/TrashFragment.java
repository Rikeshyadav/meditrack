package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.L;
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

public class TrashFragment extends Fragment {
    RecyclerView recyclerView;
    List arr;
    SharedPreferences sp;
    String patientn="";
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_pending, container, false);
        sp=view.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        searchDoctor(sp.getString("phone",""));

        return view;
    }

    public void searchDoctor(String ph){
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
                          patientn=response.getString("username");
                          arr=filterArray(response.getJSONArray("doctoradd"));

                            recyclerView = (RecyclerView) view.findViewById(R.id.pending_rec);
                            pendingAdapter adapter = new pendingAdapter(arr,getActivity(),sp);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(adapter);
                        }
                        else{
                          //  Toast.makeText(getApplicationContext(),"patient doesn't exists",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        //Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();
                        //pb.setVisibility(View.GONE);

                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //pb.setVisibility(View.GONE);
                    //Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
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
            q.add(j);


        } catch (
                Exception e) {
         //   pb.setVisibility(View.GONE);
            //       pb.setVisibility(View.GONE);

        }

    }




    public List filterArray(JSONArray jsonArray) throws JSONException {
        JSONArray newarr=new JSONArray();
    List<List> outer=new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){

            JSONObject j=jsonArray.getJSONObject(i);
            List<String> inner=new ArrayList<>();
            if(j.getString("pending").equals("true")){
                inner.add(j.getString("clinic_name"));
                inner.add(j.getString("doctor_name"));
                inner.add(j.getString("specification"));
                inner.add(j.getString("qualification"));
                inner.add(j.getString("issue"));
                inner.add(j.getString("phone"));
                outer.add(inner);

            }

        }

        return outer;
}



}