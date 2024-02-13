package com.example.trackhealth;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class pendingAdapter extends RecyclerView.Adapter<pendingAdapter.MyViewHolder> {
    private List<List> data;

    public pendingAdapter(List<List> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_ui, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        List item = data.get(position);
        holder.hosname.setText(item.get(0).toString());
        holder.docname.setText(item.get(1).toString());
        holder.spec.setText(item.get(2).toString());
        holder.qua.setText(item.get(3).toString());
        holder.issue.setText(item.get(4).toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView hosname,docname,spec,qua,issue;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            hosname = itemView.findViewById(R.id.pending_rec_hos);
            docname = itemView.findViewById(R.id.pendocnm);
            spec = itemView.findViewById(R.id.pendocspec);
            qua = itemView.findViewById(R.id.pendocqua);
            issue = itemView.findViewById(R.id.pendocissue);

        }
    }
}
