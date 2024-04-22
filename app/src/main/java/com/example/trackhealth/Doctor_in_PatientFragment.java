package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Doctor_in_PatientFragment extends Fragment {

SharedPreferences sp,sp2;
TextView dname,dspec,dhos,about;
RadioGroup radio;
RadioButton all,five,four,three,two,one;
ProgressBar progressBar;
float rate=0.0f;
ReviewAdapter adapter;
LinearLayoutManager l;
List<List> arr=new ArrayList<>();

RecyclerView review;
ShapeableImageView dp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_doctor_in__patient, container, false);
        dname=view.findViewById(R.id.Dn);
        progressBar=view.findViewById(R.id.reviewprogress);
        dspec=view.findViewById(R.id.skill);
        dhos=view.findViewById(R.id.pl);
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
    private void processFeedback(float rating, String feedbackText) {//to add to server
        String feedbackMessage = "Rating: " + rating + "Feedback: " + feedbackText;
        Toast.makeText(getActivity(), feedbackMessage, Toast.LENGTH_SHORT).show();
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
            adapter.notifyDataSetChanged();
            adapter= new ReviewAdapter(outer,getActivity());
          review.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        else{
            review.setVisibility(View.GONE);
        }


    }


    public List filterArray(JSONArray jsonArray) throws JSONException {
        List<List> outer=new ArrayList<>();

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


            outer.add(inner);

            }

        return outer;
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
}