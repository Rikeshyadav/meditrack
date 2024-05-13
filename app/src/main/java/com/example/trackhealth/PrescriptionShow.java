package com.example.trackhealth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
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
import java.util.Calendar;
import java.util.List;

public class PrescriptionShow extends AppCompatActivity {
    LinearLayout tableContent,tableContent2;
    SharedPreferences sp,sp2;
ImageView edit,del,signimg;
TextView note,date,pname,pissue,page,dname,dspec,daddress,dphone,hospital,dnamelast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_show);

pname=findViewById(R.id.presshowpname);
pissue=findViewById(R.id.presshowpatissue);
page=findViewById(R.id.presshowpagevalue);
dname=findViewById(R.id.presshowdname);
hospital=findViewById(R.id.presshowtitle);
edit=findViewById(R.id.presshowedit);
signimg=findViewById(R.id.presshowsign);
dnamelast=findViewById(R.id.presshowname4);


        sp2=getSharedPreferences("user",MODE_PRIVATE);
try{
    if(sp2.getString("identity","").equals("Patient")) {
        //signimg.setImageBitmap(getbitmap(sp2.getString("")));
    }else{
        signimg.setImageBitmap(getbitmap(sp2.getString("photosign","")));
    }
}catch (Exception e)
{

}
del=findViewById(R.id.presshowdel);


        if(sp2.getString("identity","").equals("Patient")){
            edit.setVisibility(View.GONE);
            del.setVisibility(View.GONE);
        }
del.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        setAlert();
    }
});

dspec=findViewById(R.id.presshowspec);
daddress=findViewById(R.id.presshowaddress);
dphone=findViewById(R.id.presshowphone);

        tableContent = findViewById(R.id.tableContent);
        tableContent2 = findViewById(R.id.tableContent2);
sp=getSharedPreferences("issue",MODE_PRIVATE);
        pissue.setText(sp.getString("issuetitle", ""));

if(sp2.getString("identity","").equals("Doctor")) {
    pname.setText(sp2.getString("curname", ""));
    page.setText(getAge(sp2.getString("curdob", "")));
    dname.setText("Dr. "+sp2.getString("name",""));
    dnamelast.setText("Dr. "+sp2.getString("name","")+"\n"+sp2.getString("speciality","")+","+sp2.getString("qualification",""));
    dspec.setText(sp2.getString("speciality","")+","+sp2.getString("qualification",""));
    daddress.setText(sp2.getString("city","")+","+sp2.getString("state",""));
    dphone.setText("Ph : "+sp2.getString("phone",""));
    hospital.setText(sp2.getString("clinic_name","dsa"));
}
else if(sp2.getString("identity","").equals("Patient")){

    dname.setText("Dr. "+sp2.getString("curname",""));
    dspec.setText(sp2.getString("curspec","")+","+sp2.getString("curqua",""));
    daddress.setText(sp2.getString("curcity","")+","+sp2.getString("curstate",""));
    dphone.setText("Ph : "+sp2.getString("curphone2",""));
    hospital.setText(sp2.getString("curclinic",""));
    pname.setText(sp2.getString("name", ""));
    page.setText(getAge(sp2.getString("dob", "")));
}
note=findViewById(R.id.presshownote);
date=findViewById(R.id.presshowdate);

        // Sample data for demonstration
getdata();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PrescriptionShow.this,Prescription_page.class);
                i.putExtra("key","edit");
                i.putExtra("pid",sp.getString("pid",""));
                i.putExtra("issueid",sp.getString("issueid",""));
                i.putExtra("idno",sp.getString("idno",""));
                startActivity(i);
            }
        });


    }

    public void setAlert(){
String pos="Yes";
String neg="No";
String msg="Do you want to delete the prescription?";
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage(msg);
        b.setPositiveButton(pos,(DialogInterface.OnClickListener) (dialog, which)->{
            deletepres();


        });
        b.setNegativeButton(neg,(DialogInterface.OnClickListener) (dialog,which)->{

            dialog.cancel();
        });
        AlertDialog ad=b.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                ad.getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.black));
            }
        });

        ad.show();

    }


    String getAge(String birthDate) {
        int year= Calendar.getInstance().get(Calendar.YEAR);
        int year2=year-Integer.parseInt(birthDate.substring(6));

        return year2+" Yrs";
    }


    public void deletepres(){

        String temp="https://demo-uw46.onrender.com/api/issue/deletePrescription/"+sp.getString("pid","");

        try{

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.DELETE, temp,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                           Intent i=new Intent(PrescriptionShow.this, User_prescription_activity_page.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                            finish();
                            Toast.makeText(PrescriptionShow.this, "Prescription Deleted", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {

                        Toast.makeText(PrescriptionShow.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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

            Toast.makeText(PrescriptionShow.this, "fail to delete - "+e.toString(), Toast.LENGTH_SHORT).show();

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
                            if(!sp2.getString("identity","").equals("Patient")){
                                edit.setVisibility(View.VISIBLE);
                            }

                            JSONObject jsonobj=response.getJSONObject("prescription");
                            JSONArray jsonArray = jsonobj.getJSONArray("medicine");
                            if(!jsonobj.get("note").equals("")) {
                                note.setText(jsonobj.getString("note"));
                            }
                            else{
                                note.setText("N/A");
                            }

                            date.setText("Date : "+jsonobj.getString("date"));
                            String[][] meddata=new String[jsonArray.length()][5];
                            meddata=getMeddata(jsonobj);
                            // Add rows dynamically
                            for (String[] row : meddata) {
                                addRow(tableContent, row,170);
                            }
                            jsonArray = jsonobj.getJSONArray("test");
                            String[][] testData=new String[jsonArray.length()][3];
                            testData=getTestData(jsonobj);
                            // Add rows dynamically
                            for (String[] row : testData) {
                                addRow(tableContent2, row,240);
                            }

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


    public String[][] getMeddata(JSONObject jsonObject) {
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("medicine");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String[][] arr = new String[jsonArray.length()][5]; // Initialize the array with correct dimensions

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject j = jsonArray.getJSONObject(i);
                arr[i][0]=String.valueOf(i+1);
                arr[i][1] = j.getString("name");
                arr[i][4] = j.getString("quantity");

                JSONObject ds = j.getJSONObject("dosage");
                StringBuilder dosageBuilder = new StringBuilder();
                if (!ds.getString("morning").isEmpty()) {
                    dosageBuilder.append("Morning (").append(ds.getString("morning")).append(")");
                }
                if (!ds.getString("afternoon").isEmpty()) {
                    dosageBuilder.append("\nAfternoon (").append(ds.getString("afternoon")).append(")");
                }
                if (!ds.getString("evening").isEmpty()) {
                    dosageBuilder.append("\nEvening (").append(ds.getString("evening")).append(")");
                }
                if (!ds.getString("night").isEmpty()) {
                    dosageBuilder.append("\nNight (").append(ds.getString("night")).append(")");
                }
                arr[i][2] = dosageBuilder.toString();

                JSONObject dur = j.getJSONObject("duration");
                arr[i][3] = dur.getString("start") + " - " + dur.getString("end");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return arr;
    }





    public String[][] getTestData(JSONObject jsonObject) {
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("test");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        String[][] arr = new String[jsonArray.length()][3]; // Initialize the array with correct dimensions

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject j = jsonArray.getJSONObject(i);
                arr[i][0]=String.valueOf(i+1);
                arr[i][1] = j.getString("name");
                arr[i][2] = j.getString("status");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        return arr;
    }




    private void addRow(LinearLayout tableContent, String[] rowData,int width) {
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);

        List<TextView> textViews = new ArrayList<>(); // List to hold TextViews in the row

        // Find maximum height of TextViews and adjust width of columns
        int maxHeight = 0;
        for (String cellData : rowData) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(width), // Set width to 0 initially
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0,0,0,0);
            textView.setLayoutParams(params);
            textView.setText(cellData);
          textView.setPadding(8, 8, 8, 8);
            textView.setTextColor(Color.BLACK);
            //textView.setGravity(Gravity.CENTER);
            textView.setBackgroundResource(R.drawable.rectangle_box); // Add your background drawable here

            // Add TextView to the list
            textViews.add(textView);
            rowLayout.addView(textView);

            // Measure the height of the TextView
            textView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int height = textView.getMeasuredHeight();
            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        // Set maximum height to all TextViews in the row
        int kk=0;

        for (TextView textView : textViews) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
            if(kk==0){
                params.width=dpToPx(60);
            }

            params.height = maxHeight+50;
            textView.setLayoutParams(params);
            kk=1;
        }

        // Add the row to the table content layout
        tableContent.addView(rowLayout);
    }

    private void addRow2(LinearLayout tableContent, String[] rowData) {
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);

        for (String cellData : rowData) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    dpToPx(320), // Set width to 170dp
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

    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }


}