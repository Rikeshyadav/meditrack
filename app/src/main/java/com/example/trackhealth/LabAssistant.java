package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class LabAssistant extends AppCompatActivity {
boolean back=false;
Toolbar toolbar;
SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_assistant);
        sp=getSharedPreferences("user",MODE_PRIVATE);
toolbar=findViewById(R.id.toolbar_assistant);

setSupportActionBar(toolbar);
    }

    @Override
    public void onBackPressed() {

       if(back){
            finishAffinity();
        }
        else {

               back=true;
               Toast.makeText(LabAssistant.this,"Press one more time to exit",Toast.LENGTH_SHORT).show();


        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        back=false;
    }

    public void setAlert(String msg,String pos,String neg,String flag){

        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setMessage(msg);
        b.setPositiveButton(pos,(DialogInterface.OnClickListener) (dialog, which)->{
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.assitant_logout){
            setAlert("Do you want to logout?","yes","no","logout");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assistant_menu, menu);
        return true;
    }
}