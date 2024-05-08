package com.example.trackhealth;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;


public class Patient_in_Doctor_fragemt extends Fragment {
  ShapeableImageView shapeableImageView;
  TextView  name,gender,Age,bgroup,emaild,locaton;

    public Patient_in_Doctor_fragemt() {
        // Required empty public constructor
    }

    ZegoSendCallInvitationButton vcall;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patient_in__doctor_fragemt, container, false);
        shapeableImageView=view.findViewById(R.id.PAteint_photo);
        name=view.findViewById(R.id.PAteint_name1);
        gender=view.findViewById(R.id.PAtient_gender);
        Age=view.findViewById(R.id.PAteint_age);
        bgroup=view.findViewById(R.id.PAteint_blood);
        emaild=view.findViewById(R.id.emaildetails);
        locaton=view.findViewById(R.id.patient_location);

        SharedPreferences sp44=getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
   vcall=view.findViewById(R.id.pid_vcall);
        startService(sp44.getString("phone",""));
        setVideoCall(sp44.getString("curphone2",""));

        return view;
    }
    void setVideoCall(String targetUserID){
        vcall.setIsVideoCall(true);
        vcall.setResourceID("zego_uikit_call"); // Please fill in the resource ID name that has been configured in the ZEGOCLOUD's console here.
        vcall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
    }

    void startService(String userId){
        Application application =getActivity().getApplication(); // Android's application context
        long appID = 434413145;   // yourAppID
        String appSign ="027ea8dc3c6a5baf570771f1bf803204242953e0929716ac097d30e68e426025";  // yourAppSign
        //  String userID =; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName =userId;   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();

        //  ZegoUIKitPrebuiltCallService.init(getApplication(), appID, appSign, userID, userName,callInvitationConfig);
        ZegoUIKitPrebuiltCallService.init(getActivity().getApplication(), appID, appSign, userId, userName,callInvitationConfig);
    }
}