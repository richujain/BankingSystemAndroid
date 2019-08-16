package com.example.bankingsystemandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {
    Button btnCall,btnMessage,btnEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        btnCall=findViewById(R.id.btnCall);
        btnEmail=findViewById(R.id.btnEmail);
        btnMessage=findViewById(R.id.btnSms);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ContactUs.this, "Calling 1234567890", Toast.LENGTH_SHORT).show();
            }
        });
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + 1234567890));
                startActivity(smsIntent);
            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactUs.this,EmailActivity.class));
            }
        });
    }
}
