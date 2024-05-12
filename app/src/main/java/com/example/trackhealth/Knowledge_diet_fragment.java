package com.example.trackhealth;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class Knowledge_diet_fragment extends Fragment {

    List<dataModel_exerciseGyan> dataModels_diet;
    myadapterfor_list adapter_diet;

    RecyclerView recycler_Diet;
    SearchView searchView_diet;
    dataModel_exerciseGyan data_diet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_knowledge_diet_fragment, container, false);
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.heartdiat, "", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.dietpictures, "Dietary Principles For Heart Patients.", ScaleTypes.CENTER_INSIDE));
        //   imageList.add(new SlideModel(, "And people do that.",ScaleTypes.CENTER_CROP));
        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

        recycler_Diet=view.findViewById(R.id.recycview_diet);
        searchView_diet=view.findViewById(R.id.search_diet);
        searchView_diet.setBackgroundColor(getResources().getColor(R.color.white));
        int textColor = getResources().getColor(R.color.black);
        EditText editText =searchView_diet.findViewById(androidx.appcompat.R.id.search_src_text);
        editText.setTextColor(textColor);
        editText.setHintTextColor(textColor);
        ImageView closeButton =searchView_diet.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setColorFilter(textColor);
        ImageView searchIcon = searchView_diet.findViewById(androidx.appcompat.R.id.search_button);
        searchIcon.setColorFilter(textColor);
        searchView_diet.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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




        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3, LinearLayoutManager.HORIZONTAL,false);
        recycler_Diet.setLayoutManager(gridLayoutManager);
        String description0 = getString(R.string.date);
        String description1 = getString(R.string.information1);
        String description2 = getString(R.string.information2);
        String description3 = getString(R.string.information3);
        String description4 = getString(R.string.information4);
        String description5 = getString(R.string.information5);

        dataModels_diet=new ArrayList<>();
        data_diet=new dataModel_exerciseGyan("7 Best Exercises for Diabetes","hjkjdek","fact",R.drawable.yoga);
        dataModels_diet.add( data_diet);
        data_diet=new dataModel_exerciseGyan(" Heart-healthy diet: 4 steps to prevent heart disease",description2,"fact",R.drawable.yoga);
        dataModels_diet.add( data_diet); data_diet=new dataModel_exerciseGyan(" Best Exercises for Diabetes","hjkjdek","fact",R.drawable.yoga);
        dataModels_diet.add( data_diet);
        data_diet=new dataModel_exerciseGyan(" Best Exercises for Diabetes","hjkjdek","fact",R.drawable.yoga);
        dataModels_diet.add( data_diet);
        data_diet=new dataModel_exerciseGyan(" Best Exercises for Diabetes","hjkjdek","fact",R.drawable.yoga);
        dataModels_diet.add( data_diet);


        adapter_diet=new myadapterfor_list(getActivity(),dataModels_diet);
        recycler_Diet.setAdapter(adapter_diet);
        return view;
    }



    private void searchList(String text){
        List<dataModel_exerciseGyan> dataSearchList=new ArrayList<>();
        for(dataModel_exerciseGyan data: dataModels_diet){
            if(data.getTitle().toLowerCase().contains(text.toLowerCase())){
                dataSearchList.add(data);
            }
        }
        if(dataSearchList.isEmpty()){
            Toast.makeText(getActivity(),"Not Found",Toast.LENGTH_SHORT).show();
        }
        adapter_diet.setSearchList(dataSearchList);


    }
}