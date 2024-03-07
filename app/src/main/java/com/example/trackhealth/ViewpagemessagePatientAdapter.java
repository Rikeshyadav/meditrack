package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewpagemessagePatientAdapter extends FragmentStateAdapter {
    public ViewpagemessagePatientAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
            if(position==0){
                return new Doctor_in_PatientFragment();
            } else if (position==1) {
                return new Patient_Testfragment();
            }
            else {
                return new Doctor_in_PatientFragment();
            }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
