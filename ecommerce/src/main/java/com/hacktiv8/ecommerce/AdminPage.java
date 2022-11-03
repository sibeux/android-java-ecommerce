package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class AdminPage extends AppCompatActivity implements View.OnClickListener{

    Button addStaff,addStock;
    String usernameS,passwordS,confPasswordS;
    String type,gender,category,name,quantity;
    View mDialogView;
    ProgressDialog progressDialog;
    ArrayAdapter<CharSequence> adapter;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<CharSequence> adapterCategory,adapterCategoryElectronic;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        addStaff = findViewById(R.id.adminAddStaff);
        addStaff.setOnClickListener(this);

        addStock = findViewById(R.id.adminAddStock);
        addStock.setOnClickListener(this);
    }

    void validasiData(){
        if(usernameS.equals("") || passwordS.equals("") || confPasswordS.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }

    void kirimData(){
        AndroidNetworking.post("https://sibeux.my.id/database/hacktiv_ecommerce/AddStaff.php")
                .addBodyParameter("username",""+usernameS)
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
                            Toast.makeText(AdminPage.this, ""+pesan, Toast.LENGTH_SHORT).show();
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

    @SuppressLint("InflateParams")
    void setAddStock(){
        mDialogView = LayoutInflater.from(this).inflate(R.layout.add_stock_dialog,null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mDialogView).setTitle("ADD STOCK");

        AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        Button addButton = mDialogView.findViewById(R.id.dialogAddBtn);
        Button cancelButton = mDialogView.findViewById(R.id.dialogCancelBtn);
        TextView nameProduct = mDialogView.findViewById(R.id.nameProduct);
        TextView quantityProduct = mDialogView.findViewById(R.id.quantityProduct);

        // gender
        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(this,R.array.genderStock,R.layout.dropdown_item);
        AutoCompleteTextView autoCompleteTextViewGender = mDialogView.findViewById(R.id.genderStock);
        TextInputLayout spinnerGender = mDialogView.findViewById(R.id.spinnerGender);
        autoCompleteTextViewGender.setAdapter(adapterGender);
        autoCompleteTextViewGender.setOnItemClickListener((adapterView, view, i, l) -> {
            gender = autoCompleteTextViewGender.getText().toString();
            spinnerGender.setError("");
        });

        // category
        adapterCategory = ArrayAdapter.createFromResource(this,R.array.categoryStock,R.layout.dropdown_item);
        AutoCompleteTextView autoCompleteTextViewCategory = mDialogView.findViewById(R.id.categoryStock);
        TextInputLayout spinnerCategory = mDialogView.findViewById(R.id.spinnerCategory);
        autoCompleteTextViewCategory.setAdapter(adapterCategory);
        autoCompleteTextViewCategory.setOnItemClickListener((adapterView, view, i, l) -> {
            category = autoCompleteTextViewCategory.getText().toString();
            spinnerCategory.setError("");
        });

        // category electronic
        adapterCategoryElectronic = ArrayAdapter.createFromResource(this,R.array.categoryStockElectronics,R.layout.dropdown_item);
        @SuppressLint("CutPasteId") AutoCompleteTextView autoCompleteTextViewCategoryElectronic = mDialogView.findViewById(R.id.categoryStockElectro);
        @SuppressLint("CutPasteId") TextInputLayout spinnerCategoryElectronic = mDialogView.findViewById(R.id.spinnerCategoryElectro);
        autoCompleteTextViewCategoryElectronic.setAdapter(adapterCategoryElectronic);
        autoCompleteTextViewCategoryElectronic.setOnItemClickListener((adapterView, view, i, l) -> {
            category = autoCompleteTextViewCategoryElectronic.getText().toString();
            spinnerCategoryElectronic.setError("");
        });

        // type
        adapter = ArrayAdapter.createFromResource(this, R.array.typeStock,R.layout.dropdown_item);
        autoCompleteTextView = mDialogView.findViewById(R.id.typeStock);
        TextInputLayout spinnerType = mDialogView.findViewById(R.id.spinnerType);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) ->{
            spinnerType.setError("");

                if (adapterView.getItemAtPosition(i).toString().equals("Clothing & Acc")){
                    type = "Clothing & Acc";
                    spinnerGender.setVisibility(View.VISIBLE);
                    spinnerCategory.setVisibility(View.VISIBLE);
                    spinnerCategoryElectronic.setVisibility(View.GONE);
                } else if (adapterView.getItemAtPosition(i).toString().equals("Electronics")){
                    type = "Electronics";
                    spinnerGender.setVisibility(View.GONE);
                    spinnerCategory.setVisibility(View.GONE);
                    spinnerCategoryElectronic.setVisibility(View.VISIBLE);
                }
        });

        progressDialog = new ProgressDialog(this);

        addButton.setOnClickListener(view -> {
            name = nameProduct.getText().toString();
            quantity = quantityProduct.getText().toString();

            if(autoCompleteTextView.getText().toString().equals("")){
                spinnerType.setError("Please select type!");
            } else if (autoCompleteTextViewGender.getText().toString().equals("") &&
                    spinnerGender.getVisibility() == View.VISIBLE){
                spinnerGender.setError("Please select gender!");
            } else if (autoCompleteTextViewCategory.getText().toString().equals("") &&
                    spinnerCategory.getVisibility() == View.VISIBLE){
                spinnerCategory.setError("Please select category!");
            } else if (autoCompleteTextViewCategoryElectronic.getText().toString().equals("") &&
                    spinnerCategoryElectronic.getVisibility() == View.VISIBLE){
                spinnerCategoryElectronic.setError("Please select category!");
            } else if (name.replaceAll(" ","").length() == 0){
                nameProduct.setError("Please enter name product!");
            } else if (quantity.replaceAll(" ","").length() == 0){
                quantityProduct.setError("Please enter quantity");
            } else {
                alertDialog.dismiss();
                progressDialog.setMessage("Adding Stock...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                new Handler().postDelayed(this::validasiDataStock,1000);
            }
        });

        cancelButton.setOnClickListener(view12 -> {
            alertDialog.dismiss();

            Toast.makeText(AdminPage.this, "Cancelled", Toast.LENGTH_SHORT).show();
        });
    }

    void validasiDataStock(){
        if(type.equals("") || name.equals("") || quantity.equals("")){
            progressDialog.dismiss();
            Toast.makeText(this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            // Clothing & Acc
            if (type.equals("Clothing & Acc")){
                kirimDataStock();
            } else if (type.equals("Electronics")){
                kirimDataStockElectro();
            }
        }
    }

    void kirimDataStockElectro(){
        AndroidNetworking.post("https://sibeux.my.id/database/hacktiv_ecommerce/AddStockElectro.php")
                .addBodyParameter("name",""+name)
                .addBodyParameter("quantity",""+quantity)
                .addBodyParameter("type",""+type)
                .addBodyParameter("category",""+category)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            String pesan = response.getString("result");
                            Toast.makeText(AdminPage.this, "Success", Toast.LENGTH_SHORT).show();
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

    void kirimDataStock(){
        AndroidNetworking.post("https://sibeux.my.id/database/hacktiv_ecommerce/AddStock.php")
                .addBodyParameter("name",""+name)
                .addBodyParameter("quantity",""+quantity)
                .addBodyParameter("type",""+type)
                .addBodyParameter("gender",""+gender)
                .addBodyParameter("category",""+category)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            String pesan = response.getString("result");
                            Toast.makeText(AdminPage.this, "Success", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("InflateParams")
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.adminAddStaff){
            mDialogView = LayoutInflater.from(this).inflate(R.layout.add_staff_dialog,null);

            AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
            alertDialogBuilderUserInput.setView(mDialogView).setTitle("ADD STAFF");

            TextView username = mDialogView.findViewById(R.id.usernameStaff);
            TextView password = mDialogView.findViewById(R.id.dialogPasswEt);
            TextView confPassword = mDialogView.findViewById(R.id.dialogConfPasswEt);
            Button addButton = mDialogView.findViewById(R.id.dialogAddBtn);
            Button cancelButton = mDialogView.findViewById(R.id.dialogCancelBtn);

            AlertDialog alertDialog = alertDialogBuilderUserInput.create();
            alertDialog.setCanceledOnTouchOutside(true);
            alertDialog.show();

            progressDialog = new ProgressDialog(this);

            addButton.setOnClickListener(view1 -> {
                usernameS = username.getText().toString();
                passwordS = password.getText().toString();
                confPasswordS = confPassword.getText().toString();

                if ((usernameS.replaceAll(" ","").length() == 0)){
                    username.setError("Username must filled!");
                } else if ((passwordS.replaceAll(" ","").length() == 0)){
                    password.setError("Password must filled!");
                } else if ((confPasswordS.replaceAll(" ","").length() == 0)){
                    confPassword.setError("Password must filled!");
                } else{
                    if (!Objects.equals(passwordS, confPasswordS)){
                        confPassword.setError("Password doesn't match!");
                    } else{
                        alertDialog.dismiss();
                        progressDialog.setMessage("Adding Staff...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        new Handler().postDelayed(this::validasiData,1000);
                    }
                }
            });

            cancelButton.setOnClickListener(view12 -> {
                alertDialog.dismiss();

                Toast.makeText(AdminPage.this, "Cancelled", Toast.LENGTH_SHORT).show();
            });
        } else if (view.getId() == R.id.adminAddStock){
            setAddStock();
        }
    }
}