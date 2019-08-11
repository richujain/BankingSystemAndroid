package com.example.bankingsystemandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    private List<Transaction> transactions;
    private Context context;

    public TransactionAdapter(List<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_cell,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       Transaction transaction=transactions.get(position);
        holder.tvFname.setText(transaction.);
        holder.tvLname.setText(student.getLastName()) ;


    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvDate;
        public TextView tvAmount;
        public TextView tvTransactionType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate=(TextView)itemView.findViewById(R.id.tvDate);
            tvAmount=(TextView)itemView.findViewById(R.id.tvAmount);
            tvTransactionType=itemView.findViewById(R.id.tvTransactionType);
        }
    }
}

