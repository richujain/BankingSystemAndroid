package com.example.bankingsystemandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EmailActivity extends AppCompatActivity {
    EditText txtEmailId,txtBody,txtSubject;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        txtEmailId=findViewById(R.id.txtEmailId);
        txtBody=findViewById(R.id.txtBody);
        txtSubject=findViewById(R.id.txtSubject);
        btnSend=findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = txtEmailId.getText().toString();
                String subject = txtSubject.getText().toString();
                String body = txtBody.getText().toString();

                Intent emailIntent = new Intent(Intent.ACTION_SEND); //Intent.ACTION_SENDTO
                emailIntent.setType("text/plain");
                emailIntent.setData(Uri.parse("mailto:" + to));
                //emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);
                emailIntent.setType("message/rfc822");
                if(emailIntent.resolveActivity(EmailActivity.this.getPackageManager()) != null){
                    startActivity(Intent.createChooser(emailIntent, "Select Email Client"));
                }
                else
                {
                    Toast.makeText(EmailActivity.this,"No application to handle Email",Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}
