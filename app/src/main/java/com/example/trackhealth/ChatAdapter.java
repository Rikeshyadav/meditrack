package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    List<List> arr;
    Context context;
    public ChatAdapter(List<List> data, Context context){
        this.arr=data;
        this.context=context;
    }

    public void updateData(List<List> messageList) {
        this.arr = messageList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.chat_uidesgin, parent, false);
        return new ChatAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
String txt=arr.get(position).get(0).toString();
String user=arr.get(position).get(1).toString();
        SharedPreferences sp=context.getSharedPreferences("user",Context.MODE_PRIVATE);
String id=sp.getString("phone","");
if(id.equals(user)){
    holder.opp.setVisibility(View.GONE);
    holder.me.setText(txt);
}
else{
    holder.me.setVisibility(View.GONE);
    holder.opp.setText(txt);
}
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView me,opp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            me=itemView.findViewById(R.id.chatdesginme);
            opp=itemView.findViewById(R.id.chatdesginopp);
        }
    }
}
