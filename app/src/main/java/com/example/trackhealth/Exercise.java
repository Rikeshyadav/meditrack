package com.example.trackhealth;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Exercise extends Fragment {


    List<dataModel_exerciseGyan> dataModels1;
    myadapterfor_list adapter;
    RecyclerView recyclerView1;
    List<postitem_exercisegyan> postitemList;

    Postadapter_exercisegyan postadapter;
    dataModel_exerciseGyan datafun;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_exercise, container, false);


        RecyclerView recyclerView=view.findViewById(R.id.recycleviewpost);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL));

        postitemList=new ArrayList<>();
        postitemList.add(new postitem_exercisegyan(R.drawable.img_2));
        postitemList.add(new postitem_exercisegyan(R.drawable.walking));
        postitemList.add(new postitem_exercisegyan(R.drawable.mediexercise));
        postitemList.add(new postitem_exercisegyan(R.drawable.erxercise_mental));
        postitemList.add(new postitem_exercisegyan(R.drawable.sitting));
        postitemList.add(new postitem_exercisegyan(R.drawable.img_1));
        postitemList.add(new postitem_exercisegyan(R.drawable.office_yoga));
        postitemList.add(new postitem_exercisegyan(R.drawable.yoga_benefits));
        postadapter=new Postadapter_exercisegyan(getActivity(),postitemList);
        recyclerView.setAdapter(postadapter);

        recyclerView1=view.findViewById(R.id.search_recy);

        SearchView searchView=view.findViewById(R.id.search_view);

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
        String description = "12 April";
        String description1 = "yoga is good";
        dataModels1=new ArrayList<>();
        datafun=new dataModel_exerciseGyan("Yoga and there benefits","Yoga is a mind and body practice that can build strength and flexibility. It may also help manage pain and reduce stress. ","fact",R.drawable.yoga);
        dataModels1.add(datafun);
        datafun=new dataModel_exerciseGyan("Yoga Or Gym: Which Is Better For Your Health?",description,"fact",R.drawable.ladies);
        dataModels1.add(datafun);
        datafun=new dataModel_exerciseGyan("Benefits of medication:fact and its steps...",description1,"31-03",R.drawable.meditation);
        dataModels1.add(datafun);
        datafun=new dataModel_exerciseGyan("The Top 10 Benefits of Regular Physical Activity",description1,"date",R.drawable.regual);
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