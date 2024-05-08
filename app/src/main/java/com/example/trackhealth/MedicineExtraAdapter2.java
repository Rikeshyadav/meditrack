package com.example.trackhealth;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineExtraAdapter2 extends RecyclerView.Adapter<MedicineExtraAdapter2.medicienholder> {
   List<List> MediList;
   List<List>listfun;
   public MedicineExtraAdapter2(List<List> modellist){
       this.MediList=modellist;
   }

    @NonNull
    @Override
    public medicienholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_medicine_item,parent,false);
        return new medicienholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull medicienholder holder, int position) {
       holder.imageView.setImageResource(R.drawable.tabletimg);
       holder.nametxt.setText(MediList.get(position).get(0).toString());
       if(MediList.get(position).get(1).toString().length()>35){
           holder.detailText.setText(MediList.get(position).get(1).toString().substring(0,35)+"...");
       }
       else{
           holder.detailText.setText(MediList.get(position).get(1).toString());
       }

       holder.offerText.setText(MediList.get(position).get(2).toString());
       holder.ratingBar.setRating(Float.parseFloat(MediList.get(position).get(3).toString()));
       int p=position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),DescMedicine.class);
                i.putExtra("mname",MediList.get(p).get(0).toString());
                i.putExtra("mdesc",MediList.get(p).get(1).toString());
                i.putExtra("mrate",MediList.get(p).get(3).toString());
                i.putExtra("mprice",MediList.get(p).get(2).toString());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MediList.size();
    }

    public class medicienholder  extends RecyclerView.ViewHolder{

         private TextView nametxt,detailText,offerText;
         private RatingBar ratingBar;
         private ImageView imageView;
        public medicienholder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.medic_image);
            nametxt=itemView.findViewById(R.id.medicine_name);
            detailText=itemView.findViewById(R.id.details);
            offerText=itemView.findViewById(R.id.rpice);
            ratingBar=itemView.findViewById(R.id.rating_one);
        }
    }
}
