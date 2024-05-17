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

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomePage_Patient extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView headerText;
    FragmentManager fragmentManager;
    LinearLayout home_search;
    SharedPreferences sp, boot;
    ShapeableImageView navHeaderImageView;
    boolean back = false;
    BottomNavigationView bottomNavigationView;

    //Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("user", MODE_PRIVATE);
        boot = getSharedPreferences("boot", MODE_PRIVATE);
        setContentView(R.layout.activity_home_page_patient);
        navigationView = findViewById(R.id.navigation_drawer2);
        bottomNavigationView = findViewById(R.id.bottom_navigation1);
        unchecknav();
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        drawerLayout = findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation1);
        //  bottomNavigationView.setBackground(null);
        fragmentManager = getSupportFragmentManager();
        openFragment(new Patient_HomeFragment(), "home");
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.bottom_home) {
                    //toolbar.setTitle("Home");
                    //     home_search.setVisibility(View.VISIBLE);
                    //toolbar.setPadding(5,15,5,15);
                    //toolbar.setBackgroundColor(getColor(R.color.patient_home_toolbar));
                    openFragment(new Patient_HomeFragment(), "home");
                } else if (itemId == R.id.bottom_schedule) {
                    //toolbar.setTitle(("scheduled"));

                    // toolbar.setBackgroundColor(getColor(R.color.red));
                    openFragment((new patient_scheduledFragment()), "schedule");
                } else if (itemId == R.id.bottom_search) {
                    openFragment(new SearchFragment(), "search");

                } else if (itemId == R.id.bottom_profile) {

                    openFragment(new patient_edit_profile(),"profile");
                    Toast.makeText(getApplicationContext(), "call", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        navHeaderImageView = navigationView.getHeaderView(0).findViewById(R.id.patient_navHeaderImageView);

        View headerView = navigationView.getHeaderView(0);
        headerText = headerView.findViewById(R.id.patient_navusername);
        headerText.setText(sp.getString("name", "user"));
        setPhoto(navHeaderImageView);
        navHeaderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEnlargedImageDialog();
            }
        });

    }


    public void setPhoto(ShapeableImageView imageView) {
        String photo = sp.getString("photo", "");
        if (!photo.equals("")) {
            Bitmap b = getbitmap(photo);
            imageView.setImageBitmap(b);
        } else {
            imageView.setImageResource(R.drawable.baseline_person_24);
        }
    }

    public Bitmap getbitmap(String s) {
        byte[] bytes = Base64.decode(s, Base64.DEFAULT);
        Bitmap bitmap2 = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap2;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {//navigation drawer

        int itemId = item.getItemId();
        if (itemId == R.id.patient_nav_editprofile) {
            //toolbar.setTitle("Profile");
            openFragment(new patient_edit_profile(), "profile");
        }/* else if (itemId == R.id.patient_nav_notify) {
            // toolbar.setTitle("Notification");
            openFragment(new patient_nav_notification(), "notification");
            Toast.makeText(getApplicationContext(), "Notification", Toast.LENGTH_SHORT).show();

        }*/ else if (itemId == R.id.nav_trash) {
            openFragment(new Panding_Fragment(), "trash");
            //toolbar.setTitle("Pending");
        } else if (itemId == R.id.patient_nav_setting) {
            openFragment(new SettingFragment(), "setting");
        } else if (itemId == R.id.patient_nav_about) {
            openFragment(new aboutUsFragment(),"about");
        } else if (itemId == R.id.patient_nav_logout) {
            setAlert("Do you want to logout?", "yes", "no", "logout");
            unchecknav();
           // openFragment(new Patient_HomeFragment(), "logout");
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer((GravityCompat.START));
        }


        setPhoto(navHeaderImageView);
        headerText.setText(sp.getString("name", "user"));
        super.onBackPressed();
    }

    public void openFragment(Fragment fragment, String tag) {
        back = false;
        FragmentManager fragmentManager = getSupportFragmentManager();
        boolean fragmentInStack = isFragmentInBackStack(fragment, tag);

        if (fragmentInStack) {
            popBackStackUntilFragment(fragment, tag);
        } else {

            String curr = "";
            try {
                curr = getSupportFragmentManager().getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
                if (!tag.equals(curr)) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.patient_container, fragment)
                            .addToBackStack(tag) // Add to back stack to allow popping
                            .commit();
                }
            } catch (NullPointerException e) {

                fragmentManager.beginTransaction()
                        .replace(R.id.patient_container, new Patient_HomeFragment())
                        .addToBackStack(tag) // Add to back stack to allow popping
                        .commit();
            }

        }
    }

    private boolean isFragmentInBackStack(Fragment fragment, String tag) {
        // Check if the fragment is in the back stack
        FragmentManager fragmentManager = getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount() - 1; i++) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            if (tag.equals(entry.getName())) {

                return true;
            }
        }
        return false;
    }

    private void popBackStackUntilFragment(Fragment fragment, String tag) {
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


    private void showEnlargedImageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Image");
        // imageview in dialog layout
        ShapeableImageView enlargedImageView = new ShapeableImageView(this);
        enlargedImageView.setMinimumWidth(490);
        enlargedImageView.setMinimumHeight(550);
        String photo = sp.getString("photo", "");
        if (!photo.equals("")) {
            Bitmap b = getbitmap(photo);
            enlargedImageView.setImageBitmap(b);
        } else {
            enlargedImageView.setImageResource(R.drawable.baseline_person_24);
        }

        //enlargedImageView.setImageResource(imageResourceId);  ,to take updated photo from user
        builder.setView(enlargedImageView);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void unchecknav() {
        navigationView.getMenu().getItem(0).setChecked(false);
        navigationView.getMenu().getItem(1).setChecked(false);
        navigationView.getMenu().getItem(2).setChecked(false);
        navigationView.getMenu().getItem(3).setChecked(false);
        navigationView.getMenu().getItem(4).setChecked(false);
        navigationView.getMenu().getItem(5).setChecked(false);
    }

    public void setAlert(String msg, String pos, String neg, String flag) {

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setMessage(msg);
        b.setPositiveButton(pos, (DialogInterface.OnClickListener) (dialog, which) -> {
            if (flag.equals("exit"))
                finishAffinity();
            else if (flag.equals("logout")) {
                Intent i = new Intent(HomePage_Patient.this, LoginActivity.class);
                sp.edit().putBoolean("islogged", false).apply();
                startActivity(i);
                Toast.makeText(this, "logging out", Toast.LENGTH_SHORT).show();
            }

        });
        b.setNegativeButton(neg, (DialogInterface.OnClickListener) (dialog, which) -> {

            dialog.cancel();
        });
        AlertDialog ad = b.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                ad.getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.black));
            }
        });

        ad.show();

    }

    protected void onResume() {
        super.onResume();

       setPhoto(navHeaderImageView);
        headerText.setText(sp.getString("name", "user"));
    }


}