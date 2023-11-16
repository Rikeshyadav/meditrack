package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class HomePage extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
   DrawerLayout drawerLayout;
   String username;
   BottomNavigationView bottomNavigationView;
   NavigationView nav;
   FragmentManager fragmentManager;
   Toolbar toolbar;
   FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //intent data

        username = getIntent().getStringExtra("username");


        nav = findViewById(R.id.navigation_drawer);

        // Find the header view
        View headerView = nav.getHeaderView(0);

        // Find the TextView inside the header view
        TextView headerText = headerView.findViewById(R.id.user1);

        // Set the text of the TextView
        headerText.setText(username);

        //initialise all use item to prevent from null pointer error
        fab=findViewById(R.id.fab);
        toolbar=findViewById(R.id.toolbar);
        //don't have action bar so we set custom toolbar as a action bar
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView=findViewById(R.id.navigation_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.bottom_home){
                    openFragment(new HomeFragment());
                    return true;
                }
                else if (itemId==R.id.bottom_schedule){
                    openFragment(new ScheduledFragment());
                }
               else if(itemId==R.id.bottom_search){
                openFragment(new SearchFragment());
                }
                  else if(itemId==R.id.bottom_call){
                    openFragment(new CallFragment());
                }
              /*  else if(itemId==R.id.bottom_setting){
                    openFragment(new SettingFragment());

                }
*/
                return true;
            }
        });

    //fragmentManager =getSupportFragmentManager();
    //openFragment(new HomeFragment());
    bottomNavigationView.setSelectedItemId(R.id.bottom_home);
    fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           Toast.makeText(HomePage.this,"add person",Toast.LENGTH_SHORT).show();
            Intent fab=new Intent(HomePage.this, AddPatientPage.class);
            startActivity(fab);
        }
    });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();
        if (itemId==R.id.nav_editprofile){
            openFragment(new EditprofileFragment());
        }
        else if (itemId==R.id.nav_notification) {
            openFragment(new NotificationFragment());}
        else if(itemId==R.id.nav_privacy){
            openFragment(new privacyFragment());
        }else if(itemId==R.id.nav_trash){
            openFragment(new TrashFragment());}
        else if (itemId==R.id.nav_setting) {
            openFragment(new SettingFragment());
        }else if (itemId==R.id.nav_logout) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
        }else if (itemId==R.id.nav_aboutus) {
            openFragment(new aboutUsFragment());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();
    }
    }
    private void openFragment(Fragment fragment){// help to load the fragment and also help us from writing and again and again code
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.commit();
    }
}