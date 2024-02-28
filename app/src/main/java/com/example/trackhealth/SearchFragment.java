package com.example.trackhealth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.google.android.material.tabs.TabLayout;


public class SearchFragment extends Fragment {

ViewPager2 viewPager2;
Spinner spinner;
TabLayout tabLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_search, container, false);

viewPager2=view.findViewById(R.id.viewpager_search);
tabLayout=view.findViewById(R.id.tabLayout_search);
ViewPager_search_adapter v=new ViewPager_search_adapter(getActivity());
viewPager2.setAdapter(v);
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
        tabLayout.getTabAt(position).select();
    }
});


        return view;
    }
}