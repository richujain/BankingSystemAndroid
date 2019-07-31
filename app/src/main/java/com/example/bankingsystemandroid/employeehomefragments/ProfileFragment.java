package com.example.bankingsystemandroid.employeehomefragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bankingsystemandroid.R;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        Glide.with(this).load(R.drawable.home)
                .into((ImageView) getActivity().findViewById(R.id.profilePicture));
        return inflater.inflate(R.layout.fragment_profile, null);
    }
}
