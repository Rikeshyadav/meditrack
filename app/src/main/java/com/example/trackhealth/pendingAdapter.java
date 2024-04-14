package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class pendingAdapter extends RecyclerView.Adapter<pendingAdapter.MyViewHolder> {
    private List<List> data;
    Context context;
    SharedPreferences sp;
    String patient="",doctorph="",dname="",specification="",qualification="",dclinic="";

    public pendingAdapter(List<List> data, Context context,SharedPreferences sp) {
        this.data = data;
        this.sp=sp;
        this.context=context;
        this.patient=sp.getString("phone","");
        this.doctorph=sp.getString("phone","");
        this.dname=sp.getString("name","");
        this.specification=sp.getString("speciality","");
        this.qualification=sp.getString("qualification","");
        this.dclinic=sp.getString("clinic_name","");


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_ui, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List item = data.get(position);
        holder.hosname.setText(item.get(0).toString());
        holder.docname.setText(item.get(1).toString());
        holder.spec.setText(item.get(2).toString());
        holder.qua.setText(item.get(3).toString());
        holder.issue.setText(item.get(4).toString());


        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           updatePatient(patient,item.get(5).toString(),"false",item.get(4).toString());
           updateDoctor(item.get(5).toString(),patient,"false",item.get(4).toString());
           holder.accept.setText("accepted");
           holder.reject.setClickable(false);
           holder.accept.setClickable(false);
           holder.reject.setBackgroundColor(context.getColor(R.color.hint));
           holder.accept.setBackgroundColor(context.getColor(R.color.hint));
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDoctor(item.get(5).toString(),patient,"delete",item.get(4).toString());
                updatePatient(patient,item.get(5).toString(),"delete",item.get(4).toString());
                holder.reject.setText("rejected");
                holder.reject.setClickable(false);
                holder.accept.setClickable(false);
                holder.reject.setBackgroundColor(context.getColor(R.color.hint));
                holder.accept.setBackgroundColor(context.getColor(R.color.hint));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView hosname,docname,spec,qua,issue;
        public AppCompatButton accept,reject;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hosname = itemView.findViewById(R.id.pending_rec_hos);
            docname = itemView.findViewById(R.id.pendocnm);
            spec = itemView.findViewById(R.id.pendocspec);
            qua = itemView.findViewById(R.id.pendocqua);
            issue = itemView.findViewById(R.id.pendocissue);
            accept=itemView.findViewById(R.id.pending_accept);
            reject=itemView.findViewById(R.id.pending_reject);

        }
    }

    public void updatePatient(String patient,String doctor,String pending,String issue){
        String temp = "https://demo-uw46.onrender.com/api/patient/updatepending/"+patient;
        try {
            JSONObject jj=new JSONObject();

            jj.put("phone",doctor);
            jj.put("pending",pending);
            jj.put("issue",issue);



            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp,jj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {

                        }
                    } catch (JSONException e) {


                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(context);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {



        }

    }



    public void updateDoctor(String doctor,String patient,String pending,String issue){
        String temp = "https://demo-uw46.onrender.com/api/doctor/updatepending/"+doctor;

        try {
            JSONObject jj=new JSONObject();
            jj.put("username",sp.getString("name",""));
            jj.put("dob",sp.getString("dob",""));
            jj.put("gender",sp.getString("gender",""));
            jj.put("address",sp.getString("address",""));
            jj.put("photo",sp.getString("photo",""));
            jj.put("phone",patient);
            jj.put("pending",pending);
            jj.put("issue",issue);

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp,jj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
Toast.makeText(context.getApplicationContext(), "updated doctor",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        //Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();

                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(context);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {



        }

    }

}
