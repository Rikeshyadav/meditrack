package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class Patient_ListRecyview_inDoctor extends AppCompatActivity {
     TabLayout doctor_tablayout;
     ViewPager2 doctor_view;
     ViewPage_DoctorAdapter adapterdoctor;
     TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list_recyview_in_doctor);
        doctor_tablayout=findViewById(R.id.Doctor_tablayout);
        doctor_view=findViewById(R.id.Doctorview_pager);
        doctor_tablayout.addTab(doctor_tablayout.newTab().setText("PATIENT"));
        doctor_tablayout.addTab(doctor_tablayout.newTab().setText("PROBLEM"));
        title=findViewById(R.id.doctor_tabtoptxt);
        SharedPreferences sp=getSharedPreferences("user",MODE_PRIVATE);
        if(sp.getString("identity","").equals("Patient")){

            title.setText(sp.getString("curclinic_name",""));
        }
        else{
            title.setText(sp.getString("clinic_name",""));
        }
        doctor_tablayout.addTab(doctor_tablayout.newTab().setText("CHAT"));
        adapterdoctor=new ViewPage_DoctorAdapter(this);
        doctor_view.setAdapter(adapterdoctor);
        doctor_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                doctor_view.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        doctor_view.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                doctor_tablayout.selectTab(doctor_tablayout.getTabAt(position));

            }
        });
    }
}