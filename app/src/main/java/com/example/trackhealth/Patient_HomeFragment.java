package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Patient_HomeFragment extends Fragment {
    RecyclerView recyclerView;
    Toolbar toolbar;
    ImageView refresh;

    List<List> outer=new ArrayList<>();
    ProgressBar progressBar;
    TextView nodata;
    LottieAnimationView empty;
   CardView diet,medicine,exercise;
    NavigationView navigationView;
    patient_homeAdapter adapter;
    List arr=new ArrayList<>();
    FloatingActionButton fab;
    DrawerLayout drawerLayout;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_patient__home, container, false);
diet=view.findViewById(R.id.diet_card);
exercise=view.findViewById(R.id.exercise_knowledge_card);
medicine=view.findViewById(R.id.exercise_medicine_card);
        recyclerView=view.findViewById(R.id.patient_recyclview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        sp= requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE);


        progressBar=view.findViewById(R.id.patientpage_home_progress);
        progressBar.setVisibility(View.VISIBLE);
        empty=view.findViewById(R.id.home_patientpage_lottie);
        nodata=view.findViewById(R.id.home_patientpage_nodata);
        refresh=view.findViewById(R.id.refresh_home_patientpage);
        fab=view.findViewById(R.id.fab_pat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fab=new Intent(getActivity(), AddPatientPage.class);
                startActivity(fab);
            }
        });
        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Knowledgegyan.class);
                i.putExtra("page","Exercise");
                getActivity().startActivity(i);
            }
        });
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Knowledgegyan.class);
                i.putExtra("page","diet");
                getActivity().startActivity(i);
            }
        });

        SearchView searchView=view.findViewById(R.id.patient_home_searchlayout2);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),Knowledgegyan.class);
                i.putExtra("page","medicine");
                getActivity().startActivity(i);
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchDoctors(sp.getString("phone",""));
            }
        });
       /* toolbar = view.findViewById(R.id.customtool);
        ((AppCompatActivity) getActivity()).setSupportActionBar(null);
        drawerLayout = requireActivity().findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(requireActivity(), drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
         toggle.syncState();*/


        searchDoctors(sp.getString("phone",""));
        return view;
    }

    private void searchList(String text) {
        List<List> dataSearchList = new ArrayList<>();
        for (int i=0;i<outer.size();i++) {
            if (outer.get(i).get(1).toString().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(outer.get(i));
            }
        }
        if (dataSearchList.isEmpty()) {
            Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
        }
        System.out.println("hello"+dataSearchList);
        adapter = new patient_homeAdapter(dataSearchList,getActivity(),"home");
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
    }

    public void searchDoctors(String ph){
        outer=new ArrayList<>();
        String temp = "https://demo-uw46.onrender.com/api/patient/getDetails";
        try {
            progressBar.setVisibility(View.VISIBLE);
            refresh.setVisibility(View.GONE);
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("phone", ph);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            JSONArray ja = response.getJSONArray("doctoradd");
                            if (ja.length()>0) {
                                arr = filterArray(response.getJSONArray("doctoradd"));
                                if (arr.size() > 0) {
                                    adapter = new patient_homeAdapter(arr,getActivity(),"home");
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(adapter);
                                    progressBar.setVisibility(View.GONE);
                                    refresh.setVisibility(View.GONE);
                                    empty.setVisibility(View.GONE);
                                    nodata.setVisibility(View.GONE);
                                    recyclerView.setHasFixedSize(true);
                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    empty.setVisibility(View.VISIBLE);
                                    refresh.setVisibility(View.GONE);
                                    nodata.setVisibility(View.VISIBLE);
                                }
                            }else{
                                progressBar.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                                refresh.setVisibility(View.GONE);
                                nodata.setVisibility(View.VISIBLE);
                            }
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            empty.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);
                            refresh.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(),"failed to load",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        empty.setVisibility(View.GONE);
                        nodata.setVisibility(View.GONE);
                        refresh.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    empty.setVisibility(View.GONE);
                    nodata.setVisibility(View.GONE);
                    refresh.setVisibility(View.VISIBLE);
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
            //   pb.setVisibility(View.GONE);
            //       pb.setVisibility(View.GONE);

        }

    }



    public List filterArray(JSONArray jsonArray) throws JSONException {
       outer=new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){

            JSONObject j=jsonArray.getJSONObject(i);
            List<String> inner=new ArrayList<>();
            if(j.getString("pending").equals("false")){
                try {
                    inner.add(j.getString("photo"));
                }
                catch (Exception e){
                    inner.add("");
                }
                inner.add(j.getString("username"));
                inner.add(j.getString("speciality"));
                try {
                    inner.add(j.getString("clinic_name"));
                }catch(Exception e){
                    inner.add("");
                }
                inner.add(j.getString("qualification"));
                inner.add(j.getString("about"));
                inner.add(j.getString("phone"));


                inner.add(j.getString("state"));
                inner.add(j.getString("city"));
                if(!sp.getString("identity","").equals("Doctor")) {
                    inner.add(j.getString("qualification"));
                    inner.add(j.getString("speciality"));
                    inner.add(j.getString("clinic_name"));
                }


                outer.add(inner);

            }

        }

        return outer;
    }


}