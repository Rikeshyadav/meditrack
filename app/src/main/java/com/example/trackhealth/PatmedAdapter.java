package com.example.trackhealth;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PatmedAdapter extends RecyclerView.Adapter<PatmedAdapter.ViewHolder> {
    Context context;
    List<List> arr;
    public PatmedAdapter(Context context, List<List> arr){
        this.context=context;
        this.arr=arr;
    }
    @NonNull
    @Override
    public PatmedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.patmeddesign, parent, false);
        return new PatmedAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PatmedAdapter.ViewHolder holder, int position) {
holder.mname.setText(arr.get(position).get(0).toString());
        holder.quan.setText(arr.get(position).get(1).toString());
        holder.dur.setText(arr.get(position).get(2).toString());
        holder.leftdays.setText(arr.get(position).get(3).toString()+" days left");
        holder.dos.setText(arr.get(position).get(4).toString());
        holder.tdays.setText(arr.get(position).get(5).toString()+" days");
        holder.misdays.setText(arr.get(position).get(6).toString()+" days");;
        holder.tval.setText(arr.get(position).get(8).toString());
String currdate=arr.get(position).get(10).toString();
        String startdate=arr.get(position).get(11).toString();


SharedPreferences sp=context.getSharedPreferences("user",Context.MODE_PRIVATE);
if(sp.getString("identity","").equals("Doctor")){
    holder.consume.setVisibility(View.GONE);
}

        // Parse the date strings to LocalDate objects using a DateTimeFormatter
        DateTimeFormatter formatter = null;
        LocalDate date1 = null;
        LocalDate date2 = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            date1 = LocalDate.parse(currdate, formatter);
            date2 = LocalDate.parse(startdate, formatter);


            if(date1.isBefore(date2)) {
                holder.misdays.setText("0 days");
                holder.status.setText("Dosage will Start Soon");
                holder.consume.setBackgroundDrawable(context.getDrawable(R.drawable.gray_but));
            }
        }



                //
if(!arr.get(position).get(9).toString().equals("allow")){
    holder.consume.setText("Already Consumed");
    holder.status.setText("Consumed");
    holder.consume.setBackgroundDrawable(context.getDrawable(R.drawable.orange_button2));
}
        LocalDate finalDate = date1;
        LocalDate finalDate1 = date2;
        holder.consume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (finalDate.isBefore(finalDate1)) {

                        Toast.makeText(context, "Medicine is to consume from " + startdate, Toast.LENGTH_LONG).show();
                    } else {
                        if (arr.get(position).get(9).toString().equals("allow")) {
                            if(Integer.parseInt(arr.get(position).get(5).toString())>=Integer.parseInt(arr.get(position).get(12).toString())) {
                                holder.status.setText("Ongoing");
                                updatetab(arr.get(position).get(7).toString(), holder.consume, arr.get(position).get(8).toString(),holder.status);
                            }else{
                                holder.consume.setText("Completed");
                                holder.status.setText("Completed");
                            }
                        }
                        else{
                            Toast.makeText(context, "You have already consumed for today", Toast.LENGTH_SHORT).show();
                        }
                    }
                }


            }

        });
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mname,quan,dur,dos,leftdays,tdays,misdays,status,tval;
        AppCompatButton consume;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mname=itemView.findViewById(R.id.patmeddesigntitle);
            quan=itemView.findViewById(R.id.patmeddesignquan);
            dur=itemView.findViewById(R.id.patmeddesigndur);
            tval=itemView.findViewById(R.id.patmedtaknevalue);
            dos=itemView.findViewById(R.id.patmeddesigndos);
            leftdays=itemView.findViewById(R.id.patmeddesigndaysleft);
            tdays=itemView.findViewById(R.id.patmeddesigntdays);
            misdays=itemView.findViewById(R.id.patmeddesignmisdays);
            consume=itemView.findViewById(R.id.patmeddesignconsume);
            status=itemView.findViewById(R.id.patmedstatus);
        }
    }


    public void updatetab(String mid, AppCompatButton button,String pid,TextView status) {
        SharedPreferences sp=context.getSharedPreferences("issue",Context.MODE_PRIVATE);
        String temp = "https://demo-uw46.onrender.com/api/patient/updateTaken";
        try {
JSONObject jsonObject=new JSONObject();
jsonObject.put("issueid",sp.getString("issueid",""));
            jsonObject.put("idno",sp.getString("idno",""));
            jsonObject.put("prescriptionId",pid);
            jsonObject.put("medid",mid);
            Toast.makeText(context,sp.getString("pid","")+"\n"+sp.getString("issueid",""), Toast.LENGTH_SHORT).show();
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, jsonObject, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {


                          //  JSONObject jsonobj = response.getJSONObject("prescription");
                            button.setText("Consumed");
                            button.setFocusable(false);
                            button.setClickable(false);
                            status.setText("Consumed");
                            button.setBackgroundDrawable(context.getDrawable(R.drawable.orange_button2));
                            Toast.makeText(context,"consumed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(context,response.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        //progressBar.setVisibility(View.GONE);
                        //retry.setVisibility(View.VISIBLE);
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

        /*        progressBar.setVisibility(View.GONE);
                empty.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);*/
                    //progressBar.setVisibility(View.GONE);
                    //retry.setVisibility(View.VISIBLE);
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(context);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
 /*       progressBar.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        nodata.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);*/
            //   progressBar.setVisibility(View.GONE);
            // retry.setVisibility(View.VISIBLE);
            Toast.makeText(context, "fail to load - " + e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

}
