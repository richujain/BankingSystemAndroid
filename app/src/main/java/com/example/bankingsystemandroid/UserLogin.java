package com.example.bankingsystemandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLogin extends AppCompatActivity {


    private FirebaseAuth mAuth;
    EditText username,password;
    Button btnLogin;
    TextView fingerprintMessage;
    AlertDialog.Builder alertDialogBuilder;
    Intent intent;

    //---------------
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        mAuth = FirebaseAuth.getInstance();
        init();

        loginCheck();

        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
                keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

                if(!fingerprintManager.isHardwareDetected()){
                    Toast.makeText(this, "Fingerprint Scanner Not Detected. ", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }else if(ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission Not Granted. ", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }
                else if(!keyguardManager.isKeyguardSecure()){
                    Toast.makeText(this, "Secure your phone with Phone Lock", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }
                else if(!fingerprintManager.hasEnrolledFingerprints()){
                    Toast.makeText(this, "You should add atleast one fingerprint to use this feature. ", Toast.LENGTH_SHORT).show();
                    FirebaseAuth.getInstance().signOut();
                }
                else{
                    Toast.makeText(this, "Place your finger on scanner to start scanning. ", Toast.LENGTH_SHORT).show();
                    FingerPrintHandler fingerPrintHandler = new FingerPrintHandler(this);
                    fingerPrintHandler.startAuth(fingerprintManager, null);
                }
            }
        }


    }








    private void init(){
        alertDialogBuilder = new AlertDialog.Builder(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        fingerprintMessage = findViewById(R.id.fingerprintMessage);
        intent = new Intent(UserLogin.this,EmployeeHome.class);
        //username.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());


    }

    private void loginCheck(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()){
                    final String userUsername = username.getText().toString().trim();
                    String userPassword = password.getText().toString().trim();
                    mAuth.signInWithEmailAndPassword(userUsername, userPassword)
                            .addOnCompleteListener(UserLogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information


                                        startActivity(intent);
                                        finish();
                                        //updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        /*Toast.makeText(UserLogin.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();*/
                                        alertDialogBuilder.setTitle("Login Failed");
                                        alertDialogBuilder.setMessage("Enter Correct Credentials.");
                                        alertDialogBuilder.setCancelable(false)
                                                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog,int id) {
                                                        // if this button is clicked, close
                                                        // current activity

                                                    }
                                                });
                                        alertDialogBuilder.create();
                                        alertDialogBuilder.show();
                                        //updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }
            }
        });
        /*
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
                */
    }

    private boolean validation(){
        if(username.getText().toString().trim().length() == 0){
            username.setError("Enter Email ID");
            return false;
        }
        else if(password.getText().toString().trim().length() == 0){
            password.setError("Enter Password");
            return false;
        }
        else if(!isValidEmail(username.getText().toString())){
            username.setError("Enter Valid Email");
            return false;
        }
        else{
            return true;
        }
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    /*
    * Logout
    FirebaseAuth.getInstance().signOut();
    * */

    /*
    *
    mAuth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }

                // ...
            }
        });

    * */


    /*
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    */
}
