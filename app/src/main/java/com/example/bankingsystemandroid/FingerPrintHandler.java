package com.example.bankingsystemandroid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {
    private Context context;
    public FingerPrintHandler(Context context) {
        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager,FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal =  new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an error "+ errString,false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Authentication Failed. ",false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error "+helpString,false);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Authentication successful",true);
    }

    private void update(String s, boolean b) {
        TextView fingerprintMessage = ((Activity)context).findViewById(R.id.fingerprintMessage);
        fingerprintMessage.setText(s);
        if(b == false){
            fingerprintMessage.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));

        }else{
            fingerprintMessage.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
            ((Activity)context).setIntent(new Intent(context,EmployeeHome.class));
            context.startActivity(new Intent(context,EmployeeHome.class));
            ((Activity) context).finish();
        }
    }
}
