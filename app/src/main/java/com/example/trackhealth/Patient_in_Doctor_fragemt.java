package com.example.trackhealth;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Calendar;
import java.util.Collections;


public class Patient_in_Doctor_fragemt extends Fragment {
  ShapeableImageView shapeableImageView;
  ImageView call;
  SharedPreferences sp;
  TextView  name,gender,age,bgroup,emaild,locaton;

    public Patient_in_Doctor_fragemt() {
        // Required empty public constructor
    }

    ZegoSendCallInvitationButton vcall;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patient_in__doctor_fragemt, container, false);
        shapeableImageView=view.findViewById(R.id.pid_photo);
        name=view.findViewById(R.id.pid_name);
        gender=view.findViewById(R.id.pid_gender);
        age=view.findViewById(R.id.pid_age);
        emaild=view.findViewById(R.id.emaildetails);
        locaton=view.findViewById(R.id.pid_loc);

        call=view.findViewById(R.id.patientindoc_call);
        sp=view.getContext().getSharedPreferences("user",Context.MODE_PRIVATE);

        shapeableImageView.setImageBitmap(getbitmap(sp.getString("curphoto","")));
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = sp.getString("curphone2","");
                if (!phoneNumber.isEmpty()) {
                    if (ContextCompat.checkSelfPermission(view.getContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        // You have permission, so proceed with making the call
                        Intent myIntent = new Intent(Intent.ACTION_CALL);
                        myIntent.setData(Uri.parse("tel:" + phoneNumber));
                        view.getContext().startActivity(myIntent);
                    } else {
                        // Request the CALL_PHONE permission
                        ActivityCompat.requestPermissions((Activity) view.getContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                } else {
                    Toast.makeText(view.getContext(), "Phone number not available", Toast.LENGTH_SHORT).show();
                }
            }


        });
       

        name.setText(sp.getString("curname",""));
        gender.setText(sp.getString("curgender",""));
          emaild.setText(sp.getString("curemail",""));
        try {
            age.setText(getAge(sp.getString("curdob", "")));
        }
        catch (Exception e){

        }

        locaton.setText(sp.getString("curcity","")+","+sp.getString("curstate",""));
   vcall=view.findViewById(R.id.pid_vcall);
        startService(sp.getString("phone",""));
        setVideoCall(sp.getString("curphone2",""));

        return view;
    }

    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }
    void setVideoCall(String targetUserID){
        vcall.setIsVideoCall(true);
        vcall.setResourceID("zego_uikit_call"); // Please fill in the resource ID name that has been configured in the ZEGOCLOUD's console here.
        vcall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
    }
    String getAge(String birthDate) {
        int year= Calendar.getInstance().get(Calendar.YEAR);
        int year2=year-Integer.parseInt(birthDate.substring(6));

        return year2+" yrs";
    }
    void startService(String userId){
        Application application =getActivity().getApplication(); // Android's application context
        long appID = 2035003173;   // yourAppID
        String appSign ="47a24a7211aca24715f2e101ddbed51b5d4a6f01c1dfec1800ab4af2ad7eac25";  // yourAppSign
        //  String userID =; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName =userId;   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();

        //  ZegoUIKitPrebuiltCallService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
        ZegoUIKitPrebuiltCallService.init(getActivity().getApplication(), appID, appSign, userId, userName,callInvitationConfig);
    }
}