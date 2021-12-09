package ch.zli.pg.app.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import ch.zli.pg.app.data.ContactModel;
import ch.zli.pg.app.service.ContactService;
import ch.zli.pg.contactscan.R;

public class SelectView extends AppCompatActivity {
    private ContactService mService;
    private boolean bound = false;
    private RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_view);
        list = findViewById(R.id.list);
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, ContactService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound)
            unbindService(conn);
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
        setItems();
    }

    private void setItems() {
        bound = false;
        list.setAdapter(new ContactAdapter(ContactModel.getContacts(), this));
    }
}