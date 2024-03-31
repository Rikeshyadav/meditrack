package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class myadapterfor_list extends RecyclerView.Adapter<MyHolder> {
    private Context context;
    List<dataModel_exerciseGyan>dataModels;

    public myadapterfor_list(Context context,List<dataModel_exerciseGyan> data) {
        this.context = context;
        this.dataModels=data;
    }

    public void setSearchList(List<dataModel_exerciseGyan> datasearchList){
        this.dataModels=datasearchList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.dataview_recyc_item_exercisegyan,
                parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.image.setImageResource(dataModels.get(position).getImage());
        holder.title.setText(dataModels.get(position).getTitle());
        holder.desc.setText(dataModels.get(position).getDatadesc());
        holder.language.setText(dataModels.get(position).getDataLanguage());
        holder.card_fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(context, Exercisegyan_second2.class);
                intent.putExtra("image",dataModels.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("Title",dataModels.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("Desc",dataModels.get(holder.getAdapterPosition()).getDatadesc());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }
}
class MyHolder extends RecyclerView.ViewHolder{
    ImageView image;
    TextView title,desc,language;
    CardView card_fun;
    public MyHolder(@NonNull View itemView) {
        super(itemView);
        image=itemView.findViewById(R.id.image_recy);
        title=itemView.findViewById(R.id.recytitle);
        desc=itemView.findViewById(R.id.recyDESc);
        language=itemView.findViewById(R.id.show_language);
        card_fun=itemView.findViewById(R.id.Rec_card);

    }
}