package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class StaffPage extends AppCompatActivity {

    TextView welcome;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_page);

        welcome = findViewById(R.id.welcomeStaff);

        Bundle data = getIntent().getExtras();
        welcome.setText("Welcome, "+data.getString("username"));
    }
}