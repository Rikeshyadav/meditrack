package com.example.trackhealth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
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

import java.util.List;

public class Parentrecycle_Adapter extends RecyclerView.Adapter<Parentrecycle_Adapter.Myholder> {
    private final List<List> dataList;
    Context context;
    public Parentrecycle_Adapter(Context context, List<List> dataList) {
        this.context=context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_ui_pateint,parent,false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
          holder.problem_name.setText(dataList.get(position).get(0).toString());
          holder.show_date1.setText(dataList.get(position).get(1).toString());
          if(dataList.get(position).get(3).toString().equals("")){
              holder.show_date2.setText(dataList.get(position).get(3).toString());
              holder.lasttxt.setText("No visit yet");
          }
          else {
              holder.show_date2.setText(dataList.get(position).get(3).toString());
          }

        SharedPreferences user=holder.itemView.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);
          if(user.getString("identity","").equals("Patient")){
              holder.del.setVisibility(View.GONE);
          }
          holder.del.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  setAlert(dataList.get(position).get(2).toString(),position);
              }
          });
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i=new Intent(v.getContext(), User_prescription_activity_page.class);
                  i.putExtra("issueid",dataList.get(position).get(2).toString());
                  SharedPreferences sp=v.getContext().getSharedPreferences("issue",Context.MODE_PRIVATE);
                  sp.edit().putString("issueid",dataList.get(position).get(2).toString()).apply();
                  sp.edit().putString("issuetitle",dataList.get(position).get(0).toString()).apply();
                  v.getContext().startActivity(i);
              }
          });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class Myholder extends  RecyclerView.ViewHolder implements  View.OnClickListener{
             TextView problem_name,show_date1,show_date2,lasttxt;
             ImageView del;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            problem_name = itemView.findViewById(R.id.problemvalue);
            show_date1 = itemView.findViewById(R.id.year);
            show_date2 = itemView.findViewById(R.id.year2);
            del=itemView.findViewById(R.id.problemdel);
            lasttxt=itemView.findViewById(R.id.time2);
        }

        @Override
        public void onClick(View view) {

        }
/*


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.inflate(R.menu.doctor_edit_menu);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int itemId=item.getItemId();
                    if(itemId== R.id.edit_recy){
                        showEditDialog(position);
                        return true;}
                    else if(itemId == R.id.delete_recy){
                        showDeleteDialog(position);
                        return true;}
                    else{
                        return false;}

                }
            });
            popupMenu.show();
        }*/
/*
        private void showEditDialog(int position) {
            View editView = LayoutInflater.from(context).inflate(R.layout.doctor_input, null);
            EditText userName = editView.findViewById(R.id.editIssue);
            EditText userNum = editView.findViewById(R.id.date);

            new AlertDialog.Builder(context)
                    .setView(editView)
                    .setPositiveButton("OK", (dialog, which) -> {
                       // datamodelDoctorIssue userData = dataList.get(position);
                       // userData.setUsername(userName.getText().toString());
                        //userData.setNumber(userNum.getText().toString());
                        notifyItemChanged(position);
                        Toast.makeText(context, "User Information is Edited", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        }

        private void showDeleteDialog(int position) {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setIcon(R.drawable.delete)
                    .setMessage("Are you sure you want to delete this information?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dataList.remove(position);
                        notifyItemRemoved(position);
                        Toast.makeText(context, "Deleted this Information", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        }*/
    }
    public void delissue(String id,int position){
SharedPreferences sp=context.getSharedPreferences("issue",Context.MODE_PRIVATE);
        String temp="https://demo-uw46.onrender.com/api/issue/deleteIssue/"+id;
        Toast.makeText(context,sp.getString("issueid",""),Toast.LENGTH_SHORT).show();
        try{

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.DELETE, temp,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
notifyDataSetChanged();
dataList.remove(position);
                            Intent i = new Intent(context, User_prescription_activity_page.class);
                            i.putExtra("activity", "prescription_page");
                            sp.edit().putString("isuploaded", "yes").apply();
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(i);

                    } }catch (JSONException e) {
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
            Toast.makeText(context, "fail to load - "+e.toString(), Toast.LENGTH_SHORT).show();

        }

    }


    public void setAlert(String id,int posi){
        String pos="Yes";
        String neg="No";
        String msg="Do you want to delete Patient's issue?";
        androidx.appcompat.app.AlertDialog.Builder b=new androidx.appcompat.app.AlertDialog.Builder(context);
        b.setMessage(msg);
        b.setPositiveButton(pos,(DialogInterface.OnClickListener) (dialog, which)->{
            delissue(id,posi);


        });
        b.setNegativeButton(neg,(DialogInterface.OnClickListener) (dialog,which)->{

            dialog.cancel();
        });
        AlertDialog ad=b.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                ad.getWindow().getDecorView().setBackgroundColor(context.getResources().getColor(R.color.black));
            }
        });

        ad.show();

    }

}
