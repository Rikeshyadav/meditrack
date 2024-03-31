package com.example.trackhealth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Patient_Testfragment extends Fragment {
    TextView textView;
    TextView textView1;
    RecyclerView recyclerView;
    List<datamodel2> data;
    public Patient_Testfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_patient__testfragment, container, false);
      // textView=view.findViewById(R.id.mariz);
       // textView1=view.findViewById(R.id.issu1);
        recyclerView=view.findViewById(R.id.prescr_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        data=new ArrayList<>();//initialized
        datamodel2 ob1=new datamodel2("Cancer","2.45pm","23-jun-2024","on going");
        data.add(ob1);
        recyclerView.setAdapter(new pateint_prescriptionAdapter(data));

        return view;
    }
}