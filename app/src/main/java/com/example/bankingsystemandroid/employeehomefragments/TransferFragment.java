package com.example.bankingsystemandroid.employeehomefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bankingsystemandroid.R;

public class TransferFragment extends Fragment {
    Button depositTitle,withdrawlTitle,transferTitle;
    RelativeLayout depositLayout,withdrawlLayout,transferLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //return inflater.inflate(R.layout.fragment_transaction, null);

        View layout = inflater.inflate(R.layout.fragment_transaction, container, false);
        init(layout);




        return layout;
    }
    private void init(View view){
        depositTitle = view.findViewById(R.id.depositTitle);
        withdrawlTitle = view.findViewById(R.id.withdrawlTitle);
        transferTitle = view.findViewById(R.id.transferTitle);
        depositLayout = view.findViewById(R.id.depositLayout);
        withdrawlLayout = view.findViewById(R.id.withdrawlLayout);
        transferLayout = view.findViewById(R.id.transferLayout);
        setOnClick();
    }
    private void setOnClick(){
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
