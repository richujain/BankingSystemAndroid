package com.example.bankingsystemandroid;

public class BankStatementHolder {
    private String statement;

    public BankStatementHolder() {
    }

    public BankStatementHolder(String statement) {
        this.statement = statement;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
