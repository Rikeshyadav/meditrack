package com.example.trackhealth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
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
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Parentrecycle_Adapter extends RecyclerView.Adapter<Parentrecycle_Adapter.Myholder> {
    private final List<datamodelDoctorIssue> dataList;
    Context context;
    public Parentrecycle_Adapter(Context context, List<datamodelDoctorIssue> dataList) {
        this.context=context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_item,parent,false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        datamodelDoctorIssue data=dataList.get(position);
          holder.problem_name.setText(data.username);
          holder.show_date.setText(data.number);
          holder.itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent i=new Intent(v.getContext(), User_prescription_activity_page.class);
                  v.getContext().startActivity(i);
              }
          });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class Myholder extends  RecyclerView.ViewHolder implements  View.OnClickListener{
             TextView problem_name,show_date,goin_complete;
             ImageView more_option;
             SwitchCompat toggle;

        public Myholder(@NonNull View itemView) {
            super(itemView);
            problem_name=itemView.findViewById(R.id.name_problem);
            show_date= itemView.findViewById(R.id.date_shower);
            goin_complete= itemView.findViewById(R.id.go_complete);
            more_option=itemView.findViewById(R.id.more_option);
            toggle=itemView.findViewById(R.id.toggle);
            more_option.setOnClickListener(this);
            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        goin_complete.setText("completed");
                    }
                    else{
                        goin_complete.setText("going on");
                    }
                }
            });

        }

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
        }

        private void showEditDialog(int position) {
            View editView = LayoutInflater.from(context).inflate(R.layout.doctor_input, null);
            EditText userName = editView.findViewById(R.id.editIssue);
            EditText userNum = editView.findViewById(R.id.date);

            new AlertDialog.Builder(context)
                    .setView(editView)
                    .setPositiveButton("OK", (dialog, which) -> {
                        datamodelDoctorIssue userData = dataList.get(position);
                        userData.setUsername(userName.getText().toString());
                        userData.setNumber(userNum.getText().toString());
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
        }
    }

}
