package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
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

import java.util.List;

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

        username = getIntent().getStringExtra("username");

        fab=findViewById(R.id.fab);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView=findViewById(R.id.navigation_drawer);
        View headerView = navigationView.getHeaderView(0);
        TextView headerText = headerView.findViewById(R.id.user1);
        headerText.setText(username);

        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setBackground(null);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.bottom_home){
                    openFragment(new HomeFragment());
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

    fragmentManager =getSupportFragmentManager();
    openFragment(new HomeFragment());
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
            Intent i=new Intent(this, LoginActivity.class);
            startActivity(i);
            Toast.makeText(this, "logging out", Toast.LENGTH_SHORT).show();
        }else if (itemId==R.id.nav_aboutus) {
            openFragment(new aboutUsFragment());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
    if(this.getSupportFragmentManager().getBackStackEntryCount()==1){

        setAlert("Do you want to exit?","yes","no");
    }
    else {
        super.onBackPressed();
    }
    }
    }



    public void setAlert(String msg,String pos,String neg){
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage(msg);
        b.setPositiveButton(pos,(DialogInterface.OnClickListener) (dialog,which)->{
            finishAffinity();
        });
        b.setNegativeButton(neg,(DialogInterface.OnClickListener) (dialog,which)->{
            dialog.cancel();
        });
        AlertDialog ad=b.create();
        ad.show();

    }
    private void openFragment(Fragment fragment){
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment).addToBackStack("tag");
        transaction.commit();
    }
}