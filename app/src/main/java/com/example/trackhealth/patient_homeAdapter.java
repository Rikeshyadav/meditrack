package com.example.trackhealth;

import android.content.Intent;
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

    public patient_homeAdapter(List<List> data) {
        this.data = data;
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
                v.getContext().startActivity(intent);
            }
        }
    }
}
