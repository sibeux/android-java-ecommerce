package com.hacktiv8.ecommerce.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hacktiv8.ecommerce.ProductView;
import com.hacktiv8.ecommerce.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    private List<ProductData> mProductList;

    public ProductAdapter(Context mContext, List<ProductData> mProductList){
        this.mContext = mContext;
        this.mProductList = mProductList;
    }

    public ProductAdapter(ProductView mContext, ArrayList<String> arrayName, ArrayList<String> arrayQuantity) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_row, parent, false);
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductData data = mProductList.get(position);

        holder.imageView.setImageResource(data.getProductImage());
        holder.mTitle.setText(data.getProductName());
        holder.mDesc.setText(data.getQuantity());

        holder.cardView.setOnClickListener(view -> {
            Toast.makeText(mContext, data.getProductName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView mTitle;
    TextView mDesc;
    CardView cardView;

    ViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.imageProduct);
        mTitle = itemView.findViewById(R.id.productTitle);
        mDesc = itemView.findViewById(R.id.productDesc);
        cardView = itemView.findViewById(R.id.cardview);
    }
}
