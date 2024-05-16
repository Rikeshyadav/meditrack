package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PrescriptionParentAdapter extends RecyclerView.Adapter<PrescriptionParentAdapter.MyViewHolder> {
List<List> arr;
Context context;
    public PrescriptionParentAdapter(Context context,List<List> arr){

        this.context=context;
        this.arr=arr;
    }
    @NonNull
    @Override
    public PrescriptionParentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescriptionparent_design, parent, false);
        return new PrescriptionParentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionParentAdapter.MyViewHolder holder, int position) {
holder.pid.setText(arr.get(position).get(2).toString());
SharedPreferences sp=context.getSharedPreferences("issue",Context.MODE_PRIVATE);
sp.edit().putString("pid",arr.get(position).get(2).toString()).apply();
        String data=arr.get(position).get(0).toString();
        if (data.length() >= 15) {
            data = data.substring(0, 15) + "...";
        }
        holder.note.setText(data);
        holder.date.setText(arr.get(position).get(1).toString());
        holder.test.setText(arr.get(position).get(4).toString());
        holder.med.setText(arr.get(position).get(3).toString());
        holder.sno.setText("Sno. "+arr.get(position).get(5).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(view.getContext(),PrescriptionShow.class);
                SharedPreferences sp=view.getContext().getSharedPreferences("issue",Context.MODE_PRIVATE);
                sp.edit().putString("pid",arr.get(position).get(2).toString()).apply();
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

TextView pid,date,note,test,med,sno;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sno=itemView.findViewById(R.id.pparent_sno);
            pid=itemView.findViewById(R.id.pparent_pid);
            note=itemView.findViewById(R.id.pparent_note);
            date=itemView.findViewById(R.id.pparent_date);
            test=itemView.findViewById(R.id.pparent_test);
            med=itemView.findViewById(R.id.pparent_medicine);
        }
    }
}
