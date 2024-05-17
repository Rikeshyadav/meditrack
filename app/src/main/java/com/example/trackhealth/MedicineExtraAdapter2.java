package com.example.trackhealth;

import android.Manifest;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedicineExtraAdapter2 extends RecyclerView.Adapter<MedicineExtraAdapter2.medicienholder> {
   List<List> MediList;
    List<List> originalList;
   public MedicineExtraAdapter2(List<List> modellist){
       this.MediList=modellist;
       this.originalList = new ArrayList<>(modellist);
       this.MediList=new ArrayList<>(modellist);
   }
    public void resetData() {
        MediList.clear();
        MediList.addAll(originalList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public medicienholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_medicine_item,parent,false);
        return new medicienholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull medicienholder holder, int position) {
       holder.imageView.setImageResource((Integer) MediList.get(position).get(0));
      // holder.imageView.setImageResource(R.drawable.tabletimg);
        holder.nametxt.setText(MediList.get(position).get(1).toString());
        if(MediList.get(position).get(2).toString().length()>35){
            holder.detailText.setText(MediList.get(position).get(2).toString().substring(0,35)+"...");
        }
        else{
            holder.detailText.setText(MediList.get(position).get(2).toString());
        }

        holder.offerText.setText(MediList.get(position).get(3).toString());
        holder.ratingBar.setRating(Float.parseFloat(MediList.get(position).get(4).toString()));
        int p=position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),DescMedicine.class);
                i.putExtra("image", (Integer) MediList.get(p).get(0));
                i.putExtra("mname",MediList.get(p).get(1).toString());
                i.putExtra("mdesc",MediList.get(p).get(2).toString());
                i.putExtra("mrate",MediList.get(p).get(4).toString());
                i.putExtra("mprice",MediList.get(p).get(3).toString());
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
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterPattern = constraint.toString().toLowerCase().trim();

                List<List> filteredList = new ArrayList<>();
                if (filterPattern.isEmpty()) {
                    // If the search query is empty, use the original dataset
                    filteredList.addAll(originalList);
                } else {
                    // Filter the original dataset based on the search query
                    for (List item : originalList) {
                        if (item.get(1).toString().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                MediList.clear();
                MediList.addAll((List<List>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
