package com.example.bankingsystemandroid.employeehomefragments;

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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bankingsystemandroid.R;

public class SearchFragment extends Fragment {

    private EditText accountNumber;
    private Button search;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search, container, false);
        accountNumber = layout.findViewById(R.id.accountNumber);
        search = layout.findViewById(R.id.searchImage);
        Log.v("blah inside","blah inside");
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("blah","blah");
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
            showAlert("Please Enter A Valid Account Number",this.getContext());
        }
    }
    private void showAlert(String message, Context context){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                context);

        alertDialog2.setTitle("ERROR");

        alertDialog2.setMessage(message);


        alertDialog2.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });
        alertDialog2.show();
    }
}
