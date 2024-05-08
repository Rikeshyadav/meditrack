package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class DescMedicine extends AppCompatActivity {
    TextView name,desc,price;
    RatingBar ratingBar;
    AppCompatButton order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_medicine);
        name=findViewById(R.id.medextrasecmname);
        desc=findViewById(R.id.medextrasecmdesc);
        price=findViewById(R.id.medextrasecmprice);
        ratingBar=findViewById(R.id.medextrasecmrating);
        order=findViewById(R.id.medextrasecmorder);
        name.setText(getIntent().getStringExtra("mname"));
        desc.setText(getIntent().getStringExtra("mdesc"));
        price.setText(getIntent().getStringExtra("mprice"));
        ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("mrate")));

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DescMedicine.this,"Your Order is placed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}