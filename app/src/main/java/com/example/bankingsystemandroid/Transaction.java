package com.example.bankingsystemandroid;

import java.util.Date;

public class Transaction {
    Date transactionDate;
    int Amount;
    String typeofTrnasaction;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public int getAmount() {
        return Amount;
    }

    public String getTypeofTrnasaction() {
        return typeofTrnasaction;
    }

    public Transaction(Date transactionDate, int amount, String typeofTrnasaction) {
        this.transactionDate = transactionDate;
        Amount = amount;
        this.typeofTrnasaction = typeofTrnasaction;
    }
}
