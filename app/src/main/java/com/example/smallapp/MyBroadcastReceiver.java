package com.example.smallapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.security.Provider;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isAirplaneMode = intent.getBooleanExtra("state", false);
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, "System boot complete", Toast.LENGTH_LONG).show();
        }
        String toastMessage = "Airplane mode: ";
        if (isAirplaneMode) {
            toastMessage += "ON";
        } else {
            toastMessage += "OFF";
        }
        Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
    }
}