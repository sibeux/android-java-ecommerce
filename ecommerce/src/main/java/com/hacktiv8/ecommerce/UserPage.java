package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserPage extends AppCompatActivity implements View.OnClickListener {

    CardView clothCard, electronicCard, bookCard, otherCard;
    TextView name;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        clothCard = findViewById(R.id.clothCard);
        clothCard.setOnClickListener(this);

        electronicCard = findViewById(R.id.electronicCard);
        electronicCard.setOnClickListener(this);

        bookCard = findViewById(R.id.bookCard);
        bookCard.setOnClickListener(this);

        otherCard = findViewById(R.id.otherCard);
        otherCard.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.clothCard){
            Intent intent = new Intent(this,ClothGenderPage.class);
            intent.putExtra("type","clothing_acc");
            startActivity(intent);
        } else if (view.getId() == R.id.electronicCard){
            Intent intent = new Intent(this,ElectronicsCategoryPage.class);
            intent.putExtra("type","electronics");
            startActivity(intent);
        } else if (view.getId() == R.id.bookCard){
            Intent intent = new Intent(this,ProductView.class);
            intent.putExtra("type","book");
            startActivity(intent);
        } else if (view.getId() == R.id.otherCard){
            Intent intent = new Intent(this,ProductView.class);
            intent.putExtra("type","other");
            startActivity(intent);
        }

    }
}