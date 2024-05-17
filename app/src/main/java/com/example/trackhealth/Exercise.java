package com.example.trackhealth;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Exercise extends Fragment {


    List<dataModel_exerciseGyan> dataModels1;
    myadapterfor_list adapter;
    RecyclerView recyclerView1;
   // List<postitem_exercisegyan> postitemList;

    //Postadapter_exercisegyan postadapter;
    dataModel_exerciseGyan datafun;
    SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_exercise, container, false);
        Toolbar toolbar=view.findViewById(R.id.toolbar1);


      /*  RecyclerView recyclerView=view.findViewById(R.id.recycleviewpost);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));

        postitemList=new ArrayList<>();
        postitemList.add(new postitem_exercisegyan(R.drawable.erxercise_mental));
        postitemList.add(new postitem_exercisegyan(R.drawable.mediexercise));
        postitemList.add(new postitem_exercisegyan(R.drawable.compo_exercise));
        postitemList.add(new postitem_exercisegyan(R.drawable.office_yoga));
        postitemList.add(new postitem_exercisegyan(R.drawable.yoga_benefits));
        postadapter=new Postadapter_exercisegyan(getActivity(),postitemList);

        recyclerView.setAdapter(postadapter);*/

        recyclerView1=view.findViewById(R.id.search_recy);

        searchView=view.findViewById(R.id.search_view);
        searchView.setBackgroundColor(getResources().getColor(R.color.white));
        int textColor = getResources().getColor(R.color.black);
        EditText editText = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setTextColor(textColor);
        editText.setHintTextColor(textColor);
        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setColorFilter(textColor);
        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setColorFilter(textColor);

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
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),1);
        recyclerView1.setLayoutManager(gridLayoutManager);
        String description0 = getString(R.string.date);
        String description1 = getString(R.string.information1);
        String description2 = getString(R.string.information3);
        String description3 = getString(R.string.information4);
        String description4 = getString(R.string.information5);
        String description5 = getString(R.string.information5);

        dataModels1=new ArrayList<>();
        datafun=new dataModel_exerciseGyan("7 Best Exercises for Diabetes",description0,"fact",R.drawable.yoga);
        dataModels1.add(datafun);
        datafun=new dataModel_exerciseGyan("Yoga and it's Types",description1,"fact",R.drawable.ladies);
        dataModels1.add(datafun);
        datafun=new dataModel_exerciseGyan("The Top 10 Benefits of Regular Physical Activity",description2,"date",R.drawable.regual);
        dataModels1.add(datafun);
        datafun=new dataModel_exerciseGyan("Fitness and Exercise Guidelines for Heart Disease Patients: Improving Cardiovascular Health through Regular Physical Activity",description3,"date",R.drawable.heart_disease);
        dataModels1.add(datafun);
        datafun=new dataModel_exerciseGyan("How to Overcome Depression and Anxiety..",description5,"**",R.drawable.depression);
        dataModels1.add(datafun);

        adapter=new myadapterfor_list(getActivity(),dataModels1);
        recyclerView1.setAdapter(adapter);

        return view;
    }
    private void searchList(String text){
        List<dataModel_exerciseGyan> dataSearchList=new ArrayList<>();
        for(dataModel_exerciseGyan data: dataModels1){
            if(data.getTitle().toLowerCase().contains(text.toLowerCase())){
                dataSearchList.add(data);
            }
        }
        if(dataSearchList.isEmpty()){
            Toast.makeText(getActivity(),"Not Found",Toast.LENGTH_SHORT).show();
        }
        adapter.setSearchList(dataSearchList);


    }

}