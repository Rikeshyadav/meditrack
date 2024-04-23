package com.example.trackhealth;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DoctorProblemFragment extends Fragment {
    Parentrecycle_Adapter parentrecycle_adapter;
    RecyclerView parentRecycle;
    FloatingActionButton floatButton;
    List<datamodelDoctorIssue> datalist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_doctor_problem, container, false);

            parentRecycle=view.findViewById(R.id.parentrecycleview);
            datalist=new ArrayList<>();
            parentrecycle_adapter=new Parentrecycle_Adapter(getActivity(),datalist);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
            parentRecycle.setLayoutManager(linearLayoutManager);
            parentRecycle.setAdapter(parentrecycle_adapter);
            floatButton=view.findViewById(R.id.Parentfloatbutton);

            floatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater=LayoutInflater.from(getActivity());
                    AlertDialog.Builder addDialog=new AlertDialog.Builder(getActivity());
                    final android.view.View dview = inflater.inflate(R.layout.doctor_input, null);

                    EditText userNAme=dview.findViewById(R.id.editIssue);
                    EditText userNo=dview.findViewById(R.id.date);
                    userNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar calendar=Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {

                                    userNo.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                                }
                            }, year, month, day);
                            datePickerDialog.show();
                        }

                    });


                    addDialog.setView(dview);
                    addDialog.setPositiveButton("OK",(dialog, which) ->{
                        String names=userNAme.getText().toString();
                        String number=userNo.getText().toString();
                        datalist.add(new datamodelDoctorIssue(names, number));
                        parentrecycle_adapter.notifyItemInserted(datalist.size());
                        parentrecycle_adapter.notifyItemInserted(datalist.size());
                        Toast.makeText(getActivity(),"Issue Added",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    });
                    addDialog.setNegativeButton("cancel",(dialog, which) -> {
                        dialog.dismiss();
                        Toast.makeText(getActivity(),"cancel",Toast.LENGTH_SHORT).show();
                    });
                    addDialog.create().show();
                }

            });




    return view;
    }
}