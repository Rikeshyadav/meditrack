package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Patient_chat_fragment extends Fragment {
EditText txt;
    String child="";
    List<List> messageList=new ArrayList<>();
ImageView send;
SharedPreferences sp;
RecyclerView rec;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
View view=inflater.inflate(R.layout.fragment_patient_chat_fragment, container, false);
sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
txt=view.findViewById(R.id.chateditpatient);
send=view.findViewById(R.id.chatsendpat);
rec=view.findViewById(R.id.chatrecpat);
        rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String user=sp.getString("identity","");

        if(user.equals("Doctor")){
            child=sp.getString("phone","")+"@"+sp.getString("curphone","");
        }
        else if(user.equals("Patient")){
            child=sp.getString("curphone","")+"@"+sp.getString("phone","");
        }


send.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        DatabaseReference myRef = database.getReference(child);
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("user",sp.getString("phone","") );
        messageData.put("message", txt.getText().toString());
        messageData.put("identity", sp.getString("identity",""));

        myRef.push().setValue(messageData);
        txt.setText("");


    }
});


        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference(child);

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList=new ArrayList<>();
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String message = messageSnapshot.child("message").getValue(String.class);
                    String user = messageSnapshot.child("user").getValue(String.class);
                    String identity = messageSnapshot.child("identity").getValue(String.class);
                    List<String> messageData = new ArrayList<>();
                    messageData.add(message);
                    messageData.add(user);
                    messageData.add(identity);
                    messageList.add(messageData);

                }
                System.out.println("msgdatare"+messageList);
                ChatAdapter chatAdapter=new ChatAdapter(messageList,getActivity());
                rec.setAdapter(chatAdapter);

                // Now you have the messageList containing the messages data
                // You can use it as needed, for example, pass it to an adapter for displaying in a RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });



       // rec.setAdapter(chatAdapter);
        return view;
    }
}