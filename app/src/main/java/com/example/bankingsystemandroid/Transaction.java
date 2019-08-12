package com.example.bankingsystemandroid;

import java.util.Date;

public class Transaction {
    Date transactionDate;
    int Amount;
    String typeofTransaction;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public int getAmount() {
        return Amount;
    }

    public String getTypeofTrnasaction() {
        return typeofTransaction;
    }

    public Transaction(Date transactionDate, int amount, String typeofTrnasaction) {
        this.transactionDate = transactionDate;
        Amount = amount;
        this.typeofTransaction = typeofTrnasaction;
    }
}
