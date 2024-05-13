package com.example.trackhealth;

import android.app.Application;
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

import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;

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
        public TextView pname, pdob, pgender;
        public ImageView img;
        private List<List> data; // Add a reference to data variable

        public MyViewHolder(@NonNull View itemView, List<List> data) { // Pass data as a parameter
            super(itemView);
            this.data = data; // Assign passed data to local variable

            pname = itemView.findViewById(R.id.homerecpat);
            pdob = itemView.findViewById(R.id.homerecdob);
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
