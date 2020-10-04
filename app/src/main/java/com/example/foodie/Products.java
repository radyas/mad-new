    package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodie.Models.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class Products extends AppCompatActivity {
        EditText mName, mDescription, mPrice;
        Button addProduct;
        ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        mName = findViewById(R.id.product_name);
        mDescription = findViewById(R.id.product_description);
        mPrice = findViewById(R.id.product_price);
        addProduct = findViewById(R.id.product_add_button);

        //navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.appBottomNavigationBar);

        //set selected icon
        bottomNavigationView.setSelectedItemId(R.id.navbar_fooditems_icon);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ((item.getItemId()))
                {
                    case R.id.navbar_main_menu_icon:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navbar_fooditems_icon:
                        Intent intent1 = new Intent(getApplicationContext(), Products.class);
                        startActivity(intent1);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navbar_cart_icon:
                        Intent intent2 = new Intent(getApplicationContext(), CartActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navbar_user_profile_icon:
                        Intent intent3 = new Intent(getApplicationContext(), user_profile.class);
                        startActivity(intent3);
                        overridePendingTransition(0,0);
                        return true;

                }


                return false;
            }
        });

        //navigation bar ends


        dialog = new ProgressDialog(Products.this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);


        final FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Products");

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String price = mPrice.getText().toString().trim();
                String description = mDescription.getText().toString().trim();

                Product product = new Product();
                product.setName(name);
                product.setPrice(price);
                product.setDescription(description);

                myRef.setValue(product);
                Toast.makeText(Products.this, "Product Added Successfully! " , Toast.LENGTH_SHORT).show();
            }
        });
    }
}