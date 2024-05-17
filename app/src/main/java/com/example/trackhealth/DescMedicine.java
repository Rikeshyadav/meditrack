package com.example.trackhealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DescMedicine extends AppCompatActivity {
    TextView name,desc,price,address;
    ImageView image,back;
    RatingBar ratingBar;
    AppCompatButton order,location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desc_medicine);
        name=findViewById(R.id.medextrasecmname);
        image=findViewById(R.id.workiMedicine);
        desc=findViewById(R.id.medextrasecmdesc);
        price=findViewById(R.id.medextrasecmprice);
        ratingBar=findViewById(R.id.medextrasecmrating);
        back=findViewById(R.id.medescback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        order=findViewById(R.id.medextrasecmorder);
        image.setImageResource(getIntent().getIntExtra("image",0));
        name.setText(getIntent().getStringExtra("mname"));
        desc.setText(getIntent().getStringExtra("mdesc"));
        price.setText(getIntent().getStringExtra("mprice"));
        ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("mrate")));
        location=findViewById(R.id.locaation1);
        address=findViewById(R.id.addd);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String source = "nirjuli";
                ArrayList<String> destinationsList = new ArrayList<>();
                destinationsList.add("Nerist Gate,Nirjuli");
                destinationsList.add("M/s ranka Medical,Nirjuli");
                destinationsList.add("Niti Vihar,Itanagar(A.P)");
                StringBuilder destinationsBuilder = new StringBuilder();
                for (String destination : destinationsList) {
                    destinationsBuilder.append(destination).append("\n");
                }
                address.setText(destinationsBuilder.toString());
                if (source.isEmpty()) {
                    Toast.makeText(DescMedicine.this, "Empty source", Toast.LENGTH_SHORT).show();
                    return;
                } else if (destinationsList.isEmpty()) {
                    Toast.makeText(getApplication(), "No destinations provided", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Address> sourceAddresses = null;
                List<Address> destinationAddresses = new ArrayList<>();
                try {
                    Geocoder geocoder = new Geocoder(DescMedicine.this);
                    sourceAddresses = geocoder.getFromLocationName(source, 1);
                    for (String destination : destinationsList) {
                        List<Address> addresses = geocoder.getFromLocationName(destination, 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            destinationAddresses.add(addresses.get(0));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (sourceAddresses == null || sourceAddresses.isEmpty() || destinationAddresses.isEmpty()) {
                    Toast.makeText(DescMedicine.this, "Unable to geocode addresses", Toast.LENGTH_SHORT).show();
                    return;
                }
                double sourceLatitude = sourceAddresses.get(0).getLatitude();
                double sourceLongitude = sourceAddresses.get(0).getLongitude();
                StringBuilder uriBuilder = new StringBuilder("https://www.google.com/maps/dir/?api=1&origin=");
                uriBuilder.append(sourceLatitude).append(",").append(sourceLongitude);

                for (Address destinationAddress : destinationAddresses) {
                    double destinationLatitude = destinationAddress.getLatitude();
                    double destinationLongitude = destinationAddress.getLongitude();
                    uriBuilder.append("&destination=").append(destinationLatitude).append(",").append(destinationLongitude);

                }
                Uri uri = Uri.parse(uriBuilder.toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.google.android.apps.maps");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DescMedicine.this,"Your Order is placed",Toast.LENGTH_SHORT).show();
            }
        });
        ArrayList<SlideModel> imageList = new ArrayList<>();
        imageList.add(new SlideModel(R.drawable.warfrin, "Warfarin sodium IP 5 Mg", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.tabletimg, "Relieves Acidity and heartburn", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.brain, "Get 10% on Brendname ", ScaleTypes.CENTER_INSIDE));
        imageList.add(new SlideModel(R.drawable.cancertablets, "", ScaleTypes.FIT));

        ImageSlider imageSlider = findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

    }
}