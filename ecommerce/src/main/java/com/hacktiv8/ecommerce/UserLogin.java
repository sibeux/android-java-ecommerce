package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLogin extends AppCompatActivity {

    TextView signUp, userEmail, userPassword;
    String email, password;
    Button login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        signUp = findViewById(R.id.signUp);
        userEmail = findViewById(R.id.editTextEmail);
        userPassword = findViewById(R.id.editTextPassword);

        login = findViewById(R.id.cirLoginButton);

        progressDialog = new ProgressDialog(this);

        login.setOnClickListener(view -> {
            email = userEmail.getText().toString();
            password = userPassword.getText().toString();

            if ((email.replaceAll(" ","").length()==0)){
                userEmail.setError("Email harus diisi!");
            } else if ((password.replaceAll(" ","").length()==0)){
                userPassword.setError("Password harus diisi!");
            } else{
                progressDialog.setMessage("Logging in...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                new Handler().postDelayed(this::validasiData,1000);
            }
        });

        /*
        * kalo mau ganti warna, pake cara ini bisa
        * */
//        String first = getColoredSpanned("Don't have an account?", "#00a5ff");
//        String last = getColoredSpanned("Sign Up", "#F48181");
//
//        signUp.setText(Html.fromHtml(first+" "+last));

        SpannableString ss = new SpannableString(signUp.getText().toString());

        // creating clickable span to be implemented as a link
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            public void onClick(View widget) {
                Intent intent = new Intent(UserLogin.this, UserRegister.class);
                startActivity(intent);
            }
        };

        // setting the part of string to be act as a link
        ss.setSpan(clickableSpan1, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#F48181")), 23, 30,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        signUp.setText(ss);
        signUp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    void validasiData(){
        if(email.equals("") || password.equals("")){
            progressDialog.dismiss();
            Toast.makeText(UserLogin.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            readData();
        }
    }

    void readData(){
        AndroidNetworking.post("https://sibeux.my.id/database/hacktiv_ecommerce/UserLogin.php")
                .addBodyParameter("email",""+email)
                .addBodyParameter("password",""+password)
                .setTag("User Login")
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
                                Toast.makeText(UserLogin.this, "Login Success", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(UserLogin.this,StaffPage.class);
//                                startActivity(intent);
                            } else{
                                Toast.makeText(UserLogin.this, ""+pesan, Toast.LENGTH_SHORT).show();
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

    private String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }
}