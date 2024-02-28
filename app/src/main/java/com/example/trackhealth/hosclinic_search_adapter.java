package com.example.trackhealth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class hosclinic_search_adapter extends RecyclerView.Adapter<hosclinic_search_adapter.MyViewHolder> {
    List<List> data;
    public hosclinic_search_adapter(List<List> data){
        this.data=data;
    }
    @NonNull
    @Override
    public hosclinic_search_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clinic_searchdesign, parent, false);
        return new hosclinic_search_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull hosclinic_search_adapter.MyViewHolder holder, int position) {
        List item=data.get(position);
        holder.name.setText(item.get(1).toString());
        holder.address.setText(item.get(2).toString());
        holder.type.setText(item.get(3).toString());
        holder.contact.setText(item.get(4).toString());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,address,type,contact;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_hosclinic_searchdesign);
            name=itemView.findViewById(R.id.name_hosclinic_searchdesign);
            contact=itemView.findViewById(R.id.contact_hosclinic_searchdesign);
            address=itemView.findViewById(R.id.address_hosclinic_searchdesign);
            type=itemView.findViewById(R.id.type_hosclinic_searchdesign);
        }
    }
}
