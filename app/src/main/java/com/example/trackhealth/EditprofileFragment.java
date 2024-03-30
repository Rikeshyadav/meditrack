package com.example.trackhealth;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

public class EditprofileFragment extends Fragment {
    TextView name, email, phone, state, city, hospital, hosparent, about, gender, dob, age, quali, spec, yoe, hname, htype, hstate, hcity, hphone;
    boolean adhosactive=false,adyoeactive=false,adspecactive=false,adqualiactive=false;

    TextView quadesc,yeardesc;
    ProgressBar pb;
    RelativeLayout ll;
    ImageView editbut,addqua,addspec,addyoe,addhos,profilepic;
    SharedPreferences sp;
    String doctororpatient = "", ph = "", pass = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);
        profilepic=view.findViewById(R.id.doctor_profile_image);
        editbut = view.findViewById(R.id.edit_profile_doctor_icon);
        sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        addqua=view.findViewById(R.id.doctor_profile_qualicardadd);
        addspec=view.findViewById(R.id.doctor_profile_speccardadd);
        addyoe=view.findViewById(R.id.doctor_profile_yoecardadd);
        addhos=view.findViewById(R.id.doctor_profile_hoscardadd);
quadesc=view.findViewById(R.id.pdoctor_profile_qualicard1);
        yeardesc=view.findViewById(R.id.pdoctor_profile_yearcard1);
        name = view.findViewById(R.id.pdoctor_profile_dnamecard1);
        about = view.findViewById(R.id.doctor_profile_aboutvalue);
        state = view.findViewById(R.id.doctor_profile_stateValue);
        ll=view.findViewById(R.id.hosll_deditprofile);
        city = view.findViewById(R.id.doctor_profile_cityValue);
        phone = view.findViewById(R.id.doctor_profile_phoneValue);
        email = view.findViewById(R.id.doctor_profile_emailValue);
        gender = view.findViewById(R.id.doctor_profile_genderValue);
        dob = view.findViewById(R.id.doctor_profile_dobValue);
        age = view.findViewById(R.id.doctor_profile_ageValue);

        quali = view.findViewById(R.id.doctor_profile_qualivalue);
        spec = view.findViewById(R.id.doctor_profile_specvalue);
        yoe = view.findViewById(R.id.doctor_profile_yoevalue);

        hname = view.findViewById(R.id.doctor_profile_hosnameValue);
        htype = view.findViewById(R.id.doctor_profile_hostypeValue);
        hstate = view.findViewById(R.id.doctor_profile_hosstateValue);
        hcity = view.findViewById(R.id.doctor_profile_hoscityValue);
        hphone = view.findViewById(R.id.doctor_profile_hosphoneValue);


addhos.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(adhosactive){
            ll.setVisibility(View.GONE);
            adhosactive = false;
            addhos.setImageDrawable(getResources().getDrawable(R.drawable.add_plus));
        }
        else {
            ll.setVisibility(View.VISIBLE);
            adhosactive = true;
            addhos.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
        }
    }
});


        addqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adqualiactive){
                   quali.setVisibility(View.GONE);
                    adqualiactive = false;
                    addqua.setImageDrawable(getResources().getDrawable(R.drawable.add_plus));
                }
                else {
                    quali.setVisibility(View.VISIBLE);
                    adqualiactive = true;
                    addqua.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                }
            }
        });


        addspec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adspecactive){
                    spec.setVisibility(View.GONE);
                    adspecactive = false;
                    addspec.setImageDrawable(getResources().getDrawable(R.drawable.add_plus));
                }
                else {
                    spec.setVisibility(View.VISIBLE);
                    adspecactive = true;
                    addspec.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                }
            }
        });


        addyoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adyoeactive){
                    yoe.setVisibility(View.GONE);
                    adyoeactive = false;
                    addyoe.setImageDrawable(getResources().getDrawable(R.drawable.add_plus));
                }
                else {
                    yoe.setVisibility(View.VISIBLE);
                    adyoeactive = true;
                    addyoe.setImageDrawable(getResources().getDrawable(R.drawable.uparrow));
                }
            }
        });

        editbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), editProfile.class);
                startActivity(i);
            }
        });

        getvalues2();
        return view;
    }

    String getAge(String birthDate) {
int year=Calendar.getInstance().get(Calendar.YEAR);
        int year2=year-Integer.parseInt(birthDate.substring(6));

        return year2+" years";
    }
    public void getvalues2() {
        setPhoto();
yeardesc.setText(sp.getString("yoe","")+" Yrs Practice");
quadesc.setText(sp.getString("qualification","")+" - "+sp.getString("speciality",""));
        name.setText(sp.getString("name", ""));
        email.setText(sp.getString("email", ""));
        state.setText(sp.getString("state", ""));
        city.setText(sp.getString("city", ""));
        gender.setText(sp.getString("gender", ""));
        age.setText(getAge(sp.getString("dob","")));
        dob.setText(sp.getString("dob", ""));
phone.setText(sp.getString("phone",""));
        spec.setText(sp.getString("speciality", ""));
        quali.setText(sp.getString("qualification", ""));
        yoe.setText(sp.getString("yoe", "")+" years");
        about.setText(sp.getString("about", ""));
        hname.setText(sp.getString("clinic_name", ""));
        htype.setText(sp.getString("clinic_type", ""));
        hstate.setText(sp.getString("clinic_state", ""));
        hcity.setText(sp.getString("clinic_city", ""));
        hphone.setText(sp.getString("clinic_phone", ""));


    }


    public void setPhoto(){
        String photo= sp.getString("photo","");
        if(!photo.equals("")) {
            Bitmap b = getbitmap(photo);
            profilepic.setImageBitmap(b);
        }
        else{
            profilepic.setImageResource(R.drawable.baseline_person_24);
        }
    }




    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }


    @Override
    public void onResume() {
        super.onResume();
        getvalues2();
    }

}