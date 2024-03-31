package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Exercisegyan_second2 extends AppCompatActivity {
    TextView desc, title;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercisegyan_second2);

        setContentView(R.layout.activity_exercisegyan_second2);
        imageView=findViewById(R.id.imageplacer);
        title=findViewById(R.id.title_name);
        desc=findViewById(R.id.Descr_detail);
        Bundle bundle=getIntent().getExtras();
        if(bundle !=null){
            desc.setText(bundle.getString("Desc"));
            imageView.setImageResource(bundle.getInt("image"));
            title.setText(bundle.getString("Title"));
        }
    }

}
