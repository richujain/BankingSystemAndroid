package com.example.bankingsystemandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bankingsystemandroid.employeehomefragments.BankStatement;
import com.example.bankingsystemandroid.employeehomefragments.HomeFragment;
import com.example.bankingsystemandroid.employeehomefragments.ProfileFragment;
import com.example.bankingsystemandroid.employeehomefragments.SearchFragment;
import com.example.bankingsystemandroid.employeehomefragments.SettingsFragment;
import com.example.bankingsystemandroid.employeehomefragments.TransferFragment;
import com.flarebit.flarebarlib.FlareBar;
import com.flarebit.flarebarlib.Flaretab;
import com.flarebit.flarebarlib.TabEventObject;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EmployeeHome extends AppCompatActivity  {
    FlareBar bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home);
        bottomBar = findViewById(R.id.bottomBar);
        //defining
        bottomBar.setBarBackgroundColor(Color.parseColor("#FFFFFF"));
        ArrayList<Flaretab> tabs = new ArrayList<>();
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.home),"Add User","#FFECB3"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.searchb),"Search","#80DEEA"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.transaction),"Transfer","#FFECB3"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.bankstatement),"Bank Statement","#B39DDB"));
        tabs.add(new Flaretab(getResources().getDrawable(R.drawable.avatarb),"Profile","#EF9A9A"));
        //tabs.add(new Flaretab(getResources().getDrawable(R.drawable.settingsb),"Settings","#B2DFDB"));

        bottomBar.setTabList(tabs);
        bottomBar.attachTabs(EmployeeHome.this);
        //setting
        bottomBar.setTabChangedListener(new TabEventObject.TabChangedListener() {
            @Override
            public void onTabChanged(LinearLayout selectedTab, int selectedIndex, int oldIndex) {
                //tabIndex starts from 0 (zero). Example : 4 tabs = last Index - 3
                Fragment fragment = null;

                switch (selectedIndex) {
                    case 0:
                        fragment = new HomeFragment();
                        break;

                    case 1:
                        fragment = new SearchFragment();
                        break;

                    case 2:
                        fragment = new TransferFragment();
                        break;

                    case 3:
                        fragment = new BankStatement();
                        break;
                    case 4:
                        fragment = new ProfileFragment();
                        break;
                    case 5:
                        fragment = new SettingsFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });
        loadFragment(new HomeFragment());
       // BottomNavigationView navigation = findViewById(R.id.bottomBar);
        //navigation.setOnNavigationItemSelectedListener((BottomNavigationView.OnNavigationItemSelectedListener) EmployeeHome.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.employee_home_page,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int resId = item.getItemId();
        if(resId == R.id.rateUs){

        }
        switch (resId){
            case R.id.rateUs:
                Toast.makeText(this, "Clicked on Rate Us", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EmployeeHome.this,RateUs.class));
                break;
            case R.id.contactUs:

               startActivity(new Intent(EmployeeHome.this,ContactUs.class));
               break;
            case R.id.aboutUs:
                startActivity(new Intent(EmployeeHome.this,AboutUs.class));


                break;
                default:
                    Toast.makeText(this, "Invalid Input", Toast.LENGTH_SHORT).show();
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.containerParent, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
