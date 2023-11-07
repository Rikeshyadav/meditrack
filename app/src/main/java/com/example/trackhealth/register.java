package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity {
Spinner dopcat;
EditText speciality,yoe,aboutDoc,qualification,organisation;
Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String[] st={"Select","You are a Doctor","You are a patient"};
        int textColor = getResources().getColor(R.color.black);

        RegisterSpinnerApdater adapter=new RegisterSpinnerApdater(this, R.layout.spinner1,st);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // id of edit text

        dopcat=findViewById(R.id.docpat);
        dopcat.setAdapter(adapter);
        speciality=findViewById(R.id.speciality);
        yoe=findViewById(R.id.year_of_experience);
        aboutDoc=findViewById(R.id.aboutdoctor);
        qualification=findViewById(R.id.qualification);
        organisation=findViewById(R.id.organisation);
        // id of button
               signup=findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"chl project khtm .. so ja",Toast.LENGTH_LONG).show();
            }
        });

        dopcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedOption = (String) adapterView.getItemAtPosition(i);

               if(selectedOption.equals("You are a Doctor")) {
                   speciality.setVisibility(View.VISIBLE);
                   qualification.setVisibility(View.VISIBLE);
                   aboutDoc.setVisibility(View.VISIBLE);
                   organisation.setVisibility(View.VISIBLE);
                   yoe.setVisibility(View.VISIBLE);
               }
                if(selectedOption.equals("You are a patient")) {
                    speciality.setVisibility(View.GONE);
                    aboutDoc.setVisibility(View.GONE);
                    organisation.setVisibility(View.GONE);
                    qualification.setVisibility(View.GONE);
                    yoe.setVisibility(View.GONE);
                }
                if(selectedOption.equals("Select")) {
                    speciality.setVisibility(View.GONE);
                    aboutDoc.setVisibility(View.GONE);
                    qualification.setVisibility(View.GONE);
                    yoe.setVisibility(View.GONE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
}