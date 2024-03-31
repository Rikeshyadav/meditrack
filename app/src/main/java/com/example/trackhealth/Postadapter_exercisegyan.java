package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class Postadapter_exercisegyan extends RecyclerView.Adapter<Postadapter_exercisegyan.PostViewHolder> {
    private Context context1;
    List<postitem_exercisegyan> postitems;

    public Postadapter_exercisegyan(Context context1, List<postitem_exercisegyan> postitems) {
        this.context1=context1;
        this.postitems = postitems;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item_container_exercisegyan,
                parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
           holder.setPostImage(postitems.get(position));
           holder.postImageView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent=new Intent(context1, image_Activity_exerciseGyan.class);
                   intent.putExtra("image",postitems.get(holder.getAdapterPosition()).getImage());
                   context1.startActivity(intent);
               }
           });

    }

    @Override
    public int getItemCount() {
        return postitems.size();
    }

     class PostViewHolder extends RecyclerView.ViewHolder  {
     RoundedImageView postImageView;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postImageView=itemView.findViewById(R.id.imagepost);
        }

        void setPostImage(postitem_exercisegyan postItem) {
        postImageView.setImageResource(postItem.getImage());
        }

    }
}
