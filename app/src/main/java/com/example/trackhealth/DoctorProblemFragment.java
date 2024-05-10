package com.example.trackhealth;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DoctorProblemFragment extends Fragment {
    Parentrecycle_Adapter parentrecycle_adapter;
    SharedPreferences sp,user;
    RecyclerView parentRecycle;
    List<List> outer=new ArrayList<>();
    LottieAnimationView lottie;
    TextView nodata;
    ProgressBar progressBar;
    ImageView retry;
    FloatingActionButton floatButton;
    List<List> arr=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    List<List> datalist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_doctor_problem, container, false);
        floatButton=view.findViewById(R.id.Parentfloatbutton);
        parentRecycle=view.findViewById(R.id.parentrecycleview);
        datalist=new ArrayList<>();


        progressBar=view.findViewById(R.id.progress_dproblem);
        retry=view.findViewById(R.id.retry_dproblem);
        lottie=view.findViewById(R.id.dproblem_lottie);
        nodata=view.findViewById(R.id.dproblem_nodata);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retry.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                getdata();
            }
        });
        sp=getActivity().getSharedPreferences("issue", Context.MODE_PRIVATE);
        user=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        if(!user.getString("identity","").equals("Doctor")){
        floatButton.setVisibility(View.GONE);

        }


        linearLayoutManager=new LinearLayoutManager(getActivity());
        parentRecycle.setLayoutManager(linearLayoutManager);
        parentrecycle_adapter=new Parentrecycle_Adapter(getActivity(),datalist);
        parentRecycle.setAdapter(parentrecycle_adapter);
        parentRecycle.scrollToPosition(arr.size()-1);
        parentRecycle.setHasFixedSize(true);
        getdata();

        floatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=LayoutInflater.from(getActivity());
                AlertDialog.Builder addDialog=new AlertDialog.Builder(getActivity());
                final android.view.View dview = inflater.inflate(R.layout.doctor_input, null);
                AutoCompleteTextView userNAme=dview.findViewById(R.id.editIssue);

                    String[] quan_ = {
                            "Diabetes",
                            "Hypertension",
                            "Migraine",
                            "Asthma",
                            "Arthritis",
                            "Anxiety",
                            "Depression",
                            "Back pain",
                            "Obesity",
                            "Acne",
                            "Allergies",
                            "GERD",
                            "Thyroid disorders",
                            "Chronic sinusitis",
                            "Gout",
                            "Urinary tract infection",
                            "Chronic bronchitis",
                            "Psoriasis",
                            "Eczema",
                            "Osteoporosis",
                            "Fibromyalgia",
                            "Chronic fatigue syndrome",
                            "COPD",
                            "Endometriosis",
                            "Irritable bowel syndrome",
                            "Celiac disease",
                            "Crohn's disease",
                            "Ulcerative colitis",
                            "Rheumatoid arthritis",
                            "Osteoarthritis",
                            "Chronic kidney disease",
                            "Kidney stones",
                            "Sleep apnea",
                            "Anemia",
                            "Hepatitis C",
                            "Parkinson's disease",
                            "Multiple sclerosis",
                            "Epilepsy",
                            "Chronic obstructive sleep apnea",
                            "Diverticulitis",
                            "Chronic lymphocytic leukemia",
                            "Multiple myeloma",
                            "Non-Hodgkin lymphoma",
                            "Pancreatic cancer",
                            "Prostate cancer",
                            "Breast cancer",
                            "Lung cancer",
                            "Colon cancer",
                            "Bladder cancer",
                            "Melanoma",
                            "Leukemia",
                            "Glioblastoma",
                            "Hodgkin's lymphoma",
                            "Thyroid cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Testicular cancer",
                            "Esophageal cancer",
                            "Liver cancer",
                            "Gastric cancer",
                            "Renal cell carcinoma",
                            "Sarcoma",
                            "Lymphoma",
                            "Brain tumor",
                            "Mesothelioma",
                            "Pneumonia",
                            "Bronchitis",
                            "Ear infection",
                            "Influenza",
                            "Strep throat",
                            "Tuberculosis",
                            "Cystic fibrosis",
                            "Meningitis",
                            "Lyme disease",
                            "Viral hepatitis",
                            "Gallstones",
                            "Kidney infection",
                            "Pelvic inflammatory disease",
                            "Cellulitis",
                            "MRSA",
                            "Sepsis",
                            "HIV/AIDS",
                            "Mononucleosis",
                            "Herpes",
                            "Gonorrhea",
                            "Chlamydia",
                            "Syphilis",
                            "Trichomoniasis",
                            "Genital herpes",
                            "Genital warts",
                            "Scabies",
                            "Pubic lice",
                            "Ringworm",
                            "Yeast infection",
                            "Vaginitis",
                            "Bacterial vaginosis",
                            "Hemorrhoids",
                            "Anal fissure",
                            "Anal abscess",
                            "Crohn's disease",
                            "Ulcerative colitis",
                            "Diverticulosis",
                            "Irritable bowel syndrome",
                            "Hemochromatosis",
                            "Gastroesophageal reflux disease (GERD)",
                            "Peptic ulcer disease",
                            "Pancreatitis",
                            "Gallstones",
                            "Gallbladder cancer",
                            "Liver cancer",
                            "Hepatitis",
                            "Cirrhosis",
                            "Gastritis",
                            "Gastroenteritis",
                            "Celiac disease",
                            "Diverticulitis",
                            "Hemorrhoids",
                            "Anal fissure",
                            "Anal abscess",
                            "Colorectal cancer",
                            "Hernia",
                            "Appendicitis",
                            "Peritonitis",
                            "Polycystic kidney disease",
                            "Acute kidney failure",
                            "Chronic kidney failure",
                            "Kidney stones",
                            "Hydronephrosis",
                            "Glomerulonephritis",
                            "Nephrotic syndrome",
                            "Pyelonephritis",
                            "Urinary incontinence",
                            "Bladder cancer",
                            "Kidney cancer",
                            "Urethral cancer",
                            "Interstitial cystitis",
                            "Prostate cancer",
                            "Benign prostatic hyperplasia (BPH)",
                            "Erectile dysfunction",
                            "Testicular cancer",
                            "Penile cancer",
                            "Hydrocele",
                            "Varicocele",
                            "Testicular torsion",
                            "Priapism",
                            "Infertility",
                            "Peyronie's disease",
                            "Hematuria",
                            "Prostatitis",
                            "Epididymitis",
                            "Orchitis",
                            "Hydrocele",
                            "Varicocele",
                            "Testicular torsion",
                            "Priapism",
                            "Infertility",
                            "Peyronie's disease",
                            "Hematuria",
                            "Prostatitis",
                            "Epididymitis",
                            "Orchitis",
                            "Uterine fibroids",
                            "Endometriosis",
                            "Ovarian cysts",
                            "Polycystic ovary syndrome (PCOS)",
                            "Pelvic inflammatory disease (PID)",
                            "Cervical dysplasia",
                            "Cervical cancer",
                            "Ovarian cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Vaginal cancer",
                            "Vulvar cancer",
                            "Breast cancer",
                            "Ovarian cancer",
                            "Cervical cancer",
                            "Endometrial cancer",
                            "Uterine cancer",
                            "Coronary artery disease",
                            "Heart attack (myocardial infarction)",
                            "Heart failure",
                            "Arrhythmia",
                            "Congenital heart defects",
                            "Peripheral artery disease",
                            "Rheumatic heart disease",
                            "Atrial fibrillation",
                            "Heart valve disease",
                            "Cardiomyopathy",
                            "Pericarditis",
                            "Endocarditis",

                            // Lung related diagnoses
                            "Chronic obstructive pulmonary disease (COPD)",
                            "Asthma",
                            "Lung cancer",
                            "Pneumonia",
                            "Bronchitis",
                            "Pulmonary embolism",
                            "Pulmonary fibrosis",
                            "Emphysema",
                            "Interstitial lung disease",
                            "Tuberculosis",
                            "Pleural effusion",

                            // Oral or dental related diagnoses
                            "Tooth decay (dental caries)",
                            "Gingivitis",
                            "Periodontitis",
                            "Oral cancer",
                            "Tooth abscess",
                            "Halitosis (bad breath)",
                            "Dry mouth (xerostomia)",
                            "Dental erosion",
                            "Temporomandibular joint disorders (TMJ)",
                            "Oral thrush (oral candidiasis)",
                            "Mouth ulcers",

                            // Orthopedic related diagnoses
                            "Osteoarthritis",
                            "Rheumatoid arthritis",
                            "Fractures",
                            "Sprains and strains",
                            "Osteoporosis",
                            "Back pain",
                            "Scoliosis",
                            "Tendinitis",
                            "Bursitis",
                            "Carpal tunnel syndrome",
                            "Plantar fasciitis",

                            // Neurology related diagnoses
                            "Stroke (cerebrovascular accident)",
                            "Alzheimer's disease",
                            "Parkinson's disease",
                            "Multiple sclerosis",
                            "Epilepsy",
                            "Migraine",
                            "Peripheral neuropathy",
                            "Amyotrophic lateral sclerosis (ALS)",
                            "Huntington's disease",
                            "Traumatic brain injury",
                            "Spinal cord injury",

                            // Eye related diagnoses
                            "Myopia (nearsightedness)",
                            "Hyperopia (farsightedness)",
                            "Glaucoma",
                            "Cataracts",
                            "Macular degeneration",
                            "Diabetic retinopathy",
                            "Conjunctivitis (pink eye)",
                            "Retinal detachment",
                            "Strabismus (crossed eyes)",
                            "Amblyopia (lazy eye)",
                            "Refractive errors",
                    };

                    RegisterSpinnerApdater nadapter = new RegisterSpinnerApdater(getActivity(), R.layout.spinner_login, quan_);
                    userNAme.setAdapter(nadapter);


                addDialog.setView(dview);
                addDialog.setPositiveButton("OK",(dialog, which) ->{
                    String issue=userNAme.getText().toString();
                    List<String> demo=new ArrayList<>();
                    demo.add(issue);
                    datalist.add(demo);
                    adddata(issue);
                    getdata();
                    Toast.makeText(getActivity(),"Issue Added",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
                addDialog.setNegativeButton("cancel",(dialog, which) -> {
                    dialog.dismiss();
                    Toast.makeText(getActivity(),"cancelled",Toast.LENGTH_SHORT).show();
                });
                addDialog.create().show();
            }

        });




        return view;
    }


    public void adddata(String issuetitle){

        String temp="https://demo-uw46.onrender.com/api/issue/addissue";

        try{
            JSONObject jobject=new JSONObject();
            jobject.put("idno",sp.getString("idno",""));
            jobject.put("sign","");
            jobject.put("issueTitle",issuetitle);

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, jobject, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (Boolean.parseBoolean(response.getString("success"))) {
                            lottie.setVisibility(View.GONE);
                            nodata.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(getActivity(), "check your network connectivity", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "failed to add", Toast.LENGTH_SHORT).show();

        }

    }


    public void getdata(){

        String temp="https://demo-uw46.onrender.com/api/issue/getissue/"+sp.getString("idno","");

        try{

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, temp,null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {
                        if (Boolean.parseBoolean(response.getString("success"))) {

                            JSONArray ja = response.getJSONArray("issues");
                            if (ja.length() > 0) {

                                arr = filterArray(ja);
                                if (arr.size() > 0) {
                                    progressBar.setVisibility(View.GONE);
                                    parentrecycle_adapter=new Parentrecycle_Adapter(getActivity(),arr);
                                    parentRecycle.scrollToPosition(arr.size()-1);
                                    parentRecycle.setAdapter(parentrecycle_adapter);
                        /*        progressBar.setVisibility(View.GONE);
                                empty.setVisibility(View.GONE);
                                refresh.setVisibility(View.GONE);
                                nodata.setVisibility(View.GONE);*/


                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    lottie.setVisibility(View.VISIBLE);
                                    nodata.setVisibility(View.VISIBLE);
                                    Toast.makeText(getActivity(),"no medical issue available",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                progressBar.setVisibility(View.GONE);
                                lottie.setVisibility(View.VISIBLE);
                                nodata.setVisibility(View.VISIBLE);
                                Toast.makeText(getActivity(),"no medical issue available",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            lottie.setVisibility(View.VISIBLE);
                            nodata.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "No medical issue available", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {

                        progressBar.setVisibility(View.GONE);
                        retry.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    progressBar.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);

                    Toast.makeText(getActivity(), "check your network connectivity", Toast.LENGTH_SHORT).show();
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
            retry.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "fail to load", Toast.LENGTH_SHORT).show();

        }

    }



    public List filterArray(JSONArray jsonArray) throws JSONException {
        outer=new ArrayList<>();

        for(int i=0;i<jsonArray.length();i++) {

            JSONObject j = jsonArray.getJSONObject(i);
            //  System.out.println("hbhai"+j.toString());
            List<String> inner = new ArrayList<>();
            JSONArray k=j.getJSONArray("prescription");

            inner.add(j.getString("issuetitle"));
            inner.add(j.getString("date"));
            inner.add(j.getString("issueid"));
            if(k.length()>0) {
                JSONObject kk = k.getJSONObject(k.length() - 1);
                inner.add(kk.getString("date"));
            }
            else{
                inner.add("");
            }

            outer.add(inner);

        }



        return outer;
    }


    public JSONArray toJsonArray(List<List> arr){

        JSONArray obj = new JSONArray();
        try {
            for(int i = 0; i < arr.size(); i++) {

                JSONObject list1 = new JSONObject();
                list1.put("val1",arr.get(i).get(0));
                list1.put("val2",arr.get(i).get(1));
                obj.put(list1);
            }

        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return obj;
    }

}