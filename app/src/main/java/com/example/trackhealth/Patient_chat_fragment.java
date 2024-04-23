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
import android.widget.ScrollView;
import android.widget.Toast;

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
ScrollView scrollView;
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

scrollView=view.findViewById(R.id.chatscroll);
        scrollView.fullScroll(View.FOCUS_DOWN);
        scrollView.setSmoothScrollingEnabled(true);

        rec.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String user=sp.getString("identity","");

        if(user.equals("Doctor")){
            child=sp.getString("phone","")+"@"+sp.getString("curphone2","");
        }
        else if(user.equals("Patient")){
            child=sp.getString("curphone2","")+"@"+sp.getString("phone","");
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
                if (dataSnapshot.exists()) { // Check if reference exists
                    messageList.clear(); // Clear the existing list

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

                    System.out.println("msgdatare" + messageList);
                    scrollView.fullScroll(View.FOCUS_DOWN);

                    // Notify the adapter that the dataset has changed
                    if (rec.getAdapter() == null) {
                        ChatAdapter chatAdapter = new ChatAdapter(messageList, getActivity());
                        rec.setAdapter(chatAdapter);
                        rec.smoothScrollToPosition(messageList.size() - 1);
                    } else {
                        ((ChatAdapter) rec.getAdapter()).updateData(messageList);
                        rec.smoothScrollToPosition(messageList.size() - 1);
                    }
                } else {
                    // Handle the case where the reference does not exist
                    //Toast.makeText(getContext(), "Reference does not exist", Toast.LENGTH_SHORT).show();
                    // Optionally, clear the RecyclerView or display a message to the user
                    messageList.clear();
                    if (rec.getAdapter() != null) {
                        ((ChatAdapter) rec.getAdapter()).updateData(messageList);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(getContext(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // rec.setAdapter(chatAdapter);
        return view;
    }
}