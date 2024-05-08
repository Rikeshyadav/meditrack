package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;

public class VcallActivity extends AppCompatActivity {
    TextView showname;
    EditText  userid;
    ZegoSendCallInvitationButton voiceCall, videoCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vcall);
        showname = findViewById(R.id.username);
        userid = findViewById(R.id.userIDsecond);
        voiceCall = findViewById(R.id.voice_call);
        videoCall = findViewById(R.id.video_call);
        // Get user ID from intent
        SharedPreferences sp=getSharedPreferences("user", Context.MODE_PRIVATE);
        String userIdtwo = getIntent().getStringExtra("userIdone");
        showname.setText("Hey " + userIdtwo+"--"+sp.getString("curphone2",""));

        // Text change listener for user ID EditText
        setVideoCall(sp.getString("curphone2",""));
    }


    // Set up video call
    void setVideoCall(String targetUserID){
        videoCall.setIsVideoCall(true);
        videoCall.setResourceID("zego_uikit_call"); // Please fill in the resource ID name that has been configured in the ZEGOCLOUD's console here.
        videoCall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
    }



}
