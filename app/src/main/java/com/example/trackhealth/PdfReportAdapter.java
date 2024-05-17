package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;

import org.w3c.dom.Text;

import java.util.List;

public class PdfReportAdapter extends RecyclerView.Adapter<PdfReportAdapter.ViewHolder> {
    @NonNull
    @Override
    public PdfReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdfreportdesgin, parent, false);
        return new PdfReportAdapter.ViewHolder(itemView);
    }
    Context context;
    List<List> arr;
    public PdfReportAdapter(Context context, List<List> arr){
        this.context=context;
        this.arr=arr;
    }

    @Override
    public void onBindViewHolder(@NonNull PdfReportAdapter.ViewHolder holder, int position) {

        holder.fname.setText(arr.get(position).get(0).toString());
        holder.date.setText(arr.get(position).get(2).toString());
        holder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(arr.get(position).get(1).toString()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Add this flag if the context is not an activitys
context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(),ViewPdf.class);
                intent.putExtra("filename",arr.get(position).get(0).toString());
                intent.putExtra("fileurl",arr.get(position).get(1).toString());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fname,date;
        ImageView img,down;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fname=itemView.findViewById(R.id.pdfdesign_txt);
            img=itemView.findViewById(R.id.pdfdesign_img);
            down=itemView.findViewById(R.id.pdfdesign_down);
            date=itemView.findViewById(R.id.pdfdesign_date);
        }
    }
}
