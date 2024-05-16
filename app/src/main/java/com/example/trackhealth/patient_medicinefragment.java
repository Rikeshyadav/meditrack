package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class patient_medicinefragment extends Fragment {
RecyclerView recyclerView;
SharedPreferences sp,sp2;
PatmedAdapter adapter;
List<List> arr=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_patient_medicinefragment, container, false);
List<String> inner=new ArrayList<>();
sp=getContext().getSharedPreferences("issue", Context.MODE_PRIVATE);
        sp2=getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        recyclerView=view.findViewById(R.id.patmedrec1);
adapter=new PatmedAdapter(getActivity(),arr);
recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
recyclerView.setAdapter(adapter);

getdata();
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
                            JSONArray jsonArray = response.getJSONArray("prescriptions");
                            arr=getfilterarray(jsonArray);
                            if(arr.size()>0){
                                adapter=new PatmedAdapter(getActivity(),arr);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
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
            Toast.makeText(getActivity(), "fail to load - "+e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    public static String getTimeOfDay(LocalTime time) {
        int hour = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            hour = time.getHour();
        }

        if (hour >= 0 && hour < 12) {
            return "morning";
        } else if (hour >= 12 && hour < 16) {
            return "afternoon";
        } else if (hour >= 16 && hour < 19) {
            return "evening";
        } else {
            return "night";
        }
    }
    public static int getDaysBetween(String startDateStr, String endDateStr) {
        // Parse the input dates
        LocalDate startDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        LocalDate endDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        // Calculate the difference in days
        int daysBetween = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            daysBetween = (int) Math.abs(startDate.until(endDate).getDays());
        }

        return daysBetween;
    }


    public List<List> getfilterarray(JSONArray jsonArray2){

      LocalDate currentDate = null;
      String date="";
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
          currentDate = LocalDate.now();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

          // Format the current date using the custom format
          date = currentDate.format(formatter);
      }

      // Define a custom date format


      int m=0;
      LocalTime currentTime = null;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
          currentTime = LocalTime.now();
      }
      List<List> outer=new ArrayList<>();
      JSONObject kk;
      JSONArray jsonArray;
        for(int jj=0;jj<jsonArray2.length();jj++) {
            try {
                kk = jsonArray2.getJSONObject(jj);
                jsonArray = kk.getJSONArray("medicine");

            } catch (JSONException e) {

                throw new RuntimeException(e);
            }

            try {


                for (int i = 0; i < jsonArray.length(); i++) {
                    List<String> inner = new ArrayList<>();
                    JSONObject j = jsonArray.getJSONObject(i);
                    String desc = "allow";

int miss=0;
int rem=0;
                    inner.add((m + 1) + ".  " + j.getString("name"));

                    inner.add(j.getString("quantity"));
                    JSONObject dur = j.getJSONObject("duration");
                    inner.add(dur.getString("start") + " - " + dur.getString("end"));

                    JSONObject ds = j.getJSONObject("dosage");
                    // StringBuilder dosageBuilder = new StringBuilder();


                    if(j.getString("taken").length()>1) {
                        String[] taken = j.getString("taken").split("@");
                        miss=getDaysBetween(dur.getString("start"),date)-Integer.parseInt(taken[0]);
                        rem=getDaysBetween(date,dur.getString("end"))+miss;
                        System.out.println("taken"+taken[1]);
                       if (!taken[1].equals(String.valueOf(date))) {
                            desc = "allow";
                        } else {
                            desc = "not";
                        }
                    }
                    else{
                        miss=getDaysBetween(dur.getString("start"),date)-Integer.parseInt(j.getString("taken"))-1;
                        rem=getDaysBetween(date,dur.getString("end"))+miss;
                    }

                    miss+=1;
                    if(rem>getDaysBetween(dur.getString("start"), dur.getString("end"))){
                        rem=getDaysBetween(dur.getString("start"), dur.getString("end"));
                    }
                    // Compare date1 and date2

                    inner.add(String.valueOf(rem));
                    if (!ds.getString("morning").equals("")) {
                        System.out.println("kkkl" + ds.getString("morning") + j.getString("name"));
                        if (getTimeOfDay(currentTime).equals("morning")) {

                            inner.add(ds.getString("morning"));
                            inner.add(String.valueOf(getDaysBetween(dur.getString("start"), dur.getString("end"))));
                            inner.add(String.valueOf(miss));
                            inner.add(j.getString("medid"));
                            inner.add(kk.getString("pid"));
                            inner.add(desc);
                            inner.add(date);
                            inner.add(dur.getString("start"));
                            inner.add(j.getString("taken"));
                            outer.add(inner);
                        }
                        //dosageBuilder.append("Morning (").append(ds.getString("morning")).append(")");
                    }
                    if (!ds.getString("afternoon").isEmpty()) {
                        if (getTimeOfDay(currentTime).equals("afternoon")) {

                            inner.add(ds.getString("afternoon"));
                            inner.add(String.valueOf(getDaysBetween(dur.getString("start"), dur.getString("end"))));
                            inner.add(String.valueOf(miss));
                            inner.add(j.getString("medid"));
                            inner.add(kk.getString("pid"));
                            inner.add(desc);
                            inner.add(date);
                            inner.add(dur.getString("start"));
                            inner.add(j.getString("taken"));
                            outer.add(inner);
                        }
                        // dosageBuilder.append("\nAfternoon (").append(ds.getString("afternoon")).append(")");
                    }
                    if (!ds.getString("evening").isEmpty()) {
                        if (getTimeOfDay(currentTime).equals("evening")) {

                            inner.add(ds.getString("evening"));
                            inner.add(String.valueOf(getDaysBetween(dur.getString("start"), dur.getString("end"))));
                            inner.add(String.valueOf(miss));
                            inner.add(j.getString("medid"));
                            inner.add(kk.getString("pid"));
                            inner.add(desc);
                            inner.add(date);
                            inner.add(dur.getString("start"));
                            inner.add(j.getString("taken"));
                            outer.add(inner);
                        }
                        // dosageBuilder.append("\nEvening (").append(ds.getString("evening")).append(")");
                    }
                    if (!ds.getString("night").isEmpty()) {
                        if (getTimeOfDay(currentTime).equals("night")) {

                            inner.add(ds.getString("night"));
                            inner.add(String.valueOf(getDaysBetween(dur.getString("start"), dur.getString("end"))));
                            inner.add(String.valueOf(miss));
                            inner.add(j.getString("medid"));
                            inner.add(kk.getString("pid"));
                            inner.add(desc);
                            inner.add(date);
                            inner.add(dur.getString("start"));
                            inner.add(j.getString("taken"));
                            outer.add(inner);
                        }
                    }

                    m += 1;


                }

            } catch (JSONException e) {
                System.out.println("kkklp" + e.toString());
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }

        }

            return outer;
  }




}