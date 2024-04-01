package com.example.trackhealth;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Doctor_in_PatientFragment extends Fragment {


    public Doctor_in_PatientFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_doctor_in__patient, container, false);
       Button b1=view.findViewById(R.id.feed);//this is popup trigger id
        ImageView t1map=view.findViewById(R.id.loc1);
        TextView loc=view.findViewById(R.id.loc);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView=getLayoutInflater().inflate(R.layout.feedbackpage,null);
                PopupWindow popupWindow=new PopupWindow(popupView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
                 RatingBar ratingBar=popupView.findViewById(R.id.star);
                EditText editText=popupView.findViewById(R.id.review1);
                Button button=popupView.findViewById(R.id.skip1);
                Button button1=popupView.findViewById(R.id.sumit1);
                        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                int color= Color.RED;
                              //  int numstars=ratingBar.getNumStars();
                                ratingBar.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_ATOP);

                            }
                        });

                  button.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                           popupWindow.dismiss();
                      }
                  });
                  button1.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          float userRating=ratingBar.getRating();
                          String userText=editText.getText().toString().trim();
                          if(userRating>0 && !userText.isEmpty()){
                              processFeedback(userRating,userText);
                              popupWindow.dismiss();
                          }else {
                              Toast.makeText(getContext(),"please provide both rating and text",Toast.LENGTH_SHORT).show();
                          }

                      }
                  });

                // Show the popup window
                popupWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL, 0, 10);
            }
        });
        t1map.setOnClickListener(new View.OnClickListener() {///
            @Override
            public void onClick(View v) {
            String destination=loc.getText().toString();
            String source=loc.getText().toString();
            if(destination.equals(" ")){
                Toast.makeText(getContext(),"emty",Toast.LENGTH_SHORT).show();
            }else {
                Uri uri=Uri.parse("https://www.google.com/maps/dir/"+ "  "+"/" +destination);
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            }
        });

        return view;
    }
    private void processFeedback(float rating, String feedbackText) {//to add to server
        String feedbackMessage = "Rating: " + rating + "Feedback: " + feedbackText;
        Toast.makeText(getActivity(), feedbackMessage, Toast.LENGTH_SHORT).show();
    }
}