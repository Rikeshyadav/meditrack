package com.example.trackhealth;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class pateint_prescriptionAdapter extends RecyclerView.Adapter<pateint_prescriptionAdapter.myholder> {
    private static List<datamodel2> data;

    public pateint_prescriptionAdapter(List<datamodel2> data) {
        this.data = data;
    }

    @androidx.annotation.NonNull
    @Override
    public myholder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_ui_pateint,parent,false);
        return new myholder(view) ;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull myholder holder, int position) {
        datamodel2 currentItem=data.get(position);
        holder.text1.setText(data.get(position).getTxt1());
       // holder.text2.setText(data.get(position).getTxt2());
        holder.text3.setText(data.get(position).getTxt3());
        holder.text4.setText(data.get(position).getTxt4());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  static class myholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text1,text2,text3,text4;
      public myholder(@NonNull View itemView){
          super(itemView);

          text1=itemView.findViewById(R.id.problem);
          text2=itemView.findViewById(R.id.time1);
          text3=itemView.findViewById(R.id.year);
          text4=itemView.findViewById(R.id.doctor1);
          itemView.setOnClickListener(this);
      }

        @Override
        public void onClick(View v) {
                int position=getAdapterPosition();
                if(position !=RecyclerView.NO_POSITION){
                    datamodel2 clickedItem=data.get(position);
                    Intent intent=new Intent(v.getContext(), User_prescription_activity_page.class);

                    v.getContext().startActivity(intent);
                }
            }
        }
}

