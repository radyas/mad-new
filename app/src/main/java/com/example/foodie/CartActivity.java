package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity
{

    private RecyclerView rv_cartList;
    private ArrayList<Cart> cartList = new ArrayList<>();
    private CartAdapter adapter;
    private ProgressDialog dialog;
    private TextView tv_total;
    private Button checkout;
    private double totalPriceToBePaid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        //navigation bar begins
        BottomNavigationView bottomNavigationView = findViewById(R.id.appBottomNavigationBar);

        //set selected icon
        bottomNavigationView.setSelectedItemId(R.id.navbar_cart_icon);

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


        dialog = new ProgressDialog(CartActivity.this);
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);


        if(FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Toast.makeText(this, "No Valid user found!", Toast.LENGTH_SHORT).show();
            return;
        }
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        adapter = new CartAdapter(CartActivity.this,cartList,dialog,userId);

        checkout = findViewById(R.id.checkout);
        tv_total = findViewById(R.id.tv_total);
        rv_cartList = findViewById(R.id.rv_cartList);
        rv_cartList.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        rv_cartList.setAdapter(adapter);


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(totalPriceToBePaid > 0)
                {
                    dialog.show();
                    long paymentId = System.currentTimeMillis();

                    DatabaseReference reference1 = FirebaseDatabase
                            .getInstance()
                            .getReference("Payments")
                            .child(String.valueOf(userId))
                            .child(String.valueOf(paymentId));

                    Checkout checkout = new Checkout();
                    checkout.setPrice(totalPriceToBePaid);
                    checkout.setId(paymentId);
                    reference1.setValue(checkout, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref)
                        {
                            if(error != null)
                            {
                                Toast.makeText(CartActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                            else
                            {
                                //delete cart
                                DatabaseReference reference = FirebaseDatabase
                                        .getInstance()
                                        .getReference("Cart")
                                        .child(String.valueOf(userId));

                                reference.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref)
                                    {
                                        if(error != null)
                                        {
                                            Toast.makeText(CartActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                        else
                                        {
                                            Intent intent = new Intent(CartActivity.this,MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }
                    });


                }
                else
                {
                    Toast.makeText(CartActivity.this, "No items in the cart to checkout", Toast.LENGTH_SHORT).show();
                }


            }
        });


        //addToCart(userId,"Club Sandwich","https://d1ralsognjng37.cloudfront.net/157087bb-8911-4813-9dc3-3e6d37d569c9.jpeg",250,1);
        getAllCart(userId);


    }
    public void addToCart(String userId,String name,String link,double price,int quantity)
    {
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("Cart").child(String.valueOf(userId));


        Cart cart = new Cart();
        cart.setName(name);
        cart.setPrice(price);
        cart.setLink(link);
        cart.setQuantity(quantity);

        long id = System.currentTimeMillis();
        cart.setId(id);

        reference.child(String.valueOf(cart.getId())).setValue(cart, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref)
            {
                if(error != null)
                {
                    Toast.makeText(CartActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CartActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    public void getAllCart(String userId)
    {
        dialog.show();
        totalPriceToBePaid = 0;
        cartList.clear();

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference("Cart").child(String.valueOf(userId));
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if(snapshot.exists())
                {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren())
                    {
                        Cart cart = dataSnapshot.getValue(Cart.class);
                        totalPriceToBePaid += (cart.getPrice() * cart.getQuantity());
                        tv_total.setText("Total - " + totalPriceToBePaid + " LKR");
                        cartList.add(cart);
                        adapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    Toast.makeText(CartActivity.this, "No items in the cart", Toast.LENGTH_SHORT).show();
                    tv_total.setText("");
                    totalPriceToBePaid = 0;
                    adapter.notifyDataSetChanged();
                }

                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                dialog.dismiss();
            }
        });
    }
}