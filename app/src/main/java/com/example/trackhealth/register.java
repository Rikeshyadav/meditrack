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
    TextView spec_text,yoe_text,about_text,qualification_text,text_clinicphone,text_clinic_header,text_clinic_name,text_clinic_type,text_clinic_address;
    EditText speciality,yoe,aboutDoc,qualification,organisation,clinic_type,clinic_address,clinic_phone;

    View line1;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String[] st={"Select","Doctor","patient"};
        int textColor = getResources().getColor(R.color.black);

        RegisterSpinnerApdater adapter=new RegisterSpinnerApdater(this, R.layout.spinner1,st);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //id of text view
        text_clinicphone=findViewById(R.id.text_clinicphone);
        qualification_text=findViewById(R.id.text_qualification);
        text_clinic_header=findViewById(R.id.clinic_info);
        text_clinic_name=findViewById(R.id.text_clinicname);
        text_clinic_address=findViewById(R.id.text_clinicaddress);
        text_clinic_type=findViewById(R.id.text_clinictype);
        yoe_text=findViewById(R.id.text_yoe);
        spec_text=findViewById(R.id.text_speciality);
        about_text=findViewById(R.id.text_about);

        // id of line

        line1=findViewById(R.id.line);
        // id of edit text
        clinic_phone=findViewById(R.id.clinic_phone);
        dopcat=findViewById(R.id.docpat);
        dopcat.setAdapter(adapter);
        clinic_type=findViewById(R.id.clinic_type);
        clinic_address=findViewById(R.id.clinic_address);
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

                if(selectedOption.equals("Doctor")) {
                    speciality.setVisibility(View.VISIBLE);
                    qualification.setVisibility(View.VISIBLE);
                    yoe_text.setVisibility(View.VISIBLE);
                    spec_text.setVisibility(View.VISIBLE);
                    about_text.setVisibility(View.VISIBLE);
                    aboutDoc.setVisibility(View.VISIBLE);
                    text_clinicphone.setVisibility(View.VISIBLE);
                    clinic_phone.setVisibility(View.VISIBLE);
                    clinic_type.setVisibility(View.VISIBLE);
                    clinic_address.setVisibility(View.VISIBLE);
                    qualification_text.setVisibility(View.VISIBLE);
                    line1.setVisibility(View.VISIBLE);
                    text_clinic_address.setVisibility(View.VISIBLE);
                    text_clinic_header.setVisibility(View.VISIBLE);
                    text_clinic_name.setVisibility(View.VISIBLE);
                    text_clinic_type.setVisibility(View.VISIBLE);
                    organisation.setVisibility(View.VISIBLE);
                    yoe.setVisibility(View.VISIBLE);
                }
                if(selectedOption.equals("patient")) {
                    speciality.setVisibility(View.GONE);
                    aboutDoc.setVisibility(View.GONE);
                    about_text.setVisibility(View.GONE);
                    yoe_text.setVisibility(View.GONE);
                    line1.setVisibility(View.GONE);
                    clinic_type.setVisibility(View.GONE);
                    clinic_address.setVisibility(View.GONE);
                    text_clinicphone.setVisibility(View.GONE);
                    qualification_text.setVisibility(View.GONE);
                    text_clinic_address.setVisibility(View.GONE);
                    clinic_phone.setVisibility(View.GONE);
                    text_clinic_header.setVisibility(View.GONE);
                    text_clinic_name.setVisibility(View.GONE);
                    text_clinic_type.setVisibility(View.GONE);

                    spec_text.setVisibility(View.GONE);
                    organisation.setVisibility(View.GONE);
                    qualification.setVisibility(View.GONE);
                    yoe.setVisibility(View.GONE);
                }
                if(selectedOption.equals("Select")) {
                    speciality.setVisibility(View.GONE);
                    aboutDoc.setVisibility(View.GONE);
                    yoe_text.setVisibility(View.GONE);
                    clinic_type.setVisibility(View.GONE);
                    clinic_phone.setVisibility(View.GONE);
                    line1.setVisibility(View.GONE);
                    clinic_address.setVisibility(View.GONE);
                    qualification_text.setVisibility(View.GONE);
                    text_clinic_address.setVisibility(View.GONE);
                    text_clinic_header.setVisibility(View.GONE);
                    text_clinic_name.setVisibility(View.GONE);
                    text_clinic_type.setVisibility(View.GONE);
                    text_clinicphone.setVisibility(View.GONE);
                    about_text.setVisibility(View.GONE);
                    spec_text.setVisibility(View.GONE);
                    qualification.setVisibility(View.GONE);
                    organisation.setVisibility(View.GONE);
                    yoe.setVisibility(View.GONE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }
}