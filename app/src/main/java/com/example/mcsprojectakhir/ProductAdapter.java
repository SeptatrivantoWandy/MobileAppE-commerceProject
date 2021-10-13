package com.example.mcsprojectakhir;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;

import org.json.JSONObject;

import java.util.Vector;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";

    int userId;


    private Context ctx;
    private Vector<Product> products;

    public ProductAdapter(int userId, Context ctx) {
        this.userId = userId;
        this.ctx = ctx;
    }

    public void setProducts(Vector<Product> products) {
        this.products = products;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.product_component_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.productTV.setText(products.get(position).getProductName());
        holder.minTV.setText(products.get(position).getProductMinPlayer() + " players");
        holder.maxTV.setText(products.get(position).getProductMaxPlayer() + " players");
        holder.priceTV.setText("Rp." + products.get(position).getProductPrice() + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ctx, ProductDetailForm.class);


                intent.putExtra("id", Integer.parseInt(products.get(position).getProductId()));

                intent.putExtra("nama", products.get(position).getProductName());
                intent.putExtra("min", products.get(position).getProductMinPlayer());
                intent.putExtra("max", products.get(position).getProductMaxPlayer());
                intent.putExtra("harga", products.get(position).getProductPrice());
                intent.putExtra("userid", userId);



                ctx.startActivity(intent);
                ((StoreFormRecyclerView)ctx).finish();

            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productTV, minTV, maxTV, priceTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            productTV = itemView.findViewById(R.id.productNameTV);
            minTV = itemView.findViewById(R.id.productMinPlayerTV);
            maxTV = itemView.findViewById(R.id.productMaxPlayerTV);
            priceTV = itemView.findViewById(R.id.productPriceTV);

        }


    }

}
