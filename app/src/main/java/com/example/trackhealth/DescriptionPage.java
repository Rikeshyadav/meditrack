package com.example.trackhealth;
// firstpage.java

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class DescriptionPage extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        adapter.addFragment(new StartFragment1(), ".");
        adapter.addFragment(new StartFragment2(), ".");
        adapter.addFragment(new StartFragment3(), ".");
        adapter.addFragment(new StartFragment4(), ".");
        viewPager.setAdapter(adapter);


        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight((int) getResources().getDimension(R.dimen.height));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(viewPager.getCurrentItem()==3) {
                    StartFragment4 fragment4 = (StartFragment4) adapter.getItem(3);

                    // Access the Button from StartFragment4
                    Button fragmentButton = fragment4.getFragmentButton();

                    // Perform any actions with the Button
                    slideButtonUp(fragmentButton);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing on reselection
            }
        });
    }
    private void slideButtonUp(Button button) {

        int size=button.getHeight();
        button.setTranslationY(size+130);
        button.animate()
                .translationY(36)
                .setDuration(900)
                .start();
    }

}
