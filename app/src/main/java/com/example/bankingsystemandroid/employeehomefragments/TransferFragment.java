package com.example.bankingsystemandroid.employeehomefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bankingsystemandroid.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
            //DatabaseReference myRef = database.getReference("bank");
        }
    }
    private void getAccountType(String accountNumber){
        
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
}
