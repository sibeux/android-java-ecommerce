package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ClothGenderPage extends AppCompatActivity implements View.OnClickListener {

    CardView male,female;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloath_gender_page);

        male = findViewById(R.id.maleCard);
        male.setOnClickListener(this);

        female = findViewById(R.id.femaleCard);
        female.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.maleCard){
            Intent intent = new Intent(this, CLothCategoryPage.class);
            Bundle data = getIntent().getExtras();
            type = data.getString("type");
            intent.putExtra("gender","Male");
            intent.putExtra("type",type);
            startActivity(intent);
        } else if (view.getId() == R.id.femaleCard){
            Intent intent = new Intent(this, CLothCategoryPage.class);
            Bundle data = getIntent().getExtras();
            type = data.getString("type");
            intent.putExtra("gender","Female");
            intent.putExtra("type",type);
            startActivity(intent);
        }
    }
}