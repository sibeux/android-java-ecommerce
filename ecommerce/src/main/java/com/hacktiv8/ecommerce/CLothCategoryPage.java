package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CLothCategoryPage extends AppCompatActivity implements View.OnClickListener {

    CardView shirtCard, uniformCard, jacketCard, jeansCard;
    String type,gender;
    TextView titleCategory;
    Bundle data;
    LinearLayout header;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_category_page);

        shirtCard = findViewById(R.id.shirtsCard);
        shirtCard.setOnClickListener(this);

        uniformCard = findViewById(R.id.uniformCard);
        uniformCard.setOnClickListener(this);

        jacketCard = findViewById(R.id.jacketCard);
        jacketCard.setOnClickListener(this);

        jeansCard = findViewById(R.id.jeansCard);
        jeansCard.setOnClickListener(this);

        data = getIntent().getExtras();
        gender = data.getString("gender");

        titleCategory = findViewById(R.id.titleCategory);
        titleCategory.setText("Clothing & Acc - "+gender);

        header = findViewById(R.id.headerCategoryCloth);
        if (gender.equals("Female")){
            header.setBackground(ContextCompat.getDrawable(this, R.drawable.header_bg_female));
        }
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, ProductView.class);

        type = data.getString("type");
        intent.putExtra("type",type);
        intent.putExtra("gender",gender);

        if (view.getId() == R.id.shirtsCard){
            intent.putExtra("category","T-Shirts");
            startActivity(intent);
        } else if (view.getId() == R.id.uniformCard){
            intent.putExtra("category","Uniform");
            startActivity(intent);
        } else if (view.getId() == R.id.jacketCard){
            intent.putExtra("category","Jacket");
            startActivity(intent);
        } else if (view.getId() == R.id.jeansCard){
            intent.putExtra("category","Jeans");
            startActivity(intent);
        }

    }
}