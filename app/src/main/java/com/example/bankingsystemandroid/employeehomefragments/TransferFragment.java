package com.example.bankingsystemandroid.employeehomefragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bankingsystemandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class TransferFragment extends Fragment {
    private Button depositTitle,withdrawlTitle,transferTitle;
    private RelativeLayout depositLayout,withdrawlLayout,transferLayout;
    //Deposit
    EditText depositAccountNumber,depositAmout;
    Button depositButton;
    //withdrawl
    EditText withdrawlAccountNumber,withdrawAmount;
    Button withdrawlButton;
    //Transaction
    EditText beneficiaryAccountNumber,remitterAccountNumber,transferAmount;
    Button transferButton;
    //firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    //flag
    int flag = -1;
    String balance;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //return inflater.inflate(R.layout.fragment_transaction, null);

        View layout = inflater.inflate(R.layout.fragment_transaction, container, false);
        initTitleButton(layout);
        init(layout);
        setOnClick();




        return layout;
    }

    private void init(View view){
        depositAccountNumber = view.findViewById(R.id.depositAccountNumber);
        depositAmout = view.findViewById(R.id.depositAmount);
        depositButton = view.findViewById(R.id.depositButton);
        withdrawlAccountNumber = view.findViewById(R.id.withdrawlAccountNumber);
        withdrawAmount = view.findViewById(R.id.withdrawlAmount);
        withdrawlButton = view.findViewById(R.id.withdrawlButton);
        beneficiaryAccountNumber = view.findViewById(R.id.beneficiaryAccountNumber);
        remitterAccountNumber = view.findViewById(R.id.remitterAccountNumber);
        transferAmount = view.findViewById(R.id.transferAmount);
        transferButton = view.findViewById(R.id.transferButton);

    }

    private void setOnClick(){
        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deposit();
            }
        });
        withdrawlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //withdraw();
            }
        });
        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //transfer();
            }
        });
    }
    private void deposit(){
        if(depositAccountNumber.getText().toString().trim().length() == 0){
            depositAccountNumber.setError(getString(R.string.thisFieldShouldNotBeEmpty));
        }
        else if(depositAmout.getText().toString().trim().length() == 0){
            depositAmout.setError(getString(R.string.thisFieldShouldNotBeEmpty));
        }
        else {
            DatabaseReference myRef = database.getReference("bank");
            String accountType = getAccountType(depositAccountNumber.getText().toString().trim());
            if(!accountType.isEmpty()){
                if(accountType.isEmpty()){
                    depositAccountNumber.setError("Account Does Not Exists");
                }
                else if(accountType.equals("savings")){
                    myRef = database.getReference("bank").child("savings");
                    Double accountBalance = getAccountBalance(depositAccountNumber.getText().toString().trim(),accountType);
                    Double newBalance = Double.parseDouble(depositAmout.getText().toString()) + accountBalance;
                    myRef.child(depositAccountNumber.getText().toString().trim()).child("accountbalance").setValue(""+newBalance);
                    showAlert("Deposited Successfully. New Balance is "+newBalance,getContext());
                }
                else if(accountType.equals("current")){
                    myRef = database.getReference("bank").child("current");
                    Double accountBalance = getAccountBalance(depositAccountNumber.getText().toString().trim(),accountType);
                    Double newBalance = Double.parseDouble(depositAmout.getText().toString()) + accountBalance;
                    myRef.child(depositAccountNumber.getText().toString().trim()).child("accountbalance").setValue(""+newBalance);
                    showAlert("Deposited Successfully. New Balance is "+newBalance,getContext());
                }
                else{
                    depositAccountNumber.setError("Unknown Error");
                    showAlert("Unknown Error Occured.",getContext());
                }
            }
        }
    }
    private Double getAccountBalance(final String accountNumber, String accountType){
        DatabaseReference balanceRef = database.getReference("bank");
        balanceRef = database.getReference("bank").child(accountType).child(accountNumber);
        balance = "";
        balanceRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    balance = (String) dataSnapshot.child("accountbalance").getValue();

                }
                else{
                    showAlert("No customer record found associated with the account number "+ accountNumber,getContext());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return Double.parseDouble(balance);
    }
    private String getAccountType(String accountNumber){
        flag = -1;
        DatabaseReference myRef = database.getReference("bank").child("savings").child(accountNumber);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()){
                    flag = 0;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        if(flag == -1){
            myRef = database.getReference("bank").child("current").child(accountNumber);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if(dataSnapshot.exists()){
                        flag = 1;
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

        if(flag == 0){
            return "savings";
        }
        else if(flag == 1){
            return "current";
        }
        else{
            return "";
        }

    }
    private void initTitleButton(View view){
        depositTitle = view.findViewById(R.id.depositTitle);
        withdrawlTitle = view.findViewById(R.id.withdrawlTitle);
        transferTitle = view.findViewById(R.id.transferTitle);
        depositLayout = view.findViewById(R.id.depositLayout);
        withdrawlLayout = view.findViewById(R.id.withdrawlLayout);
        transferLayout = view.findViewById(R.id.transferLayout);
        setOnClickTitleButton();
    }
    private void setOnClickTitleButton(){
        depositTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depositLayout.setVisibility(View.VISIBLE);
                withdrawlLayout.setVisibility(View.INVISIBLE);
                transferLayout.setVisibility(View.INVISIBLE);
            }
        });
        withdrawlTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depositLayout.setVisibility(View.INVISIBLE);
                withdrawlLayout.setVisibility(View.VISIBLE);
                transferLayout.setVisibility(View.INVISIBLE);
            }
        });
        transferTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                depositLayout.setVisibility(View.INVISIBLE);
                withdrawlLayout.setVisibility(View.INVISIBLE);
                transferLayout.setVisibility(View.VISIBLE);
            }
        });
    }
    private void showAlert(String message, Context context){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                context);

        alertDialog.setTitle("ERROR");

        alertDialog.setMessage(message);


        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}
