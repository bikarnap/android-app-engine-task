package com.example.smallapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    EditText etPhoneNumber;
    EditText etMessage;
    Button btnSendSms;
    TextView tvSendSms;
    private MyBroadcastReceiver receiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);;
        this.registerReceiver(receiver, filter);

        etPhoneNumber = (EditText) findViewById(R.id.editTextPhone);
        etMessage = (EditText) findViewById(R.id.editTextMessage);
        tvSendSms = (TextView) findViewById(R.id.tvSendSms);
        btnSendSms = (Button) findViewById(R.id.btn_send_sms);
        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(
                        etPhoneNumber.getText().toString()
                        ,null
                        , etMessage.getText().toString()
                        , null
                        , null);
                tvSendSms.setText("Sms sent");
            }
        });

        if(ActivityCompat.checkSelfPermission(this
                , Manifest.permission.SEND_SMS ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this
                    , new String[] {Manifest.permission.SEND_SMS}
                    , 123);
            btnSendSms.setEnabled(false);
        } else {
            Log.d("PERMISSION","Permission is granted");
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Sending Sms is enabled", Toast.LENGTH_LONG).show();
                btnSendSms.setEnabled(true);
            } else {
                Toast.makeText(this, "Sending Sms needs permission", Toast.LENGTH_LONG).show();
                btnSendSms.setEnabled(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(receiver);
        super.onDestroy();
    }
}