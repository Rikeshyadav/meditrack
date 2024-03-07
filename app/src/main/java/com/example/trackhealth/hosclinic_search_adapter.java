package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hospital_searchdesign, parent, false);
        return new hosclinic_search_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull hosclinic_search_adapter.MyViewHolder holder, int position) {

        List item=data.get(position);
        holder.name.setText(item.get(0).toString());
        holder.address.setText(item.get(1).toString());
        holder.type.setText(item.get(2).toString());
        holder.ratecount.setText(item.get(3).toString());
        holder.ratingBar.setRating(Float.parseFloat(item.get(3).toString()));
        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = item.get(4).toString();
                if (!phoneNumber.isEmpty()) {
                    Intent myIntent = new Intent(Intent.ACTION_CALL);
                    String phNum = "tel:" + phoneNumber;
                    myIntent.setData(Uri.parse(phNum));
                    view.getContext().startActivity(myIntent);
                } else {

                    Toast.makeText(view.getContext(), "Phone number not available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        RatingBar ratingBar;

        TextView name,address,type,contact,ratecount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_hosclinic_searchdesign2);
            name=itemView.findViewById(R.id.name_hosclinic_searchdesign2);
            contact=itemView.findViewById(R.id.phone_hosclinic_searchdesign2);
            address=itemView.findViewById(R.id.address_hosclinic_searchdesign2);
            type=itemView.findViewById(R.id.type_hosclinic_searchdesign2);
            ratecount=itemView.findViewById(R.id.ratecount_hosclinic);
            ratingBar=itemView.findViewById(R.id.ratingBar);
        }
    }
}
