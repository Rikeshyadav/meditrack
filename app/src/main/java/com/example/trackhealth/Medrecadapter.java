package com.example.trackhealth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class Medrecadapter extends RecyclerView.Adapter<Medrecadapter.MyViewHolder> {
    Context context;
    List<List> arr;
    public Medrecadapter(Context context, List<List> medfinal) {
        arr=medfinal;
        this.context=context;
    }

    @NonNull
    @Override
    public Medrecadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.medicinepres_ui,parent,false);
        return new Medrecadapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Medrecadapter.MyViewHolder holder, int position) {
holder.medname.setText(arr.get(position).get(0).toString());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr.remove(position);
                notifyDataSetChanged();
            }
        });
if(!arr.get(position).get(1).toString().trim().equals("")) {
    holder.medmorn.setText(arr.get(position).get(1).toString());
}
else{

    holder.mtitle.setVisibility(View.GONE);
}


        if(!arr.get(position).get(2).toString().trim().equals("")) {
            holder.medafter.setText(arr.get(position).get(2).toString());
        }
        else{

            holder.atitle.setVisibility(View.GONE);
        }


        if(!arr.get(position).get(3).toString().trim().equals("")) {
            holder.medsnack.setText(arr.get(position).get(3).toString());
        }
        else{

            holder.etitle.setVisibility(View.GONE);
        }


        if(!arr.get(position).get(4).toString().trim().equals("")) {
            holder.meddinner.setText(arr.get(position).get(4).toString());
        }
        else{

            holder.ntitle.setVisibility(View.GONE);
        }

        holder.medstart.setText(arr.get(position).get(5).toString());
        holder.medend.setText(arr.get(position).get(6).toString());

        holder.medquan.setText(arr.get(position).get(7).toString());

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
TextView medname,medmorn,medafter,medsnack,meddinner,medstart,medend,medquan;
LinearLayout mtitle,atitle,etitle,ntitle;
ImageView del;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            medname=itemView.findViewById(R.id.meddesignmname);
            medmorn=itemView.findViewById(R.id.meddesign_morning_);
            medafter=itemView.findViewById(R.id.meddesign_after_);

            del=itemView.findViewById(R.id.meddesigndel);

            mtitle=itemView.findViewById(R.id.meddesgin_morning);
            atitle=itemView.findViewById(R.id.meddesgin_after);
            etitle=itemView.findViewById(R.id.meddesgin_even);
            ntitle=itemView.findViewById(R.id.meddesgin_night);

            medsnack=itemView.findViewById(R.id.meddesign_snack_);
            meddinner=itemView.findViewById(R.id.meddesign_dinner_);
            medstart=itemView.findViewById(R.id.meddesign_start_);
            medend=itemView.findViewById(R.id.meddesign_end_);
            medquan=itemView.findViewById(R.id.meddesign_quan_);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
