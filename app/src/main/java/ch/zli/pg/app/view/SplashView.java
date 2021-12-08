package ch.zli.pg.app.view;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import ch.zli.pg.app.service.ContactService;
import ch.zli.pg.contactscan.R;

public class SplashView extends AppCompatActivity {

    private ContactService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_view);
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bindService();
            } else {
                Toast.makeText(this, "App not working without permission to read contacts...", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void bindService() {
        Intent intent = new Intent(this, ContactService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(conn);
    }

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ContactService.ContactBinder binder = (ContactService.ContactBinder) service;
            mService = binder.getService();
            onServiceBound();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void onServiceBound() {
        mService.loadContacts();
        startActivity(new Intent(this, MenuView.class));
    }

}