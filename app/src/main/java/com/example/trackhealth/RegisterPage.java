package com.example.trackhealth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterPage extends AppCompatActivity {
    Spinner dopcat;
    int select=1;
    ImageView img;

    ScrollView scroll;

    RadioGroup radiogroup_gender, radiogroup_work;

    RadioButton male_radio, female_radio, others_radio, clinic_radio, hospital_radio;

    TextView spec_text, yoe_text, about_text, qualification_text, text_clinicphone, text_clinic_header, text_clinic_name, text_clinic_type, text_clinic_address, country_code2;

    TextView additional_det,verification_text,phone_text,doc_reg,pass_warn,docpat_warn,upload_warn;

    EditText speciality, yoe, aboutDoc, qualification, organisation, clinic_type, clinic_address, clinic_phone, dob,phone_no,pass;

    LinearLayout l1;

    AppCompatButton signup,otp_but,verify_but;

    CardView card2,card3,card4,card5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



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
        doc_reg=findViewById(R.id.reg_document_header);
        verification_text=findViewById(R.id.reg_verification_header);
        additional_det=findViewById(R.id.edu_detail);
        phone_text=findViewById(R.id.phone_for_verification);
        pass_warn=findViewById(R.id.pass_warn);
        docpat_warn=findViewById(R.id.docpat_warn);



        //id of scrollView

        scroll=findViewById(R.id.scroll);

        // id of edit text

        clinic_phone = findViewById(R.id.clinic_phone);
        dopcat = findViewById(R.id.docpat);
        clinic_type = findViewById(R.id.clinic_type);
        clinic_address = findViewById(R.id.clinic_address);
        speciality = findViewById(R.id.speciality);
        yoe = findViewById(R.id.year_of_experience);
        aboutDoc = findViewById(R.id.aboutdoctor);
        qualification = findViewById(R.id.qualification);
        organisation = findViewById(R.id.organisation);
        phone_no=findViewById(R.id.phoneNo);
        pass=findViewById(R.id.pass);



        //image view
        img=findViewById(R.id.image1);

        // id of button
        signup = findViewById(R.id.signup);
        otp_but=findViewById(R.id.get_otp_but);
        verify_but=findViewById(R.id.verify_but_reg);
        upload_warn=findViewById(R.id.upload_doc);


        //layout
        l1=findViewById(R.id.reg_otp);


        //radio button and group
        radiogroup_work = findViewById(R.id.work);
        hospital_radio = findViewById(R.id.hospital_radio);
        clinic_radio = findViewById(R.id.clinic_radio);



        //date of birth
        dob = findViewById(R.id.Age);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });





        //id of cardView
        card2=findViewById(R.id.card2);
        card3=findViewById(R.id.card3);
        card4=findViewById(R.id.card4);
        card5=findViewById(R.id.card5);



        //select

        String[] st = {"Select", "Doctor", "patient"};
        RegisterSpinnerApdater adapter = new RegisterSpinnerApdater(this, R.layout.spinner1, st);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dopcat.setAdapter(adapter);

        dopcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedOption = (String) adapterView.getItemAtPosition(i);

                if (selectedOption.equals("Doctor")) {
                    if(!phone_no.getText().toString().equals(""))
                        phone_text.setText(phone_no.getText().toString());
                    select=1;
                    docpat_warn.setVisibility(View.GONE);
                    card5.setVisibility(View.VISIBLE);
                    card2.setVisibility(View.VISIBLE);
                    card3.setVisibility(View.GONE);
                    card4.setVisibility(View.VISIBLE);
                    upload_warn.setVisibility(View.GONE);
                    additional_det.setVisibility(View.VISIBLE);
                    verification_text.setVisibility(View.VISIBLE);
                    qualification_text.setVisibility(View.VISIBLE);
                    text_clinic_header.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.GONE);
                    hospital_radio.setVisibility(View.VISIBLE);
                    clinic_radio.setVisibility(View.VISIBLE);
                    otp_but.setVisibility(View.VISIBLE);
                    verify_but.setVisibility(View.GONE);
                    doc_reg.setVisibility(View.VISIBLE);

                }
                if (selectedOption.equals("patient")) {
                    select=1;
                    card5.setVisibility(View.GONE);
                    docpat_warn.setVisibility(View.GONE);
                    otp_but.setText("GET OTP");
                    card2.setVisibility(View.GONE);
                    card3.setVisibility(View.GONE);
                    card4.setVisibility(View.VISIBLE);
                    additional_det.setVisibility(View.GONE);
                    verification_text.setVisibility(View.GONE);
                    // qualification_text.setVisibility(View.GONE);
                    upload_warn.setVisibility(View.GONE);
                    text_clinic_header.setVisibility(View.GONE);
                    l1.setVisibility(View.GONE);
                    otp_but.setVisibility(View.VISIBLE);
                    verify_but.setVisibility(View.GONE);
                    doc_reg.setVisibility(View.GONE);
                    hospital_radio.setVisibility(View.GONE);
                    clinic_radio.setVisibility(View.GONE);

                }
                if (selectedOption.equals("Select")) {
                    card5.setVisibility(View.GONE);
                    select=0;
                    card2.setVisibility(View.GONE);
                    upload_warn.setVisibility(View.GONE);
                    otp_but.setText("GET OTP");
                    card3.setVisibility(View.GONE);
                    card4.setVisibility(View.GONE);
                    additional_det.setVisibility(View.GONE);
                    verification_text.setVisibility(View.GONE);
                    //  qualification_text.setVisibility(View.GONE);
                    text_clinic_header.setVisibility(View.GONE);
                    l1.setVisibility(View.GONE);
                    otp_but.setVisibility(View.GONE);
                    verify_but.setVisibility(View.GONE);
                    doc_reg.setVisibility(View.GONE);
                    hospital_radio.setVisibility(View.GONE);
                    clinic_radio.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                select=0;

            }
        });


        // radio button

                hospital_radio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        text_clinic_address.setText("Hospital Address");
                        text_clinic_name.setText("Hospital Name");
                        card3.setVisibility(View.VISIBLE);
                        text_clinic_type.setText("Hospital Type");

                        text_clinicphone.setText("Hospital Phone Number");

                        clinic_address.setHint("Eg. Greater Noida, sector 45");
                        organisation.setHint("Eg. Apollo Hospital");
                        clinic_type.setHint("Eg. General Hospital");
                        clinic_phone.setHint("Hospital Contact Number");

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

                clinic_address.setHint("Eg. Greater Noida, sector 45");
                organisation.setHint("Eg. Ravi health clinic");
                clinic_type.setHint("Eg. Cardiac Clinic");
                clinic_phone.setHint("Clinic Contact Number");



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





        otp_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!phone_no.getText().toString().equals("") && phone_no.getText().toString().length()==10) {
                    phone_text.setText("+91"+phone_no.getText().toString());
                    l1.setVisibility(View.VISIBLE);
                    verify_but.setVisibility(View.VISIBLE);
                    otp_but.setText("Resend OTP");

                }
                else if(!phone_no.getText().toString().equals("") && phone_no.getText().toString().length()!=10){
                    Toast.makeText(getApplicationContext(),"invalid phone no.",Toast.LENGTH_SHORT).show();
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                }
                else{
                    Toast.makeText(getApplicationContext(),"empty phone no.",Toast.LENGTH_SHORT).show();
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                }
            }
        });        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });



// document image
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pass.getText().toString().length()<8 ){
                    pass_warn.setText("** must be atleast 8 characters long");
                    pass_warn.setVisibility(View.VISIBLE);
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                }

                if(pass.getText().toString().length()>=8 ) {
                    if (!isValid(pass.getText().toString())) {
                        pass_warn.setText("** must include atleast one Upper case,one Lowercase and one number character");
                        scroll.fullScroll(ScrollView.FOCUS_UP);
                        pass_warn.setVisibility(View.VISIBLE);
                    }
                    else {
                        pass_warn.setVisibility(View.GONE);
                        if(select==1) {
                            docpat_warn.setVisibility(View.GONE);
                            Intent i=new Intent(getApplicationContext(), Register_process.class);
                            startActivity(i);

                            Toast.makeText(getApplicationContext(), "Registration under process", Toast.LENGTH_LONG).show();
                        }
                        else {
                            docpat_warn.setText("** kindly select one option");
                            docpat_warn.setTextSize(13);
                            docpat_warn.setVisibility(View.VISIBLE);

                        }
                    }
                }



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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // The result is for picking a PDF file

            ContentResolver contentResolver = getApplicationContext().getContentResolver();
            String type = contentResolver.getType(data.getData());
            if (type != null) {
                if (type.startsWith("image")) {
                    upload_warn.setVisibility(View.GONE);
                    img.setImageURI(data.getData());
                } else if (type.startsWith("video")) {
                    img.setImageResource(R.drawable.add_photo);
                    upload_warn.setText("** kindly select image file");
                    upload_warn.setVisibility(View.VISIBLE);

                }
        }
    }}


    public boolean isValid(String str){
        int u=0,l=0,n=0;
        for(int i=0;i<str.length();i++){
            if(Character.isUpperCase(str.charAt(i))) u=1;
            if(Character.isLowerCase(str.charAt(i))) l=1;
            if(Character.isDigit(str.charAt(i))) n=1;
            if((u+l+n)==3) return true;
        }
        return false;

    }
}

