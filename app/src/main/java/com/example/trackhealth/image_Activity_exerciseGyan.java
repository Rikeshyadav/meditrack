package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class image_Activity_exerciseGyan extends AppCompatActivity {
    ImageView imageVi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_exercise_gyan);
        imageVi=findViewById(R.id.image_pic);
        Bundle bundle=getIntent().getExtras();
        if(bundle !=null){
            // desc.setText(bundle.getString("Desc"));
            imageVi.setImageResource(bundle.getInt("image"));
            //  title.setText(bundle.getString("Title"));
        }


    }
}