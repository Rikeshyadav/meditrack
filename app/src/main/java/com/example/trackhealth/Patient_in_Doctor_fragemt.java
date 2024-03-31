package com.example.trackhealth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;


public class Patient_in_Doctor_fragemt extends Fragment {
  ShapeableImageView shapeableImageView;
  TextView  name,gender,Age,bgroup,emaild,locaton;

    public Patient_in_Doctor_fragemt() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patient_in__doctor_fragemt, container, false);
        shapeableImageView=view.findViewById(R.id.PAteint_photo);
        name=view.findViewById(R.id.PAteint_name1);
        gender=view.findViewById(R.id.PAtient_gender);
        Age=view.findViewById(R.id.PAteint_age);
        bgroup=view.findViewById(R.id.PAteint_blood);
        emaild=view.findViewById(R.id.emaildetails);
        locaton=view.findViewById(R.id.patient_location);
        return view;
    }
}