package com.example.foodie;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Cart> cartList;
    private ProgressDialog dialog;
    private String userId;


    public CartAdapter(Context context, ArrayList<Cart> cartList, ProgressDialog dialog,String userId)
    {
        this.cartList = cartList;
        this.context = context;
        this.dialog = dialog;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        if(cartList != null && cartList.size() > position)
        {
            final Cart cart = cartList.get(position);
//            Glide.with(context).load(cart.getLink()).into(holder.image);

            holder.name.setText(cart.getName());
            holder.price.setText("" + cart.getPrice() + " LKR");
            holder.quantity.setText("Qty - " + cart.getQuantity());

            holder.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int cQty = cart.getQuantity();
                    cart.setQuantity(++cQty);
                    UpdateQuantity(cart);


                }
            });

            holder.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int cQty = cart.getQuantity();
                    if(cQty > 1)
                    {
                        cart.setQuantity(--cQty);
                        UpdateQuantity(cart);
                    }
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    deleteItem(cart);
                }
            });

        }
    }

    private void UpdateQuantity(Cart cart)
    {
        dialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(String.valueOf(userId))
                .child(String.valueOf(cart.getId()));

        reference.child("quantity").setValue(cart.getQuantity(), new DatabaseReference.CompletionListener()
        {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref)
            {
                if(error != null)
                {
                    Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
                    if(context instanceof CartActivity)
                    {
                        CartActivity activity = (CartActivity) context;
                        activity.getAllCart(userId);
                    }
                }
                dialog.dismiss();
            }
        });
    }
    private void deleteItem(final Cart cart)
    {
        dialog.show();
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Cart")
                .child(String.valueOf(userId))
                .child(String.valueOf(cart.getId()));

        reference.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref)
            {
                if(error != null)
                {
                    Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                    if(context instanceof CartActivity)
                    {
                        CartActivity activity = (CartActivity) context;
                        activity.getAllCart(userId);
                    }
                }
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if(cartList != null)
        {
            return cartList.size();
        }
        else
        {
            return 0;
        }

    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image,plus,minus,delete;
        TextView name,price,quantity;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
