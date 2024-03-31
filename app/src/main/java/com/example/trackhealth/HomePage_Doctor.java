package com.example.trackhealth;

import static androidx.constraintlayout.motion.widget.Debug.getName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomePage_Doctor extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
   DrawerLayout drawerLayout;
   SharedPreferences sp,boot;
   ImageView profile_photo;
    NavigationView navigationView;
   String username;
    boolean back=false;
   BottomNavigationView bottomNavigationView;
   NavigationView nav;
   FragmentManager fragmentManager;
    TextView headerText;
   Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_doctor);
        sp=getSharedPreferences("user",MODE_PRIVATE);
        boot=getSharedPreferences("boot",MODE_PRIVATE);
        username = sp.getString("name","user");

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView=findViewById(R.id.navigation_drawer);
        View headerView = navigationView.getHeaderView(0);
        headerText = headerView.findViewById(R.id.user1);
        headerText.setText("Dr. "+sp.getString("name","user"));
         profile_photo=headerView.findViewById(R.id.pphoto);
        setPhoto();
         profile_photo.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 openFragment(new EditprofileFragment(),"profile");
                 setPhoto();
                 drawerLayout.closeDrawer(GravityCompat.START);
             }
         });
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView=findViewById(R.id.bottom_navigation);
        //use due to transparent in bottomnavigation

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         unchecknav();

                int itemId=item.getItemId();
                if(itemId==R.id.bottom_home){
                    toolbar.setTitle("Home");
                    openFragment(new HomeFragment(),"home");
               
                }
                else if (itemId==R.id.bottom_schedule){
                    toolbar.setTitle("Schedule");
                    openFragment(new ScheduledFragment(),"schedule");
                }
               else if(itemId==R.id.bottom_search){
                    toolbar.setTitle("Search");
                openFragment(new SearchFragment(),"search");

                }
                  else if(itemId==R.id.bottom_call){
                    toolbar.setTitle("Call");
                    openFragment(new CallFragment(),"call");

                }
              /*  else if(itemId==R.id.bottom_setting){
                    openFragment(new SettingFragment());

                }
*/
                return true;
            }
        });

    fragmentManager =getSupportFragmentManager();
    openFragment(new HomeFragment(),"home");


    }

    public void setPhoto(){
        String photo= sp.getString("photo","");
        if(!photo.equals("")) {
            Bitmap b = getbitmap(photo);
            profile_photo.setImageBitmap(b);
        }
        else{
            profile_photo.setImageResource(R.drawable.baseline_person_24);
        }
    }
    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(false);

        int itemId=item.getItemId();
        if (itemId==R.id.nav_editprofile){
            openFragment(new EditprofileFragment(),"profile");
            toolbar.setTitle("Profile");
        }
       else if (itemId==R.id.nav_notification) {
            toolbar.setTitle("Notification");
            openFragment(new NotificationFragment(),"notification");}
        else if(itemId==R.id.nav_privacy){
            openFragment(new privacyFragment(),"privacy");
        }
        else if (itemId==R.id.nav_setting) {
            openFragment(new SettingFragment(),"setting");
            toolbar.setTitle("Setting");
        }else if (itemId==R.id.nav_logout) {
           setAlert("Do you want to logout?","yes","no","logout");
           unchecknav();
            openFragment(new HomeFragment(),"home");
        }else if (itemId==R.id.nav_aboutus) {
            toolbar.setTitle("About");
            openFragment(new aboutUsFragment(),"about");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void unchecknav(){
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(false);
        navigationView.getMenu().getItem(3).setChecked(false);
        navigationView.getMenu().getItem(4).setChecked(false);
        navigationView.getMenu().getItem(5).setChecked(false);
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else if(this.getSupportFragmentManager().getBackStackEntryCount()==1 && !back){
            back=true;
            Toast.makeText(getApplicationContext(),"Press back again to exit",Toast.LENGTH_SHORT).show();

        }
        else if(this.getSupportFragmentManager().getBackStackEntryCount()==1 && back){

            finishAffinity();
        }
    else {
   super.onBackPressed();
    }
    }



    // Assuming you have a method to replace a fragment
    public void openFragment(Fragment fragment,String tag) {
        back=false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean fragmentInStack = isFragmentInBackStack(fragment,tag);

        if (fragmentInStack) {
            popBackStackUntilFragment(fragment,tag);
        } else {

        String curr="";
        try{
            curr=getSupportFragmentManager().getBackStackEntryAt(fragmentManager.getBackStackEntryCount()-1).getName();
            if(!tag.equals(curr)){
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(tag) // Add to back stack to allow popping
                        .commit();
            }
        }
        catch(NullPointerException e){

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .addToBackStack(tag) // Add to back stack to allow popping
                        .commit();
        }

        }
    }

    private boolean isFragmentInBackStack(Fragment fragment,String tag) {
        // Check if the fragment is in the back stack
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount()-1; i++) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            if (tag.equals(entry.getName())) {

                return true;
            }
        }
        return false;
    }

    private void popBackStackUntilFragment(Fragment fragment,String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
            if (Objects.equals(entry.getName(), tag)) {
                break;
            } else {
                fragmentManager.popBackStackImmediate();
            }
        }
    }

    public void setAlert(String msg,String pos,String neg,String flag){

        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage(msg);
        b.setPositiveButton(pos,(DialogInterface.OnClickListener) (dialog,which)->{
            if(flag.equals("exit"))
            finishAffinity();
            else if(flag.equals("logout")){
                Intent i=new Intent(this, LoginActivity.class);
                sp.edit().putBoolean("islogged",false).apply();
                startActivity(i);
                Toast.makeText(this, "logging out", Toast.LENGTH_SHORT).show();
            }

        });
        b.setNegativeButton(neg,(DialogInterface.OnClickListener) (dialog,which)->{

            dialog.cancel();
        });
        AlertDialog ad=b.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                ad.getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.black));
            }
        });

        ad.show();

    }


    @Override
    protected void onResume() {
        super.onResume();
        back=false;
        headerText.setText("Dr."+sp.getString("name","user"));
    }
}