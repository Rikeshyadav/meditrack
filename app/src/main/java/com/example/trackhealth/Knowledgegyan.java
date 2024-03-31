package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Knowledgegyan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_knowledgegyan);
        String page=getIntent().getStringExtra("page");
        if(page.equals("Exercise")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_knowledgeGyan, new Exercise())// Add to back stack to allow popping
                    .commit();
        }

        else if(page.equals("diet")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_knowledgeGyan, new Knowledge_diet_fragment())// Add to back stack to allow popping
                    .commit();
        }

        else if(page.equals("medicine")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_knowledgeGyan, new Knowledge_medicine_fragment())// Add to back stack to allow popping
                    .commit();
        }
    }


}