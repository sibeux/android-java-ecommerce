package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button logAdmin, logStaff, logUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logAdmin = findViewById(R.id.logAdmin);
        logAdmin.setOnClickListener(this);

        logStaff = findViewById(R.id.logStaff);
        logStaff.setOnClickListener(this);

        logUser = findViewById(R.id.logUser);
        logUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.logAdmin){
            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
        } else if(view.getId() == R.id.logStaff){
            Intent intent = new Intent(this, StaffLogin.class);
            startActivity(intent);
        } else if(view.getId() == R.id.logUser){
            Intent intent = new Intent(this, UserLogin.class);
            startActivity(intent);
        }
    }
}