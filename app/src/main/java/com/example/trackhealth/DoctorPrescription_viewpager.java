package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DoctorPrescription_viewpager extends FragmentStateAdapter {

    String issueid;
    public DoctorPrescription_viewpager(@NonNull FragmentActivity fragmentActivity, String issueid) {
        super(fragmentActivity);
        this.issueid=issueid;

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
         if(position==0) {
            return new prescriptionParent(issueid);
        } else if (position==1) {
            return new patient_medicinefragment();
        }else
            return new Patient_Testfragment();
        }


    @Override
    public int getItemCount() {
        return 3;
    }
}
