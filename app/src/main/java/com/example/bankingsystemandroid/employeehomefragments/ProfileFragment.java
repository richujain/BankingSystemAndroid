package com.example.bankingsystemandroid.employeehomefragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bankingsystemandroid.LoginTimeDetailsManager;
import com.example.bankingsystemandroid.R;
import com.example.bankingsystemandroid.UserLogin;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileFragment extends Fragment {
    private View myFragmentView;
    private Button logoutUser;
    private TextView heading;

    private ArrayAdapter myAdapter;

    private LoginTimeDetailsManager loginTimeDetailsManager;
    private final static String TABLE_NAME = "AndroidLoginDetails";
    //sql string to create the table
    private static final String tableCreatorString =
            "CREATE TABLE "+ TABLE_NAME + " (id text primary key, loginTime text);";



    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        myFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutUser = myFragmentView.findViewById(R.id.logoutUser);
        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), UserLogin.class));
                getActivity().finish();
            }
        });

        try {
            loginTimeDetailsManager = new LoginTimeDetailsManager(myFragmentView.getContext());
            //create the table
            loginTimeDetailsManager.dbInitialize(TABLE_NAME, tableCreatorString);
        }
        catch(Exception exception)
        {
            Log.i("Error: ",exception.getMessage());
        }

        //ImageView profilePicture = myFragmentView.findViewById(R.id.profilePicture);
        // profilePicture.setBackgroundResource(R.drawable.app_logo);

        /*Glide.with(getContext()).load(R.drawable.home)
                .into((ImageView) myFragmentView.findViewById(R.id.profilePicture));*/
        showLoginData();

        return myFragmentView;
    }

    public void showLoginData()
    {


        try {
            ArrayList f = loginTimeDetailsManager.ShowLoginDetails();

            String result=f.toString();

            ListView list=myFragmentView.findViewById( R.id.logindetailsList);
            // myAdapter = new ArrayAdapter(this, R.layout.fragment_bankstatement, f);
            myAdapter = new ArrayAdapter<String>(getContext(),R.layout.bankstatement_layout,R.id.bankStatementTextView, f);

            // assign the list adapter
            list.setAdapter(myAdapter);


        }
        catch (Exception exception)
        {
            Log.i("Error: ",exception.getMessage());

        }


    }


}