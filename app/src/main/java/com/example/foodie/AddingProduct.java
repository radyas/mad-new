package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
//import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddingProduct extends AppCompatActivity {

    EditText editID, editName, editPrice;
    Button btnAdd;
    private DatabaseReference databaseProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_product);

//getting views
        editID = (EditText) findViewById(R.id.editID);
        editName = (EditText) findViewById(R.id.editName);
        editPrice = (EditText) findViewById(R.id.editPrice);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        btnAdd = (Button) findViewById(R.id.adddata);

        //getting reference
        databaseProduct = FirebaseDatabase.getInstance().getReference("ProductData");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                addProduct();
            }
        });


    }


    public void addProduct() {
        String productID = editID.getText().toString().trim();
        String productName = editName.getText().toString().trim();
        String price = editPrice.getText().toString().trim();

        if (!TextUtils.isEmpty(productID)) {
            String id = databaseProduct.push().getKey();

            //creating a new product
            ProductData product = new ProductData(productID, productName, price);

            databaseProduct.child(id).setValue(product);

            editID.setText("");
            editName.setText("");
            editPrice.setText("");
            // Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
        } else {
            // Toast.makeText(this, "Please enter an ID", Toast.LENGTH_LONG).show();
        }


    }
}