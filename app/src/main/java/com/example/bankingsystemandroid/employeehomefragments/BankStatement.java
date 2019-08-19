package com.example.bankingsystemandroid.employeehomefragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bankingsystemandroid.BankStatementHolder;
import com.example.bankingsystemandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BankStatement extends Fragment {
    ListView listView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<BankStatementHolder> arrayList;
    ArrayAdapter<BankStatementHolder> adapter;
    BankStatementHolder bankStatementHolder;
    EditText accountNumber;
    Button searchButton;
    ScrollView scrollView;
    String id = "";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //return inflater.inflate(R.layout.fragment_bankstatement, null);
        final View layout = inflater.inflate(R.layout.fragment_bankstatement, container, false);
        listView = layout.findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        accountNumber = layout.findViewById(R.id.accountNumberToSearch);
        searchButton = layout.findViewById(R.id.searchButton);
        adapter = new ArrayAdapter<>(getContext(),R.layout.bankstatement_layout,R.id.bankStatementTextView, arrayList);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("transactions");
        scrollView = layout.findViewById(R.id.scrollViewStatement);
        //scrollView.setVisibility(View.INVISIBLE);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(accountNumber.getText().toString().trim().length() == 0){
                    accountNumber.setError("Enter a valid account number");
                }
                else{
                    String accountNumberToSearch = accountNumber.getText().toString().trim();
                    getBankStatement(accountNumberToSearch);
                }
            }
        });

        return layout;
    }
    private void getBankStatement(final String accountNumberToSearch){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    id = dataSnapshot1.getKey();
                    String date = (String) dataSnapshot1.child(id).child("datetime").getValue();
                    String amount = (String) dataSnapshot1.child(id).child("amount").getValue();
                    String beneficiary = (String) dataSnapshot1.child(id).child("beneficiary").getValue();
                    String remitter = (String) dataSnapshot1.child(id).child("remitter").getValue();
                    String statement = "";
                    if(dataSnapshot1.child(id).child("remitter").equals(accountNumberToSearch) && dataSnapshot1.child(id).child("beneficiary").equals("cash")){
                        statement = "$"+amount+" Deposited on "+date;
                    }
                    else if(dataSnapshot1.child(id).child("remitter").equals("cash") && dataSnapshot1.child(id).child("beneficiary").equals(accountNumberToSearch)) {
                        statement = "$"+amount+" Withdrawn on "+date;
                    }
                    else if(dataSnapshot1.child(id).child("remitter").equals(accountNumberToSearch)){
                        statement = "$"+amount+" transferred to "+beneficiary +" on "+date;
                    }
                    else if(dataSnapshot1.child(id).child("beneficiary").equals(accountNumberToSearch)){
                        statement = "$"+amount+" credited from "+remitter +" on "+date;
                    }
                    if(!statement.isEmpty()){{
                        bankStatementHolder = new BankStatementHolder(statement);
                        arrayList.add(bankStatementHolder);
                    }}
                }
                if(!arrayList.isEmpty()){
                    scrollView.setVisibility(View.VISIBLE);
                }
                Log.v("arrayList",""+arrayList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
