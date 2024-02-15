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

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.checkerframework.checker.units.qual.Length;

public class HomePage_Patient extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
 DrawerLayout drawerLayout;
 NavigationView navigationView;

 FragmentManager fragmentManager;
 BottomNavigationView bottomNavigationView;
 Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_patient);
        navigationView=findViewById(R.id.navigation_drawer2);
        bottomNavigationView=findViewById(R.id.bottom_navigation1);
        toolbar=findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout=findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
         drawerLayout.addDrawerListener(toggle);
         toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }

        bottomNavigationView=findViewById(R.id.bottom_navigation1);
      //  bottomNavigationView.setBackground(null);
        fragmentManager =getSupportFragmentManager();
        openFragment(new Patient_HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId=item.getItemId();
                if(itemId==R.id.bottom_home){
                    toolbar.setTitle("Patient HOME");
                    openFragment(new Patient_HomeFragment());
                }
                else if (itemId==R.id.bottom_schedule){
                    toolbar.setTitle(("scheduled"));
                openFragment((new patient_scheduledFragment()));
                } else if (itemId==R.id.bottom_search) {
                    toolbar.setTitle("Search");
                    Toast.makeText(getApplicationContext(),"search", Toast.LENGTH_SHORT).show();
                }else if (itemId==R.id.bottom_call) {
                    toolbar.setTitle("call");
                    Toast.makeText(getApplicationContext(),"call", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        ShapeableImageView navHeaderImageView=navigationView.getHeaderView(0).findViewById(R.id.navHeaderImageView);
        navHeaderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showEnlargedImageDialog();
            }
        });

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {//navigation drawer

        int itemId= item.getItemId();
        if(itemId==R.id.patient_nav_editprofile){
            toolbar.setTitle("Profile");
            openFragment(new patient_edit_profile());
        }
        else if (itemId==R.id.patient_nav_notify) {
            toolbar.setTitle("Notification");
            openFragment(new patient_nav_notification());
            Toast.makeText(getApplicationContext(),"Notification", Toast.LENGTH_SHORT).show();

        }else if (itemId==R.id.patient_nav_setting) {
            toolbar.setTitle("Setting");
            Toast.makeText(getApplicationContext(),"setting", Toast.LENGTH_SHORT).show();
        }else if (itemId==R.id.patient_nav_about) {
            toolbar.setTitle("about us");
        }
       drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer((GravityCompat.START));
        }
        super.onBackPressed();
    }
    private void openFragment(Fragment fragment){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.patient_container, fragment);
        transaction.commit();

    }

    private void showEnlargedImageDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
       builder.setTitle("Image");
        // imageview in dialog layout
        ShapeableImageView enlargedImageView=  new ShapeableImageView(this);
        enlargedImageView.setImageResource(R.drawable.img_1);
        //enlargedImageView.setImageResource(imageResourceId);  ,to take updated photo from user
        builder.setView(enlargedImageView);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
       AlertDialog alertDialog=builder.create();
       alertDialog.show();
    }
}