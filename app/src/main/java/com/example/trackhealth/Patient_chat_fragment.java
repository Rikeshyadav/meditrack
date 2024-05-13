package com.example.trackhealth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Patient_chat_fragment extends Fragment {

    EditText txt;
    String child = "";
    private static Bundle mBundleRecyclerViewState;
    private static final int PAGE_SIZE = 20; // Number of items to load per page
    ScrollView scrollView;
    private boolean loadFirstTime = true;
    private boolean isLoading = true;
    List<List> messageList = new ArrayList<>();
    List<List>  rowsArrayList = new ArrayList<>();
    int dsize = 0;
    ImageView send;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    ChatAdapter chatAdapter;
    SharedPreferences sp;
    RecyclerView rec;
    Parcelable recyclerViewState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_chat_fragment, container, false);
        sp = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        txt = view.findViewById(R.id.chateditpatient);
        send = view.findViewById(R.id.chatsendpat);
        scrollView=view.findViewById(R.id.chatscroll);
        scrollView.fullScroll(View.FOCUS_DOWN);
        rec = view.findViewById(R.id.chatrecpat);
        rec.setHasFixedSize(true);
        rec.setVisibility(View.GONE);
        rec.setLayoutManager(new LinearLayoutManager(getActivity()));

        chatAdapter = new ChatAdapter(messageList, getActivity());
        rec.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rec.setLayoutManager(layoutManager);
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
                // Remove the listener to avoid multiple calls
                scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                rec.setVisibility(View.VISIBLE);
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String user = sp.getString("identity", "");

        if (user.equals("Doctor")) {
            child = sp.getString("phone", "") + "@" + sp.getString("curphone2", "");
        } else if (user.equals("Patient")) {
            child = sp.getString("curphone2", "") + "@" + sp.getString("phone", "");
        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = database.getReference(child);
                Map<String, Object> messageData = new HashMap<>();
                messageData.put("user", sp.getString("phone", ""));
                messageData.put("message", txt.getText().toString());
                myRef.push().setValue(messageData);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                txt.setText("");
            }
        });

        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference(child);
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageList.clear(); // Clear the existing list

                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String message = messageSnapshot.child("message").getValue(String.class);
                    String user = messageSnapshot.child("user").getValue(String.class);

                    List<String> messageData = new ArrayList<>();
                    messageData.add(message);
                    messageData.add(user);
                    messageList.add(messageData);

                }
                /*if (mBundleRecyclerViewState != null) {
                   Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    rec.getLayoutManager().onRestoreInstanceState(listState);
                }
                */

                if (loadFirstTime) {
                    chatAdapter.notifyDataSetChanged();
                    rec.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                    rec.scrollToPosition(messageList.size() - 1);
                    dsize = messageList.size();
                    loadFirstTime = false;
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);

                }
                if (messageList.size() > dsize) {
                    //chatAdapter.notifyDataSetChanged();
                    chatAdapter=new ChatAdapter(messageList,getActivity());
                    rec.setAdapter(chatAdapter);
                    rec.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                    rec.scrollToPosition(messageList.size() - 1);
                    dsize = messageList.size();
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
                Toast.makeText(getContext(), "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

  /*      rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mBundleRecyclerViewState = new Bundle();
                    Parcelable listState = rec.getLayoutManager().onSaveInstanceState();
                    mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
                }
            }});
*/
        return view;
    }



}