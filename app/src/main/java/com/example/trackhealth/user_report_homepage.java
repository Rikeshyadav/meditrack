package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class user_report_homepage extends AppCompatActivity {
  TabLayout tabLayout;
  ViewPager2 viewPager2;
  ImageView back;
  TextView title;
  ViewPage_PatientAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report_homepage);
         tabLayout=findViewById(R.id.Patinet_tablayout);
         title=findViewById(R.id.patient_tabtoptxt);
         back=findViewById(R.id.innerpres1_back);
         viewPager2=findViewById(R.id.Patient_pager);
        tabLayout.addTab(tabLayout.newTab().setText("DOCTOR"));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("Problem"));
        tabLayout.addTab(tabLayout.newTab().setText("CHAT"));
        tabLayout.addTab(tabLayout.newTab().setText("Report"));
         adapter1=new ViewPage_PatientAdapter(this);
         viewPager2.setAdapter(adapter1);
        SharedPreferences sp=getSharedPreferences("user",MODE_PRIVATE);
        if(sp.getString("identity","").equals("Patient")){

            title.setText(sp.getString("curclinic_name",""));
        }
        else{
            title.setText(sp.getString("clinic_name",""));
        }
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
    public void onBackPressed() {
        super.onBackPressed();
    /*    Intent i=new Intent(user_report_homepage.this,HomePage_Patient.class);
        startActivity(i);*/
    }
}