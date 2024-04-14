package com.example.trackhealth;

import android.content.Intent;
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


public class HomeDoctorAdapter extends RecyclerView.Adapter<HomeDoctorAdapter.MyViewHolder> {
    private List<List> data;

    public HomeDoctorAdapter(List<List> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_rec_ui, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List item = data.get(position);
        holder.pname.setText(item.get(0).toString());
        holder.pdob.setText(item.get(1).toString());
        holder.pgender.setText(item.get(2).toString());
        try {
            if(!item.get(6).toString().equals("")) {
                holder.img.setImageBitmap(getbitmap(item.get(6).toString()));
            }
        }
        catch (Exception e){
            holder.img.setImageResource(R.drawable.user);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView pname,pdob,pgender;
        public ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            pname = itemView.findViewById(R.id.homerecpat);
            pdob = itemView.findViewById(R.id.homerecdob);
            pgender = itemView.findViewById(R.id.homerecgen);
            img=itemView.findViewById(R.id.dp_patient_page_doctor);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            if(position !=RecyclerView.NO_POSITION){
                Intent intent=new Intent(v.getContext(), Patient_ListRecyview_inDoctor.class);
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
