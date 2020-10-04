package com.example.foodie;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

//constants to pass the values
public class ViewProduct extends AppCompatActivity {
    public static final String PRODUCT_ID = "";
    public static final String PRODUCT_NAME = "";
    public static final String PRICE = "";

    //view objects
    EditText editID,editName,editPrice;
    Button btnAdd,btnDelete;
    private DatabaseReference databaseProduct;
//a list to store all the data
    List<ProductData> productData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        //getting reference
        databaseProduct = FirebaseDatabase.getInstance().getReference("ProductData");

        //getting views
        editID = (EditText)findViewById(R.id.editID);
        editName = (EditText)findViewById(R.id.editName);
        editPrice = (EditText)findViewById(R.id.editPrice);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(ViewProduct.this,AddingProduct.class);
                startActivity(intent);
                //addProduct();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        //Intent intent = new Intent(ViewProduct.this, DeleteProduct.class);
                        //startActivity(intent);
//                        deleteProduct(productID);
                    }
                });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        ProductData[] productData = new ProductData[]{
                new ProductData("12345","Pizza","Rs.600"),
                new ProductData("12345","Pizza","Rs.600"),
                new ProductData("12345","Pizza","Rs.600"),

        };

        ProductAdapter productAdapter = new ProductAdapter(productData,ViewProduct.this);
        recyclerView.setAdapter(productAdapter);

    }
    public void deleteProduct(ProductData productData){


    }

    public void addProduct(){
        String productID = editID.getText().toString().trim();
        String productName = editName.getText().toString().trim();
        String price = editPrice.getText().toString().trim();

        if(!TextUtils.isEmpty(productID)){
            String id = databaseProduct.push().getKey();

            //creating a new product
            ProductData product = new ProductData(productID,productName,price);

            databaseProduct.child(id).setValue(product);

            editID.setText("");
            editName.setText("");
            editPrice.setText("");
            Toast.makeText(this, "Product added", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Please enter an ID", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();




        //navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.appBottomNavigationBar);

        //set selected icon
        bottomNavigationView.setSelectedItemId(R.id.navbar_user_profile_icon);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ((item.getItemId())) {
                    case R.id.navbar_main_menu_icon:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.navbar_fooditems_icon:
                       Intent intent1 = new Intent(getApplicationContext(), ViewProduct.class);
                       startActivity(intent1);
                       overridePendingTransition(0,0);
                        return true;

                    case R.id.navbar_cart_icon:
//                        Intent intent2 = new Intent(getApplicationContext(), .class);
//                        startActivity(intent2);
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navbar_user_profile_icon:
                        Intent intent3 = new Intent(getApplicationContext(), user_profile.class);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;

                }


                return false;
            }
        });

        //navigation bar ends
    }
}
