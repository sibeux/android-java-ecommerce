package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ElectronicsCategoryPage extends AppCompatActivity implements View.OnClickListener{

    CardView smartphone, computer;
    String type;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronics_category_page);

        smartphone = findViewById(R.id.smartphoneCard);
        smartphone.setOnClickListener(this);

        computer = findViewById(R.id.computerCard);
        computer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, ProductView.class);
        Bundle data = getIntent().getExtras();
        type = data.getString("type");
        intent.putExtra("type",type);

        if (view.getId() == R.id.smartphoneCard){
            intent.putExtra("category","SMARTPHONE");
            startActivity(intent);
        } else if (view.getId() == R.id.computerCard){
            intent.putExtra("category","COMPUTER");
            startActivity(intent);
        }
    }
}