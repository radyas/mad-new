package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodie.Models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_profile extends AppCompatActivity {
    EditText mEmail, mPhone;
    TextView mName;
    private ProgressDialog dialog;
    Button updateBtn, logoutBtn;
    User activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.userprofile_email);
        mPhone = findViewById(R.id.userprofile_phone);
        updateBtn = findViewById(R.id.button);
        logoutBtn = findViewById(R.id.button2);

        //navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.appBottomNavigationBar);

        //set selected icon
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
        dialog = new ProgressDialog(user_profile.this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.show();


        final FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String userID = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User").child(userID);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mName.setText(user.getName());
                mEmail.setText(user.getEmail());
                mPhone.setText((user.getPhone().equals("null")) ? "" : user.getPhone());
                activeUser = user;
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mPhone.getText().toString().trim();
                if (!phone.equals("")){
                    activeUser.setPhone(phone);
                    myRef.setValue(activeUser);
                }

            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                auth.signOut();
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();



    }

}