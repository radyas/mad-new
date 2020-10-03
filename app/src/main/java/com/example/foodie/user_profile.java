package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class user_profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.appBottomNavigationBar);

        //set selected icons
        bottomNavigationView.setSelectedItemId(R.id.navbar_user_profile_icon);

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