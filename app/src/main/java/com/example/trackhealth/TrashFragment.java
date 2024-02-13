package com.example.trackhealth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TrashFragment extends Fragment {
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_pending, container, false);
        List<String> innerList1 = new ArrayList<>();
        innerList1.add("Cardiologist Clinic");
        innerList1.add("Dr. Rikesh Yadav");
        innerList1.add("Cardiologist");
        innerList1.add("MBBS");
        innerList1.add("Heart Attack");

        List<String> innerList2 = new ArrayList<>();
        innerList2.add("Eye Clinic");
        innerList2.add("Dr. Mrinal Thakur");
        innerList2.add("Eye Doctor");
        innerList2.add("MBBS");
        innerList2.add("Power increased of left eye");

        List<List> innerList3 = new ArrayList<>();

        innerList3.add(innerList1);
        innerList3.add(innerList2);
        recyclerView = (RecyclerView) view.findViewById(R.id.pending_rec);
        pendingAdapter adapter = new pendingAdapter(innerList3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }
}