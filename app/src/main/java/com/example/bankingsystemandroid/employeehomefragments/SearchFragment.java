package com.example.bankingsystemandroid.employeehomefragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bankingsystemandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SearchFragment extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private EditText accountNumber;
    private Button search;
    String accountNumberToSearch;
    TextView customerName,customerAddress,customerBirthDate,customerEmailId,customerContactNumber,customerPhotoAddressIdProof;
    ScrollView scrollView;
    TextView customerAccountNumber,customerAccountBalance,customerBankBranch;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        customerPhotoAddressIdProof = layout.findViewById(R.id.customerPhotoAddressIdProof);
        customerAddress = layout.findViewById(R.id.customerAddress);
        customerBirthDate = layout.findViewById(R.id.customerBirthDate);
        customerContactNumber = layout.findViewById(R.id.customerContactNumber);
        customerEmailId = layout.findViewById(R.id.customerEmailId);
        customerName = layout.findViewById(R.id.customerName);
        customerAccountBalance = layout.findViewById(R.id.customerAccountBalance);
        customerAccountNumber = layout.findViewById(R.id.customerAccountNumber);
        customerBankBranch = layout.findViewById(R.id.customerBankBranch);
        scrollView = layout.findViewById(R.id.scrollViewSearch);
        scrollView.setVisibility(View.INVISIBLE);
        accountNumber = layout.findViewById(R.id.accountNumber);
        search = layout.findViewById(R.id.searchImage);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchCustomer();
            }
        });

        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //Error resolved https://stackoverflow.com/questions/23490880/button-onclick-inside-fragment-is-not-working
        //return inflater.inflate(R.layout.fragment_search, null);
        return layout;
    }

    private void searchCustomer(){
        String userAccountNumber = accountNumber.getText().toString().trim();
        if(userAccountNumber.isEmpty()){
            showAlert("Enter A Valid Account Number",this.getContext());
        }
        else{
            accountNumberToSearch = accountNumber.getText().toString().trim();

            DatabaseReference myRef = database.getReference("customers").child(accountNumberToSearch);
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if (dataSnapshot.exists()){
                        String name = (String) dataSnapshot.child("name").getValue();
                        customerName.setText("Name : "+name);
                        String dummyVariable = (String) dataSnapshot.child("address").getValue();
                        customerAddress.setText("Address : "+dummyVariable);
                        dummyVariable = (String) dataSnapshot.child("birthdate").getValue();
                        customerBirthDate.setText("Birth Date : "+dummyVariable);
                        dummyVariable = (String) dataSnapshot.child("contactnumber").getValue();
                        customerContactNumber.setText("Contact Number : "+dummyVariable);
                        dummyVariable = (String) dataSnapshot.child("emailid").getValue();
                        customerEmailId.setText("Email ID : "+dummyVariable);
                        dummyVariable = (String) dataSnapshot.child("photoaddressproofid").getValue();
                        customerPhotoAddressIdProof.setText("ID Number : "+dummyVariable);
                        getAccountType(accountNumberToSearch);
                        scrollView.setVisibility(View.VISIBLE);
                    }
                    else{
                        scrollView.setVisibility(View.INVISIBLE);
                        showAlert("No customer record found associated with the account number "+ accountNumberToSearch,getContext());
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
    }
    private void getAccountType(final String accountNumberToSearch){
        DatabaseReference myRef = database.getReference("bank").child("savings").child(accountNumberToSearch);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()){
                    displayBankDetails(accountNumberToSearch,"savings");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        myRef = database.getReference("bank").child("current").child(accountNumberToSearch);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()){
                    displayBankDetails(accountNumberToSearch,"current");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
    private void displayBankDetails(final String accountNumberToSearch, String accountType){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("bank").child(accountType).child(accountNumberToSearch);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    String dummyVariable = (String) dataSnapshot.child("accountbalance").getValue();
                    customerAccountBalance.setText("Balance : "+dummyVariable);
                    dummyVariable = (String) dataSnapshot.child("bankbranch").getValue();
                    customerBankBranch.setText("Branch : "+dummyVariable);
                    scrollView.setVisibility(View.VISIBLE);
                }
                else{
                    scrollView.setVisibility(View.INVISIBLE);
                    showAlert("No customer record found associated with the account number "+ accountNumberToSearch,getContext());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
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
