package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPage_DoctorAdapter extends FragmentStateAdapter {


    public ViewPage_DoctorAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return new Patient_in_Doctor_fragemt();
        } else if (position==1) {
            return new DoctorProblemFragment();
        }else if(position==2){
            return new Patient_chat_fragment();
        }
        else {
            return new Patient_in_Doctor_fragemt();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
