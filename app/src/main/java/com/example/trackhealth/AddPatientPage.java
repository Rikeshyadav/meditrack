package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class AddPatientPage extends AppCompatActivity {
TextView name,dob,gender;
AppCompatButton add;
SharedPreferences sp;
String doctorph="",patientph="";
    AppCompatButton consult;
String dname="",dclinic="",specification="",qualification="",issue="";
//ImageView search;
AppCompatAutoCompleteTextView ph;
RelativeLayout layout;
ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient_page);
        add=findViewById(R.id.search_add_patient);
        ph=findViewById(R.id.addPatient_searchBar);
        name=findViewById(R.id.spatient_name2);
        pb=findViewById(R.id.spb);
        dob=findViewById(R.id.spatient_dob2);
        gender=findViewById(R.id.spatient_gender2);
        sp=getSharedPreferences("user", Context.MODE_PRIVATE);
      //  search=findViewById(R.id.search_but_add);
        doctorph=sp.getString("phone","");
        dname=sp.getString("name","");
        specification=sp.getString("speciality","");
        qualification=sp.getString("qualification","");
        dclinic=sp.getString("clinic_name","");

        patientph=ph.getText().toString().trim();
        pb.setVisibility(View.GONE);
        layout=findViewById(R.id.slayout);
        layout.setVisibility(View.GONE);



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

        RegisterSpinnerApdater nadapter = new RegisterSpinnerApdater(AddPatientPage.this, R.layout.spinner_login, quan_);
        ph.setAdapter(nadapter);





    /*  search.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              pb.setVisibility(View.VISIBLE);
              layout.setVisibility(View.GONE);
              String phone=ph.getText().toString().trim();
              if(!phone.equals("") && phone.length()==10){
                  searchPatient(phone);
              }
              else{
                  Toast.makeText(getApplicationContext(),"invalid number",Toast.LENGTH_SHORT).show();
                  pb.setVisibility(View.GONE);
                  layout.setVisibility(View.GONE);
              }
          }
      });
*/
/*      add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
String phone=ph.getText().toString().trim();

if(!phone.equals("")) {
    updateDoctor(phone);
}else{
    Toast.makeText(getApplicationContext(),"empty phone number",Toast.LENGTH_SHORT).show();
}

          }
      });*/


      consult=findViewById(R.id.connectdoc);
      consult.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String ss=(String) getIntent().getStringExtra("phone");
               if (ss==null) ss="";
              if(!ss.equals("") ) {
                  if(!ph.getText().toString().equals("")) {
                      updateDoctor(getIntent().getStringExtra("phone"), ph.getText().toString());
                  }
                  else{
                      Toast.makeText(AddPatientPage.this,"empty issue",Toast.LENGTH_SHORT).show();
                  }
              }
              else{
                  Toast.makeText(AddPatientPage.this,"invalid phone no",Toast.LENGTH_SHORT).show();
              }
          }
      });

    }


  /*  public void searchPatient(String ph){
            String temp = "https://demo-uw46.onrender.com/api/doctor/getDetails";
        try {
            //  pb.setVisibility(View.VISIBLE);
            HashMap<String, String> jsonobj = new HashMap<>();
            jsonobj.put("phone", ph);
            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, new JSONObject(jsonobj), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            pb.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            name.setText(response.getString("username"));
                            *//*email.setText(response.getString("email"));
                            phone.setText(ph);
                            address.setText(response.getString("address"));
                            if(doctororpatient.equals("Doctor")) {
                                hosparent.setVisibility(View.VISIBLE);
                                hospital.setText(response.getString("speciality"));
                            }
*//*
                            name.setText(response.getString("username"));
                            dob.setText(response.getString("dob"));
                            gender.setText(response.getString("gender"));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Doctor doesn't exists",Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                            layout.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        layout.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(AddPatientPage.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            pb.setVisibility(View.GONE);
            layout.setVisibility(View.GONE);
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            //       pb.setVisibility(View.GONE);

        }

    }

*/


    public void updateDoctor(String ph,String issue){ //doctor no.
        pb.setVisibility(View.VISIBLE);
        String temp = "https://demo-uw46.onrender.com/api/doctor/addDoctor/"+doctorph;  //patient no.
        try {

            JSONObject jj=new JSONObject();
            jj.put("phone",ph);
            jj.put("issue",issue);
            jj.put("pending","true");

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp,jj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {

                            String phone=sp.getString("phone","").trim();
                            if(!phone.equals("")) {
                                  updatePatient(ph,issue);
                                Toast.makeText(getApplicationContext(),"sent request to Doctor",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"invalid user",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Doctor already added",Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(AddPatientPage.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            pb.setVisibility(View.GONE);
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            //       pb.setVisibility(View.GONE);

        }

    }


    public void updatePatient(String ph,String issue){  //ph is patient contact
        pb.setVisibility(View.VISIBLE);
        String photosign_=sp.getString("photosign","");
        String temp = "https://demo-uw46.onrender.com/api/patient/addPatient/"+ph;
        try {
            JSONObject jj=new JSONObject();
            jj.put("username",dname);
            jj.put("issue",issue);
            jj.put("sign","");
            jj.put("phone",sp.getString("phone",""));
            jj.put("pending","true");
            jj.put("address","");
            jj.put("dob",sp.getString("dob",""));
            jj.put("state",sp.getString("state",""));
            jj.put("city",sp.getString("city",""));
            jj.put("photo",sp.getString("photo",""));
            jj.put("email",sp.getString("email",""));
            jj.put("gender",sp.getString("gender",""));

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp,jj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (Boolean.parseBoolean(response.getString("success"))) {
                            pb.setVisibility(View.GONE);
                            consult.setText("Request sent");
                            consult.setBackgroundColor(getColor(R.color.hint2));
                            consult.setTextColor(getColor(R.color.black));
                            consult.setFocusable(false);
                            Toast.makeText(getApplicationContext(),"added successfully",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Doctor already added",Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                            deleteDoctor(ph,sp.getString("phone",""),"delete","");
                        }
                    } catch (JSONException e) {
                        deleteDoctor(ph,sp.getString("phone",""),"delete","");
                        Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pb.setVisibility(View.GONE);
                    deleteDoctor(ph,sp.getString("phone",""),"delete","");
                    Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(AddPatientPage.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
            pb.setVisibility(View.GONE);
            deleteDoctor(sp.getString("phone",""),ph,"delete","");
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();


        }

    }

    public void deleteDoctor(String doctor,String patient,String pending,String issue){
        String temp = "https://demo-uw46.onrender.com/api/doctor/updatepending/"+patient;

        try {
            JSONObject jj=new JSONObject();

      /*      jj.put("username",sp.getString("name",""));
            jj.put("dob",sp.getString("dob",""));
            jj.put("gender",sp.getString("gender",""));
            jj.put("email",sp.getString("email",""));
            jj.put("address",sp.getString("address",""));
            jj.put("photo",sp.getString("photo",""));
            jj.put("state",sp.getString("state",""));
            jj.put("city",sp.getString("city",""));*/
            jj.put("phone",doctor);
         /*   jj.put("pending",pending);
            jj.put("issue",issue);
            jj.put("clinic_name",dclinic);
            jj.put("speciality",specification);
            jj.put("qualification",qualification);
            jj.put("sign",sp.getString("sign",""));
            jj.put("about",sp.getString("about",""));
*/

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp,jj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {
                            Toast.makeText(AddPatientPage.this, "try again",Toast.LENGTH_SHORT).show();
                            add.setText("add");
                        }
                    } catch (JSONException e) {
                        //Toast.makeText(getApplicationContext(), "error"+e, Toast.LENGTH_SHORT).show();

                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(AddPatientPage.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {



        }

    }

}

