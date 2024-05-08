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

public class MedicineExtraAdapter1 extends RecyclerView.Adapter<MedicineExtraAdapter1.newViewHolder> {
    List<List> listmodel;
    List<List>listfun;
    public MedicineExtraAdapter1(List<List> modellist){
        this.listmodel=modellist;
    }

    @NonNull
    @Override
    public newViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.boxlayout,parent,false);
        return new newViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull newViewHolder holder, int position) {
        holder.image1.setImageResource(R.drawable.tabletimg);
        holder.name.setText(listmodel.get(position).get(0).toString());
        holder.price.setText(listmodel.get(position).get(2).toString());
        int p=position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), DescMedicine.class);
                i.putExtra("mname",listmodel.get(p).get(0).toString());
                i.putExtra("mdesc",listmodel.get(p).get(1).toString());
                i.putExtra("mrate",listmodel.get(p).get(3).toString());
                i.putExtra("mprice",listmodel.get(p).get(2).toString());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listmodel.size();
    }

    public class newViewHolder extends RecyclerView.ViewHolder{
          ImageView image1;
          TextView name,price;

        public newViewHolder(@NonNull View itemView) {
            super(itemView);
            image1=itemView.findViewById(R.id.horfun);
            name=itemView.findViewById(R.id.medicne_name1);
            price=itemView.findViewById(R.id.price1);
        }
    }

}
