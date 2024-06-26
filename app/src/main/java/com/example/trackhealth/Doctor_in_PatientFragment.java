package com.example.trackhealth;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.imageview.ShapeableImageView;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Doctor_in_PatientFragment extends Fragment {

SharedPreferences sp,sp2,sp44;
TextView dname,dspec,dhos,about,vote;
RadioGroup radio;
    RadioButton all,five,four,three,two,one;
ImageView call;

ProgressBar progressBar;
ShapeableImageView pimg;
float rate=0.0f;
ReviewAdapter adapter;
LinearLayoutManager l;
List<List> arr=new ArrayList<>();
    ZegoSendCallInvitationButton videoCall;
RecyclerView review;
ShapeableImageView dp;
RatingBar ratingBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_doctor_in__patient, container, false);
        dname=view.findViewById(R.id.Dn);
        pimg=view.findViewById(R.id.doctorpho);
        ratingBar=view.findViewById(R.id.rating_doctor_patientpage);
        pimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),SearchProfile.class);
                i.putExtra("nonuserprofile",sp44.getString("curphone2",""));
            startActivity(i);
            }
        });
        call=view.findViewById(R.id.doctorinpat_call);
        progressBar=view.findViewById(R.id.reviewprogress);
        dspec=view.findViewById(R.id.skill);
        vote=view.findViewById(R.id.vote_dppage);
        dhos=view.findViewById(R.id.pl);
        sp44=getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        videoCall=view.findViewById(R.id.dip_vcall);
        startService(sp44.getString("phone",""));
        setVideoCall(sp44.getString("curphone2",""));

        dp=view.findViewById(R.id.doctorpho);
        Button b1=view.findViewById(R.id.feed);//this is popup trigger id
        about=view.findViewById(R.id.dipabout);
        radio=view.findViewById(R.id.dipradio);
        all=view.findViewById(R.id.dipall);
        five=view.findViewById(R.id.dip5);
        four=view.findViewById(R.id.dip4);
        three=view.findViewById(R.id.dip3);
        two=view.findViewById(R.id.dip2);
        one=view.findViewById(R.id.dip1);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
setAlert();
            }


        });
        all.setSelected(true);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,7);


            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,5);


            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,4);


            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,3);


            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,2);


            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getList(arr,1);


            }
        });

        ImageView t1map=view.findViewById(R.id.loc1);
        sp=view.getContext().getSharedPreferences("docpat", Context.MODE_PRIVATE);
        sp2=view.getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        review=view.findViewById(R.id.diprecreview);
        dname.setText("Dr. "+sp.getString("dname",""));
        dspec.setText(sp.getString("dspec",""));
        dhos.setText(sp.getString("dhos",""));
        getReview(sp.getString("dphone",""));
        if(!sp.getString("dphoto","").equals("")) {
            dp.setImageBitmap(getbitmap(sp.getString("dphoto", "")));
        }
        about.setText(sp.getString("dabout",""));
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
                                ratingBar.setRating(rating);
                                rate=rating;
                                //ratingBar.getProgressDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_ATOP);

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
                          float userRating=rate;
                          String userText=editText.getText().toString().trim();
                          if(userRating>0 && !userText.isEmpty()){
                              //processFeedback(userRating,userText);
                              addReview(userRating,userText);
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


    // Set up video call
    void setVideoCall(String targetUserID){
        videoCall.setIsVideoCall(true);
        videoCall.setResourceID("zego_uikit_call"); // Please fill in the resource ID name that has been configured in the ZEGOCLOUD's console here.
        videoCall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserID)));
    }

    private void processFeedback(float rating, String feedbackText) {//to add to server
        String feedbackMessage = "Rating: " + rating + "Feedback: " + feedbackText;
        Toast.makeText(getActivity(), feedbackMessage, Toast.LENGTH_SHORT).show();
    }

    public void setAlert(){
        String pos="Yes";
        String neg="No";
        String msg="Do you want to do voice call?";
        androidx.appcompat.app.AlertDialog.Builder b=new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        b.setMessage(msg);
        b.setPositiveButton(pos,(DialogInterface.OnClickListener) (dialog, which)->{


                String phoneNumber = sp44.getString("curphone2","");
                if (!phoneNumber.isEmpty()) {
                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        // You have permission, so proceed with making the call
                        Intent myIntent = new Intent(Intent.ACTION_CALL);
                        myIntent.setData(Uri.parse("tel:" + phoneNumber));
                       getActivity().startActivity(myIntent);
                    } else {
                        // Request the CALL_PHONE permission
                        ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                } else {
                    Toast.makeText(getActivity(), "Phone number not available", Toast.LENGTH_SHORT).show();
                }


        });
        b.setNegativeButton(neg,(DialogInterface.OnClickListener) (dialog,which)->{

            dialog.cancel();
        });
        AlertDialog ad=b.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                ad.getWindow().getDecorView().setBackgroundColor(getActivity().getResources().getColor(R.color.black));
            }
        });

        ad.show();

    }

    public Bitmap getbitmap(String s){
        byte[] bytes= Base64.decode(s,Base64.DEFAULT);
        Bitmap bitmap2= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap2;
    }


    public void getReview(String ph){

        String temp = "https://demo-uw46.onrender.com/api/doctor/getDetails";
        try {
            progressBar.setVisibility(View.VISIBLE);
           // refresh.setVisibility(View.GONE);
           // empty.setVisibility(View.GONE);

            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("phone", ph);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            JSONArray ja = response.getJSONArray("reviews");
                            if (ja.length()>0) {
                                arr = filterArray(response.getJSONArray("reviews"));
                                if (arr.size() > 0) {
                                    adapter = new ReviewAdapter(arr,getActivity());
                                    review.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                                    review.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);
                                    review.setVisibility(View.VISIBLE);
                                    review.setHasFixedSize(true);
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    review.setVisibility(View.VISIBLE);
                                }
                            }else{
                                progressBar.setVisibility(View.GONE);
                                review.setVisibility(View.VISIBLE);
                            }
                        }
                        else{
                      progressBar.setVisibility(View.GONE);
                            review.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(),"failed to load"+sp.getString("dphone",""),Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        review.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    review.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"check internet connectivity", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(getActivity());
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            review.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            //       pb.setVisibility(View.GONE);

        }

    }


    public void getList(List<List> arr,int key){

        List<List> outer=new ArrayList<>();

        for(int i=0;i<arr.size();i++) {
            List<String> inner = new ArrayList<>();
            int a = (int) Float.parseFloat(arr.get(i).get(3).toString());


            if (a==key) {
                try {
                    inner.add(arr.get(i).get(0).toString());

                } catch (Exception e) {
                    inner.add("");
                }
                inner.add(arr.get(i).get(1).toString());
                inner.add(arr.get(i).get(2).toString());
                inner.add(arr.get(i).get(3).toString());
                inner.add(arr.get(i).get(4).toString());
                inner.add(arr.get(i).get(5).toString());
                outer.add(inner);
            }
            else if(key==7){
                try {
                    inner.add(arr.get(i).get(0).toString());

                } catch (Exception e) {
                    inner.add("");
                }
                inner.add(arr.get(i).get(1).toString());
                inner.add(arr.get(i).get(2).toString());
                inner.add(arr.get(i).get(3).toString());
                inner.add(arr.get(i).get(4).toString());
                inner.add(arr.get(i).get(5).toString());
                outer.add(inner);
            }
        }


        if (outer.size() > 0) {
            review.setVisibility(View.VISIBLE);
            l = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            review.setLayoutManager(l);
           // adapter.notifyDataSetChanged();
            adapter= new ReviewAdapter(outer,getActivity());
          review.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
        }
        else{
            review.setVisibility(View.GONE);
        }


    }


    public List filterArray(JSONArray jsonArray) throws JSONException {
        List<List> outer=new ArrayList<>();

  float startot=0.0f;
        for(int i=0;i<jsonArray.length();i++){

            JSONObject j=jsonArray.getJSONObject(i);
            List<String> inner=new ArrayList<>();

                    try {
                        inner.add(j.getString("photo"));
                    } catch (Exception e) {
                        inner.add("");
                    }
                    inner.add(j.getString("username"));
                    inner.add(j.getString("review"));
                    inner.add(j.getString("star"));
                    inner.add(j.getString("date"));
                    inner.add(j.getString("time"));
                    startot+=Float.parseFloat(j.getString("star"));

            outer.add(inner);

            }
startot=startot/(jsonArray.length());
        ratingBar.setRating(startot);
        vote.setText(truncateToOneDecimalPlace(startot)+" ( "+jsonArray.length()+" votes )");

        return outer;
    }
    public static float truncateToOneDecimalPlace(float value) {
        return (float) ((int) (value * 10)) / 10;
    }

    public void addReview(float rate,String txt){
        String temp = "https://demo-uw46.onrender.com/api/doctor/review/"+sp.getString("dphone","");
        try {
            progressBar.setVisibility(View.VISIBLE);
            review.setVisibility(View.GONE);
            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("username",sp2.getString("name",""));
            jsonobj.put("review",txt);
            jsonobj.put("star",String.valueOf(rate));
            jsonobj.put("phone",sp2.getString("phone",""));
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            jsonobj.put("date",dateFormat.format(date));
            dateFormat = new SimpleDateFormat("HH:mm:ss");
            date = new Date();
            jsonobj.put("time",dateFormat.format(date));

            try {
                jsonobj.put("photo", sp2.getString("photo", ""));
            }
            catch(Exception e){
                jsonobj.put("photo","");
            }
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {

                            getReview(sp.getString("dphone",""));

                        }

                    } catch (Exception e) {
                        review.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    review.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(),"check internet connectivity", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(getActivity());
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            progressBar.setVisibility(View.GONE);
            review.setVisibility(View.VISIBLE);
            //       pb.setVisibility(View.GONE);

        }

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