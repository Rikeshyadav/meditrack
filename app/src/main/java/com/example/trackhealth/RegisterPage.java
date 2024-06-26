
package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RegisterPage extends AppCompatActivity {
    Spinner dopcat;
    int select = 1;
    FirebaseAuth auth;
    ImageView img,sign;
    String phh="",verificationId="";
    SharedPreferences sp;
    ProgressBar progress,progress2;
    String photoid_="",photosign_="";
    String verified_phone="";
    String otp="";
    boolean verified=false ,existUser=false;


    ScrollView scroll;

    RadioGroup radiogroup_gender, radiogroup_work;

    RadioButton male_radio, female_radio, others_radio, clinic_radio, hospital_radio;

    TextView spec_text, yoe_text, about_text, qualification_text, text_clinicphone, text_clinic_header, text_clinic_name, text_clinic_type, text_clinic_state,text_clinic_city, country_code2;

    TextView additional_det, verification_text, phone_text, doc_reg, pass_warn, docpat_warn, upload_warn,upload_warn2;

    EditText speciality, yoe, aboutDoc, qualification, organisation, clinic_type, clinic_phone, dob, phone_no, pass;
    EditText password, firstname, lastname, emailid, otpedit;

    AutoCompleteTextView city,state,clinic_state,clinic_city;

    AppCompatButton signup, otp_but, verify_but;

    CardView card2, card3, card4, card5,card6;
    String doctorOrPatient = "null", gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progress=findViewById(R.id.progressregister);
        progress2=findViewById(R.id.progressregister2);

        //id of text view
        text_clinicphone = findViewById(R.id.text_clinicphone);
        qualification_text = findViewById(R.id.text_qualification);
        text_clinic_header = findViewById(R.id.clinic_info);
        text_clinic_name = findViewById(R.id.text_clinicname);
        text_clinic_state = findViewById(R.id.text_clinicstate);
        sp=getSharedPreferences("user",MODE_PRIVATE);
        text_clinic_city=findViewById(R.id.text_cliniccity);
        text_clinic_type = findViewById(R.id.text_clinictype);
        radiogroup_gender=findViewById(R.id.gender);
        radiogroup_gender.clearCheck();
        yoe_text = findViewById(R.id.text_yoe);
        spec_text = findViewById(R.id.text_speciality);
        about_text = findViewById(R.id.text_about);
        country_code2 = findViewById(R.id.country_code2);
        doc_reg = findViewById(R.id.reg_document_header);
        verification_text = findViewById(R.id.reg_verification_header);
        additional_det = findViewById(R.id.edu_detail);
        phone_text = findViewById(R.id.phone_for_verification);
        pass_warn = findViewById(R.id.pass_warn);
        docpat_warn = findViewById(R.id.docpat_warn);
        auth= FirebaseAuth.getInstance();

        //id of scrollView

        scroll = findViewById(R.id.scroll);

        // id of edit text

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        emailid = findViewById(R.id.email);
        clinic_phone = findViewById(R.id.clinic_phone);
        dopcat = findViewById(R.id.docpat);
        clinic_type = findViewById(R.id.clinic_type);
        clinic_state = findViewById(R.id.clinic_state);
        clinic_city = findViewById(R.id.clinic_city);
        speciality = findViewById(R.id.speciality);
        yoe = findViewById(R.id.year_of_experience);
        aboutDoc = findViewById(R.id.aboutdoctor);
        qualification = findViewById(R.id.qualification);
        organisation = findViewById(R.id.organisation);
        phone_no = findViewById(R.id.phoneNo);
        password = findViewById(R.id.pass);
        otpedit = findViewById(R.id.reg_otp);

        state=findViewById(R.id.state);
        city=findViewById(R.id.city);

        //image view
        img = findViewById(R.id.image1);

        // id of button
        signup = findViewById(R.id.signup);
        otp_but = findViewById(R.id.get_otp_but);
        verify_but = findViewById(R.id.verify_but_reg);
        upload_warn = findViewById(R.id.upload_doc);
        upload_warn2 = findViewById(R.id.upload_signdoc);


        //radio button and group
        radiogroup_work = findViewById(R.id.work);
        hospital_radio = findViewById(R.id.hospital_radio);
        clinic_radio = findViewById(R.id.clinic_radio);
        male_radio = findViewById(R.id.male);
        female_radio = findViewById(R.id.female);
        others_radio = findViewById(R.id.others);



        String[] cities={"Guwahati","Patna","Delhi","Mumbai","Kolkata","Kerela","Chennai","Raipur","Panaji","Bangalore","Itanagar","Bhubneshwar","Hyderabad","Nirjuli","Noida","Bhopal","Indore","Sikunderabad"};
        String[] states={"Assam","UP","Bihar","Andhra Pradesh","Uttrakhand","West Bengal","Meghalaya","Sikkim","Punjab","Haryana","Madhya Pradesh","Maharashtra","Gujrat","J&K","Himachal Pradesh","Tamil Nadu",
                "Jharkhand","Odisha","Rajasthan","Goa","Arunachal Pradesh","Karnataka"};

        RegisterSpinnerApdater stateAdapter=new RegisterSpinnerApdater(this,R.layout.spinner_login,states);
        RegisterSpinnerApdater cityAdapter=new RegisterSpinnerApdater(this,R.layout.spinner_login,cities);
        city.setAdapter(cityAdapter);
        clinic_city.setAdapter(cityAdapter);
        state.setAdapter(stateAdapter);
        clinic_state.setAdapter(stateAdapter);

        //date of birth
        dob = findViewById(R.id.Age);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });


        //id of cardView
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        card4 = findViewById(R.id.card4);
        card5 = findViewById(R.id.card5);
        card6 = findViewById(R.id.card6);


        //gender

        male_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Male";
            }
        });


        female_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Female";
            }
        });


        others_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = "Other";
            }
        });

        //select
        otpedit.setVisibility(View.GONE);
        String[] st = {"Select", "Doctor", "patient","pathologist"};
        RegisterSpinnerApdater adapter = new RegisterSpinnerApdater(this, R.layout.spinner_login, st);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dopcat.setAdapter(adapter);


        dopcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedOption = (String) adapterView.getItemAtPosition(i);
                if (selectedOption.equals("Select")) {
                    card5.setVisibility(View.GONE);
                    card6.setVisibility(View.GONE);
                    select = 0;
                    doctorOrPatient = "null";
                    card2.setVisibility(View.GONE);
                    upload_warn.setVisibility(View.GONE);
                    otp_but.setText("GET OTP");
                    card3.setVisibility(View.GONE);

                    card4.setVisibility(View.GONE);
                    additional_det.setVisibility(View.GONE);
                    verification_text.setVisibility(View.GONE);
                    text_clinic_header.setVisibility(View.GONE);
                    otp_but.setVisibility(View.GONE);
                    verify_but.setVisibility(View.GONE);
                    doc_reg.setVisibility(View.GONE);
                    hospital_radio.setVisibility(View.GONE);
                    clinic_radio.setVisibility(View.GONE);
                }else{
                    if ((firstname.getText().toString().trim() + lastname.getText().toString().trim()).equals("")) {
                        scroll.fullScroll(scroll.FOCUS_UP);
                        dopcat.setSelection(0);
                        Toast.makeText(getApplicationContext(), "empty username", Toast.LENGTH_SHORT).show();
                    } else if (emailid.getText().toString().trim().equals("")) {
                        scroll.fullScroll(scroll.FOCUS_UP);
                        dopcat.setSelection(0);
                        Toast.makeText(getApplicationContext(), "empty email", Toast.LENGTH_SHORT).show();
                    } else if (dob.getText().toString().trim().equals("")) {
                        scroll.fullScroll(scroll.FOCUS_UP);
                        dopcat.setSelection(0);
                        Toast.makeText(getApplicationContext(), "empty dob", Toast.LENGTH_SHORT).show();
                    } else if (gender.equals("")) {
                        scroll.fullScroll(scroll.FOCUS_UP);
                        dopcat.setSelection(0);
                        Toast.makeText(getApplicationContext(), "empty gender", Toast.LENGTH_SHORT).show();
                    } else if (state.getText().toString().trim().equals("")) {
                        scroll.fullScroll(scroll.FOCUS_UP);
                        dopcat.setSelection(0);
                        Toast.makeText(getApplicationContext(), "empty state", Toast.LENGTH_SHORT).show();
                    }
                    else if (city.getText().toString().trim().equals("")) {
                        scroll.fullScroll(scroll.FOCUS_UP);
                        dopcat.setSelection(0);
                        Toast.makeText(getApplicationContext(), "empty city", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (selectedOption.equals("Doctor")) {

                            select = 1;
                            doctorOrPatient = "Doctor";
                            docpat_warn.setVisibility(View.GONE);
                            card5.setVisibility(View.VISIBLE);
                            card6.setVisibility(View.VISIBLE);
                            card2.setVisibility(View.VISIBLE);
                            card3.setVisibility(View.GONE);
                            if (!verified) {
                                otp_but.setText("Get OTP");
                                otp_but.setFocusable(true);
                            } else {
                                otp_but.setText("verified");
                                otp_but.setFocusable(false);
                            }
                            card4.setVisibility(View.VISIBLE);
                            upload_warn.setVisibility(View.GONE);
                            additional_det.setVisibility(View.VISIBLE);
                            verification_text.setVisibility(View.VISIBLE);
                            qualification_text.setVisibility(View.VISIBLE);
                            text_clinic_header.setVisibility(View.VISIBLE);

                            hospital_radio.setVisibility(View.VISIBLE);
                            clinic_radio.setVisibility(View.VISIBLE);
                            otp_but.setVisibility(View.VISIBLE);
                            verify_but.setVisibility(View.GONE);
                            doc_reg.setVisibility(View.VISIBLE);


                        }
                        if (selectedOption.equals("patient")) {
                            select = 1;

                            doctorOrPatient = "Patient";
                            card5.setVisibility(View.GONE);
                            card6.setVisibility(View.GONE);
                            docpat_warn.setVisibility(View.GONE);
                            otp_but.setText("GET OTP");
                            card2.setVisibility(View.GONE);
                            card3.setVisibility(View.GONE);
                            card4.setVisibility(View.VISIBLE);
                            additional_det.setVisibility(View.GONE);
                            verification_text.setVisibility(View.GONE);
                            upload_warn.setVisibility(View.GONE);
                            text_clinic_header.setVisibility(View.GONE);
                            otp_but.setVisibility(View.VISIBLE);
                            verify_but.setVisibility(View.GONE);
                            if (!verified) {
                                otp_but.setText("Get OTP");
                                otp_but.setFocusable(true);
                            } else {
                                otp_but.setText("verified");
                                otp_but.setFocusable(false);
                            }
                            doc_reg.setVisibility(View.GONE);
                            hospital_radio.setVisibility(View.GONE);
                            clinic_radio.setVisibility(View.GONE);


                        }if (selectedOption.equals("pathologist")) {
                            select = 1;

                            doctorOrPatient = "Lab Assistant";
                            card5.setVisibility(View.GONE);
                            card6.setVisibility(View.GONE);
                            docpat_warn.setVisibility(View.GONE);
                            otp_but.setText("GET OTP");
                            card2.setVisibility(View.GONE);
                            card3.setVisibility(View.GONE);
                            card4.setVisibility(View.VISIBLE);
                            additional_det.setVisibility(View.GONE);
                            verification_text.setVisibility(View.GONE);
                            upload_warn.setVisibility(View.GONE);
                            text_clinic_header.setVisibility(View.GONE);
                            otp_but.setVisibility(View.VISIBLE);
                            verify_but.setVisibility(View.GONE);
                            if (!verified) {
                                otp_but.setText("Get OTP");
                                otp_but.setFocusable(true);
                            } else {
                                otp_but.setText("verified");
                                otp_but.setFocusable(false);
                            }
                            doc_reg.setVisibility(View.GONE);
                            hospital_radio.setVisibility(View.GONE);
                            clinic_radio.setVisibility(View.GONE);


                        }

                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                select = 0;

            }
        });


        // radio button

        hospital_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_clinic_state.setText("Hospital State");
                text_clinic_city.setText("Hospital City");
                text_clinic_name.setText("Hospital Name");
                card3.setVisibility(View.VISIBLE);
                text_clinic_type.setText("Hospital Type");
                text_clinicphone.setText("Hospital Phone Number");

                clinic_state.setHint("Eg. UP");
                clinic_city.setHint("Eg. Delhi");
                organisation.setHint("Eg. Apollo Hospital");
                clinic_type.setHint("Eg. General Hospital");
                clinic_phone.setHint("Hospital Contact Number");

                text_clinic_city.setVisibility(View.VISIBLE);
                text_clinic_state.setVisibility(View.VISIBLE);
                text_clinic_name.setVisibility(View.VISIBLE);
                text_clinic_type.setVisibility(View.VISIBLE);
                text_clinicphone.setVisibility(View.VISIBLE);
                country_code2.setVisibility(View.VISIBLE);
                organisation.setVisibility(View.VISIBLE);
                clinic_type.setVisibility(View.VISIBLE);
                clinic_city.setVisibility(View.VISIBLE);
                clinic_state.setVisibility(View.VISIBLE);
                clinic_phone.setVisibility(View.VISIBLE);

            }
        });


        clinic_radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_clinic_state.setText("Clinic State");
                text_clinic_city.setText("Clinic City");
                text_clinic_name.setText("Clinic Name");
                text_clinic_type.setText("Clinic Type");
                text_clinicphone.setText("Clinic Phone Number");
                clinic_state.setHint("Eg. UP");
                clinic_city.setHint("Eg. Delhi");

                organisation.setHint("Eg. Ravi health clinic");
                clinic_type.setHint("Eg. Cardiac Clinic");
                clinic_phone.setHint("Clinic Contact Number");


                text_clinic_city.setVisibility(View.VISIBLE);
                text_clinic_state.setVisibility(View.VISIBLE);
                text_clinic_name.setVisibility(View.VISIBLE);
                text_clinic_type.setVisibility(View.VISIBLE);
                card3.setVisibility(View.VISIBLE);
                text_clinicphone.setVisibility(View.VISIBLE);
                organisation.setVisibility(View.VISIBLE);
                country_code2.setVisibility(View.VISIBLE);
                clinic_type.setVisibility(View.VISIBLE);
                clinic_city.setVisibility(View.VISIBLE);
                clinic_state.setVisibility(View.VISIBLE);
                clinic_phone.setVisibility(View.VISIBLE);
            }
        });

        verify_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String verotp=otpedit.getText().toString().trim();
                if(!verotp.equals("")){

                    signin(verotp);
                }
                else{
                    Toast.makeText(RegisterPage.this, "empty otp", Toast.LENGTH_SHORT).show();
                }

            }
        });

        otp_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!phone_no.getText().toString().equals("") && phone_no.getText().toString().length() == 10) {
                    phone_text.setText("+91" + phone_no.getText().toString());

                    // verify_but.setVisibility(View.VISIBLE);
                    if(!verified) {
                        progress.setVisibility(View.VISIBLE);
                        // sendmsg(phone_no.getText().toString().trim());
                        getotpFirebase(phone_no.getText().toString().trim());


                    }
                    else Toast.makeText(getApplicationContext(), "already verified", Toast.LENGTH_SHORT).show();

                } else if (!phone_no.getText().toString().equals("") && phone_no.getText().toString().length() != 10) {
                    Toast.makeText(getApplicationContext(), "invalid phone no.", Toast.LENGTH_SHORT).show();
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                } else {
                    Toast.makeText(getApplicationContext(), "empty phone no.", Toast.LENGTH_SHORT).show();
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                }

                //
            }
        });

        sign=findViewById(R.id.imagesign);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               startImageCapture(1);
            }
        });


// document image
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImageCapture(2);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                progress2.setVisibility(View.VISIBLE);
                if (password.getText().toString().length() < 8) {
                    pass_warn.setText("** must be atleast 8 characters long");
                    pass_warn.setVisibility(View.VISIBLE);
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                    progress2.setVisibility(View.GONE);
                }

                if (password.getText().toString().length() >= 8) {

                    if (!isValid(password.getText().toString())) {

                        pass_warn.setText("** must include atleast one Upper case,one Lowercase and one number character");
                        scroll.fullScroll(ScrollView.FOCUS_UP);
                        pass_warn.setVisibility(View.VISIBLE);
                        progress2.setVisibility(View.GONE);
                    } else {

                        pass_warn.setVisibility(View.GONE);
                        if (select == 1) {

                            docpat_warn.setVisibility(View.GONE);
                            String username = firstname.getText().toString().trim() + " " + lastname.getText().toString().trim();
                            username=username.trim();
                            String email = emailid.getText().toString().trim();
                            String pass = password.getText().toString().trim();
                            String phone = phone_no.getText().toString().trim();
                            String dateofbirth = dob.getText().toString().trim();
                            String gen = gender.trim();
                            String statee = state.getText().toString().trim();
                            String cityy=city.getText().toString().trim();


                            if(!existUser) {
                                if (verified) {
                                    progress2.setVisibility(View.VISIBLE);
                                    verified_phone=phone;
                                    if (verified_phone.equals(phone)) {
                                        progress2.setVisibility(View.VISIBLE);


                                        if (doctorOrPatient.equals("Patient")) {

                                            progress2.setVisibility(View.VISIBLE);
                                            signup.setVisibility(View.GONE);
                                            sendPatient(username, email, pass, phone, dateofbirth, gen, statee,cityy,"patient");


                                        } else if(doctorOrPatient.equals("Lab Assistant")) {

                                            progress2.setVisibility(View.VISIBLE);
                                            signup.setVisibility(View.GONE);
                                            sendPatient(username, email, pass, phone, dateofbirth, gen, statee,cityy,"assistant");



                                        }else {


                                            if (speciality.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty speciality", Toast.LENGTH_SHORT).show();
                                            } else if (yoe.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty year of experience", Toast.LENGTH_SHORT).show();
                                            } else if (qualification.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty qualification", Toast.LENGTH_SHORT).show();

                                            } else if (about_text.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty about", Toast.LENGTH_SHORT).show();

                                            } else if (organisation.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty hospital/clinic name", Toast.LENGTH_SHORT).show();

                                            } else if (clinic_state.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty hospital/clinic state", Toast.LENGTH_SHORT).show();

                                            }
                                            else if (clinic_city.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty hospital/clinic city", Toast.LENGTH_SHORT).show();

                                            }

                                            else if (clinic_type.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty hospital/clinic type", Toast.LENGTH_SHORT).show();

                                            } else if (clinic_phone.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty hospital/clinic phone", Toast.LENGTH_SHORT).show();

                                            }
                                            else if(aboutDoc.getText().toString().trim().equals("")) {
                                                scroll.fullScroll(scroll.FOCUS_UP);
                                                Toast.makeText(getApplicationContext(), "empty about", Toast.LENGTH_SHORT).show();

                                            }
                                            else {
                                                progress2.setVisibility(View.VISIBLE);
                                                signup.setVisibility(View.GONE);
                                                sendDoctor(username, email, pass, phone, dateofbirth, gen, statee,cityy, speciality.getText().toString().trim(), yoe.getText().toString().trim(), qualification.getText().toString().trim(), aboutDoc.getText().toString().trim(), organisation.getText().toString().trim(), clinic_type.getText().toString().trim(), clinic_state.getText().toString().trim(),clinic_city.getText().toString().trim(), clinic_phone.getText().toString().trim());
                                            }
                                        }

                                    }


                                    else{
                                        verified = false;
                                        verified_phone = "";
                                        verify_but.setText("Verify");
                                        otp_but.setVisibility(View.VISIBLE);
                                        verify_but.setVisibility(View.GONE);

                                        if (!phone_no.getText().toString().equals("") && phone_no.getText().toString().length() == 10) {
                                            Toast.makeText(getApplicationContext(), "verify changed phone no.", Toast.LENGTH_SHORT).show();
                                            phone_text.setText("+91" + phone_no.getText().toString());

                                        } else if (!phone_no.getText().toString().equals("") && phone_no.getText().toString().length() != 10) {
                                            Toast.makeText(getApplicationContext(), "invalid phone no.", Toast.LENGTH_SHORT).show();
                                            scroll.fullScroll(ScrollView.FOCUS_UP);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "empty phone no.", Toast.LENGTH_SHORT).show();
                                            scroll.fullScroll(ScrollView.FOCUS_UP);
                                        }

                                    }
                                }else{
                                    Toast.makeText(getApplicationContext(),"verify phone no.", Toast.LENGTH_SHORT).show();
                                }
                                //

                            }


                            else{
                                Toast.makeText(getApplicationContext(),"user already exist", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            docpat_warn.setText("** kindly select one option");
                            docpat_warn.setTextSize(13);
                            docpat_warn.setVisibility(View.VISIBLE);

                        }
                    }
                }


            }
        });


    }
    public void sendPatient(String username, String email, String password, String phone, String dob, String gender, String state,String city,String key) {
        progress2.setVisibility(View.VISIBLE);
        String temp="";
        if(key.equals("Patient")) {
             temp= "https://demo-uw46.onrender.com/api/patient/register";
        }else{
            temp= "https://demo-uw46.onrender.com/api/assistant/register";
        }
              JSONObject inner2=new JSONObject();
                JSONObject jsonobj=new JSONObject();
        try {
            if(key.equals("Patient")) {
                jsonobj.put("doctor_patient", "patient");
            }else{
                jsonobj.put("doctor_patient", "assistant");
            }
                  jsonobj.put("username", username);
                jsonobj.put("email", email);
                jsonobj.put("password", password);
                jsonobj.put("phone",verified_phone);
                jsonobj.put("dob", dob);
                jsonobj.put("gender", gender);
                jsonobj.put("state", state);
                jsonobj.put("city",city);
                jsonobj.put("doctor",inner2);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
                    JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, jsonobj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                if (response.getString("success").equals("true")) {

                                    Toast.makeText(getApplicationContext(), "registered successfully", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                } else {

                                    Toast.makeText(RegisterPage.this, "user already exists", Toast.LENGTH_SHORT).show();
                                    progress2.setVisibility(View.GONE);
                                    signup.setVisibility(View.VISIBLE);

                                }
                            } catch (JSONException e) {
                                progress2.setVisibility(View.GONE);
                                signup.setVisibility(View.VISIBLE);
                                Toast.makeText(RegisterPage.this, "error : "+e, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                            progress2.setVisibility(View.GONE);
                            progress.setVisibility(View.GONE);

                        }
                    });
                    RequestQueue q = Volley.newRequestQueue(RegisterPage.this);

                    q.add(j);
                }


    public void sendDoctor(String username,String email,String password,String phone,String dob,String gender,String state,String city,String speciality,String yoe,String qualification,String about,String hosName,String hosType,String hosState,String hosCity,String hosPhone){
progress2.setVisibility(View.VISIBLE);

            String temp="https://demo-uw46.onrender.com/api/doctor/register";
            JSONObject clinic_hospital=new JSONObject();

        try {
            clinic_hospital.put("name", hosName);
            clinic_hospital.put("type", hosType);
            clinic_hospital.put("state",hosState);
            clinic_hospital.put("city",hosCity);
            clinic_hospital.put("phone", hosPhone);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
            JSONObject jsonobj=new JSONObject();
        JSONObject inner2=new JSONObject();

        try {
            jsonobj.put("doctor_patient", doctorOrPatient);
            jsonobj.put("username", username);
            jsonobj.put("email", email);
            jsonobj.put("password", password);
            jsonobj.put("phone", verified_phone);
            jsonobj.put("dob", dob);
            jsonobj.put("gender", gender);
            jsonobj.put("state", state);
            jsonobj.put("city",city);
            jsonobj.put("speciality", speciality);
            jsonobj.put("yoe", yoe);
            jsonobj.put("qualification", qualification);
            jsonobj.put("about", about);
            jsonobj.put("clinic_hospital", clinic_hospital);
jsonobj.put("sign",photosign_+"");
jsonobj.put("photoid",photoid_+"");
            jsonobj.put("patient",inner2);
        } catch (JSONException e) {

    }
                    JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, jsonobj, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getString("success").equals("true")) {
                                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();

                                } else {
                                    progress2.setVisibility(View.GONE);
                                    Toast.makeText(RegisterPage.this, "user already exists", Toast.LENGTH_SHORT).show();
                                    signup.setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                signup.setVisibility(View.VISIBLE);
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            progress.setVisibility(View.GONE);
                            progress2.setVisibility(View.GONE);
                            signup.setVisibility(View.VISIBLE);

                        }
                    });
                    RequestQueue q = Volley.newRequestQueue(RegisterPage.this);

                    q.add(j);
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






    public void signin(String otp){
        if(!verificationId.equals("")) {
            PhoneAuthCredential p = PhoneAuthProvider.getCredential(verificationId, otp);
            verifyit(p);
        }
        else{
            progress.setVisibility(View.GONE);
            Intent i=new Intent(this, LoginActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "retry again", Toast.LENGTH_SHORT).show();
        }

    }




    public void verifyit(PhoneAuthCredential pac){
        //pb.setVisibility(View.GONE);
        auth.signInWithCredential(pac)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        progress.setVisibility(View.GONE);
                        verified=true;
                        verified_phone=phone_no.getText().toString();
                        verify_but.setText("Verified");
                        otp_but.setVisibility(View.GONE);
                        otpedit.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(), "Verification successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progress.setVisibility(View.GONE);
                        //otpedit.setVisibility(View.GONE);
                        if(!verified)
                        Toast.makeText(getApplicationContext(), "Verification failed", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplicationContext(), "already verified", Toast.LENGTH_SHORT).show();
                    }
                });    }

    public void getotpFirebase(String phone2) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + phone2, 60, TimeUnit.SECONDS, RegisterPage.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                progress.setVisibility(View.VISIBLE);
                verifyit(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                verificationId=s;




                super.onCodeSent(s, forceResendingToken);
                progress.setVisibility(View.GONE);
verify_but.setVisibility(View.VISIBLE);
otpedit.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "otp sent to +91"+phone_no.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public void setPhoto(ImageView profilepic){
        String photo= sp.getString("photo","");
        if(!photo.equals("")) {
            Bitmap b = getbitmap(photo);
            profilepic.setImageBitmap(b);
        }
        else{
            profilepic.setImageResource(R.drawable.baseline_person_24);
        }
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                img.setImageBitmap(imageBitmap);
                photoid_= String.valueOf(getImageUri(RegisterPage.this, imageBitmap));
                sp.edit().putString("photo",photoid_).apply();
                //updateprofile(photo,imageBitmap);
            }
            catch (Exception e){
                img.setImageResource(R.drawable.baseline_person_24);
            }

        }
else if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
sign.setImageBitmap(imageBitmap);
                photosign_= String.valueOf(getImageUri(RegisterPage.this, imageBitmap));
                sp.edit().putString("photosign",photosign_).apply();
                //updateprofile(photo,imageBitmap);
            }
            catch (Exception e){
                sign.setImageResource(R.drawable.baseline_person_24);
            }

        }

    }
    public String getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] byteImage_photo = bytes.toByteArray();
        return Base64.encodeToString(byteImage_photo,Base64.DEFAULT);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode >= 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startImageCapture(requestCode);
            } else {

            }
        }
    }

    private void startImageCapture(int i) {
        if (ContextCompat.checkSelfPermission(RegisterPage.this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null ) {

                startActivityForResult(intent,i);

            } else {

                Toast.makeText(RegisterPage.this, "No camera app available", Toast.LENGTH_SHORT).show();
            }
        } else {

            ActivityCompat.requestPermissions(RegisterPage.this, new String[]{android.Manifest.permission.CAMERA}, 1);
        }
    }


    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }



}

