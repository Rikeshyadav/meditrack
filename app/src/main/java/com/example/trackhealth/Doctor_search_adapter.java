package com.example.trackhealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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
            //holder.contact.setText(item.get(4).toString());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        ImageView contact;
        TextView name,qualification,specification,address;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView=itemView.findViewById(R.id.dp_doctor_searchdesign);
            name=itemView.findViewById(R.id.name_doctor_searchdesign);
            qualification=itemView.findViewById(R.id.qualification_doctor_searchdesign);
            specification=itemView.findViewById(R.id.speciality_doctor_searchdesign);
            contact=itemView.findViewById(R.id.contact_doctor_searchdesign);
            address=itemView.findViewById(R.id.address_doctor_searchdesign);
        }
    }
}
