package com.example.trackhealth;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class hospital_search_viewpager extends Fragment {

    Spinner spinner,parameter_filter;
    List<List> arr=new ArrayList<>();
    String[] parameterpop = {"Hospital Name", "Hospital Type", "State", "City"};
    RecyclerView recyclerView;
    ProgressBar progressBar_hosclinicSearch;
    boolean selectpara=false;
    LottieAnimationView lottieAnimationView;
    ViewGroup root;
    ImageView loc;
    int parabackup=0,placebackup=0,typebackup=0, ratebackup =0;
    int parabackup2=0,placebackup2=0,typebackup2=0, ratebackup2 =0;
    String para="Hospital Name",place="",key="name";
    String key1=key;
    TextView emptymsg;
    boolean statebool2=false,citybool2=false, ratebool2 =false, typebool2 =false;
    String ratesearch="",typesearch="",statesearch="",citysearch="";
    TextInputLayout textInputLayout;
    int y=0;
    AutoCompleteTextView txt;
    TextView filter_but;
    String corh="hospital";

    RelativeLayout layout;
    String parameter_="Hospital Name",filter_="no";
    Spinner spinner2;
    Context context;
    List<List> arr2 =new ArrayList<>();

    hosclinic_search_adapter hospital_searchadapter;
    TextInputEditText cityEditText,stateEditText,paraEditText, typeEditText,rateEditText;
    TextInputLayout parameter_button,state_button,city_button, type_button, rate_button;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_hospital_search_viewpager, container, false);

        recyclerView = view.findViewById(R.id.hospital_search_rec);
        key="name";
        root = (ViewGroup) requireActivity().getWindow().getDecorView().getRootView();
        txt=view.findViewById(R.id.hospital_searchtxt);
        progressBar_hosclinicSearch =view.findViewById(R.id.hospital_search_progress);
        lottieAnimationView=view.findViewById(R.id.search_hospital_lottie);
        textInputLayout=view.findViewById(R.id.hospital_search_txtinputlayout);
        emptymsg=view.findViewById(R.id.hospital_search_empty_msg);

        parameter_button=view.findViewById(R.id.para_but_hospital_search_filter_parent);
        state_button=view.findViewById(R.id.state_but_hospital_search_filter_parent);
        city_button=view.findViewById(R.id.city_but_hospital_search_filter_parent);
        layout=view.findViewById(R.id.layout_hospital_search);
        filter_but=view.findViewById(R.id.filter_but);
        paraEditText=view.findViewById(R.id.para_but_hospital_search_filter);
        cityEditText=view.findViewById(R.id.city_but_hospital_search_filter);
        stateEditText=view.findViewById(R.id.state_but_hospital_search_filter);
        type_button=view.findViewById(R.id.type_but_hospital_search_filter_parent);
        rate_button=view.findViewById(R.id.yoe_but_hospital_search_filter_parent);
        typeEditText=view.findViewById(R.id.type_but_hospital_search_filter);
        parameter_filter=view.findViewById(R.id.para_spinner_hosclinic_search_filter);
        RegisterSpinnerApdater filteradapter = new RegisterSpinnerApdater(getContext(), R.layout.spinner1, parameterpop);
        parameter_filter.setAdapter(filteradapter);

        parameter_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedOption = (String) adapterView.getItemAtPosition(i);
                parabackup = i;
                if (selectedOption.equals("Hospital Name")) {
                    para = "Hospital Name";
                    key = "name";
                    selectpara = true;

                } else if (selectedOption.equals("Hospital Type")) {
                    para = "Hospital Type";
                    key = "type";
                    selectpara = true;

                } else if (selectedOption.equals("State")) {
                    para = "State";
                    key = "state";
                    selectpara = true;
                } else if (selectedOption.equals("City")) {
                    para = "City";
                    key = "city";
                    selectpara = true;
                } else {
                    para = "Hospital Name";
                    key = "name";
                    selectpara = false;
                }
                paraEditText.setText(para);
                key1=key;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                para = "Hospital Name";
                key = "name";
                selectpara = false;
            }
        });


        rateEditText=view.findViewById(R.id.rate_but_hospital_search_filter);
        filter_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createWindow();
            }
        });



        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterlogic();
            }
        });

        state_button.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state_button.setVisibility(View.GONE);
                statebool2=false;
                statesearch="";
            }
        });

        parameter_button.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parameter_button.setVisibility(View.GONE);
                parameter_="Hospital Name";
                selectpara=false;
                para=parameter_;
                key="name";
                key1=key;
            }
        });


        city_button.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city_button.setVisibility(View.GONE);
                citybool2=false;
                citysearch="";
            }
        });
        txt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getAction()==KeyEvent.ACTION_DOWN && i==KeyEvent.KEYCODE_ENTER){
                    enterlogic();
                    return true;
                }
                return false;
            }
        });
        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                enterlogic();
            }
        });

        rate_button.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rate_button.setVisibility(View.GONE);
                ratebool2=false;
                ratesearch="";
            }
        });

        type_button.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type_button.setVisibility(View.GONE);
                typebool2=false;
                typesearch="";
            }
        });


        return view;
    }


    public void enterlogic() {
        recyclerView.setVisibility(View.GONE);
        progressBar_hosclinicSearch.setVisibility(View.VISIBLE);
        String content=txt.getText().toString().trim();
        if(!content.equals("")){
            searchhospital(content,key1,statesearch,citysearch);
        }
        else{
            Toast.makeText(getActivity(),"empty search",Toast.LENGTH_SHORT).show();
            recyclerView.setVisibility(View.GONE);
            progressBar_hosclinicSearch.setVisibility(View.GONE);
            emptymsg.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.GONE);
        }
    }
    public void searchhospital (String value,String parameter, String state,String city){
        progressBar_hosclinicSearch.setVisibility(View.VISIBLE);



        emptymsg.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.GONE);
        String temp = "https://demo-uw46.onrender.com/api/doctor/searchHospitals";

        try {
            HashMap<String, String> jsonobj = new HashMap<>();

            if(ratebool2) jsonobj.put("totalStar", ratesearch);
            if(typebool2) jsonobj.put("type", typesearch);
            if(citybool2) jsonobj.put("city",citysearch);
            if(statebool2) jsonobj.put("state",statesearch);
            jsonobj.put("hospital_clinic","hospital");
            jsonobj.put(parameter,value);
            Toast.makeText(getActivity(),ratebool2+ratesearch+parameter,Toast.LENGTH_SHORT).show();
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            lottieAnimationView.setVisibility(View.GONE);
                            emptymsg.setVisibility(View.GONE);
                            JSONArray ja = response.getJSONArray("data");

                            if (ja.length()>0) {
                                arr2 =filterArray(ja);

                                if(arr2.size()>0) {
                                    hospital_searchadapter = new hosclinic_search_adapter(arr2);
                                    progressBar_hosclinicSearch.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(hospital_searchadapter);
                                    recyclerView.setHasFixedSize(true);
                                }
                                else{
                                    Toast.makeText(getActivity(),"empty data",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        else{
                            Toast.makeText(getActivity(),"no data",Toast.LENGTH_SHORT).show();
                            progressBar_hosclinicSearch.setVisibility(View.GONE);
                            lottieAnimationView.setVisibility(View.VISIBLE);
                            emptymsg.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        progressBar_hosclinicSearch.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        emptymsg.setVisibility(View.VISIBLE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(),"volley error "+error.toString(), Toast.LENGTH_SHORT).show();
                    progressBar_hosclinicSearch.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
            });
            RequestQueue q = Volley.newRequestQueue(requireActivity());
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);
            q.add(j);


        } catch (
                Exception e) {
            Toast.makeText(getActivity(), "catched error", Toast.LENGTH_SHORT).show();
            progressBar_hosclinicSearch.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }

    }



    public void searchhospital (JSONObject jsonobj) {
        progressBar_hosclinicSearch.setVisibility(View.VISIBLE);
        emptymsg.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.GONE);
        String temp = "https://demo-uw46.onrender.com/api/doctor/searchHospitals";

        try {


            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, jsonobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            lottieAnimationView.setVisibility(View.GONE);
                            emptymsg.setVisibility(View.GONE);
                            JSONArray ja = response.getJSONArray("data");

                            if (ja.length() > 0) {
                                arr2 = filterArray(ja);

                                if (arr2.size() > 0) {
                                    hospital_searchadapter = new hosclinic_search_adapter(arr2);
                                    progressBar_hosclinicSearch.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(hospital_searchadapter);
                                    recyclerView.setHasFixedSize(true);
                                } else {
                                    Toast.makeText(getActivity(), "empty data", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
                            progressBar_hosclinicSearch.setVisibility(View.GONE);
                            lottieAnimationView.setVisibility(View.VISIBLE);
                            emptymsg.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        progressBar_hosclinicSearch.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        emptymsg.setVisibility(View.VISIBLE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "volley error " + error.toString(), Toast.LENGTH_SHORT).show();
                    progressBar_hosclinicSearch.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
            });
            RequestQueue q = Volley.newRequestQueue(requireActivity());
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);
            q.add(j);


        } catch (
                Exception e) {
            Toast.makeText(getActivity(), "catched error", Toast.LENGTH_SHORT).show();
            progressBar_hosclinicSearch.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }

    }






    public void createWindow() {
        y = 1;

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.filter_layout_hosclinicsearch, null);
        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = 1100;
        PopupWindow popupWindow = new PopupWindow(popup, width, height, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        AppCompatButton search_but=popup.findViewById(R.id.search_button_filter_hosclinic_search);
        AutoCompleteTextView state_city_edit2 = popup.findViewById(R.id.state_filter_hosclinic_search2);
        Spinner city_state_spinner2 = popup.findViewById(R.id.cityState_spinner_filter_hosclinic2);
        Spinner typespinner2 = popup.findViewById(R.id.typespinner_spinner_filter_hosclinic2);
        Spinner ratespinner2 = popup.findViewById(R.id.ratespinner_spinner_filter_hosclinic2);

        String[] cityorstate = {"set location", "City", "State"};
        String[] typestring = {"select", "Govt Hospital", "Private Hospital"};
        String[] ratestring = {"select", "5 star", "4 star", "3 star", "2 star", "1 star", "0 star"};


        RegisterSpinnerApdater cityStateAdapter = new RegisterSpinnerApdater(getActivity(), R.layout.spinner_login, cityorstate);


        RegisterSpinnerApdater typeadapter = new RegisterSpinnerApdater(getActivity(), R.layout.spinner_login, typestring);
        RegisterSpinnerApdater rateadapter = new RegisterSpinnerApdater(getActivity(), R.layout.spinner_login, ratestring);
        city_state_spinner2.setAdapter(cityStateAdapter);
        state_city_edit2.setVisibility(View.GONE);
        typespinner2.setAdapter(typeadapter);
        ratespinner2.setAdapter(rateadapter);
        JSONObject j = new JSONObject();
        try {
            j.put("hospital_clinic","hospital");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        String[] cities = {"Guwahati", "Patna", "Delhi", "Mumbai", "Kolkata", "Kerela", "Chennai", "Raipur", "Panaji", "Bangalore", "Itanagar", "Bhubneshwar", "Hyderabad", "Nirjuli", "Noida", "Bhopal", "Indore", "Sikunderabad"};
        String[] states = {"Assam", "UP", "Bihar", "Andhra Pradesh", "Uttrakhand", "West Bengal", "Meghalaya", "Sikkim", "Punjab", "Haryana", "Madhya Pradesh", "Maharashtra", "Gujrat", "J&K", "Himachal Pradesh", "Tamil Nadu",
                "Jharkhand", "Odisha", "Rajasthan", "Goa", "Arunachal Pradesh", "Karnataka"};

        RegisterSpinnerApdater stateAdapter = new RegisterSpinnerApdater(getActivity(), R.layout.spinner_login, states);
        RegisterSpinnerApdater cityAdapter = new RegisterSpinnerApdater(getActivity(), R.layout.spinner_login, cities);




        ratespinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ratebackup2=i;
                String item = (String) adapterView.getItemAtPosition(i);
                if (!item.equals("select")) {
                    ratebool2 = true;
                    ratesearch = String.valueOf(item.charAt(0));
                } else {
                    ratebool2 = false;
                    ratesearch = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ratebool2 = false;
            }
        });






        typespinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typebackup2 = i;
                String item = (String) adapterView.getItemAtPosition(i);
                if (!item.equals("select")) {
                    typebool2 = true;
                    if (item.equals("Govt Hospital")) typesearch = "Govt";
                    else if (item.equals("Private Hospital")) typesearch = "Private";
                } else {
                    typebool2 = false;
                    typesearch="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                typebool2 = false;
            }
        });

        city_state_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                placebackup2=i;
                String selection = (String) adapterView.getItemAtPosition(i);

                if (selection.equals("City")) {
                    citybool2 = true;
                    statebool2 = false;
                    state_city_edit2.setHint("Eg. Guwahati");
                    state_city_edit2.setFocusableInTouchMode(true);
                    state_city_edit2.setVisibility(View.VISIBLE);
                    state_city_edit2.setAdapter(cityAdapter);
                } else if (selection.equals("State")) {

                    state_city_edit2.setFocusableInTouchMode(true);
                    state_city_edit2.setHint("Eg. Assam");

                    statebool2 = true;
                    citybool2 = false;
                    state_city_edit2.setVisibility(View.VISIBLE);
                    state_city_edit2.setAdapter(stateAdapter);
                } else {
                    statebool2 = false;
                    citybool2 = false;
                    state_city_edit2.setFocusable(false);
                    state_city_edit2.setFocusableInTouchMode(false);
state_city_edit2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                statebool2 = false;
                citybool2 = false;
                state_city_edit2.setBackgroundColor(getResources().getColor(R.color.hint));
                state_city_edit2.setFocusable(false);
            }
        });


        AppCompatButton apply=popup.findViewById(R.id.filter_button_filter_hosclinic_search);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
          if(!state_city_edit2.getText().toString().trim().equals("")) {
              if (citybool2) {
                  citysearch = state_city_edit2.getText().toString().trim();
                  city_button.setVisibility(View.VISIBLE);
                  cityEditText.setText(citysearch);
              } else if (statebool2) {
                  statesearch = state_city_edit2.getText().toString().trim();
                  state_button.setVisibility(View.VISIBLE);
                  stateEditText.setText(statesearch);
              } else {

                  citysearch = "";
                  statesearch = "";
                  city_button.setVisibility(View.GONE);
                  state_button.setVisibility(View.GONE);
                  cityEditText.setText("");
                  stateEditText.setText("");

              }
          }else{
              Toast.makeText(getActivity(),"location not set",Toast.LENGTH_SHORT).show();
          }

                if (typebool2) {
                    type_button.setVisibility(View.VISIBLE);
                    typeEditText.setText(typesearch);
                }
                else{
                    type_button.setVisibility(View.GONE);
                    typeEditText.setText("");
                }

                if (ratebool2) {
                    rate_button.setVisibility(View.VISIBLE);
                    rateEditText.setText(ratesearch+" star");
                }
                else{
                    rate_button.setVisibility(View.GONE);
                    rateEditText.setText("");
                }

                popupWindow.dismiss();
            }
        });
        search_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                progressBar_hosclinicSearch.setVisibility(View.VISIBLE);

                if (citybool2) {
                    citysearch = state_city_edit2.getText().toString().trim();
                    city_button.setVisibility(View.VISIBLE);
                    cityEditText.setText(citysearch);
                    try {
                        j.put("city", citysearch);
                    } catch (JSONException e) {

                    }
                } else if (statebool2) {
                    statesearch = state_city_edit2.getText().toString().trim();
                    state_button.setVisibility(View.VISIBLE);
                    stateEditText.setText(statesearch);
                    try {
                        j.put("state", statesearch);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                } else {

                    citysearch = "";
                    statesearch = "";
                    city_button.setVisibility(View.GONE);
                    state_button.setVisibility(View.GONE);
                    cityEditText.setText("");
                    stateEditText.setText("");

                }

                if (typebool2) {
                    type_button.setVisibility(View.VISIBLE);
                    type_button.setVisibility(View.VISIBLE);
                    typeEditText.setText(typesearch);
                    try {
                        j.put("type", typesearch);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else{
                    type_button.setVisibility(View.GONE);
                    typeEditText.setText("");
                }

                if (ratebool2) {
                    rate_button.setVisibility(View.VISIBLE);
                    rateEditText.setText(ratesearch+" star");
                    try {
                        j.put("totalStar", ratesearch);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
                else{
                    rate_button.setVisibility(View.GONE);
                    rateEditText.setText("");
                }


                searchhospital(j);
                popupWindow.dismiss();
            }

        });


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim(root);
            }
        });

        layout.post(new Runnable() {
            @Override
            public void run() {

                applyDim(root,0.5f);
                popupWindow.showAtLocation(layout, Gravity.CENTER,0,0);


            }
        });

    }


    public  void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    public List filterArray(JSONArray jsonArray) throws JSONException {
        List<List> outer=new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++){
            JSONObject j=jsonArray.getJSONObject(i);
            List<String> inner=new ArrayList<>();
            inner.add(j.getString("name"));
            inner.add(j.getString("state")+","+j.getString("city"));
            inner.add(j.getString("type"));
            if(j.getString("totalStar").equals("")){inner.add("0");}
            else inner.add(j.getString("totalStar"));
            inner.add(j.getString("phone"));

            outer.add(inner);
        }

        return outer;
    }

}