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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class RegisterPage extends AppCompatActivity {
    Spinner dopcat;
    int select = 1;
    ImageView img;
    String phh="";
    ProgressBar progress;
    String verified_phone="";
    String otp="";
    boolean verified=false,existUser=false;

    ScrollView scroll;

    RadioGroup radiogroup_gender, radiogroup_work;

    RadioButton male_radio, female_radio, others_radio, clinic_radio, hospital_radio;

    TextView spec_text, yoe_text, about_text, qualification_text, text_clinicphone, text_clinic_header, text_clinic_name, text_clinic_type, text_clinic_address, country_code2;

    TextView additional_det, verification_text, phone_text, doc_reg, pass_warn, docpat_warn, upload_warn;

    EditText speciality, yoe, aboutDoc, qualification, organisation, clinic_type, clinic_address, clinic_phone, dob, phone_no, pass;
    EditText password, firstname, lastname, emailid, address,otpedit;

    AppCompatButton signup, otp_but, verify_but;

    CardView card2, card3, card4, card5;
    String doctorOrPatient = "null", gender = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

progress=findViewById(R.id.progressregister);

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
        doc_reg = findViewById(R.id.reg_document_header);
        verification_text = findViewById(R.id.reg_verification_header);
        additional_det = findViewById(R.id.edu_detail);
        phone_text = findViewById(R.id.phone_for_verification);
        pass_warn = findViewById(R.id.pass_warn);
        docpat_warn = findViewById(R.id.docpat_warn);


        //id of scrollView

        scroll = findViewById(R.id.scroll);

        // id of edit text

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        emailid = findViewById(R.id.email);
        clinic_phone = findViewById(R.id.clinic_phone);
        dopcat = findViewById(R.id.docpat);
        clinic_type = findViewById(R.id.clinic_type);
        address = findViewById(R.id.address);
        clinic_address = findViewById(R.id.clinic_address);
        speciality = findViewById(R.id.speciality);
        yoe = findViewById(R.id.year_of_experience);
        aboutDoc = findViewById(R.id.aboutdoctor);
        qualification = findViewById(R.id.qualification);
        organisation = findViewById(R.id.organisation);
        phone_no = findViewById(R.id.phoneNo);
        password = findViewById(R.id.pass);
        otpedit = findViewById(R.id.reg_otp);

        //image view
        img = findViewById(R.id.image1);

        // id of button
        signup = findViewById(R.id.signup);
        otp_but = findViewById(R.id.get_otp_but);
        verify_but = findViewById(R.id.verify_but_reg);
        upload_warn = findViewById(R.id.upload_doc);


        //radio button and group
        radiogroup_work = findViewById(R.id.work);
        hospital_radio = findViewById(R.id.hospital_radio);
        clinic_radio = findViewById(R.id.clinic_radio);
        male_radio = findViewById(R.id.male);
        female_radio = findViewById(R.id.female);
        others_radio = findViewById(R.id.others);


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
        String[] st = {"Select", "Doctor", "patient"};
        RegisterSpinnerApdater adapter = new RegisterSpinnerApdater(this, R.layout.spinner1, st);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dopcat.setAdapter(adapter);


        dopcat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedOption = (String) adapterView.getItemAtPosition(i);


                if (selectedOption.equals("Doctor")) {
                        select = 1;

                    select = 1;
                    doctorOrPatient = "Doctor";
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
                        doc_reg.setVisibility(View.GONE);
                        hospital_radio.setVisibility(View.GONE);
                        clinic_radio.setVisibility(View.GONE);



                }
                if (selectedOption.equals("Select")) {
                    card5.setVisibility(View.GONE);
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

        verify_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String verotp=otpedit.getText().toString().trim();
                if(!verotp.equals("")){
                    if(otp.equals(verotp)){
                        verified=true;
                        verify_but.setText("Verified");
                        otpedit.setVisibility(View.GONE);
                        otp_but.setVisibility(View.GONE);
                    }
                    else{
                        Toast.makeText(RegisterPage.this, "wrong otp", Toast.LENGTH_SHORT).show();
                    }
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
                        sendmsg(phone_no.getText().toString().trim());
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
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });


// document image
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (password.getText().toString().length() < 8) {
                    pass_warn.setText("** must be atleast 8 characters long");
                    pass_warn.setVisibility(View.VISIBLE);
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                }

                if (password.getText().toString().length() >= 8) {

                    if (!isValid(password.getText().toString())) {

                        pass_warn.setText("** must include atleast one Upper case,one Lowercase and one number character");
                        scroll.fullScroll(ScrollView.FOCUS_UP);
                        pass_warn.setVisibility(View.VISIBLE);
                    } else {

                        pass_warn.setVisibility(View.GONE);
                        if (select == 1) {

                            docpat_warn.setVisibility(View.GONE);
                            String username = firstname.getText() + " " + lastname.getText();
                            String email = emailid.getText().toString().trim();
                            String pass = password.getText().toString().trim();
                            String phone = phone_no.getText().toString().trim();
                            String dateofbirth = dob.getText().toString().trim();
                            String gen = gender.trim();
                            String addres = address.getText().toString().trim();
                             if(!existUser) {

                                  if(verified_phone.equals(phone)){
                                 if (verified) {

                                     if (doctorOrPatient.equals("Patient")) {

                                         sendPatient(username, email, pass, phone, dateofbirth, gen, addres);

                                     } else {
                                         sendDoctor(username, email, pass, phone, dateofbirth, gen, addres, speciality.getText().toString().trim(), yoe.getText().toString().trim(), qualification.getText().toString().trim(), aboutDoc.getText().toString().trim(), organisation.getText().toString().trim(), clinic_type.getText().toString().trim(), clinic_address.getText().toString().trim(), clinic_phone.getText().toString().trim());

                                     }
                                 } else {
                                     Toast.makeText(getApplicationContext(), "verify phone no.", Toast.LENGTH_SHORT).show();
                                 }

                             }
                                  else{
                                      verified=false;
                                      verified_phone="";
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

    public void sendPatient(String username, String email, String password, String phone, String dob, String gender, String address) {
        String temp = "https://demo-uw46.onrender.com/api/patient/register";

        HashMap<String, String> jsonobj = new HashMap<>();
        jsonobj.put("doctor_patient", "patient");

        jsonobj.put("username", username);
        jsonobj.put("email", email);
        jsonobj.put("password", password);
        jsonobj.put("phone",verified_phone);
        jsonobj.put("dob", dob);
        jsonobj.put("gender", gender);
        jsonobj.put("address", address);

        JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("success").equals("true")) {
                        Toast.makeText(getApplicationContext(), "registered successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(RegisterPage.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("sorry : " + error);
            }
        });
        RequestQueue q = Volley.newRequestQueue(RegisterPage.this);

        q.add(j);

    }


    public void sendDoctor(String username,String email,String password,String phone,String dob,String gender,String address,String speciality,String yoe,String qualification,String about,String hosName,String hosType,String hosAddress,String hosPhone){
        String temp="https://demo-uw46.onrender.com/api/doctor/register";
        ArrayList<String> clinic_hospital=new ArrayList<>();
        clinic_hospital.add(hosName);
        clinic_hospital.add(hosType);
        clinic_hospital.add(hosAddress);
        clinic_hospital.add(hosPhone);
        HashMap<String,String> jsonobj=new HashMap<>();
        jsonobj.put("doctor_patient","patient");

        jsonobj.put("username", username);
        jsonobj.put("email", email);
        jsonobj.put("password", password);
        jsonobj.put("phone", verified_phone);
        jsonobj.put("dob", dob);
        jsonobj.put("gender", gender);
        jsonobj.put("address", address);
        jsonobj.put("speciality",speciality);
        jsonobj.put("yoe",yoe);
        jsonobj.put("qualification",qualification);
        jsonobj.put("about",about);
        jsonobj.put("clinic_hospital",clinic_hospital.toString());
        JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp,new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getString("success").equals("true")) {
                        Intent i = new Intent(getApplicationContext(), Register_process.class);
                        startActivity(i);
                        Toast.makeText(getApplicationContext(), "Registration under process", Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(RegisterPage.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("sorry : "+error);
            }
        });
        RequestQueue q= Volley.newRequestQueue(RegisterPage.this);

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




    public void getOtp(String phone){
        String token="00501cc79e211e158fb6a251af6c5f32";
        String temp = "https://demo-uw46.onrender.com/api/getotp";
        HashMap<String,String> jsonobj=new HashMap<>();
        jsonobj.put("token",token);
        jsonobj.put("phone", "+91"+phone);

        JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp,new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(Boolean.parseBoolean(response.getString("success"))){

                        otp=response.getString("otp");
                        verified_phone=phone;
                        progress.setVisibility(View.GONE);
                        otpedit.setText("");
                        otpedit.setVisibility(View.VISIBLE);

                        verify_but.setVisibility(View.VISIBLE);

                        Toast.makeText(getApplicationContext(),"otp sent", Toast.LENGTH_LONG).show();
                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    Toast.makeText(getApplicationContext(),"Something went wrong", Toast.LENGTH_SHORT).show();
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("sorry : "+error);
            }
        });
        RequestQueue q= Volley.newRequestQueue(RegisterPage.this);

        q.add(j);
    }


    public void sendmsg(String myphone){
        myphone=myphone.trim();
        phh=myphone;
        if(myphone.equals("")){
            Toast.makeText(getApplicationContext(),"please enter phone no.",Toast.LENGTH_SHORT).show();
        }
        String temp = "https://demo-uw46.onrender.com/api/getotpsms";
        try{
            progress.setVisibility(View.VISIBLE);
            //  HashMap<String,String> jsonobj=new HashMap<>();
            //jsonobj.put("phone",myphone);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, temp,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    progress.setVisibility(View.GONE);
                    try {

                        if(Boolean.parseBoolean(response.getString("success"))){

                            if(ContextCompat.checkSelfPermission(RegisterPage.this, android.Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                                progress.setVisibility(View.VISIBLE);
                                SmsManager sms=SmsManager.getDefault();
                                otp=response.getString("otp");
                                verified_phone=phh;
                                progress.setVisibility(View.GONE);
                                otpedit.setText("");
                                otpedit.setVisibility(View.VISIBLE);

                                verify_but.setVisibility(View.VISIBLE);

                                sms.sendTextMessage(phone_no.getText().toString().trim(),null,"Your OTP for TrackHealth App verification is "+response.getString("otp"),null,null);
                                progress.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"otp sent",Toast.LENGTH_LONG).show();

                                progress.setVisibility(View.GONE);
                            }else{

                                ActivityCompat.requestPermissions(RegisterPage.this,new String[]{Manifest.permission.SEND_SMS},100);
                            }

                        }

                    } catch (JSONException e) {
                        progress.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("sorry : "+error);
                }
            });
            RequestQueue q= Volley.newRequestQueue(RegisterPage.this);

            q.add(j);


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==100 && grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
            //sendmsg(e1.getText().toString());
        }
        else{
            Toast.makeText(getApplicationContext(),"Permission Denied!, kindly allow sms permission", Toast.LENGTH_LONG).show();        }
    }




}

