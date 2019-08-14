package com.example.bankingsystemandroid.employeehomefragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bankingsystemandroid.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

public class HomeFragment extends Fragment {

    EditText customerBirthDate,customerName,customerAddress,customerContactNumber,customerEmailId,customerPhotoAddressProofId;
    EditText customerAccountBalance,customerBankBranch;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Button addUserButton;
    RadioGroup radioGroup;
    private RadioButton radioButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //return inflater.inflate(R.layout.fragment_home, null);
        final View layout = inflater.inflate(R.layout.fragment_home, container, false);
        customerBirthDate = layout.findViewById(R.id.birthDate);
        customerName = layout.findViewById(R.id.name);
        customerAddress = layout.findViewById(R.id.address);
        customerContactNumber = layout.findViewById(R.id.contactNumber);
        customerEmailId = layout.findViewById(R.id.emailId);
        customerPhotoAddressProofId = layout.findViewById(R.id.photoAddressIdProof);
        addUserButton = layout.findViewById(R.id.addUserButton);
        radioGroup = layout.findViewById(R.id.radioGroup);
        customerAccountBalance = layout.findViewById(R.id.accountBalance);
        customerBankBranch = layout.findViewById(R.id.bankBranch);
        //first 2131296358
        //second 2131296446
        int selectedId = radioGroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        radioButton = layout.findViewById(selectedId);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });

        //DatePicker
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        customerBirthDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(layout.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //end of DatePicker

        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        //Error resolved https://stackoverflow.com/questions/23490880/button-onclick-inside-fragment-is-not-working
        //return inflater.inflate(R.layout.fragment_search, null);
        return layout;
    }

    private void addUser(){
        Boolean flag = validateDetails();
        if(flag){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("customers");
            Random rand = new Random();
            int accountNumber = rand.nextInt(9000000) + 1000000;
            Log.v("accountnumber",""+accountNumber);
            //Fetching and storing user details to a local variable
            String name = customerName.getText().toString();
            String birthDate = customerBirthDate.getText().toString();
            String address = customerAddress.getText().toString();
            String contactNumber = customerContactNumber.getText().toString();
            String photoAddressIdProof = customerPhotoAddressProofId.getText().toString();
            String accountType = radioButton.getText().toString();
            String accountBalance = customerAccountBalance.getText().toString();
            String bankBranch = customerBankBranch.getText().toString();
            //Storing Data to Firebase
            myRef.child(""+accountNumber).child("name").setValue(name);
            myRef.child(""+accountNumber).child("birthdate").setValue(birthDate);
            myRef.child(""+accountNumber).child("address").setValue(address);
            myRef.child(""+accountNumber).child("contactnumber").setValue(contactNumber);
            myRef.child(""+accountNumber).child("photoaddressproofid").setValue(photoAddressIdProof);
            myRef = database.getReference("bank");
            myRef.child(accountType).child(""+accountNumber).child("accountbalance").setValue(accountBalance);
            myRef.child(accountType).child(""+accountNumber).child("bankbranch").setValue(bankBranch);
            myRef.child(accountType).child(""+accountNumber).child("accountnumber").setValue(accountNumber);
            showAlert("Success","Customer Added. \n Account Number is "+accountNumber,getContext());
        }
    }
    private void showAlert(String title,String message, Context context){
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                context);

        alertDialog2.setTitle(title);

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
    private Boolean validateDetails(){
        String thisFieldCannotBeEmpty = "This Field Cannot Be Empty";
        Boolean flag = true;
        //Checking if the user left any field empty
        if(customerName.getText().toString().trim().length() == 0){
            customerName.setError(thisFieldCannotBeEmpty);
            flag = false;
        }
        else if(customerBirthDate.getText().toString().trim().length() == 0){
            customerBirthDate.setError(thisFieldCannotBeEmpty);
            flag = false;
        }
        else if(customerEmailId.getText().toString().trim().length() == 0){
            customerEmailId.setError(thisFieldCannotBeEmpty);
            flag = false;
        }
        else if(customerContactNumber.getText().toString().trim().length() == 0){
            customerContactNumber.setError(thisFieldCannotBeEmpty);
            flag = false;
        }
        else if(customerAddress.getText().toString().trim().length() == 0){
            customerAddress.setError(thisFieldCannotBeEmpty);
            flag = false;
        }
        else if(customerPhotoAddressProofId.getText().toString().trim().length() == 0){
            customerPhotoAddressProofId.setError(thisFieldCannotBeEmpty);
            flag = false;
        }else if(customerAccountBalance.getText().toString().trim().length() == 0){
            customerAccountBalance.setError(thisFieldCannotBeEmpty);
            flag = false;
        }else if(customerBankBranch.getText().toString().trim().length() == 0){
            customerBankBranch.setError(thisFieldCannotBeEmpty);
            flag = false;
        }
        return flag;
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        customerBirthDate.setText(sdf.format(myCalendar.getTime()));
    }
}
