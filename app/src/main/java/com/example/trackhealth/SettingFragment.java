package com.example.trackhealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SettingFragment extends Fragment {
TextView delete,setting;
String user;
SharedPreferences sp;
String phone;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_setting, container, false);

        delete=view.findViewById(R.id.delete_acc);
        sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
                setting=view.findViewById(R.id.setting_changeph);
        user=sp.getString("identity","");
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              openFragment(new delete_page(),user);
            }
        });

setting.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent i=new Intent(getActivity(),ChangePhone.class);
        startActivity(i);
    }
});
        return view;

    }

    public void openFragment(Fragment fragment,String user) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (user.equals("Doctor")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    else{
            fragmentManager.beginTransaction()
                    .replace(R.id.patient_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }

    }
}