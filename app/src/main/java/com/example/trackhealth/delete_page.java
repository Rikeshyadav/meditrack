package com.example.trackhealth;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

public class delete_page extends Fragment {
AppCompatButton confirm;
SharedPreferences sp;
TextView txt1;
EditText txtconfirm;
String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_delete_page, container, false);
        confirm=view.findViewById(R.id.delete_acc_confirm);
        sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        txt1=view.findViewById(R.id.delete_token);
        txtconfirm=view.findViewById(R.id.delete_token_write);
        token=sp.getString("phone","").trim()+"@"+sp.getString("identity","").trim();
        txt1.setText(token);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txttoken=txtconfirm.getText().toString().trim();
                if(txttoken.equals(token)){
                   setAlert();
                }
                else{
                    Toast.makeText(getActivity(),"text doesn't matched",Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }

    public void delete(String phone){
        String temp="";
if(sp.getString("identity","").equals("Doctor")) {
    temp = "https://demo-uw46.onrender.com/api/doctor/delete/" + phone;
}
else if(sp.getString("identity","").equals("Patient")) {
    temp = "https://demo-uw46.onrender.com/api/patient/delete/" + phone;
}
        try {

            JsonObjectRequest j = new JsonObjectRequest(Request.Method.PUT, temp,new JSONObject(), new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (Boolean.parseBoolean(response.getString("success"))) {

                            Toast.makeText(getActivity(),"your account is deleted",Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getActivity(),LoginActivity.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(getActivity(),"no user exist",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), "error"+e, Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(),error.toString(), Toast.LENGTH_SHORT).show();
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

            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();


        }



    }

    public void setAlert(){

        AlertDialog.Builder b=new AlertDialog.Builder(getActivity());
        b.setMessage("Do you want to delete your account?");
        b.setPositiveButton("yes",(DialogInterface.OnClickListener) (dialog, which)->{
            delete(sp.getString("phone",""));
            });
        b.setNegativeButton("no",(DialogInterface.OnClickListener) (dialog,which)->{

            dialog.cancel();
        });
        AlertDialog ad=b.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                ad.getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.black));
            }
        });

        ad.show();

    }

}