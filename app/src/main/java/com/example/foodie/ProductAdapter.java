package com.example.foodie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ProductData[] productData;
    Context context;

    public ProductAdapter(ProductData[] productData,ViewProduct viewProduct) {
        this.productData = productData;
        this.context = viewProduct;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        final ProductData productDataList = productData[position];
        holder.productID.setText(productDataList.getProductID());
        holder.productName.setText(productDataList.getProductName());
        holder.price.setText(productDataList.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,productDataList.getProductName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productData.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView productID;
        TextView productName;
        TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productID = itemView.findViewById(R.id.productID);
            productName = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.price);
        }
    }
}
