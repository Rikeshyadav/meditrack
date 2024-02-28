package com.example.trackhealth;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager_search_adapter extends FragmentStateAdapter {

    Context context;
    public ViewPager_search_adapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.context=fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new doctor_search_viewpager(context);
            case 1: return new hospital_search_viewpager();
            case 2: return new clinic_search_viewpager();
            default:return new doctor_search_viewpager(context);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
