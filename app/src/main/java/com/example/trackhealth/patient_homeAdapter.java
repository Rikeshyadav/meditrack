package com.example.trackhealth;

import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class patient_homeAdapter extends RecyclerView.Adapter<patient_homeAdapter.myholder> {

    private static List<List>data;
Context context;
    public patient_homeAdapter(List<List> data,Context context) {
        this.data = data;
        this.context=context;
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
        holder.dhospital.setText(data.get(position).get(3).toString());
        holder.dqua.setText(data.get(position).get(4).toString());
        if(!data.get(position).get(0).toString().equals("")) {
            holder.dp.setImageBitmap(getbitmap(data.get(position).get(0).toString()));

        }



    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView dp;
        TextView dname, dspec, dhospital,dqua;
        public myholder(@NonNull View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.dp_patient_page_doctor);
            dname =itemView.findViewById(R.id.dname_patient_homepage);
            dspec =itemView.findViewById(R.id.dspec_patient_homepage);
            dhospital =itemView.findViewById(R.id.dhospital_patient_homepage);
            dqua=itemView.findViewById(R.id.dqua_patient_homepage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            if(position !=RecyclerView.NO_POSITION){
                Intent intent=new Intent(v.getContext(), user_report_homepage.class);
                SharedPreferences sp=v.getContext().getSharedPreferences("docpat", Context.MODE_PRIVATE);
                sp.edit().putString("dname",data.get(position).get(1).toString()).apply();
                sp.edit().putString("dspec",data.get(position).get(2).toString()).apply();
                sp.edit().putString("dhos",data.get(position).get(3).toString()).apply();
                sp.edit().putString("dqua",data.get(position).get(4).toString()).apply();
                sp.edit().putString("dphoto",data.get(position).get(0).toString()).apply();
                sp.edit().putString("dabout",data.get(position).get(5).toString()).apply();
                sp.edit().putString("dphone",data.get(position).get(6).toString()).apply();
                SharedPreferences sp2=v.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
                sp2.edit().putString("curphone2",data.get(position).get(6).toString()).apply();
                sp2.edit().putString("curname",data.get(position).get(1).toString()).apply();
                SharedPreferences sp3=v.getContext().getSharedPreferences("issue", Context.MODE_PRIVATE);
                sp3.edit().putString("idno",data.get(position).get(6).toString()+sp.getString("phone","")).apply();
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
