package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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
import java.util.List;

public class PrescriptionShow extends AppCompatActivity {
    LinearLayout tableContent;
    List<List> outer=new ArrayList<>();
    List<List> arr=new ArrayList<>();
    SharedPreferences sp;
    String[][] meddata = {
            {"Medicine A", "10mg", "2 weeks", "3"},
            {"Medicine B", "20mg", "1 week", "2"},
            {"Medicine C", "15mg", "3 weeks", "1"}
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_show);

        tableContent = findViewById(R.id.tableContent);
sp=getSharedPreferences("issue",MODE_PRIVATE);
        // Sample data for demonstration
getdata();

        // Add rows dynamically
        for (String[] row : meddata) {
            addRow(tableContent, row);
        }

    }

    public void getdata(){

        String temp="https://demo-uw46.onrender.com/api/issue/getPrescriptions/"+sp.getString("idno","")+"/"+sp.getString("issueid","")+"/"+sp.getString("pid","");
        System.out.println("haare--"+temp);
        try{

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, temp,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            JSONArray jsonArray=response.getJSONArray("prescriptions");

                            //arr=filterArray(jsonArray);


                        }
                    } catch (JSONException e) {
                        //progressBar.setVisibility(View.GONE);
                        //retry.setVisibility(View.VISIBLE);
                        Toast.makeText(PrescriptionShow.this, e.toString(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PrescriptionShow.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(PrescriptionShow.this);
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
            Toast.makeText(PrescriptionShow.this, "fail to load - "+e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

/*
    public String[][] getMeddata(JSONObject jsonObject) {
        String[][] arr={};
        JSONArray jsonArray=jsonObject.getJSONArray("medicine");
        for(int i=0;i<jsonArray.length();i++) {
             String[] inner={};
            try {
                JSONObject j = jsonArray.getJSONObject(i);
                 inner[i]=j.getString()
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //  System.out.println("hbhai"+j.toString());


            outer.add(inner);
        }
return arr;
    }*/




    private void addRow(LinearLayout tableContent, String[] rowData) {
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (String cellData : rowData) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(170), // Set width to 170dp
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.weight = 1;
            textView.setLayoutParams(params);
            textView.setText(cellData);
            textView.setPadding(8, 8, 8, 8);
            textView.setTextColor(Color.BLACK);
            textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.rectangle_box); // Add your background drawable here
            rowLayout.addView(textView);
        }

        // Add the row to the table content layout
        tableContent.addView(rowLayout);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }


}