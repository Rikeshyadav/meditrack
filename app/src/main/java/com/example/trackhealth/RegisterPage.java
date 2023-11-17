package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterPage extends AppCompatActivity {
    Spinner dopcat;

    LinearLayout hosOrClinic;
    RadioGroup radiogroup_gender, radiogroup_work;
    RadioButton male_radio, female_radio, others_radio, clinic_radio, hospital_radio;
    TextView spec_text, yoe_text, about_text, qualification_text, text_clinicphone, text_clinic_header, text_clinic_name, text_clinic_type, text_clinic_address, country_code2;
    EditText speciality, yoe, aboutDoc, qualification, organisation, clinic_type, clinic_address, clinic_phone, dob;
    AppCompatButton signup;

    CardView card2,card3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        String[] st = {"Select", "Doctor", "patient"};
        int textColor = getResources().getColor(R.color.black);

        RegisterSpinnerApdater adapter = new RegisterSpinnerApdater(this, R.layout.spinner1, st);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //id of text view
        text_clinicphone = findViewById(R.id.text_clinicphone);
        qualification_text = findViewById(R.id.text_qualification);
        text_clinic_header = findViewById(R.id.clinic_info);
        text_clinic_name = findViewById(R.id.text_clinicname);
        text_clinic_address = findViewById(R.id.text_clinicaddress);
        text_clinic_type = findViewById(R.id.text_clinictype);
        yoe_text = findViewById(R.id.text_yoe);
        spec_text = findViewById(R.id.text_speciality);
        about_text = findViewById(R.id.text_about);
        country_code2 = findViewById(R.id.country_code2);

        // id of line

        // id of edit text
        clinic_phone = findViewById(R.id.clinic_phone);
        dopcat = findViewById(R.id.docpat);
        dopcat.setAdapter(adapter);
        clinic_type = findViewById(R.id.clinic_type);
        clinic_address = findViewById(R.id.clinic_address);
        speciality = findViewById(R.id.speciality);
        yoe = findViewById(R.id.year_of_experience);
        aboutDoc = findViewById(R.id.aboutdoctor);
        qualification = findViewById(R.id.qualification);
        organisation = findViewById(R.id.organisation);
        // id of button
        signup = findViewById(R.id.signup);


        //radio button and group

        radiogroup_work = findViewById(R.id.work);
        hospital_radio = findViewById(R.id.hospital_radio);
        clinic_radio = findViewById(R.id.clinic_radio);


        //layout

        hosOrClinic = findViewById(R.id.hos_or_clinic);


        //date of birth

        dob = findViewById(R.id.Age);


        //id of cardView

        card2=findViewById(R.id.card2);
        card3=findViewById(R.id.card3);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });


                hospital_radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        text_clinic_address.setText("Hospital Address");
                        text_clinic_name.setText("Hospital Name");
                        card3.setVisibility(View.VISIBLE);
                        text_clinic_type.setText("Hospital Type");
                        text_clinicphone.setText("Hospital Phone Number");
                        text_clinic_address.setVisibility(View.VISIBLE);
                        text_clinic_name.setVisibility(View.VISIBLE);
                        text_clinic_type.setVisibility(View.VISIBLE);
                        text_clinicphone.setVisibility(View.VISIBLE);
                        country_code2.setVisibility(View.VISIBLE);
                        organisation.setVisibility(View.VISIBLE);
                        clinic_type.setVisibility(View.VISIBLE);
                        clinic_address.setVisibility(View.VISIBLE);
                        clinic_phone.setVisibility(View.VISIBLE);

                    }
                });


        clinic_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_clinic_address.setText("Clinic Address");
                text_clinic_name.setText("Clinic Name");
                text_clinic_type.setText("Clinic Type");
                text_clinicphone.setText("Clinic Phone Number");
                text_clinic_address.setVisibility(View.VISIBLE);
                text_clinic_name.setVisibility(View.VISIBLE);
                text_clinic_type.setVisibility(View.VISIBLE);
                card3.setVisibility(View.VISIBLE);
                text_clinicphone.setVisibility(View.VISIBLE);
                organisation.setVisibility(View.VISIBLE);
                country_code2.setVisibility(View.VISIBLE);
                clinic_type.setVisibility(View.VISIBLE);
                clinic_address.setVisibility(View.VISIBLE);
                clinic_phone.setVisibility(View.VISIBLE);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "chl project khtm .. so ja", Toast.LENGTH_LONG).show();
            }
        });

        dopcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedOption = (String) adapterView.getItemAtPosition(i);

                if (selectedOption.equals("Doctor")) {
                    speciality.setVisibility(View.VISIBLE);
                    qualification.setVisibility(View.VISIBLE);
                    yoe_text.setVisibility(View.VISIBLE);
                    spec_text.setVisibility(View.VISIBLE);
                    about_text.setVisibility(View.VISIBLE);
                    aboutDoc.setVisibility(View.VISIBLE);
                    qualification_text.setVisibility(View.VISIBLE);
                    text_clinic_header.setVisibility(View.VISIBLE);
                    yoe.setVisibility(View.VISIBLE);
                    card2.setVisibility(View.VISIBLE);
                    card3.setVisibility(View.GONE);
                    country_code2.setVisibility(View.GONE);
                    text_clinic_address.setVisibility(View.GONE);
                    text_clinic_name.setVisibility(View.GONE);
                    text_clinic_type.setVisibility(View.GONE);
                    hospital_radio.setVisibility(View.VISIBLE);
                    clinic_radio.setVisibility(View.VISIBLE);
                    text_clinicphone.setVisibility(View.GONE);
                    organisation.setVisibility(View.GONE);
                    clinic_type.setVisibility(View.GONE);
                    clinic_address.setVisibility(View.GONE);
                    clinic_phone.setVisibility(View.GONE);

                    hosOrClinic.setVisibility(View.VISIBLE);

                }
                if (selectedOption.equals("patient")) {
                    speciality.setVisibility(View.GONE);
                    aboutDoc.setVisibility(View.GONE);
                    about_text.setVisibility(View.GONE);
                    yoe_text.setVisibility(View.GONE);
                    text_clinicphone.setVisibility(View.GONE);
                    qualification_text.setVisibility(View.GONE);
                    text_clinic_header.setVisibility(View.GONE);
                    card2.setVisibility(View.GONE);
                    card3.setVisibility(View.GONE);
                    hospital_radio.setVisibility(View.GONE);
                    clinic_radio.setVisibility(View.GONE);
                    country_code2.setVisibility(View.GONE);
                    spec_text.setVisibility(View.GONE);
                    qualification.setVisibility(View.GONE);
                    yoe.setVisibility(View.GONE);

                    hosOrClinic.setVisibility(View.GONE);

                    text_clinic_address.setVisibility(View.GONE);
                    text_clinic_name.setVisibility(View.GONE);
                    text_clinic_type.setVisibility(View.GONE);
                    text_clinicphone.setVisibility(View.GONE);
                    organisation.setVisibility(View.GONE);
                    clinic_type.setVisibility(View.GONE);
                    clinic_address.setVisibility(View.GONE);
                    clinic_phone.setVisibility(View.GONE);

                    radiogroup_work.clearCheck();
                }
                if (selectedOption.equals("Select")) {
                    speciality.setVisibility(View.GONE);
                    aboutDoc.setVisibility(View.GONE);
                    yoe_text.setVisibility(View.GONE);
                    qualification_text.setVisibility(View.GONE);
                    text_clinic_header.setVisibility(View.GONE);
                    about_text.setVisibility(View.GONE);
                    spec_text.setVisibility(View.GONE);
                    qualification.setVisibility(View.GONE);
                    yoe.setVisibility(View.GONE);
                    card2.setVisibility(View.GONE);
                    card3.setVisibility(View.GONE);
                    hosOrClinic.setVisibility(View.GONE);
                    hospital_radio.setVisibility(View.GONE);
                    clinic_radio.setVisibility(View.GONE);
                    country_code2.setVisibility(View.GONE);
                    text_clinic_address.setVisibility(View.GONE);
                    text_clinic_name.setVisibility(View.GONE);
                    text_clinic_type.setVisibility(View.GONE);
                    text_clinicphone.setVisibility(View.GONE);
                    organisation.setVisibility(View.GONE);
                    clinic_type.setVisibility(View.GONE);
                    clinic_address.setVisibility(View.GONE);
                    clinic_phone.setVisibility(View.GONE);
                    radiogroup_work.clearCheck();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String formattedDate = dateFormat.format(selectedDate.getTime());

                        dob.setText(formattedDate);
                    }
                },
                currentYear,
                currentMonth,
                currentDay
        );

        datePickerDialog.show();
    }

}