package com.example.bankingsystemandroid.employeehomefragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bankingsystemandroid.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    EditText customerBirthDate,customerName,customerAddress,customerContactNumber,customerEmailId,customerPhotoAddressProofId;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    Button addUserButton;

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
        }
        else{

        }
        return flag;
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        customerBirthDate.setText(sdf.format(myCalendar.getTime()));
    }
}
