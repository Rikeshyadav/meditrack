package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class patient_homeAdapter extends RecyclerView.Adapter<patient_homeAdapter.myholder> {

    private static List<datamodel>data;

    public patient_homeAdapter(List<datamodel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pateint_iui,parent,false);
        return new myholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull myholder holder, int position) {
        datamodel currentItem=data.get(position);
        holder.dp.setImageResource(data.get(position).getDp());
        holder.txt1.setText(data.get(position).getText1());
        holder.txt2.setText(data.get(position).getText2());
        holder.txt3.setText(data.get(position).getText3());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class myholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView dp;
        TextView txt1,txt2,txt3;
        public myholder(@NonNull View itemView) {
            super(itemView);
            dp=itemView.findViewById(R.id.shapeimage);
            txt1=itemView.findViewById(R.id.itemtext);
            txt2=itemView.findViewById(R.id.itemtext2);
            txt3=itemView.findViewById(R.id.itemtext3);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            if(position !=RecyclerView.NO_POSITION){
                datamodel clickedItem=data.get(position);
                Intent intent=new Intent(v.getContext(), user_report_homepage.class);
                v.getContext().startActivity(intent);
            }
        }
    }
}
