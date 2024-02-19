package com.example.trackhealth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class patient_edit_profile extends Fragment {

    ImageView imageView;
    FloatingActionButton fbut;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_patient_edit_profile, container, false);
        imageView=view.findViewById(R.id.prof_image);
        fbut=view.findViewById(R.id.uplodimage);
        fbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(patient_edit_profile.this)
                        .crop()//Crop image(Optional), Check Customization for more option
                        .cropSquare()
                        .compress(2024)//Final image size will be less than 2 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        return view;

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==ImagePicker.REQUEST_CODE && resultCode==getActivity().RESULT_OK){
            Uri uri=data.getData();
            imageView.setImageURI(uri);
        }else {
            Toast.makeText(getContext(), "Task Cancelled",Toast.LENGTH_SHORT).show();
        }
    }
}