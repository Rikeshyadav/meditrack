package com.example.trackhealth;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class patient_homeAdapter extends RecyclerView.Adapter<patient_homeAdapter.myholder> {

    private static List<List>data;
Context context;
String key;
    public patient_homeAdapter(List<List> data,Context context,String key) {
        this.data = data;
        this.context=context;
        this.key=key;
    }

    @NonNull
    @Override
    public patient_homeAdapter.myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pateint_iui,parent,false);
        return new myholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull patient_homeAdapter.myholder holder, int position) {
        //holder.dp.setImageResource(data.get(position));
        holder.dname.setText("Dr. "+data.get(position).get(1).toString());
        holder.dspec.setText(data.get(position).get(2).toString());
        SharedPreferences sp=context.getSharedPreferences("user",Context.MODE_PRIVATE);
        holder.dhospital.setText(data.get(position).get(3).toString());
        sp.edit().putString("curclinic_name",data.get(position).get(3).toString()).apply();
        holder.dqua.setText(data.get(position).get(4).toString());
        if(!data.get(position).get(0).toString().equals("")) {
            holder.dp.setImageBitmap(getbitmap(data.get(position).get(0).toString()));

        }
        if(data.get(position).get(6).toString().trim().length()>10){
            holder.delremark.setVisibility(View.VISIBLE);
            holder.del.setVisibility(View.VISIBLE);
        }
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlert(sp.getString("phone",""),data.get(position).get(6).toString().trim());
            }
        });
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (!key.equals("lab")) {
            Intent intent = new Intent(v.getContext(), user_report_homepage.class);
            SharedPreferences sp = v.getContext().getSharedPreferences("docpat", Context.MODE_PRIVATE);
            sp.edit().putString("dname", data.get(position).get(1).toString()).apply();
            sp.edit().putString("dspec", data.get(position).get(2).toString()).apply();
            sp.edit().putString("dhos", data.get(position).get(3).toString()).apply();
            sp.edit().putString("dqua", data.get(position).get(4).toString()).apply();
            sp.edit().putString("dphoto", data.get(position).get(0).toString()).apply();
            sp.edit().putString("dabout", data.get(position).get(5).toString()).apply();
            sp.edit().putString("dphone", data.get(position).get(6).toString()).apply();
            SharedPreferences sp2 = v.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
            sp2.edit().putString("curphone2", data.get(position).get(6).toString()).apply();
            sp2.edit().putString("curname", data.get(position).get(1).toString()).apply();
            SharedPreferences sp3 = v.getContext().getSharedPreferences("issue", Context.MODE_PRIVATE);
            sp3.edit().putString("idno", data.get(position).get(6).toString() + sp2.getString("phone", "")).apply();
            sp2.edit().putString("curstate", data.get(position).get(7).toString()).apply();
            sp2.edit().putString("curcity", data.get(position).get(8).toString()).apply();

            if (!sp2.getString("identity", "").equals("Doctor")) {
                sp2.edit().putString("curspec", data.get(position).get(10).toString()).apply();
                sp2.edit().putString("curqua", data.get(position).get(9).toString()).apply();

                sp2.edit().putString("curclinic", data.get(position).get(11).toString()).apply();
            }

            v.getContext().startActivity(intent);
        }
        else{
            Intent intent = new Intent(v.getContext(), uploadReport.class);
            intent.putExtra("patient",data.get(position).get(12).toString());
            intent.putExtra("doctor",data.get(position).get(6).toString());
            context.startActivity(intent);
        }
        //
    }
});


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myholder extends RecyclerView.ViewHolder{
        ImageView dp,del;
        TextView dname, dspec, dhospital,dqua,delremark;
        public myholder(@NonNull View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.dp_patient_page_doctor);
            dname =itemView.findViewById(R.id.dname_patient_homepage);
            dspec =itemView.findViewById(R.id.dspec_patient_homepage);
            delremark=itemView.findViewById(R.id.deletepremark);
            del=itemView.findViewById(R.id.pathomedel);
            dhospital =itemView.findViewById(R.id.dhospital_patient_homepage);
            dqua=itemView.findViewById(R.id.dqua_patient_homepage);

        }



    }
    public void setAlert(String patient,String doctor){
        String pos="Yes";
        String neg="No";
        String msg="Do you want to delete the Doctor?";
        AlertDialog.Builder b=new AlertDialog.Builder(context);
        b.setMessage(msg);
        b.setPositiveButton(pos,(DialogInterface.OnClickListener) (dialog, which)->{
      deldoc(patient,doctor);

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

    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }

    public void deldoc(String patient,String doctor){
            String temp = "https://demo-uw46.onrender.com/api/patient/deleteDoctor/"+patient+"/"+doctor;

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

    }
