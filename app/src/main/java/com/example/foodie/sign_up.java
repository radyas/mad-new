package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodie.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {

    private FirebaseAuth auth;
    private static final String TAG = "TAG";

    Button mRegister, mLogin;
    EditText mEmail, mPassword, mName;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mName = findViewById(R.id.sign_up_name);
        mEmail = findViewById(R.id.sign_up_email);
        mPassword = findViewById(R.id.sign_up_password);
        mRegister = findViewById(R.id.sign_up_button1);
        mLogin = findViewById(R.id.sign_up_button2);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String name = mName.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    mName.setError("Name is empty.");
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is empty.");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is empty.");
                }

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(sign_up.this, "Verification mail sent.", Toast.LENGTH_SHORT).show();
                                    userID = auth.getCurrentUser().getUid();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("User");

                                    User user = new User();
                                    user.setEmail(email);
                                    user.setName(name);
                                    user.setUserID("user");

                                    myRef.child(userID).setValue(user);

                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "Email not sent. " + e.getMessage());
                                }
                            });
                        }
                        else{
                            Toast.makeText(sign_up.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                Toast.makeText(sign_up.this, "User Created", Toast.LENGTH_SHORT).show();




            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


    }

}
// test