package com.example.trackhealth;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class patient_scheduledFragment extends Fragment {
 Toolbar toolbar;
 DrawerLayout drawerLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patient_scheduled, container, false);
        toolbar = view.findViewById(R.id.customtool2);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        // ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // ((AppCompatActivity) requireActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.back); // Set your hamburger icon
toolbar.setTitle("");
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    return view;
    }
}