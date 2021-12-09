package ch.zli.pg.app.view;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ch.zli.pg.app.service.ContactService;
import ch.zli.pg.contactscan.R;

public class ShareView extends AppCompatActivity {

    private ContactService mService;
    private boolean bound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_view);
        int contact_id = getIntent().getIntExtra("contact_id", -1);
        if (contact_id == -1) {
            Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
        }
        connectService();
    }

    private void connectService() {

    }

    private final ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ContactService.ContactBinder binder = (ContactService.ContactBinder) service;
            mService = binder.getService();
            bound = true;
            onServiceBound();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void onServiceBound() {


    }
}