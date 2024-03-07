package com.example.trackhealth;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Patient_HomeFragment extends Fragment {
    RecyclerView recyclerView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    List<datamodel>data;
    public Patient_HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_patient__home, container, false);
        ((AppCompatActivity) getActivity()).setSupportActionBar(null);
        recyclerView=view.findViewById(R.id.patient_recyclview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        data=new ArrayList<>();//initialized
        datamodel ob1=new datamodel(R.drawable.doctorimage_2,"DR. rikesh","heart","trims");
        data.add(ob1);
        datamodel ob2=new datamodel(R.drawable.img,"DR. Jugal","eye","nerist");
        data.add(ob2);
        datamodel ob3=new datamodel(R.drawable.logo,"DR. min","nose","Aiims");
        data.add(ob3);
        datamodel ob4=new datamodel(R.drawable.img_1,"DR. fun","nose","Aiims");
        data.add(ob4);
        datamodel ob5=new datamodel(R.drawable.img_1,"DR. richo","nose","Aiims");
        data.add(ob5);
        // datamodel ob4=new datamodel();
        //   ob4.setDp(R.drawable.img);    not able to take due to the way we define in datamodel
        int spacingInPixels= 2;
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.left=spacingInPixels*6;
                outRect.right=spacingInPixels*6;
                outRect.top=spacingInPixels;
                outRect.bottom=spacingInPixels;
            }
        });
        recyclerView.setAdapter(new patient_homeAdapter(data));
        toolbar = view.findViewById(R.id.customtool);
        //((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
       // ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // ((AppCompatActivity) requireActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.back); // Set your hamburger icon

        drawerLayout = requireActivity().findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
         toggle.syncState();

        return view;
    }
}