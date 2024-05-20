package com.example.trackhealth;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class HomeDoctorAdapter extends RecyclerView.Adapter<HomeDoctorAdapter.MyViewHolder> {
    private List<List> data;
Context context;
    public HomeDoctorAdapter(List<List> data,Context context) {
        this.data = data;
        this.context=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_rec_ui, parent, false);
        return new MyViewHolder(itemView,data);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List item = data.get(position);
        holder.pname.setText(item.get(0).toString());
        holder.pdob.setText(item.get(1).toString());
        holder.pgender.setText(item.get(2).toString());
        if(data.get(position).get(5).toString().trim().length()>10){
            holder.remark.setVisibility(View.VISIBLE);
            holder.del.setVisibility(View.VISIBLE);
        }
        try {
            if(!item.get(6).toString().equals("")) {
                holder.img.setImageBitmap(getbitmap(item.get(6).toString()));
            }
        }
        catch (Exception e){
            holder.img.setImageResource(R.drawable.user);
        }
SharedPreferences sp=context.getSharedPreferences("user",Context.MODE_PRIVATE);
holder.del.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

            setAlert(item.get(5).toString(), sp.getString("phone", ""));


    }
});

    }


    public void setAlert(String patient,String doctor){
        String pos="Yes";
        String neg="No";
        String msg="Do you want to delete the patient?";
        AlertDialog.Builder b=new AlertDialog.Builder(context);
        b.setMessage(msg);
        b.setPositiveButton(pos,(DialogInterface.OnClickListener) (dialog, which)->{
            if(patient.trim().length()>10){
deldoc(patient,doctor);
            }
            else {

                deldata(patient, doctor);
            }
           // deldata(doctor,patient);


        });
        b.setNegativeButton(neg,(DialogInterface.OnClickListener) (dialog,which)->{

            dialog.cancel();
        });
        AlertDialog ad=b.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                ad.getWindow().getDecorView().setBackgroundColor(context.getResources().getColor(R.color.black));
            }
        });

        ad.show();

    }

    public void deldata(String patient,String doctor){
        String temp = "https://demo-uw46.onrender.com/api/doctor/updatepending/"+doctor;

        try {
            JSONObject jj=new JSONObject();
            jj.put("phone",patient);
            jj.put("pending","delete");

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
    public void deldoc(String patient,String doctor){
        String temp = "https://demo-uw46.onrender.com/api/doctor/deletePatient/"+doctor+"/"+patient;

        try {

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.DELETE, temp,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            Toast.makeText(context.getApplicationContext(), "Doctor Deleted",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context.getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(context.getApplicationContext(), error.toString(),Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context.getApplicationContext(), e.toString(),Toast.LENGTH_SHORT).show();


        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView pname, pdob, pgender,remark;
        public ImageView img,del;
        private List<List> data; // Add a reference to data variable

        public MyViewHolder(@NonNull View itemView, List<List> data) { // Pass data as a parameter
            super(itemView);
            this.data = data; // Assign passed data to local variable

            del=itemView.findViewById(R.id.docrecdel);
            pname = itemView.findViewById(R.id.homerecpat);
            pdob = itemView.findViewById(R.id.homerecdob);
            remark=itemView.findViewById(R.id.deletedremark);
            pgender = itemView.findViewById(R.id.homerecgen);
            img = itemView.findViewById(R.id.dp_patient_page_doctor);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Intent intent = new Intent(v.getContext(), Patient_ListRecyview_inDoctor.class);
                SharedPreferences sp = v.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);

               sp.edit().putString("curphone2", data.get(position).get(5).toString()).apply();
                sp.edit().putString("curname", data.get(position).get(0).toString()).apply();
                sp.edit().putString("curphoto", data.get(position).get(6).toString()).apply();
                sp.edit().putString("curdob", data.get(position).get(1).toString()).apply();
                sp.edit().putString("curstate", data.get(position).get(7).toString()).apply();
                sp.edit().putString("curcity", data.get(position).get(8).toString()).apply();
                sp.edit().putString("curgender", data.get(position).get(9).toString()).apply();
                sp.edit().putString("curemail", data.get(position).get(10).toString()).apply();
                if(!sp.getString("identity","").equals("Doctor")){
                sp.edit().putString("curspec", data.get(position).get(12).toString()).apply();
                sp.edit().putString("curqua", data.get(position).get(11).toString()).apply();

                sp.edit().putString("curclinic", data.get(position).get(13).toString()).apply();
}


                SharedPreferences sp2=v.getContext().getSharedPreferences("issue", Context.MODE_PRIVATE);
               sp2.edit().putString("idno",sp.getString("phone","")+data.get(position).get(5).toString()).apply();
                v.getContext().startActivity(intent);
            }
        }
    }


    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }



}
