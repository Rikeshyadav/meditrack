package com.example.trackhealth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {
    List<List> data;
    Context context;
    public ReviewAdapter(List<List> data, Context context){
        this.data=data;
        this.context=context;
    }
    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.reviewdesgin, parent, false);
        return new ReviewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position) {

        holder.name.setText(data.get(position).get(1).toString());
        holder.txt.setText(data.get(position).get(2).toString());
        if(!data.get(position).get(0).toString().equals("")) {
            holder.img.setImageBitmap(getbitmap(data.get(position).get(0).toString()));
        }
        try {
            holder.rb.setRating(Float.parseFloat(data.get(position).get(3).toString()));
        }
        catch(Exception e){
        }
holder.date.setText(data.get(position).get(4).toString());
        holder.time.setText(data.get(position).get(5).toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView img;
        TextView txt,name,date,time;
        RatingBar rb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.reviewdesgin_img);
            rb=itemView.findViewById(R.id.review_rating);
            txt=itemView.findViewById(R.id.review_text);
            name=itemView.findViewById(R.id.reviewname);
            date=itemView.findViewById(R.id.reviewdate);
            time=itemView.findViewById(R.id.reviewtime);
        }
    }

    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }
}
