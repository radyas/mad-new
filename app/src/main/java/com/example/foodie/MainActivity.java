package com.example.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);
    }


    public void  updateUI(FirebaseUser account){
        if(account != null){
            Toast.makeText(this,"Signed In",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Sign in Failed",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,sign_up.class));
        }
    }
}