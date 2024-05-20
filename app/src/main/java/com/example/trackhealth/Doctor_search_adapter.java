package com.example.trackhealth;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Doctor_search_adapter extends RecyclerView.Adapter<Doctor_search_adapter.MyViewHolder> {
    List<List> data;
    Context context;
    public Doctor_search_adapter(List<List> data, Context context){
        this.data=data;
        this.context=context;
    }
    @NonNull
    @Override
    public Doctor_search_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.doctor_searchdesign, parent, false);
        return new Doctor_search_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Doctor_search_adapter.MyViewHolder holder, int position) {
        List item=data.get(position);
        holder.name.setText("Dr."+item.get(0).toString());
        holder.address.setText(item.get(1).toString());
        holder.specification.setText(item.get(2).toString());
        holder.qualification.setText(item.get(3).toString());
        holder.hos.setText(item.get(7).toString());
        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = item.get(4).toString();
                if (!phoneNumber.isEmpty()) {
                    if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        // You have permission, so proceed with making the call
                        Intent myIntent = new Intent(Intent.ACTION_CALL);
                        myIntent.setData(Uri.parse("tel:" + phoneNumber));
                        view.getContext().startActivity(myIntent);
                    } else {
                        // Request the CALL_PHONE permission
                        ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                } else {
                    Toast.makeText(view.getContext(), "Phone number not available", Toast.LENGTH_SHORT).show();
                }
            }

        });
        //holder.contact.setText(item.get(4).toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),SearchProfile.class);
                i.putExtra("nonuserprofile", item.get(4).toString());
                view.getContext().startActivity(i);
            }
        });
if(item.get(6).toString().equals("") ) {
    if (item.get(5).toString().equals("male") || item.get(5).toString().equals("Male")) {
        holder.imageView.setImageResource(R.drawable.doctor_male);
    } else {
        holder.imageView.setImageResource(R.drawable.doctor_female);
    }
}else{
    holder.imageView.setImageBitmap(getbitmap(item.get(6).toString()));
}

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageView contact;
        LinearLayout l;
        TextView name,qualification,specification,address,hos;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.dp_doctor_searchdesign);
            name=itemView.findViewById(R.id.name_doctor_searchdesign);
            qualification=itemView.findViewById(R.id.qualification_doctor_searchdesign);
            specification=itemView.findViewById(R.id.speciality_doctor_searchdesign);
            contact=itemView.findViewById(R.id.contact_doctor_searchdesign);
            l=itemView.findViewById(R.id.maincontent_doctor_search_design);
            address=itemView.findViewById(R.id.address_doctor_searchdesign);
            hos=itemView.findViewById(R.id.dhospital_searchdoctor);
        }
    }

    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }

}
