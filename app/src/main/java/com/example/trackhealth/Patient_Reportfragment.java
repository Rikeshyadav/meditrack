package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Patient_Reportfragment extends Fragment {
DatabaseReference databaseReference;
    List<List> uploderpdf=new ArrayList<>();
    LottieAnimationView pb;
    PdfReportAdapter adapter;
RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_patient__reportfragment, container, false);
recyclerView=view.findViewById(R.id.pdfreportrec);
pb=view.findViewById(R.id.report_searchlottie);
String key="";
        SharedPreferences sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sp.getString("identity","").equals("Patient")) {
            key=sp.getString("curphone2","")+sp.getString("phone","");
        }
else{
    key=sp.getString("phone","")+sp.getString("curphone2","");
        }
        retrievePDFiles(key);

        return view;
    }

    private void retrievePDFiles(String key){
        databaseReference= FirebaseDatabase.getInstance().getReference("Report").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    List<String> inner=new ArrayList<>();
                    inner.add(ds.child("filename").getValue().toString());
                    inner.add(ds.child("fileurl").getValue(String.class).toString());
                    try {
                        inner.add(ds.child("datee").getValue(String.class).toString());
                    }
                    catch (Exception e){
                        inner.add("");
                    }
                    uploderpdf.add(inner);

                }
                adapter=new PdfReportAdapter(getContext(),uploderpdf);
                LinearLayoutManager  l = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(l);
                pb.setVisibility(View.GONE);
                recyclerView.setAdapter(adapter);
if(uploderpdf.size()==0){
    Toast.makeText(getContext(),"no report available",Toast.LENGTH_SHORT).show();
}

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}