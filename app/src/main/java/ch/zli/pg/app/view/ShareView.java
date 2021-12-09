package ch.zli.pg.app.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ch.zli.pg.app.service.ContactService;
import ch.zli.pg.contactscan.R;

public class ShareView extends AppCompatActivity {

    private ContactService mService;
    private boolean bound;

    private ImageView imageView;
    private ProgressBar progressBar;
    private long contact_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_view);
        contact_id = getIntent().getLongExtra("contact_id", -1L);
        if (contact_id == -1) {
            Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
        }
        imageView = findViewById(R.id.imageCode);
        progressBar = findViewById(R.id.progressBar);
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(this, ContactService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
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
        Bitmap bitmap = mService.createQRCode(contact_id);
        Log.e("Bitmap", bitmap + " ");
        imageView.setImageBitmap(bitmap);
        progressBar.setVisibility(View.INVISIBLE);
    }
}