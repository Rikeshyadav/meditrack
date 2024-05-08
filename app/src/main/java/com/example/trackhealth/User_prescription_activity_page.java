package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class User_prescription_activity_page extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    DoctorPrescription_viewpager adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_prescription_page);
        tabLayout=findViewById(R.id.Patinet_tablayout2);
        viewPager2=findViewById(R.id.Patient_pager2);
        tabLayout.addTab(tabLayout.newTab().setText("PRESCRIPTION"));
        tabLayout.addTab(tabLayout.newTab().setText("MEDICINE"));
        tabLayout.addTab(tabLayout.newTab().setText("ANALYSIS"));
        tabLayout.addTab(tabLayout.newTab().setText("REPORT"));
        adapter=new DoctorPrescription_viewpager(this,getIntent().getStringExtra("issueid"));
        viewPager2.setAdapter(adapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));

            }
        });
    }
}