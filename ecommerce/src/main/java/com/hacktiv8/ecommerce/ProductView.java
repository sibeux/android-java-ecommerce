package com.hacktiv8.ecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hacktiv8.ecommerce.adapter.ProductAdapter;
import com.hacktiv8.ecommerce.adapter.ProductData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductView extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductData productData;
    ProgressDialog progressDialog;
    List<ProductData> productDataList = new ArrayList<>();
    String type,gender,category;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        recyclerView = findViewById(R.id.productRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ProductView.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        progressDialog = new ProgressDialog(this);

        Bundle data = getIntent().getExtras();
        type = data.getString("type");

        if (data.getString("type").equals("clothing_acc")){
            gender = data.getString("gender");
        }

        category = data.getString("category");

        scrollRefresh();

    }

    public void scrollRefresh(){
        progressDialog.setMessage("Loading Product...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Handler().postDelayed(this::getData,1000);
    }

    void getData(){
        AndroidNetworking.post("https://sibeux.my.id/database/hacktiv_ecommerce/GetData.php")
                .addBodyParameter("type",""+type)
                .addBodyParameter("gender",""+gender)
                .addBodyParameter("category",""+category)
                .setTag("Get Data")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try{
                            boolean status = response.getBoolean("status");
                            if(status){
                                JSONArray ja = response.getJSONArray("result");

                                for(int i = 0 ; i < ja.length() ; i++){
                                    JSONObject jo = ja.getJSONObject(i);

                                    if (jo.getString("type").equals("Clothing & Acc")){
                                        if (jo.getString("category").equals("T-Shirts")){
                                            productData = new ProductData(jo.getString("name"),"$"+jo.getString("quantity"),R.drawable.shirts);
                                        } else if (jo.getString("category").equals("Uniform")){
                                            productData = new ProductData(jo.getString("name"),"$"+jo.getString("quantity"),R.drawable.uniform2);
                                        } else if (jo.getString("category").equals("Jacket")){
                                            productData = new ProductData(jo.getString("name"),"$"+jo.getString("quantity"),R.drawable.jacket2);
                                        } else{
                                            productData = new ProductData(jo.getString("name"),"$"+jo.getString("quantity"),R.drawable.jeansprod);
                                        }
                                    } else if (jo.getString("type").equals("Electronics")){
                                        if (jo.getString("category").equals("SMARTPHONE")){
                                            productData = new ProductData(jo.getString("name"),"$"+jo.getString("quantity"),R.drawable.smartphone);
                                        } else if (jo.getString("category").equals("COMPUTER")){
                                            productData = new ProductData(jo.getString("name"),"$"+jo.getString("quantity"),R.drawable.computer);
                                        }
                                    } else if (jo.getString("type").equals("Books")){
                                        productData = new ProductData(jo.getString("name"),"$"+jo.getString("quantity"),R.drawable.books);
                                    } else if (jo.getString("type").equals("Others")){
                                        productData = new ProductData(jo.getString("name"),"$"+jo.getString("quantity"),R.drawable.others);
                                    }
                                    productDataList.add(productData);
                                }
                                ProductAdapter productAdapter = new ProductAdapter(ProductView.this,productDataList);
                                recyclerView.setAdapter(productAdapter);
                            }else{
                                Toast.makeText(ProductView.this, "Product not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}