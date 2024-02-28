package com.example.trackhealth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class clinic_search_viewpager extends Fragment {
    
    Spinner spinner;
    List<List> arr=new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_clinic_search_viewpager, container, false);
        String[] parameter={"Name","Specialization","Qualification","State","City"};
        RegisterSpinnerApdater paradapter=new RegisterSpinnerApdater(getContext(),R.layout.spinner1,parameter);
        spinner.setAdapter(paradapter);
        recyclerView = view.findViewById(R.id.clinic_search_rec);
        List<String> arr2=new ArrayList<>();
        arr2.add("");
        arr2.add("Ravi Denatl Clinic");
        arr2.add("Guwahati,Assam");
        arr2.add("Govt");
        arr2.add("+916000505880");
        arr.add(arr2);
        arr.add(arr2);
        hosclinic_search_adapter hc= new hosclinic_search_adapter(arr);
        recyclerView.setAdapter(hc);
        recyclerView.setHasFixedSize(true);
        return view;
    }
}