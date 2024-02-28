package com.example.trackhealth;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

public class doctor_search_viewpager extends Fragment {
RecyclerView recyclerView;
ProgressBar progressBar_doctorSearch;
boolean selectpara=false;
LottieAnimationView lottieAnimationView;
ViewGroup root;
ImageView loc;
int parabackup=0,placebackup=0,genderbackup=0,yoebackup=0;
    String para="name",place="",key="username";
    String key1=key;
    TextView emptymsg;
    boolean statebool=false,citybool=false,yoebool=false,genderbool=false;
TextInputLayout textInputLayout;
String city="",state="",yoe_="",gender_="";
int y=0;
AutoCompleteTextView txt,spinner;
TextView filter_but;

RelativeLayout layout;
String parameter_="name",filter_="no";
Spinner spinner2;
Context context;
    List<List> arr=new ArrayList<>();

Doctor_search_adapter doctor_searchadapter;
TextInputEditText cityEditText,stateEditText,paraEditText,genderEditText,yoeEditText;
TextInputLayout parameter_button,state_button,city_button,gender_button,yoe_button;

    public doctor_search_viewpager(Context context) {
        this.context=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_doctor_search_viewpager, container, false);
         root = (ViewGroup) requireActivity().getWindow().getDecorView().getRootView();
        txt=view.findViewById(R.id.doctor_searchtxt);
        progressBar_doctorSearch=view.findViewById(R.id.hosclinic_search_progress);
        lottieAnimationView=view.findViewById(R.id.search_hosclinic_lottie);
        recyclerView = (RecyclerView) view.findViewById(R.id.doctor_search_rec);
        textInputLayout=view.findViewById(R.id.doctor_search_txtinputlayout);
        emptymsg=view.findViewById(R.id.hosclinic_search_empty_msg);
        parameter_button=view.findViewById(R.id.para_but_hosclinic_search_filter_parent);
        state_button=view.findViewById(R.id.state_but_doctor_search_filter_parent);
        city_button=view.findViewById(R.id.city_but_doctor_search_filter_parent);
        layout=view.findViewById(R.id.layout_hosclinic_search);
        filter_but=view.findViewById(R.id.filter_but);
        paraEditText=view.findViewById(R.id.para_but_hosclinic_search_filter);
        cityEditText=view.findViewById(R.id.city_but_doctor_search_filter);
        stateEditText=view.findViewById(R.id.state_but_doctor_search_filter);
  gender_button=view.findViewById(R.id.type_but_clinic_search_filter_parent);
  yoe_button=view.findViewById(R.id.yoe_but_doctor_search_filter_parent);
  genderEditText=view.findViewById(R.id.gender_but_doctor_search_filter);
  yoeEditText=view.findViewById(R.id.rate_but_hosclinic_search_filter);
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
                statebool=false;
                state="";
            }
        });

        parameter_button.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parameter_button.setVisibility(View.GONE);
                parameter_="Doctor name";
                selectpara=false;
                para=parameter_;
                key="username";
                key1=key;
            }
        });

        city_button.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city_button.setVisibility(View.GONE);
                citybool=false;
                city="";
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

        yoe_button.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yoe_button.setVisibility(View.GONE);
                yoebool=false;
                yoe_="";
            }
        });

        gender_button.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender_button.setVisibility(View.GONE);
                genderbool=false;
                gender_="";
            }
        });

        return view;
    }


public void enterlogic() {
        recyclerView.setVisibility(View.GONE);
        progressBar_doctorSearch.setVisibility(View.VISIBLE);
    String content=txt.getText().toString().trim();
    if(!content.equals("")){
        searchdoctor(content,key1,state,city);
    }
    else{
        Toast.makeText(getActivity(),"empty search",Toast.LENGTH_SHORT).show();
        recyclerView.setVisibility(View.GONE);
        progressBar_doctorSearch.setVisibility(View.GONE);
        emptymsg.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.GONE);
    }
}
    public void searchdoctor (String value,String parameter, String state,String city){
progressBar_doctorSearch.setVisibility(View.VISIBLE);
        emptymsg.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.GONE);
          String temp = "https://demo-uw46.onrender.com/api/doctor/searchDoctors";

        try {
            HashMap<String, String> jsonobj = new HashMap<>();
            if(yoebool) jsonobj.put("yoe",yoe_);
            if(genderbool) jsonobj.put("gender",gender_);
if(citybool) jsonobj.put("city",city);
if(statebool) jsonobj.put("state",state);
            jsonobj.put(parameter,value);

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            lottieAnimationView.setVisibility(View.GONE);
                            emptymsg.setVisibility(View.GONE);
                            JSONArray ja = response.getJSONArray("data");

                            if (ja.length()>0) {
                                arr=filterArray(ja);
                                if(arr.size()>0) {
                                    doctor_searchadapter = new Doctor_search_adapter(arr, context);
                                    progressBar_doctorSearch.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    recyclerView.setAdapter(doctor_searchadapter);
                                    recyclerView.setHasFixedSize(true);
                                }
                                else{
                                    Toast.makeText(getActivity(),"empty data",Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        else{
                            Toast.makeText(getActivity(),"no data",Toast.LENGTH_SHORT).show();
                            progressBar_doctorSearch.setVisibility(View.GONE);
                            lottieAnimationView.setVisibility(View.VISIBLE);
                            emptymsg.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        progressBar_doctorSearch.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.GONE);
                        emptymsg.setVisibility(View.VISIBLE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(),"volley error "+error.toString(), Toast.LENGTH_SHORT).show();
                    progressBar_doctorSearch.setVisibility(View.GONE);
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
            progressBar_doctorSearch.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        }

    }

    public void createWindow(){
y=1;

        LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popup=inflater.inflate(R.layout.filter_layout,null);
        int width=ViewGroup.LayoutParams.WRAP_CONTENT;
        int height=ViewGroup.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow=new PopupWindow(popup,width,height,true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

       Spinner parameter_filter=popup.findViewById(R.id.para_spinner_doctor_search_filter);
        AutoCompleteTextView state_city_edit=popup.findViewById(R.id.state_filter_doctor_search);
        Spinner city_state_spinner=popup.findViewById(R.id.cityState_spinner_filter_doctor);
        AppCompatButton close=popup.findViewById(R.id.close_button_filter_doctor_search);
        AppCompatButton apply=popup.findViewById(R.id.apply_button_filter_doctor_search);
        Spinner genderspinner=popup.findViewById(R.id.typespinner_spinner_filter_doctor);
        Spinner yoespinner=popup.findViewById(R.id.ratespinner_spinner_filter_doctor);

        String[] cityorstate={"select location","City","State"};
        String[] genderstring={"select","Male","Female","Others"};
        String[] yoestring={"select","0+ years","1+ years","2+ years","3+ years","4+ years","5+ years","10+ years","15+ years","20+ years","25+ years","30+ years","35+ years","40+ years","45+ years","50+ years","55+ years","60+ years"};

        RegisterSpinnerApdater cityStateAdapter=new RegisterSpinnerApdater(getActivity(),R.layout.spinner_login,cityorstate);

        RegisterSpinnerApdater genderadapter=new RegisterSpinnerApdater(getActivity(),R.layout.spinner_login,genderstring);
        RegisterSpinnerApdater yoeadapter=new RegisterSpinnerApdater(getActivity(),R.layout.spinner_login,yoestring);

        city_state_spinner.setAdapter(cityStateAdapter);
        genderspinner.setAdapter(genderadapter);
        yoespinner.setAdapter(yoeadapter);

        String[] parameterpop={"Doctor Name","Specialization","Qualification","Gender","State","City"};
        RegisterSpinnerApdater filteradapter=new RegisterSpinnerApdater(getContext(),R.layout.spinner_login,parameterpop);
        parameter_filter.setAdapter(filteradapter);

        String[] cities={"Guwahati","Patna","Delhi","Mumbai","Kolkata","Kerela","Chennai","Raipur","Panaji","Bangalore","Itanagar","Bhubneshwar","Hyderabad","Nirjuli","Noida","Bhopal","Indore","Sikunderabad"};
        String[] states={"Assam","UP","Bihar","Andhra Pradesh","Uttrakhand","West Bengal","Meghalaya","Sikkim","Punjab","Haryana","Madhya Pradesh","Maharashtra","Gujrat","J&K","Himachal Pradesh","Tamil Nadu",
                "Jharkhand","Odisha","Rajasthan","Goa","Arunachal Pradesh","Karnataka"};

        RegisterSpinnerApdater stateAdapter=new RegisterSpinnerApdater(getActivity(),R.layout.spinner_login,states);
        RegisterSpinnerApdater cityAdapter=new RegisterSpinnerApdater(getActivity(),R.layout.spinner_login,cities);

parameter_filter.setSelection(parabackup);
genderspinner.setSelection(genderbackup);
yoespinner.setSelection(yoebackup);
city_state_spinner.setSelection(placebackup);
        yoespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item=(String)adapterView.getItemAtPosition(i);
                yoebackup=i;
                   StringBuilder yearr= new StringBuilder();
                   if(!item.equals("select")) {
                       yoebool=true;
                       for (int j = 0; j < item.length(); j++) {
                           if (item.charAt(j) == '+') {
                               break;
                           } else {
                               yearr.append(item.charAt(j));
                           }
                       }
                       yoe_ = yearr.toString();
                   }
                   else {
                       yoebool=false;
                       yoe_="";
                   }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
yoebool=false;
            }
        });
        genderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item=(String)adapterView.getItemAtPosition(i);

                genderbackup=i;
                if(!item.equals("select")){
                    genderbool=true;
                    gender_=item;
                }
                else{
                    genderbool=false;
                    gender_="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

genderbool=false;
            }
        });

        city_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selection=(String)adapterView.getItemAtPosition(i);
 placebackup=i;

                if(selection.equals("City")){
                    state_city_edit.setVisibility(View.VISIBLE);
                    citybool=true;
                    statebool=false;
                    state_city_edit.setBackgroundColor(getResources().getColor(R.color.white));
                    state_city_edit.setHint("Eg. Guwahati");
                    state_city_edit.setAdapter(cityAdapter);
                }
                else if(selection.equals("State")){
                   state_city_edit.setVisibility(View.VISIBLE);

                   state_city_edit.setBackgroundColor(getResources().getColor(R.color.white));
                    state_city_edit.setHint("Eg. Assam");

                    statebool=true;
                    citybool=false;
                    state_city_edit.setAdapter(stateAdapter);
                }
                else{
                    statebool=false;
                    citybool=false;
                    state_city_edit.setBackgroundColor(getResources().getColor(R.color.hint));
                    state_city_edit.setVisibility(View.GONE);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                statebool=false;
                citybool=false;
                state_city_edit.setBackgroundColor(getResources().getColor(R.color.hint));
                state_city_edit.setFocusable(false);
            }
        });


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
parameter_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String selectedOption = (String) adapterView.getItemAtPosition(i);
        parabackup=i;
        if (selectedOption.equals("Doctor Name")) {
            para="Doctor Name";
            key="username";
            selectpara=true;
        }
else if(selectedOption.equals("Specialization")){
    para="Specialization";
    key="speciality";
            selectpara=true;
        }
        else if(selectedOption.equals("Qualification")){
            para="Qualification";
            key="qualification";
            selectpara=true;
        }
        else if(selectedOption.equals("Gender")){
            para="Gender";
            key="gender";
            selectpara=true;
        }
        else if(selectedOption.equals("State")){
            para="State";
            key="state";
            selectpara=true;
        }
        else if(selectedOption.equals("City")){
            para="City";
            key="city";
            selectpara=true;
        }
        else{
            para="Doctor Name";
            key="username";
            selectpara=false;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
para="name";
key="username";
selectpara=false;
    }
});

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectpara) parameter_button.setVisibility(View.VISIBLE);
                 parameter_=para;
                 key1=key;
                 paraEditText.setText(parameter_);
                 if(citybool){
                     city_button.setVisibility(View.VISIBLE);
                     city=state_city_edit.getText().toString().trim();
                     state_button.setVisibility(View.GONE);
                     cityEditText.setText(city);
                     place=state_city_edit.getText().toString().trim();
                 }
                 else if(statebool){
                     city_button.setVisibility(View.GONE);
                     state_button.setVisibility(View.VISIBLE);
                     state=state_city_edit.getText().toString().trim();
                    stateEditText.setText(state);
                     place=state_city_edit.getText().toString().trim();

                 }
                 else{
                     place="";
                     city_button.setVisibility(View.GONE);
                     state_button.setVisibility(View.GONE);
                 }

                 if(genderbool) {
                     gender_button.setVisibility(View.VISIBLE);

                     genderEditText.setText(gender_);
                 }
                 else{
                     gender_button.setVisibility(View.GONE);
                 }
                 if(yoebool){
                     yoe_button.setVisibility(View.VISIBLE);
                     yoeEditText.setText(yoe_+"+ years of experience");
                 }
                 else{
                     yoe_button.setVisibility(View.GONE);
                 }

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
                inner.add(j.getString("username"));
                inner.add(j.getString("state")+","+j.getString("city"));
                inner.add(j.getString("speciality"));
                inner.add(j.getString("qualification"));
                inner.add(j.getString("phone"));
                outer.add(inner);
        }

        return outer;
    }

}