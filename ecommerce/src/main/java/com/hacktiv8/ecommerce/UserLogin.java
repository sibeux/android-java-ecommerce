package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UserLogin extends AppCompatActivity {

    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        signUp = findViewById(R.id.signUp);

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

    private String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }
}