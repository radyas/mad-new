package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";

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
            Toast.makeText(this,"Signed In",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Sign in Failed",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,sign_up.class));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        //navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.appBottomNavigationBar);

        //set selected icon
        bottomNavigationView.setSelectedItemId(R.id.navbar_main_menu_icon);

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
//                        Intent intent1 = new Intent(getApplicationContext(), .class);
//                        startActivity(intent1);
//                        overridePendingTransition(0,0);
                        return true;

                    case R.id.navbar_cart_icon:
//                        Intent intent2 = new Intent(getApplicationContext(), .class);
//                        startActivity(intent2);
//                        overridePendingTransition(0,0);
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

    }
}