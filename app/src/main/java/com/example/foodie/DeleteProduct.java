package com.example.foodie;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteProduct extends AppCompatActivity {
    private boolean deleteProduct(String productID) {
        //getting the reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("productData").child(productID);

        //removing
        dR.removeValue();

        //Toast.makeText("Product Deleted", Toast.LENGTH_LONG).show();

        return true;
    }
}
