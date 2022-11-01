package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminLogin extends AppCompatActivity {

    TextView adminUsername,adminPassword;
    String username,password;
    Button login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        adminUsername = findViewById(R.id.adminUsername);
        adminPassword = findViewById(R.id.adminPassword);

        login = findViewById(R.id.adminLoginButton);

        progressDialog      = new ProgressDialog(this);

        login.setOnClickListener(view -> {

            username = adminUsername.getText().toString();
            password = adminPassword.getText().toString();

            if ((username.replaceAll(" ","").length()==0)){
                adminUsername.setError("Username harus diisi!");
            } else if ((password.replaceAll(" ","").length()==0)){
                adminPassword.setError("Password harus diisi!");
            } else{
                progressDialog.setMessage("Logging in...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                new Handler().postDelayed(this::validasiData,1000);
            }
        });
    }

    void validasiData(){
        if(username.equals("") || password.equals("")){
            progressDialog.dismiss();
            Toast.makeText(AdminLogin.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            readData();
        }
    }

    void readData(){
        AndroidNetworking.post("https://sibeux.my.id/database/hacktiv_ecommerce/AdminLogin.php")
                .addBodyParameter("username",""+username)
                .addBodyParameter("password",""+password)
                .setTag("Admin Login")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            if (status){
                                Toast.makeText(AdminLogin.this, "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AdminLogin.this,AdminPage.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(AdminLogin.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}