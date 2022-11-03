package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetail extends AppCompatActivity {

    TextView title,desc;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        title = findViewById(R.id.productTitle);
        desc = findViewById(R.id.productDesc);
        imageView = findViewById(R.id.imageProduct);

        Bundle data = getIntent().getExtras();
        title.setText(data.getString("name"));
        desc.setText(data.getString("quantity"));
        imageView.setImageResource(data.getInt("image"));
    }
}