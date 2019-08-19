package com.example.bankingsystemandroid.employeehomefragments;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bankingsystemandroid.R;
import com.example.bankingsystemandroid.UserLogin;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    private View myFragmentView;
    private Button logoutUser;

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

        //ImageView profilePicture = myFragmentView.findViewById(R.id.profilePicture);
       // profilePicture.setBackgroundResource(R.drawable.app_logo);

        /*Glide.with(getContext()).load(R.drawable.home)
                .into((ImageView) myFragmentView.findViewById(R.id.profilePicture));*/


        return myFragmentView;
    }


}
