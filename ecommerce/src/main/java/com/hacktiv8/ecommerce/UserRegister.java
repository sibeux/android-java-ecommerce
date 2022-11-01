package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import java.util.Objects;

public class UserRegister extends AppCompatActivity implements View.OnClickListener{

    TextView signIn;
    Button register;
    String nama, emailS, passwordS, confPasswordS;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        signIn = findViewById(R.id.signIn);
        register = findViewById(R.id.cirLoginButton);
        register.setOnClickListener(this);

        SpannableString ss = new SpannableString(signIn.getText().toString());

        // creating clickable span to be implemented as a link
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            public void onClick(View widget) {
                Intent intent = new Intent(UserRegister.this, UserLogin.class);
                startActivity(intent);
            }
        };

        // setting the part of string to be act as a link
        ss.setSpan(clickableSpan1, 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(Color.parseColor("#F48181")), 25, 32,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        signIn.setText(ss);
        signIn.setMovementMethod(LinkMovementMethod.getInstance());
    }

    void validasiData(){
        if(nama.equals("") || emailS.equals("") || confPasswordS.equals("") || passwordS.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }

    void kirimData(){
        AndroidNetworking.post("https://sibeux.my.id/database/hacktiv_ecommerce/UserRegister.php")
                .addBodyParameter("nama",""+nama)
                .addBodyParameter("email",""+emailS)
                .addBodyParameter("password",""+passwordS)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            String pesan = response.getString("result");
                            boolean status = response.getBoolean("status");
                            Toast.makeText(UserRegister.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            if (status){
                                Intent intent = new Intent(UserRegister.this, UserLogin.class);
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }

    void register(){

        TextView name = findViewById(R.id.editTextName);
        TextView email = findViewById(R.id.editTextEmail);
        TextView password = findViewById(R.id.editTextPassword);
        TextView confPassword = findViewById(R.id.editTextConfirmPassword);

        progressDialog = new ProgressDialog(this);

            nama = name.getText().toString();
            emailS = email.getText().toString();
            passwordS = password.getText().toString();
            confPasswordS = confPassword.getText().toString();

            if ((nama.replaceAll(" ","").length() == 0)){
                name.setError("Name must filled!");
            } else if ((emailS.replaceAll(" ","").length() == 0)){
                password.setError("Email must filled!");}
            else if ((passwordS.replaceAll(" ","").length() == 0)){
                password.setError("Password must filled!");
            } else if ((confPasswordS.replaceAll(" ","").length() == 0)){
                confPassword.setError("Password must filled!");
            } else{
                if (!Objects.equals(passwordS, confPasswordS)){
                    confPassword.setError("Password doesn't match!");
                } else{
                    progressDialog.setMessage("Register...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new Handler().postDelayed(this::validasiData,1000);
                }
            }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.cirLoginButton){
            register();
        }
    }
}