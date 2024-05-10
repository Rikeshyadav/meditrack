package com.example.trackhealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.TelephonyCallback;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Prescription_page extends AppCompatActivity {
    String issueid;

    RecyclerView medrec, testrec;
    String date_ = "";
    RelativeLayout layout;
    AppCompatButton addmed, addtest, upload;
    ProgressBar progressBar;
    EditText editpres;
    String mspin_ = "", aspin_ = "", espin_ = "", nspin_ = "", tstatus = "";
    TextView startdate, enddate;
    List<List> medfinal = new ArrayList<>();
    SharedPreferences sp;
    LinearLayoutManager l;
    Medrecadapter medrecadapter;
    Testrecadapter testrecadapter;
    List<List> testfinal = new ArrayList<>();


    ViewGroup root;
    String key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_page);
        issueid = getIntent().getStringExtra("issueid");
        medrec = findViewById(R.id.presmedrec);
        testrec = findViewById(R.id.prestestrec);
        testrecadapter = new Testrecadapter(Prescription_page.this, testfinal);
        addmed = findViewById(R.id.presmedadd);
        addtest = findViewById(R.id.prestestadd);
        layout = findViewById(R.id.preslayout);
        progressBar = findViewById(R.id.presprogress);
        sp = getSharedPreferences("issue", MODE_PRIVATE);
        root = (ViewGroup) getWindow().getDecorView().getRootView();
        upload = findViewById(R.id.uploadpres);
        l = new LinearLayoutManager(Prescription_page.this, LinearLayoutManager.VERTICAL, false);

        medrec.setLayoutManager(l);

        editpres = findViewById(R.id.presedit);

        medrecadapter = new Medrecadapter(Prescription_page.this, medfinal);
        medrec.setAdapter(medrecadapter);

        l = new LinearLayoutManager(Prescription_page.this, LinearLayoutManager.VERTICAL, false);
        testrec.setLayoutManager(l);
        testrec.setAdapter(testrecadapter);

key=getIntent().getStringExtra("key");
if(key==null || key.equals("")){
    key="";
}
else {
    if (getIntent().getStringExtra("key").equals("edit")) {
        upload.setText("Update Prescription");
        updatedata();
    }

}
        addmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createWindow();
            }


        });

        addtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createWindowtest();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String idno = sp.getString("idno", "");
                String note = editpres.getText().toString();
                System.out.println("medfinal" + medfinal);
                addmedfun(idno, issueid, note, convertToJsonArray(medfinal, "medicine"), convertToJsonArray(testfinal, "test"));

            }
        });

    }


    public void updatedata() {

        String temp = "https://demo-uw46.onrender.com/api/issue/getPrescriptions/" + sp.getString("idno", "") + "/" + sp.getString("issueid", "") + "/" + sp.getString("pid", "");
        try {

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.GET, temp, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {

                            JSONObject jsonobj = response.getJSONObject("prescription");
                            editpres.setText(jsonobj.getString("note"));
                            JSONArray jsonArraymed = jsonobj.getJSONArray("medicine");
                           medfinal=convertToListArray(jsonArraymed,"medicine");
                            medrecadapter=new Medrecadapter(Prescription_page.this,medfinal);
                            medrec.setAdapter(medrecadapter);
                            JSONArray jsonArraytest = jsonobj.getJSONArray("test");
                            testfinal=convertToListArray(jsonArraytest,"test");
                            testrecadapter=new Testrecadapter(Prescription_page.this,testfinal);
                            testrec.setAdapter(testrecadapter);
                            System.out.println("testarr"+testfinal);
                            //testrecadapter.notifyDataSetChanged();

                        }
                    } catch (JSONException e) {
                        //progressBar.setVisibility(View.GONE);
                        //retry.setVisibility(View.VISIBLE);
                        Toast.makeText(Prescription_page.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

        /*        progressBar.setVisibility(View.GONE);
                empty.setVisibility(View.GONE);
                nodata.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);*/
                    //progressBar.setVisibility(View.GONE);
                    //retry.setVisibility(View.VISIBLE);
                    Toast.makeText(Prescription_page.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(Prescription_page.this);
            RetryPolicy retryPolicy = new DefaultRetryPolicy(
                    30000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            );
            j.setRetryPolicy(retryPolicy);
            q.add(j);


        } catch (
                Exception e) {
 /*       progressBar.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        nodata.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);*/
            //   progressBar.setVisibility(View.GONE);
            // retry.setVisibility(View.VISIBLE);
            Toast.makeText(Prescription_page.this, "fail to load - " + e.toString(), Toast.LENGTH_SHORT).show();

        }

    }

    private void addmedfun(String idno, String issueid, String note, JSONArray medfinal, JSONArray testfinal) {


        String temp = "";
if(key.equals("edit")){
    temp="https://demo-uw46.onrender.com/api/issue/updatePrescription";
}
else{
    temp="https://demo-uw46.onrender.com/api/issue/addPrescription";
}
        try {
            JSONObject jsonobj = new JSONObject();
            jsonobj.put("idno", idno);
            jsonobj.put("issueid", issueid);
            jsonobj.put("note", note);
            jsonobj.put("medicine", medfinal);
            jsonobj.put("test", testfinal);
            jsonobj.put("pid",sp.getString("pid",""));
            System.out.println("hman" + medfinal + "/n" + testfinal);

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.POST, temp, jsonobj, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //pb.setVisibility(View.GONE);
                    try {
                        if (Boolean.parseBoolean(response.getString("success"))) {
if(!key.equals("edit")) {
    Toast.makeText(Prescription_page.this, "Prescription uploaded", Toast.LENGTH_SHORT).show();
}
else{
    Toast.makeText(Prescription_page.this, "Prescription updated", Toast.LENGTH_SHORT).show();
}
                            Intent i = new Intent(Prescription_page.this, User_prescription_activity_page.class);
                            i.putExtra("activity", "prescription_page");
                            sp.edit().putString("isuploaded", "yes").apply();
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                          finish();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Prescription_page.this, "fail to load", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Prescription_page.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Prescription_page.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue q = Volley.newRequestQueue(Prescription_page.this);
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
            Toast.makeText(Prescription_page.this, "fail to load", Toast.LENGTH_SHORT).show();

        }

    }


    private void showDatePicker(TextView date) {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        String formattedDate = dateFormat.format(selectedDate.getTime());


                        date.setText(formattedDate);

                    }
                },
                currentYear,
                currentMonth,
                currentDay
        );

        datePickerDialog.show();
    }


    public void createWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.medicine_popupwindow, null);

       /* int width=ViewGroup.LayoutParams.MATCH_PARENT;
        int height=ViewGroup.LayoutParams.WRAP_CONTENT;

*/

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - 55;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        PopupWindow popupWindow = new PopupWindow(popup);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);


        AppCompatButton addmed2 = popup.findViewById(R.id.mpopupdesing_add);
        AppCompatButton delete = popup.findViewById(R.id.mpopupdesing_delete);
        Spinner morningspin, afterspin, evenspin, nightspin;
        AppCompatAutoCompleteTextView quan;
        CheckBox morningcheck, aftercheck, evencheck, nightcheck;
        TextView startdate, enddate;

        //textview
        AppCompatAutoCompleteTextView mname = popup.findViewById(R.id.mpopdesgin_mname);
        startdate = popup.findViewById(R.id.mpopdesginStartdate);
        enddate = popup.findViewById(R.id.mpopdesginEnddate);
        morningspin = popup.findViewById(R.id.mpopdesgin_morningspin);


        //spinner
        afterspin = popup.findViewById(R.id.mpopdesgin_afterspin);
        evenspin = popup.findViewById(R.id.mpopdesgin_eveningspin);
        nightspin = popup.findViewById(R.id.mpopdesgin_nightspin);

        //checkboxes
        morningcheck = popup.findViewById(R.id.mpopdesgin_morning);
        aftercheck = popup.findViewById(R.id.mpopdesgin_after);
        evencheck = popup.findViewById(R.id.mpopdesgin_evening);
        nightcheck = popup.findViewById(R.id.mpopdesgin_night);

        //quantity
        quan = popup.findViewById(R.id.mpopdesgin_quan);

        RegisterSpinnerApdater madapter, aadapter, eadapter, nadapter;
        String[] mornspin_ = {"Select", "After Breakfast", "Before Breakfast"};
        madapter = new RegisterSpinnerApdater(Prescription_page.this, R.layout.spinner1, mornspin_);
        morningspin.setAdapter(madapter);

        String[] afterspin_ = {"Select", "After Lunch", "Before Lunch"};
        aadapter = new RegisterSpinnerApdater(Prescription_page.this, R.layout.spinner1, afterspin_);
        afterspin.setAdapter(aadapter);


        String[] evenspin_ = {"Select", "After Snack", "Before Snack"};
        eadapter = new RegisterSpinnerApdater(Prescription_page.this, R.layout.spinner1, evenspin_);
        evenspin.setAdapter(eadapter);


        String[] night_ = {"Select", "After Dinner", "Before Dinner"};
        nadapter = new RegisterSpinnerApdater(Prescription_page.this, R.layout.spinner1, night_);
        nightspin.setAdapter(nadapter);

        String[] quan_ = {"1 Tablet/dosage", "1 Tablet/day", "Tablet/dosage", "Tablet/day", "1 Tablespoon/dosage", "Tablespoon/dosage", "ml/dosage", "2 Tablet/dosage", "2 Tablet/day", "3 Tablet/dosage", "3 Tablet/day", "2 Tablespoon/dosage"};
        nadapter = new RegisterSpinnerApdater(Prescription_page.this, R.layout.spinner_login, quan_);
        quan.setAdapter(nadapter);

        ImageView cross = popup.findViewById(R.id.popmedcross);


        String[] medicines = {"Paracetamol", "Ibuprofen", "Aspirin", "Loratadine", "Cetirizine", "Diphenhydramine", "Ranitidine", "Omeprazole",
                "Famotidine", "Metoclopramide", "Ondansetron", "Domperidone", "Esomeprazole", "Simethicone", "Lansoprazole", "Docusate", "Bisacodyl", "Senna", "Lactulose",
                "Psyllium", "Ferrous sulfate", "Folic acid", "Multivitamin", "Calcium carbonate", "Vitamin D", "Vitamin B12", "Vitamin C", "Zinc oxide", "Dextromethorphan", "Guaifenesin", "Phenylephrine", "Pseudoephedrine", "Acetaminophen",
                "Dextrose", "Glucose", "Insulin", "Epinephrine", "Albuterol", "Beclometasone", "Budesonide", "Fluticasone", "Montelukast", "Salbutamol", "Ceftriaxone", "Amoxicillin", "Ciprofloxacin", "Doxycycline", "Metronidazole", "Azithromycin", "Hydrochlorothiazide", "Amlodipine",
                "Losartan", "Metoprolol", "Lisinopril", "Atenolol", "Furosemide", "Warfarin", "Clopidogrel", "Rosuvastatin", "Atorvastatin", "Simvastatin", "Pravastatin", "Omeprazole", "Esomeprazole", "Rabeprazole", "Lansoprazole", "Famotidine", "Ranitidine", "Pantoprazole", "Sucralfate", "Ondansetron", "Metoclopramide", "Prochlorperazine",
                "Promethazine", "Dimenhydrinate", "Diazepam", "Lorazepam", "Clonazepam", "Alprazolam", "Zolpidem", "Temazepam", "Buspirone", "Methylphenidate", "Amphetamine", "Dextroamphetamine", "Modafinil", "Atomoxetine", "Venlafaxine", "Duloxetine", "Bupropion",
                "Tramadol", "Codeine", "Hydromorphone", "Morphine", "Oxycodone", "Fentanyl", "Methadone", "Naloxone", "Naltrexone", "Buprenorphine", "Nicotine", "Varenicline", "Bupropion", "Buspirone", "Mirtazapine", "Trazodone", "Doxepin", "Amitriptyline", "Nortriptyline", "Imipramine",
                "Desipramine",
                "Phenelzine",
                "Tranylcypromine",
                "Isocarboxazid",
                "Selegiline",
                "Lithium",
                "Valproate",
                "Carbamazepine",
                "Oxcarbazepine",
                "Lamotrigine",
                "Topiramate",
                "Gabapentin",
                "Pregabalin",
                "Levetiracetam",
                "Phenytoin",
                "Clobazam",
                "Clonazepam",
                "Lorazepam",
                "Diazepam",
                "Alprazolam",
                "Midazolam",
                "Haloperidol",
                "Chlorpromazine",
                "Risperidone",
                "Olanzapine",
                "Quetiapine",
                "Ziprasidone",
                "Aripiprazole",
                "Paliperidone",
                "Lurasidone",
                "Brexpiprazole",
                "Cariprazine",
                "Valproate",
                "Lamotrigine",
                "Lithium",
                "Aripiprazole",
                "Risperidone",
                "Quetiapine",
                "Olanzapine",
                "Ziprasidone",
                "Paliperidone",
                "Lurasidone",
                "Asenapine",
                "Iloperidone",
                "Clozapine",
                "Eszopiclone",
                "Zaleplon",
                "Zolpidem",
                "Ramelteon",
                "Trazodone",
                "Dothiepin",
                "Trimipramine",
                "Opipramol",
                "Citalopram",
                "Escitalopram",
                "Sertraline",
                "Fluoxetine",
                "Paroxetine",
                "Fluvoxamine",
                "Duloxetine",
                "Venlafaxine",
                "Milnacipran",
                "Levomilnacipran",
                "Imipramine",
                "Amitriptyline",
                "Nortriptyline",
                "Desipramine",
                "Clomipramine",
                "Doxepin",
                "Maprotiline",
                "Tianeptine",
                "Moclobemide",
                "Phenelzine",
                "Tranylcypromine",
                "Isocarboxazid",
                "Selegiline",
                "Bupropion",
                "Mirtazapine",
                "Trazodone",
                "Vilazodone",
                "Vortioxetine",
                "Agomelatine",
                "Nefazodone",
                "Venlafaxine",
                "Duloxetine",
                "Desvenlafaxine",
                "Levomilnacipran",
                "Milnacipran",
                "Amphetamine",
                "Dextroamphetamine",
                "Lisdexamfetamine",
                "Methylphenidate",
                "Dexmethylphenidate",
                "Atomoxetine",
                "Guanfacine",
                "Clonidine",
                "Modafinil",
                "Armodafinil",
                "Ranitidine",
                "Famotidine",
                "Cimetidine",
                "Esomeprazole",
                "Rabeprazole",
                "Lansoprazole",
                "Iron sucrose",
                "Iron dextran",
                "Ferrous gluconate",
                "Ferrous fumarate",
                "Ferrous ascorbate",
                "Cyanocobalamin (Vitamin B12)",
                "Epoetin alfa",
                "Darbepoetin alfa",
                "Filgrastim",
                "Lenograstim", "LIVOZEST SYRUP 200 ML", "Gatiquin Eye Drop", "Latanoprost Ophthalmic Eye Drop", "Cipla Brimodin Eye Drops", "Latanoprost Ophthalmic Eye Drop", "Acetaminophen", "Aspirin", " naproxen",
                "Sargramostim",
                "Hydroxocobalamin",
                "Iron polymaltose",
                "Multivitamin 200ml", "Zincovit Multivitamin & Multimineral Syrup", "Sunlife Multivitamin Syrup", "Optumevit Multivitamin Syrup", "Vitamin B complex", "Lyodiet Allopathic Lycopene Multiviatmin Multiminerals",
                "Cyanocobalamin (Vitamin B12)", "Liv 52 Syrup",
                "Epoetin alfa",
                "Ferrous sulfate",
                "Vitamin K",
                "Sucralfate",
                "Misoprostol",
                "Ondansetron",
                "Metoclopramide",
                "Prochlorperazine",
                "Dicyclomine",
                "Hyoscyamine",
                "Loperamide",
                "Promethazine",
                "Ondansetron",
                "Doxylamine",
                "Pyridoxine",
                "Metoclopramide",
                "Nifedipine",
                "Methyldopa",
                "Labetalol",
                "Hydralazine",
                "Isosorbide dinitrate",
                "Isosorbide mononitrate",
                "Nitroglycerin",
                "Digoxin",
                "Amiodarone",
                "Verapamil",
                "Diltiazem",
                "Amlodipine",
                "Losartan",
                "Valsartan",
                "Olmesartan",
                "Irbesartan",
                "Candesartan",
                "Furosemide",
                "Bumetanide",
                "Torsemide",
                "Hydrochlorothiazide",
                "Chlorthalidone",
                "Indapamide",
                "Metoprolol",
                "Atenolol",
                "Carvedilol",
                "Lisinopril",
                "Enalapril",
                "Benazepril",
                "Ramipril",
                "Captopril",
                "Trandolapril",
                "Quinapril",
                "Spironolactone",
                "Eplerenone",
                "Hydralazine",
                "Minoxidil",
                "Nitroprusside",
                "Albuterol",
                "Levalbuterol",
                "Salmeterol",
                "Formoterol",
                "Ipratropium",
                "Tiotropium",
                "Montelukast",
                "Zafirlukast",
                "Zileuton",
                "Theophylline",
                "Beclometasone",
                "Budesonide",
                "Fluticasone",
                "Mometasone",
                "Prednisone",
                "Prednisolone",
                "Methylprednisolone",
                "Hydrocortisone",
                "Dexamethasone",
                "Cromolyn",
                "Nedocromil",
                "Leukotriene inhibitors",
                "Oral contraceptives",
                "Norethindrone",
                "Levonorgestrel",
                "Drospirenone",
                "Ethinyl estradiol",
                "Norgestimate",
                "Desogestrel",
                "Medroxyprogesterone",
                "Progesterone",
                "Etonogestrel",
                "Implanon",
                "Mifepristone",
                "Misoprostol",
                "Leuprolide",
                "Nafarelin",
                "Histrelin",
                "Goserelin",
                "Cetrorelix",
                "Degarelix",
                "Clomiphene",
                "Menotropins",
                "Gonadotropin-releasing hormone",
                "Follitropin",
                "Estradiol",
                "Progesterone",
                "Human chorionic gonadotropin",
                "Ergonovine",
                "Methylergonovine",
                "Oxytocin",
                "Dinoprostone",
                "Carboprost",
                "Methotrexate",
                "Misoprostol",
                "Mifepristone",
                "Levonorgestrel",
                "Ulipristal",
                "Pegvisomant",
                "Octreotide",
                "Lanreotide",
                "Pasireotide",
                "Bromocriptine",
                "Cabergoline",
                "Pramipexole",
                "Ropinirole",
                "Rotigotine",
                "Apomorphine",
                "Sumatriptan",
                "Rizatriptan",
                "Zolmitriptan",
                "Eletriptan",
                "Naratriptan",
                "Frovatriptan",
                "Almotriptan",
                "Dihydroergotamine",
                "Ergotamine",
                "Acetaminophen",
                "Ibuprofen",
                "Naproxen",
                "Aspirin",
                "Acetylsalicylic acid",
                "Diclofenac",
                "Celecoxib",
                "Meloxicam",
                "Indomethacin",
                "Ketorolac",
                "Oxycodone",
                "Hydrocodone",
                "Codeine",
                "Tramadol",
                "Morphine",
                "Fentanyl",
                "Oxymorphone",
                "Hydromorphone",
                "Meperidine",
                "Propofol",
                "Etomidate",
                "Ketamine",
                "Droperidol",
                "Haloperidol",
                "Midazolam",
                "Diazepam",
                "Lorazepam",
                "Clonazepam",
                "Alprazolam",
                "Chlordiazepoxide",
                "Ondansetron",
                "Prochlorperazine",
                "Promethazine",
                "Meclizine",
                "Scopolamine",
                "Dimenhydrinate",
                "Doxylamine",
                "Ginger",
                "Cyclizine",
                "Trimethobenzamide"
        };


        nadapter = new RegisterSpinnerApdater(Prescription_page.this, R.layout.spinner_login, medicines);
        mname.setAdapter(nadapter);

        startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(startdate);
                startdate.setText(date_);
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(enddate);
            }
        });


        morningcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (morningcheck.isChecked()) {
                    morningspin.setVisibility(View.VISIBLE);

                }
                if (!morningcheck.isChecked()) {
                    morningspin.setVisibility(View.GONE);
                }

            }
        });


        aftercheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (aftercheck.isChecked()) {
                    afterspin.setVisibility(View.VISIBLE);
                } else {
                    afterspin.setVisibility(View.GONE);
                }

            }
        });

        evencheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (evencheck.isChecked()) {
                    evenspin.setVisibility(View.VISIBLE);
                } else {
                    evenspin.setVisibility(View.GONE);
                }

            }
        });
        nightcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (nightcheck.isChecked()) {
                    nightspin.setVisibility(View.VISIBLE);
                } else {
                    nightspin.setVisibility(View.GONE);
                }

            }
        });

        morningspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selection = (String) adapterView.getItemAtPosition(i);
                if (selection.equals("Select")) {
                    mspin_ = "Select";
                } else if (selection.equals("After Breakfast")) {
                    mspin_ = "After Breakfast";
                } else {
                    mspin_ = "Before Breakfast";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        afterspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selection = (String) adapterView.getItemAtPosition(i);
                if (selection.equals("Select")) {
                    aspin_ = "Select";
                } else if (selection.equals("After Lunch")) {
                    aspin_ = "After Lunch";
                } else {
                    aspin_ = "Before Lunch";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        evenspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selection = (String) adapterView.getItemAtPosition(i);
                if (selection.equals("Select")) {
                    espin_ = "Select";
                } else if (selection.equals("After Snack")) {
                    espin_ = "After Snack";
                } else {
                    espin_ = "Before Snack";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        nightspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selection = (String) adapterView.getItemAtPosition(i);
                if (selection.equals("Select")) {
                    nspin_ = "Select";
                } else if (selection.equals("After Dinner")) {
                    nspin_ = "After Dinner";
                } else {
                    nspin_ = "Before Dinner";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        addmed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mname.getText().toString().equals("")) {
                    Toast.makeText(Prescription_page.this, "empty medicine name", Toast.LENGTH_SHORT).show();
                } else if (morningcheck.isChecked() && mspin_.equals("Select")) {

                    Toast.makeText(Prescription_page.this, "Please select morning dosage", Toast.LENGTH_SHORT).show();

                } else if (aftercheck.isChecked() && aspin_.equals("Select")) {
                    Toast.makeText(Prescription_page.this, "Please select afternoon dosage", Toast.LENGTH_SHORT).show();

                } else if (evencheck.isChecked() && espin_.equals("Select")) {

                    Toast.makeText(Prescription_page.this, "Please select evening dosage", Toast.LENGTH_SHORT).show();

                } else if (nightcheck.isChecked() && nspin_.equals("Select")) {
                    Toast.makeText(Prescription_page.this, "Please select night dosage", Toast.LENGTH_SHORT).show();
                } else if (startdate.getText().toString().equals("")) {
                    Toast.makeText(Prescription_page.this, "empty start date", Toast.LENGTH_SHORT).show();
                } else if (enddate.getText().toString().equals("")) {
                    Toast.makeText(Prescription_page.this, "empty end date", Toast.LENGTH_SHORT).show();
                } else if (quan.getText().toString().equals("")) {
                    Toast.makeText(Prescription_page.this, "empty quantity", Toast.LENGTH_SHORT).show();
                } else {
                    List<String> innerm = new ArrayList<>();
                    innerm.add(mname.getText().toString());
                    innerm.add(mspin_);
                    innerm.add(aspin_);
                    innerm.add(espin_);
                    innerm.add(nspin_);
                    innerm.add(startdate.getText().toString());
                    innerm.add(enddate.getText().toString());
                    innerm.add(quan.getText().toString());
                    medfinal.add(innerm);
                    medrecadapter.notifyDataSetChanged();
                    Toast.makeText(Prescription_page.this, "medicine added", Toast.LENGTH_SHORT).show();
                    System.out.println("jkd" + medfinal);
                    popupWindow.dismiss();
                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim(root);
            }
        });

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        layout.post(new Runnable() {
            @Override
            public void run() {

                applyDim(root, 0.5f);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });
    }


    public void createWindowtest() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popup = inflater.inflate(R.layout.testprespopup, null);

       /* int width=ViewGroup.LayoutParams.MATCH_PARENT;
        int height=ViewGroup.LayoutParams.WRAP_CONTENT;

*/

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - 55;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        PopupWindow popupWindow = new PopupWindow(popup);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        AppCompatAutoCompleteTextView test = popup.findViewById(R.id.testdesgin_testname);
        AppCompatButton addtest2 = popup.findViewById(R.id.testdesgin_addtest);
        AppCompatButton deletetest2 = popup.findViewById(R.id.testdesgin_deletetest);
        Spinner status = popup.findViewById(R.id.testdesgin_pendingspin);
        String[] statusstring = {"Select", "Pending", "Done"};
        RegisterSpinnerApdater statusadapter = new RegisterSpinnerApdater(Prescription_page.this, R.layout.spinner1, statusstring);
        status.setAdapter(statusadapter);

        status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selection = (String) adapterView.getItemAtPosition(i);
                if (selection.equals("Select")) {
                    tstatus = "Select";
                } else if (selection.equals("Pending")) {
                    tstatus = "Pending";
                } else {
                    tstatus = "Done";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        addtest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (test.getText().toString().equals("")) {
                    Toast.makeText(Prescription_page.this, "empty test name", Toast.LENGTH_SHORT).show();
                } else if (tstatus.equals("Select")) {
                    Toast.makeText(Prescription_page.this, "select test status", Toast.LENGTH_SHORT).show();
                } else {

                    List<String> innertest = new ArrayList<>();
                    innertest.add(test.getText().toString());
                    innertest.add(tstatus);
                    testfinal.add(innertest);
                    testrecadapter.notifyDataSetChanged();
                    popupWindow.dismiss();
                    Toast.makeText(Prescription_page.this, "Test added", Toast.LENGTH_SHORT).show();


                }
            }
        });

        ImageView cross = popup.findViewById(R.id.poptestcross);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        String[] test_ = {
                "Complete Blood Count (CBC)",
                "Basic Metabolic Panel (BMP)",
                "Comprehensive Metabolic Panel (CMP)",
                "Liver Function Tests (LFTs)",
                "Kidney Function Tests (KFTs)",
                "Thyroid Function Tests (TFTs)",
                "Lipid Profile",
                "Urinalysis (UA)",
                "Electrocardiogram (ECG or EKG)",
                "Echocardiogram",
                "Chest X-ray",
                "Computed Tomography (CT) scan",
                "Magnetic Resonance Imaging (MRI) scan",
                "Positron Emission Tomography (PET) scan",
                "Ultrasound",
                "Bone Density Test (DEXA scan)",
                "Mammogram",
                "Pap Smear",
                "Colonoscopy",
                "Endoscopy",
                "Biopsy",
                "Electroencephalogram (EEG)",
                "Electromyography (EMG)",
                "Nerve Conduction Studies (NCS)",
                "Sleep Study (Polysomnography)",
                "Lumbar Puncture (Spinal Tap)",
                "Arthroscopy",
                "Cardiac Catheterization",
                "Holter Monitor",
                "Tilt Table Test",
                "Glucose Test",
                "Hemoglobin A1c (HbA1c) Test",
                "Oral Glucose Tolerance Test (OGTT)",
                "Fasting Blood Sugar (FBS) Test",
                "Random Blood Sugar (RBS) Test",
                "Urine Glucose Test",
                "Urine Ketone Test",
                "Blood Urea Nitrogen (BUN) Test",
                "Creatinine Test",
                "Uric Acid Test",
                "Urine Protein Test",
                "Urine Creatinine Test",
                "Urine Calcium Test",
                "Calcium Blood Test",
                "Potassium Blood Test",
                "Sodium Blood Test",
                "Chloride Blood Test",
                "Phosphorus Blood Test",
                "Magnesium Blood Test",
                "C-Reactive Protein (CRP) Test",
                "Erythrocyte Sedimentation Rate (ESR) Test",
                "Rheumatoid Factor (RF) Test",
                "Antinuclear Antibody (ANA) Test",
                "Complement Tests",
                "Hepatitis Panel",
                "HIV Test",
                "Syphilis Test",
                "Herpes Simplex Virus (HSV) Test",
                "Human Papillomavirus (HPV) Test",
                "Rubella Test",
                "Toxoplasmosis Test",
                "Cytomegalovirus (CMV) Test",
                "Varicella-Zoster Virus (VZV) Test",
                "Epstein-Barr Virus (EBV) Test",
                "Influenza Test",
                "Strep Throat Test",
                "Mononucleosis Test",
                "Tuberculosis (TB) Test",
                "Mantoux Test (Tuberculin Skin Test)",
                "QuantiFERON-TB Gold Test",
                "Sputum Culture",
                "Stool Culture",
                "Blood Culture",
                "Throat Culture",
                "Urine Culture",
                "Chlamydia Test",
                "Gonorrhea Test",
                "Trichomoniasis Test",
                "Chlamydia and Gonorrhea Tests",
                "Culture for Sexually Transmitted Infections (STIs)",
                "Pregnancy Test",
                "Ovulation Test",
                "Progesterone Test",
                "Estradiol Test",
                "Follicle-Stimulating Hormone (FSH) Test",
                "Luteinizing Hormone (LH) Test",
                "Testosterone Test",
                "Prolactin Test",
                "Thyroid-Stimulating Hormone (TSH) Test",
                "Free Thyroxine (FT4) Test",
                "Total Thyroxine (T4) Test",
                "Free Triiodothyronine (FT3) Test",
                "Total Triiodothyronine (T3) Test",
                "Thyroid Antibody Tests",
                "Vitamin B12 Test",
                "Folate Test",
                "Iron Test",
                "Total Iron-Binding Capacity (TIBC) Test",
                "Ferritin Test",
                "Transferrin Test",
                "Vitamin D Test",
                "Vitamin A Test",
                "Vitamin E Test",
                "Vitamin K Test",
                "Vitamin C Test",
                "Vitamin B1 Test",
                "Vitamin B2 Test",
                "Vitamin B3 Test",
                "Vitamin B5 Test",
                "Vitamin B6 Test",
                "Vitamin B7 Test",
                "Vitamin B9 Test",
                "Carotene Test",
                "Alpha-Carotene Test",
                "Beta-Carotene Test",
                "Lutein Test",
                "Zeaxanthin Test",
                "Cryptoxanthin Test",
                "Lycopene Test",
                "Electrolyte Panel",
                "Arterial Blood Gas (ABG) Test",
                "Thyroid Imaging",
                "Thyroid Ultrasound",
                "Thyroid Scan",
                "Thyroid Biopsy",
                "Pulmonary Function Tests (PFTs)",
                "Forced Vital Capacity (FVC) Test",
                "Forced Expiratory Volume in 1 Second (FEV1) Test",
                "Peak Expiratory Flow (PEF) Test",
                "Diffusing Capacity of the Lungs for Carbon Monoxide (DLCO) Test",
                "Spirometry",
                "Lung Volume Tests",
                "Bronchoscopy",
                "Plethysmography",
                "Fractional Exhaled Nitric Oxide (FeNO) Test",
                "Body Mass Index (BMI) Test",
                "Waist Circumference Test",
                "Waist-to-Hip Ratio (WHR) Test",
                "Skinfold Thickness Test",
                "Bioelectrical Impedance Analysis (BIA)",
                "Dual-Energy X-ray Absorptiometry (DEXA) Scan",
                "Hydrostatic Weighing",
                "Air Displacement Plethysmography (ADP) Test",
                "Calipers Test",
                "Ultrasonography",
                "Echocardiography",
                "Magnetic Resonance Imaging (MRI) Angiography",
                "Computed Tomography (CT) Angiography",
                "Nuclear Medicine Tests",
                "Positron Emission Tomography (PET) Scan",
                "Single-Photon Emission Computed Tomography (SPECT) Scan",
                "Bone Scan",
                "Thyroid Scan",
                "Magnetic Resonance Imaging (MRI) of the Head",
                "Magnetic Resonance Angiography (MRA)",
                "Magnetic Resonance Cholangiopancreatography (MRCP)",
                "Magnetic Resonance Enterography (MRE)"

        };
        RegisterSpinnerApdater nadapter = new RegisterSpinnerApdater(Prescription_page.this, R.layout.spinner1, test_);
        test.setAdapter(nadapter);


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                clearDim(root);
            }
        });

        layout.post(new Runnable() {
            @Override
            public void run() {

                applyDim(root, 0.5f);
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });
    }

    public void applyDim(@NonNull ViewGroup parent, float dimAmount) {
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

    public static JSONArray convertToJsonArray(List<List> listList, String key) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < listList.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                if (key.equals("medicine")) {
                    System.out.println("medinside" + listList.get(i));
                    jsonObject.put("name", listList.get(i).get(0).toString());

                    JSONObject dobj = new JSONObject();
                    dobj.put("morning", listList.get(i).get(1));
                    dobj.put("afternoon", listList.get(i).get(2));
                    dobj.put("evening", listList.get(i).get(3));
                    dobj.put("night", listList.get(i).get(4));

                    jsonObject.put("dosage", dobj);

                    JSONObject durobj = new JSONObject();
                    durobj.put("start", listList.get(i).get(5));
                    durobj.put("end", listList.get(i).get(6));

                    jsonObject.put("duration", durobj);
                    jsonObject.put("quantity", listList.get(i).get(7));
                    jsonArray.put(jsonObject);
                } else if (key.equals("test")) {
                    JSONObject tobj = new JSONObject();
                    tobj.put("name", listList.get(i).get(0));
                    tobj.put("status", listList.get(i).get(1));
                    jsonArray.put(tobj);
                }


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        //  System.out.println("finaljson" + jsonArray);
        return jsonArray;
    }


    public static List<List> convertToListArray(JSONArray jsonArray, String key) {
        List<List> listList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                List<Object> innerList = new ArrayList<>();
                if (key.equals("medicine")) {
                    innerList.add(jsonObject.getString("name"));

                    JSONObject dosageObj = jsonObject.getJSONObject("dosage");
                    innerList.add(dosageObj.get("morning"));
                    innerList.add(dosageObj.get("afternoon"));
                    innerList.add(dosageObj.get("evening"));
                    innerList.add(dosageObj.get("night"));

                    JSONObject durationObj = jsonObject.getJSONObject("duration");
                    innerList.add(durationObj.get("start"));
                    innerList.add(durationObj.get("end"));

                    innerList.add(jsonObject.get("quantity"));
                } else if (key.equals("test")) {
                    innerList.add(jsonObject.get("name"));
                    innerList.add(jsonObject.get("status"));
                }
                listList.add(innerList);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return listList;
    }
}
