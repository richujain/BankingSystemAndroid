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
import android.widget.Toast;

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
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    boolean flag = false;
    //BankStatementHolder bankStatementHolder;
    EditText accountNumber;
    Button searchButton;
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
        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),R.layout.bankstatement_layout,R.id.bankStatementTextView, arrayList);
        flag = false;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    String date = dataSnapshot1.child("datetime").getValue(String.class);
                    String amount = dataSnapshot1.child("amount").getValue(String.class);
                    String beneficiary = dataSnapshot1.child("beneficiary").getValue(String.class);
                    String remitter = dataSnapshot1.child("remitter").getValue(String.class);
                    Log.v("accountNumberToSearch","accountNumberToSearch"+accountNumberToSearch);
                    Log.v("remitter","remitter"+remitter);

                    String statement = "";
                    if(!remitter.isEmpty() && !beneficiary.isEmpty()){
                        if(remitter.equals(accountNumberToSearch) && beneficiary.equals("cash")){
                            statement = "$"+amount+" Deposited on "+date;
                            flag = true;
                        }
                        else if(remitter.equals("cash") && beneficiary.equals(accountNumberToSearch)) {
                            statement = "$"+amount+" Withdrawn on "+date;
                            flag = true;
                        }
                        else if(remitter.equals(accountNumberToSearch)){
                            statement = "$"+amount+" transferred to "+beneficiary +" on "+date;
                            flag = true;
                        }
                        else if(beneficiary.equals(accountNumberToSearch)){
                            statement = "$"+amount+" credited from "+remitter +" on "+date;
                            flag = true;
                        }

                    }

                    Log.v("statement","statement"+statement);
                    if(!statement.isEmpty()){
                        flag = true;
                        arrayList.add(statement);
                    }
                }
                if(!arrayList.isEmpty()){
                    listView.setVisibility(View.VISIBLE);
                    listView.setAdapter(adapter);
                    if(!flag){
                        Toast.makeText(getContext(), "No Transaction Record Found.", Toast.LENGTH_SHORT).show();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
