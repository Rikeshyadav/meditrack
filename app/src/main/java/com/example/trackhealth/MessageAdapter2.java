package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MessageAdapter2 extends FragmentStateAdapter {
    public MessageAdapter2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
         if(position==0) {
            return new Patient_Analysisfragment();
        } else if (position==1) {
            return new patient_medicinefragment();
        } else if (position==2) {
            return new Patient_chat_fragment();
        }else {
            return new Patient_Analysisfragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
