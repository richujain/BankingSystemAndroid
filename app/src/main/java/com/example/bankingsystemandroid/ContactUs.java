package com.example.bankingsystemandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {
    Button btnCall,btnMessage,btnEmail;
    TextView tvAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        btnCall=findViewById(R.id.btnCall);
        btnEmail=findViewById(R.id.btnEmail);
        btnMessage=findViewById(R.id.btnSms);
        tvAddress=findViewById(R.id.tvAddress);
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:43.887501,79.428406?z=16"));
                if (intent.resolveActivity(ContactUs.this.getPackageManager()) != null) {
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ContactUs.this, "No Intent Available", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:1234567890"));
                startActivity(intent);

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
