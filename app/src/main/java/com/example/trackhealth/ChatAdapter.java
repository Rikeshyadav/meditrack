package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;

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

    holder.t2.setText(arr.get(position).get(3).toString());
    holder.l1.setVisibility(View.GONE);

    if(!arr.get(position).get(4).toString().trim().equals("same")){
        holder.d2.setText(arr.get(position).get(2).toString());
        holder.d2.setVisibility(View.VISIBLE);
    }
}
else{
    holder.me.setVisibility(View.GONE);
    holder.opp.setText(txt);
    holder.t1.setText(arr.get(position).get(3).toString());
holder.l2.setVisibility(View.GONE);
    if(!arr.get(position).get(4).toString().trim().equals("same")) {
        holder.d1.setText(arr.get(position).get(2).toString());
        holder.d1.setVisibility(View.VISIBLE);
    }
}
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView me,opp,t1,t2,d1,d2;
        LinearLayout l1,l2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            me=itemView.findViewById(R.id.chatdesginme);
            opp=itemView.findViewById(R.id.chatdesginopp);
            t1=itemView.findViewById(R.id.chattime1);
            t2=itemView.findViewById(R.id.chattime2);
            d1=itemView.findViewById(R.id.chatdt1);
            d2=itemView.findViewById(R.id.chatdt2);
            l1=itemView.findViewById(R.id.chatdesginoppll);
            l2=itemView.findViewById(R.id.chatdesginoppll2);
        }
    }


    public void addMessage(List<List> messageData) {
        arr.add(messageData);
        notifyItemInserted(arr.size() - 1);
    }
}
