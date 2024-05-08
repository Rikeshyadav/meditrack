package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class user_report_homepage extends AppCompatActivity {
  TabLayout tabLayout;
  ViewPager2 viewPager2;
  ViewPage_PatientAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report_homepage);
         tabLayout=findViewById(R.id.Patinet_tablayout);
         viewPager2=findViewById(R.id.Patient_pager);
        tabLayout.addTab(tabLayout.newTab().setText("DOCTOR"));
        tabLayout.addTab(tabLayout.newTab().setText("Problem"));
        tabLayout.addTab(tabLayout.newTab().setText("CHAT"));
         adapter1=new ViewPage_PatientAdapter(this);
         viewPager2.setAdapter(adapter1);
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