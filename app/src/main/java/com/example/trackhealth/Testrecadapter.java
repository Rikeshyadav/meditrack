package com.example.trackhealth;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;

import java.util.List;

public class Testrecadapter extends RecyclerView.Adapter<Testrecadapter.MyViewHolder> {
    Context context;
    List<List> arr;
    String key;
    public Testrecadapter(Context context, List<List>arr,String key){
        this.arr=arr;
        this.context=context;
        this.key=key;
    }
    @NonNull
    @Override
    public Testrecadapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.testpres,parent,false);
        return new Testrecadapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Testrecadapter.MyViewHolder holder, int position) {
        if(key.toLowerCase().equals("general")){
            holder.del.setVisibility(View.GONE);
        }
        holder.tname.setText(arr.get(position).get(0).toString());
        holder.tstatus.setText(arr.get(position).get(1).toString());
        if(arr.get(position).get(1).toString().toLowerCase().equals("done")){
            holder.tstatus.setTextColor(context.getColor(R.color.green_button));
        }
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tname,tstatus;
        ImageView del;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tname=itemView.findViewById(R.id.testdesgin_title);
            tstatus=itemView.findViewById(R.id.testdesgin_status);
            del= itemView.findViewById(R.id.testdesigndel);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
