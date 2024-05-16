package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class prescriptionParent extends Fragment {
    RecyclerView rec;
    FloatingActionButton add;
    TextView issue;
    List<List> outer;
    List<List> arr=new ArrayList<>();
    ProgressBar progressBar;
    ImageView retry;
    PrescriptionParentAdapter adapter;
    SharedPreferences sp,sp2;
String issueid;
    public prescriptionParent(String issueid) {
        this.issueid=issueid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_prescription_parent, container, false);

        rec=view.findViewById(R.id.presparentrec);
        issue=view.findViewById(R.id.pparent_issuevalue);
        progressBar=view.findViewById(R.id.progress_pparent);

        retry=view.findViewById(R.id.retry_pparent);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                getdata();
            }
        });
        rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        add=view.findViewById(R.id.presparentadd);

        sp=getActivity().getSharedPreferences("issue", Context.MODE_PRIVATE);
        sp2=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sp2.getString("identity","").equals("Patient")){
            add.setVisibility(View.GONE);
        }

        issue.setText(sp.getString("issuetitle",""));
        getdata();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),Prescription_page.class);
                i.putExtra("issueid",issueid);
                sp.edit().putString("isuploaded","no").apply();
                startActivity(i);
            }
        });
        return view;
    }

    public void getdata(){

        String temp="https://demo-uw46.onrender.com/api/issue/getPrescriptions/"+sp.getString("idno","")+"/"+sp.getString("issueid","");
        System.out.println("haare--"+temp);
        try{

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, temp,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
JSONArray jsonArray=response.getJSONArray("prescriptions");
if(jsonArray.length()>0){
    arr=filterArray(jsonArray);
    System.out.println("hbhai"+arr.toString());
    if(arr.size()>0){
        adapter=new PrescriptionParentAdapter(getActivity(),arr);
        progressBar.setVisibility(View.GONE);
        rec.scrollToPosition(arr.size()-1);
        rec.setVisibility(View.VISIBLE);
        rec.setAdapter(adapter);

    }
    else{
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getActivity(),"something wrong",Toast.LENGTH_SHORT).show();
    }
}else{
    progressBar.setVisibility(View.GONE);
    Toast.makeText(getActivity(),"No Prescription added",Toast.LENGTH_SHORT).show();
}
                        }
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        retry.setVisibility(View.VISIBLE);
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
                    progressBar.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
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
            progressBar.setVisibility(View.GONE);
            retry.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "fail to load - "+e.toString(), Toast.LENGTH_SHORT).show();

        }

    }


    public List filterArray(JSONArray jsonArray) throws JSONException {
        outer=new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++) {

            JSONObject j = jsonArray.getJSONObject(i);
            //  System.out.println("hbhai"+j.toString());
            List<String> inner = new ArrayList<>();

            inner.add(j.getString("note"));
            inner.add(j.getString("date"));
            inner.add(j.getString("pid"));
            JSONArray medicineArray = j.getJSONArray("medicine");
            int medicineLength = medicineArray.length();
            inner.add(String.valueOf(medicineLength));
            JSONArray testArray = j.getJSONArray("test");
            int testLength = testArray.length();
            inner.add(String.valueOf(testLength));
            inner.add(String.valueOf(i+1));
            outer.add(inner);
        }



        return outer;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sp.getString("isuploaded","").equals("yes")) {
            progressBar.setVisibility(View.VISIBLE);
            rec.setVisibility(View.GONE);
            getdata();
        }

    }




}