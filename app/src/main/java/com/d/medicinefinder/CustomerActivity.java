package com.d.medicinefinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.net.Uri;


public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Button btn = (Button) findViewById(R.id.button6);

        btn.setOnClickListener(new View.OnClickListener()

        {

            public void onClick (View v){

                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("0123456789"));
                startActivity(intent);
            }
        }
    );
    }

    public void search(View view) {
        Intent i = new Intent(CustomerActivity.this, MedicineSearch.class);
        startActivity(i);
    }

    public void nearbyShops(View view) {
        Intent i = new Intent(CustomerActivity.this, NearbyShops.class);
        startActivity(i);
    }



};