package com.example.mcsprojectakhir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Vector;

public class TransactionDataHistoryAdapter extends RecyclerView.Adapter<TransactionDataHistoryAdapter.MyViewHolder> {
    private Context ctx;
    Vector<TransactionHistoryData> transactions;

    TransactionHistoryData object_trData;
    String trId;

    int userId;
    int productId;
    String trDate;


    public TransactionDataHistoryAdapter(Context ctx, int userId) {
        this.ctx = ctx;
        this.userId = userId;
    }

    public void setTransactions(Vector<TransactionHistoryData> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.transaction_history_component, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        final TransactionHistoryData td = transactions.get(position);

        trId = transactions.get(position).getTrId();
        trDate = transactions.get(position).getTrDate();

        productId = Integer.parseInt(transactions.get(position).getTrProductId());
        String productname = HomeForm.PDV.get(productId).getProductName();
        int productPrice = HomeForm.PDV.get(productId).getProductPrice();

        holder.trProductNameTV.setText(productname);

        holder.trProductPriceTV.setText("RP." + productPrice);
        holder.trTransactionDateTV.setText(transactions.get(position).getTrDate());
        holder.userId = userId;
        holder.posCardV = position;

        holder.buttontrDelete.setOnClickListener((view) -> {

            TransactionDataDBHelper TDdb = new TransactionDataDBHelper(ctx);

            TDdb.deleteTD(Integer.parseInt(td.getTrId()));
            transactions.remove(td);
            notifyDataSetChanged();

            Toast.makeText(ctx, "data deleted", Toast.LENGTH_SHORT).show();


        });

    }

    @Override
    public int getItemCount() {

        return transactions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        public Button buttontrDelete;
        TextView trProductNameTV, trProductPriceTV, trTransactionDateTV, emptyDataTV;

        int userId;
        int posCardV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trProductNameTV = itemView.findViewById(R.id.trProductNameTV);
            trProductPriceTV = itemView.findViewById(R.id.trProductPriceTV);
            trTransactionDateTV = itemView.findViewById(R.id.trTransactionDate);
            buttontrDelete = itemView.findViewById(R.id.buttonTrDelete);

        }
    }

}
